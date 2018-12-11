/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author felipecaggi
 */
public class Log {
    
    private int     cpuContextSwitch;
    private int     cpuCycle;
    private int     cpuSlice;
    private String  cpuState;
    
    private String  processName;
    private int     processTime;
    private int     processCycle;
    private int     processWaitTimeIO;
    
    private Memory  memory;
    private Queue<Process> memoryIOQueue;
    private Queue<Process> memoryReadyQueue;
    private Queue<Process> memoryConcludedQueue;
    
    private List<String> message;
    
    public Log(){
        message = new LinkedList<>();
    }

//  --------- CPU -------------------------------------------------------------------------------------

    public int getCpuContextSwitch() {return cpuContextSwitch;}
    public int getCpuCycle() {return cpuCycle;}
    public int getCpuSlice() {return cpuSlice;}
    public String getCpuState() {return cpuState;}
    
    public void setCpuContextSwitch(int cpuContextSwitch) {this.cpuContextSwitch = cpuContextSwitch;}
    public void setCpuCycle(int cpuCycle) {this.cpuCycle = cpuCycle;}
    public void setCpuSlice(int cpuSlice) {this.cpuSlice = cpuSlice;}
    public void setCpuState(String cpuState) {this.cpuState = cpuState;}
    
//  --------- PROCESS -------------------------------------------------------------------------------------    

    public String getProcessName() {return processName;}
    public int getProcessTime() {return processTime;}
    public int getProcessCycle() {return processCycle;}
    public int getProcessWaitTimeIO() {return processWaitTimeIO;}
    public Memory getMemory() {return memory;}
    
    public void setProcessName(String processName) {this.processName = processName;}
    public void setProcessTime(int processTime) {this.processTime = processTime;}
    public void setProcessCycle(int processCycle) {this.processCycle = processCycle;}
    public void setProcessWaitTimeIO(int processWaitTimeIO) {this.processWaitTimeIO = processWaitTimeIO;}
    public void setMemory(Memory memory) {this.memory = memory;}
    
//  --------- MEMORY --------------------------------------------------------------------------------------    

    public Queue<Process> getMemoryIOQueue() {return memoryIOQueue;}
    public Queue<Process> getMemoryReadyQueue() {return memoryReadyQueue;}
    public Queue<Process> getMemoryConcludedQueue() {return memoryConcludedQueue;}

    public void setMemoryIOQueue(Queue<Process> memoryIOQueue) {this.memoryIOQueue = memoryIOQueue;}
    public void setMemoryReadyQueue(Queue<Process> memoryReadyQueue) {this.memoryReadyQueue = memoryReadyQueue;}
    public void setMemoryConcludedQueue(Queue<Process> memoryConcludedQueue) {this.memoryConcludedQueue = memoryConcludedQueue;}

   //  --------- OTHERS --------------------------------------------------------------------------------------    

    public List<String> getMessage() {return message;}

    public void setMessage(String mensage) {this.message.add(mensage);}
    
    //  --------- STATISTICS -------------------------------------------------------------------------------------
       
}
