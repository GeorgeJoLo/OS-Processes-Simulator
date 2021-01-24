package CPU;

import Memory.MMU;
import Scheduler.Scheduler;
import Process.Process;
import Process.ProcessState;

import java.util.ArrayList;

public class CPU {

    public static int clock = 0; // this should be incremented on every CPU.CPU cycle
    
    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private int currentProcess;

    private ArrayList<Process> processesToLoad = new ArrayList<>(); // stores the process have arrived and not loaded
    
    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
    }
    
    public void run() {
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */

        // Set the ID of the current (previously executed) process
        currentProcess = 0; // processes IDs start from 1

        // Run tick until there are no processes left to run or fit into RAM
        while (!this.allProcessesTerminated()) {
            tick();
            clock++;
        }

        int totalResponseTime = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        // Print the statistics for each process
        for (Process process : processes) {
            if (process.getPCB().getStartTimes().isEmpty()) {
                System.out.println("Process: " + process.getPCB().getPid() + " causes memory overflow.");
                continue;
            }

            System.out.println("Process: " + process.getPCB().getPid());

            System.out.println("\tResponse Time: " + process.getResponseTime());
            totalResponseTime += process.getResponseTime();

            System.out.println("\tWaiting Time: " + process.getWaitingTime());
            totalWaitingTime += process.getWaitingTime();

            System.out.println("\tTurn around Time: " + process.getTurnAroundTime());
            totalTurnAroundTime += process.getTurnAroundTime();

        }

        System.out.println();
        System.out.println("===========================================");
        System.out.println();

        System.out.println("Average Response Time: " + (totalResponseTime / (float) processes.length));
        System.out.println("Average Waiting Time: " + (totalWaitingTime / (float) processes.length));
        System.out.println("Average Turn around Time: " + (totalTurnAroundTime / (float) processes.length));

        // Reset clock at the end of the cpu run so tests can run properly
        clock = 0;
    }
    
    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU.CPU cycle */

        System.out.println("Clock: " + clock);

        // Check which processes arrive at the current clock time
        for (Process process : processes)
            if (process.getArrivalTime() == clock)
                processesToLoad.add(process);

        // Get the previously run process
        Process previousProcess = this.findProcessById(currentProcess);

        // If previousProcess has terminated
        if (previousProcess != null && previousProcess.getBurstTime() == 0) {
            previousProcess.getPCB().setState(ProcessState.TERMINATED, clock);
            scheduler.removeProcess(previousProcess);
        }

        // Check if all processes are TERMINATED
        if (this.allProcessesTerminated()){
            System.out.println("NOOP\n");
            return;
        }

        // Check if there are NEW processes (arrived now / could not fit into RAM previously)
        // Load the first process from the waiting list
        for (int i = 0; i < processesToLoad.size(); i++) {
            Process p = processesToLoad.get(i);
            if (mmu.loadProcessIntoRAM(p)) {
                // Process State is now READY to run
                p.getPCB().setState(ProcessState.READY, clock);
                p.setStartWaitingTime(clock);
                scheduler.addProcess(p);

                // Remove the loaded process form the waiting list
                processesToLoad.remove(p);
                // If one process should be loaded into RAM per tick `i--;` changes to `break;`
                i--; // break;
            }
        }

        // Get the next process to run from scheduler
        Process newProcess = scheduler.getNextProcess();

        // If there is no new process to run skip the clock cycle
        if (newProcess == null) {
            System.out.println("NOOP");
            System.out.println("===========================================");
            return;
        }

        // Set the previousProcess state = READY
        if (
                previousProcess != null
                && newProcess != previousProcess
                && previousProcess.getPCB().getState() != ProcessState.TERMINATED
        ) {
            previousProcess.getPCB().setState(ProcessState.READY, clock);
            previousProcess.waitInBackground();
        }

        System.out.println("Running Process ID: " + newProcess.getPCB().getPid());
        System.out.println("===========================================");

        // Run the next process
        newProcess.getPCB().setState(ProcessState.RUNNING, clock);
        newProcess.run();
        currentProcess = newProcess.getPCB().getPid();
    }

    /**
     * Check if there are still processes left to RUN
     */
    private boolean allProcessesTerminated() {
       for (Process process : processes)
           if (process.getPCB().getState() != ProcessState.TERMINATED)
               return false;
       return true;
    }

    private Process findProcessById(int id) {
        for (Process process : processes)
            if (process.getPCB().getPid() == id)
                return process;
        return null;
    }
}
