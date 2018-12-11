package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortTermScheduler {

    private String algorithm;
    private CPU cpu;
    private Memory memory;
    private int count = 0;
    
    private List<Log> logs;

    public ShortTermScheduler(String algorithm ,CPU cpu, Memory memory) {
        
        this.algorithm = algorithm;
        this.cpu = cpu;
        this.memory = memory;
        
        logs = new LinkedList<>();
        
        
        startScheduling();
    }
    


    /**
     *
     */
    private void startScheduling() {
        
        System.out.println(algorithm);
        
        int counter = 1;
        
        if (algorithm.equals("ROUNDROBIN"))
            counter = 1;
        else if (algorithm.equals("SJF"))
            counter = 2;
        else if (algorithm.equals("FCFS"))
            counter = 3;
        else if (algorithm.equals("PRIORITY"))
            counter = 4;
        
        switch(counter) {
            case 1:
                roundRobin();
                break;
            case 2:
                cpu.setSlice(Integer.MAX_VALUE);
                fjs();
                break;
            case 3:
                cpu.setSlice(Integer.MAX_VALUE);
                fcfs();
                break;
            case 4: 
                cpu.setSlice(Integer.MAX_VALUE);
                priority();
                break;
        }
    }
    
    public CPU getCpu() {
        return cpu;
    }
    
    public Log getLog(int index) {
        return logs.get(index);
    }
    
    public List getLog() {
        return logs;
    }
    
    public Memory getMemory() {
        return memory;
    }
    
    
    
    
    
    
    private void fcfs() {
        
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size() || cpu.getProcess() != null){

            memory = cpu.cycle();
            
            test();
            
            if (cpu.getCycle() > 200)
                break;
        }        
        
    }

    private void roundRobin() {
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size() || cpu.getProcess() != null){

            memory = cpu.cycle();
            
            test();
            
        }
    }

    private void fjs() {
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size() || cpu.getProcess() != null){
            System.out.println("test1: " + (cpu.getMemory().getQueueProcess().size()));
            System.out.println("test1: " + (0 != cpu.getMemory().getQueueProcess().size()));
            System.out.println("test2: " + (0 != cpu.getMemory().getInputOutputRequest().size()));
            System.out.println("test3: " + (cpu.getProcess() != null));
            
            List<Process> backup = new LinkedList<>();
            for (Process p: memory.getQueueProcess()) {
                backup.add(p);
            }
            
            Memory orderedMemory = new Memory();
            
            while (!backup.isEmpty()){
            
                Process smallerProcess = backup.get(0);
                for (Process p: backup) {
                    if (p.getTime() < smallerProcess.getTime())
                        smallerProcess = p;
                }

                backup.remove(smallerProcess);

                orderedMemory.addProcess(smallerProcess);
                
            }
            
            orderedMemory.setInputOutputRequest(memory.getInputOutputRequest());
            orderedMemory.setConcludedProcess(memory.getConcludedProcess());
            orderedMemory.setProcessLogs(memory.getProcessLogs());
            memory = orderedMemory;
            
            cpu.setMemory(memory);
            
            memory = cpu.cycle();
            
            test();
            
        }
    }

    private void priority() {
        
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size() || cpu.getProcess() != null){
            System.out.println("test1: " + (cpu.getMemory().getQueueProcess().size()));
            System.out.println("test1: " + (0 != cpu.getMemory().getQueueProcess().size()));
            System.out.println("test2: " + (0 != cpu.getMemory().getInputOutputRequest().size()));
            System.out.println("test3: " + (cpu.getProcess() != null));
            
            List<Process> backup = new LinkedList<>();
            for (Process p: memory.getQueueProcess()) {
                backup.add(p);
            }
            
            Memory orderedMemory = new Memory();
            
            while (!backup.isEmpty()){
            
                Process priorityProcess = backup.get(0);
                for (Process p: backup) {
                    if (p.getPriority() < priorityProcess.getPriority())
                        priorityProcess = p;
                }

                backup.remove(priorityProcess);

                orderedMemory.addProcess(priorityProcess);
                
            }
            
            orderedMemory.setInputOutputRequest(memory.getInputOutputRequest());
            orderedMemory.setConcludedProcess(memory.getConcludedProcess());
            orderedMemory.setProcessLogs(memory.getProcessLogs());
            memory = orderedMemory;
            
            cpu.setMemory(memory);
            
            memory = cpu.cycle();
            
            test();
            
            if (cpu.getCycle() > 200)
                break;
            
        }

    }

    
    
    public void test(){
        
        for (Process io: memory.getInputOutputRequest()) {
            System.out.println(io.getName() + " " + io.getTimeWaitIO() + " " + io.getIoTime());
            io.setTimeWaitIO(io.getTimeWaitIO() + 1);
            System.out.println(io.getTimeWaitIO());
            Log log = cpu.getLog();
            if (io.getTimeWaitIO() == io.getIoTime()) {
                io.setTimeWaitIO(0);
                memory.returnProcessIO(io);
                memory.receiveIO(io);

                log.setMemoryIOQueue(memory.getInputOutputRequest());
                log.setMemoryReadyQueue(memory.getQueueProcess());

            }
            log.setProcessWaitTimeIO(io.getTimeWaitIO());
            cpu.setLog(log);
        }
        
        Memory memory2 = new Memory();
        
        for (Process p: memory.getQueueProcess()) {
            String name = p.getName();
            int ioRequestTime = p.getIoTime();
            int priority = p.getPriority();
            int ioProcessingTime = p.getProcessingTimeIO();
            int time = p.getTime();
            String type = p.getType();
            memory2.addProcess(name, ioRequestTime, ioProcessingTime, priority, time, type);
        }

        for (Process p: memory.getInputOutputRequest()) {
            String name = p.getName();
            int ioRequestTime = p.getIoTime();
            int ioProcessingTime = p.getProcessingTimeIO();
            int priority = p.getPriority();
            int time = p.getTime();
            int timeWaitIO = p.getTimeWaitIO();
            String type = p.getType();
            memory2.addProcessIOPriority(name, ioRequestTime, ioProcessingTime, priority, time, timeWaitIO, type);
        }

        for (Process p: memory.getConcludedProcess()) {
            String name = p.getName();
            int ioRequestTime = p.getIoTime();
            int ioProcessingTime = p.getProcessingTimeIO();
            int priority = p.getPriority();
            int time = p.getTime();
            String type = p.getType();
            memory2.addProcessConcluded(name, ioRequestTime, ioProcessingTime, priority, time, type);
        }
        
//--------------------------------- Average Memory Timeout ----------------------------------
        for (Process p: memory.getQueueProcess()) {
            ProcessLog pl = memory.getProcessLog(p);
            pl.setMemoryTimeout(pl.getMemoryTimeout()+1);
            memory.returnProcessLog(pl);
        }
//-------------------------------------------------------------------------------------------

//--------------------------------- Average Turnaround Time ---------------------------------
        for (Process p: memory.getQueueProcess()) {
            ProcessLog pl = memory.getProcessLog(p);
            pl.setTurnaroundTime(pl.getTurnaroundTime()+1);
            memory.returnProcessLog(pl);
        }
        
        for (Process p: memory.getInputOutputRequest()) {
            ProcessLog pl = memory.getProcessLog(p);
            pl.setTurnaroundTime(pl.getTurnaroundTime()+1);
            memory.returnProcessLog(pl);
        }
        
//-------------------------------------------------------------------------------------------        
        
        memory2.setProcessLogs(memory.getProcessLogs());
        memory = new Memory(memory2.getInputOutputRequest(), memory2.getQueueProcess(), memory2.getConcludedProcess());
        memory.setProcessLogs(memory2.getProcessLogs());
        cpu.setMemory(memory);        
        
        logs.add(count++, cpu.getLog());
        cpu.clearLog();
    }
}
