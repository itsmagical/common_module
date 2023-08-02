package com.sinieco.common.network.entity;

import java.io.Serializable;


/**
 * pda端向web端请求时序列化对象
 * @author liwenhao
 * 2016-12-19 上午11:28:34
 * @param <T>
 */
public class ARequest<T> implements Serializable {

    private static final long serialVersionUID = -7816945325851639128L;
    
    /**
     * 请求的日期(手机端的时间)
     */
    public String requestTime;
    
    /**
     * 请求的其他信息
     */
    public T data;
    
    /**
     * 分页对象。如果返回对象PdaResponse的data为list，则此参数有效。
     */
    public Pagination pagination;

	public ARequest() {
	}

	public ARequest(String requestTime, T data, Pagination pagination) {
		this.requestTime = requestTime;
		this.data = data;
		this.pagination = pagination;
	}

	public ARequest(String requestTime, T data) {
		this.requestTime = requestTime;
		this.data = data;
	}

	@Override
	public String toString() {
		return "ARequest{" +
				"requestTime='" + requestTime + '\'' +
				", data=" + data +
				", pagination=" + pagination +
				'}';
	}

	public ARequest(String requestTime, Pagination pagination) {
		this.requestTime = requestTime;
		this.pagination = pagination;
	}
}
