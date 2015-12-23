package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.UrlBasedPageFragment;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.local.AssetUtils;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.ContentFlagging.FlagContentResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.VendingProtos.ModifyCommentRequestProto;
import com.google.android.finsky.protos.VendingProtos.ModifyCommentResponseProto;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class FlagItemFragment
  extends UrlBasedPageFragment
  implements FlagItemUserMessageDialog.Listener, ButtonBar.ClickListener
{
  private ViewGroup mDetailsPanel;
  private DfeDetails mDfeDetails;
  private Document mDoc;
  private String mFlagMessage;
  private RadioGroup mFlagRadioButtons;
  private int mSelectedRadioButtonId;
  private DetailsSummaryViewBinder mSummaryViewBinder;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(301);
  
  private List<FlagType> getFlagTypesForCurrentCorpus(int paramInt)
  {
    if (paramInt == 3)
    {
      String str = this.mDoc.getAppDetails().packageName;
      if (!FinskyApp.get().mLibraries.getAppOwners(str).isEmpty()) {}
      for (int i = 1;; i = 0)
      {
        boolean bool = ((Boolean)G.vendingHideContentRating.get()).booleanValue();
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.add(new AppFlagType(1, 2131362175, -1));
        localArrayList2.add(new AppFlagType(2, 2131362161, -1));
        localArrayList2.add(new AppFlagType(3, 2131362164, -1));
        if (i != 0) {
          localArrayList2.add(new AppFlagType(4, 2131362163, 2131362162));
        }
        if (!bool) {
          localArrayList2.add(new AppFlagType(6, 2131362165, -1));
        }
        localArrayList2.add(new AppFlagType(2131362174, "PHARMA_VIOLATION"));
        localArrayList2.add(new AppFlagType(2131362167, "IP_INFRINGEMENT"));
        localArrayList2.add(new AppFlagType(5, 2131362170, 2131362169));
        return localArrayList2;
      }
    }
    if (paramInt == 2)
    {
      ArrayList localArrayList1 = new ArrayList();
      localArrayList1.add(new MusicFlagType(5, 2131362166));
      localArrayList1.add(new MusicFlagType(1, 2131362175));
      localArrayList1.add(new MusicFlagType(4, 2131362164));
      localArrayList1.add(new MusicFlagType(6, 2131362176));
      localArrayList1.add(new MusicFlagType(2, 2131362168));
      localArrayList1.add(new MusicFlagType(8, 2131362170));
      return localArrayList1;
    }
    throw new IllegalStateException("unsupported backend type");
  }
  
  private FlagType getSelectedFlagType()
  {
    if ((this.mView == null) || (this.mFlagRadioButtons.getCheckedRadioButtonId() == -1)) {}
    int i;
    List localList;
    do
    {
      return null;
      i = this.mFlagRadioButtons.indexOfChild(this.mView.findViewById(this.mFlagRadioButtons.getCheckedRadioButtonId()));
      localList = getFlagTypesForCurrentCorpus(this.mDoc.mDocument.backendId);
    } while (i >= localList.size());
    return (FlagType)localList.get(i);
  }
  
  public static FlagItemFragment newInstance(String paramString)
  {
    FlagItemFragment localFlagItemFragment = new FlagItemFragment();
    localFlagItemFragment.setDfeTocAndUrl(FinskyApp.get().mToc, paramString);
    return localFlagItemFragment;
  }
  
  private void postFlag()
  {
    this.mPageFragmentHost.goBack();
    getSelectedFlagType().postFlag(this.mContext, this.mDoc, this.mFlagMessage);
  }
  
  protected final int getLayoutRes()
  {
    return 2130968752;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    View localView = this.mView;
    this.mDetailsPanel = ((ViewGroup)localView.findViewById(2131755333));
    this.mFlagRadioButtons = ((RadioGroup)localView.findViewById(2131755517));
    final ButtonBar localButtonBar = (ButtonBar)localView.findViewById(2131755300);
    localButtonBar.setPositiveButtonTitle(2131362769);
    localButtonBar.setPositiveButtonEnabled(false);
    localButtonBar.setClickListener(this);
    this.mFlagRadioButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
      public final void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt)
      {
        localButtonBar.setPositiveButtonEnabled(true);
      }
    });
    if (paramBundle != null)
    {
      this.mFlagMessage = paramBundle.getString("flag_free_text_message");
      if (!paramBundle.containsKey("flag_selected_button_id")) {
        break label147;
      }
    }
    label147:
    for (int i = paramBundle.getInt("flag_selected_button_id");; i = -1)
    {
      this.mSelectedRadioButtonId = i;
      if ((paramBundle == null) || (!paramBundle.containsKey("doc"))) {
        break;
      }
      onDocumentLoaded((Document)paramBundle.getParcelable("doc"));
      return;
    }
    this.mLayoutSwitcher.switchToLoadingMode();
    this.mDfeDetails = new DfeDetails(this.mDfeApi, this.mUrl);
    this.mDfeDetails.addDataChangedListener(new OnDataChangedListener()
    {
      public final void onDataChanged()
      {
        if (FlagItemFragment.this.mDoc == null)
        {
          FlagItemFragment.this.onDocumentLoaded(FlagItemFragment.this.mDfeDetails.getDocument());
          return;
        }
        FinskyLog.d("Ignoring soft TTL refresh.", new Object[0]);
      }
    });
    this.mDfeDetails.addErrorListener(new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        String str = ErrorStrings.get(FlagItemFragment.this.getActivity(), paramAnonymousVolleyError);
        if (str != null)
        {
          FlagItemFragment.this.mPageFragmentHost.showErrorDialog(null, str, true);
          return;
        }
        FlagItemFragment.this.mPageFragmentHost.goBack();
      }
    });
  }
  
  public final void onDestroyView()
  {
    if (this.mSummaryViewBinder != null) {
      this.mSummaryViewBinder.onDestroyView();
    }
    super.onDestroyView();
  }
  
  public final void onDocumentLoaded(Document paramDocument)
  {
    this.mDoc = paramDocument;
    FinskyEventLog.setServerLogCookie(this.mUiElementProto, this.mDoc.mDocument.serverLogsCookie);
    if (this.mSummaryViewBinder == null)
    {
      this.mSummaryViewBinder = BinderFactory.getSummaryViewBinder(this.mDfeToc, this.mDoc.mDocument.backendId, this.mDfeApi.getAccount());
      this.mSummaryViewBinder.setDynamicFeaturesVisibility(false);
      this.mSummaryViewBinder.mIsCompactMode = true;
      this.mSummaryViewBinder.init(this.mContext, this.mNavigationManager, this.mBitmapLoader, this, false, null, null, false, this);
    }
    this.mFlagRadioButtons.removeAllViews();
    LayoutInflater localLayoutInflater = LayoutInflater.from(this.mContext);
    Iterator localIterator = getFlagTypesForCurrentCorpus(this.mDoc.mDocument.backendId).iterator();
    while (localIterator.hasNext())
    {
      FlagType localFlagType = (FlagType)localIterator.next();
      RadioButton localRadioButton = (RadioButton)localLayoutInflater.inflate(2130969033, this.mFlagRadioButtons, false);
      localRadioButton.setText(localFlagType.stringId);
      localRadioButton.setTag(localFlagType);
      this.mFlagRadioButtons.addView(localRadioButton);
      if ((this.mSelectedRadioButtonId != -1) && (this.mSelectedRadioButtonId == localFlagType.stringId)) {
        this.mFlagRadioButtons.check(localRadioButton.getId());
      }
    }
    onDataChanged();
  }
  
  public final void onNegativeButtonClick()
  {
    this.mPageFragmentHost.goBack();
  }
  
  public final void onPositiveButtonClick()
  {
    FlagType localFlagType = getSelectedFlagType();
    if (localFlagType == null) {
      return;
    }
    if (localFlagType.textEntryStringId != -1) {}
    for (int i = 1;; i = 0)
    {
      if (i == 0) {
        break label70;
      }
      FragmentManagerImpl localFragmentManagerImpl = this.mFragmentManager;
      if (localFragmentManagerImpl.findFragmentByTag("flag_item_dialog") != null) {
        break;
      }
      FlagItemUserMessageDialog localFlagItemUserMessageDialog = FlagItemUserMessageDialog.newInstance(localFlagType.textEntryStringId);
      localFlagItemUserMessageDialog.setTargetFragment(this, 0);
      localFlagItemUserMessageDialog.show(localFragmentManagerImpl, "flag_item_dialog");
      return;
    }
    label70:
    postFlag();
  }
  
  public final void onPositiveClick(String paramString)
  {
    this.mFlagMessage = paramString;
    postFlag();
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mDoc != null)
    {
      paramBundle.putParcelable("doc", this.mDoc);
      paramBundle.putString("flag_free_text_message", this.mFlagMessage);
      if (getSelectedFlagType() != null) {
        paramBundle.putInt("flag_selected_button_id", getSelectedFlagType().stringId);
      }
    }
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mContext.getString(2131362177));
    this.mPageFragmentHost.updateCurrentBackendId(this.mDoc.mDocument.backendId, false);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  public final void rebindViews()
  {
    TextView localTextView1;
    if (this.mDoc != null)
    {
      localTextView1 = (TextView)this.mDataView.findViewById(2131755516);
      if (this.mDoc.mDocument.backendId != 3) {
        break label189;
      }
    }
    label189:
    for (int i = 2131362171;; i = 2131362172)
    {
      localTextView1.setText(i);
      if (this.mDoc.mDocument.backendId == 2)
      {
        TextView localTextView2 = (TextView)this.mDataView.findViewById(2131755518);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = G.musicDmcaReportLink.get();
        localTextView2.setText(Html.fromHtml(getString(2131362173, arrayOfObject)));
        localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
        localTextView2.setVisibility(0);
      }
      DetailsSummaryViewBinder localDetailsSummaryViewBinder = this.mSummaryViewBinder;
      Document localDocument = this.mDoc;
      View[] arrayOfView = new View[1];
      arrayOfView[0] = this.mDetailsPanel;
      localDetailsSummaryViewBinder.bind(localDocument, false, arrayOfView);
      this.mDataView.findViewById(2131755515).setBackgroundColor(CorpusResourceUtils.getPrimaryColor(this.mContext, this.mDoc.mDocument.backendId));
      rebindActionBar();
      return;
    }
  }
  
  protected final void requestData() {}
  
  public static final class AppFlagType
    extends FlagItemFragment.FlagType
  {
    private String mFlagMessage;
    private final int mRpcId;
    
    AppFlagType(int paramInt1, int paramInt2, int paramInt3)
    {
      super(paramInt3);
      this.mRpcId = paramInt1;
    }
    
    AppFlagType(int paramInt, String paramString)
    {
      this(5, paramInt, -1);
      this.mFlagMessage = paramString;
    }
    
    public final void postFlag(final Context paramContext, Document paramDocument, String paramString)
    {
      if (paramString != null) {
        this.mFlagMessage = paramString;
      }
      VendingApi localVendingApi = FinskyApp.get().getVendingApi();
      AppDetails localAppDetails = paramDocument.getAppDetails();
      String str1 = AssetUtils.makeAssetId(localAppDetails.packageName, localAppDetails.versionCode);
      int i = this.mRpcId;
      String str2 = this.mFlagMessage;
      Response.Listener local1 = new Response.Listener() {};
      Response.ErrorListener local2 = new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError) {}
      };
      VendingProtos.ModifyCommentRequestProto localModifyCommentRequestProto = new VendingProtos.ModifyCommentRequestProto();
      localModifyCommentRequestProto.assetId = str1;
      localModifyCommentRequestProto.hasAssetId = true;
      localModifyCommentRequestProto.flagType = i;
      localModifyCommentRequestProto.hasFlagType = true;
      if (!TextUtils.isEmpty(str2))
      {
        localModifyCommentRequestProto.flagMessage = str2;
        localModifyCommentRequestProto.hasFlagMessage = true;
      }
      VendingRequest localVendingRequest = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.ModifyCommentRequestProto.class, localModifyCommentRequestProto, VendingProtos.ModifyCommentResponseProto.class, local1, localVendingApi.mApiContext, local2);
      localVendingApi.mRequestQueue.add(localVendingRequest);
    }
  }
  
  public static abstract class FlagType
  {
    public final int stringId;
    public final int textEntryStringId;
    
    protected FlagType(int paramInt1, int paramInt2)
    {
      this.stringId = paramInt1;
      this.textEntryStringId = paramInt2;
    }
    
    public abstract void postFlag(Context paramContext, Document paramDocument, String paramString);
  }
  
  public static final class MusicFlagType
    extends FlagItemFragment.FlagType
  {
    private final int mContentFlagType;
    
    MusicFlagType(int paramInt1, int paramInt2)
    {
      super(2131362169);
      this.mContentFlagType = paramInt1;
    }
    
    public final void postFlag(final Context paramContext, Document paramDocument, String paramString)
    {
      FinskyApp.get().getDfeApi(null).flagContent(paramDocument.mDocument.docid, this.mContentFlagType, paramString, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError) {}
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.FlagItemFragment
 * JD-Core Version:    0.7.0.1
 */