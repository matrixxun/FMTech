package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.zzh;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount
  implements SafeParcelable
{
  public static final Parcelable.Creator<GoogleSignInAccount> CREATOR = new zzc();
  public static Clock zzXb = zzh.zzrs();
  private static Comparator<Scope> zzXh = new Comparator() {};
  final int versionCode;
  String zzUe;
  List<Scope> zzVF;
  String zzWy;
  String zzXc;
  Uri zzXd;
  String zzXe;
  long zzXf;
  String zzXg;
  String zzyx;
  
  GoogleSignInAccount(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, Uri paramUri, String paramString5, long paramLong, String paramString6, List<Scope> paramList)
  {
    this.versionCode = paramInt;
    this.zzyx = paramString1;
    this.zzWy = paramString2;
    this.zzXc = paramString3;
    this.zzUe = paramString4;
    this.zzXd = paramUri;
    this.zzXe = paramString5;
    this.zzXf = paramLong;
    this.zzXg = paramString6;
    this.zzVF = paramList;
  }
  
  public static GoogleSignInAccount zzbq(String paramString)
    throws JSONException
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    JSONObject localJSONObject = new JSONObject(paramString);
    String str1 = localJSONObject.optString("photoUrl", null);
    if (!TextUtils.isEmpty(str1)) {}
    for (Uri localUri = Uri.parse(str1);; localUri = null)
    {
      long l = Long.parseLong(localJSONObject.getString("expirationTime"));
      HashSet localHashSet = new HashSet();
      JSONArray localJSONArray = localJSONObject.getJSONArray("grantedScopes");
      int i = localJSONArray.length();
      for (int j = 0; j < i; j++) {
        localHashSet.add(new Scope(localJSONArray.getString(j)));
      }
      String str2 = localJSONObject.optString("id");
      String str3 = localJSONObject.optString("tokenId", null);
      String str4 = localJSONObject.optString("email", null);
      String str5 = localJSONObject.optString("displayName", null);
      Long localLong1 = Long.valueOf(l);
      String str6 = localJSONObject.getString("obfuscatedIdentifier");
      if (localLong1 == null) {}
      for (Long localLong2 = Long.valueOf(zzXb.currentTimeMillis() / 1000L);; localLong2 = localLong1)
      {
        GoogleSignInAccount localGoogleSignInAccount = new GoogleSignInAccount(2, str2, str3, str4, str5, localUri, null, localLong2.longValue(), zzx.zzcG(str6), new ArrayList((Collection)zzx.zzC(localHashSet)));
        localGoogleSignInAccount.zzXe = localJSONObject.optString("serverAuthCode", null);
        return localGoogleSignInAccount;
      }
    }
  }
  
  private JSONObject zzke()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      if (this.zzyx != null) {
        localJSONObject.put("id", this.zzyx);
      }
      if (this.zzWy != null) {
        localJSONObject.put("tokenId", this.zzWy);
      }
      if (this.zzXc != null) {
        localJSONObject.put("email", this.zzXc);
      }
      if (this.zzUe != null) {
        localJSONObject.put("displayName", this.zzUe);
      }
      if (this.zzXd != null) {
        localJSONObject.put("photoUrl", this.zzXd.toString());
      }
      if (this.zzXe != null) {
        localJSONObject.put("serverAuthCode", this.zzXe);
      }
      localJSONObject.put("expirationTime", this.zzXf);
      localJSONObject.put("obfuscatedIdentifier", this.zzXg);
      JSONArray localJSONArray = new JSONArray();
      Collections.sort(this.zzVF, zzXh);
      Iterator localIterator = this.zzVF.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put(((Scope)localIterator.next()).zzaoy);
      }
      localJSONObject.put("grantedScopes", localJSONArray);
    }
    catch (JSONException localJSONException)
    {
      throw new RuntimeException(localJSONException);
    }
    return localJSONObject;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof GoogleSignInAccount)) {
      return false;
    }
    return ((GoogleSignInAccount)paramObject).zzke().toString().equals(zzke().toString());
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.api.signin.GoogleSignInAccount
 * JD-Core Version:    0.7.0.1
 */