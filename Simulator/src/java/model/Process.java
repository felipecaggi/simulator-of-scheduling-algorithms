package model;

public class Process {

    private String name;

    private int priority;
    
    private int ioTime;
    
    private int processingTimeIO;

    private int time;
    
    private int timeWaitIO;

    private String type;

    private String state;
    
    private int arrivalCycleInMemory;
    private int memoryTimeout;
    private int cpuInputCycle;
    
    public Process(String name, int time, String type) {
        this.name = name;
        this.time = time;
        this.type = type;
        state = "Ready";
        arrivalCycleInMemory = 0;
        memoryTimeout = 0;
    }

    public Process(String name, int priority, int time, String type) {
        this.name = name;
        this.priority = priority;
        this.time = time;
        this.type = type;
        state = "Ready";
        arrivalCycleInMemory = 0;
        memoryTimeout = 0;
    }

    public Process(String name, int ioTime, int processingTimeIO, int time, String type) {
        this.name = name;
        this.ioTime = ioTime;
        this.processingTimeIO = processingTimeIO;
        this.time = time;
        timeWaitIO = 0;
        this.type = type;
        state = "Ready";
        arrivalCycleInMemory = 0;
        memoryTimeout = 0;
    }    
    
    public Process(String name, int ioTime, int processingTimeIO, int time, int timeWaitIO, String type) {
        this.name = name;
        this.ioTime = ioTime;
        this.processingTimeIO = processingTimeIO;
        this.time = time;
        this.timeWaitIO = timeWaitIO;
        this.type = type;
        state = "Ready";
        arrivalCycleInMemory = 0;
        memoryTimeout = 0;
    }

    Process() {
        state = "Ready";
        arrivalCycleInMemory = 0;
        memoryTimeout = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getIoTime() {
        return ioTime;
    }

    public void setIoTime(int ioTime) {
        this.ioTime = ioTime;
    }

    public int getProcessingTimeIO() {
        return processingTimeIO;
    }

    public void setProcessingTimeIO(int processingTimeIO) {
        this.processingTimeIO = processingTimeIO;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTimeWaitIO() {
        return timeWaitIO;
    }

    public void setTimeWaitIO(int timeWaitIO) {
        this.timeWaitIO = timeWaitIO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    //  --------- STATISTICS -------------------------------------------------------------------------------------

    public int getArrivalCycleInMemory() {
        return arrivalCycleInMemory;
    }

    public void setArrivalCycleInMemory(int arrivalCycleInMemory) {
        this.arrivalCycleInMemory = arrivalCycleInMemory;
    }

    public int getMemoryTimeout() {
        return memoryTimeout;
    }

    public void setMemoryTimeout(int memoryTimeout) {
        this.memoryTimeout = memoryTimeout;
    }

    public int getCpuInputCycle() {
        return cpuInputCycle;
    }

    public void setCpuInputCycle(int cpuInputCycle) {
        this.cpuInputCycle = cpuInputCycle;
    }
    
    
}
