package com.google.android.finsky.billing.switchfamilyinstrument;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.LoggingFragmentActivity;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.protos.FamilyFopRequest;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.utils.ParcelableProtoArray;
import java.util.ArrayList;

public class SwitchFamilyInstrumentActivity
  extends LoggingFragmentActivity
  implements AdapterView.OnItemClickListener, SimpleAlertDialog.Listener, SidecarFragment.Listener, ButtonBar.ClickListener
{
  private ButtonBar mButtonBar;
  private View mChooserContentView;
  private Instrument[] mInstruments;
  private ListView mListView;
  private View mLoadingIndicatorView;
  private SwitchFamilyInstrumentSidecar mSidecar;
  
  public static Intent createIntent(String paramString, Instrument[] paramArrayOfInstrument)
  {
    Intent localIntent = new Intent(FinskyApp.get(), SwitchFamilyInstrumentActivity.class);
    localIntent.putExtra("SwitchFamilyInstrumentActivity.instruments", ParcelableProtoArray.forProtoArray(paramArrayOfInstrument));
    LoggingFragmentActivity.addAccountNameExtra(localIntent, paramString);
    return localIntent;
  }
  
  private void onDialogDismissed(int paramInt)
  {
    setResult(paramInt);
    finish();
  }
  
  private void showInstrumentsChoice()
  {
    this.mLoadingIndicatorView.setVisibility(4);
    this.mChooserContentView.setVisibility(0);
  }
  
  private void syncPositiveButton()
  {
    if (this.mListView.getCheckedItemPosition() != -1) {}
    for (boolean bool = true;; bool = false)
    {
      this.mButtonBar.setPositiveButtonEnabled(bool);
      return;
    }
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 5200;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968630);
    this.mListView = ((ListView)findViewById(2131755279));
    this.mLoadingIndicatorView = findViewById(2131755277);
    this.mChooserContentView = findViewById(2131755278);
    this.mButtonBar = ((ButtonBar)findViewById(2131755300));
    this.mButtonBar.setPositiveButtonTitle(2131362418);
    this.mButtonBar.setNegativeButtonTitle(2131361915);
    this.mButtonBar.setClickListener(this);
    this.mInstruments = ((Instrument[])ParcelableProtoArray.getProtoArrayFromIntent(getIntent(), "SwitchFamilyInstrumentActivity.instruments"));
    ArrayList localArrayList = new ArrayList(this.mInstruments.length);
    int i = -1;
    for (int j = 0; j < this.mInstruments.length; j++)
    {
      if (this.mInstruments[j].familyInfo != null) {
        i = j;
      }
      this.mEventLog.logPathImpression(0L, 802, this.mInstruments[j].serverLogsCookie, this);
      localArrayList.add(j, this.mInstruments[j].displayTitle);
    }
    ArrayAdapter localArrayAdapter = new ArrayAdapter(this, 17367055, localArrayList);
    this.mListView.setAdapter(localArrayAdapter);
    this.mListView.setItemsCanFocus(false);
    this.mListView.setChoiceMode(1);
    this.mListView.setOnItemClickListener(this);
    if (i != -1) {
      this.mListView.setItemChecked(i, true);
    }
    syncPositiveButton();
    showInstrumentsChoice();
    if (paramBundle == null)
    {
      this.mSidecar = SwitchFamilyInstrumentSidecar.newInstance(this.mAccountName);
      getSupportFragmentManager().beginTransaction().add(this.mSidecar, "SwitchFamilyInstrumentActivity.sidecar").commit();
      return;
    }
    this.mSidecar = ((SwitchFamilyInstrumentSidecar)getSupportFragmentManager().findFragmentByTag("SwitchFamilyInstrumentActivity.sidecar"));
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    syncPositiveButton();
  }
  
  public final void onNegativeButtonClick()
  {
    onDialogDismissed(0);
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPositiveButtonClick()
  {
    Instrument localInstrument = this.mInstruments[this.mListView.getCheckedItemPosition()];
    this.mEventLog.logClickEvent(5201, localInstrument.serverLogsCookie, this);
    if (localInstrument.familyInfo == null)
    {
      SwitchFamilyInstrumentSidecar localSwitchFamilyInstrumentSidecar = this.mSidecar;
      BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(345);
      FinskyApp.get().getEventLogger(localSwitchFamilyInstrumentSidecar.mDfeApi.getAccountName()).sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
      FamilyFopRequest localFamilyFopRequest = new FamilyFopRequest();
      localFamilyFopRequest.familyFop = localInstrument;
      localSwitchFamilyInstrumentSidecar.mDfeApi.changeFamilyInstrument(localFamilyFopRequest, localSwitchFamilyInstrumentSidecar, localSwitchFamilyInstrumentSidecar);
      localSwitchFamilyInstrumentSidecar.setState(1, 0);
      return;
    }
    onDialogDismissed(0);
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 0) {
      showInstrumentsChoice();
    }
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    syncPositiveButton();
  }
  
  protected void onStart()
  {
    super.onStart();
    this.mSidecar.setListener(this);
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    int i = paramSidecarFragment.mState;
    if (i == 2) {
      onDialogDismissed(-1);
    }
    do
    {
      return;
      if (i == 1)
      {
        this.mChooserContentView.setVisibility(4);
        this.mLoadingIndicatorView.setVisibility(0);
        return;
      }
    } while (paramSidecarFragment.mState != 3);
    String str = this.mSidecar.mErrorMessage;
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessage(str).setPositiveId(2131362418).setCallback(null, 0, null);
    localBuilder.build().show(getSupportFragmentManager(), "SwitchFamilyInstrumentActivity.error_dialog");
  }
  
  protected void onStop()
  {
    this.mSidecar.setListener(null);
    super.onStop();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.switchfamilyinstrument.SwitchFamilyInstrumentActivity
 * JD-Core Version:    0.7.0.1
 */