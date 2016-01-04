package com.fmtech.empf.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.model.NewsInfo;

import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/1/4 12:52
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2016/1/4 12:52  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class NewsAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private static final int TYPE_ITEM_NORMAL = 0;
    private static final int TYPE_ITEM_WITH_BG = 1;

    private Context mContext;
    private List<NewsInfo> mNewsInfos;
    private NewsItemClickListener mNewsItemClickListener;

    public NewsAdapter(Context context, List<NewsInfo> newsInfos){
        mContext = context;
        mNewsInfos = newsInfos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM_NORMAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_normal_bg, parent, false);
            return new NewsViewHolder(view);
        }else if(viewType == TYPE_ITEM_WITH_BG){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_grey_bg, parent, false);
            return new NewsViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NewsViewHolder){
            NewsInfo newsInfo = mNewsInfos.get(position);
//            ((NewsViewHolder)holder).newsPic;
            ((NewsViewHolder)holder).newsTitle.setText(newsInfo.getNewsTitle());
            ((NewsViewHolder)holder).newsTime.setText(newsInfo.getNewsTime());
            ((NewsViewHolder)holder).newsItem.setTag(position);
            ((NewsViewHolder)holder).newsItem.setOnClickListener(this);
        }

    }

    @Override
    public int getItemCount() {
        return null == mNewsInfos ? 0: mNewsInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if((position %2) == 0){
            return TYPE_ITEM_NORMAL;
        }else{
            return TYPE_ITEM_WITH_BG;
        }
    }

    public void setNewsItemClickListener(NewsItemClickListener listener){
        mNewsItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_item_news:
                if(null != mNewsItemClickListener){
                    mNewsItemClickListener.onNewsItemClick(view, (Integer)view.getTag());
                }
                break;
        }

    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        public ImageView newsPic;
        public TextView newsTitle;
        public TextView newsTime;
        public LinearLayout newsItem;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newsPic = (ImageView)itemView.findViewById(R.id.iv_news_pic);
            newsTitle = (TextView)itemView.findViewById(R.id.tv_news_title);
            newsTime = (TextView)itemView.findViewById(R.id.tv_news_time);
            newsItem = (LinearLayout)itemView.findViewById(R.id.ll_item_news);
        }
    }

    public interface NewsItemClickListener{
        public void onNewsItemClick(View v, int position);
    }
}
