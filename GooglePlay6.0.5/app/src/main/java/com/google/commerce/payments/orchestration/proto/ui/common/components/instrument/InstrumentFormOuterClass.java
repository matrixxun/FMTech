package com.google.commerce.payments.orchestration.proto.ui.common.components.instrument;

import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.Dcb.DcbVerifyAssociationForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.Dcb.DcbVerifyAssociationFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.UsernamePassword.UsernamePasswordForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.UsernamePassword.UsernamePasswordFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectFormValue;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface InstrumentFormOuterClass
{
  public static final class InstrumentForm
    extends MessageNano
  {
    public CardFormOuterClass.CardForm card = null;
    public CreditCard.CreditCardForm creditCard = null;
    public Dcb.DcbVerifyAssociationForm dcbVerifyAssociation = null;
    public RedirectFormOuterClass.RedirectForm redirect = null;
    public SimpleFormOuterClass.SimpleForm simpleForm = null;
    public UsernamePassword.UsernamePasswordForm usernamePassword = null;
    
    public InstrumentForm()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.creditCard != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.creditCard);
      }
      if (this.dcbVerifyAssociation != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(5, this.dcbVerifyAssociation);
      }
      if (this.usernamePassword != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.usernamePassword);
      }
      if (this.simpleForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.simpleForm);
      }
      if (this.card != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(14, this.card);
      }
      if (this.redirect != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(15, this.redirect);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.creditCard != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.creditCard);
      }
      if (this.dcbVerifyAssociation != null) {
        paramCodedOutputByteBufferNano.writeMessage(5, this.dcbVerifyAssociation);
      }
      if (this.usernamePassword != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.usernamePassword);
      }
      if (this.simpleForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.simpleForm);
      }
      if (this.card != null) {
        paramCodedOutputByteBufferNano.writeMessage(14, this.card);
      }
      if (this.redirect != null) {
        paramCodedOutputByteBufferNano.writeMessage(15, this.redirect);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class InstrumentFormValue
    extends MessageNano
  {
    public CardFormOuterClass.CardFormValue card = null;
    public CreditCard.CreditCardFormValue creditCard = null;
    public Dcb.DcbVerifyAssociationFormValue dcbVerifyAssociation = null;
    public RedirectFormOuterClass.RedirectFormValue redirect = null;
    public SimpleFormOuterClass.SimpleFormValue simpleForm = null;
    public UsernamePassword.UsernamePasswordFormValue usernamePassword = null;
    
    public InstrumentFormValue()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.creditCard != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.creditCard);
      }
      if (this.dcbVerifyAssociation != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.dcbVerifyAssociation);
      }
      if (this.usernamePassword != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(6, this.usernamePassword);
      }
      if (this.simpleForm != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(11, this.simpleForm);
      }
      if (this.card != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(12, this.card);
      }
      if (this.redirect != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(13, this.redirect);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.creditCard != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.creditCard);
      }
      if (this.dcbVerifyAssociation != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.dcbVerifyAssociation);
      }
      if (this.usernamePassword != null) {
        paramCodedOutputByteBufferNano.writeMessage(6, this.usernamePassword);
      }
      if (this.simpleForm != null) {
        paramCodedOutputByteBufferNano.writeMessage(11, this.simpleForm);
      }
      if (this.card != null) {
        paramCodedOutputByteBufferNano.writeMessage(12, this.card);
      }
      if (this.redirect != null) {
        paramCodedOutputByteBufferNano.writeMessage(13, this.redirect);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass
 * JD-Core Version:    0.7.0.1
 */