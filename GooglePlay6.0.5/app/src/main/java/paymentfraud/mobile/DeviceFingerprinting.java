package paymentfraud.mobile;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class DeviceFingerprinting
  extends MessageNano
{
  public Parsed parsed;
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if (this.parsed != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(2, this.parsed);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if (this.parsed != null) {
      paramCodedOutputByteBufferNano.writeMessage(2, this.parsed);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
  
  public static final class Parsed
    extends MessageNano
  {
    public Properties properties = null;
    public State state = null;
    
    public Parsed()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.properties != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.properties);
      }
      if (this.state != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.state);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.properties != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.properties);
      }
      if (this.state != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.state);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class Properties
      extends MessageNano
    {
      public String androidBuildBrand = "";
      public long androidId = 0L;
      public String buildFingerprint = "";
      public String deviceName = "";
      public String esn = "";
      public String imei = "";
      public String manufacturer = "";
      public String meid = "";
      public String modelName = "";
      public int operatingSystem = 100;
      public String osVersion = "";
      public String phoneNumber = "";
      public String productName = "";
      
      public Properties()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.operatingSystem != 100) {
          i += CodedOutputByteBufferNano.computeInt32Size(1, this.operatingSystem);
        }
        if (!this.imei.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(2, this.imei);
        }
        if (!this.meid.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(3, this.meid);
        }
        if (!this.esn.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(5, this.esn);
        }
        if (!this.phoneNumber.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(6, this.phoneNumber);
        }
        if (this.androidId != 0L) {
          i += CodedOutputByteBufferNano.computeInt64Size(7, this.androidId);
        }
        if (!this.deviceName.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(9, this.deviceName);
        }
        if (!this.productName.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(10, this.productName);
        }
        if (!this.modelName.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(11, this.modelName);
        }
        if (!this.manufacturer.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(12, this.manufacturer);
        }
        if (!this.buildFingerprint.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(13, this.buildFingerprint);
        }
        if (!this.osVersion.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(15, this.osVersion);
        }
        if (!this.androidBuildBrand.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(21, this.androidBuildBrand);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.operatingSystem != 100) {
          paramCodedOutputByteBufferNano.writeInt32(1, this.operatingSystem);
        }
        if (!this.imei.equals("")) {
          paramCodedOutputByteBufferNano.writeString(2, this.imei);
        }
        if (!this.meid.equals("")) {
          paramCodedOutputByteBufferNano.writeString(3, this.meid);
        }
        if (!this.esn.equals("")) {
          paramCodedOutputByteBufferNano.writeString(5, this.esn);
        }
        if (!this.phoneNumber.equals("")) {
          paramCodedOutputByteBufferNano.writeString(6, this.phoneNumber);
        }
        if (this.androidId != 0L) {
          paramCodedOutputByteBufferNano.writeInt64(7, this.androidId);
        }
        if (!this.deviceName.equals("")) {
          paramCodedOutputByteBufferNano.writeString(9, this.deviceName);
        }
        if (!this.productName.equals("")) {
          paramCodedOutputByteBufferNano.writeString(10, this.productName);
        }
        if (!this.modelName.equals("")) {
          paramCodedOutputByteBufferNano.writeString(11, this.modelName);
        }
        if (!this.manufacturer.equals("")) {
          paramCodedOutputByteBufferNano.writeString(12, this.manufacturer);
        }
        if (!this.buildFingerprint.equals("")) {
          paramCodedOutputByteBufferNano.writeString(13, this.buildFingerprint);
        }
        if (!this.osVersion.equals("")) {
          paramCodedOutputByteBufferNano.writeString(15, this.osVersion);
        }
        if (!this.androidBuildBrand.equals("")) {
          paramCodedOutputByteBufferNano.writeString(21, this.androidBuildBrand);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class State
      extends MessageNano
    {
      public String cellOperator = "";
      public boolean devModeOn = false;
      public String[] emailAccounts = WireFormatNano.EMPTY_STRING_ARRAY;
      public long gmtOffsetMillis = -86400000L;
      public PackageInfo[] installedPackages = PackageInfo.emptyArray();
      public String[] ipAddr = WireFormatNano.EMPTY_STRING_ARRAY;
      public String language = "";
      public Location lastGpsLocation = null;
      public String locale = "";
      public boolean nonPlayInstallAllowed = false;
      public int percentBattery = -1;
      public String simOperator = "";
      
      public State()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.installedPackages != null) && (this.installedPackages.length > 0)) {
          for (int i3 = 0; i3 < this.installedPackages.length; i3++)
          {
            PackageInfo localPackageInfo = this.installedPackages[i3];
            if (localPackageInfo != null) {
              i += CodedOutputByteBufferNano.computeMessageSize(1, localPackageInfo);
            }
          }
        }
        if ((this.emailAccounts != null) && (this.emailAccounts.length > 0))
        {
          int n = 0;
          int i1 = 0;
          for (int i2 = 0; i2 < this.emailAccounts.length; i2++)
          {
            String str2 = this.emailAccounts[i2];
            if (str2 != null)
            {
              n++;
              i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str2);
            }
          }
          i = i + i1 + n * 1;
        }
        if (this.percentBattery != -1) {
          i += CodedOutputByteBufferNano.computeInt32Size(3, this.percentBattery);
        }
        if (this.gmtOffsetMillis != -86400000L) {
          i += CodedOutputByteBufferNano.computeInt64Size(4, this.gmtOffsetMillis);
        }
        if (this.lastGpsLocation != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(6, this.lastGpsLocation);
        }
        if (this.devModeOn) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(7);
        }
        if (this.nonPlayInstallAllowed) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(8);
        }
        if (!this.language.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(9, this.language);
        }
        if ((this.ipAddr != null) && (this.ipAddr.length > 0))
        {
          int j = 0;
          int k = 0;
          for (int m = 0; m < this.ipAddr.length; m++)
          {
            String str1 = this.ipAddr[m];
            if (str1 != null)
            {
              j++;
              k += CodedOutputByteBufferNano.computeStringSizeNoTag(str1);
            }
          }
          i = i + k + j * 1;
        }
        if (!this.locale.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(11, this.locale);
        }
        if (!this.cellOperator.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(14, this.cellOperator);
        }
        if (!this.simOperator.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(15, this.simOperator);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.installedPackages != null) && (this.installedPackages.length > 0)) {
          for (int k = 0; k < this.installedPackages.length; k++)
          {
            PackageInfo localPackageInfo = this.installedPackages[k];
            if (localPackageInfo != null) {
              paramCodedOutputByteBufferNano.writeMessage(1, localPackageInfo);
            }
          }
        }
        if ((this.emailAccounts != null) && (this.emailAccounts.length > 0)) {
          for (int j = 0; j < this.emailAccounts.length; j++)
          {
            String str2 = this.emailAccounts[j];
            if (str2 != null) {
              paramCodedOutputByteBufferNano.writeString(2, str2);
            }
          }
        }
        if (this.percentBattery != -1) {
          paramCodedOutputByteBufferNano.writeInt32(3, this.percentBattery);
        }
        if (this.gmtOffsetMillis != -86400000L) {
          paramCodedOutputByteBufferNano.writeInt64(4, this.gmtOffsetMillis);
        }
        if (this.lastGpsLocation != null) {
          paramCodedOutputByteBufferNano.writeMessage(6, this.lastGpsLocation);
        }
        if (this.devModeOn) {
          paramCodedOutputByteBufferNano.writeBool(7, this.devModeOn);
        }
        if (this.nonPlayInstallAllowed) {
          paramCodedOutputByteBufferNano.writeBool(8, this.nonPlayInstallAllowed);
        }
        if (!this.language.equals("")) {
          paramCodedOutputByteBufferNano.writeString(9, this.language);
        }
        if ((this.ipAddr != null) && (this.ipAddr.length > 0)) {
          for (int i = 0; i < this.ipAddr.length; i++)
          {
            String str1 = this.ipAddr[i];
            if (str1 != null) {
              paramCodedOutputByteBufferNano.writeString(10, str1);
            }
          }
        }
        if (!this.locale.equals("")) {
          paramCodedOutputByteBufferNano.writeString(11, this.locale);
        }
        if (!this.cellOperator.equals("")) {
          paramCodedOutputByteBufferNano.writeString(14, this.cellOperator);
        }
        if (!this.simOperator.equals("")) {
          paramCodedOutputByteBufferNano.writeString(15, this.simOperator);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
      
      public static final class Location
        extends MessageNano
      {
        public float accuracy = 0.0F;
        public double altitude = 0.0D;
        public double latitude = 0.0D;
        public double longitude = 0.0D;
        public double timeInMs = -1.0D;
        
        public Location()
        {
          this.cachedSize = -1;
        }
        
        protected final int computeSerializedSize()
        {
          int i = super.computeSerializedSize();
          if (Double.doubleToLongBits(this.altitude) != Double.doubleToLongBits(0.0D)) {
            i += 8 + CodedOutputByteBufferNano.computeTagSize(1);
          }
          if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(0.0D)) {
            i += 8 + CodedOutputByteBufferNano.computeTagSize(2);
          }
          if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(0.0D)) {
            i += 8 + CodedOutputByteBufferNano.computeTagSize(3);
          }
          if (Float.floatToIntBits(this.accuracy) != Float.floatToIntBits(0.0F)) {
            i += 4 + CodedOutputByteBufferNano.computeTagSize(4);
          }
          if (Double.doubleToLongBits(this.timeInMs) != Double.doubleToLongBits(-1.0D)) {
            i += 8 + CodedOutputByteBufferNano.computeTagSize(5);
          }
          return i;
        }
        
        public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
          throws IOException
        {
          if (Double.doubleToLongBits(this.altitude) != Double.doubleToLongBits(0.0D)) {
            paramCodedOutputByteBufferNano.writeDouble(1, this.altitude);
          }
          if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(0.0D)) {
            paramCodedOutputByteBufferNano.writeDouble(2, this.latitude);
          }
          if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(0.0D)) {
            paramCodedOutputByteBufferNano.writeDouble(3, this.longitude);
          }
          if (Float.floatToIntBits(this.accuracy) != Float.floatToIntBits(0.0F)) {
            paramCodedOutputByteBufferNano.writeFloat(4, this.accuracy);
          }
          if (Double.doubleToLongBits(this.timeInMs) != Double.doubleToLongBits(-1.0D)) {
            paramCodedOutputByteBufferNano.writeDouble(5, this.timeInMs);
          }
          super.writeTo(paramCodedOutputByteBufferNano);
        }
      }
      
      public static final class PackageInfo
        extends MessageNano
      {
        private static volatile PackageInfo[] _emptyArray;
        public long firstInstallTime = -1L;
        public String installLocation = "";
        public long lastUpdateTime = -1L;
        public String name = "";
        public String versionCode = "";
        
        public PackageInfo()
        {
          this.cachedSize = -1;
        }
        
        public static PackageInfo[] emptyArray()
        {
          if (_emptyArray == null) {}
          synchronized (InternalNano.LAZY_INIT_LOCK)
          {
            if (_emptyArray == null) {
              _emptyArray = new PackageInfo[0];
            }
            return _emptyArray;
          }
        }
        
        protected final int computeSerializedSize()
        {
          int i = super.computeSerializedSize();
          if (!this.name.equals("")) {
            i += CodedOutputByteBufferNano.computeStringSize(1, this.name);
          }
          if (!this.versionCode.equals("")) {
            i += CodedOutputByteBufferNano.computeStringSize(2, this.versionCode);
          }
          if (this.lastUpdateTime != -1L) {
            i += CodedOutputByteBufferNano.computeInt64Size(3, this.lastUpdateTime);
          }
          if (this.firstInstallTime != -1L) {
            i += CodedOutputByteBufferNano.computeInt64Size(4, this.firstInstallTime);
          }
          if (!this.installLocation.equals("")) {
            i += CodedOutputByteBufferNano.computeStringSize(5, this.installLocation);
          }
          return i;
        }
        
        public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
          throws IOException
        {
          if (!this.name.equals("")) {
            paramCodedOutputByteBufferNano.writeString(1, this.name);
          }
          if (!this.versionCode.equals("")) {
            paramCodedOutputByteBufferNano.writeString(2, this.versionCode);
          }
          if (this.lastUpdateTime != -1L) {
            paramCodedOutputByteBufferNano.writeInt64(3, this.lastUpdateTime);
          }
          if (this.firstInstallTime != -1L) {
            paramCodedOutputByteBufferNano.writeInt64(4, this.firstInstallTime);
          }
          if (!this.installLocation.equals("")) {
            paramCodedOutputByteBufferNano.writeString(5, this.installLocation);
          }
          super.writeTo(paramCodedOutputByteBufferNano);
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     paymentfraud.mobile.DeviceFingerprinting
 * JD-Core Version:    0.7.0.1
 */