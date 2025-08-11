package com.system.common.utils.api.basicConfiguration;

import com.system.common.enums.api.basicConfiguration.ClientTypeMenu;

import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-31 031 10:27
 */
public class ClientApiUtil {
    public static String getClientTypeReturnCode(String clientType) {
        if (Objects.equals(clientType, ClientTypeMenu.PT.getName())) {
            return ClientTypeMenu.PT.getCode();
        } else if (Objects.equals(clientType, ClientTypeMenu.GJ.getName())) {
            return ClientTypeMenu.GJ.getCode();
        } else if (clientType == null){
            return null;
        } else {
            return clientType;
        }
    }

    public static String getClientTypeReturnName(String clientType) {
        if (Objects.equals(clientType, ClientTypeMenu.PT.getCode())) {
            return ClientTypeMenu.PT.getName();
        } else if (Objects.equals(clientType, ClientTypeMenu.GJ.getCode())) {
            return ClientTypeMenu.GJ.getName();
        } else if (clientType == null){
            return null;
        } else {
            return clientType;
        }
    }
}
