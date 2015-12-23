package com.android.ex.photo.util;

import android.content.ContentResolver;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.Base64;
import android.util.Log;
import com.android.ex.photo.PhotoViewController;
import com.android.ex.photo.loaders.PhotoBitmapLoaderInterface.BitmapResult;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ImageUtils
{
  private static final Pattern BASE64_IMAGE_URI_PATTERN = Pattern.compile("^(?:.*;)?base64,.*");
  public static final ImageSize sUseImageSize = ImageSize.EXTRA_SMALL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      sUseImageSize = ImageSize.NORMAL;
      return;
    }
    if (PhotoViewController.sMemoryClass >= 32L)
    {
      sUseImageSize = ImageSize.NORMAL;
      return;
    }
    if (PhotoViewController.sMemoryClass >= 24L)
    {
      sUseImageSize = ImageSize.SMALL;
      return;
    }
  }
  
  public static PhotoBitmapLoaderInterface.BitmapResult createLocalBitmap(ContentResolver paramContentResolver, Uri paramUri, int paramInt)
  {
    PhotoBitmapLoaderInterface.BitmapResult localBitmapResult = new PhotoBitmapLoaderInterface.BitmapResult();
    if ("data".equals(paramUri.getScheme())) {}
    for (Object localObject = new DataInputStreamFactory(paramContentResolver, paramUri);; localObject = new BaseInputStreamFactory(paramContentResolver, paramUri)) {
      try
      {
        BitmapFactory.Options localOptions1 = new BitmapFactory.Options();
        localOptions1.inJustDecodeBounds = true;
        decodeStream$44dd74d5((InputStreamFactory)localObject, localOptions1);
        Point localPoint = new Point(localOptions1.outWidth, localOptions1.outHeight);
        BitmapFactory.Options localOptions2 = new BitmapFactory.Options();
        localOptions2.inSampleSize = Math.max(localPoint.x / paramInt, localPoint.y / paramInt);
        localBitmapResult.bitmap = decodeStream$44dd74d5((InputStreamFactory)localObject, localOptions2);
        localBitmapResult.status = 0;
        return localBitmapResult;
      }
      catch (IOException localIOException)
      {
        localBitmapResult.status = 1;
        return localBitmapResult;
      }
      catch (SecurityException localSecurityException)
      {
        localBitmapResult.status = 1;
        return localBitmapResult;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        return localBitmapResult;
      }
      catch (FileNotFoundException localFileNotFoundException) {}
    }
    return localBitmapResult;
  }
  
  /* Error */
  private static android.graphics.Bitmap decodeStream$44dd74d5(InputStreamFactory paramInputStreamFactory, BitmapFactory.Options paramOptions)
    throws FileNotFoundException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: invokeinterface 139 1 0
    //   8: astore_2
    //   9: aload_2
    //   10: invokestatic 145	com/android/ex/photo/util/Exif:getOrientation$4cd69bc7	(Ljava/io/InputStream;)I
    //   13: istore 12
    //   15: aload_2
    //   16: ifnull +7 -> 23
    //   19: aload_2
    //   20: invokevirtual 150	java/io/InputStream:close	()V
    //   23: aload_0
    //   24: invokeinterface 139 1 0
    //   29: astore_2
    //   30: aload_2
    //   31: aconst_null
    //   32: aload_1
    //   33: invokestatic 156	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   36: astore 10
    //   38: aload_2
    //   39: ifnull +59 -> 98
    //   42: aload 10
    //   44: ifnonnull +54 -> 98
    //   47: aload_1
    //   48: getfield 91	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   51: ifne +47 -> 98
    //   54: ldc 158
    //   56: ldc 160
    //   58: invokestatic 166	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   61: pop
    //   62: new 168	java/lang/UnsupportedOperationException
    //   65: dup
    //   66: ldc 170
    //   68: invokespecial 173	java/lang/UnsupportedOperationException:<init>	(Ljava/lang/String;)V
    //   71: athrow
    //   72: astore 8
    //   74: ldc 158
    //   76: ldc 175
    //   78: aload 8
    //   80: invokestatic 179	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   83: pop
    //   84: aload_2
    //   85: ifnull +7 -> 92
    //   88: aload_2
    //   89: invokevirtual 150	java/io/InputStream:close	()V
    //   92: aconst_null
    //   93: astore 10
    //   95: aload 10
    //   97: areturn
    //   98: aload 10
    //   100: ifnull +68 -> 168
    //   103: iload 12
    //   105: ifeq +63 -> 168
    //   108: new 181	android/graphics/Matrix
    //   111: dup
    //   112: invokespecial 182	android/graphics/Matrix:<init>	()V
    //   115: astore 14
    //   117: aload 14
    //   119: iload 12
    //   121: i2f
    //   122: invokevirtual 186	android/graphics/Matrix:postRotate	(F)Z
    //   125: pop
    //   126: aload 10
    //   128: iconst_0
    //   129: iconst_0
    //   130: aload 10
    //   132: invokevirtual 192	android/graphics/Bitmap:getWidth	()I
    //   135: aload 10
    //   137: invokevirtual 195	android/graphics/Bitmap:getHeight	()I
    //   140: aload 14
    //   142: iconst_1
    //   143: invokestatic 199	android/graphics/Bitmap:createBitmap	(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;
    //   146: astore 16
    //   148: aload 16
    //   150: astore 10
    //   152: aload_2
    //   153: ifnull -58 -> 95
    //   156: aload_2
    //   157: invokevirtual 150	java/io/InputStream:close	()V
    //   160: aload 10
    //   162: areturn
    //   163: astore 17
    //   165: aload 10
    //   167: areturn
    //   168: aload_2
    //   169: ifnull -74 -> 95
    //   172: aload_2
    //   173: invokevirtual 150	java/io/InputStream:close	()V
    //   176: aload 10
    //   178: areturn
    //   179: astore 13
    //   181: aload 10
    //   183: areturn
    //   184: astore 5
    //   186: ldc 158
    //   188: ldc 201
    //   190: aload 5
    //   192: invokestatic 179	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   195: pop
    //   196: aload_2
    //   197: ifnull +7 -> 204
    //   200: aload_2
    //   201: invokevirtual 150	java/io/InputStream:close	()V
    //   204: aconst_null
    //   205: areturn
    //   206: astore_3
    //   207: aload_2
    //   208: ifnull +7 -> 215
    //   211: aload_2
    //   212: invokevirtual 150	java/io/InputStream:close	()V
    //   215: aload_3
    //   216: athrow
    //   217: astore 11
    //   219: goto -127 -> 92
    //   222: astore 7
    //   224: goto -20 -> 204
    //   227: astore 4
    //   229: goto -14 -> 215
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	232	0	paramInputStreamFactory	InputStreamFactory
    //   0	232	1	paramOptions	BitmapFactory.Options
    //   1	211	2	localInputStream	InputStream
    //   206	10	3	localObject1	Object
    //   227	1	4	localIOException1	IOException
    //   184	7	5	localIOException2	IOException
    //   222	1	7	localIOException3	IOException
    //   72	7	8	localOutOfMemoryError	java.lang.OutOfMemoryError
    //   36	146	10	localObject2	Object
    //   217	1	11	localIOException4	IOException
    //   13	107	12	i	int
    //   179	1	13	localIOException5	IOException
    //   115	26	14	localMatrix	android.graphics.Matrix
    //   146	3	16	localBitmap	android.graphics.Bitmap
    //   163	1	17	localIOException6	IOException
    // Exception table:
    //   from	to	target	type
    //   2	15	72	java/lang/OutOfMemoryError
    //   19	23	72	java/lang/OutOfMemoryError
    //   23	38	72	java/lang/OutOfMemoryError
    //   47	72	72	java/lang/OutOfMemoryError
    //   108	148	72	java/lang/OutOfMemoryError
    //   156	160	163	java/io/IOException
    //   172	176	179	java/io/IOException
    //   2	15	184	java/io/IOException
    //   19	23	184	java/io/IOException
    //   23	38	184	java/io/IOException
    //   47	72	184	java/io/IOException
    //   108	148	184	java/io/IOException
    //   2	15	206	finally
    //   19	23	206	finally
    //   23	38	206	finally
    //   47	72	206	finally
    //   74	84	206	finally
    //   108	148	206	finally
    //   186	196	206	finally
    //   88	92	217	java/io/IOException
    //   200	204	222	java/io/IOException
    //   211	215	227	java/io/IOException
  }
  
  private static class BaseInputStreamFactory
    implements ImageUtils.InputStreamFactory
  {
    protected final ContentResolver mResolver;
    protected final Uri mUri;
    
    public BaseInputStreamFactory(ContentResolver paramContentResolver, Uri paramUri)
    {
      this.mResolver = paramContentResolver;
      this.mUri = paramUri;
    }
    
    public InputStream createInputStream()
      throws FileNotFoundException
    {
      return this.mResolver.openInputStream(this.mUri);
    }
  }
  
  private static final class DataInputStreamFactory
    extends ImageUtils.BaseInputStreamFactory
  {
    private byte[] mData;
    
    public DataInputStreamFactory(ContentResolver paramContentResolver, Uri paramUri)
    {
      super(paramUri);
    }
    
    private static byte[] parseDataUri(Uri paramUri)
    {
      String str = paramUri.getSchemeSpecificPart();
      try
      {
        if (str.startsWith("base64,")) {
          return Base64.decode(str.substring(7), 8);
        }
        if (ImageUtils.BASE64_IMAGE_URI_PATTERN.matcher(str).matches())
        {
          byte[] arrayOfByte = Base64.decode(str.substring(7 + str.indexOf("base64,")), 0);
          return arrayOfByte;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Log.e("ImageUtils", "Mailformed data URI: " + localIllegalArgumentException);
      }
      return null;
    }
    
    public final InputStream createInputStream()
      throws FileNotFoundException
    {
      if (this.mData == null)
      {
        this.mData = parseDataUri(this.mUri);
        if (this.mData == null) {
          return super.createInputStream();
        }
      }
      return new ByteArrayInputStream(this.mData);
    }
  }
  
  public static enum ImageSize
  {
    static
    {
      NORMAL = new ImageSize("NORMAL", 2);
      ImageSize[] arrayOfImageSize = new ImageSize[3];
      arrayOfImageSize[0] = EXTRA_SMALL;
      arrayOfImageSize[1] = SMALL;
      arrayOfImageSize[2] = NORMAL;
      $VALUES = arrayOfImageSize;
    }
    
    private ImageSize() {}
  }
  
  public static abstract interface InputStreamFactory
  {
    public abstract InputStream createInputStream()
      throws FileNotFoundException;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.util.ImageUtils
 * JD-Core Version:    0.7.0.1
 */