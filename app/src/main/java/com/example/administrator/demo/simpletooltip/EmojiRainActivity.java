package com.example.administrator.demo.simpletooltip;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.luolc.emojirain.EmojiRainLayout;

import me.james.biuedittext.BiuEditText;

/*
*类似于微信表情雨效果
*https://github.com/Luolc/EmojiRain/blob/master/README-cn.md*/

public class EmojiRainActivity extends Activity {

    private EmojiRainLayout mContainer;
    private TextView tv;
    private BiuEditText et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_rain);
        initDate();
        initView();
    }

    public void initDate() {

    }

    public void initView() {
        // bind view
        mContainer = (EmojiRainLayout) findViewById(R.id.group_emoji_container);
        tv = (TextView) findViewById(R.id.tv);
        et = (BiuEditText) findViewById(R.id.et);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv.setText(et.getText().toString());
                if (et.getText().toString().equals("圣诞快乐")) {
                    mContainer.startDropping();
                }
            }
        });
        // add emoji sources
        mContainer.addEmoji(R.drawable.emoji_1_3);
        mContainer.addEmoji(R.drawable.emoji_2_3);
        mContainer.addEmoji(R.drawable.emoji_3_3);
        mContainer.addEmoji(R.drawable.emoji_4_3);
        mContainer.addEmoji(R.drawable.emoji_5_3);

        // set emojis per flow, default 6
        mContainer.setPer(10);

        // set total duration in milliseconds, default 8000
        mContainer.setDuration(7200);

        // set average drop duration in milliseconds, default 2400
        mContainer.setDropDuration(2400);

        // set drop frequency in milliseconds, default 500
        mContainer.setDropFrequency(500);
    }
}
