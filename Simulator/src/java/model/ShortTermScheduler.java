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

    /**
     *
     */
    private void roundRobin() {
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size() || cpu.getProcess() != null){

            memory = cpu.cycle();
            
            System.out.println("\n$$$ REQADY QUEUE OF PROCESSES");
            for (Process p: memory.getQueueProcess()) {
                System.err.println("Process " + p.getName());
            }
            
            System.err.println("$$$ IO PROCESS QUEUE");
            for (Process p: memory.getInputOutputRequest()) {
                System.err.println("Process " + p.getName());
            }
            
            for (Process io: memory.getInputOutputRequest()) {
                io.setTimeWaitIO(io.getTimeWaitIO() + 1);
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
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                String type = p.getType();
                memory2.addProcess(name, ioRequestTime, ioProcessingTime, time, type);
            }
            
            for (Process p: memory.getInputOutputRequest()) {
                String name = p.getName();
                int ioRequestTime = p.getIoTime();
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                int timeWaitIO = p.getTimeWaitIO();
                String type = p.getType();
                memory2.addProcessIO(name, ioRequestTime, ioProcessingTime, time, timeWaitIO, type);
            }
            
            for (Process p: memory.getConcludedProcess()) {
                String name = p.getName();
                int ioRequestTime = p.getIoTime();
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                String type = p.getType();
                memory2.addProcessConcluded(name, ioRequestTime, ioProcessingTime, time, type);
            }
            
            memory = new Memory(memory2.getInputOutputRequest(), memory2.getQueueProcess(), memory2.getConcludedProcess());
            
            cpu.setMemory(memory);
            
//            Queue<Process> concludQueue = cpu.getMemory().getConcludedProcess();
            
            logs.add(count++, cpu.getLog());
            cpu.clearLog();
            
//            Log log = cpu.getLog();
//            log.setMemoryConcludedQueue(concludQueue);
//            cpu.setLog(log);
        }
    }

    /**
     *
     */
    private void fjs() {
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size() || cpu.getProcess() != null){

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
            
            memory = orderedMemory;
            
            cpu.setMemory(memory);
            
            memory = cpu.cycle();
            
            System.out.println("\n$$$ REQADY QUEUE OF PROCESSES");
            for (Process p: memory.getQueueProcess()) {
                System.err.println("Process " + p.getName());
            }
            
            System.err.println("$$$ IO PROCESS QUEUE");
            for (Process p: memory.getInputOutputRequest()) {
                System.err.println("Process " + p.getName());
            }
            
            for (Process io: memory.getInputOutputRequest()) {
                io.setTimeWaitIO(io.getTimeWaitIO() + 1);
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
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                String type = p.getType();
                memory2.addProcess(name, ioRequestTime, ioProcessingTime, time, type);
            }
            
            for (Process p: memory.getInputOutputRequest()) {
                String name = p.getName();
                int ioRequestTime = p.getIoTime();
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                int timeWaitIO = p.getTimeWaitIO();
                String type = p.getType();
                memory2.addProcessIO(name, ioRequestTime, ioProcessingTime, time, timeWaitIO, type);
            }
            
            for (Process p: memory.getConcludedProcess()) {
                String name = p.getName();
                int ioRequestTime = p.getIoTime();
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                String type = p.getType();
                memory2.addProcessConcluded(name, ioRequestTime, ioProcessingTime, time, type);
            }
            
            memory = new Memory(memory2.getInputOutputRequest(), memory2.getQueueProcess(), memory2.getConcludedProcess());
            
            cpu.setMemory(memory);
                        
            logs.add(count++, cpu.getLog());
            cpu.clearLog();
            
        }
    }

    /**
     *
     */
    private void priority() {
        
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size() || cpu.getProcess() != null){

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
            
            memory = orderedMemory;
            
            cpu.setMemory(memory);
            
            memory = cpu.cycle();
            
            System.out.println("\n$$$ REQADY QUEUE OF PROCESSES");
            for (Process p: memory.getQueueProcess()) {
                System.err.println("Process " + p.getName());
            }
            
            System.err.println("$$$ IO PROCESS QUEUE");
            for (Process p: memory.getInputOutputRequest()) {
                System.err.println("Process " + p.getName());
            }
            
            for (Process io: memory.getInputOutputRequest()) {
                io.setTimeWaitIO(io.getTimeWaitIO() + 1);
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
                int ioProcessingTime = p.getProcessingTimeIO();
                int priority = p.getPriority();
                int time = p.getTime();
                String type = p.getType();
                memory2.addProcessPriority(name, ioRequestTime, ioProcessingTime, priority, time, type);
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
                memory2.addProcessConcludedPriority(name, ioRequestTime, ioProcessingTime, priority, time, type);
            }
            
            memory = new Memory(memory2.getInputOutputRequest(), memory2.getQueueProcess(), memory2.getConcludedProcess());
            
            cpu.setMemory(memory);
            
//            Queue<Process> concludQueue = cpu.getMemory().getConcludedProcess();
            
            logs.add(count++, cpu.getLog());
            cpu.clearLog();
            
//            Log log = cpu.getLog();
//            log.setMemoryConcludedQueue(concludQueue);
//            cpu.setLog(log);
        }

    }

    /**
     *
     */
    private void fcfs() {
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size() || cpu.getProcess() != null){

            memory = cpu.cycle();
            
            System.out.println("\n$$$ REQADY QUEUE OF PROCESSES");
            for (Process p: memory.getQueueProcess()) {
                System.err.println("Process " + p.getName());
            }
            
            System.err.println("$$$ IO PROCESS QUEUE");
            for (Process p: memory.getInputOutputRequest()) {
                System.err.println("Process " + p.getName());
            }
            
            for (Process io: memory.getInputOutputRequest()) {
                io.setTimeWaitIO(io.getTimeWaitIO() + 1);
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
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                String type = p.getType();
                memory2.addProcess(name, ioRequestTime, ioProcessingTime, time, type);
            }
            
            for (Process p: memory.getInputOutputRequest()) {
                String name = p.getName();
                int ioRequestTime = p.getIoTime();
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                int timeWaitIO = p.getTimeWaitIO();
                String type = p.getType();
                memory2.addProcessIO(name, ioRequestTime, ioProcessingTime, time, timeWaitIO, type);
            }
            
            for (Process p: memory.getConcludedProcess()) {
                String name = p.getName();
                int ioRequestTime = p.getIoTime();
                int ioProcessingTime = p.getProcessingTimeIO();
                int time = p.getTime();
                String type = p.getType();
                memory2.addProcessConcluded(name, ioRequestTime, ioProcessingTime, time, type);
            }
            
            memory = new Memory(memory2.getInputOutputRequest(), memory2.getQueueProcess(), memory2.getConcludedProcess());
            
            cpu.setMemory(memory);
            
//            Queue<Process> concludQueue = cpu.getMemory().getConcludedProcess();
            
            logs.add(count++, cpu.getLog());
            cpu.clearLog();
            
//            Log log = cpu.getLog();
//            log.setMemoryConcludedQueue(concludQueue);
//            cpu.setLog(log);
        }
    }
}
