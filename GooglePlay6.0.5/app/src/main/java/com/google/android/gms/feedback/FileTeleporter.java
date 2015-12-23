package com.google.android.gms.feedback;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class FileTeleporter
  implements SafeParcelable
{
  public static final Parcelable.Creator<FileTeleporter> CREATOR = new zzc();
  final String mMimeType;
  final int mVersionCode;
  ParcelFileDescriptor zzHC;
  final String zzaLl;
  private byte[] zzaLm;
  public File zzaro;
  
  FileTeleporter(int paramInt, ParcelFileDescriptor paramParcelFileDescriptor, String paramString1, String paramString2)
  {
    this.mVersionCode = paramInt;
    this.zzHC = paramParcelFileDescriptor;
    this.mMimeType = paramString1;
    this.zzaLl = paramString2;
  }
  
  private static void zza(Closeable paramCloseable)
  {
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException)
    {
      Log.w("FileTeleporter", "Could not close stream", localIOException);
    }
  }
  
  /* Error */
  private java.io.FileOutputStream zzpE()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 64	com/google/android/gms/feedback/FileTeleporter:zzaro	Ljava/io/File;
    //   4: ifnonnull +13 -> 17
    //   7: new 66	java/lang/IllegalStateException
    //   10: dup
    //   11: ldc 68
    //   13: invokespecial 71	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   16: athrow
    //   17: ldc 73
    //   19: ldc 75
    //   21: aload_0
    //   22: getfield 64	com/google/android/gms/feedback/FileTeleporter:zzaro	Ljava/io/File;
    //   25: invokestatic 81	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)Ljava/io/File;
    //   28: astore_2
    //   29: new 83	java/io/FileOutputStream
    //   32: dup
    //   33: aload_2
    //   34: invokespecial 86	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   37: astore_3
    //   38: aload_0
    //   39: aload_2
    //   40: ldc 87
    //   42: invokestatic 93	android/os/ParcelFileDescriptor:open	(Ljava/io/File;I)Landroid/os/ParcelFileDescriptor;
    //   45: putfield 35	com/google/android/gms/feedback/FileTeleporter:zzHC	Landroid/os/ParcelFileDescriptor;
    //   48: aload_2
    //   49: invokevirtual 97	java/io/File:delete	()Z
    //   52: pop
    //   53: aload_3
    //   54: areturn
    //   55: astore_1
    //   56: new 66	java/lang/IllegalStateException
    //   59: dup
    //   60: ldc 99
    //   62: aload_1
    //   63: invokespecial 102	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   66: athrow
    //   67: astore 4
    //   69: new 66	java/lang/IllegalStateException
    //   72: dup
    //   73: ldc 104
    //   75: invokespecial 71	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   78: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	79	0	this	FileTeleporter
    //   55	8	1	localIOException	IOException
    //   28	21	2	localFile	File
    //   37	17	3	localFileOutputStream	java.io.FileOutputStream
    //   67	1	4	localFileNotFoundException	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   17	29	55	java/io/IOException
    //   29	48	67	java/io/FileNotFoundException
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    DataOutputStream localDataOutputStream;
    if (this.zzHC == null) {
      localDataOutputStream = new DataOutputStream(zzpE());
    }
    try
    {
      localDataOutputStream.writeInt(this.zzaLm.length);
      localDataOutputStream.writeUTF(this.mMimeType);
      localDataOutputStream.writeUTF(this.zzaLl);
      localDataOutputStream.write(this.zzaLm);
      zza(localDataOutputStream);
      zzc.zza(this, paramParcel, paramInt);
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("Could not write into unlinked file", localIOException);
    }
    finally
    {
      zza(localDataOutputStream);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.feedback.FileTeleporter
 * JD-Core Version:    0.7.0.1
 */