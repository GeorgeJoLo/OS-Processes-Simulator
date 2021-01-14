package Scheduler;

import Process.Process;
import java.util.ArrayList;

public abstract class Scheduler {

    protected ArrayList<Process> processes; // the list of processes to be executed
    
    public Scheduler() {
        this.processes = new ArrayList<Process>();
    }

    /* the addProcess() method should add a new process to the list of
     * processes that are candidates for execution. This will probably
     * differ for different schedulers */
    public abstract void addProcess(Process p);
    
    /* the removeProcess() method should remove a process from the list
     * of processes that are candidates for execution. Common for all
     * schedulers. */
    public void removeProcess(Process p) {
        /* TODO: you need to add some code here */

        // Remove the process from the queue
        this.processes.remove(p);
    }
    
    /* the getNextProcess() method should return the process that should
     * be executed next by the CPU.CPU */
    public abstract Process getNextProcess();
}
