package com.system.common.utils.api.basicConfiguration;

import com.system.common.enums.api.basicConfiguration.VendorTypeEnum;

import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-31 031 10:17
 */

public class VendorApiUtil {
    public static String getVendorTypeReturnCode(String vendorType) {
        if (Objects.equals(vendorType, VendorTypeEnum.YL.getName())) {
            return VendorTypeEnum.YL.getCode();
        } else if (Objects.equals(vendorType, VendorTypeEnum.WL.getName())) {
            return VendorTypeEnum.WL.getCode();
        } else {
            return vendorType;
        }
    }

    public static String getVendorTypeReturnName(String vendorType) {
        if (Objects.equals(vendorType, VendorTypeEnum.YL.getCode())) {
            return VendorTypeEnum.YL.getName();
        } else if (Objects.equals(vendorType, VendorTypeEnum.WL.getCode())) {
            return VendorTypeEnum.WL.getName();
        } else {
            return vendorType;
        }
    }
}
