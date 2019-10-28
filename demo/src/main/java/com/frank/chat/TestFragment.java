package com.frank.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by frank on 2016/12/14.
 */

public class TestFragment extends Fragment {

    public final static String CONTENT = "content";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        String content = getArguments().getString(CONTENT);
        TextView textView = (TextView) view.findViewById(R.id.tv_content);
        textView.setText(content);
        return view;
    }
}
