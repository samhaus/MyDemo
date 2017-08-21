package com.example.administrator.demo.indexListview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.demo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SideBar sideBar;
    private ArrayList<User> list;
    private List<String> etList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongxunlu);
        initData();
        initView();
    }

    private void initView() {
        SortAdapter adapter = new SortAdapter(this, list);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        sideBar = (SideBar) findViewById(R.id.side_bar);
        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < list.size(); i++) {
                    if (selectStr.equalsIgnoreCase(list.get(i).getFirstLetter())) {
                        listView.setSelection(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });
        AutoCompleteTextView et = (AutoCompleteTextView) findViewById(R.id.et);
        //第一种系统默认的样式来设置自动完成编辑框
        ArrayAdapter<String> etAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, etList);

        //第二种自定义的样式的自动完成编辑框，第二个参数类似下拉框类似的联想框样式，第三个参数是里面显示联想字段的textView的id，第四个为联想字段集合数组
        //如果需要输入文字在联想列表变红之类的  可以使用本自定义的样式
//        ArrayAdapter etAdapter2 = new ArrayAdapter(this, R.layout.layout, R.id.text1, list);

        et.setAdapter(etAdapter);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new User("老王"));
        list.add(new User("球总"));
        list.add(new User("通狗"));
        list.add(new User("俞总"));
        list.add(new User("DJ"));
        list.add(new User("李善皓"));
        list.add(new User("亳州")); // 亳[bó]属于不常见的二级汉字
        list.add(new User("大娃"));
        list.add(new User("二娃"));
        list.add(new User("三娃"));
        list.add(new User("四娃"));
        list.add(new User("五娃"));
        list.add(new User("六娃"));
        list.add(new User("七娃"));
        list.add(new User("喜羊羊"));
        list.add(new User("美羊羊"));
        list.add(new User("懒羊羊"));
        list.add(new User("沸羊羊"));
        list.add(new User("暖羊羊"));
        list.add(new User("慢羊羊"));
        list.add(new User("灰太狼"));
        list.add(new User("红太狼"));
        list.add(new User("孙悟空"));
        list.add(new User("黑猫警长"));
        list.add(new User("舒克"));
        list.add(new User("贝塔"));
        list.add(new User("海尔"));
        list.add(new User("阿凡提"));
        list.add(new User("邋遢大王"));
        list.add(new User("哪吒"));
        list.add(new User("没头脑"));
        list.add(new User("不高兴"));
        list.add(new User("蓝皮鼠"));
        list.add(new User("大脸猫"));
        list.add(new User("大头儿子"));
        list.add(new User("小头爸爸"));
        list.add(new User("蓝猫"));
        list.add(new User("淘气"));
        list.add(new User("叶峰"));
        list.add(new User("楚天歌"));
        list.add(new User("江流儿"));
        list.add(new User("Tom"));
        list.add(new User("Jerry"));
        list.add(new User("12345"));
        list.add(new User("54321"));
        list.add(new User("_(:з」∠)_"));
        list.add(new User("……%￥#￥%#"));
        Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法

        etList = new ArrayList<>();
        //从list取出数据源给搜索输入框
        for (User bean : list) {
            etList.add(bean.getName());
        }
    }

    //输入搜索框的适配器（即 搜索框联想下拉列表的适配器）
    public class myEtAdapter extends BaseAdapter {
        private Context context;
        private List list;

        public void myEtAdapter(Context context, List list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return null;
        }
    }
}
