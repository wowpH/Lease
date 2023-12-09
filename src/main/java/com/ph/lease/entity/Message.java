package com.ph.lease.entity;

/**
 * @author PengHao
 */
public class Message {
    /**
     * 静态常量用于后端
     */
    public static final int SF_SUCCESS = 100;
    public static final int SF_FAILURE = 300;
    /**
     * 非静态常量用于前端，因为静态常量无法传到前端
     */
    private final int SUCCESS = SF_SUCCESS;
    private final int FAILURE = SF_FAILURE;
    private final int ADMIN = 100;
    private final int CUSTOMER = 200;
    private final int ERROR = 300;
    private int msgCode;
    private String msg;
    
    public Message() {
        this(SF_FAILURE);
    }
    
    public Message(int msgCode) {
        this(msgCode, "未知原因");
    }
    
    public Message(int msgCode, String msg) {
        this.msgCode = msgCode;
        this.msg = msg;
    }
    
    public int getSUCCESS() {
        return SUCCESS;
    }
    
    public int getFAILURE() {
        return FAILURE;
    }
    
    public int getADMIN() {
        return ADMIN;
    }
    
    public int getCUSTOMER() {
        return CUSTOMER;
    }
    
    public int getERROR() {
        return ERROR;
    }
    
    public int getMsgCode() {
        return msgCode;
    }
    
    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
}