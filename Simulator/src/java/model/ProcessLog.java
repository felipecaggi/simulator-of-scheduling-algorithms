/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author felipecaggi
 */
public class ProcessLog {
    
    private String  name;
    private int     arrivalCycleInMemory;
    private int     memoryTimeout;
    private int     cpuInputCycle;
    private int     cpuOutputCycle;

//  ++++++++++ BUILDERS ++++++++++
    
    public ProcessLog() {}

    public ProcessLog(String name) {
        this.name = name;
    }

// ++++++++++ GETTERS ++++++++++

    public String getName() {
        return name;
    }

    public int getArrivalCycleInMemory() {
        return arrivalCycleInMemory;
    }

    public int getMemoryTimeout() {
        return memoryTimeout;
    }

    public int getCpuInputCycle() {
        return cpuInputCycle;
    }

    public int getCpuOutputCycle() {
        return cpuOutputCycle;
    }

// ++++++++++ SETTERS ++++++++++
    
    public void setName(String name) {
        this.name = name;
    }

    public void setArrivalCycleInMemory(int arrivalCycleInMemory) {
        this.arrivalCycleInMemory = arrivalCycleInMemory;
    }

    public void setMemoryTimeout(int memoryTimeout) {
        this.memoryTimeout = memoryTimeout;
    }

    public void setCpuInputCycle(int cpuInputCycle) {
        this.cpuInputCycle = cpuInputCycle;
        memoryTimeout = cpuInputCycle - arrivalCycleInMemory;
    }

    public void setCpuOutputCycle(int cpuOutputCycle) {
        this.cpuOutputCycle = cpuOutputCycle;
    }
    
}
