package com.fmtech.empf.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.model.NewsInfo;
import com.fmtech.empf.ui.adapter.NewsAdapter;
import com.fmtech.empf.ui.decorator.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/29 13:42
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/29 13:42  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class NewsFragment extends PageFragment{

    private RecyclerView mRecyclerList;
    private List<NewsInfo> mNewsInfos = new ArrayList<NewsInfo>();;
    private NewsAdapter mNewsAdapter;

    public static NewsFragment newInstance(){
        NewsFragment newsFragment = new NewsFragment();
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initData();
        mRecyclerList = (RecyclerView)view.findViewById(R.id.rv_news);
        mRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsAdapter = new NewsAdapter(getActivity(), mNewsInfos);
        mRecyclerList.setAdapter(mNewsAdapter);
        mRecyclerList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    private void initData(){
        for(int i=0; i<10; i++){
            NewsInfo newsInfo = new NewsInfo("Praesent volutpat eros neqe\n" +
                    "Morbi vel ligula finibus\n" +
                    "finibus augue...", "", "09/12/2015 | 15:00");

            mNewsInfos.add(newsInfo);
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void rebindViews() {

    }

    @Override
    public void requestData() {

    }
}
