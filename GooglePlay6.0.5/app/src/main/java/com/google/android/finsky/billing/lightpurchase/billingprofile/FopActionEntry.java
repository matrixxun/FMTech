package com.google.android.finsky.billing.lightpurchase.billingprofile;

import android.view.View.OnClickListener;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfileOption;
import com.google.android.finsky.protos.Common.Image;

public final class FopActionEntry
{
  public final View.OnClickListener action;
  public final String displaySubtitle;
  public final String displayTitle;
  public final String editButtonLabel;
  public final Common.Image iconImage;
  public final byte[] paymentsIntegratorEditToken;
  public final int playStoreUiElementType;
  public final byte[] serverLogsCookie;
  
  public FopActionEntry(BillingProfileProtos.BillingProfileOption paramBillingProfileOption, View.OnClickListener paramOnClickListener, int paramInt)
  {
    this(paramBillingProfileOption.displayTitle, null, paramBillingProfileOption.iconImage, null, null, paramOnClickListener, paramBillingProfileOption.serverLogsCookie, paramInt);
  }
  
  public FopActionEntry(String paramString1, String paramString2, Common.Image paramImage, String paramString3, byte[] paramArrayOfByte1, View.OnClickListener paramOnClickListener, byte[] paramArrayOfByte2, int paramInt)
  {
    this.displayTitle = paramString1;
    this.displaySubtitle = paramString2;
    this.iconImage = paramImage;
    this.editButtonLabel = paramString3;
    this.paymentsIntegratorEditToken = paramArrayOfByte1;
    this.action = paramOnClickListener;
    this.serverLogsCookie = paramArrayOfByte2;
    this.playStoreUiElementType = paramInt;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.FopActionEntry
 * JD-Core Version:    0.7.0.1
 */