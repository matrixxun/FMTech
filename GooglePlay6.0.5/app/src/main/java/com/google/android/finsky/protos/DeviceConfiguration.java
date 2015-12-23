package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface DeviceConfiguration
{
  public static final class DeviceConfigurationProto
    extends MessageNano
  {
    public int glEsVersion = 0;
    public String[] glExtension = WireFormatNano.EMPTY_STRING_ARRAY;
    public boolean hasFiveWayNavigation = false;
    public boolean hasGlEsVersion = false;
    public boolean hasHardKeyboard = false;
    public boolean hasHasFiveWayNavigation = false;
    public boolean hasHasHardKeyboard = false;
    public boolean hasKeyboard = false;
    public boolean hasLowRamDevice = false;
    public boolean hasMaxApkDownloadSizeMb = false;
    public boolean hasMaxNumOfCpuCores = false;
    public boolean hasNavigation = false;
    public boolean hasScreenDensity = false;
    public boolean hasScreenHeight = false;
    public boolean hasScreenLayout = false;
    public boolean hasScreenWidth = false;
    public boolean hasSmallestScreenWidthDp = false;
    public boolean hasTotalMemoryBytes = false;
    public boolean hasTouchScreen = false;
    public int keyboard = 0;
    public boolean lowRamDevice = false;
    public int maxApkDownloadSizeMb = 50;
    public int maxNumOfCpuCores = 0;
    public String[] nativePlatform = WireFormatNano.EMPTY_STRING_ARRAY;
    public int navigation = 0;
    public int screenDensity = 0;
    public int screenHeight = 0;
    public int screenLayout = 0;
    public int screenWidth = 0;
    public int smallestScreenWidthDp = 0;
    public String[] systemAvailableFeature = WireFormatNano.EMPTY_STRING_ARRAY;
    public String[] systemSharedLibrary = WireFormatNano.EMPTY_STRING_ARRAY;
    public String[] systemSupportedLocale = WireFormatNano.EMPTY_STRING_ARRAY;
    public long totalMemoryBytes = 0L;
    public int touchScreen = 0;
    
    public DeviceConfigurationProto()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.touchScreen != 0) || (this.hasTouchScreen)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.touchScreen);
      }
      if ((this.keyboard != 0) || (this.hasKeyboard)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.keyboard);
      }
      if ((this.navigation != 0) || (this.hasNavigation)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.navigation);
      }
      if ((this.screenLayout != 0) || (this.hasScreenLayout)) {
        i += CodedOutputByteBufferNano.computeInt32Size(4, this.screenLayout);
      }
      if ((this.hasHasHardKeyboard) || (this.hasHardKeyboard)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(5);
      }
      if ((this.hasHasFiveWayNavigation) || (this.hasFiveWayNavigation)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(6);
      }
      if ((this.hasScreenDensity) || (this.screenDensity != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(7, this.screenDensity);
      }
      if ((this.hasGlEsVersion) || (this.glEsVersion != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.glEsVersion);
      }
      if ((this.systemSharedLibrary != null) && (this.systemSharedLibrary.length > 0))
      {
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < this.systemSharedLibrary.length; i11++)
        {
          String str5 = this.systemSharedLibrary[i11];
          if (str5 != null)
          {
            i9++;
            i10 += CodedOutputByteBufferNano.computeStringSizeNoTag(str5);
          }
        }
        i = i + i10 + i9 * 1;
      }
      if ((this.systemAvailableFeature != null) && (this.systemAvailableFeature.length > 0))
      {
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < this.systemAvailableFeature.length; i8++)
        {
          String str4 = this.systemAvailableFeature[i8];
          if (str4 != null)
          {
            i6++;
            i7 += CodedOutputByteBufferNano.computeStringSizeNoTag(str4);
          }
        }
        i = i + i7 + i6 * 1;
      }
      if ((this.nativePlatform != null) && (this.nativePlatform.length > 0))
      {
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.nativePlatform.length; i5++)
        {
          String str3 = this.nativePlatform[i5];
          if (str3 != null)
          {
            i3++;
            i4 += CodedOutputByteBufferNano.computeStringSizeNoTag(str3);
          }
        }
        i = i + i4 + i3 * 1;
      }
      if ((this.hasScreenWidth) || (this.screenWidth != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(12, this.screenWidth);
      }
      if ((this.hasScreenHeight) || (this.screenHeight != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(13, this.screenHeight);
      }
      if ((this.systemSupportedLocale != null) && (this.systemSupportedLocale.length > 0))
      {
        int n = 0;
        int i1 = 0;
        for (int i2 = 0; i2 < this.systemSupportedLocale.length; i2++)
        {
          String str2 = this.systemSupportedLocale[i2];
          if (str2 != null)
          {
            n++;
            i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
          }
        }
        i = i + i1 + n * 1;
      }
      if ((this.glExtension != null) && (this.glExtension.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.glExtension.length; m++)
        {
          String str1 = this.glExtension[m];
          if (str1 != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
          }
        }
        i = i + k + j * 1;
      }
      if ((this.hasMaxApkDownloadSizeMb) || (this.maxApkDownloadSizeMb != 50)) {
        i += CodedOutputByteBufferNano.computeInt32Size(17, this.maxApkDownloadSizeMb);
      }
      if ((this.hasSmallestScreenWidthDp) || (this.smallestScreenWidthDp != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(18, this.smallestScreenWidthDp);
      }
      if ((this.hasLowRamDevice) || (this.lowRamDevice)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(19);
      }
      if ((this.hasTotalMemoryBytes) || (this.totalMemoryBytes != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(20, this.totalMemoryBytes);
      }
      if ((this.hasMaxNumOfCpuCores) || (this.maxNumOfCpuCores != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(21, this.maxNumOfCpuCores);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.touchScreen != 0) || (this.hasTouchScreen)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.touchScreen);
      }
      if ((this.keyboard != 0) || (this.hasKeyboard)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.keyboard);
      }
      if ((this.navigation != 0) || (this.hasNavigation)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.navigation);
      }
      if ((this.screenLayout != 0) || (this.hasScreenLayout)) {
        paramCodedOutputByteBufferNano.writeInt32(4, this.screenLayout);
      }
      if ((this.hasHasHardKeyboard) || (this.hasHardKeyboard)) {
        paramCodedOutputByteBufferNano.writeBool(5, this.hasHardKeyboard);
      }
      if ((this.hasHasFiveWayNavigation) || (this.hasFiveWayNavigation)) {
        paramCodedOutputByteBufferNano.writeBool(6, this.hasFiveWayNavigation);
      }
      if ((this.hasScreenDensity) || (this.screenDensity != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(7, this.screenDensity);
      }
      if ((this.hasGlEsVersion) || (this.glEsVersion != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.glEsVersion);
      }
      if ((this.systemSharedLibrary != null) && (this.systemSharedLibrary.length > 0)) {
        for (int n = 0; n < this.systemSharedLibrary.length; n++)
        {
          String str5 = this.systemSharedLibrary[n];
          if (str5 != null) {
            paramCodedOutputByteBufferNano.writeString(9, str5);
          }
        }
      }
      if ((this.systemAvailableFeature != null) && (this.systemAvailableFeature.length > 0)) {
        for (int m = 0; m < this.systemAvailableFeature.length; m++)
        {
          String str4 = this.systemAvailableFeature[m];
          if (str4 != null) {
            paramCodedOutputByteBufferNano.writeString(10, str4);
          }
        }
      }
      if ((this.nativePlatform != null) && (this.nativePlatform.length > 0)) {
        for (int k = 0; k < this.nativePlatform.length; k++)
        {
          String str3 = this.nativePlatform[k];
          if (str3 != null) {
            paramCodedOutputByteBufferNano.writeString(11, str3);
          }
        }
      }
      if ((this.hasScreenWidth) || (this.screenWidth != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(12, this.screenWidth);
      }
      if ((this.hasScreenHeight) || (this.screenHeight != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(13, this.screenHeight);
      }
      if ((this.systemSupportedLocale != null) && (this.systemSupportedLocale.length > 0)) {
        for (int j = 0; j < this.systemSupportedLocale.length; j++)
        {
          String str2 = this.systemSupportedLocale[j];
          if (str2 != null) {
            paramCodedOutputByteBufferNano.writeString(14, str2);
          }
        }
      }
      if ((this.glExtension != null) && (this.glExtension.length > 0)) {
        for (int i = 0; i < this.glExtension.length; i++)
        {
          String str1 = this.glExtension[i];
          if (str1 != null) {
            paramCodedOutputByteBufferNano.writeString(15, str1);
          }
        }
      }
      if ((this.hasMaxApkDownloadSizeMb) || (this.maxApkDownloadSizeMb != 50)) {
        paramCodedOutputByteBufferNano.writeInt32(17, this.maxApkDownloadSizeMb);
      }
      if ((this.hasSmallestScreenWidthDp) || (this.smallestScreenWidthDp != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(18, this.smallestScreenWidthDp);
      }
      if ((this.hasLowRamDevice) || (this.lowRamDevice)) {
        paramCodedOutputByteBufferNano.writeBool(19, this.lowRamDevice);
      }
      if ((this.hasTotalMemoryBytes) || (this.totalMemoryBytes != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(20, this.totalMemoryBytes);
      }
      if ((this.hasMaxNumOfCpuCores) || (this.maxNumOfCpuCores != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(21, this.maxNumOfCpuCores);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.DeviceConfiguration
 * JD-Core Version:    0.7.0.1
 */