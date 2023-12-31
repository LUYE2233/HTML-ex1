package org.kukuking.entity;

public class Message {
    private int publisherID;

    private int messageID;//所有的messageID都是唯一的正整数

    private int parentID;//此处为回复的message的id，如果这个帖子是顶楼，那本值为-1

    private long changeTime;//时间戳，根据推送者修改时间系统自动生成

    private int support;//点赞数

    private String messageText;

    public Message(int publisherID, int messageID, int parentID, String messageText) {
        this.publisherID = publisherID;
        this.messageID = messageID;
        this.parentID = parentID;
        this.messageText = messageText;
        this.changeTime = System.currentTimeMillis();
        this.support = 0;
    }

    public Message(){
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }
}
