package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class UserNotificationData
  extends MessageNano
{
  public String dialogText = "";
  public String dialogTitle = "";
  public boolean hasDialogText = false;
  public boolean hasDialogTitle = false;
  public boolean hasNotificationText = false;
  public boolean hasNotificationTitle = false;
  public boolean hasTickerText = false;
  public String notificationText = "";
  public String notificationTitle = "";
  public String tickerText = "";
  
  public UserNotificationData()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasNotificationTitle) || (!this.notificationTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(1, this.notificationTitle);
    }
    if ((this.hasNotificationText) || (!this.notificationText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.notificationText);
    }
    if ((this.hasTickerText) || (!this.tickerText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(3, this.tickerText);
    }
    if ((this.hasDialogTitle) || (!this.dialogTitle.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(4, this.dialogTitle);
    }
    if ((this.hasDialogText) || (!this.dialogText.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(5, this.dialogText);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasNotificationTitle) || (!this.notificationTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(1, this.notificationTitle);
    }
    if ((this.hasNotificationText) || (!this.notificationText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.notificationText);
    }
    if ((this.hasTickerText) || (!this.tickerText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(3, this.tickerText);
    }
    if ((this.hasDialogTitle) || (!this.dialogTitle.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(4, this.dialogTitle);
    }
    if ((this.hasDialogText) || (!this.dialogText.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(5, this.dialogText);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.UserNotificationData
 * JD-Core Version:    0.7.0.1
 */