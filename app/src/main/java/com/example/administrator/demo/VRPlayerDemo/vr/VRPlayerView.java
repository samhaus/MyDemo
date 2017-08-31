package com.example.administrator.demo.VRPlayerDemo.vr;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.asha.vrlib.MD360Director;
import com.asha.vrlib.MD360DirectorFactory;
import com.asha.vrlib.MDVRLibrary;
import com.asha.vrlib.model.MDPinchConfig;

import tv.danmaku.ijk.media.player.AbstractMediaPlayer;

/**
 * Created by lsh on 2017/7/29.
 */

public class VRPlayerView extends FrameLayout implements VRMediaController.VRControl {

    private SurfaceView mGLSurfaceView;
    private MDVRLibrary mVRLibrary;
    private PlayerView mPlayerView;
    private VRMediaController mMediaController;


    public VRPlayerView(@NonNull Context context) {
        super(context);
        init();
    }

    public VRPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VRPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VRPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setKeepScreenOn(true);
        mGLSurfaceView=new GLSurfaceView(getContext());
        addView(mGLSurfaceView);
        mPlayerView = new PlayerView(getContext());
        addView(mPlayerView);
        mMediaController = new VRMediaController(getContext());
        mPlayerView.setMediaController(mMediaController);
        mMediaController.setMediaPlayer(mPlayerView);
        mMediaController.setOnVRControlListener(this);
        initVRLibrary();
    }

    private void initVRLibrary() {
        // new instance
        mVRLibrary = MDVRLibrary.with(getContext())
                .displayMode(MDVRLibrary.DISPLAY_MODE_GLASS)
                .interactiveMode(MDVRLibrary.INTERACTIVE_MODE_CARDBORAD_MOTION)
                .projectionMode(MDVRLibrary.PROJECTION_MODE_SPHERE)
                .pinchConfig(new MDPinchConfig().setDefaultValue(0.7f).setMin(0.5f))
                .pinchEnabled(true)
                .directorFactory(new MD360DirectorFactory() {
                    @Override
                    public MD360Director createDirector(int index) {
                        return MD360Director.builder().setPitch(90).build();
                    }
                })
                .asVideo(new MDVRLibrary.IOnSurfaceReadyCallback() {
                    @Override
                    public void onSurfaceReady(Surface surface) {
                        // IjkMediaPlayer or MediaPlayer
                        mPlayerView.getPlayer().setSurface(surface);
                    }
                })
                .build(mGLSurfaceView);
        mVRLibrary.setAntiDistortionEnabled(true);
    }

    public AbstractMediaPlayer getMediaPlayer() {
        return mPlayerView.getPlayer();
    }

    public PlayerView getPlayerView() {
        return mPlayerView;
    }

    public void setVideoPath(String path) {
        mPlayerView.setVideoPath(path);
    }

    public void setVideoUri(Uri uri) {
        mPlayerView.setVideoURI(uri);
    }

    public void setMediaControllerTitle(View v) {
        mMediaController.setTitleView(v);
    }


    //控制vr显示效果 可触摸移动画面
    @Override
    public void onInteractiveClick(int currentMode) {
        if (currentMode == MDVRLibrary.INTERACTIVE_MODE_CARDBORAD_MOTION) {
            mVRLibrary.switchInteractiveMode(getContext(), MDVRLibrary.INTERACTIVE_MODE_TOUCH);
        } else {
            mVRLibrary.switchInteractiveMode(getContext(), MDVRLibrary.INTERACTIVE_MODE_CARDBORAD_MOTION);
        }
    }

    //控制vr显示效果 vr双眼模式换成全屏幕单屏模式
    @Override
    public void onDisplayClick(int currentMode) {
        if (currentMode == MDVRLibrary.DISPLAY_MODE_GLASS) {
            mVRLibrary.switchDisplayMode(getContext(), MDVRLibrary.DISPLAY_MODE_NORMAL);
            mVRLibrary.setAntiDistortionEnabled(false);
        } else {
            mVRLibrary.switchDisplayMode(getContext(), MDVRLibrary.DISPLAY_MODE_GLASS);
            mVRLibrary.setAntiDistortionEnabled(true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVRLibrary!=null)mVRLibrary.handleTouchEvent(event);
        return true;
    }

    public void onPause(){
        if (mVRLibrary!=null)mVRLibrary.onPause(getContext());
        if (mPlayerView!=null)mPlayerView.pause();
    }
    public void onResume(){
        if (mVRLibrary!=null)
            mVRLibrary.onResume(getContext());
        if (mPlayerView!=null)
            mPlayerView.resume();
    }
    public void onDestroy(){
        if (mVRLibrary!=null) mVRLibrary.onDestroy();
        if (mPlayerView!=null) mPlayerView.stopPlayback();
    }

}
