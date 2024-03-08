package com.oldking.mall.util;

import java.io.Serializable;

/**
 * @author wangzhengxiang
 */
public class RespResult<T> implements Serializable {
    //响应数据
    private T data;

    /**
     * 状态码
     * 20000 操作成功
     * 50000 操作失败
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RespResult(T data, RespCode respCode) {
        this.data = data;
        this.code = respCode.getCode();
        this.message = respCode.getMsg();
    }
    public RespResult( RespCode respCode) {
        this.code = respCode.getCode();
        this.message = respCode.getMsg();
    }
    public RespResult(){}
    public static RespResult ok(){
        return new RespResult(null,RespCode.SUCCESS);
    }
    public static RespResult ok(Object data){
        return new RespResult(data,RespCode.SUCCESS);
    }
    public static RespResult error(){
        return new RespResult(null,RespCode.ERROR);
    }
    public static RespResult error(String message){
        return secByerror(RespCode.ERROR.getCode(), message);
    }

    public static RespResult secByerror(Integer code,String message){
        RespResult err = new RespResult();
        err.setCode(code);
        err.setMessage(message);
        return err;
    }
}
