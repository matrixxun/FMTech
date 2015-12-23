package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Notification
  extends MessageNano
{
  private static volatile Notification[] _emptyArray;
  public AndroidAppNotificationData appData = null;
  public AndroidAppDeliveryData appDeliveryData = null;
  public String docTitle = "";
  public Common.Docid docid = null;
  public boolean hasDocTitle = false;
  public boolean hasNotificationId = false;
  public boolean hasNotificationType = false;
  public boolean hasTimestamp = false;
  public boolean hasUserEmail = false;
  public InAppNotificationData inAppNotificationData = null;
  public LibraryDirtyData libraryDirtyData = null;
  public LibraryUpdateProto.LibraryUpdate libraryUpdate = null;
  public String notificationId = "";
  public int notificationType = 1;
  public PurchaseDeclinedData purchaseDeclinedData = null;
  public PurchaseRemovalData purchaseRemovalData = null;
  public long timestamp = 0L;
  public String userEmail = "";
  public UserNotificationData userNotificationData = null;
  public UserSettingDirtyData userSettingDirtyData = null;
  
  public Notification()
  {
    this.cachedSize = -1;
  }
  
  public static Notification[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new Notification[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.notificationType != 1) || (this.hasNotificationType)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.notificationType);
    }
    if ((this.hasTimestamp) || (this.timestamp != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(3, this.timestamp);
    }
    if (this.docid != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(4, this.docid);
    }
    if ((this.hasDocTitle) || (!this.docTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.docTitle);
    }
    if ((this.hasUserEmail) || (!this.userEmail.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(6, this.userEmail);
    }
    if (this.appData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(7, this.appData);
    }
    if (this.appDeliveryData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(8, this.appDeliveryData);
    }
    if (this.purchaseRemovalData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(9, this.purchaseRemovalData);
    }
    if (this.userNotificationData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(10, this.userNotificationData);
    }
    if (this.inAppNotificationData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(11, this.inAppNotificationData);
    }
    if (this.purchaseDeclinedData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(12, this.purchaseDeclinedData);
    }
    if ((this.hasNotificationId) || (!this.notificationId.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(13, this.notificationId);
    }
    if (this.libraryUpdate != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(14, this.libraryUpdate);
    }
    if (this.libraryDirtyData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(15, this.libraryDirtyData);
    }
    if (this.userSettingDirtyData != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(16, this.userSettingDirtyData);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.notificationType != 1) || (this.hasNotificationType)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.notificationType);
    }
    if ((this.hasTimestamp) || (this.timestamp != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(3, this.timestamp);
    }
    if (this.docid != null) {
      paramCodedOutputByteBufferNano.writeMessage(4, this.docid);
    }
    if ((this.hasDocTitle) || (!this.docTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.docTitle);
    }
    if ((this.hasUserEmail) || (!this.userEmail.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(6, this.userEmail);
    }
    if (this.appData != null) {
      paramCodedOutputByteBufferNano.writeMessage(7, this.appData);
    }
    if (this.appDeliveryData != null) {
      paramCodedOutputByteBufferNano.writeMessage(8, this.appDeliveryData);
    }
    if (this.purchaseRemovalData != null) {
      paramCodedOutputByteBufferNano.writeMessage(9, this.purchaseRemovalData);
    }
    if (this.userNotificationData != null) {
      paramCodedOutputByteBufferNano.writeMessage(10, this.userNotificationData);
    }
    if (this.inAppNotificationData != null) {
      paramCodedOutputByteBufferNano.writeMessage(11, this.inAppNotificationData);
    }
    if (this.purchaseDeclinedData != null) {
      paramCodedOutputByteBufferNano.writeMessage(12, this.purchaseDeclinedData);
    }
    if ((this.hasNotificationId) || (!this.notificationId.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(13, this.notificationId);
    }
    if (this.libraryUpdate != null) {
      paramCodedOutputByteBufferNano.writeMessage(14, this.libraryUpdate);
    }
    if (this.libraryDirtyData != null) {
      paramCodedOutputByteBufferNano.writeMessage(15, this.libraryDirtyData);
    }
    if (this.userSettingDirtyData != null) {
      paramCodedOutputByteBufferNano.writeMessage(16, this.userSettingDirtyData);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Notification
 * JD-Core Version:    0.7.0.1
 */