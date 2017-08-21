package com.example.administrator.demo.EmptyLoadRetryDemo.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.demo.EmptyLoadRetryDemo.LoadingAndRetryManager;
import com.example.administrator.demo.R;

import java.util.Arrays;
import java.util.List;

public class CategoryActivity extends AppCompatActivity
{
    private ListView mListView;
    private List<String> mDatas = Arrays.asList("LoadingAndRetry in Activity", "LoadingAndRetry in Fragment", "LoadingAndRetry in Any View");

    private Class[] mClazz = new Class[]{MainActivity.class, FragmentTestActivity.class,AnyViewTestActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;

        mListView = (ListView) findViewById(R.id.id_listview_category);

        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_category, R.id.id_title, mDatas));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (position + 1 > mClazz.length) return;
                Intent intent = new Intent(CategoryActivity.this, mClazz[position]);
                startActivity(intent);
            }
        });

    }

}
