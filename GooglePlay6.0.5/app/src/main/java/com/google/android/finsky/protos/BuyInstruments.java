package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface BuyInstruments
{
  public static final class BillingProfileResponse
    extends MessageNano
  {
    public BillingProfileProtos.BillingProfile billingProfile = null;
    public boolean hasResult = false;
    public boolean hasUserMessageHtml = false;
    public int result = 1;
    public String userMessageHtml = "";
    
    public BillingProfileResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.result != 1) || (this.hasResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.result);
      }
      if (this.billingProfile != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.billingProfile);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.userMessageHtml);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.result != 1) || (this.hasResult)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.result);
      }
      if (this.billingProfile != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.billingProfile);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.userMessageHtml);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CheckIabPromoResponse
    extends MessageNano
  {
    public boolean actualResult = false;
    public boolean eligible = false;
    public boolean hasActualResult = false;
    public boolean hasEligible = false;
    
    public CheckIabPromoResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasEligible) || (this.eligible)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.hasActualResult) || (this.actualResult)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasEligible) || (this.eligible)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.eligible);
      }
      if ((this.hasActualResult) || (this.actualResult)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.actualResult);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CheckInstrumentResponse
    extends MessageNano
  {
    public boolean hasUserHasValidInstrument = false;
    public boolean userHasValidInstrument = false;
    
    public CheckInstrumentResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasUserHasValidInstrument) || (this.userHasValidInstrument)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasUserHasValidInstrument) || (this.userHasValidInstrument)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.userHasValidInstrument);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class CreateInstrumentResponse
    extends MessageNano
  {
    public CreateInstrument.DeviceCreateInstrumentFlowState createInstrumentFlowState = null;
    public boolean hasInstrumentId = false;
    public boolean hasResult = false;
    public boolean hasUserMessageHtml = false;
    public String instrumentId = "";
    public int result = 1;
    public String userMessageHtml = "";
    
    public CreateInstrumentResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.result != 1) || (this.hasResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.result);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.userMessageHtml);
      }
      if ((this.hasInstrumentId) || (!this.instrumentId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.instrumentId);
      }
      if (this.createInstrumentFlowState != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.createInstrumentFlowState);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.result != 1) || (this.hasResult)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.result);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.userMessageHtml);
      }
      if ((this.hasInstrumentId) || (!this.instrumentId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.instrumentId);
      }
      if (this.createInstrumentFlowState != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.createInstrumentFlowState);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class GetInitialInstrumentFlowStateResponse
    extends MessageNano
  {
    public CreateInstrument.DeviceCreateInstrumentFlowState createInstrumentFlowState = null;
    public CreateInstrument.DeviceCreateInstrumentMetadata createInstrumentMetadata = null;
    public boolean hasResult = false;
    public boolean hasUserMessageHtml = false;
    public int result = 1;
    public String userMessageHtml = "";
    
    public GetInitialInstrumentFlowStateResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.result != 1) || (this.hasResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.result);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.userMessageHtml);
      }
      if (this.createInstrumentFlowState != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.createInstrumentFlowState);
      }
      if (this.createInstrumentMetadata != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.createInstrumentMetadata);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.result != 1) || (this.hasResult)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.result);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.userMessageHtml);
      }
      if (this.createInstrumentFlowState != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.createInstrumentFlowState);
      }
      if (this.createInstrumentMetadata != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.createInstrumentMetadata);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InstrumentSetupInfoResponse
    extends MessageNano
  {
    public InstrumentSetupInfo[] setupInfo = InstrumentSetupInfo.emptyArray();
    
    public InstrumentSetupInfoResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.setupInfo != null) && (this.setupInfo.length > 0)) {
        for (int j = 0; j < this.setupInfo.length; j++)
        {
          InstrumentSetupInfo localInstrumentSetupInfo = this.setupInfo[j];
          if (localInstrumentSetupInfo != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localInstrumentSetupInfo);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.setupInfo != null) && (this.setupInfo.length > 0)) {
        for (int i = 0; i < this.setupInfo.length; i++)
        {
          InstrumentSetupInfo localInstrumentSetupInfo = this.setupInfo[i];
          if (localInstrumentSetupInfo != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localInstrumentSetupInfo);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RedeemGiftCardResponse
    extends MessageNano
  {
    public ChallengeProto.AddressChallenge addressChallenge = null;
    public String balanceHtml = "";
    public boolean hasBalanceHtml = false;
    public boolean hasResult = false;
    public boolean hasUserMessageHtml = false;
    public int result = 0;
    public String userMessageHtml = "";
    
    public RedeemGiftCardResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.result != 0) || (this.hasResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.result);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.userMessageHtml);
      }
      if ((this.hasBalanceHtml) || (!this.balanceHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.balanceHtml);
      }
      if (this.addressChallenge != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.addressChallenge);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.result != 0) || (this.hasResult)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.result);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.userMessageHtml);
      }
      if ((this.hasBalanceHtml) || (!this.balanceHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.balanceHtml);
      }
      if (this.addressChallenge != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.addressChallenge);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UpdateInstrumentRequest
    extends MessageNano
  {
    public String checkoutToken = "";
    public boolean hasCheckoutToken = false;
    public Instrument instrument = null;
    
    public UpdateInstrumentRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.instrument != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.instrument);
      }
      if ((this.hasCheckoutToken) || (!this.checkoutToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.checkoutToken);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.instrument != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.instrument);
      }
      if ((this.hasCheckoutToken) || (!this.checkoutToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.checkoutToken);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UpdateInstrumentResponse
    extends MessageNano
  {
    public ChallengeProto.InputValidationError[] errorInputField = ChallengeProto.InputValidationError.emptyArray();
    public boolean hasInstrumentId = false;
    public boolean hasResult = false;
    public boolean hasUserMessageHtml = false;
    public String instrumentId = "";
    public RedeemedPromoOffer redeemedOffer = null;
    public int result = 0;
    public String userMessageHtml = "";
    
    public UpdateInstrumentResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.result != 0) || (this.hasResult)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.result);
      }
      if ((this.hasInstrumentId) || (!this.instrumentId.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.instrumentId);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.userMessageHtml);
      }
      if ((this.errorInputField != null) && (this.errorInputField.length > 0)) {
        for (int j = 0; j < this.errorInputField.length; j++)
        {
          ChallengeProto.InputValidationError localInputValidationError = this.errorInputField[j];
          if (localInputValidationError != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localInputValidationError);
          }
        }
      }
      if (this.redeemedOffer != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.redeemedOffer);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.result != 0) || (this.hasResult)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.result);
      }
      if ((this.hasInstrumentId) || (!this.instrumentId.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.instrumentId);
      }
      if ((this.hasUserMessageHtml) || (!this.userMessageHtml.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.userMessageHtml);
      }
      if ((this.errorInputField != null) && (this.errorInputField.length > 0)) {
        for (int i = 0; i < this.errorInputField.length; i++)
        {
          ChallengeProto.InputValidationError localInputValidationError = this.errorInputField[i];
          if (localInputValidationError != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localInputValidationError);
          }
        }
      }
      if (this.redeemedOffer != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.redeemedOffer);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.BuyInstruments
 * JD-Core Version:    0.7.0.1
 */