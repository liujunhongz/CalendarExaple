package com.codbking.calendar.exaple;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarUtil;
import com.codbking.calendar.CalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XiaomiActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.calendarDateView)
    CalendarDateView mCalendarDateView;
    @BindView(R.id.list)
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaomi);
        ButterKnife.bind(this);
        initView();
        initList();
    }

    private void initList() {
        mList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(XiaomiActivity.this).inflate(android.R.layout.simple_list_item_1, null);
                }

                TextView textView = (TextView) convertView;
                textView.setText("position:" + position);

                return convertView;
            }
        });
    }

    final List<String> hastodoList = new ArrayList<>();

    private void initView() {
        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {

                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_xiaomi, null);
                }

                TextView chinaText = (TextView) convertView.findViewById(R.id.chinaText);
                TextView text = (TextView) convertView.findViewById(R.id.text);

                text.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    text.setTextColor(0xff9299a1);
                } else {
                    text.setTextColor(0xff444444);
                }
                chinaText.setText(bean.chinaDay);
                if (hastodoList.contains(bean.toServerFormat())) {       // 添加条件
                    Drawable bottomDrawable = getResources().getDrawable(R.drawable.icon_point_normal);
                    bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
                    chinaText.setCompoundDrawables(null, null, null, bottomDrawable);
                } else {
                    chinaText.setCompoundDrawables(null, null, null, null);
                }
                return convertView;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                hastodoList.add("2017-09-08");
                hastodoList.add("2017-09-09");
                hastodoList.add("2017-09-28");
                hastodoList.add("2017-10-08");
                hastodoList.add("2017-10-11");
                hastodoList.add("2017-11-20");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(XiaomiActivity.this, "开始更新数据", Toast.LENGTH_SHORT).show();
                        mCalendarDateView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }).start();

        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                mTitle.setText(bean.year + "/" + bean.moth + "/" + bean.day);
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText(data[0] + "/" + data[1] + "/" + data[2]);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
