package Scheduler;

import Process.Process;

public class FCFS extends Scheduler {

    Process runningProcess;

    public FCFS() {
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

        //First came, first served

        if(runningProcess == null || runningProcess.getBurstTime() == 0){
            runningProcess = this.processes.get(0);
        }
        return runningProcess;

    }
}


