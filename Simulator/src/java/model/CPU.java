package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CPU {

//  CPU attributes
    private int     contextSwitch;
    private int     countContextSwitch;
    private int     cpuCycle;
    private int     slice;
    private String  state;
    private int     processCycle;
    private List    generalLog;
    private List    cycleLog;
    private boolean performingContextSwitch;
    
    //private int    programCounter;
    //private int    processCycleIO;
    
//  Relationships
    private Memory      memory;
    private Process     process;
    private Log         log;
    private ProcessLog  processLog;

    public CPU() {
        
    }

    public CPU(int contextSwitch, int slice) {
        this.contextSwitch = contextSwitch;
        countContextSwitch = contextSwitch;
        this.cpuCycle = 0;
        this.slice = slice;
        state = "idle";
        processCycle = 0;
        performingContextSwitch = false;
        generalLog = new LinkedList();
        cycleLog = new LinkedList();
        
        log = new Log();
        log.setCpuContextSwitch(contextSwitch);
        log.setCpuCycle(cpuCycle);
        log.setCpuSlice(slice);
        log.setCpuState(state);
    }

    public String getState() {
        return state;
    }

    public int getCycle() {
        return cpuCycle;
    }

    public Memory getMemory() {
        return memory;
    }
    
    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
    
    public void clearLog() {
        Queue<Process> concludQueue = memory.getConcludedProcess();
        log = new Log();
        log.setCpuContextSwitch(contextSwitch);
        log.setCpuCycle(cpuCycle);
        log.setCpuSlice(slice);
        log.setCpuState(state);
        //memory.setConcludedProcess(concludQueue);
    }
    
    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public void setSlice(int slice) {
        this.slice = slice;
    }

    public List getGeneralLog() {
        return generalLog;
    }

    public List getCycleLog() {
        return cycleLog;
    }

    public Process getProcess() {
        return process;
    }
    
    public Memory cycle() {
        cpuCycle++;
        log.setCpuCycle(cpuCycle);
        
        System.err.println("\nCPU cycle: "+cpuCycle);
        
        if (performingContextSwitch == false) {
        
            if (state.equals("idle"))
            {
                pullProcess();
            }
            
        } else if (performingContextSwitch == true) {
            
            startContextSwitch();
            
        }
        
        if (process != null) {
            if (process.getType().equals("cpu")){
                cpuProcess();
            }
            else if (process.getType().equals("io")){
                ioProcess();
            }
        }
        
        
        cycleLog.clear();
        
        
        
        return memory;
            
    }
    
    public void cpuProcess() {
     
        if (process.getTime() != 0) {
            if (processCycle < slice)
            {
                executeProcess();
            }
            else if (processCycle == slice)
            {
                pushProcess();
            }
        }
        
        else if (process.getTime() == 0) {
            concludeProcess();
        }
        
    }
    
    public void ioProcess() {
        
        if (process.getTime() != 0) {
            if (processCycle < slice && processCycle < process.getProcessingTimeIO()) {
                executeProcess();
            }
            else if (processCycle == process.getProcessingTimeIO()) {
                requestIO();
            }
            else if (processCycle == slice) {
                pushProcess();
            }
        }
        
        else if (process.getTime() == 0) {
            concludeProcess();
        }
        
    }
    
    public void pullProcess() {
        
        process = memory.getProcess();
        
        if (process != null){
            processLog  = memory.getProcessLog(process);
            processLog.setCpuInputCycle(cpuCycle);
            log.setMessage("+: Process " + process.getName() + " pull to cpu");
            log.setProcessName(process.getName());
            cycleLog.add("+ LOADING CPU PROCESS " + process.getName()  +  ": " + processCycle);
            System.err.printf("+ LOADING CPU PROCESS %s: %d\n",process.getName(), processCycle);
            state = "busy";
            log.setCpuState(state);
            
        }
        else {
            log.setProcessName(null);
        }
        
        log.setMemoryReadyQueue(memory.getQueueProcess());
        log.setMemoryIOQueue(memory.getInputOutputRequest());
        log.setMemoryConcludedQueue(memory.getConcludedProcess());
        
    }
    
    public void executeProcess() {
        process.setTime(process.getTime()-1);
        processCycle += 1;
        cycleLog.add("= CPU EXECUTING PROCESS "+ process.getName() +": " + processCycle);
        log.setMessage("ex: "+ processCycle  +"º execution cycle of process " + process.getName());
        log.setProcessTime(process.getTime());
        log.setProcessCycle(processCycle);
        log.setMemoryReadyQueue(memory.getQueueProcess());
        log.setMemoryIOQueue(memory.getInputOutputRequest());
        log.setMemoryConcludedQueue(memory.getConcludedProcess());
        log.setProcessName(process.getName());
        System.err.printf("= CPU EXECUTING PROCESS %s: %d\n",process.getName(), processCycle);
    }
    
    public void requestIO() {
        startContextSwitch();
        memory.requestIO(process);
        cycleLog.add("i/o TRANSFERING CPU PROCESS "+ process.getName() +" FOR IO QUEUE: "+ processCycle);
        System.err.printf("i/o TRANSFERING CPU PROCESS %s FOR IO QUEUE: %d\n",process.getName(), processCycle);
        log.setMessage("i/o: Process " + process.getName() + " transfering for io queue");
        process = null;
        state = "idle";
        log.setCpuState(state);
        log.setMemoryReadyQueue(memory.getQueueProcess());
        log.setMemoryIOQueue(memory.getInputOutputRequest());
        log.setMemoryConcludedQueue(memory.getConcludedProcess());
        processCycle = 0;
        
        System.err.println("##################################################################");
    }
    
    public void pushProcess() {
        startContextSwitch();
        
        memory.returnProcess(process);
        memory.returnProcessLog(processLog);
        
        cycleLog.add("- REMOVING CPU PROCESS "+ process.getName() +": " + processCycle);
        System.err.printf("- REMOVING CPU PROCESS %s: %d\n",process.getName(), processCycle);
        log.setMessage("-: process " + process.getName() + " removing to cpu and return to ready queue");
        process = null;
        state = "idle";
        log.setCpuState(state);
        log.setMemoryReadyQueue(memory.getQueueProcess());
        log.setMemoryIOQueue(memory.getInputOutputRequest());
        log.setMemoryConcludedQueue(memory.getConcludedProcess());
        processCycle = 0;   
        System.err.println("##################################################################");
    }
    
    public void concludeProcess() {
        startContextSwitch();

        processLog.setCpuOutputCycle(cpuCycle);
        
        memory.returnProcessLog(processLog);
        memory.concludedProcess(process);
        
        cycleLog.add("ok PROCESS CONCLUDED "+ process.getName() +": "+ processCycle);
        System.err.printf("ok PROCESS CONCLUDED %s: %d\n",process.getName(), processCycle);
        log.setMessage("ok: process " + process.getName() + " conclude and transfering to conclude queue");
        process = null;
        state = "idle";
        log.setCpuState(state);
        
        log.setMemoryReadyQueue(memory.getQueueProcess());
        log.setMemoryIOQueue(memory.getInputOutputRequest());
        log.setMemoryConcludedQueue(memory.getConcludedProcess());
        processCycle = 0;
        System.err.println("##################################################################");
    }
    
    public void startContextSwitch() {
        
        log.setMessage("(): Performing Context Switch ");
        
        if (performingContextSwitch == false)
            performingContextSwitch = true;
        
        if (performingContextSwitch == true) {
            
            countContextSwitch--;
            
            if (countContextSwitch <= 0){
                performingContextSwitch = false;
                countContextSwitch = contextSwitch;
            }

        }
        log.setMemoryReadyQueue(memory.getQueueProcess());
        log.setMemoryIOQueue(memory.getInputOutputRequest());
        log.setMemoryConcludedQueue(memory.getConcludedProcess());
            
       cycleLog.add("@ CONTEXT SWITCH");
       System.err.println("@ CONTEXT SWITCH");
       
       
    }    

}
