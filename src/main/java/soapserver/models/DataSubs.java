package soapserver.models;

import java.util.ArrayList;
import java.util.List;

public class DataSubs {
    private List<Subscription> data;

    public DataSubs() {
        this.data = new ArrayList<>();
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
