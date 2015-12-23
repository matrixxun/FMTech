package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.measurement.zza;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

public final class zzm
  extends zzw
{
  private static final X500Principal zzbmT = new X500Principal("CN=Android Debug,O=Android,C=US");
  private String mAppName;
  private String mAppVersion;
  private String zzbkS;
  private String zzblT;
  private String zzblX;
  private long zzbmU;
  
  zzm(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  private boolean zzCn()
  {
    try
    {
      PackageInfo localPackageInfo = super.getContext().getPackageManager().getPackageInfo(super.getContext().getPackageName(), 64);
      if ((localPackageInfo != null) && (localPackageInfo.signatures != null) && (localPackageInfo.signatures.length > 0))
      {
        Signature localSignature = localPackageInfo.signatures[0];
        boolean bool = ((X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(localSignature.toByteArray()))).getSubjectX500Principal().equals(zzbmT);
        return bool;
      }
    }
    catch (CertificateException localCertificateException)
    {
      super.zzBh().zzbmW.zzm("Error obtaining certificate", localCertificateException);
      return true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        super.zzBh().zzbmW.zzm("Package name not found", localNameNotFoundException);
      }
    }
  }
  
  /* Error */
  protected final void onInitialize()
  {
    // Byte code:
    //   0: lconst_0
    //   1: lstore_1
    //   2: ldc 126
    //   4: astore_3
    //   5: ldc 126
    //   7: astore 4
    //   9: aload_0
    //   10: invokespecial 39	com/google/android/gms/measurement/internal/zzw:getContext	()Landroid/content/Context;
    //   13: invokevirtual 45	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   16: astore 5
    //   18: aload_0
    //   19: invokespecial 39	com/google/android/gms/measurement/internal/zzw:getContext	()Landroid/content/Context;
    //   22: invokevirtual 49	android/content/Context:getPackageName	()Ljava/lang/String;
    //   25: astore 6
    //   27: aload 5
    //   29: aload 6
    //   31: invokevirtual 130	android/content/pm/PackageManager:getInstallerPackageName	(Ljava/lang/String;)Ljava/lang/String;
    //   34: astore 7
    //   36: aload 7
    //   38: ifnonnull +250 -> 288
    //   41: ldc 132
    //   43: astore 7
    //   45: aload 5
    //   47: aload_0
    //   48: invokespecial 39	com/google/android/gms/measurement/internal/zzw:getContext	()Landroid/content/Context;
    //   51: invokevirtual 49	android/content/Context:getPackageName	()Ljava/lang/String;
    //   54: iconst_0
    //   55: invokevirtual 55	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   58: astore 25
    //   60: aload 25
    //   62: ifnull +38 -> 100
    //   65: aload 5
    //   67: aload 25
    //   69: getfield 136	android/content/pm/PackageInfo:applicationInfo	Landroid/content/pm/ApplicationInfo;
    //   72: invokevirtual 140	android/content/pm/PackageManager:getApplicationLabel	(Landroid/content/pm/ApplicationInfo;)Ljava/lang/CharSequence;
    //   75: astore 26
    //   77: aload 26
    //   79: invokestatic 146	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   82: ifne +12 -> 94
    //   85: aload 26
    //   87: invokeinterface 151 1 0
    //   92: astore 4
    //   94: aload 25
    //   96: getfield 154	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   99: astore_3
    //   100: aload_0
    //   101: aload 6
    //   103: putfield 156	com/google/android/gms/measurement/internal/zzm:zzbkS	Ljava/lang/String;
    //   106: aload_0
    //   107: aload 7
    //   109: putfield 158	com/google/android/gms/measurement/internal/zzm:zzblX	Ljava/lang/String;
    //   112: aload_0
    //   113: aload_3
    //   114: putfield 160	com/google/android/gms/measurement/internal/zzm:mAppVersion	Ljava/lang/String;
    //   117: aload_0
    //   118: aload 4
    //   120: putfield 162	com/google/android/gms/measurement/internal/zzm:mAppName	Ljava/lang/String;
    //   123: ldc 164
    //   125: invokestatic 169	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   128: astore 11
    //   130: aload_0
    //   131: invokespecial 171	com/google/android/gms/measurement/internal/zzm:zzCn	()Z
    //   134: ifne +196 -> 330
    //   137: aload 5
    //   139: aload_0
    //   140: invokespecial 39	com/google/android/gms/measurement/internal/zzw:getContext	()Landroid/content/Context;
    //   143: invokevirtual 49	android/content/Context:getPackageName	()Ljava/lang/String;
    //   146: bipush 64
    //   148: invokevirtual 55	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   151: astore 14
    //   153: aload 11
    //   155: ifnull +175 -> 330
    //   158: aload 14
    //   160: getfield 61	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   163: ifnull +167 -> 330
    //   166: aload 14
    //   168: getfield 61	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   171: arraylength
    //   172: ifle +158 -> 330
    //   175: aload 11
    //   177: aload 14
    //   179: getfield 61	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   182: iconst_0
    //   183: aaload
    //   184: invokevirtual 77	android/content/pm/Signature:toByteArray	()[B
    //   187: invokevirtual 175	java/security/MessageDigest:digest	([B)[B
    //   190: astore 15
    //   192: aload 15
    //   194: invokestatic 181	com/google/android/gms/common/internal/zzx:zzC	(Ljava/lang/Object;)Ljava/lang/Object;
    //   197: pop
    //   198: aload 15
    //   200: arraylength
    //   201: ifle +123 -> 324
    //   204: iconst_1
    //   205: istore 17
    //   207: iload 17
    //   209: invokestatic 185	com/google/android/gms/common/internal/zzx:zzaa	(Z)V
    //   212: iconst_m1
    //   213: aload 15
    //   215: arraylength
    //   216: iadd
    //   217: istore 18
    //   219: iconst_0
    //   220: istore 19
    //   222: iload 18
    //   224: istore 20
    //   226: lload_1
    //   227: lstore 12
    //   229: iload 20
    //   231: iflt +102 -> 333
    //   234: iload 20
    //   236: bipush 248
    //   238: aload 15
    //   240: arraylength
    //   241: iadd
    //   242: if_icmplt +91 -> 333
    //   245: aload 15
    //   247: iload 20
    //   249: baload
    //   250: istore 21
    //   252: lload 12
    //   254: ldc2_w 186
    //   257: iload 21
    //   259: i2l
    //   260: land
    //   261: iload 19
    //   263: lshl
    //   264: ladd
    //   265: lstore 22
    //   267: iload 19
    //   269: bipush 8
    //   271: iadd
    //   272: istore 24
    //   274: iinc 20 255
    //   277: iload 24
    //   279: istore 19
    //   281: lload 22
    //   283: lstore 12
    //   285: goto -56 -> 229
    //   288: ldc 189
    //   290: aload 7
    //   292: invokevirtual 192	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   295: ifeq -250 -> 45
    //   298: ldc 194
    //   300: astore 7
    //   302: goto -257 -> 45
    //   305: astore 8
    //   307: aload_0
    //   308: invokespecial 98	com/google/android/gms/measurement/internal/zzw:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   311: getfield 104	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   314: ldc 196
    //   316: aload 4
    //   318: invokevirtual 112	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
    //   321: goto -221 -> 100
    //   324: iconst_0
    //   325: istore 17
    //   327: goto -120 -> 207
    //   330: lload_1
    //   331: lstore 12
    //   333: lload 12
    //   335: lstore_1
    //   336: aload_0
    //   337: lload_1
    //   338: putfield 198	com/google/android/gms/measurement/internal/zzm:zzbmU	J
    //   341: return
    //   342: astore 10
    //   344: aload_0
    //   345: invokespecial 98	com/google/android/gms/measurement/internal/zzw:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   348: getfield 104	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   351: ldc 200
    //   353: aload 10
    //   355: invokevirtual 112	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
    //   358: goto -22 -> 336
    //   361: astore 9
    //   363: aload_0
    //   364: invokespecial 98	com/google/android/gms/measurement/internal/zzw:zzBh	()Lcom/google/android/gms/measurement/internal/zzo;
    //   367: getfield 104	com/google/android/gms/measurement/internal/zzo:zzbmW	Lcom/google/android/gms/measurement/internal/zzo$zza;
    //   370: ldc 114
    //   372: aload 9
    //   374: invokevirtual 112	com/google/android/gms/measurement/internal/zzo$zza:zzm	(Ljava/lang/String;Ljava/lang/Object;)V
    //   377: goto -41 -> 336
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	380	0	this	zzm
    //   1	337	1	l1	long
    //   4	110	3	str1	String
    //   7	310	4	str2	String
    //   16	122	5	localPackageManager	PackageManager
    //   25	77	6	str3	String
    //   34	267	7	str4	String
    //   305	1	8	localNameNotFoundException1	PackageManager.NameNotFoundException
    //   361	12	9	localNameNotFoundException2	PackageManager.NameNotFoundException
    //   342	12	10	localNoSuchAlgorithmException	java.security.NoSuchAlgorithmException
    //   128	48	11	localMessageDigest	java.security.MessageDigest
    //   227	107	12	l2	long
    //   151	27	14	localPackageInfo1	PackageInfo
    //   190	56	15	arrayOfByte	byte[]
    //   205	121	17	bool	boolean
    //   217	6	18	i	int
    //   220	60	19	j	int
    //   224	51	20	k	int
    //   250	8	21	m	int
    //   265	17	22	l3	long
    //   272	6	24	n	int
    //   58	37	25	localPackageInfo2	PackageInfo
    //   75	11	26	localCharSequence	java.lang.CharSequence
    // Exception table:
    //   from	to	target	type
    //   45	60	305	android/content/pm/PackageManager$NameNotFoundException
    //   65	94	305	android/content/pm/PackageManager$NameNotFoundException
    //   94	100	305	android/content/pm/PackageManager$NameNotFoundException
    //   123	153	342	java/security/NoSuchAlgorithmException
    //   158	204	342	java/security/NoSuchAlgorithmException
    //   207	219	342	java/security/NoSuchAlgorithmException
    //   234	252	342	java/security/NoSuchAlgorithmException
    //   123	153	361	android/content/pm/PackageManager$NameNotFoundException
    //   158	204	361	android/content/pm/PackageManager$NameNotFoundException
    //   207	219	361	android/content/pm/PackageManager$NameNotFoundException
    //   234	252	361	android/content/pm/PackageManager$NameNotFoundException
  }
  
  final String zzCl()
  {
    zziL();
    if (zzc.isPackageSide()) {
      return "";
    }
    Status localStatus;
    if (this.zzblT == null)
    {
      localStatus = zza.zzaS(super.getContext());
      if ((localStatus == null) || (!localStatus.isSuccess())) {}
    }
    else
    {
      for (;;)
      {
        try
        {
          if (!zza.zzBk()) {
            continue;
          }
          String str = zza.zzBi();
          if (TextUtils.isEmpty(str)) {
            str = "";
          }
          this.zzblT = str;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          this.zzblT = "";
          super.zzBh().zzbmW.zzm("getGoogleAppId or isMeasurementEnabled failed with exception", localIllegalStateException);
          continue;
        }
        return this.zzblT;
        this.zzblT = "";
      }
    }
    this.zzblT = "";
    zzo.zza localzza = super.zzBh().zzbmW;
    if (localStatus == null) {}
    for (int i = 0;; i = localStatus.zzakr)
    {
      localzza.zzm("getGoogleAppId failed with status", Integer.valueOf(i));
      if ((localStatus == null) || (localStatus.zzanv == null)) {
        break;
      }
      super.zzBh().zzbnd.zzeB(localStatus.zzanv);
      break;
    }
  }
  
  final AppMetadata zzez(String paramString)
  {
    String str1 = this.zzbkS;
    String str2 = zzCl();
    String str3 = this.mAppVersion;
    String str4 = this.zzblX;
    long l = zzc.zzBH();
    zziL();
    return new AppMetadata(str1, str2, str3, str4, l, this.zzbmU, paramString, super.zzBZ().zzBk());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzm
 * JD-Core Version:    0.7.0.1
 */