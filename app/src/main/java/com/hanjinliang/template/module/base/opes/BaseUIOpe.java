package com.hanjinliang.template.module.base.opes;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;


import com.hanjinliang.template.module.base.activity.BaseUIActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import com.hanjinliang.template.module.base.fragment.*;

/**
 * ui处理操作者 处理对象 uibean fragment view
 */
public class BaseUIOpe<A extends ViewDataBinding> {

    private A bind;
    private Context context;
    private BaseUIFrag frag;
    private View view;

    public BaseUIOpe(){

    }


    public void init(Context context) {
        this.context = context;
        bind = initViewDataBinding();
        bind.executePendingBindings();
    }

    public void init(BaseUIFrag frag) {
        this.frag = frag;
        this.context = frag.getActivity();
        bind = initViewDataBinding();
        bind.executePendingBindings();
    }


    public void copy(BaseUIOpe baseUIOpe){
        this.context = baseUIOpe.context;
        this.frag = baseUIOpe.frag;
        this.bind = (A) baseUIOpe.bind;
        initUI();
    }


    public void initUI(){

    }



    public A initViewDataBinding() {
        A viewDataBinding = null;
        if (viewDataBinding == null) {
            if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
                Class<A> a = (Class<A>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                Method method = null;
                try {
                    method = a.getMethod("inflate", LayoutInflater.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    viewDataBinding = (A) method.invoke(null, LayoutInflater.from(context));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                viewDataBinding = getBind();
            }
        }
        return viewDataBinding;
    }



    public A getBind() {
        return bind;
    }


    public BaseUIFrag getFrag() {
        return frag;
    }

    public BaseUIActivity getActivity(){
//        if(frag!=null ){
//            return frag.getBaseUIAct();
//        }else{
            return (BaseUIActivity) context;
//        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
