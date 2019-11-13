package com.example.administrator.myapplication;
import java.util.List;

/**
 * Created by Administrator on 2018/10/22.
 */

public class Data {
    private int count;
    private List<Datastreams> datastreams;
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setDatastreams(List<Datastreams> datastreams) {
        this.datastreams = datastreams;
    }
    public List<Datastreams> getDatastreams() {
        return datastreams;
    }


}
