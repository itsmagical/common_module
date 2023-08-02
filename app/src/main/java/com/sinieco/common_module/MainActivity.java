package com.sinieco.common_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.sinieco.arch.base.BaseActivity;
import com.sinieco.architecture.base.BaseActivity;
import com.sinieco.common.retrofit_client_manager.OkHttpOptions;
import com.sinieco.common.retrofit_client_manager.RetrofitClient;
import com.sinieco.common.retrofit_client_manager.RetrofitClientManager;
import com.sinieco.common.widget.multiplestatus.MultipleStatusLayout;
import com.sinieco.common.widget.multiplestatus.OnRetryingListener;
import com.sinieco.common.widget.multiplestatus.StatusOptions;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    static {

//        0000 0000 0000 0000 0000 0000 0000 0000
//
//        0100 0000 0000 0000 0000 0000 0000 0000
//
//        1000 0000 0000 0000 0000 0000 0000 0000
//
//        1100 0000 0000 0000 0000 0000 0000 0000
//        ~
//        0011 1111 1111 1111 1111 1111 1111 1111
//        &
//        1000 0000 0000 0000 0000 0000 0000 0100
//
//        0000 0000 0000 0000 0000 0000 0000 0100





//        0000 0001 0000 0000 0000 0000 0000 0000
//        0000 0000 0000 0000 0000 0000 0000 0100
//        0000 0001 0000 0000 0000 0000 0000 0100
//
//
//        0000 0000 1111 1111 1111 1111 1111 1111
//        0000 0000 0000 0000 0000 0000 0000 0100
//        0000 0000 0000 0000 0000 0000 0000 0100


        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpOptions okHttpOptions = new OkHttpOptions()
            .cookieEnable(false)
            .setOkHttpBuilder(
                new OkHttpClient.Builder()
                    .addInterceptor(new ContentTypeInterceptor(ContentTypeInterceptor.jsonContentType))
            );

        RetrofitClientManager
            .getInstance()
            .createRetrofitClient("http://192.168.100.236:8082/uia/",
                    okHttpOptions, true);

        RetrofitClient client = RetrofitClientManager
            .getInstance()
            .getRetrofitClient();

        LoginService loginService = client.create(LoginService.class);
        loginService.passkey("a").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, "onResponse: " + response.body() );
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " );
            }
        });

        final MultipleStatusLayout multipleStatusLayout = findViewById(R.id.multiple_status_layout);

        TextView textView = new TextView(this);
        textView.setText("局部配置加载布局");

//        multipleStatusLayout.setLoadingView(textView);

        multipleStatusLayout.showStatus(StatusOptions.LOADING);

        multipleStatusLayout.setOnRetryingListener(new OnRetryingListener() {
            @Override
            public void onRetrying() {
                Log.e(TAG, "onRetrying: " );
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                multipleStatusLayout.showStatus(StatusOptions.ERROR);
            }
        }, 2000);

    }
}
