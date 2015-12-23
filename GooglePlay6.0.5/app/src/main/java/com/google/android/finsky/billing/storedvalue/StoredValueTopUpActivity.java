package com.google.android.finsky.billing.storedvalue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.billing.lightpurchase.PurchaseActivity;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams.Builder;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.fragments.SidecarFragment.Listener;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.TopupInfo;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;

public class StoredValueTopUpActivity
  extends AppCompatActivity
  implements AdapterView.OnItemClickListener, SimpleAlertDialog.Listener, SidecarFragment.Listener, ButtonBar.ClickListener
{
  private String mAccountName;
  private ButtonBar mButtonBar;
  private Document[] mDocuments;
  private int mLastShownErrorId = 0;
  private ListSidecar mListSidecar;
  private ListView mListView;
  private View mLoadingIndicator;
  private String mSelectedDocumentFormattedAmount;
  
  public static Intent createIntent(String paramString, TopupInfo paramTopupInfo)
  {
    Intent localIntent = new Intent(FinskyApp.get(), StoredValueTopUpActivity.class);
    localIntent.putExtra("authAccount", paramString);
    localIntent.putExtra("list_url", paramTopupInfo.optionsListUrl);
    return localIntent;
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
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 1)
    {
      Intent localIntent = new Intent();
      if (paramInt2 == -1)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.mSelectedDocumentFormattedAmount;
        Toast.makeText(this, getString(2131362799, arrayOfObject), 0).show();
      }
      setResult(paramInt2, localIntent);
      finish();
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968637);
    this.mLoadingIndicator = findViewById(2131755289);
    this.mListView = ((ListView)findViewById(2131755279));
    this.mButtonBar = ((ButtonBar)findViewById(2131755300));
    this.mButtonBar.setPositiveButtonTitle(2131362418);
    this.mButtonBar.setNegativeButtonTitle(2131361915);
    this.mButtonBar.setClickListener(this);
    syncPositiveButton();
    this.mAccountName = getIntent().getStringExtra("authAccount");
    if (paramBundle == null)
    {
      this.mListSidecar = ListSidecar.access$000(this.mAccountName, getIntent().getStringExtra("list_url"));
      getSupportFragmentManager().beginTransaction().add(this.mListSidecar, "list_sidecar").commit();
      return;
    }
    this.mSelectedDocumentFormattedAmount = paramBundle.getString("selected_document_formatted_amount");
    this.mLastShownErrorId = paramBundle.getInt("last_shown_error");
    this.mListSidecar = ((ListSidecar)getSupportFragmentManager().findFragmentByTag("list_sidecar"));
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    syncPositiveButton();
  }
  
  public final void onNegativeButtonClick()
  {
    setResult(0);
    finish();
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPositiveButtonClick()
  {
    int i = this.mListView.getCheckedItemPosition();
    Document localDocument = this.mDocuments[i];
    Common.Offer localOffer = localDocument.getOffer(1);
    if (localOffer == null)
    {
      FinskyLog.w("Document selected without PURCHASE offer. Ignoring.", new Object[0]);
      return;
    }
    this.mSelectedDocumentFormattedAmount = localOffer.formattedAmount;
    PurchaseParams.Builder localBuilder = PurchaseParams.builder().setDocument(localDocument);
    localBuilder.offerType = 1;
    PurchaseParams localPurchaseParams = localBuilder.build();
    startActivityForResult(PurchaseActivity.createIntent(AccountHandler.findAccount(this.mAccountName, this), localPurchaseParams, localDocument.mDocument.serverLogsCookie, null), 1);
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 0)
    {
      setResult(0);
      finish();
    }
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    syncPositiveButton();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("last_shown_error", this.mLastShownErrorId);
    paramBundle.putString("selected_document_formatted_amount", this.mSelectedDocumentFormattedAmount);
  }
  
  protected void onStart()
  {
    super.onStart();
    this.mListSidecar.setListener(this);
    if (this.mListSidecar.mState == 0)
    {
      ListSidecar localListSidecar = this.mListSidecar;
      localListSidecar.mDfeList.startLoadItems();
      localListSidecar.setState(1, 0);
    }
  }
  
  public final void onStateChange(SidecarFragment paramSidecarFragment)
  {
    this.mLoadingIndicator.setVisibility(8);
    this.mListView.setVisibility(8);
    this.mButtonBar.setVisibility(4);
    int i = paramSidecarFragment.mState;
    if (i == 2)
    {
      this.mDocuments = this.mListSidecar.mDfeList.mContainerDocument.getChildren();
      ArrayList localArrayList = Lists.newArrayList(this.mDocuments.length);
      Document[] arrayOfDocument = this.mDocuments;
      int k = arrayOfDocument.length;
      for (int m = 0; m < k; m++) {
        localArrayList.add(arrayOfDocument[m].mDocument.title);
      }
      ArrayAdapter localArrayAdapter = new ArrayAdapter(this, 17367055, localArrayList);
      this.mListView.setAdapter(localArrayAdapter);
      this.mListView.setItemsCanFocus(false);
      this.mListView.setChoiceMode(1);
      this.mListView.setOnItemClickListener(this);
      this.mListView.setVisibility(0);
      this.mButtonBar.setVisibility(0);
      syncPositiveButton();
    }
    do
    {
      return;
      if (i == 1)
      {
        this.mLoadingIndicator.setVisibility(0);
        return;
      }
    } while (paramSidecarFragment.mState != 3);
    if (this.mLastShownErrorId == paramSidecarFragment.mStateInstance)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mLastShownErrorId);
      FinskyLog.d("Already showed error %d, ignoring.", arrayOfObject);
      return;
    }
    this.mLastShownErrorId = paramSidecarFragment.mStateInstance;
    String str;
    if (paramSidecarFragment.mSubstate == 0) {
      str = ErrorStrings.get(FinskyApp.get(), this.mListSidecar.mLastError);
    }
    while (str != null)
    {
      SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
      localBuilder.setMessage(str).setPositiveId(2131362418).setCallback(null, 0, null);
      localBuilder.build().show(getSupportFragmentManager(), "error_dialog");
      return;
      int j = paramSidecarFragment.mSubstate;
      str = null;
      if (j == 1) {
        str = FinskyApp.get().getString(2131362798);
      }
    }
    FinskyLog.w("Received error without error message.", new Object[0]);
    setResult(0);
    finish();
  }
  
  protected void onStop()
  {
    this.mListSidecar.setListener(null);
    super.onStop();
  }
  
  public static final class ListSidecar
    extends SidecarFragment
    implements Response.ErrorListener, OnDataChangedListener
  {
    DfeList mDfeList;
    VolleyError mLastError;
    
    public final void onCreate(Bundle paramBundle)
    {
      super.onCreate(paramBundle);
      String str = this.mArguments.getString("authAccount");
      this.mDfeList = new DfeList(FinskyApp.get().getDfeApi(str), this.mArguments.getString("list_url"), false);
      this.mDfeList.addDataChangedListener(this);
      this.mDfeList.addErrorListener(this);
    }
    
    public final void onDataChanged()
    {
      if ((this.mDfeList.mContainerDocument != null) && (this.mDfeList.mContainerDocument.getChildCount() > 0))
      {
        setState(2, 0);
        return;
      }
      setState(3, 1);
    }
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      this.mLastError = paramVolleyError;
      setState(3, 0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.storedvalue.StoredValueTopUpActivity
 * JD-Core Version:    0.7.0.1
 */