package com.google.android.finsky.services;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.SpannableString;
import android.text.TextUtils;
import com.google.android.finsky.utils.Objects;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsumptionAppDoc
  implements Parcelable
{
  public static final Parcelable.Creator<ConsumptionAppDoc> CREATOR = new Parcelable.Creator() {};
  public final String mDocId;
  public final Uri mImageUri;
  public final long mLastUpdateTimeMs;
  public final String mReason;
  public final Intent mViewIntent;
  
  ConsumptionAppDoc(Uri paramUri, CharSequence paramCharSequence, long paramLong, String paramString, Intent paramIntent)
  {
    this.mImageUri = paramUri;
    this.mLastUpdateTimeMs = paramLong;
    this.mDocId = paramString;
    this.mViewIntent = paramIntent;
    String str = (String)paramCharSequence;
    if (TextUtils.isEmpty(str))
    {
      SpannableString localSpannableString = (SpannableString)paramCharSequence;
      if (localSpannableString != null) {
        str = localSpannableString.toString();
      }
    }
    this.mReason = str;
  }
  
  public ConsumptionAppDoc(Bundle paramBundle)
  {
    this((Uri)paramBundle.getParcelable("Play.ImageUri"), (CharSequence)paramBundle.get("Play.Reason"), paramBundle.getLong("Play.LastUpdateTimeMillis"), paramBundle.getString("Play.FinskyDocId"), (Intent)paramBundle.getParcelable("Play.ViewIntent"));
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ConsumptionAppDoc)) {}
    ConsumptionAppDoc localConsumptionAppDoc;
    do
    {
      return false;
      localConsumptionAppDoc = (ConsumptionAppDoc)paramObject;
    } while ((!Objects.equal(this.mImageUri, localConsumptionAppDoc.mImageUri)) || (!TextUtils.equals(this.mReason, localConsumptionAppDoc.mReason)) || (this.mLastUpdateTimeMs != localConsumptionAppDoc.mLastUpdateTimeMs) || (!TextUtils.equals(this.mDocId, localConsumptionAppDoc.mDocId)));
    return true;
  }
  
  public String toString()
  {
    DateFormat localDateFormat = SimpleDateFormat.getDateTimeInstance();
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.mDocId;
    arrayOfObject[1] = this.mImageUri;
    arrayOfObject[2] = this.mReason;
    arrayOfObject[3] = localDateFormat.format(new Date(this.mLastUpdateTimeMs));
    return String.format("Doc %s, Image %s, Reason %s, Last update %s", arrayOfObject);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(1L);
    paramParcel.writeParcelable(this.mImageUri, 0);
    paramParcel.writeString(this.mReason);
    paramParcel.writeLong(this.mLastUpdateTimeMs);
    paramParcel.writeString(this.mDocId);
    paramParcel.writeParcelable(this.mViewIntent, 0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.services.ConsumptionAppDoc
 * JD-Core Version:    0.7.0.1
 */