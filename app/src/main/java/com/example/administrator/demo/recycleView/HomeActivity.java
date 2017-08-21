package com.example.administrator.demo.recycleView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import com.example.administrator.demo.BiliDemo.BiliActivity;
import com.example.administrator.demo.EmptyLoadRetryDemo.test.CategoryActivity;
import com.example.administrator.demo.PickViewActivity;
import com.example.administrator.demo.PopuActivity;
import com.example.administrator.demo.R;
import com.example.administrator.demo.TagViewActivity;
import com.example.administrator.demo.WindowMgrDemo.WindowMgrActivity;
import com.example.administrator.demo.boomMenu.MainBoomMenuActivity;
import com.example.administrator.demo.indexListview.MainActivity;
import com.example.administrator.demo.lottieDemo.LottieDemoActivity;
import com.example.administrator.demo.recycleView.RvAdapter.ItemClickListener;
import com.example.administrator.demo.simpletooltip.EmojiRainActivity;
import com.example.administrator.demo.swipeCaptchaDemo.swipeCaptchaActivity;
import com.example.administrator.demo.util.AppManager;
import com.example.administrator.demo.util.TimeUtil;
import com.example.administrator.demo.util.ToastUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：LSH on 2016/12/2 0002 11:12
 */
public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";

    private RvAdapter rvAdapter;
    private List list;

    private TwinklingRefreshLayout refreshLayout;
    private long preTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initDate();
        initView();
    }

    public void initDate() {
        list = new ArrayList<>();
        list.add("切换布局");
        list.add("不同位置的popuwindow");
        list.add("TagView流式标签");
        list.add("神奇的输入框");
        list.add("仿苹果底部弹出筛选框");
        list.add("神奇的弹出菜单");
        list.add("数据存储Realm");
        list.add("自定义的悬浮层");
        list.add("AirBnb的开源动画库");
        list.add("仿斗鱼滑动验证码");
        list.add("b站开源直播以及弹幕");
        list.add("仿通讯录");
        list.add("空布局emptyLayout");


        for (int i = 'A'; i < 'Z'; i++) {
            list.add((char) i);
        }
    }

    public void initView() {
        Toolbar title = (Toolbar) findViewById(R.id.title);
        title.setTitle("Samhuas");
        title.setTitleTextColor(getResources().getColor(R.color.white));
        title.setBackgroundColor(getResources().getColor(R.color.blue));
        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        //设置布局方式
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        final GridLayoutManager layoutManager2 = new GridLayoutManager(this, 2);
        final StaggeredGridLayoutManager layoutManager3 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);

        //设置表格，根据position计算在该position处1列占几格数据
        layoutManager2.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //计算在哪个position时要显示1列数据，即columnCount / 1列 = 3格，return数值为几 即1列数据占满几格
                if (position == 2 || position == list.size() - 2) {
                    //return 2即为columnCount / 2 = 2列，一格数据占2列，该行显示2列
                    return 2;
                }
                //1格1列，即改行有columnCount / 1 = 4列，该行显示4列
                return 1;
            }
        });
        rv.setLayoutManager(layoutManager);
        //设置动画
        rv.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
        rvAdapter = new RvAdapter(this, list);
        rv.setAdapter(rvAdapter);
        rvAdapter.setOnclicListener(new ItemClickListener() {
            @Override
            public void onItemClick(int Pos) {
                switch (Pos) {
                    case 0:
                        if (rv.getLayoutManager() == layoutManager) {
                            rv.setLayoutManager(layoutManager2);
                        } else if (rv.getLayoutManager() == layoutManager2) {
                            rv.setLayoutManager(layoutManager3);
                        } else if (rv.getLayoutManager() == layoutManager3) {
                            rv.setLayoutManager(layoutManager);
                        }
                        break;
                    case 1:
                        startActivity(new Intent(HomeActivity.this, PopuActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this, TagViewActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity.this, EmojiRainActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(HomeActivity.this, PickViewActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(HomeActivity.this, MainBoomMenuActivity.class));
                        break;
                    case 6:
//                        startActivity(new Intent(HomeActivity.this, AddDataActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(HomeActivity.this, WindowMgrActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(HomeActivity.this, LottieDemoActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(HomeActivity.this, swipeCaptchaActivity.class));
                        break;
                    case 10:
                        startActivity(new Intent(HomeActivity.this, BiliActivity.class));
                        break;
                    case 11:
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        break;
                    case 12:
                        startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void OnItemLongClick(int Pos) {
                Log.e(TAG, "OnItemLongClick: --------------" + Pos);
            }
        });

        refreshLayout = new TwinklingRefreshLayout(this);
//        refreshLayout.setEnableOverScroll(true);//是否允许越界回弹。
//        refreshLayout.setFloatRefresh(true);//支持切换到像SwipeRefreshLayout一样的悬浮刷新模式了
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run: ------下拉刷新");
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run: ------上拉加载");
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);

            }
        });
    }

    /**
     * 对系统按键以及物理按键的操作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                long currentTime = TimeUtil.getCurrentTime();
                if (currentTime - preTime > 2000) {
                    ToastUtil.show(getResources().getString(R.string.msg_exit_tow_click_app));
                    preTime = currentTime;
                    return true;
                } else {
                    AppManager.getAppManager().appExit(HomeActivity.this);
                    return false;
                }
        }
        return false;
    }


}
