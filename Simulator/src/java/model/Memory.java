package model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Memory {

    private Queue<Process> inputOutputRequest;

    private Queue<Process> queueProcess;

    public Memory() {
        
        inputOutputRequest = new LinkedList<>();
        queueProcess = new LinkedList<>();
    }

    public void addProcess(String name, int priority, int time, String type) {
                
        Process process = new Process(name, priority, time, type);
        queueProcess.add(process);
        
    }
    
    public void addProcess(String name, int priority, int ioTime, int processingTimeIO, int time, String type) {
        
        Process process = new Process(name, priority, ioTime, processingTimeIO, time, type);
        queueProcess.add(process);
        
    }
    
    public void removeProcess(Process process) {
        
        queueProcess.poll();
        
    }
    
    public void returnProcess(Process process) {
        
        queueProcess.poll();
        queueProcess.add(process);
    }
    
    public void returnProcessIO(Process process) {
        
        queueProcess.add(process);
    }
     
    public void requestIO(Process process) {
        
        inputOutputRequest.add(process);
    }
    
    public void receiveIO(Process process) {
        
        inputOutputRequest.remove(process);
    }

    public Queue<Process> getQueueProcess() {
        return queueProcess;
    }

    public Queue<Process> getInputOutputRequest() {
        return inputOutputRequest;
    }
    
    public Process getProcess(){
    
        return queueProcess.peek();
        
    }

}
