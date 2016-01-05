package com.fmtech.empf.ui.component.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fmtech.accessibilityservicedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/29 08:15
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/29 08:15  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class DrawerAdapter extends BaseAdapter{

    public List<FMDrawerLayout.DrawerAction> mDrawerActions = new ArrayList<FMDrawerLayout.DrawerAction>();
    private Context mContext;
    private LayoutInflater mInflater;
    private ListView mListView;
    private FMDrawerLayout mDrawerLayout;
    private DrawerContentClickListener mDrawerContentClickListener;
    private int mCurrActionIndex;

    public DrawerAdapter(Context context,DrawerContentClickListener drawerContentClickListener,ListView listView, FMDrawerLayout drawerLayout){
        mContext = context;
        mDrawerContentClickListener = drawerContentClickListener;
        mInflater = LayoutInflater.from(mContext);
        mListView = listView;
        mDrawerLayout = drawerLayout;
        mCurrActionIndex = 0;
    }

    @Override
    public int getCount() {
        return null == mDrawerActions ? 0:mDrawerActions.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return mDrawerActions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(position == 0){
            return mInflater.inflate(R.layout.drawer_action_top_spacing, parent, false);
        }else {
            FMDrawerLayout.DrawerAction drawerAction = mDrawerActions.get(position - 1);
            TextView actionTextView;
            if (drawerAction.isActive) {
                actionTextView = (TextView) mInflater.inflate(R.layout.drawer_action_active, parent, false);
            } else {
                actionTextView = (TextView) mInflater.inflate(R.layout.drawer_action_regular, parent, false);
            }
            actionTextView.setText(drawerAction.actionText);
            actionTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mDrawerContentClickListener) {
                        if(mCurrActionIndex != position - 1){
                            mDrawerActions.get(mCurrActionIndex).isActive = !mDrawerActions.get(mCurrActionIndex).isActive;
                            mDrawerContentClickListener.onDrawActionClicked(mDrawerActions.get(position - 1));
                            mCurrActionIndex = position - 1;
                            mDrawerActions.get(mCurrActionIndex).isActive = !mDrawerActions.get(mCurrActionIndex).isActive;
                            notifyDataSetChanged();
                        }
                    }
                    mDrawerLayout.closeDrawer();
                }
            });
//        setPaddingStart(localTextView, localResources.getDimensionPixelSize(R.dimen.play_drawer_item_left_padding));
            return actionTextView;
        }
    }


}
