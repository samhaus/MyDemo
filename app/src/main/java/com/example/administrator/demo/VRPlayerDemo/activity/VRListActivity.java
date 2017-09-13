package com.example.administrator.demo.VRPlayerDemo.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.demo.R;
import com.example.administrator.demo.VRPlayerDemo.VRAdapter;
import com.example.administrator.demo.VRPlayerDemo.VRVideo;
import com.example.administrator.demo.base.BaseActivity;
import com.example.administrator.demo.base.CommonAdapter;
import com.example.administrator.demo.base.CommonViewHolder;
import com.example.administrator.demo.base.MvvmBaseActivity;
import com.example.administrator.demo.databinding.ActivityVrListBinding;
import com.example.administrator.demo.util.FileUtil;
import com.example.administrator.demo.util.LogUtil;
import com.example.administrator.demo.util.StringUtil;
import com.example.administrator.demo.util.TimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//采用mvvm databinding模式
public class VRListActivity extends MvvmBaseActivity<ActivityVrListBinding> implements SwipeRefreshLayout.OnRefreshListener {


    //存放vr视频的list
    private List<VRVideo> mVRVideoList ;

    /**
     * 也可以采用mvvm模式的adapter 这里采用mvc用的统一封装的adapter
     */
    private CommonAdapter adapter;
    private VRAdapter mvvmAdapter;

    //权限相关
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_PERMISSIONS = 1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_vr_list;
    }

    @Override
    public void initView() {
        getBinding().refresh.setOnRefreshListener(this);
        getBinding().rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CommonAdapter<VRVideo>(this, mVRVideoList, R.layout.item_vr_list) {
            @Override
            public void convert(CommonViewHolder holder, VRVideo item) {
//                ImageView vrImage = holder.getView(R.id.vr_image);
//                Glide.with(VRListActivity.this)
//                        .load(Uri.fromFile(new File(item.getPath())))
//                        .into(vrImage);
                holder.setImageURI(VRListActivity.this,R.id.vr_image,Uri.fromFile(new File(item.getPath())).toString());
                holder.setText(R.id.tv_title, StringUtil.isNull(item.getTitle()));
                holder.setText(R.id.tv_time,StringUtil.isNull(item.getDurationShow()));
                holder.setText(R.id.tv_file_size,StringUtil.isNull(item.getFileLength()));
                LogUtil.e(item.getPath()+"~~~~~~~");
            }

        };

        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(VRListActivity.this, VRPlayerActivity.class);
                intent.putExtra("path", mVRVideoList.get(position).getPath());
                view.getContext().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        getBinding().rv.setAdapter(adapter);
        checkPermission();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mVRVideoList = new ArrayList<>();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_PERMISSIONS);
        } else {
            scanVideo();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanVideo();
                }
            }
            return;
        }
    }


    @Override
    public void onRefresh() {
        checkPermission();
    }

    /**
     * 遍历视频文件  目前先使用扫描本地 可改成获取网络数据的视频列表
     */
    private void scanVideo() {
        ContentResolver contentResolver = getContentResolver();
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.SIZE,
        };

        Cursor cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

        //int allVideo = cursor.getCount();
        mVRVideoList.clear();
        while (cursor.moveToNext()) {
            VRVideo mVRVideo = new VRVideo();
            mVRVideo.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
            mVRVideo.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
            mVRVideo.setDurationShow(TimeUtil.millisToTime(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))));
            mVRVideo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)).toString());
            mVRVideo.setDateModified(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED)));
            long len = new File((cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)))).length();
            mVRVideo.setFileLength(FileUtil.convertFileSize(len));
            cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
            mVRVideoList.add(mVRVideo);
        }
        cursor.close();
        adapter.setList(mVRVideoList);
        getBinding().refresh.setRefreshing(false);
    }

}
