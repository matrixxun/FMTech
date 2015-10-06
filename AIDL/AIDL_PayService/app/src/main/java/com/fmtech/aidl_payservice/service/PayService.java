package com.fmtech.aidl_payservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.fmtech.aidl_payservice.IPayService;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/10/6 22:11
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/10/6 22:11  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class PayService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("-------Payservice created.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("-------Payservice onBind.");
        return new MyBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void pay(){
        System.out.println("-------Remote Service, paying money for game...\n" +
                "-------Transaction processed Successfully!");
    }

    class MyBinder extends IPayService.Stub{

        @Override
        public void callMethodInService() throws RemoteException {
            pay();
        }
    }
}
