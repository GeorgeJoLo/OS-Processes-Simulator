package Paging;

public class Page {

    int id;
    int timeStored;
    int timeAccessed;

    public Page(int id) {
        this.id = id;
        timeAccessed = -1;
        timeStored = -1;
    }
}
