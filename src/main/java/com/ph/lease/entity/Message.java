package com.ph.lease.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author PengHao
 */
@Data
@AllArgsConstructor
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

}
