package com.google.android.gms.common.people.data;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.Arrays;

public final class AudienceMember
  implements SafeParcelable
{
  public static final AudienceMemberCreator CREATOR = new AudienceMemberCreator();
  final int mVersionCode;
  final int zzTv;
  public final String zzUe;
  @Deprecated
  final Bundle zzarz;
  final int zzauS;
  public final String zzauT;
  final String zzauU;
  final String zzauV;
  
  AudienceMember(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3, String paramString4, Bundle paramBundle)
  {
    this.mVersionCode = paramInt1;
    this.zzTv = paramInt2;
    this.zzauS = paramInt3;
    this.zzauT = paramString1;
    this.zzauU = paramString2;
    this.zzUe = paramString3;
    this.zzauV = paramString4;
    if (paramBundle != null) {}
    for (;;)
    {
      this.zzarz = paramBundle;
      return;
      paramBundle = new Bundle();
    }
  }
  
  private AudienceMember(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this(1, paramInt1, paramInt2, paramString1, paramString2, paramString3, null, null);
  }
  
  public static AudienceMember forCircle(String paramString1, String paramString2)
  {
    return new AudienceMember(1, -1, paramString1, null, paramString2, null);
  }
  
  public static AudienceMember forPersonWithPeopleQualifiedId$4a62ecd5(String paramString)
  {
    return new AudienceMember(2, 0, null, paramString, null, null);
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AudienceMember)) {}
    AudienceMember localAudienceMember;
    do
    {
      return false;
      localAudienceMember = (AudienceMember)paramObject;
    } while ((this.mVersionCode != localAudienceMember.mVersionCode) || (this.zzTv != localAudienceMember.zzTv) || (this.zzauS != localAudienceMember.zzauS) || (!zzw.equal(this.zzauT, localAudienceMember.zzauT)) || (!zzw.equal(this.zzauU, localAudienceMember.zzauU)));
    return true;
  }
  
  public final int hashCode()
  {
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = Integer.valueOf(this.mVersionCode);
    arrayOfObject[1] = Integer.valueOf(this.zzTv);
    arrayOfObject[2] = Integer.valueOf(this.zzauS);
    arrayOfObject[3] = this.zzauT;
    arrayOfObject[4] = this.zzauU;
    return Arrays.hashCode(arrayOfObject);
  }
  
  public final String toString()
  {
    if (this.zzTv == 2) {}
    for (int i = 1; i != 0; i = 0)
    {
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = this.zzauU;
      arrayOfObject3[1] = this.zzUe;
      return String.format("Person [%s] %s", arrayOfObject3);
    }
    if ((this.zzTv == 1) && (this.zzauS == -1)) {}
    for (int j = 1; j != 0; j = 0)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = this.zzauT;
      arrayOfObject2[1] = this.zzUe;
      return String.format("Circle [%s] %s", arrayOfObject2);
    }
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = this.zzauT;
    arrayOfObject1[1] = this.zzUe;
    return String.format("Group [%s] %s", arrayOfObject1);
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt)
  {
    AudienceMemberCreator.zza$4b7b3a63(this, paramParcel);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.people.data.AudienceMember
 * JD-Core Version:    0.7.0.1
 */