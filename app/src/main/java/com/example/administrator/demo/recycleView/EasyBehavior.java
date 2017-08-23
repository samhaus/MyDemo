package com.example.administrator.demo.recycleView;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

/**
 * Created by samhaus on 2017/8/23.
 */

public class EasyBehavior extends CoordinatorLayout.Behavior {

    //关联view的依赖关系
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    //当被关联的view变化时，关联的view的变化设定
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
