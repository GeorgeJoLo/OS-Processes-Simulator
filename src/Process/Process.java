package Process;

public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    
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

        this.burstTime -= 1;
    }
    
    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
    }

    public double getWaitingTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        double sum = this.getResponseTime();

        for (int i = 1; i < this.pcb.getStartTimes().size(); i++) {
            sum += this.pcb.getStartTimes().get(i) - this.pcb.getStopTimes().get(i - 1);
        }

        return sum;
    }
    
    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        return this.pcb.getStartTimes().get(0) - this.arrivalTime;
    }
    
    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        return this.pcb.getStopTimes().get(pcb.getStopTimes().size() - 1) - this.arrivalTime;
    }

    public int getMemoryRequirements() {
        return memoryRequirements;
    }
}
