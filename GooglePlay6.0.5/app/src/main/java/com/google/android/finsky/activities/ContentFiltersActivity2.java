package com.google.android.finsky.activities;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.model.CachedDfeModel;
import com.google.android.finsky.api.model.DfeContentFilters;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.config.ContentFiltersUtils;
import com.google.android.finsky.config.ContentFiltersUtils.ContentFilterSelection;
import com.google.android.finsky.config.ContentLevel;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.library.AccountsProvider;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.ContentFilters.ContentFilterSettingsResponse;
import com.google.android.finsky.protos.ContentFilters.FilterChoice;
import com.google.android.finsky.protos.ContentFilters.FilterRange;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Lists;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.config.GservicesValue;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ContentFiltersActivity2
  extends AppCompatActivity
  implements View.OnClickListener, Response.ErrorListener, SimpleAlertDialog.Listener, OnDataChangedListener
{
  private Set<Integer> mConfirmationDialogsShown;
  private TextView mContentFilterOnOffText;
  private SwitchCompat mContentFilterOnOffToggle;
  private View mContentFilterOnOffWrapper;
  private View mContentFilterSplashInfoWrapper;
  private View mContentFilterVerticalsWrapper;
  private DfeContentFilters mDfeContentFilters;
  private FinskyEventLog mEventLogger;
  private Bundle mExtraParamsOnConfirmedPin;
  private ViewGroup mFiltersList;
  private FinskyApp mFinskyApp;
  private boolean mHasCompletedPin;
  private View mLoadProgress;
  private FifeImageView mMoreInfoImageView;
  private TextView mMoreInfoText;
  private GenericUiElementNode mNode = new GenericUiElementNode(315, null, null, null);
  private String mOldContentFilterPref;
  private ContentFiltersUtils.ContentFilterSelection[] mSelections;
  private boolean mTemporaryToggleState;
  private TextView mTopInfoText;
  private TextView mTopInfoTitle;
  
  private void applyFilterToggle(boolean paramBoolean)
  {
    toggleFilterOptions(paramBoolean);
    saveAndBroadcast(paramBoolean);
    if (!paramBoolean) {
      FinskyPreferences.contentPin.remove();
    }
    this.mHasCompletedPin = paramBoolean;
  }
  
  private void applySelectedFilterChoice(ContentFilters.FilterChoice paramFilterChoice, ContentFilters.FilterRange paramFilterRange, View paramView)
  {
    if (paramFilterChoice.hasLabelSummary) {
      ((TextView)paramView.findViewById(2131755357)).setText(paramFilterChoice.labelSummary);
    }
    if (paramFilterRange.representChoiceAsToggle) {
      ((CheckBox)paramView.findViewById(2131755356)).performClick();
    }
    this.mSelections = ContentFiltersUtils.setOrAddSelection(this.mSelections, paramFilterRange, paramFilterChoice);
    saveAndBroadcast(this.mContentFilterOnOffToggle.isChecked());
  }
  
  private boolean launchChoiceConfirmationDialog(ContentFilters.FilterRange paramFilterRange, int paramInt1, int paramInt2)
  {
    if (((paramFilterRange.confirmationDialogTitle.isEmpty()) && (paramFilterRange.confirmationDialogContent.isEmpty())) || (this.mConfirmationDialogsShown.contains(Integer.valueOf(paramInt2)))) {
      return false;
    }
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    if (!TextUtils.isEmpty(paramFilterRange.confirmationDialogTitle)) {
      localBuilder.setTitle(paramFilterRange.confirmationDialogTitle);
    }
    if (!TextUtils.isEmpty(paramFilterRange.confirmationDialogContent)) {
      localBuilder.setMessage(paramFilterRange.confirmationDialogContent);
    }
    localBuilder.setEventLog(322, null, -1, -1, null);
    localBuilder.setPositiveId(2131362418).setCanceledOnTouchOutside(true);
    Bundle localBundle = new Bundle();
    localBundle.putInt("ContentFiltersActivity2.selectedChoiceIndex", paramInt1);
    localBundle.putByteArray("ContentFiltersActivity2.filterRange", MessageNano.toByteArray(paramFilterRange));
    localBundle.putInt("ContentFiltersActivity2.filterRangeIndex", paramInt2);
    localBuilder.setCallback(null, 1, localBundle);
    localBuilder.build().show(getSupportFragmentManager(), "ContentFiltersActivity2.confirmationDialog");
    return true;
  }
  
  private void onFilterLineClicked(ContentFilters.FilterRange paramFilterRange, int paramInt)
  {
    View localView = this.mFiltersList.getChildAt(paramInt);
    if (paramFilterRange.representChoiceAsToggle)
    {
      if (((CheckBox)localView.findViewById(2131755356)).isChecked()) {}
      for (int i = 0;; i = 1)
      {
        if (!launchChoiceConfirmationDialog(paramFilterRange, i, paramInt)) {
          applySelectedFilterChoice(paramFilterRange.filterChoice[i], paramFilterRange, localView);
        }
        return;
      }
    }
    byte[] arrayOfByte = MessageNano.toByteArray(paramFilterRange);
    Bundle localBundle1 = new Bundle();
    localBundle1.putByteArray("ContentFiltersActivity2.filterRange", arrayOfByte);
    localBundle1.putInt("ContentFiltersActivity2.filterRangeIndex", paramInt);
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setLayoutId(2130968669).setCallback(null, 2, localBundle1);
    if (paramFilterRange.hasSelectionDialogLabel) {
      localBuilder.setTitle(paramFilterRange.selectionDialogLabel);
    }
    Bundle localBundle2 = new Bundle();
    localBundle2.putString("ContentFiltersDialogView.contentFilterSelections", ContentFiltersUtils.encodeSelections(this.mSelections));
    localBundle2.putByteArray("ContentFiltersDialogView.encodedFilterRange", arrayOfByte);
    localBuilder.setViewConfiguration(localBundle2);
    ContentFiltersSelectionDialog localContentFiltersSelectionDialog = new ContentFiltersSelectionDialog();
    localBuilder.configureDialog(localContentFiltersSelectionDialog);
    localContentFiltersSelectionDialog.show(getSupportFragmentManager(), "ContentFiltersActivity2.selectionDialog");
  }
  
  private void readInstanceState(Bundle paramBundle)
  {
    this.mHasCompletedPin = paramBundle.getBoolean("ContentFiltersActivity2.hasCompletedPin");
    this.mTemporaryToggleState = paramBundle.getBoolean("ContentFiltersActivity2.temporaryToggleState");
    this.mOldContentFilterPref = paramBundle.getString("ContentFiltersActivity2.oldContentFilterPref");
    this.mConfirmationDialogsShown.clear();
    this.mConfirmationDialogsShown.addAll(paramBundle.getIntegerArrayList("ContentFiltersActivity2.shownConfirmationDialogsIndices"));
  }
  
  private void saveAndBroadcast(boolean paramBoolean)
  {
    ContentFiltersUtils.savePreferences(paramBoolean, (ContentFilters.ContentFilterSettingsResponse)this.mDfeContentFilters.mResponse, this.mSelections, FinskyPreferences.contentFilters2Selections, FinskyPreferences.contentFilters2);
    String str = (String)FinskyPreferences.contentFilters2.get();
    Integer localInteger = Integer.valueOf(ContentLevel.importFromSettings(this).getDfeValue());
    Iterator localIterator = this.mFinskyApp.mAccountsProvider.getAccounts().iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      this.mFinskyApp.getDfeApi(localAccount.name).getApiContext().addHeaderForContentFilters(localInteger.intValue(), str);
    }
    sendBroadcast(new Intent("com.google.android.finsky.action.CONTENT_FILTERS_CHANGED"));
    this.mFinskyApp.getDfeApi(null).saveContentFilters(new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.d("Error saving content filters in dfe. %s", new Object[] { paramAnonymousVolleyError });
      }
    });
  }
  
  private void startPinDialog(Bundle paramBundle, boolean paramBoolean)
  {
    String str = (String)FinskyPreferences.contentPin.get();
    if ((TextUtils.isEmpty(str)) || (paramBoolean))
    {
      startActivityForResult(PinEntryDialog.getSetupIntent$19bcc0ce(this, paramBundle), 3);
      return;
    }
    startActivityForResult(PinEntryDialog.getIntent(this, 2131362544, 2131362545, str, paramBundle), 4);
  }
  
  private void toggleFilterOptions(boolean paramBoolean)
  {
    this.mContentFilterOnOffToggle.setChecked(paramBoolean);
    if (paramBoolean)
    {
      this.mContentFilterOnOffText.setText(getString(2131362114));
      this.mContentFilterSplashInfoWrapper.setVisibility(8);
      this.mContentFilterVerticalsWrapper.setVisibility(0);
      return;
    }
    this.mContentFilterOnOffText.setText(getString(2131362095));
    this.mContentFilterSplashInfoWrapper.setVisibility(0);
    this.mContentFilterVerticalsWrapper.setVisibility(8);
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1) {
      return;
    }
    if ((paramInt1 == 4) || (paramInt1 == 3))
    {
      String str = paramIntent.getStringExtra("PinEntryDialog.resultPin");
      if (TextUtils.isEmpty(str))
      {
        FinskyLog.w("Create / confirm PIN result OK but no PIN sent back.", new Object[0]);
        return;
      }
      FinskyPreferences.contentPin.put(str);
      this.mHasCompletedPin = true;
      this.mExtraParamsOnConfirmedPin = paramIntent.getBundleExtra("PinEntryDialog.extraParams");
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed()
  {
    if (this.mDfeContentFilters.mResponse != null) {
      setResult(-1, ContentFiltersUtils.getFilterChoicesAsIntent(((ContentFilters.ContentFilterSettingsResponse)this.mDfeContentFilters.mResponse).filterRange, this.mSelections));
    }
    super.onBackPressed();
  }
  
  public void onClick(View paramView)
  {
    int i = 1;
    if (paramView == this.mContentFilterOnOffWrapper)
    {
      if (!this.mHasCompletedPin) {
        break label38;
      }
      if (this.mContentFilterOnOffToggle.isChecked()) {
        break label33;
      }
    }
    for (;;)
    {
      applyFilterToggle(i);
      return;
      label33:
      i = 0;
    }
    label38:
    Bundle localBundle = new Bundle();
    if (this.mContentFilterOnOffToggle.isChecked())
    {
      localBundle.putInt("ContentFiltersActivity2.onPinSuccess", 2);
      startPinDialog(localBundle, false);
      return;
    }
    localBundle.putInt("ContentFiltersActivity2.onPinSuccess", i);
    startPinDialog(localBundle, i);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968667);
    this.mContentFilterOnOffWrapper = findViewById(2131755341);
    this.mContentFilterOnOffText = ((TextView)findViewById(2131755342));
    this.mContentFilterOnOffToggle = ((SwitchCompat)findViewById(2131755343));
    this.mContentFilterSplashInfoWrapper = findViewById(2131755344);
    this.mContentFilterVerticalsWrapper = findViewById(2131755346);
    this.mTopInfoTitle = ((TextView)findViewById(2131755347));
    this.mTopInfoText = ((TextView)findViewById(2131755348));
    this.mMoreInfoText = ((TextView)findViewById(2131755340));
    this.mMoreInfoImageView = ((FifeImageView)findViewById(2131755345));
    this.mLoadProgress = findViewById(2131755350);
    this.mFiltersList = ((ViewGroup)findViewById(2131755349));
    this.mHasCompletedPin = false;
    this.mTemporaryToggleState = false;
    this.mOldContentFilterPref = null;
    this.mConfirmationDialogsShown = new HashSet();
    if (paramBundle != null)
    {
      readInstanceState(paramBundle);
      this.mFinskyApp = FinskyApp.get();
      this.mEventLogger = this.mFinskyApp.getEventLogger();
      if (paramBundle == null) {
        this.mEventLogger.logPathImpression(0L, this.mNode);
      }
      if ((((String)FinskyPreferences.contentFilters2.get()).isEmpty()) && (!this.mTemporaryToggleState) && (TextUtils.isEmpty((CharSequence)FinskyPreferences.contentPin.get()))) {
        break label355;
      }
    }
    label355:
    for (boolean bool = true;; bool = false)
    {
      this.mContentFilterOnOffToggle.setChecked(bool);
      toggleFilterOptions(bool);
      setResult(0);
      this.mDfeContentFilters = new DfeContentFilters(this.mFinskyApp.getDfeApi(null), this);
      this.mDfeContentFilters.addDataChangedListener(this);
      this.mDfeContentFilters.addErrorListener(this);
      DfeContentFilters localDfeContentFilters = this.mDfeContentFilters;
      localDfeContentFilters.fetchSettingsOverNetwork(localDfeContentFilters, localDfeContentFilters, false);
      this.mSelections = ContentFiltersUtils.getSelectionsFromPref(FinskyPreferences.contentFilters2Selections);
      return;
      this.mOldContentFilterPref = ((String)FinskyPreferences.contentFilters2.get());
      break;
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131820545, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  public final void onDataChanged()
  {
    this.mLoadProgress.setVisibility(8);
    ContentFilters.ContentFilterSettingsResponse localContentFilterSettingsResponse = (ContentFilters.ContentFilterSettingsResponse)this.mDfeContentFilters.mResponse;
    Common.Image localImage = localContentFilterSettingsResponse.tutorialImageFife;
    label98:
    final int i;
    label156:
    label182:
    final ContentFilters.FilterRange localFilterRange;
    View localView;
    String str;
    int j;
    int m;
    label276:
    label288:
    FifeImageView localFifeImageView;
    label356:
    ContentFilters.FilterChoice localFilterChoice1;
    CheckBox localCheckBox;
    ContentFilters.FilterChoice localFilterChoice2;
    boolean bool;
    if (localImage != null)
    {
      this.mMoreInfoImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, this.mFinskyApp.mBitmapLoader);
      this.mMoreInfoImageView.setVisibility(0);
      if (!localContentFilterSettingsResponse.hasTutorialText) {
        break label521;
      }
      this.mMoreInfoText.setText(Html.fromHtml(localContentFilterSettingsResponse.tutorialText));
      this.mMoreInfoText.setMovementMethod(LinkMovementMethod.getInstance());
      this.mMoreInfoText.setVisibility(0);
      if ((!localContentFilterSettingsResponse.hasInfoTitle) || (!localContentFilterSettingsResponse.hasInfoText)) {
        break label533;
      }
      this.mTopInfoTitle.setText(Html.fromHtml(localContentFilterSettingsResponse.infoTitle));
      this.mTopInfoText.setText(Html.fromHtml(localContentFilterSettingsResponse.infoText));
      this.mTopInfoTitle.setVisibility(0);
      this.mTopInfoText.setVisibility(0);
      this.mContentFilterOnOffWrapper.setOnClickListener(this);
      this.mFiltersList.removeAllViews();
      BitmapLoader localBitmapLoader = this.mFinskyApp.mBitmapLoader;
      i = 0;
      if (i >= localContentFilterSettingsResponse.filterRange.length) {
        break label642;
      }
      localFilterRange = localContentFilterSettingsResponse.filterRange[i];
      if ((localFilterRange.hasAuthorityId) && (localFilterRange.filterChoice != null))
      {
        localView = getLayoutInflater().inflate(2130968670, this.mFiltersList, false);
        localView.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            if (ContentFiltersActivity2.this.mHasCompletedPin)
            {
              ContentFiltersActivity2.this.onFilterLineClicked(localFilterRange, i);
              return;
            }
            Bundle localBundle = new Bundle();
            localBundle.putInt("ContentFiltersActivity2.onPinSuccess", 3);
            localBundle.putByteArray("ContentFiltersActivity2.filterRange", MessageNano.toByteArray(localFilterRange));
            localBundle.putInt("ContentFiltersActivity2.filterRangeIndex", i);
            ContentFiltersActivity2.access$200$2c57d84c(ContentFiltersActivity2.this, localBundle);
          }
        });
        str = localFilterRange.appPackageName;
        j = localFilterRange.minVersionCode;
        if (!TextUtils.isEmpty(str)) {
          break label554;
        }
        m = 1;
        if (m != 0) {
          break label595;
        }
        localView.setVisibility(8);
        ((TextView)localView.findViewById(2131755355)).setText(localFilterRange.label);
        localFifeImageView = (FifeImageView)localView.findViewById(2131755354);
        if (localFilterRange.iconFife == null) {
          break label604;
        }
        localFifeImageView.setImage(localFilterRange.iconFife.imageUrl, localFilterRange.iconFife.supportsFifeUrlOptions, localBitmapLoader);
        localFifeImageView.setVisibility(0);
        localFilterChoice1 = ContentFiltersUtils.getFilterChoice(localFilterRange, this.mSelections);
        if (localFilterChoice1 != null) {
          ((TextView)localView.findViewById(2131755357)).setText(localFilterChoice1.labelSummary);
        }
        localCheckBox = (CheckBox)localView.findViewById(2131755356);
        if (!localFilterRange.representChoiceAsToggle) {
          break label632;
        }
        localFilterChoice2 = localFilterRange.filterChoice[1];
        if ((localFilterChoice1 == null) || (!localFilterChoice2.hasLevel)) {
          break label619;
        }
        if (localFilterChoice1.level != localFilterChoice2.level) {
          break label613;
        }
        bool = true;
        label448:
        localCheckBox.setChecked(bool);
        label455:
        localCheckBox.setVisibility(0);
      }
    }
    for (;;)
    {
      this.mFiltersList.addView(localView);
      if ((ContentFiltersUtils.findSelection(localFilterRange, this.mSelections) == -1) && (localFilterChoice1 != null)) {
        this.mSelections = ContentFiltersUtils.setOrAddSelection(this.mSelections, localFilterRange, localFilterChoice1);
      }
      i++;
      break label182;
      this.mMoreInfoImageView.setVisibility(8);
      break;
      label521:
      this.mMoreInfoText.setVisibility(8);
      break label98;
      label533:
      this.mTopInfoTitle.setVisibility(8);
      this.mTopInfoText.setVisibility(8);
      break label156;
      label554:
      int k = this.mFinskyApp.mPackageStateRepository.getVersionCode(str);
      if ((k == -1) || (k >= j))
      {
        m = 1;
        break label276;
      }
      m = 0;
      break label276;
      label595:
      localView.setVisibility(0);
      break label288;
      label604:
      localFifeImageView.setVisibility(4);
      break label356;
      label613:
      bool = false;
      break label448;
      label619:
      localCheckBox.setChecked(localFilterChoice2.selected);
      break label455;
      label632:
      localCheckBox.setVisibility(8);
    }
    label642:
    FinskyPreferences.contentFilters2VisitedOnce.put(Boolean.valueOf(true));
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    this.mLoadProgress.setVisibility(8);
    String str = ErrorStrings.get(this, paramVolleyError);
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessage(str).setPositiveId(2131362418);
    localBuilder.build().show(getSupportFragmentManager(), "ContentFiltersActivity2.errorDialog");
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 2131756242)
    {
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setData(Uri.parse((String)G.contentFilterInfoUrl.get()));
      startActivity(localIntent);
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public void onPause()
  {
    if (this.mDfeContentFilters.mResponse != null) {
      saveAndBroadcast(this.mContentFilterOnOffToggle.isChecked());
    }
    super.onPause();
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    int k;
    byte[] arrayOfByte2;
    int m;
    if (paramInt == 1)
    {
      k = paramBundle.getInt("ContentFiltersActivity2.selectedChoiceIndex");
      arrayOfByte2 = paramBundle.getByteArray("ContentFiltersActivity2.filterRange");
      m = paramBundle.getInt("ContentFiltersActivity2.filterRangeIndex");
      this.mConfirmationDialogsShown.add(Integer.valueOf(m));
    }
    for (;;)
    {
      try
      {
        ContentFilters.FilterRange localFilterRange2 = ContentFilters.FilterRange.parseFrom(arrayOfByte2);
        applySelectedFilterChoice(localFilterRange2.filterChoice[k], localFilterRange2, this.mFiltersList.getChildAt(m));
        return;
      }
      catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException2)
      {
        FinskyLog.wtf(localInvalidProtocolBufferNanoException2, "Cannot parse FilterRange proto from byte[] in arguments.", new Object[0]);
        return;
      }
      if (paramInt == 2)
      {
        int i = paramBundle.getInt("ContentFiltersDialogView.selectedChoiceIndex");
        byte[] arrayOfByte1 = paramBundle.getByteArray("ContentFiltersActivity2.filterRange");
        int j = paramBundle.getInt("ContentFiltersActivity2.filterRangeIndex");
        try
        {
          ContentFilters.FilterRange localFilterRange1 = ContentFilters.FilterRange.parseFrom(arrayOfByte1);
          if (!launchChoiceConfirmationDialog(localFilterRange1, i, j))
          {
            applySelectedFilterChoice(localFilterRange1.filterChoice[i], localFilterRange1, this.mFiltersList.getChildAt(j));
            return;
          }
        }
        catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException1)
        {
          FinskyLog.e(localInvalidProtocolBufferNanoException1, "Cannot parse FilterRange proto from byte[] in arguments.", new Object[0]);
        }
      }
    }
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    readInstanceState(paramBundle);
  }
  
  protected final void onResumeFragments()
  {
    super.onResumeFragments();
    if (this.mExtraParamsOnConfirmedPin != null) {
      switch (this.mExtraParamsOnConfirmedPin.getInt("ContentFiltersActivity2.onPinSuccess"))
      {
      }
    }
    for (;;)
    {
      this.mExtraParamsOnConfirmedPin = null;
      return;
      applyFilterToggle(true);
      continue;
      applyFilterToggle(false);
      continue;
      byte[] arrayOfByte = this.mExtraParamsOnConfirmedPin.getByteArray("ContentFiltersActivity2.filterRange");
      int i = this.mExtraParamsOnConfirmedPin.getInt("ContentFiltersActivity2.filterRangeIndex");
      try
      {
        onFilterLineClicked(ContentFilters.FilterRange.parseFrom(arrayOfByte), i);
      }
      catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
      {
        FinskyLog.wtf(localInvalidProtocolBufferNanoException, "Cannot parse FilterRange proto from byte[] in arguments.", new Object[0]);
      }
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putBoolean("ContentFiltersActivity2.hasCompletedPin", this.mHasCompletedPin);
    paramBundle.putBoolean("ContentFiltersActivity2.temporaryToggleState", this.mContentFilterOnOffToggle.isChecked());
    paramBundle.putString("ContentFiltersActivity2.oldContentFilterPref", this.mOldContentFilterPref);
    paramBundle.putIntegerArrayList("ContentFiltersActivity2.shownConfirmationDialogsIndices", Lists.newArrayList(this.mConfirmationDialogsShown));
    super.onSaveInstanceState(paramBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ContentFiltersActivity2
 * JD-Core Version:    0.7.0.1
 */