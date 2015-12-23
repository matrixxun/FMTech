package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.EmailDecoder;
import com.google.android.gms.people.internal.agg.PhoneEmailDecoder.PhoneDecoder;
import com.google.android.gms.people.model.Person;
import java.util.regex.Pattern;

public final class zzq
  extends zzc
  implements Person
{
  public static final String[] zzaTX = { "_id", "qualified_id", "gaia_id", "name", "sort_key", "sort_key_irank", "avatar", "profile_type", "v_circle_ids", "blocked", "in_viewer_domain", "last_modified", "name_verified", "given_name", "family_name", "affinity1", "affinity2", "affinity3", "affinity4", "affinity5", "people_in_common", "v_emails", "v_phones" };
  private final Bundle zzarz;
  private final PhoneEmailDecoder.PhoneDecoder zzbsX;
  private final PhoneEmailDecoder.EmailDecoder zzbsY;
  private final boolean zzbsZ;
  
  public zzq(DataHolder paramDataHolder, int paramInt, Bundle paramBundle, PhoneEmailDecoder.PhoneDecoder paramPhoneDecoder, PhoneEmailDecoder.EmailDecoder paramEmailDecoder)
  {
    super(paramDataHolder, paramInt);
    this.zzarz = paramBundle;
    this.zzbsX = paramPhoneDecoder;
    this.zzbsY = paramEmailDecoder;
    this.zzbsZ = this.zzarz.getBoolean("emails_with_affinities", false);
  }
  
  public final String[] getBelongingCircleIds()
  {
    String str = getString("v_circle_ids");
    if (TextUtils.isEmpty(str)) {
      return zzp.zzbxM;
    }
    return zzp.zzbxN.split(str, -1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzq
 * JD-Core Version:    0.7.0.1
 */