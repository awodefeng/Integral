package com.xxun.bean;

public class GoldRevenueBean {
    private String taskName;
    private String timeStamp;
    private String getGold;

    public GoldRevenueBean(String taskName, String timeStamp, String getGold) {
        this.taskName = taskName;
        this.timeStamp = timeStamp;
        this.getGold = getGold;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getGetGold() {
        return getGold;
    }

    public void setGetGold(String getGold) {
        this.getGold = getGold;
    }
}
