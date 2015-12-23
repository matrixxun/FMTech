package com.google.android.finsky.layout.play;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.CallToAction;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.protos.TopChartsContainer;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.util.List;

public class TopChartsClusterContentView
  extends FrameLayout
{
  private TextView mActionButton;
  private FifeImageView mPromoImage;
  private TextView mTopChartsContentSubTitle;
  private TextView mTopChartsContentTitle;
  
  public TopChartsClusterContentView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TopChartsClusterContentView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void bind(Document paramDocument, BitmapLoader paramBitmapLoader, final DfeApi paramDfeApi, final DfeToc paramDfeToc, final NavigationManager paramNavigationManager, final PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    final TopChartsContainer localTopChartsContainer;
    String str;
    TextView localTextView;
    if (paramDocument.isTopChartsContainer())
    {
      localTopChartsContainer = paramDocument.getTemplate().topChartsContainer;
      Common.Image localImage = (Common.Image)paramDocument.getImages(14).get(0);
      this.mPromoImage.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, paramBitmapLoader);
      this.mTopChartsContentTitle.setText(paramDocument.getChildAt(0).mDocument.title);
      this.mTopChartsContentSubTitle.setText(localTopChartsContainer.topChartDescription);
      str = localTopChartsContainer.action.buttonText;
      localTextView = this.mActionButton;
      if (TextUtils.isEmpty(str)) {
        break label140;
      }
    }
    for (;;)
    {
      localTextView.setText(str);
      setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(1238, null, paramPlayStoreUiElementNode);
          paramNavigationManager.resolveCallToAction(localTopChartsContainer.action, paramDfeApi, paramDfeToc, FinskyApp.get().getPackageManager());
        }
      });
      return;
      localTopChartsContainer = null;
      break;
      label140:
      str = getContext().getString(2131362796).toUpperCase();
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mPromoImage = ((FifeImageView)findViewById(2131755971));
    this.mTopChartsContentTitle = ((TextView)findViewById(2131756162));
    this.mTopChartsContentSubTitle = ((TextView)findViewById(2131756163));
    this.mActionButton = ((TextView)findViewById(2131755213));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.TopChartsClusterContentView
 * JD-Core Version:    0.7.0.1
 */