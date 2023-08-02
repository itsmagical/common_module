package com.sinieco.common.network.entity;

/**
 * 响应对象
 * Created by LiuHe on 2018/9/10.
 */

public class AResponse<T> {

    /**
     *  请求状态
     */
    private boolean success;

    /**
     *  处理信息
     */
    private String message;

    /**
     *  响应数据
     */
    private T data;

    private long total;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "AResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}
