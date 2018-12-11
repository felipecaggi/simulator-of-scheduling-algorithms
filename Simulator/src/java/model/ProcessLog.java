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
    private int     memoryTimeout;
    private int     cpuInputCycle;
    private int     cpuOutputCycle;
    private int     turnaroundTime;

//  ++++++++++ BUILDERS ++++++++++
    
    public ProcessLog() {}

    public ProcessLog(String name) {
        this.name = name;
    }

// ++++++++++ GETTERS ++++++++++

    public String getName() {
        return name;
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

    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    
// ++++++++++ SETTERS ++++++++++
    
    public void setName(String name) {
        this.name = name;
    }

    public void setMemoryTimeout(int memoryTimeout) {
        this.memoryTimeout = memoryTimeout;
    }

    public void setCpuInputCycle(int cpuInputCycle) {
        if (this.cpuInputCycle == 0)
            this.cpuInputCycle = cpuInputCycle;
    }

    public void setCpuOutputCycle(int cpuOutputCycle) {
        this.cpuOutputCycle = cpuOutputCycle;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
    
}
