package com.fmtech.fmdianping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/8 15:59
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/8 15:59  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class FMSharedPres implements SharedPreferences{
    private static final String PREFS_NAME = "FM_PREFS";

    private static SharedPreferences mSharedPref;

    private static FMSharedPres instance;

    public static void init(Context context){
        mSharedPref = context.getSharedPreferences(PREFS_NAME , Context.MODE_PRIVATE);
        instance = new FMSharedPres();
    }

    private FMSharedPres(){

    }

    public static FMSharedPres shareInstance(){
        return instance;
    }

    @Override
    public Map<String, ?> getAll() {
        Map<String, ?> cStore = mSharedPref.getAll();
        Map dResult = new HashMap();
        Set<String> ckeys = cStore.keySet();
        for(String ckey : ckeys){
            if(cStore.get(ckey) instanceof String){
                String cvalue = (String) cStore.get(ckey);
                dResult.put(ckey, cvalue);
            }else{
                dResult.put(ckey, ckey);
            }

        }
        return dResult;
    }

    @Nullable
    @Override
    public String getString(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return mSharedPref.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return mSharedPref.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return mSharedPref.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return mSharedPref.contains(key);
    }

    @Override
    public Editor edit() {
        return mSharedPref.edit();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mSharedPref.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mSharedPref.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public void apply() {
        edit().apply();
    }

    public Editor clear() {
        return edit().clear();
    }

    public boolean commit() {
        return edit().commit();
    }

    public Editor putBoolean(String key, boolean value) {
        return edit().putBoolean(key, value);
    }

    public Editor putFloat(String key, float value) {
        return edit().putFloat(key, value);
    }

    public Editor putInt(String key, int value) {
        return edit().putInt(key, value);
    }

    public Editor putLong(String key, long value) {
        return edit().putLong(key, value);
    }

    public Editor putString(String key, String value) {
        return edit().putString(key, value);
    }

    public Editor putStringSet(String key, Set<String> values) {
        return edit().putStringSet(key, values);
    }

    public Editor remove(String key) {
        return edit().remove(key);
    }

}
