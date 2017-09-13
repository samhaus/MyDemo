package com.example.administrator.demo.recycleView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.administrator.demo.BiliDemo.BiliActivity;
import com.example.administrator.demo.VRPlayerDemo.activity.VRListActivity;
import com.example.administrator.demo.activity.BatteryListenActivity;
import com.example.administrator.demo.bean.CommonRequestBean;
import com.example.administrator.demo.bean.TestBean;
import com.example.administrator.demo.emptyLoadRetryDemo.test.CategoryActivity;
import com.example.administrator.demo.activity.PickViewActivity;
import com.example.administrator.demo.activity.PopuActivity;
import com.example.administrator.demo.R;
import com.example.administrator.demo.activity.TagViewActivity;
import com.example.administrator.demo.WindowMgrDemo.WindowMgrActivity;
import com.example.administrator.demo.base.BaseActivity;
import com.example.administrator.demo.boomMenu.MainBoomMenuActivity;
import com.example.administrator.demo.indexListview.MainActivity;
import com.example.administrator.demo.lottieDemo.LottieDemoActivity;
import com.example.administrator.demo.network.BaseObserver;
import com.example.administrator.demo.network.MonkeyNet;
import com.example.administrator.demo.network.RetrofitManager;
import com.example.administrator.demo.network.RxSchedulers;
import com.example.administrator.demo.recycleView.RvAdapter.ItemClickListener;
import com.example.administrator.demo.simpletooltip.EmojiRainActivity;
import com.example.administrator.demo.swipeCaptchaDemo.swipeCaptchaActivity;
import com.example.administrator.demo.util.AppConstants;
import com.example.administrator.demo.util.AppManager;
import com.example.administrator.demo.util.LogUtil;
import com.example.administrator.demo.util.RVItemTouchHelper;
import com.example.administrator.demo.util.TimeUtil;
import com.example.administrator.demo.util.ToastUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


/**
 * 作者：LSH on 2016/12/2 0002 11:12
 */
public class HomeActivity extends BaseActivity {

    private RvAdapter rvAdapter;
    private List<HomeListBean> list;

    private TwinklingRefreshLayout refreshLayout;
    private long preTime;

    private RVItemTouchHelper itemTouchHelper;

    //    monkeyNet 测试
    private MonkeyNet monkeyNet;

    @Override
    public int generateLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadUpgradeInfo();
        loadAppInfo();
    }

    private void loadUpgradeInfo() {
//        if (upgradeInfoTv == null)
//            return;

        /***** 获取升级信息 *****/
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();

        if (upgradeInfo == null) {
            LogUtil.e("无升级信息");
            return;
        }

        StringBuilder info = new StringBuilder();
        info.append("id: ").append(upgradeInfo.id).append("\n");
        info.append("标题: ").append(upgradeInfo.title).append("\n");
        info.append("升级说明: ").append(upgradeInfo.newFeature).append("\n");
        info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n");
        info.append("versionName: ").append(upgradeInfo.versionName).append("\n");
        info.append("发布时间: ").append(upgradeInfo.publishTime).append("\n");
        info.append("安装包Md5: ").append(upgradeInfo.apkMd5).append("\n");
        info.append("安装包下载地址: ").append(upgradeInfo.apkUrl).append("\n");
        info.append("安装包大小: ").append(upgradeInfo.fileSize).append("\n");
        info.append("弹窗间隔（ms）: ").append(upgradeInfo.popInterval).append("\n");
        info.append("弹窗次数: ").append(upgradeInfo.popTimes).append("\n");
        info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n");
        info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType);

        LogUtil.e(info.toString());
    }

    private void loadAppInfo() {
        try {
            StringBuilder info = new StringBuilder();
            PackageInfo packageInfo =
                    this.getPackageManager().getPackageInfo(this.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            info.append("appid: ").append(AppConstants.BUGLY_APPID).append(" ")
                    .append("channel: ").append(" ")
                    .append("version: ").append(versionName).append(".").append(versionCode);
            LogUtil.e(info.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initData(Bundle bundle) {
        list = new ArrayList<>();
        list.add(new HomeListBean("切换布局", 0));
        list.add(new HomeListBean("不同位置的PopuWindow", 1));
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
        list.add(new HomeListBean("空布局EmptyLayout", 12));
        list.add(new HomeListBean("Duer OS智能语音体验", 13));
        list.add(new HomeListBean("VR播放器", 14));
        list.add(new HomeListBean("电源分析监听", 15));

//        for (int i = 'A'; i < 'Z'; i++) {
//            list.add(new HomeListBean((char) i + "", i));
//        }
    }

    @Override
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
                        monkeyNet = new MonkeyNet(HomeActivity.this);
                        monkeyNet.initMockNet();
                        monkeyNet.startServer();
                        /***** 检查更新 *****/
//                        Beta.checkUpgrade();
//                        loadUpgradeInfo();

//                        if (rv.getLayoutManager() == layoutManager) {
//                            rv.setLayoutManager(layoutManager2);
//                        } else if (rv.getLayoutManager() == layoutManager2) {
//                            rv.setLayoutManager(layoutManager3);
//                        } else if (rv.getLayoutManager() == layoutManager3) {
//                            rv.setLayoutManager(layoutManager);
//                        }
                        break;
                    case 1:
                        Observable<CommonRequestBean<TestBean>> observable= RetrofitManager.build().getTest("val1","val2");
                        observable.compose(RxSchedulers.<CommonRequestBean<TestBean>>compose()).subscribe(new BaseObserver<TestBean>() {
                            @Override
                            protected void onHandleSuccess(TestBean testBean) {
                                LogUtil.e("testbean==="+testBean.toString());

                            }
                        });
//                        startActivity(new Intent(HomeActivity.this, PopuActivity.class));
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
                    case 13:
//                        startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
                        break;
                    case 14:
                        startActivity(new Intent(HomeActivity.this, VRListActivity.class));
                        break;
                    case 15:
                        startActivity(new Intent(HomeActivity.this, BatteryListenActivity.class));
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void OnItemLongClick(int Pos) {
                LogUtil.e("OnItemLongClick: --------------" + Pos);
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
                        LogUtil.e("run: ------下拉刷新");
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
                        LogUtil.e("run: ------上拉加载");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
