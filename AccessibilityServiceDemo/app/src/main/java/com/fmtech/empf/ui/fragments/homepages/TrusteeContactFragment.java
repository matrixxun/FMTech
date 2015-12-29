package com.fmtech.empf.ui.fragments.homepages;

import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.fragments.PageFragment;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/29 14:52
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/29 14:52  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class TrusteeContactFragment extends PageFragment{

    public static TrusteeContactFragment newInstance(){
        TrusteeContactFragment trusteeContactFragment= new TrusteeContactFragment();
        return trusteeContactFragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.pager_trustee_contact;
    }

    @Override
    public void rebindViews() {

    }

    @Override
    public void requestData() {

    }
}
