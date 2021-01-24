package Paging;

import java.util.ArrayList;

public class Optimal extends ReplacementPolicy{

    public Optimal(ArrayList<Integer> pageRequests) {
        super(pageRequests);
    }

    public Page removePage(int request, Page[] memoryFrames) {

        Page res = memoryFrames[0];
        int mx = nextRequest(res, request);
        for (Page p : memoryFrames)
            if (nextRequest(p, request) > mx) {
                mx = nextRequest(p, request);
                res = p;
            }

        return res;
    }

    private int nextRequest(Page page, int clock) {
        for (int i = clock+1; i < pageRequests.size(); i++)
            if (pageRequests.get(i) == page.id)
                return i;

        return 100000;
    }
}
