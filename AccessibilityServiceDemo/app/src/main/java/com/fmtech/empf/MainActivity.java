package com.fmtech.empf;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.fmtech.empf.ui.activities.LoginActivity;
import com.fmtech.empf.ui.component.MPFADialog;
import com.fmtech.empf.ui.fragments.HomeFragment;
import com.fmtech.empf.ui.fragments.LoginSignupFragment;
import com.fmtech.accessibilityservicedemo.R;

public class MainActivity extends AppCompatActivity {

    public int MY_DATA_CHECK_CODE = 0;
    private int mClickCount = 0;
    private MPFADialog mDialog;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check for TTS data
//        Intent checkTTSIntent = new Intent();
//        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        initViews();
        initActionBar();
        setUpFragments();
    }

    protected void initViews(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    protected void initActionBar() {
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white);
        ab.setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }
    private void setUpFragments(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_frame, new HomeFragment(), "HOME_FRAGMENT")
                .commit();
    }

    public void toLogin(View view){
        switch (mClickCount){
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
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
