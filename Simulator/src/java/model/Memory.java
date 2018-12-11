package model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Memory {

    private Queue<Process> inputOutputRequest;

    private Queue<Process> queueProcess;
    
    private Queue<Process> concludedProcess;
    
    private List<ProcessLog> processLogs;


    public Memory() {
        
        inputOutputRequest = new LinkedList<>();
        queueProcess = new LinkedList<>();
        concludedProcess = new LinkedList<>();
        
        processLogs = new LinkedList<>();
    }
    
    public void configureLogs() {
        
        for (Process process: queueProcess) {
            
            ProcessLog processLog = new ProcessLog(process.getName());
            
            processLogs.add(processLog);
            
        }
        
    }
    
    public Memory(Queue<Process> queueProcess) {
        this.queueProcess = queueProcess;
        inputOutputRequest = new LinkedList<>();
        concludedProcess = new LinkedList<>();
    }

    public Memory(Queue<Process> inputOutputRequest, Queue<Process> queueProcess, Queue<Process> concludedProcess) {
        this.inputOutputRequest = inputOutputRequest;
        this.queueProcess = queueProcess;
        this.concludedProcess = concludedProcess;
    }
    
    public void addProcess(Process process) {
        queueProcess.add(process);
    }
    
    public void addProcess(String name, int time, String type) {
                
        Process process = new Process(name, time, type);
        queueProcess.add(process);
        
    }

    public void addProcess(String name, int priority, int time, String type) {
                
        Process process = new Process(name, priority, time, type);
        queueProcess.add(process);
        
    }
    
    public void addProcess(String name, int ioTime, int processingTimeIO, int time, String type) {
        
        Process process = new Process(name, ioTime, processingTimeIO, time, type);
        queueProcess.add(process);
        
    }
    
    public void addProcess(String name, int ioTime, int ioProcessingTime, int priority, int time, String type) {
        
        Process process = new Process();
        process.setName(name);
        process.setIoTime(ioTime);
        process.setProcessingTimeIO(ioProcessingTime);
        process.setPriority(priority);
        process.setTime(time);
        process.setType(type);
        queueProcess.add(process);
        
    }
    
    public void addProcessPriority(String name, int ioTime, int processingTimeIO, int priority, int time, String type) {
        
        Process process = new Process();
        process.setName(name);
        process.setIoTime(ioTime);
        process.setProcessingTimeIO(processingTimeIO);
        process.setPriority(priority);
        process.setTime(time);
        process.setType(type);
        queueProcess.add(process);
        
    }
    
    public void addProcessIO(String name, int ioTime, int processingTimeIO, int priority, int time, int timeWaitIO, String type) {
        
        Process process = new Process();
        process.setName(name);
        process.setIoTime(ioTime);
        process.setProcessingTimeIO(processingTimeIO);
        process.setPriority(priority);
        process.setTime(time);
        process.setType(type);
        inputOutputRequest.add(process);
        
    }
    
    public void addProcessIOPriority(String name, int ioTime, int processingTimeIO, int priority, int time, int timeWaitIO, String type) {
        
        Process process = new Process();
        process.setName(name);
        process.setIoTime(ioTime);
        process.setProcessingTimeIO(processingTimeIO);
        process.setPriority(priority);
        process.setTime(time);
        process.setTimeWaitIO(timeWaitIO);
        process.setType(type);
        inputOutputRequest.add(process);
        
    }
    
    public void addProcessConcluded(String name, int ioTime, int ioProcessingTime, int priority, int time, String type) {
        
        Process process = new Process();
        process.setName(name);
        process.setIoTime(ioTime);
        process.setProcessingTimeIO(ioProcessingTime);
        process.setPriority(priority);
        process.setTime(time);
        process.setType(type);
        concludedProcess.add(process);
        
    }
    
    public void addProcessConcludedPriority(String name, int ioTime, int processingTimeIO, int priority, int time, String type) {
       
        Process process = new Process();
        process.setName(name);
        process.setIoTime(ioTime);
        process.setProcessingTimeIO(processingTimeIO);
        process.setPriority(priority);
        process.setTime(time);
        process.setType(type);
        concludedProcess.add(process);
        
    }
    
    public void removeProcess() {
        
        Deque<Process> deque = new LinkedList<>();
        
        queueProcess.forEach((p) -> {
            deque.add(p);
        });
        
        deque.pollLast();
        
        queueProcess.clear();
        
        deque.forEach((p) -> {
            queueProcess.add(p);
        });
        
    }
    
    public void removeProcess(Process process) {
        
        queueProcess.poll();
        
    }
    
    public void concludedProcess(Process process) {
        
        concludedProcess.add(process);
        
    }
    
    public void returnProcess(Process process) {
        
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

    public Queue<Process> getConcludedProcess() {
        return concludedProcess;
    }
    

    public void setInputOutputRequest(Queue<Process> inputOutputRequest) {
        this.inputOutputRequest = inputOutputRequest;
    }

    public void setQueueProcess(Queue<Process> queueProcess) {
        this.queueProcess = queueProcess;
    }

    public void setConcludedProcess(Queue<Process> concludedProcess) {
        this.concludedProcess = concludedProcess;
    }

    public void setProcessLogs(List<ProcessLog> processLogs) {
        this.processLogs = processLogs;
    }

    
    
    public Process getProcess() {
        return queueProcess.poll();
    }

    public List<ProcessLog> getProcessLogs() {
        return processLogs;
    }
    
    public ProcessLog getProcessLog(Process process) {
        
        for (ProcessLog processLog: processLogs) {
            if (processLog.getName().equals(process.getName()))
                return processLog;
        }
        
        return null;
        
    }
    
    public void returnProcessLog(ProcessLog processLog) {
        
        int index = 0;
        for (ProcessLog p: processLogs) {
            if (processLog.getName().equals(p.getName()))
                break;
            index++;
        }
        
        processLogs.set(index, processLog);
        
    }
    
    public Process firstProcess() {
        return queueProcess.peek();
    }

}
