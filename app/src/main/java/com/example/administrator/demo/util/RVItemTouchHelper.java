package com.example.administrator.demo.util;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by samhaus on 2017/8/21.
 */

//针对recycleview的item滑动工具类
public class RVItemTouchHelper extends ItemTouchHelper {
    public RVItemTouchHelper(Callback callback) {
        super(callback);
    }


    //关联recycleView
    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        super.attachToRecyclerView(recyclerView);
    }

    //viewholder开始拖动
    @Override
    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        super.startDrag(viewHolder);
    }

    //viewholder开始滑动
    @Override
    public void startSwipe(RecyclerView.ViewHolder viewHolder) {
        super.startSwipe(viewHolder);
    }




}
