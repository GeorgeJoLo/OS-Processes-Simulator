package Scheduler;

public class RoundRobin extends Scheduler {

    private int quantum;
    
    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */
    }
    
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */
        return null;
    }
}
