package model;

public class CPU {

//  CPU attributes
    private int    contextSwitch;
    private int    cpuCycle;
    private int    slice;
    private String state;
    private int    processCycle;
    
    //private int    programCounter;
    //private int    processCycleIO;
    
//  Relationships
    private Memory memory;
    private Process process;

    public CPU() {
        
    }

    public CPU(int contextSwitch, int slice) {
        this.contextSwitch = contextSwitch;
        this.cpuCycle = 0;
        this.slice = slice;
        this.state = "idle";
        processCycle = 0;
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
    
    public void setMemory(Memory memory) {
        this.memory = memory;
    }
    
    
    public Memory cycle() {
        cpuCycle++;
        System.err.println("\nCPU cycle: "+cpuCycle);
        
        if (state.equals("idle"))
        {
            pullProcess();
        }
        
        if (process != null) {
            if (process.getType().equals("cpu")){
                cpuProcess();
            }
            else if (process.getType().equals("i/o")){
                ioProcess();
            }
        }
        
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
            System.err.printf("+ LOADING CPU PROCESS %s: %d\n",process.getName(), processCycle);
            state = "busy";
        }
        
    }
    
    public void executeProcess() {
        process.setTime(process.getTime()-1);
        processCycle += 1;
        System.err.printf("= CPU EXECUTING PROCESS %s: %d\n",process.getName(), processCycle);
    }
    
    public void requestIO() {
        startContextSwitch();
        memory.requestIO(process);
        memory.removeProcess(process);
        System.err.printf("i/o TRANSFERING CPU PROCESS %s FOR IO QUEUE: %d\n",process.getName(), processCycle);
        process = null;
        state = "idle";
        processCycle = 0;
        System.err.println("##################################################################");
    }
    
    public void pushProcess() {
        startContextSwitch();
        memory.returnProcess(process);
        System.err.printf("- REMOVING CPU PROCESS %s: %d\n",process.getName(), processCycle);
        process = null;
        state = "idle";
        processCycle = 0;
        System.err.println("##################################################################");
    }
    
    public void concludeProcess() {
        startContextSwitch();
        memory.removeProcess(process);
        System.err.printf("ok PROCESS CONCLUDED %s: %d\n",process.getName(), processCycle);;
        process = null;
        state = "idle";
        processCycle = 0;
        System.err.println("##################################################################");
    }
    
    public void startContextSwitch() {
       System.err.println("@ CONTEXT SWITCH");
       cpuCycle += contextSwitch-1;
    }    

}
