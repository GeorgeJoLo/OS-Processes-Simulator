package Process;

public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;

    private int startWaitingTime = -1; // the time process loaded into RAM
    
    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
    }
    
    public ProcessControlBlock getPCB() {
        return this.pcb;
    }
   
    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */

        // Every time the process runs, reduce its burst time by 1
        this.burstTime -= 1;
    }

    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
    }

    public double getWaitingTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        // Calculate the waiting time of a process
        double sum = this.pcb.getStartTimes().get(0) - this.startWaitingTime;

        for (int i = 1; i < this.pcb.getStartTimes().size(); i++) {
            sum += this.pcb.getStartTimes().get(i) - this.pcb.getStopTimes().get(i - 1);
        }

        return sum;
    }
    
    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        // Return the response time of a process
        return this.pcb.getStartTimes().get(0) - this.arrivalTime;
    }
    
    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        // Return the turn around time of a process
        return this.pcb.getStopTimes().get(pcb.getStopTimes().size() - 1) - this.arrivalTime;
    }

    public int getMemoryRequirements() {
        return memoryRequirements;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }


    public void setStartWaitingTime(int startWaitingTime) {
        this.startWaitingTime = startWaitingTime;
    }
}
