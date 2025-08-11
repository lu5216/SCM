package com.system.common.utils.api.basicConfiguration;

import com.system.common.enums.api.basicConfiguration.CompanyBusinessEnum;
import com.system.common.enums.api.basicConfiguration.CompanyStatusEnum;
import com.system.common.enums.api.basicConfiguration.CompanyTypeEnum;

import java.util.Objects;

/**
 * @author lutong
 * @data 2024-12-31 031 10:55
 */
public class CompanyApiUtil {

    public static String getCompanyBusinessReturnCode(String companyBusiness) {
        if (Objects.equals(companyBusiness, CompanyBusinessEnum.NLMY.getName())) {
            return CompanyBusinessEnum.NLMY.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.CK.getName())) {
            return CompanyBusinessEnum.CK.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.ZZ.getName())) {
            return CompanyBusinessEnum.ZZ.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.DRRS.getName())) {
            return CompanyBusinessEnum.DRRS.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.HG.getName())) {
            return CompanyBusinessEnum.HG.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JZ.getName())) {
            return CompanyBusinessEnum.JZ.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JCY.getName())) {
            return CompanyBusinessEnum.JCY.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.IT.getName())) {
            return CompanyBusinessEnum.IT.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.PL.getName())) {
            return CompanyBusinessEnum.PL.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.ZC.getName())) {
            return CompanyBusinessEnum.ZC.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JB.getName())) {
            return CompanyBusinessEnum.JB.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.FDC.getName())) {
            return CompanyBusinessEnum.FDC.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.ZS.getName())) {
            return CompanyBusinessEnum.ZS.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.KJD.getName())) {
            return CompanyBusinessEnum.KJD.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.SHG.getName())) {
            return CompanyBusinessEnum.SHG.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JQ.getName())) {
            return CompanyBusinessEnum.JQ.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JY.getName())) {
            return CompanyBusinessEnum.JY.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.WSS.getName())) {
            return CompanyBusinessEnum.WSS.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.WTY.getName())) {
            return CompanyBusinessEnum.WTY.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.ZH.getName())) {
            return CompanyBusinessEnum.ZH.getCode();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.QT.getName())) {
            return CompanyBusinessEnum.QT.getCode();
        } else {
            return companyBusiness;
        }
    }

    public static String getCompanyBusinessReturnName(String companyBusiness) {
        if (Objects.equals(companyBusiness, CompanyBusinessEnum.NLMY.getCode())) {
            return CompanyBusinessEnum.NLMY.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.CK.getCode())) {
            return CompanyBusinessEnum.CK.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.ZZ.getCode())) {
            return CompanyBusinessEnum.ZZ.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.DRRS.getCode())) {
            return CompanyBusinessEnum.DRRS.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.HG.getCode())) {
            return CompanyBusinessEnum.HG.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JZ.getCode())) {
            return CompanyBusinessEnum.JZ.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JCY.getCode())) {
            return CompanyBusinessEnum.JCY.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.IT.getCode())) {
            return CompanyBusinessEnum.IT.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.PL.getCode())) {
            return CompanyBusinessEnum.PL.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.ZC.getCode())) {
            return CompanyBusinessEnum.ZC.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JB.getCode())) {
            return CompanyBusinessEnum.JB.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.FDC.getCode())) {
            return CompanyBusinessEnum.FDC.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.ZS.getCode())) {
            return CompanyBusinessEnum.ZS.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.KJD.getCode())) {
            return CompanyBusinessEnum.KJD.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.SHG.getCode())) {
            return CompanyBusinessEnum.SHG.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JQ.getCode())) {
            return CompanyBusinessEnum.JQ.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.JY.getCode())) {
            return CompanyBusinessEnum.JY.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.WSS.getCode())) {
            return CompanyBusinessEnum.WSS.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.WTY.getCode())) {
            return CompanyBusinessEnum.WTY.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.ZH.getCode())) {
            return CompanyBusinessEnum.ZH.getName();
        } else if (Objects.equals(companyBusiness, CompanyBusinessEnum.QT.getCode())) {
            return CompanyBusinessEnum.QT.getName();
        } else {
            return companyBusiness;
        }
    }

    public static String getCompanyTypeReturnCode(String companyType) {
        if (Objects.equals(companyType, CompanyTypeEnum.GYQY.getName())) {
            return CompanyTypeEnum.GYQY.getCode();
        } else if (Objects.equals(companyType, CompanyTypeEnum.JTSYZ.getName())) {
            return CompanyTypeEnum.JTSYZ.getCode();
        } else if (Objects.equals(companyType, CompanyTypeEnum.SYQY.getName())) {
            return CompanyTypeEnum.SYQY.getCode();
        } else if (Objects.equals(companyType, CompanyTypeEnum.YXZRGS.getName())) {
            return CompanyTypeEnum.YXZRGS.getCode();
        } else if (Objects.equals(companyType, CompanyTypeEnum.GFYXGS.getName())) {
            return CompanyTypeEnum.GFYXGS.getCode();
        } else if (Objects.equals(companyType, CompanyTypeEnum.YXHHQY.getName())) {
            return CompanyTypeEnum.YXHHQY.getCode();
        } else if (Objects.equals(companyType, CompanyTypeEnum.LYQY.getName())) {
            return CompanyTypeEnum.LYQY.getCode();
        } else if (Objects.equals(companyType, CompanyTypeEnum.WSTZQY.getName())) {
            return CompanyTypeEnum.WSTZQY.getCode();
        } else if (Objects.equals(companyType, CompanyTypeEnum.GRDZQY.getName())) {
            return CompanyTypeEnum.GRDZQY.getCode();
        } else {
            return companyType;
        }
    }

    public static String getCompanyTypeReturnName(String companyType) {
        if (Objects.equals(companyType, CompanyTypeEnum.GYQY.getCode())) {
            return CompanyTypeEnum.GYQY.getName();
        } else if (Objects.equals(companyType, CompanyTypeEnum.JTSYZ.getCode())) {
            return CompanyTypeEnum.JTSYZ.getName();
        } else if (Objects.equals(companyType, CompanyTypeEnum.SYQY.getCode())) {
            return CompanyTypeEnum.SYQY.getName();
        } else if (Objects.equals(companyType, CompanyTypeEnum.YXZRGS.getCode())) {
            return CompanyTypeEnum.YXZRGS.getName();
        } else if (Objects.equals(companyType, CompanyTypeEnum.GFYXGS.getCode())) {
            return CompanyTypeEnum.GFYXGS.getName();
        } else if (Objects.equals(companyType, CompanyTypeEnum.YXHHQY.getCode())) {
            return CompanyTypeEnum.YXHHQY.getName();
        } else if (Objects.equals(companyType, CompanyTypeEnum.LYQY.getCode())) {
            return CompanyTypeEnum.LYQY.getName();
        } else if (Objects.equals(companyType, CompanyTypeEnum.WSTZQY.getCode())) {
            return CompanyTypeEnum.WSTZQY.getName();
        } else if (Objects.equals(companyType, CompanyTypeEnum.GRDZQY.getCode())) {
            return CompanyTypeEnum.GRDZQY.getName();
        } else {
            return companyType;
        }
    }

    public static String getCompanyStatusReturnCode(String status) {
        if (Objects.equals(status, CompanyStatusEnum.CX.getName())) {
            return CompanyStatusEnum.CX.getCode();
        } else if (Objects.equals(status, CompanyStatusEnum.ZY.getName())) {
            return CompanyStatusEnum.ZY.getCode();
        } else if (Objects.equals(status, CompanyStatusEnum.DX.getName())) {
            return CompanyStatusEnum.DX.getCode();
        } else if (Objects.equals(status, CompanyStatusEnum.ZX.getName())) {
            return CompanyStatusEnum.ZX.getCode();
        } else if (Objects.equals(status, CompanyStatusEnum.QR.getName())) {
            return CompanyStatusEnum.QR.getCode();
        } else if (Objects.equals(status, CompanyStatusEnum.QC.getName())) {
            return CompanyStatusEnum.QC.getCode();
        } else if (Objects.equals(status, CompanyStatusEnum.TY.getName())) {
            return CompanyStatusEnum.TY.getCode();
        } else if (Objects.equals(status, CompanyStatusEnum.QS.getName())) {
            return CompanyStatusEnum.QS.getCode();
        } else {
            return status;
        }
    }

    public static String getCompanyStatusReturnName(String status) {
        if (Objects.equals(status, CompanyStatusEnum.CX.getCode())) {
            return CompanyStatusEnum.CX.getName();
        } else if (Objects.equals(status, CompanyStatusEnum.ZY.getCode())) {
            return CompanyStatusEnum.ZY.getName();
        } else if (Objects.equals(status, CompanyStatusEnum.DX.getCode())) {
            return CompanyStatusEnum.DX.getName();
        } else if (Objects.equals(status, CompanyStatusEnum.ZX.getCode())) {
            return CompanyStatusEnum.ZX.getName();
        } else if (Objects.equals(status, CompanyStatusEnum.QR.getCode())) {
            return CompanyStatusEnum.QR.getName();
        } else if (Objects.equals(status, CompanyStatusEnum.QC.getCode())) {
            return CompanyStatusEnum.QC.getName();
        } else if (Objects.equals(status, CompanyStatusEnum.TY.getCode())) {
            return CompanyStatusEnum.TY.getName();
        } else if (Objects.equals(status, CompanyStatusEnum.QS.getCode())) {
            return CompanyStatusEnum.QS.getName();
        } else {
            return status;
        }
    }
}
