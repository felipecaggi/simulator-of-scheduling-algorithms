package controller;

import model.ShortTermScheduler;
import model.CPU;
import model.Memory;

public class OperationalSystem1 {
    
    private ShortTermScheduler scheduler;
    
    private final Memory memory;
    private final CPU cpu;

    public OperationalSystem1(CPU cpu, Memory memory) {
        this.cpu = cpu;
        this.memory = memory;
    }

    public void setProcessInformation(String name, int priority, int time, String type) {
        
        memory.addProcess(name, priority, time, type);
        
    }
    
    public void setProcessInformation(String name, int priority, int ioTime, int processingTimeIO, int time, String type) {
        
        memory.addProcess(name, priority, ioTime, processingTimeIO, time, type);
        
    }
    
    public void startSimulator(String algorithm) {
        
        scheduler = new ShortTermScheduler(algorithm, cpu, memory);
        
    }

}