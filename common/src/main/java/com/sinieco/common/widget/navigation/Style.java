package com.sinieco.common.widget.navigation;

/**
 * Created by Administrator on 2019/5/27.
 */

public enum Style {

    TRANSPARENT(0),
    TRANSLUCENCE(1),
    FILL(2),
    DEFAULT(3);

    int val;

    Style(int val) {
        this.val = val;
    }

    int getVal() {
        return val;
    }

}
