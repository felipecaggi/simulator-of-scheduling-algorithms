package model;

public class CPU {

    private int cycle;

    private int slice;

    private String state;

    private int contextSwitch;

    private ShortTermScheduler shortTermScheduler;

    private Memory memory;

    public CPU(int slice, int contextSwitch) {
        this.slice = slice;
        this.contextSwitch = contextSwitch;
    }

    /**
     *
     */
    public void setStatus(String status) {

    }

    /**
     *
     */
    public String getStatus() {
        return null;
    }

    /**
     *
     */
    public int getCycle() {
        return 0;
    }

    /**
     *
     */
    public void startProcess(Process process) {

    }

    /**
     *
     */
    public Process stopProcess() {
        return null;
    }

    /**
     *
     */
    public Process concludeProcess() {
        return null;
    }

}
