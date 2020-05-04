package com.ph.lease.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PengHao
 */
@Data
public class SessionSave {

    private static Map<String, String> sessionMap = new HashMap<String, String>(5);

    public static Map<String, String> getSessionMap() {
        return sessionMap;
    }

    public static void setSessionMap(Map<String, String> sessionMap) {
        SessionSave.sessionMap = sessionMap;
    }

}
