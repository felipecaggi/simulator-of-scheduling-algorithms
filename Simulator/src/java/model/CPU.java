package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CPU {

//  CPU attributes
    private int    contextSwitch;
    private int    cpuCycle;
    private int    slice;
    private String state;
    private int    processCycle;
    private List generalLog;
    private List cycleLog;
    
    
    //private int    programCounter;
    //private int    processCycleIO;
    
//  Relationships
    private Memory memory;
    private Process process;
    private Log log;

    public CPU() {
        
    }

    public CPU(int contextSwitch, int slice) {
        this.contextSwitch = contextSwitch;
        this.cpuCycle = 0;
        this.slice = slice;
        state = "idle";
        processCycle = 0;
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
        
        if (state.equals("idle"))
        {
            pullProcess();
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
        cycleLog.add("- REMOVING CPU PROCESS "+ process.getName() +": " + processCycle);
        System.err.printf("- REMOVING CPU PROCESS %s: %d\n",process.getName(), processCycle);
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
        memory.concludedProcess(process);
        cycleLog.add("ok PROCESS CONCLUDED "+ process.getName() +": "+ processCycle);
        System.err.printf("ok PROCESS CONCLUDED %s: %d\n",process.getName(), processCycle);
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
        cycleLog.add("@ CONTEXT SWITCH");
       System.err.println("@ CONTEXT SWITCH");
       cpuCycle += contextSwitch-1;
    }    

}
