package Paging;

import java.util.ArrayList;

public abstract class ReplacementPolicy {

    protected ArrayList<Integer> pageRequests;

    public ReplacementPolicy(ArrayList<Integer> pageRequests) {
        this.pageRequests = pageRequests;
    }

    public abstract Page removePage(int request, Page[] memoryFrames);
}
