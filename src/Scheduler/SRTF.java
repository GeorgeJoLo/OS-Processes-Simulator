package Scheduler;

import Process.Process;

import java.util.Arrays;

public class SRTF extends Scheduler {

    public SRTF() {}

    public void addProcess(Process p) {
        /* TODO: you need to add some code here */
        this.processes.add(p);
    }

    public Process getNextProcess() {
        /* TODO: you need to add some code here*/

        if(this.processes.isEmpty()){
            return null;
        }

        sortProcessesByBurstTime();

        return this.processes.get(0);
    }

    private void sortProcessesByBurstTime() {
        Process[] temp = new Process[this.processes.size()];
        temp = this.processes.toArray(temp);

        // Simple BubbleSort :))))))))))))))))))))))))))
        for (int i = this.processes.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (temp[j].getBurstTime() > temp[j+1].getBurstTime()) {
                    Process tmp = temp[j];
                    temp[j] = temp[j+1];
                    temp[j+1] = tmp;
                }
            }
        }

        for (int i = 0; i < this.processes.size(); i++) {
            this.processes.set(i, temp[i]);
        }
    }
}
