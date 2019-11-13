package com.example.administrator.myapplication;
import java.util.List;
/**
 * Created by Administrator on 2018/10/22.
 */

public class Datastreams {
    private List<Datapoints> datapoints;
    private String id;
    public void setDatapoints(List<Datapoints> datapoints) {
        this.datapoints = datapoints;
    }
    public List<Datapoints> getDatapoints() {
        return datapoints;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }


}
