package Scheduler;

import Process.Process;
import Process.ProcessState;

public class RoundRobin extends Scheduler {

    private int quantum;
    private int quantumExecutionIndex;

    private Process runningProcess;
    
    public RoundRobin() {
        this.quantum = 1; // default quantum
        /* TODO: you _may_ need to add some code here */

        // Used to indicate the quantum progress for the current executing process
        quantumExecutionIndex = 0;
    }
    
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */

        // Add the process to the end of the queue
        if (quantumExecutionIndex == 0) {
            runningProcess = p;
        }

        this.processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here
         * and change the return value */

        // If there are no processes in the scheduler return null
        if (this.processes.isEmpty())
            return null;

        // If the quantum time of the current process has ended
        if (quantumExecutionIndex == quantum && runningProcess.getPCB().getState() != ProcessState.TERMINATED) {
            // Move the last executed process from the front to the back of the queue
            Process currentExecutingProcess = this.processes.get(0);
            this.processes.remove(currentExecutingProcess);
            this.processes.add(currentExecutingProcess);

            quantumExecutionIndex = 1;
            runningProcess = this.processes.get(0);
        } else if (quantumExecutionIndex == quantum && runningProcess.getPCB().getState() == ProcessState.TERMINATED) {
            // If the quantum of a process has ended and the process has TERMINATED
            quantumExecutionIndex = 1;
            runningProcess = this.processes.get(0);
        } else if (quantumExecutionIndex < quantum && this.processes.get(0) == runningProcess) {
            // Increase the process quantum of the current process
            quantumExecutionIndex++;
        } else {
            quantumExecutionIndex = 1;
            runningProcess = this.processes.get(0);
        }

        // Return the process at the front of the queue
        return this.processes.get(0);
    }
}
