package com.system.common.vo;

import com.system.common.enums.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  统一输出格式
 * @author lutong
 * @data 2024-11-27 027 15:46
 * {
 *    msg: "查询成功",
 *    ok: true,
 *    status: 200,
 *    data: dataJson
 * }
 */

@ApiModel(value="响应对象", description="返回值")
public class Result<T> {
    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String msg;
    /**
     * 请求成功(true)/失败(false)
     */
    @ApiModelProperty(value = "请求成功(true)/失败(false)")
    private Boolean ok;
    /**
     * 响应状态
     */
    @ApiModelProperty(value = "响应状态")
    private Integer status;
    /**
     * 数据集
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Result() {};

    public Result(String msg, Boolean ok) {
        this.msg = msg;
        this.ok = ok;
    }

    public Result(String msg, Boolean ok, T data) {
        this.msg = msg;
        this.ok = ok;
        this.data = data;
    }

    public Result(String msg, Boolean ok, Integer status, T data) {
        this.msg = msg;
        this.ok = ok;
        this.status = status;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Boolean getOk() {
        return ok;
    }
    public void setOk(Boolean ok) {
        this.ok = ok;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return apiResult(data, ResultEnum.SUCCESS.getCode(), "", true);
    }

    public static <T> Result<T> success(String msg, T data) {
        return apiResult(data, ResultEnum.SUCCESS.getCode(), msg, true);
    }

    public static <T> Result<T> success(String msg) {
        return apiResult(null, ResultEnum.SUCCESS.getCode(), msg, true);
    }

    public static <T> Result<T> failed(String msg) {
        return apiResult(null, ResultEnum.FAILED.getCode(), msg, false);
    }

    public static <T> Result<T> failed(String msg, T data) {
        return apiResult(data, ResultEnum.FAILED.getCode(), msg, false);
    }

    public static <T> Result<T> failed(String msg, T data, Integer status) {
        return apiResult(data, status, msg, false);
    }

    private static <T> Result<T> apiResult(T data, Integer status, String msg, Boolean ok) {
        Result<T> result = new Result<>();
        result.setStatus(status);
        result.setData(data);
        result.setMsg(msg);
        result.setOk(ok);
        return result;
    }
}
