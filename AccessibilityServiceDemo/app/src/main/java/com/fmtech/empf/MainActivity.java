package com.fmtech.empf;

import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.fmtech.empf.image.BitmapLoader;
import com.fmtech.empf.ui.activities.BaseActivity;
import com.fmtech.empf.ui.activities.LoginActivity;
import com.fmtech.empf.ui.component.MPFADialog;
import com.fmtech.empf.ui.component.actionbar.ActionBarController;
import com.fmtech.empf.ui.component.actionbar.ActionBarHelper;
import com.fmtech.empf.ui.component.drawer.DrawerAdapter;
import com.fmtech.empf.ui.component.drawer.FMDrawerLayout;
import com.fmtech.empf.ui.fragments.HomeFragment;
import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.fragments.PageFragment;
import com.fmtech.empf.ui.fragments.PageFragmentHost;
import com.fmtech.empf.ui.navigationmanager.NavigationManager;

public class MainActivity extends BaseActivity implements ActionBarController, PageFragmentHost {

    public int MY_DATA_CHECK_CODE = 0;
    private int mClickCount = 0;
    private MPFADialog mDialog;
    private Toolbar mToolbar;
    private FMDrawerLayout mDrawerLayout;
    public boolean mStateSaved;
    public NavigationManager mNavigationManager;
    protected ActionBarHelper mActionBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // check for TTS data
//        Intent checkTTSIntent = new Intent();
//        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        initNavigationManager();
        initActionBarHelper();
        initViews();
        initActionBar();
//        setUpFragments();
    }

    private void initNavigationManager() {
        mNavigationManager = new NavigationManager(this);
    }

    private void initActionBarHelper() {
        mActionBarHelper = new ActionBarHelper(mNavigationManager, this, this);
    }

    protected void initViews() {
        mDrawerLayout = (FMDrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout.mNavigationManager = mNavigationManager;
        mDrawerLayout.mDrawerAdapter = new DrawerAdapter(this, mDrawerLayout, mDrawerLayout.mDrawerList, mDrawerLayout);
        mDrawerLayout.mDrawerList.setAdapter(mDrawerLayout.mDrawerAdapter);
        mDrawerLayout.refesh();
    }

    protected void initActionBar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white);
        ab.setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void setUpFragments() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_frame, new HomeFragment(), "HOME_FRAGMENT")
                .addToBackStack(null)
                .commit();
//        mNavigationManager.showPage(FragmentConfig.FRAGMENT_HOME, null, HomeFragment.newInstance(), false, new View[0]);
    }

    public void toLogin(View view) {
        switch (mClickCount) {
            case 0:
                showMaintanceMessage();
                mClickCount++;
                break;
            case 1:
                toLoginActivity();
                break;
        }
    }

    private void showMaintanceMessage() {
        mDialog = new MPFADialog(MainActivity.this, "Maintenance", "The system is under maintenance/down, please try again later,thanks.", "OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void toLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
//        finish();
    }

    @Override
    public void onBackPressed() {
        //TODO
        if (mDrawerLayout.isDrawerOpen()) {
            mDrawerLayout.closeDrawer();
            return;
        }
        PageFragment activePage = this.mNavigationManager.getActivePage();
        if (((null != activePage) && (activePage.onBackPressed())) || (mNavigationManager.goBack())) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mNavigationManager.mActivity = null;
        super.onDestroy();
    }

    /***************
     * Methods implements from ActionBarController
     ****************/
    @Override
    public void disableActionBarOverlay() {

    }

    @Override
    public void disableStatusBarOverlay() {

    }

    @Override
    public void enableActionBarOverlay() {

    }

    @Override
    public void enableStatusBarOverlay() {

    }

    @Override
    public void enterActionBarSearchMode() {

    }

    @Override
    public void exitActionBarSearchMode() {

    }

    @Override
    public void exitActionBarSectionExpandedMode() {

    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public void setActionBarSearchModeListener(ActionBarSearchModeListener actionBarSearchModeListener) {

    }

    @Override
    public void setHomeAsUpIndicator(int param) {

    }

    /***************
     * Methods implements from PageFragmentHost
     ****************/
    @Override
    public ActionBarController getActionBarController() {
        return null;
    }

    @Override
    public BitmapLoader getBitmapLoader() {
        return null;
    }

    @Override
    public NavigationManager getNavigationManager() {
        return null;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void overrideSearchBoxWidth(int paramInt) {

    }

    @Override
    public void showErrorDialog(String paramString1, String paramString2, boolean paramBoolean) {

    }

    @Override
    public void switchToRegularActionBar() {

    }

    @Override
    public void updateActionBarTitle(String titleName) {
        mActionBarHelper.updateDefaultTitle(titleName);
    }

    @Override
    public void updateCurrentBackendId(int paramInt, boolean paramBoolean) {

    }
}
