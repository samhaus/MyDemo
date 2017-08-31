package com.example.administrator.demo.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.administrator.demo.R;
import com.example.administrator.demo.simpletooltip.ToolTip;
import com.example.administrator.demo.simpletooltip.ToolTipView;

public class PopuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popu);
        findViewById(R.id.top_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTipView(v, Gravity.RIGHT, "Simple tool tip!",
                        ContextCompat.getColor(PopuActivity.this, R.color.blue));
            }
        });
        findViewById(R.id.top_right_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTipView(v, Gravity.BOTTOM, "It is yet another very simple tool tip!",
                        ContextCompat.getColor(PopuActivity.this, R.color.green));
            }
        });
        findViewById(R.id.central_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTipView(v, Gravity.END, "It is a very simple tool tip in the center!",
                        ContextCompat.getColor(PopuActivity.this, R.color.magenta));
            }
        });
        findViewById(R.id.bottom_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTipView(v, Gravity.TOP, "Tool tip, once more!",
                        ContextCompat.getColor(PopuActivity.this, R.color.maroon));
            }
        });
        findViewById(R.id.bottom_right_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTipView(v, Gravity.LEFT, "Magical tool tip!",
                        ContextCompat.getColor(PopuActivity.this, R.color.navy));
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToolTipViewWithParent((Button) v, Gravity.BOTTOM);
            }
        };
        findViewById(R.id.button1).setOnClickListener(listener);
        findViewById(R.id.button2).setOnClickListener(listener);
        findViewById(R.id.button3).setOnClickListener(listener);
        findViewById(R.id.button4).setOnClickListener(listener);
        findViewById(R.id.button5).setOnClickListener(listener);
        findViewById(R.id.button6).setOnClickListener(listener);
        findViewById(R.id.button7).setOnClickListener(listener);

        showToolTipView(findViewById(R.id.central_button), Gravity.START, "A simple tool tip!",
                ContextCompat.getColor(PopuActivity.this, R.color.magenta), 750L);
    }

    private void showToolTipView(View anchorView, int gravity, CharSequence text, int backgroundColor) {
        showToolTipView(anchorView, gravity, text, backgroundColor, 0L);
    }

    private void showToolTipViewWithParent(final Button anchorView, int gravity) {
        showToolTipView(anchorView, (FrameLayout) findViewById(R.id.tool_tip_view_holder), gravity,
                "Tool tip for " + anchorView.getText(), Color.BLACK, 0L);
    }

    private void showToolTipView(final View anchorView, int gravity, CharSequence text, int backgroundColor, long delay) {
        showToolTipView(anchorView, null, gravity, text, backgroundColor, delay);
    }

    private void showToolTipView(final View anchorView, ViewGroup parentView, int gravity,
                                 CharSequence text, int backgroundColor, long delay) {
        if (anchorView.getTag() != null) {
            ((ToolTipView) anchorView.getTag()).remove();
            anchorView.setTag(null);
            return;
        }

        ToolTip toolTip = createToolTip(text, backgroundColor);
        ToolTipView toolTipView = createToolTipView(toolTip, anchorView, parentView, gravity);
        if (delay > 0L) {
            toolTipView.showDelayed(delay);
        } else {
            toolTipView.show();
        }
        anchorView.setTag(toolTipView);

        toolTipView.setOnToolTipClickedListener(new ToolTipView.OnToolTipClickedListener() {
            @Override
            public void onToolTipClicked(ToolTipView toolTipView) {
                anchorView.setTag(null);
            }
        });
    }

    private ToolTip createToolTip(CharSequence text, int backgroundColor) {
        Resources resources = getResources();
        int padding = resources.getDimensionPixelSize(R.dimen.padding);
        int textSize = resources.getDimensionPixelSize(R.dimen.text_size);
        int radius = resources.getDimensionPixelSize(R.dimen.radius);
        return new ToolTip.Builder()
                .withText(text)
                .withTextColor(Color.WHITE)
                .withTextSize(textSize)
                .withBackgroundColor(backgroundColor)
                .withPadding(padding, padding, padding, padding)
                .withCornerRadius(radius)
                .build();
    }

    private ToolTipView createToolTipView(ToolTip toolTip, View anchorView, ViewGroup parentView, int gravity) {
        return new ToolTipView.Builder(this)
                .withAnchor(anchorView)
                .withParent(parentView)
                .withToolTip(toolTip)
                .withGravity(gravity)
                .build();
    }
}
