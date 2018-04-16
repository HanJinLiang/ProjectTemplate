package com.hanjinliang.template.module.base.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.hanjinliang.template.module.base.opes.BaseDAOpe;
import com.hanjinliang.template.module.base.opes.BaseOpes;
import com.hanjinliang.template.module.base.opes.BaseUIOpe;
import com.hanjinliang.template.R;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import butterknife.ButterKnife;


/**
 * Created by summer on 2016/4/16 0016 11:51.
 */
public abstract class BaseUIActivity<A extends BaseUIOpe, B extends BaseDAOpe> extends AppCompatActivity {

    protected ViewDataBinding baseUIRoot;

    protected BaseOpes<A, B> opes;

    private String moudle;

    private ArrayList<String> moudles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getBaseUILayout().getRoot());
        if(getP().getU().getBind()!=null){
            ((ViewGroup)baseUIRoot.getRoot()).addView(getP().getU().getBind().getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        getP().getD().initDA();
        getP().getU().initUI();
        initNow();
        ButterKnife.bind(getActivity());
        if(registerEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    protected void initNow(){

    }

    protected <T extends ViewDataBinding> T getBaseUILayout() {
        if(baseUIRoot==null){
            baseUIRoot = DataBindingUtil.bind(LayoutInflater.from(getActivity()).inflate(R.layout.act_baseui,null,false));
        }
        return (T) baseUIRoot;
    }


    public BaseOpes<A, B> getP() {
        if (opes == null) {
            opes= new BaseOpes<>(null,null);
            initaa(getClass());
            initbb(getClass());
        }
        return opes;
    }

    private void initbb(Class<?> c) {
        if (c == null) {
            opes.setDa((B)(new BaseDAOpe()));
            return;
        }
        if (c.getGenericSuperclass() instanceof ParameterizedType) {
            Class<B> b = (Class<B>) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[1];
            try {
                Constructor<B> bc = b.getConstructor();
                B bb = bc.newInstance();
                opes.setDa(bb);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            initbb(c.getSuperclass());
        }
    }


    private void initaa(Class<?> c) {
        if (c == null) {
            opes.setUi((A)(new BaseUIOpe<ViewDataBinding>()));
            opes.getU().init(getActivity());
            return;
        }
        if (c.getGenericSuperclass() instanceof ParameterizedType) {
            Class<A> a = (Class<A>) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0];
            try {
                Constructor<A> ac = a.getConstructor();
                A aa = ac.newInstance();
                aa.init(getActivity());
                opes.setUi(aa);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            initaa(c.getSuperclass());
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        moudles.clear();
        if(registerEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected boolean registerEventBus(){
        return false;
    }

    public String getMoudle() {
        return moudle;
    }

    public void setMoudle(String moudle) {
        this.moudle = moudle;
        moudles.add(moudle);
    }

    public BaseUIActivity getActivity() {
        return this;
    }


    public ArrayList<String> getMoudles() {
        return moudles;
    }
}
