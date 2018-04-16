package com.hanjinliang.template.module.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hanjinliang.template.R;
import com.hanjinliang.template.module.base.activity.BaseUIActivity;
import com.hanjinliang.template.module.base.opes.BaseDAOpe;
import com.hanjinliang.template.module.base.opes.BaseOpes;
import com.hanjinliang.template.module.base.opes.BaseUIOpe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by summer on 2016/4/16 0016 16:03.
 */
public abstract class BaseUIFrag<A extends BaseUIOpe, B extends BaseDAOpe> extends Fragment implements View.OnClickListener, View.OnLongClickListener {


    private Unbinder unbinder;

    private BaseOpes<A, B> opes;


    private boolean isFiistVisibleinit = false;

    private ViewGroup baseUIRoot;

    private BaseUIFrag baseUIFrag;

    private BaseUIActivity baseUIActivity;

    private long uniqueid;

    private BaseUIFrag frag;


    public BaseUIFrag() {
        baseUIFrag = this;
        setArguments(new Bundle());
        opes = new BaseOpes<>(null, null);
        initbb(getClass());
        getP().getD().initDA();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uniqueid = System.currentTimeMillis();
        frag = this;
        if(is注册事件总线()){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseUIActivity) {
            baseUIActivity = (BaseUIActivity) activity;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View group = inflater.inflate(getBaseUILayout(),container,false);
        return group;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initNow();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                baseUIRoot = view.findViewById(R.id.act_base_root);
                initaa(baseUIFrag.getClass());
                baseUIRoot.addView(getP().getU().getBind().getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                getP().getU().setView(getView());
                getP().getU().initUI();
                unbinder = ButterKnife.bind(baseUIFrag, baseUIRoot);
                initdelay();
            }
        }, delayTime());
    }

    protected int delayTime(){
        return 300;
    }


    public void initdelay() {
        if(getView()==null){
            return;
        }
    }

    public void initNow() {
        if(getView()==null){
            return;
        }
    }

    public void onFristVisible(){
        if(!isFiistVisibleinit){
            onFristVisibleInit();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    on第一次显示延迟加载();
                }
            }, delayTime());
            isFiistVisibleinit = true;
        }
    }

    protected void on第一次显示延迟加载(){

    }

    protected void onFristVisibleInit(){

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    /**
     * 重新此方法获取布局文件
     */
    public int getBaseUILayout() {
        return R.layout.act_baseui;
    }

    /**
     * 获取操作类
     */
    public BaseOpes<A, B> getP() {
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
            opes.getU().init(getBaseUIFrag());
            return;
        }
        if (c.getGenericSuperclass() instanceof ParameterizedType) {
            Class<A> a = (Class<A>) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0];
            try {
                Constructor<A> ac = a.getConstructor();
                A aa = ac.newInstance();
                aa.init(getBaseUIFrag());
                opes.setUi(aa);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            initaa(c.getSuperclass());
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }

    public void onResult(int req, Bundle bundle) {

    }

    @Override
    public void onDestroy() {
        if(is注册事件总线()){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    public void startLoading(){
//        loadUtil.startLoading(getActivity(), (ViewGroup) getView());
//    }
//
//    public void stopLoading(){
//        loadUtil.stopLoading( (ViewGroup) getView());
//    }

//    public void showTips(String txt){
////        if(getBaseUIAct()==null){
////            return;
////        }
//        tipUtil.showTips(getBaseUIAct(),  (ViewGroup) getView(),txt);
//    }
//
//    public void removeTips(){
//        tipUtil.removeTips( (ViewGroup) getView());
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected boolean is注册事件总线(){
        return false;
    }

    public ViewGroup getBaseUIRoot() {
        return baseUIRoot;
    }

//    public String get容器() {
//        return getArguments().getString(ValueConstant.容器);
//    }
//
//    public void set容器(String 容器){
//        getArguments().putString(ValueConstant.容器,容器);
//    }

    public BaseUIActivity getBaseUIAct() {
        return baseUIActivity;
    }
//
//    public FragManager2 getFragM() {
//        return fragM;
//    }
//
//    public void setFragM(FragManager2 fragM) {
//        this.fragM = fragM;
//    }

    public BaseUIFrag getBaseUIFrag() {
        return baseUIFrag;
    }
}
