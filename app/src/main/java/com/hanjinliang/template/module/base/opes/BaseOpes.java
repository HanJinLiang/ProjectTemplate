package com.hanjinliang.template.module.base.opes;

import java.io.Serializable;

/**
 * Created by ${viwmox} on 2016-12-21.
 */

public class BaseOpes<A extends BaseUIOpe, B extends BaseDAOpe> implements Serializable {

    /**
     * 操作者
     */
    A ui;
    /**
     * 数据操作者
     */
    B da;


    public BaseOpes(A ui, B da) {
        this.ui = ui;
        this.da = da;
    }

    public B getD() {
        return da;
    }

    public void setDa(B da) {
        this.da = da;
    }

    public A getU() {
        return ui;
    }

    public void setUi(A ui) {
        this.ui = ui;
    }
}
