package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018/11/20.
 */


import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class Exitactivity extends Application {


    private List<Activity> activityList=new LinkedList<Activity>();

    private static Exitactivity instance;

    private Exitactivity()
    {
    }
    //单例模式中获取唯一的ExitApplication 实例
    public static Exitactivity getInstance()
    {
        if(null == instance)
        {
            instance = new Exitactivity();
        }
        return instance;

    }
    //添加Activity 到容器中
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    //遍历所有Activity 并finish

    public void exit()
    {

        for(Activity activity:activityList)
        {
            activity.finish();
        }

        System.exit(0);

    }
}
