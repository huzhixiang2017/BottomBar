package com.frank.chat;

import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.frank.bottombar.AlphaRadioGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2016/12/14.
 */

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private AlphaRadioGroup alphaRadioGroup;
    private Toolbar toolbar;
    private String[] title = new String[]{
            ChatApplication.getInstance().getResources().getString(R.string.message),
            ChatApplication.getInstance().getResources().getString(R.string.contact),
            ChatApplication.getInstance().getResources().getString(R.string.discover),
            ChatApplication.getInstance().getResources().getString(R.string.about_me)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        registerListener();

        alphaRadioGroup.showDot(1);//show the dot on bottomBar
        alphaRadioGroup.showDot(3);
        alphaRadioGroup.showUnreadCount(0, 9);//show the unreadCount on bottomBar
        alphaRadioGroup.showUnreadCount(2, 99);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                alphaRadioGroup.hideUnreadCount(2);//hide the unreadCount after show it
                SystemClock.sleep(2000);
                alphaRadioGroup.hideDot(3);//hide the dot on bottomBar after show it
            }
        }).start();

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        alphaRadioGroup = (AlphaRadioGroup) findViewById(R.id.radio_group);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initData(){
        List<TestFragment> list = new ArrayList<>();
        for (int i = 0; i < alphaRadioGroup.getChildCount(); i++) {
            TestFragment fragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TestFragment.CONTENT, title[i]);
            fragment.setArguments(bundle);
            list.add(fragment);
        }

        viewPager.setAdapter(new ChatPagerAdapter(getSupportFragmentManager(), list));
        alphaRadioGroup.setViewPager(viewPager);
    }

    private void registerListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class ChatPagerAdapter extends FragmentPagerAdapter {

        List<TestFragment> mData;

        ChatPagerAdapter(FragmentManager fm, List<TestFragment> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }
}
