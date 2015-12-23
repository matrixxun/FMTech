package com.google.commerce.payments.orchestration.proto.ui.common.generic;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

public abstract interface DependencyGraphOuterClass
{
  public static final class ComponentValueReference
    extends MessageNano
  {
    private static volatile ComponentValueReference[] _emptyArray;
    public int componentId = 0;
    public int[] valueId = WireFormatNano.EMPTY_INT_ARRAY;
    
    public ComponentValueReference()
    {
      this.cachedSize = -1;
    }
    
    public static ComponentValueReference[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ComponentValueReference[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.componentId != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.componentId);
      }
      if ((this.valueId != null) && (this.valueId.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.valueId.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.valueId[k]);
        }
        i = i + j + 1 * this.valueId.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.componentId != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.componentId);
      }
      if ((this.valueId != null) && (this.valueId.length > 0)) {
        for (int i = 0; i < this.valueId.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(2, this.valueId[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ComponentValuesDependency
    extends MessageNano
  {
    private static volatile ComponentValuesDependency[] _emptyArray;
    public DependencyGraphOuterClass.ComponentValueReference[] applicableValueId = DependencyGraphOuterClass.ComponentValueReference.emptyArray();
    public DependencyGraphOuterClass.ResultingActionReference[] resultingActionReference = DependencyGraphOuterClass.ResultingActionReference.emptyArray();
    public DependencyGraphOuterClass.ComponentValueReference[] selectedValueId = DependencyGraphOuterClass.ComponentValueReference.emptyArray();
    public DependencyGraphOuterClass.TriggerValueReference[] triggerValueReference = DependencyGraphOuterClass.TriggerValueReference.emptyArray();
    
    public ComponentValuesDependency()
    {
      this.cachedSize = -1;
    }
    
    public static ComponentValuesDependency[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ComponentValuesDependency[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.selectedValueId != null) && (this.selectedValueId.length > 0)) {
        for (int n = 0; n < this.selectedValueId.length; n++)
        {
          DependencyGraphOuterClass.ComponentValueReference localComponentValueReference2 = this.selectedValueId[n];
          if (localComponentValueReference2 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localComponentValueReference2);
          }
        }
      }
      if ((this.applicableValueId != null) && (this.applicableValueId.length > 0)) {
        for (int m = 0; m < this.applicableValueId.length; m++)
        {
          DependencyGraphOuterClass.ComponentValueReference localComponentValueReference1 = this.applicableValueId[m];
          if (localComponentValueReference1 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localComponentValueReference1);
          }
        }
      }
      if ((this.triggerValueReference != null) && (this.triggerValueReference.length > 0)) {
        for (int k = 0; k < this.triggerValueReference.length; k++)
        {
          DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = this.triggerValueReference[k];
          if (localTriggerValueReference != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(3, localTriggerValueReference);
          }
        }
      }
      if ((this.resultingActionReference != null) && (this.resultingActionReference.length > 0)) {
        for (int j = 0; j < this.resultingActionReference.length; j++)
        {
          DependencyGraphOuterClass.ResultingActionReference localResultingActionReference = this.resultingActionReference[j];
          if (localResultingActionReference != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(4, localResultingActionReference);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.selectedValueId != null) && (this.selectedValueId.length > 0)) {
        for (int m = 0; m < this.selectedValueId.length; m++)
        {
          DependencyGraphOuterClass.ComponentValueReference localComponentValueReference2 = this.selectedValueId[m];
          if (localComponentValueReference2 != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localComponentValueReference2);
          }
        }
      }
      if ((this.applicableValueId != null) && (this.applicableValueId.length > 0)) {
        for (int k = 0; k < this.applicableValueId.length; k++)
        {
          DependencyGraphOuterClass.ComponentValueReference localComponentValueReference1 = this.applicableValueId[k];
          if (localComponentValueReference1 != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localComponentValueReference1);
          }
        }
      }
      if ((this.triggerValueReference != null) && (this.triggerValueReference.length > 0)) {
        for (int j = 0; j < this.triggerValueReference.length; j++)
        {
          DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = this.triggerValueReference[j];
          if (localTriggerValueReference != null) {
            paramCodedOutputByteBufferNano.writeMessage(3, localTriggerValueReference);
          }
        }
      }
      if ((this.resultingActionReference != null) && (this.resultingActionReference.length > 0)) {
        for (int i = 0; i < this.resultingActionReference.length; i++)
        {
          DependencyGraphOuterClass.ResultingActionReference localResultingActionReference = this.resultingActionReference[i];
          if (localResultingActionReference != null) {
            paramCodedOutputByteBufferNano.writeMessage(4, localResultingActionReference);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ComponentsDependency
    extends MessageNano
  {
    private static volatile ComponentsDependency[] _emptyArray;
    public int[] affectedComponentId = WireFormatNano.EMPTY_INT_ARRAY;
    public int[] affectingComponentId = WireFormatNano.EMPTY_INT_ARRAY;
    public int subjectComponentId = 0;
    
    public ComponentsDependency()
    {
      this.cachedSize = -1;
    }
    
    public static ComponentsDependency[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ComponentsDependency[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.subjectComponentId != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.subjectComponentId);
      }
      if ((this.affectingComponentId != null) && (this.affectingComponentId.length > 0))
      {
        int m = 0;
        for (int n = 0; n < this.affectingComponentId.length; n++) {
          m += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.affectingComponentId[n]);
        }
        i = i + m + 1 * this.affectingComponentId.length;
      }
      if ((this.affectedComponentId != null) && (this.affectedComponentId.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.affectedComponentId.length; k++) {
          j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.affectedComponentId[k]);
        }
        i = i + j + 1 * this.affectedComponentId.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.subjectComponentId != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.subjectComponentId);
      }
      if ((this.affectingComponentId != null) && (this.affectingComponentId.length > 0)) {
        for (int j = 0; j < this.affectingComponentId.length; j++) {
          paramCodedOutputByteBufferNano.writeInt32(2, this.affectingComponentId[j]);
        }
      }
      if ((this.affectedComponentId != null) && (this.affectedComponentId.length > 0)) {
        for (int i = 0; i < this.affectedComponentId.length; i++) {
          paramCodedOutputByteBufferNano.writeInt32(3, this.affectedComponentId[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DependencyGraph
    extends MessageNano
  {
    public DependencyGraphOuterClass.ComponentsDependency[] componentsDependency = DependencyGraphOuterClass.ComponentsDependency.emptyArray();
    public DependencyGraphOuterClass.ComponentValuesDependency[] valuesDependency = DependencyGraphOuterClass.ComponentValuesDependency.emptyArray();
    
    public DependencyGraph()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.componentsDependency != null) && (this.componentsDependency.length > 0)) {
        for (int k = 0; k < this.componentsDependency.length; k++)
        {
          DependencyGraphOuterClass.ComponentsDependency localComponentsDependency = this.componentsDependency[k];
          if (localComponentsDependency != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localComponentsDependency);
          }
        }
      }
      if ((this.valuesDependency != null) && (this.valuesDependency.length > 0)) {
        for (int j = 0; j < this.valuesDependency.length; j++)
        {
          DependencyGraphOuterClass.ComponentValuesDependency localComponentValuesDependency = this.valuesDependency[j];
          if (localComponentValuesDependency != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localComponentValuesDependency);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.componentsDependency != null) && (this.componentsDependency.length > 0)) {
        for (int j = 0; j < this.componentsDependency.length; j++)
        {
          DependencyGraphOuterClass.ComponentsDependency localComponentsDependency = this.componentsDependency[j];
          if (localComponentsDependency != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localComponentsDependency);
          }
        }
      }
      if ((this.valuesDependency != null) && (this.valuesDependency.length > 0)) {
        for (int i = 0; i < this.valuesDependency.length; i++)
        {
          DependencyGraphOuterClass.ComponentValuesDependency localComponentValuesDependency = this.valuesDependency[i];
          if (localComponentValuesDependency != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localComponentValuesDependency);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class ResultingActionReference
    extends MessageNano
  {
    private static volatile ResultingActionReference[] _emptyArray;
    public int actionType = 0;
    public int componentId = 0;
    public SendRequestAction sendRequestAction = null;
    public ShowComponentAction showComponentAction = null;
    
    public ResultingActionReference()
    {
      this.cachedSize = -1;
    }
    
    public static ResultingActionReference[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new ResultingActionReference[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.actionType != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.actionType);
      }
      if (this.componentId != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.componentId);
      }
      if (this.showComponentAction != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(3, this.showComponentAction);
      }
      if (this.sendRequestAction != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.sendRequestAction);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.actionType != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.actionType);
      }
      if (this.componentId != 0) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.componentId);
      }
      if (this.showComponentAction != null) {
        paramCodedOutputByteBufferNano.writeMessage(3, this.showComponentAction);
      }
      if (this.sendRequestAction != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.sendRequestAction);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class SendRequestAction
      extends MessageNano
    {
      public int[] componentIdToValidate = WireFormatNano.EMPTY_INT_ARRAY;
      public byte[] requestToken = WireFormatNano.EMPTY_BYTES;
      public boolean validateAllElements = false;
      
      public SendRequestAction()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.componentIdToValidate != null) && (this.componentIdToValidate.length > 0))
        {
          int j = 0;
          for (int k = 0; k < this.componentIdToValidate.length; k++) {
            j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.componentIdToValidate[k]);
          }
          i = 1 + (i + j) + CodedOutputByteBufferNano.computeRawVarint32Size(j);
        }
        if (this.validateAllElements) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
        }
        if (!Arrays.equals(this.requestToken, WireFormatNano.EMPTY_BYTES)) {
          i += CodedOutputByteBufferNano.computeBytesSize(5, this.requestToken);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.componentIdToValidate != null) && (this.componentIdToValidate.length > 0))
        {
          int i = 0;
          for (int j = 0; j < this.componentIdToValidate.length; j++) {
            i += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.componentIdToValidate[j]);
          }
          paramCodedOutputByteBufferNano.writeRawVarint32(10);
          paramCodedOutputByteBufferNano.writeRawVarint32(i);
          for (int k = 0; k < this.componentIdToValidate.length; k++) {
            paramCodedOutputByteBufferNano.writeInt32NoTag(this.componentIdToValidate[k]);
          }
        }
        if (this.validateAllElements) {
          paramCodedOutputByteBufferNano.writeBool(2, this.validateAllElements);
        }
        if (!Arrays.equals(this.requestToken, WireFormatNano.EMPTY_BYTES)) {
          paramCodedOutputByteBufferNano.writeBytes(5, this.requestToken);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class ShowComponentAction
      extends MessageNano
    {
      public int[] valueId = WireFormatNano.EMPTY_INT_ARRAY;
      
      public ShowComponentAction()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.valueId != null) && (this.valueId.length > 0))
        {
          int j = 0;
          for (int k = 0; k < this.valueId.length; k++) {
            j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.valueId[k]);
          }
          i = 1 + (i + j) + CodedOutputByteBufferNano.computeRawVarint32Size(j);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.valueId != null) && (this.valueId.length > 0))
        {
          int i = 0;
          for (int j = 0; j < this.valueId.length; j++) {
            i += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.valueId[j]);
          }
          paramCodedOutputByteBufferNano.writeRawVarint32(10);
          paramCodedOutputByteBufferNano.writeRawVarint32(i);
          for (int k = 0; k < this.valueId.length; k++) {
            paramCodedOutputByteBufferNano.writeInt32NoTag(this.valueId[k]);
          }
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class TriggerValueReference
    extends MessageNano
  {
    private static volatile TriggerValueReference[] _emptyArray;
    public int componentId = 0;
    public int triggerType = 0;
    public ValueChangedTrigger valueChangedTrigger = null;
    
    public TriggerValueReference()
    {
      this.cachedSize = -1;
    }
    
    public static TriggerValueReference[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new TriggerValueReference[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.componentId != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.componentId);
      }
      if (this.valueChangedTrigger != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(2, this.valueChangedTrigger);
      }
      if (this.triggerType != 0) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.triggerType);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.componentId != 0) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.componentId);
      }
      if (this.valueChangedTrigger != null) {
        paramCodedOutputByteBufferNano.writeMessage(2, this.valueChangedTrigger);
      }
      if (this.triggerType != 0) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.triggerType);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class ComponentValue
      extends MessageNano
    {
      public int[] valueId = WireFormatNano.EMPTY_INT_ARRAY;
      public String valueStringRegex = "";
      
      public ComponentValue()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.valueId != null) && (this.valueId.length > 0))
        {
          int j = 0;
          for (int k = 0; k < this.valueId.length; k++) {
            j += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.valueId[k]);
          }
          i = 1 + (i + j) + CodedOutputByteBufferNano.computeRawVarint32Size(j);
        }
        if (!this.valueStringRegex.equals("")) {
          i += CodedOutputByteBufferNano.computeStringSize(2, this.valueStringRegex);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.valueId != null) && (this.valueId.length > 0))
        {
          int i = 0;
          for (int j = 0; j < this.valueId.length; j++) {
            i += CodedOutputByteBufferNano.computeInt32SizeNoTag(this.valueId[j]);
          }
          paramCodedOutputByteBufferNano.writeRawVarint32(10);
          paramCodedOutputByteBufferNano.writeRawVarint32(i);
          for (int k = 0; k < this.valueId.length; k++) {
            paramCodedOutputByteBufferNano.writeInt32NoTag(this.valueId[k]);
          }
        }
        if (!this.valueStringRegex.equals("")) {
          paramCodedOutputByteBufferNano.writeString(2, this.valueStringRegex);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
    
    public static final class ValueChangedTrigger
      extends MessageNano
    {
      public DependencyGraphOuterClass.TriggerValueReference.ComponentValue newValue = null;
      
      public ValueChangedTrigger()
      {
        this.cachedSize = -1;
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if (this.newValue != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(1, this.newValue);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if (this.newValue != null) {
          paramCodedOutputByteBufferNano.writeMessage(1, this.newValue);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass
 * JD-Core Version:    0.7.0.1
 */