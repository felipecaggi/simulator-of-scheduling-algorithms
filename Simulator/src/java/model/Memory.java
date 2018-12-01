package model;

import java.util.Deque;
import java.util.Collection;

public class Memory {

    private Deque processesReady;

    private Deque inputOutputRequest;

    private Deque timeSliceExpired;

    private Collection<Process> process;

    private CPU cPU;

    private ShortTermScheduler shortTermScheduler;

    public Memory() {
    }

    /**
     *
     */
    public void addProcess(String name, String type, int priority, int time) {

    }

}
