package Scheduler;

import Process.Process;

public class SJF extends Scheduler {

    private int reset = 0;
    Process runningProcess;

    public SJF() {
        /* TODO: you _may_ need to add some code here */
        runningProcess = null;
    }

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        this.processes.add(p);
    }
    
    public Process getNextProcess() {
        /* TODO: you need to add some code here*/

        if(this.processes.isEmpty()){
            return null;
        }

        completionTimeBasedSorting();

        if(runningProcess == null || runningProcess.getBurstTime() == 0){
            runningProcess = this.processes.get(0);
        }

        return runningProcess;

    }

    private void completionTimeBasedSorting() {
        Process[] temp = new Process[this.processes.size()];
        temp = this.processes.toArray(temp);
        for (int i = 1; i < this.processes.size(); i++) {
            for (int j = 1; j < this.processes.size() - i; j++) {
                if (temp[j].getBurstTime() < temp[j-1].getBurstTime()) {
                    Process tmp = temp[j];
                    temp[j] = temp[j-1];
                    temp[j-1] = tmp;
                }
            }
        }
        this.processes.clear();
        for (Process process : temp) {
            this.addProcess(process);
        }
    }

}