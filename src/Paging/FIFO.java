package Paging;

import java.util.ArrayList;

public class FIFO extends ReplacementPolicy{

    public FIFO(ArrayList<Integer> pageRequests) {
        super(pageRequests);
    }

    public Page removePage(int request, Page[] memoryFrames) {

        Page res = memoryFrames[0];
        int mn = res.timeStored;
        for (Page p : memoryFrames)
            if (p.timeStored < mn) {
                mn = p.timeStored;
                res = p;
            }

        return res;
    }
}
