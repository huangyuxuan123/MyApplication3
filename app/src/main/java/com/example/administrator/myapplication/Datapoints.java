package com.example.administrator.myapplication;
import java.util.Date;
/**
 * Created by Administrator on 2018/10/22.
 */

public class Datapoints {
    private String at;
    private String value;


    public void setValue(String value) {
        this.value = value;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getValue() {
        return value;
    }

}
