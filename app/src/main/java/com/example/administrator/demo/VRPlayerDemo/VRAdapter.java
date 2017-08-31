package com.example.administrator.demo.VRPlayerDemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.demo.R;
import com.example.administrator.demo.VRPlayerDemo.activity.VRPlayerActivity;
import com.example.administrator.demo.databinding.ItemVrListMvvmBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wheat7 on 05/07/2017.
 */

public class VRAdapter extends RecyclerView.Adapter<VRAdapter.ViewHolder> {

    private List<VRVideo> mVRVideoList = new ArrayList<>();
    private Context mContext;

    public VRAdapter(Context context, List<VRVideo> data) {
        this.mVRVideoList = data;
        this.mContext = context;
    }

    public VRAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVrListMvvmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_vr_list_mvvm, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        binding.setViewHolder(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mVRVideoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mVRVideoList.size();
    }

    public void setVRVideoList(List<VRVideo> VRVideoList) {
        if (VRVideoList != null && VRVideoList.size() != 0) {
            mVRVideoList = VRVideoList;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private VRVideo mData;
        private ItemVrListMvvmBinding viewDataBinding;

        public ViewHolder(ItemVrListMvvmBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        public void bindData(VRVideo vrVideo) {
            mData = vrVideo;
//            viewDataBinding.setVariable(BR.data, vrVideo);
            Glide.with(itemView.getContext())
                    .load(Uri.fromFile(new File(mData.getPath())))
                    .into(viewDataBinding.vrImage);
            viewDataBinding.executePendingBindings();
        }

        public void onItemClick(View view) {
            Intent intent = new Intent(view.getContext(), VRPlayerActivity.class);
            intent.putExtra("path", mData.getPath());
            view.getContext().startActivity(intent);
        }

    }
}
