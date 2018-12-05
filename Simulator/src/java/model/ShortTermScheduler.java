package model;

public class ShortTermScheduler {

    private int algorithm;
    private CPU cpu;
    private Memory memory;

    public ShortTermScheduler(int algorithm ,CPU cpu, Memory memory) {
        
        this.algorithm = algorithm;
        this.cpu = cpu;
        this.memory = memory;
        
        startScheduling();
    }

    /**
     *
     */
    private void startScheduling() {
        switch(algorithm) {
            case 1:
                fcfs();
                break;
        }
    }

    /**
     *
     */
    private void fcfs() {
        while (0 != cpu.getMemory().getQueueProcess().size() || 0 != cpu.getMemory().getInputOutputRequest().size()){
            //memory = cpu.startProcess();
            memory = cpu.cycle();
            
            System.err.println("\n$$$ REQADY QUEUE OF PROCESSES");
            for (Process p: memory.getQueueProcess()) {
                System.err.println("Process " + p.getName());
            }
            
            System.err.println("$$$ IO PROCESS QUEUE");
            for (Process p: memory.getInputOutputRequest()) {
                System.err.println("Process " + p.getName());
            }
            
            for (Process io: memory.getInputOutputRequest()) {
                io.setTimeWaitIO(io.getTimeWaitIO() + 1);
                if (io.getTimeWaitIO() == io.getIoTime()) {
                    io.setTimeWaitIO(0);
                    memory.returnProcessIO(io);
                    memory.receiveIO(io);
                }
            }
            
            cpu.setMemory(memory);
            
        }
    }

    /**
     *
     */
    private void fjs() {

    }

    /**
     *
     */
    private void prioridade() {

    }

    /**
     *
     */
    private void roundRobin() {

    }

}
