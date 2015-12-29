package com.fmtech.empf.ui.navigationmanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/28 09:30
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/28 09:30  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class NavigationState implements Parcelable {

    public static final Parcelable.Creator<NavigationState> CREATOR = new Parcelable.Creator() {
        @Override
        public NavigationState createFromParcel(Parcel source) {
            return new NavigationState(source);
        }

        @Override
        public NavigationState[] newArray(int size) {
            return new NavigationState[size];
        }
    };

    public final String backstackName;
    public boolean canTriggerSearchSurvey;
    public int drawerIconStateSwitchType;
    public boolean isActionBarOverlayEnabled;
    public boolean isStatusBarOverlayEnabled;
    public final int pageType;
    public final String url;


    private NavigationState(Parcel source){
        backstackName = source.readString();
        pageType = source.readInt();
        url = source.readString();
    }

    public NavigationState(int pageType, String backstackName, String url){
        this.backstackName = backstackName;
        this.pageType = pageType;
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backstackName);
        dest.writeInt(pageType);
        dest.writeString(url);
    }

    public String toString(){
        return "[type: " + this.pageType + ", name: " + this.backstackName + "]";
    }
}
