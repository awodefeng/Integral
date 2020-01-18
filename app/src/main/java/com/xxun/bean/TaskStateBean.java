package com.xxun.bean;

public class TaskStateBean {
    private String taskName;  //任务名称
    private String moduleid;//用来标识是哪个模块用来标识是哪个模块
    private int action; //需要多次触发事件 点赞（action=1)发布(action=2)
    private int type;  //0金币收入1.金币兑换
    private String getgold; //根据规则算出获取金币
    private String timestamp; //完成时间
    private int flag;   //0上传 1.表示未上传过  默认为1




    public TaskStateBean(String taskName) {
        this.taskName = taskName;
    }

    public TaskStateBean(String taskName, String moduleid, int action, int type, String getgold, String timestamp, int flag) {
        this.taskName = taskName;
        this.moduleid = moduleid;
        this.action = action;
        this.type = type;
        this.getgold = getgold;
        this.timestamp = timestamp;
        this.flag = flag;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGetgold() {
        return getgold;
    }

    public void setGetgold(String getgold) {
        this.getgold = getgold;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
