package com.fmtech.fmdianping.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/13 10:48
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/13 10:48  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class FMEnvironment {
    private static String buildNumber;
    private static boolean buildNumberInited;
    private static String efteVersion;
    private static String imei;
    private static String mapiUserAgent;
    private static String oldUdid;
    private static boolean oldUdidInited;
    private static PackageInfo packageInfo;
    private static String screenInfo;
    private static String source;
    private static String source2;
    private static boolean sourceInited;
    private static String uuid;


	private static PackageInfo pkgInfo(){
		if(null == packageInfo){
			try{
				FMApplication applicationContext = FMApplication._instance();
				packageInfo = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);
			}catch(PackageManager.NameNotFoundException ex){
				ex.printStackTrace();
				return packageInfo;
			}
			
		}
		return packageInfo;
	}
	
	public static int versionCode(){
		return pkgInfo().versionCode;
	}
	
	public static String versionName(){
		return pkgInfo().versionName;
	}
	
}
