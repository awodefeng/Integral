package com.xxun.bean;

/**
 * 表结构字段
 */

public class IntergralBean {
    private String taskName;  //任务名称
    private int moduleid;//用来标识是哪个模块用来标识是哪个模块
    //private int action; //需要多次触发事件 点赞（action=1)发布(action=2)
    private int type;  //0金币收入1.金币兑换
    private int getgold; //根据规则算出获取金币
    private String timestamp; //完成时间
    private int flag;   //0上传 1.表示未上传过  默认为1
    private String exchangeName;//兑换表盘的名称

    public IntergralBean(String taskName,int moduleid, int type, int getgold, String timestamp, int flag, String exchangeName) {
        this.taskName = taskName;
        this.moduleid = moduleid;
        this.type = type;
        this.getgold = getgold;
        this.timestamp = timestamp;
        this.flag = flag;
        this.exchangeName = exchangeName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public IntergralBean() {

    }

    public int getModuleid() {
        return moduleid;
    }

    public void setModuleid(int moduleID) {
        this.moduleid = moduleID;
    }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getGetgold() {
            return getgold;
        }

        public void setGetgold(int getgold) {
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



