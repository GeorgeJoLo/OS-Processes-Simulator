package Paging;

import java.util.ArrayList;

public class LRU extends ReplacementPolicy{

    public LRU(ArrayList<Integer> pageRequests) {
        super(pageRequests);
    }

    public Page removePage(int request, Page[] memoryFrames) {

        Page res = memoryFrames[0];
        int mn = res.timeAccessed;
        for (Page p : memoryFrames)
            if (p.timeAccessed < mn) {
                mn = p.timeAccessed;
                res = p;
            }

        return res;
    }
}
