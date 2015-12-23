package com.google.android.finsky.billing.refund;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.utils.FinskyLog;

public final class RequestRefundSidecar
  extends SidecarFragment
{
  private String mAccountName;
  private Document mDocument;
  VolleyError mVolleyError;
  
  public final String getDialogMessage()
  {
    switch (this.mState)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mState);
      FinskyLog.wtf("Invalid sidecar state: %d", arrayOfObject);
      return null;
    case 4: 
      return "Are you sure you want to request a refund? If the refund is approved, your item maybe uninstalled or removed from your library.";
    case 5: 
      return "We need a little more information to process your refund request. We are sorry you have to wait a little longer. Continue with the refund?";
    case 6: 
      return "Your refund has been approved. In the meantime, do you want to take a quick survey and let us help you better next time?";
    case 7: 
      return "Thank you for providing more details on your refund request. You will also receive an email confirmation.";
    }
    return "Error while requesting refunds. Try again later.";
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    Bundle localBundle = this.mArguments;
    this.mAccountName = localBundle.getString("RequestRefundSidecar.accountName");
    this.mDocument = ((Document)localBundle.getParcelable("RequestRefundSidecar.document"));
    super.onCreate(paramBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.refund.RequestRefundSidecar
 * JD-Core Version:    0.7.0.1
 */