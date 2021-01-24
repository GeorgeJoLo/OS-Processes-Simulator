package Paging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MMU {

    private ArrayList<Page> pageRequests;
    Page[] memoryFrames;
    ReplacementPolicy algorithm;

    public MMU(ReplacementPolicy algorithm, ArrayList<Integer> pageRequests, int memorySize) {
        this.algorithm = algorithm;

        this.pageRequests = new ArrayList<>();

        for (int id : pageRequests){
            boolean added = false;

            for (Page page : this.pageRequests) {
                if (page.id == id) {
                    this.pageRequests.add(page);
                    added = true;
                    break;
                }
            }

            if (!added)
                this.pageRequests.add(new Page(id));
        }

        this.memoryFrames = new Page[memorySize];
        Arrays.fill(memoryFrames, null);
    }

    public void handleRequests() {
        int count = 0;

        // For every page, search in memory
        for (int clock = 0; clock < pageRequests.size(); clock++) {

            System.out.println("Clock: " + clock);

            Page page = pageRequests.get(clock);
            //if page already in RAM
            if (hit(page)) {
                System.out.println(page.id + "-Hit!");
                System.out.println("===============");
                page.timeAccessed = clock;
                continue;
            }
            //if page not in RAM, fetch in from Dick
            else {
                System.out.print(page.id + "-Miss ---> ");
                count++;

                // if there is empty space in RAM, just put it there
                if (emptyFrame() != -1) {
                    memoryFrames[emptyFrame()] = page;
                    System.out.println("No replacement!");
                }
                // if RAM is full, replace a page
                else {
                    Page removalPage = algorithm.removePage(clock, memoryFrames);
                    memoryFrames[pageIndex(removalPage)] = page;  //replace
                    System.out.println(removalPage.id + "  (" + count + ")");
                }
                page.timeAccessed = clock;
                page.timeStored = clock;
            }

            System.out.println("===============");
        }

        System.out.println("Page Faults: " + count);
    }

    private boolean hit(Page page) {
        for (Page p : memoryFrames)
            if (p != null && p.id == page.id) return true;
        return false;
    }

    private int pageIndex(Page page) {
        for (int i = 0; i < memoryFrames.length; i++)
            if (memoryFrames[i].id == page.id) return i;
        return -1;
    }

    private int emptyFrame() {
        for (int i = 0; i < memoryFrames.length; i++)
            if (memoryFrames[i] == null) return i;
        return -1;
    }
}
