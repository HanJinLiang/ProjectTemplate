package com.hanjinliang.template.module.main;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.template.R;
import com.hanjinliang.template.data.source.BaseCallBack;
import com.hanjinliang.template.data.source.DataRepository;
import com.hanjinliang.template.tools.image.MyImageLoader;
import com.summer.base.activity.BaseUIAct;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseUIAct<MainUIOpe,MainDAOpe> {


    @Override
    protected void initNow() {
        super.initNow();
        ImageView imageView=findViewById(R.id.imageView);
        MyImageLoader.getInstance()
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523610076829&di=687085808298ecd12fb42db8d8cd535c&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F24%2F50%2F43Q58PICkj4_1024.jpg")
                .error(R.drawable.ic_launcher_background)
                .angle(50)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }



    @OnClick(R.id.retrofitTest)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.retrofitTest:
                DataRepository.getInstance().getTest(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        LogUtils.d(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                break;
        }
    }
}
