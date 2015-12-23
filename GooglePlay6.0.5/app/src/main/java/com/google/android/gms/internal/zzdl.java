package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.config.Flag;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.HashMap;
import java.util.Map;

@zzhb
public final class zzdl
{
  public static final zzdm zzyZ = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap) {}
  };
  public static final zzdm zzza = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      String str1 = (String)paramAnonymousMap.get("urls");
      if (TextUtils.isEmpty(str1))
      {
        zzb.w("URLs missing in canOpenURLs GMSG.");
        return;
      }
      String[] arrayOfString1 = str1.split(",");
      HashMap localHashMap = new HashMap();
      PackageManager localPackageManager = paramAnonymouszzjo.getContext().getPackageManager();
      int i = arrayOfString1.length;
      int j = 0;
      if (j < i)
      {
        String str2 = arrayOfString1[j];
        String[] arrayOfString2 = str2.split(";", 2);
        String str3 = arrayOfString2[0].trim();
        String str4;
        if (arrayOfString2.length > 1)
        {
          str4 = arrayOfString2[1].trim();
          label110:
          if (localPackageManager.resolveActivity(new Intent(str4, Uri.parse(str3)), 65536) == null) {
            break label163;
          }
        }
        label163:
        for (boolean bool = true;; bool = false)
        {
          localHashMap.put(str2, Boolean.valueOf(bool));
          j++;
          break;
          str4 = "android.intent.action.VIEW";
          break label110;
        }
      }
      paramAnonymouszzjo.zzb("openableURLs", localHashMap);
    }
  };
  public static final zzdm zzzb = new zzdm()
  {
    /* Error */
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      // Byte code:
      //   0: aload_1
      //   1: invokeinterface 22 1 0
      //   6: invokevirtual 28	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
      //   9: astore_3
      //   10: aload_2
      //   11: ldc 30
      //   13: invokeinterface 36 2 0
      //   18: checkcast 38	java/lang/String
      //   21: astore 4
      //   23: new 40	org/json/JSONObject
      //   26: dup
      //   27: aload 4
      //   29: invokespecial 43	org/json/JSONObject:<init>	(Ljava/lang/String;)V
      //   32: astore 5
      //   34: aload 5
      //   36: ldc 45
      //   38: invokevirtual 49	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
      //   41: astore 7
      //   43: new 40	org/json/JSONObject
      //   46: dup
      //   47: invokespecial 50	org/json/JSONObject:<init>	()V
      //   50: astore 8
      //   52: iconst_0
      //   53: istore 9
      //   55: iload 9
      //   57: aload 7
      //   59: invokevirtual 56	org/json/JSONArray:length	()I
      //   62: if_icmpge +300 -> 362
      //   65: aload 7
      //   67: iload 9
      //   69: invokevirtual 60	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
      //   72: astore 11
      //   74: aload 11
      //   76: ldc 62
      //   78: invokevirtual 66	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
      //   81: astore 12
      //   83: aload 11
      //   85: ldc 68
      //   87: invokevirtual 66	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
      //   90: astore 13
      //   92: aload 11
      //   94: ldc 70
      //   96: invokevirtual 66	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
      //   99: astore 14
      //   101: aload 11
      //   103: ldc 72
      //   105: invokevirtual 66	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
      //   108: astore 15
      //   110: aload 11
      //   112: ldc 74
      //   114: invokevirtual 66	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
      //   117: astore 16
      //   119: aload 11
      //   121: ldc 76
      //   123: invokevirtual 66	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
      //   126: astore 17
      //   128: aload 11
      //   130: ldc 78
      //   132: invokevirtual 66	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
      //   135: pop
      //   136: aload 11
      //   138: ldc 80
      //   140: invokevirtual 66	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
      //   143: pop
      //   144: new 82	android/content/Intent
      //   147: dup
      //   148: invokespecial 83	android/content/Intent:<init>	()V
      //   151: astore 20
      //   153: aload 13
      //   155: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   158: ifne +14 -> 172
      //   161: aload 20
      //   163: aload 13
      //   165: invokestatic 95	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
      //   168: invokevirtual 99	android/content/Intent:setData	(Landroid/net/Uri;)Landroid/content/Intent;
      //   171: pop
      //   172: aload 14
      //   174: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   177: ifne +11 -> 188
      //   180: aload 20
      //   182: aload 14
      //   184: invokevirtual 103	android/content/Intent:setAction	(Ljava/lang/String;)Landroid/content/Intent;
      //   187: pop
      //   188: aload 15
      //   190: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   193: ifne +11 -> 204
      //   196: aload 20
      //   198: aload 15
      //   200: invokevirtual 106	android/content/Intent:setType	(Ljava/lang/String;)Landroid/content/Intent;
      //   203: pop
      //   204: aload 16
      //   206: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   209: ifne +11 -> 220
      //   212: aload 20
      //   214: aload 16
      //   216: invokevirtual 109	android/content/Intent:setPackage	(Ljava/lang/String;)Landroid/content/Intent;
      //   219: pop
      //   220: aload 17
      //   222: invokestatic 89	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   225: ifne +41 -> 266
      //   228: aload 17
      //   230: ldc 111
      //   232: iconst_2
      //   233: invokevirtual 115	java/lang/String:split	(Ljava/lang/String;I)[Ljava/lang/String;
      //   236: astore 24
      //   238: aload 24
      //   240: arraylength
      //   241: iconst_2
      //   242: if_icmpne +24 -> 266
      //   245: aload 20
      //   247: new 117	android/content/ComponentName
      //   250: dup
      //   251: aload 24
      //   253: iconst_0
      //   254: aaload
      //   255: aload 24
      //   257: iconst_1
      //   258: aaload
      //   259: invokespecial 120	android/content/ComponentName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
      //   262: invokevirtual 124	android/content/Intent:setComponent	(Landroid/content/ComponentName;)Landroid/content/Intent;
      //   265: pop
      //   266: aload_3
      //   267: aload 20
      //   269: ldc 125
      //   271: invokevirtual 131	android/content/pm/PackageManager:resolveActivity	(Landroid/content/Intent;I)Landroid/content/pm/ResolveInfo;
      //   274: ifnull +70 -> 344
      //   277: iconst_1
      //   278: istore 21
      //   280: aload 8
      //   282: aload 12
      //   284: iload 21
      //   286: invokevirtual 135	org/json/JSONObject:put	(Ljava/lang/String;Z)Lorg/json/JSONObject;
      //   289: pop
      //   290: iinc 9 1
      //   293: goto -238 -> 55
      //   296: astore 30
      //   298: aload_1
      //   299: ldc 137
      //   301: new 40	org/json/JSONObject
      //   304: dup
      //   305: invokespecial 50	org/json/JSONObject:<init>	()V
      //   308: invokeinterface 141 3 0
      //   313: return
      //   314: astore 6
      //   316: aload_1
      //   317: ldc 137
      //   319: new 40	org/json/JSONObject
      //   322: dup
      //   323: invokespecial 50	org/json/JSONObject:<init>	()V
      //   326: invokeinterface 141 3 0
      //   331: return
      //   332: astore 10
      //   334: ldc 143
      //   336: aload 10
      //   338: invokestatic 148	com/google/android/gms/ads/internal/util/client/zzb:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
      //   341: goto -51 -> 290
      //   344: iconst_0
      //   345: istore 21
      //   347: goto -67 -> 280
      //   350: astore 22
      //   352: ldc 150
      //   354: aload 22
      //   356: invokestatic 148	com/google/android/gms/ads/internal/util/client/zzb:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
      //   359: goto -69 -> 290
      //   362: aload_1
      //   363: ldc 137
      //   365: aload 8
      //   367: invokeinterface 141 3 0
      //   372: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	373	0	this	4
      //   0	373	1	paramAnonymouszzjo	zzjo
      //   0	373	2	paramAnonymousMap	Map<String, String>
      //   9	258	3	localPackageManager	PackageManager
      //   21	7	4	str1	String
      //   32	3	5	localJSONObject1	org.json.JSONObject
      //   314	1	6	localJSONException1	org.json.JSONException
      //   41	25	7	localJSONArray	org.json.JSONArray
      //   50	316	8	localJSONObject2	org.json.JSONObject
      //   53	238	9	i	int
      //   332	5	10	localJSONException2	org.json.JSONException
      //   72	65	11	localJSONObject3	org.json.JSONObject
      //   81	202	12	str2	String
      //   90	74	13	str3	String
      //   99	84	14	str4	String
      //   108	91	15	str5	String
      //   117	98	16	str6	String
      //   126	103	17	str7	String
      //   151	117	20	localIntent	Intent
      //   278	68	21	bool	boolean
      //   350	5	22	localJSONException3	org.json.JSONException
      //   236	20	24	arrayOfString	String[]
      //   296	1	30	localJSONException4	org.json.JSONException
      // Exception table:
      //   from	to	target	type
      //   23	34	296	org/json/JSONException
      //   34	43	314	org/json/JSONException
      //   65	74	332	org/json/JSONException
      //   280	290	350	org/json/JSONException
    }
  };
  public static final zzdm zzzc = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      str1 = (String)paramAnonymousMap.get("u");
      if (str1 == null)
      {
        zzb.w("URL missing from click GMSG.");
        return;
      }
      localUri1 = Uri.parse(str1);
      try
      {
        zzao localzzao = paramAnonymouszzjo.zzhM();
        if ((localzzao == null) || (!localzzao.isGoogleAdUrl(localUri1))) {
          break label127;
        }
        Uri localUri3 = localzzao.zzb(localUri1, paramAnonymouszzjo.getContext());
        localUri2 = localUri3;
      }
      catch (zzap localzzap)
      {
        for (;;)
        {
          String str2;
          zzb.w("Unable to append parameter to URL: " + str1);
          Uri localUri2 = localUri1;
        }
      }
      str2 = localUri2.toString();
      new zzix(paramAnonymouszzjo.getContext(), paramAnonymouszzjo.zzhN().afmaVersion, str2).zzhf();
    }
  };
  public static final zzdm zzzd = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      zzd localzzd1 = paramAnonymouszzjo.zzhI();
      if (localzzd1 != null)
      {
        localzzd1.close();
        return;
      }
      zzd localzzd2 = paramAnonymouszzjo.zzhJ();
      if (localzzd2 != null)
      {
        localzzd2.close();
        return;
      }
      zzb.w("A GMSG tried to close something that wasn't an overlay.");
    }
  };
  public static final zzdm zzze = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      paramAnonymouszzjo.zzE("1".equals(paramAnonymousMap.get("custom_close")));
    }
  };
  public static final zzdm zzzf = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      String str = (String)paramAnonymousMap.get("u");
      if (str == null)
      {
        zzb.w("URL missing from httpTrack GMSG.");
        return;
      }
      new zzix(paramAnonymouszzjo.getContext(), paramAnonymouszzjo.zzhN().afmaVersion, str).zzhf();
    }
  };
  public static final zzdm zzzg = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      zzb.i("Received log message: " + (String)paramAnonymousMap.get("string"));
    }
  };
  public static final zzdm zzzh = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      String str1 = (String)paramAnonymousMap.get("tx");
      String str2 = (String)paramAnonymousMap.get("ty");
      String str3 = (String)paramAnonymousMap.get("td");
      try
      {
        int i = Integer.parseInt(str1);
        int j = Integer.parseInt(str2);
        int k = Integer.parseInt(str3);
        zzao localzzao = paramAnonymouszzjo.zzhM();
        if (localzzao != null) {
          localzzao.zzoj.zza(i, j, k);
        }
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        zzb.w("Could not parse touch parameters from gmsg.");
      }
    }
  };
  public static final zzdm zzzi = new zzdm()
  {
    public final void zza(zzjo paramAnonymouszzjo, Map<String, String> paramAnonymousMap)
    {
      Flag localFlag = Flags.zzxc;
      if (!((Boolean)zzp.zzbR().zzd(localFlag)).booleanValue()) {
        return;
      }
      if (!Boolean.parseBoolean((String)paramAnonymousMap.get("disabled"))) {}
      for (boolean bool = true;; bool = false)
      {
        paramAnonymouszzjo.zzF(bool);
        return;
      }
    }
  };
  public static final zzdm zzzj = new zzdu();
  public static final zzdm zzzk = new zzdy();
  public static final zzdm zzzl = new zzdk();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzdl
 * JD-Core Version:    0.7.0.1
 */