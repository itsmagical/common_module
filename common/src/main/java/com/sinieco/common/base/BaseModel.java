package com.sinieco.common.base;

import com.google.gson.Gson;
import com.sinieco.arch.util.TimeUtils;
import com.sinieco.common.network.NetworkConfig;
import com.sinieco.common.network.entity.ARequest;
import com.sinieco.common.network.entity.AResponse;
import com.sinieco.common.network.entity.Pagination;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 请求参数转换成json格式参数以及RxJava处理基本网络数据
 * Created by LiuHe on 2018/9/10.
 */

public abstract class BaseModel {

    protected Gson gson;

    protected BaseModel() {
        gson = new Gson();
    }

    protected <T> String toJson(T element) {
        return gson.toJson(element);
    }

    /**
     * 请求参数转换成json格式参数
     * @param params 请求参数
     * @return json格式参数
     */
    protected <T> String getRequestJson(T params) {
        ARequest<T> request = new ARequest<>(TimeUtils.formatTime(System.currentTimeMillis()), params);
        return toJson(request);
    }

    /**
     * 包含分页参数的请求参数转换成json格式参数
     * @param params 请求参数
     * @param pagination 分页参数对象
     * @return json格式参数
     */
    protected  <T> String getRequestJson(T params, Pagination pagination) {
        ARequest<T> request = new ARequest<>(TimeUtils.formatTime(System.currentTimeMillis()), params, pagination);
        return toJson(request);
    }

    /**
     * 包含分页参数的请求参数转换成json格式参数
     * @param params 请求参数
     * @param start 分页起始值，分页数量默认20
     * @return json格式参数
     */
    protected <T> String getRequestJson(T params, int start) {
        ARequest<T> request = new ARequest<>(TimeUtils.formatTime(System.currentTimeMillis()), params, getPagination(start, NetworkConfig.PAGE_ITEM_COUNT));
        return toJson(request);
    }


    protected Pagination getPagination(int start, int count) {
        return new Pagination(start, count);
    }

    protected Pagination getPagination(int start, int count, String sort, boolean dir) {
        return new Pagination(start, count, sort, dir);
    }

    /**
     * 处理网络响应数据线程
     */
    protected <T> Observable<T> observable(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 网络响应数据Observable<AResponse<T>> 变换为Observable<T>
     */
    protected <T> Observable<T> dataObservable(Observable<AResponse<T>> observable) {
        return filterObservable(observable)
                .map(new Function<AResponse<T>, T>() {
                    @Override
                    public T apply(@NonNull AResponse<T> aResponse) throws Exception {
                        return aResponse.getData();
                    }
                });
    }

    protected <T> Observable<AResponse<T>> filterObservable(Observable<AResponse<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<AResponse<T>>() {
                    @Override
                    public boolean test(@NonNull AResponse<T> aResponse) throws Exception {
                        if (aResponse.isSuccess()) {
                            return true;
                        } else {
                            throw new Exception(aResponse.getMessage());
                        }
                    }
                });
    }

}
