package com.example.administrator.demo.VRPlayerDemo.vr;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.demo.util.LogUtil;

import java.lang.reflect.Method;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by lsh on 26/07/2017.
 */

public class VRMediaController extends FrameLayout {

    MediaController mMediaController = new MediaController(getContext());

    private final static String TAG = "VRMediaController";
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private static final int sDefaultTimeout = 3000;
    private static final int VR_INTERACTIVE_MODE_GYROSCOPE = 4;
    private static final int VR_INTERACTIVE_MODE_TOUCH = 3;
    private static final int VR_DISPLAY_MODE_GLASS = 102;
    private static final int VR_DISPLAY_MODE_NORMAL = 101;

    private int interactiveMode = VR_INTERACTIVE_MODE_TOUCH;
    private int displayMode = VR_DISPLAY_MODE_NORMAL;

    private MediaPlayerControl mPlayer;
    private final Context mContext;
    private View mAnchor;
    private View mRoot;
    private PopupWindow mWindow;
    private ProgressBar mProgress;
    private TextView mEndTime, mCurrentTime;
    private boolean mShowing;
    private boolean mDragging;
    private boolean mFromXml;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private ImageView mPauseButton;

    private ImageView mVRInteractiveModeButton;
    private ImageView mVRDisplayModeButton;

    private int mAnimStyle;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case FADE_OUT:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    pos = setProgress();
                    if (!mDragging && mShowing) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                        updatePausePlay();
                    }
                    break;
            }
        }
    };


    public VRMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRoot = this;
        mContext = context;
        mFromXml = true;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        if (mRoot != null)
            initControllerView(mRoot);
    }


    public VRMediaController(Context context, boolean useFastForward) {
        super(context);
        mContext = context;
        initFloatingWindow();
    }

    public VRMediaController(Context context) {
        this(context, true);
    }

    private void initFloatingWindow() {
        mWindow = new PopupWindow(mContext);
        mWindow.setFocusable(false);
        mWindow.setBackgroundDrawable(null);
        mWindow.setOutsideTouchable(true);
        mAnimStyle = android.R.style.Animation;
        requestFocus();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setWindowLayoutType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                mAnchor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                Method setWindowLayoutType = PopupWindow.class.getMethod("setWindowLayoutType", new Class[]{int.class});
                setWindowLayoutType.invoke(mWindow, WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG);
            } catch (Exception e) {
            }
        }
    }

    private final OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (mShowing) {
                    hide();
                }
            }
            return false;
        }
    };

    public void setMediaPlayer(MediaPlayerControl player) {
        mPlayer = player;
        updatePausePlay();
    }

    /**
     * Set the view that acts as the anchor for the control view.
     * This can for example be a VideoView, or your Activity's main view.
     * When VideoView calls this method, it will use the VideoView's parent
     * as the anchor.
     *
     * @param view The view to which to anchor the controller when it is visible.
     */
    public void setAnchorView(View view) {
        mAnchor = view;
        if (!mFromXml) {
            removeAllViews();
            mRoot = makeControllerView();
            mWindow.setContentView(mRoot);
            mWindow.setWidth(LayoutParams.MATCH_PARENT);
            mWindow.setHeight(LayoutParams.WRAP_CONTENT);
        }
        initControllerView(mRoot);
    }

    /**
     * Create the view that holds the widgets that control playback.
     * Derived classes can override this to create their own.
     *
     * @return The controller view.
     * @hide This doesn't work as advertised
     */
    protected View makeControllerView() {
        return ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResources().getIdentifier("media_controller_vr", "layout", mContext.getPackageName()), this);
    }

    private void initControllerView(View v) {
        mPauseButton = (ImageView) v.findViewById(getResources().getIdentifier("mediacontroller_play_pause", "id", mContext.getPackageName()));
        if (mPauseButton != null) {
            mPauseButton.requestFocus();
            mPauseButton.setOnClickListener(mPauseListener);
        }

        mVRInteractiveModeButton = (ImageView) v.findViewById(getResources().getIdentifier("mediacontroller_interactive", "id", mContext.getPackageName()));
        if (mVRInteractiveModeButton != null) {
            mVRInteractiveModeButton.requestFocus();
            mVRInteractiveModeButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVRControl.onInteractiveClick(interactiveMode);
                    updateInteractive();
                }
            });
        }
        mVRDisplayModeButton = (ImageView) v.findViewById(getResources().getIdentifier("mediacontroller_display", "id", mContext.getPackageName()));
        if (mVRDisplayModeButton != null) {
            mVRDisplayModeButton.requestFocus();
            mVRDisplayModeButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVRControl.onDisplayClick(displayMode);
                    updateDisplay();
                }
            });
        }

        mProgress = (SeekBar) v.findViewById(getResources().getIdentifier("mediacontroller_seekbar", "id", mContext.getPackageName()));
        if (mProgress != null) {
            if (mProgress instanceof SeekBar) {
                SeekBar seeker = (SeekBar) mProgress;
                seeker.setOnSeekBarChangeListener(mSeekListener);
            }
            mProgress.setMax(1000);
        }

        mEndTime = (TextView) v.findViewById(getResources().getIdentifier("mediacontroller_time_total", "id", mContext.getPackageName()));
        mCurrentTime = (TextView) v.findViewById(getResources().getIdentifier("mediacontroller_time_current", "id", mContext.getPackageName()));

        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    /**
     * Show the controller on screen. It will go away
     * automatically after 3 seconds of inactivity.
     */
    public void show() {
        show(sDefaultTimeout);
    }


    /**
     * Show the controller on screen. It will go away
     * automatically after 'timeout' milliseconds of inactivity.
     *
     * @param timeout The timeout in milliseconds. Use 0 to show
     *                the controller until hide() is called.
     */
    public void show(int timeout) {
        if (!mShowing && mAnchor != null) {
            setProgress();
            if (mPauseButton != null) {
                mPauseButton.requestFocus();
            }
            //disableUnsupportedButtons();
            //updateFloatingWindowLayout();
//            mWindowManager.addView(mDecor, mDecorLayoutParams);
            if (mFromXml) {
                setVisibility(View.VISIBLE);
            } else {
                int[] location = new int[2];

                mAnchor.getLocationOnScreen(location);
                Rect anchorRect = new Rect(location[0], location[1], location[0] + mAnchor.getWidth(), location[1]);

                mWindow.setAnimationStyle(mAnimStyle);
//        setWindowLayoutType();
//        mWindow.showAtLocation(mAnchor, Gravity.TOP, anchorRect.left, anchorRect.bottom);
                mWindow.showAsDropDown(mAnchor, 0, -getResources().getDimensionPixelOffset(getResources().getIdentifier("media_control_height", "dimen", mContext.getPackageName())));
            }
            if (mTitleView!=null)
                mTitleView.setVisibility(View.VISIBLE);
            mShowing = true;
        }

        updatePausePlay();

        // cause the progress bar to be updated even if mShowing
        // was already true.  This happens, for example, if we're
        // paused with the progress bar showing the user hits play.
//        post(mShowProgress);
        mHandler.sendEmptyMessage(SHOW_PROGRESS);
        if (timeout != 0) {
//            removeCallbacks(mFadeOut);
//            postDelayed(mFadeOut, timeout);
            mHandler.removeMessages(FADE_OUT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FADE_OUT), timeout);
        }
    }

    public boolean isShowing() {
        return mShowing;
    }

    /**
     * Remove the controller from the screen.
     */
    public void hide() {
        if (mAnchor == null)
            return;

        if (mShowing) {
            try {
                if (mFromXml) {
//                    removeCallbacks(mShowProgress);
                    mHandler.removeMessages(SHOW_PROGRESS);
                    setVisibility(View.GONE);
                } else {
                    mWindow.dismiss();
                    if (mTitleView!=null)
                        mTitleView.setVisibility(View.GONE);
                }
            } catch (IllegalArgumentException ex) {
                Log.w("MediaController", "already removed");
            }
            Log.d(TAG, "hide");
            mShowing = false;
        }
    }


