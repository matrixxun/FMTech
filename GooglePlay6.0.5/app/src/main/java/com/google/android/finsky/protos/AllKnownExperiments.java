package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface AllKnownExperiments
{
  public static final class AllKnownExperimentsResponse
    extends MessageNano
  {
    public AllKnownExperiments.DebugTarget[] inExperimentTarget = AllKnownExperiments.DebugTarget.emptyArray();
    public AllKnownExperiments.DebugTarget[] outExperimentTarget = AllKnownExperiments.DebugTarget.emptyArray();
    
    public AllKnownExperimentsResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.inExperimentTarget != null) && (this.inExperimentTarget.length > 0)) {
        for (int k = 0; k < this.inExperimentTarget.length; k++)
        {
          AllKnownExperiments.DebugTarget localDebugTarget2 = this.inExperimentTarget[k];
          if (localDebugTarget2 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localDebugTarget2);
          }
        }
      }
      if ((this.outExperimentTarget != null) && (this.outExperimentTarget.length > 0)) {
        for (int j = 0; j < this.outExperimentTarget.length; j++)
        {
          AllKnownExperiments.DebugTarget localDebugTarget1 = this.outExperimentTarget[j];
          if (localDebugTarget1 != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localDebugTarget1);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.inExperimentTarget != null) && (this.inExperimentTarget.length > 0)) {
        for (int j = 0; j < this.inExperimentTarget.length; j++)
        {
          AllKnownExperiments.DebugTarget localDebugTarget2 = this.inExperimentTarget[j];
          if (localDebugTarget2 != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localDebugTarget2);
          }
        }
      }
      if ((this.outExperimentTarget != null) && (this.outExperimentTarget.length > 0)) {
        for (int i = 0; i < this.outExperimentTarget.length; i++)
        {
          AllKnownExperiments.DebugTarget localDebugTarget1 = this.outExperimentTarget[i];
          if (localDebugTarget1 != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localDebugTarget1);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class DebugTarget
    extends MessageNano
  {
    private static volatile DebugTarget[] _emptyArray;
    public boolean hasTargetId = false;
    public boolean hasTargetName = false;
    public long targetId = 0L;
    public String targetName = "";
    
    public DebugTarget()
    {
      this.cachedSize = -1;
    }
    
    public static DebugTarget[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new DebugTarget[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasTargetId) || (this.targetId != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(1, this.targetId);
      }
      if ((this.hasTargetName) || (!this.targetName.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(2, this.targetName);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasTargetId) || (this.targetId != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(1, this.targetId);
      }
      if ((this.hasTargetName) || (!this.targetName.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(2, this.targetName);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.AllKnownExperiments
 * JD-Core Version:    0.7.0.1
 */