package com.example.administrator.demo.recycleView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

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
import com.example.administrator.demo.util.LogUtil;
import com.example.administrator.demo.util.RVItemTouchHelper;
import com.example.administrator.demo.util.TimeUtil;
import com.example.administrator.demo.util.ToastUtil;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 作者：LSH on 2016/12/2 0002 11:12
 */
public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";

    private RvAdapter rvAdapter;
    private List<HomeListBean> list;

    private TwinklingRefreshLayout refreshLayout;
    private long preTime;

    private RVItemTouchHelper itemTouchHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //导航栏  底部  防止被虚拟按键遮住
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initDate();
        initView();
    }

    public void initDate() {
        list = new ArrayList<>();
        list.add(new HomeListBean("切换布局", 0));
        list.add(new HomeListBean("不同位置的popuwindow", 1));
        list.add(new HomeListBean("TagView流式标签", 2));
        list.add(new HomeListBean("神奇的输入框", 3));
        list.add(new HomeListBean("仿苹果底部弹出筛选框", 4));
        list.add(new HomeListBean("神奇的弹出菜单", 5));
        list.add(new HomeListBean("数据存储Realm", 6));
        list.add(new HomeListBean("自定义的悬浮层", 7));
        list.add(new HomeListBean("AirBnb的开源动画库", 8));
        list.add(new HomeListBean("仿斗鱼滑动验证码", 9));
        list.add(new HomeListBean("b站开源直播以及弹幕", 10));
        list.add(new HomeListBean("仿通讯录", 11));
        list.add(new HomeListBean("空布局emptyLayout", 12));


//        for (int i = 'A'; i < 'Z'; i++) {
//            HomeListBean bean = new HomeListBean((char) i);
//        }
    }

    public void initView() {

        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        //设置rv布局方式
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
                switch (list.get(Pos).getPos()) {
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
                LogUtil.e(TAG, "OnItemLongClick: --------------" + Pos);
                itemTouchHelper.startDrag(rv.getChildViewHolder(rv.getChildAt(Pos)));
            }
        });

        AppBarLayout bar = (AppBarLayout) findViewById(R.id.bar);
        bar.setBackgroundColor(getResources().getColor(R.color.blue));
        //添加滑动偏移监听
        bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshLayout.setEnableRefresh(true);
                    refreshLayout.setEnableOverScroll(false);
                } else {
                    refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableOverScroll(false);
                }
            }
        });

        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
        //更换默认head
        ProgressLayout header = new ProgressLayout(this);
        refreshLayout.setHeaderView(header);
        refreshLayout.setHeaderHeight(140);
        refreshLayout.setMaxHeadHeight(240);
        //设置coordinate以后设置需要刷新的view
        refreshLayout.setTargetView(rv);

        refreshLayout.setEnableOverScroll(true);//是否允许越界回弹
        refreshLayout.setFloatRefresh(true);//支持切换到像SwipeRefreshLayout一样的悬浮刷新模式
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e(TAG, "run: ------下拉刷新");
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
                        LogUtil.e(TAG, "run: ------上拉加载");
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);

            }
        });

        //处理RV移动item移动操作
        itemTouchHelper = new RVItemTouchHelper(new ItemTouchHelper.Callback() {
            int dragFlags;//设定的拖拽方向
            int swipFlags;//设定的能滑动方向

            //设置处理rv的item是否滑动拖动以及移动操作的方向
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //判断rv布局管理器数据
                if (rv.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                    swipFlags = 0;//不滑动
                }
                return makeMovementFlags(dragFlags, swipFlags);
            }

            //item拖动过程中调用
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {

                int oldPosition = viewHolder.getAdapterPosition();//旧的位置
                int newPosition = viewHolder1.getAdapterPosition();//新的位置
                if (oldPosition < newPosition) {
                    for (int i = oldPosition; i < newPosition; i++) {
                        Collections.swap(list, i, i + 1);
                    }
                } else {
                    for (int i = oldPosition; i > newPosition; i--) {
                        Collections.swap(list, i, i - 1);
                    }
                }
                rvAdapter.notifyDataSetChanged();
                return true;
            }

            //滑动消失后调用
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

            }

            //打开长按效果
//            @Override
//            public boolean isLongPressDragEnabled() {
//                LogUtil.e("我是长按");
//                return false;
//            }

            //最后要刷新适配器
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

                LogUtil.e("测试有没有长按选中--" + actionState);
            }
        });
        itemTouchHelper.attachToRecyclerView(rv);


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
