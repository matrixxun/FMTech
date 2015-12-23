package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zzjo;
import java.util.HashMap;

@zzhb
public final class zzk
  extends FrameLayout
{
  private String mMimeType;
  final FrameLayout zzEk;
  public final zzq zzEl;
  public zzi zzEm;
  public boolean zzEn;
  public boolean zzEo;
  TextView zzEp;
  long zzEq;
  public final zzjo zzpX;
  public String zzzK;
  
  public zzk(Context paramContext, zzjo paramzzjo)
  {
    super(paramContext);
    this.zzpX = paramzzjo;
    this.zzEk = new FrameLayout(paramContext);
    addView(this.zzEk, new FrameLayout.LayoutParams(-1, -1));
    zzb.zzy(paramzzjo.zzhH());
    this.zzEm = paramzzjo.zzhH().zzpS.zza$357f7b22();
    if (this.zzEm != null) {
      this.zzEk.addView(this.zzEm, new FrameLayout.LayoutParams(-1, -1, 17));
    }
    this.zzEp = new TextView(paramContext);
    this.zzEp.setBackgroundColor(-16777216);
    if (!zzfF())
    {
      this.zzEk.addView(this.zzEp, new FrameLayout.LayoutParams(-1, -1));
      this.zzEk.bringChildToFront(this.zzEp);
    }
    this.zzEl = new zzq(this);
    this.zzEl.zzfL();
    if (this.zzEm == null) {
      zza("error", new String[] { "what", "AdVideoUnderlay Error", "extra", "Allocating player failed." });
    }
  }
  
  public static void zzd(zzjo paramzzjo)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("event", "no_video_view");
    paramzzjo.zzb("onVideoEvent", localHashMap);
  }
  
  public final void setMimeType(String paramString)
  {
    this.mMimeType = paramString;
  }
  
  final void zza(String paramString, String... paramVarArgs)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("event", paramString);
    int i = paramVarArgs.length;
    int j = 0;
    Object localObject = null;
    if (j < i)
    {
      String str = paramVarArgs[j];
      if (localObject == null) {}
      for (;;)
      {
        j++;
        localObject = str;
        break;
        localHashMap.put(localObject, str);
        str = null;
      }
    }
    this.zzpX.zzb("onVideoEvent", localHashMap);
  }
  
  public final void zzd(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt3 == 0) || (paramInt4 == 0)) {
      return;
    }
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(paramInt3 + 2, paramInt4 + 2);
    localLayoutParams.setMargins(paramInt1 - 1, paramInt2 - 1, 0, 0);
    this.zzEk.setLayoutParams(localLayoutParams);
    requestLayout();
  }
  
  public final void zzfA()
  {
    if (this.zzEm == null) {
      return;
    }
    if (!TextUtils.isEmpty(this.zzzK))
    {
      this.zzEm.setMimeType(this.mMimeType);
      this.zzEm.setVideoPath(this.zzzK);
      return;
    }
    zza("no_src", new String[0]);
  }
  
  public final void zzfB()
  {
    if (this.zzEm == null) {
      return;
    }
    TextView localTextView = new TextView(this.zzEm.getContext());
    localTextView.setText("AdMob - " + this.zzEm.zzeX());
    localTextView.setTextColor(-65536);
    localTextView.setBackgroundColor(-256);
    this.zzEk.addView(localTextView, new FrameLayout.LayoutParams(-2, -2, 17));
    this.zzEk.bringChildToFront(localTextView);
  }
  
  final boolean zzfF()
  {
    return this.zzEp.getParent() != null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.overlay.zzk
 * JD-Core Version:    0.7.0.1
 */