import Memory.MMU;
import Scheduler.Scheduler;
import Process.Process;
import Process.ProcessState;

public class CPU {

    public static int clock = 0; // this should be incremented on every CPU cycle
    
    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private int currentProcess;

    private Process[] newProcesses; // Processes that are not inserted into RAM yet
    // Set the bounds of the processes in newProcesses and are not inserted into RAM yet
    private int newProcessesStartIndex;
    private int newProcessesEndIndex;
    
    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
    }
    
    public void run() {
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */

        // Initialize the newProcesses and its indexes
        newProcesses = new Process[processes.length];
        newProcessesStartIndex = 0;
        newProcessesEndIndex = 0;

        // Set the ID of the current (previously executed) process
        currentProcess = 0;

        // Run tick until there are no processes left to run or fit into RAM
        // TODO : TERMINATE a process that doesn't fit in RAM (inside MMU)
        while (!this.allProcessesTerminated()) {
            tick();
            clock++;
        }

        // Print the statistics for each process
        for (Process process : processes) {
            if (process.getPCB().getStartTimes().isEmpty()) {
                System.out.println("Process: " + process.getPCB().getPid() + " causes memory overflow.");
                continue;
            }

            System.out.println("Process: " + process.getPCB().getPid());

            System.out.println("\tResponse Time: " + process.getResponseTime());

            System.out.println("\tWaiting Time: " + process.getWaitingTime());

            System.out.println("\tTurn around Time: " + process.getTurnAroundTime());

        }
    }
    
    public void tick() {
        /* TODO: you need to add some code here
         * Hint: this method should run once for every CPU cycle */

        System.out.println("Clock: " + clock);

        // Check which processes arrive at the current clock time
        for (Process process : processes) {
            if (process.getArrivalTime() == clock) {
                newProcesses[newProcessesEndIndex] = process;
                newProcessesEndIndex++;
            }
        }

        if (newProcessesEndIndex == 0) {
            System.out.println("NOOP");
            System.out.println("===========================================");
            return;
        }

        // Get the previously run process and the new process to run
        Process previousProcess = this.findProcessById(currentProcess);

        // If its the first process
        if (previousProcess != null) {
            // If the process is fully executed
            if (previousProcess.getBurstTime() == 0) {
                previousProcess.getPCB().setState(ProcessState.TERMINATED, clock);
                scheduler.removeProcess(previousProcess);
            }
        }

        if (this.allProcessesTerminated()){
            System.out.println("NOOP");
            System.out.println();
            return;
        }

        // Check if there are NEW processes (arrived now / could not fit into RAM previously)
        if (newProcessesStartIndex != newProcessesEndIndex) {
            // Pick the first NEW process that fits into RAM
            for (int i = newProcessesStartIndex; i < newProcessesEndIndex; i++) {
                if (mmu.loadProcessIntoRAM(newProcesses[i])) {
                    // Process State is now READY to run
                    newProcesses[i].getPCB().setState(ProcessState.READY, clock);
                    newProcesses[i].setStartWaitingTime(clock);
                    scheduler.addProcess(newProcesses[i]);
                    newProcessesStartIndex++;
                    break;
                }
            }
        }

        Process newProcess = scheduler.getNextProcess();

        // If its the first process
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

    // Check if there are still processes left to RUN
    private boolean allProcessesTerminated() {
       for (Process process : processes) {
           if (process.getPCB().getState() != ProcessState.TERMINATED)
               return false;
       }

       return true;
    }

    private Process findProcessById(int id) {
        for (Process process : processes) {
            if (process.getPCB().getPid() == id) {
                return process;
            }
        }

        return null;
    }
}
