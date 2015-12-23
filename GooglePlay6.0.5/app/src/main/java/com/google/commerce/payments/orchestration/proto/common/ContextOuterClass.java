package com.google.commerce.payments.orchestration.proto.common;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;
import paymentfraud.mobile.DeviceFingerprinting.Parsed;

public abstract interface ContextOuterClass
{
  public static final class NativeClientContext
    extends MessageNano
  {
    public int androidClientSubtype = 0;
    public String device = "";
    public String gid1 = "";
    public String imsiHash = "";
    public String integratorPackageName = "";
    public String marketClientId = "";
    public String mccMnc = "";
    public String osVersion = "";
    public String packageName = "";
    public String packageVersionCode = "";
    public String packageVersionName = "";
    public int readPhoneStatePermissionState = 0;
    public DeviceFingerprinting.Parsed riskData = null;
    public long roundedImsi = 0L;
    public int screenHeightPx = 0;
    public int screenWidthPx = 0;
    public float screenXDpi = 0.0F;
    public float screenYDpi = 0.0F;
    public String simOperatorName = "";
    
    public NativeClientContext()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (!this.imsiHash.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(1, this.imsiHash);
      }
      if (!this.mccMnc.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.mccMnc);
      }
      if (!this.osVersion.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.osVersion);
      }
      if (!this.device.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(4, this.device);
      }
      if (this.screenWidthPx != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.screenWidthPx);
      }
      if (this.screenHeightPx != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.screenHeightPx);
      }
      if (Float.floatToIntBits(this.screenXDpi) != Float.floatToIntBits(0.0F)) {
        i += 4 + CodedOutputByteBufferNano.computeTagSize(7);
      }
      if (Float.floatToIntBits(this.screenYDpi) != Float.floatToIntBits(0.0F)) {
        i += 4 + CodedOutputByteBufferNano.computeTagSize(8);
      }
      if (!this.packageName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.packageName);
      }
      if (!this.packageVersionCode.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(10, this.packageVersionCode);
      }
      if (!this.packageVersionName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(11, this.packageVersionName);
      }
      if (this.riskData != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.riskData);
      }
      if (!this.integratorPackageName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(13, this.integratorPackageName);
      }
      if (!this.marketClientId.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(14, this.marketClientId);
      }
      if (this.androidClientSubtype != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(15, this.androidClientSubtype);
      }
      if (this.readPhoneStatePermissionState != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(16, this.readPhoneStatePermissionState);
      }
      if (!this.simOperatorName.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(17, this.simOperatorName);
      }
      if (!this.gid1.equals("")) {
        i += CodedOutputByteBufferNano.computeStringSize(18, this.gid1);
      }
      if (this.roundedImsi != 0L) {
        i += CodedOutputByteBufferNano.computeInt64Size(19, this.roundedImsi);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (!this.imsiHash.equals("")) {
        paramCodedOutputByteBufferNano.writeString(1, this.imsiHash);
      }
      if (!this.mccMnc.equals("")) {
        paramCodedOutputByteBufferNano.writeString(2, this.mccMnc);
      }
      if (!this.osVersion.equals("")) {
        paramCodedOutputByteBufferNano.writeString(3, this.osVersion);
      }
      if (!this.device.equals("")) {
        paramCodedOutputByteBufferNano.writeString(4, this.device);
      }
      if (this.screenWidthPx != 0) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.screenWidthPx);
      }
      if (this.screenHeightPx != 0) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.screenHeightPx);
      }
      if (Float.floatToIntBits(this.screenXDpi) != Float.floatToIntBits(0.0F)) {
        paramCodedOutputByteBufferNano.writeFloat(7, this.screenXDpi);
      }
      if (Float.floatToIntBits(this.screenYDpi) != Float.floatToIntBits(0.0F)) {
        paramCodedOutputByteBufferNano.writeFloat(8, this.screenYDpi);
      }
      if (!this.packageName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(9, this.packageName);
      }
      if (!this.packageVersionCode.equals("")) {
        paramCodedOutputByteBufferNano.writeString(10, this.packageVersionCode);
      }
      if (!this.packageVersionName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(11, this.packageVersionName);
      }
      if (this.riskData != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.riskData);
      }
      if (!this.integratorPackageName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(13, this.integratorPackageName);
      }
      if (!this.marketClientId.equals("")) {
        paramCodedOutputByteBufferNano.writeString(14, this.marketClientId);
      }
      if (this.androidClientSubtype != 0) {
        paramCodedOutputByteBufferNano.writeInt32(15, this.androidClientSubtype);
      }
      if (this.readPhoneStatePermissionState != 0) {
        paramCodedOutputByteBufferNano.writeInt32(16, this.readPhoneStatePermissionState);
      }
      if (!this.simOperatorName.equals("")) {
        paramCodedOutputByteBufferNano.writeString(17, this.simOperatorName);
      }
      if (!this.gid1.equals("")) {
        paramCodedOutputByteBufferNano.writeString(18, this.gid1);
      }
      if (this.roundedImsi != 0L) {
        paramCodedOutputByteBufferNano.writeInt64(19, this.roundedImsi);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.common.ContextOuterClass
 * JD-Core Version:    0.7.0.1
 */