//    private final Runnable mFadeOut = new Runnable() {
//        @Override
//        public void run() {
//            Log.d(TAG, "fade," + "mShowing=:" + mShowing);
//            if (mShowing) hide();
//
//        }
//    };
//
//    private final Runnable mShowProgress = new Runnable() {
//        @Override
//        public void run() {
//            long pos = setProgress();
//            if (!mDragging && mShowing && mPlayer.isPlaying()) {
//                postDelayed(mShowProgress, 1000 - (pos % 1000));
//            }
//        }
//    };

    /**
     * Time Format
     *
     * @param timeMs
     * @return
     */
    private String stringForTime(long timeMs) {
        long totalSeconds = timeMs / 1000;

        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    private long setProgress() {
        if (mPlayer == null || mDragging) {
            return 0;
        }
        long position = mPlayer.getCurrentPosition();
        long duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        if (mEndTime != null)
            mEndTime.setText(stringForTime(duration));
        if (mCurrentTime != null)
            mCurrentTime.setText(stringForTime(position));

        return position;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                show(0); // showe until hide is called
                break;
            case MotionEvent.ACTION_UP:
                show(sDefaultTimeout); // start timeout
                break;
            case MotionEvent.ACTION_CANCEL:
                hide();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        show(sDefaultTimeout);
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        final boolean uniqueDown = event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN;
        if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                || keyCode == KeyEvent.KEYCODE_SPACE) {
            if (uniqueDown) {
                doPauseResume();
                show(sDefaultTimeout);
                if (mPauseButton != null) {
                    mPauseButton.requestFocus();
                }
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
            if (uniqueDown && !mPlayer.isPlaying()) {
                mPlayer.start();
                updatePausePlay();
                show(sDefaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
                || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
            if (uniqueDown && mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePausePlay();
                show(sDefaultTimeout);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
                || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                || keyCode == KeyEvent.KEYCODE_VOLUME_MUTE
                || keyCode == KeyEvent.KEYCODE_CAMERA) {
            // don't show the controls for volume adjustment
            return super.dispatchKeyEvent(event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            if (uniqueDown) {
                hide();
            }
            return true;
        }

        show(sDefaultTimeout);
        return super.dispatchKeyEvent(event);
    }

    private final OnClickListener mPauseListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    private void updatePausePlay() {
        if (mRoot == null || mPauseButton == null)
            return;
        if (mPlayer.isPlaying())
            mPauseButton.setImageResource(getResources().getIdentifier("mediacontroller_pause", "drawable", mContext.getPackageName()));
        else
            mPauseButton.setImageResource(getResources().getIdentifier("mediacontroller_play", "drawable", mContext.getPackageName()));
    }



    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();
    }

    // There are two scenarios that can trigger the seekbar listener to trigger:
    //
    // The first is the user using the touchpad to adjust the posititon of the
    // seekbar's thumb. In this case onStartTrackingTouch is called followed by
    // a number of onProgressChanged notifications, concluded by onStopTrackingTouch.
    // We're setting the field "mDragging" to true for the duration of the dragging
    // session to avoid jumps in the position in case of ongoing playback.
    //
    // The second scenario involves the user operating the scroll ball, in this
    // case there WON'T BE onStartTrackingTouch/onStopTrackingTouch notifications,
    // we will simply apply the updated position without suspending regular updates.
    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            show(3600000);
            mDragging = true;
            // By removing these pending progress messages we make sure
            // that a) we won't update the progress while the user adjusts
            // the seekbar and b) once the user is done dragging the thumb
            // we will post one of these messages to the queue again and
            // this ensures that there will be exactly one message queued up.

//            removeCallbacks(mShowProgress);
            mHandler.removeMessages(SHOW_PROGRESS);
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser) {
                // We're not interested in programmatically generated changes to
                // the progress bar's position.
                return;
            }

            long duration = mPlayer.getDuration();
            long newPosition = (duration * progress) / 1000L;
            mPlayer.seekTo((int) newPosition);
            if (mCurrentTime != null)
                mCurrentTime.setText(stringForTime((int) newPosition));
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            mDragging = false;
            setProgress();
            updatePausePlay();
            show(sDefaultTimeout);

            // Ensure that progress is properly updated in the future,
            // the call to show() does not guarantee this because it is a
            // no-op if we are already showing.
//            post(mShowProgress);
            mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);
        }
    };

    @Override
    public void setEnabled(boolean enabled) {
        if (mPauseButton != null) {
            mPauseButton.setEnabled(enabled);

            if (mProgress != null) {
                mProgress.setEnabled(enabled);
            }
            //disableUnsupportedButtons();
            super.setEnabled(enabled);
        }

    }

    public interface MediaPlayerControl {
        void start();

        void pause();

        long getDuration();

        long getCurrentPosition();

        void seekTo(long pos);

        boolean isPlaying();

        int getBufferPercentage();
    }

    /*----------------------------------------Extend VR ------------------------------------------*/

    private VRControl mVRControl;

    public interface VRControl {
        void onInteractiveClick(int currentMode);
        void onDisplayClick(int currentMode);
    }

    public void setOnVRControlListener(VRControl vrControl) {
        mVRControl = vrControl;
    }

    private void updateInteractive() {
        if (mRoot == null || mVRInteractiveModeButton == null)
            return;
        LogUtil.e(interactiveMode+"---interactiveMode");
        if (interactiveMode == VR_INTERACTIVE_MODE_GYROSCOPE) {
            interactiveMode = VR_INTERACTIVE_MODE_TOUCH;
            mVRInteractiveModeButton.setImageResource(getResources().getIdentifier("ic_gyroscope", "drawable", mContext.getPackageName()));
        }
        else {
            interactiveMode = VR_INTERACTIVE_MODE_GYROSCOPE;
            mVRInteractiveModeButton.setImageResource(getResources().getIdentifier("ic_touch_mode", "drawable", mContext.getPackageName()));
        }
    }

    private void updateDisplay() {
        if (mRoot == null || mVRDisplayModeButton == null)
            return;
        LogUtil.e(interactiveMode+"---interactiveMode");
        if (displayMode == VR_DISPLAY_MODE_GLASS) {
            displayMode = VR_DISPLAY_MODE_NORMAL;
            mVRDisplayModeButton.setImageResource(getResources().getIdentifier("ic_vr_mode", "drawable", mContext.getPackageName()));
        }
        else {
            displayMode = VR_DISPLAY_MODE_GLASS;
            mVRDisplayModeButton.setImageResource(getResources().getIdentifier("ic_eye_mode", "drawable", mContext.getPackageName()));
        }
    }

    /*----------------------------------------Extend Title ------------------------------------------*/

    private View mTitleView;

    public void setTitleView(View v){
        mTitleView=v;
    }

}
