package kinokuniya.model;

import java.util.ArrayList;
import java.util.List;

public class DataPagination {
    private int pageCount;
    private List<Subscription> data;

    public DataPagination() {
        this.pageCount = 0;
        this.data = new ArrayList<>();
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Subscription> getData() {
        return data;
    }

    public void setData(List<Subscription> data) {
        this.data = data;
    }

    public void addData(Subscription subscribe) {
        this.data.add(subscribe);
    }
}