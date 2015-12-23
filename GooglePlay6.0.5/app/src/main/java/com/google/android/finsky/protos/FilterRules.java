package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract interface FilterRules
{
  public static final class Availability
    extends MessageNano
  {
    public FilterRules.AvailabilityProblem[] availabilityProblem = FilterRules.AvailabilityProblem.emptyArray();
    public boolean availableIfOwned = false;
    public FilterRules.FilterEvaluationInfo filterInfo = null;
    public boolean hasAvailableIfOwned = false;
    public boolean hasHidden = false;
    public boolean hasOfferType = false;
    public boolean hasRestriction = false;
    public boolean hidden = false;
    public Common.Install[] install = Common.Install.emptyArray();
    public int offerType = 1;
    public Ownership.OwnershipInfo ownershipInfo = null;
    public PerDeviceAvailabilityRestriction[] perDeviceAvailabilityRestriction = PerDeviceAvailabilityRestriction.emptyArray();
    public int restriction = 1;
    public FilterRules.Rule rule = null;
    
    public Availability()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.restriction != 1) || (this.hasRestriction)) {
        i += CodedOutputByteBufferNano.computeInt32Size(5, this.restriction);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.offerType);
      }
      if (this.rule != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(7, this.rule);
      }
      if ((this.perDeviceAvailabilityRestriction != null) && (this.perDeviceAvailabilityRestriction.length > 0)) {
        for (int m = 0; m < this.perDeviceAvailabilityRestriction.length; m++)
        {
          PerDeviceAvailabilityRestriction localPerDeviceAvailabilityRestriction = this.perDeviceAvailabilityRestriction[m];
          if (localPerDeviceAvailabilityRestriction != null) {
            i += CodedOutputByteBufferNano.computeGroupSize(9, localPerDeviceAvailabilityRestriction);
          }
        }
      }
      if ((this.hasAvailableIfOwned) || (this.availableIfOwned)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(13);
      }
      if ((this.install != null) && (this.install.length > 0)) {
        for (int k = 0; k < this.install.length; k++)
        {
          Common.Install localInstall = this.install[k];
          if (localInstall != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(14, localInstall);
          }
        }
      }
      if (this.filterInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(16, this.filterInfo);
      }
      if (this.ownershipInfo != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(17, this.ownershipInfo);
      }
      if ((this.availabilityProblem != null) && (this.availabilityProblem.length > 0)) {
        for (int j = 0; j < this.availabilityProblem.length; j++)
        {
          FilterRules.AvailabilityProblem localAvailabilityProblem = this.availabilityProblem[j];
          if (localAvailabilityProblem != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(18, localAvailabilityProblem);
          }
        }
      }
      if ((this.hasHidden) || (this.hidden)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(21);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.restriction != 1) || (this.hasRestriction)) {
        paramCodedOutputByteBufferNano.writeInt32(5, this.restriction);
      }
      if ((this.offerType != 1) || (this.hasOfferType)) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.offerType);
      }
      if (this.rule != null) {
        paramCodedOutputByteBufferNano.writeMessage(7, this.rule);
      }
      if ((this.perDeviceAvailabilityRestriction != null) && (this.perDeviceAvailabilityRestriction.length > 0)) {
        for (int k = 0; k < this.perDeviceAvailabilityRestriction.length; k++)
        {
          PerDeviceAvailabilityRestriction localPerDeviceAvailabilityRestriction = this.perDeviceAvailabilityRestriction[k];
          if (localPerDeviceAvailabilityRestriction != null) {
            paramCodedOutputByteBufferNano.writeGroup(9, localPerDeviceAvailabilityRestriction);
          }
        }
      }
      if ((this.hasAvailableIfOwned) || (this.availableIfOwned)) {
        paramCodedOutputByteBufferNano.writeBool(13, this.availableIfOwned);
      }
      if ((this.install != null) && (this.install.length > 0)) {
        for (int j = 0; j < this.install.length; j++)
        {
          Common.Install localInstall = this.install[j];
          if (localInstall != null) {
            paramCodedOutputByteBufferNano.writeMessage(14, localInstall);
          }
        }
      }
      if (this.filterInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(16, this.filterInfo);
      }
      if (this.ownershipInfo != null) {
        paramCodedOutputByteBufferNano.writeMessage(17, this.ownershipInfo);
      }
      if ((this.availabilityProblem != null) && (this.availabilityProblem.length > 0)) {
        for (int i = 0; i < this.availabilityProblem.length; i++)
        {
          FilterRules.AvailabilityProblem localAvailabilityProblem = this.availabilityProblem[i];
          if (localAvailabilityProblem != null) {
            paramCodedOutputByteBufferNano.writeMessage(18, localAvailabilityProblem);
          }
        }
      }
      if ((this.hasHidden) || (this.hidden)) {
        paramCodedOutputByteBufferNano.writeBool(21, this.hidden);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
    
    public static final class PerDeviceAvailabilityRestriction
      extends MessageNano
    {
      private static volatile PerDeviceAvailabilityRestriction[] _emptyArray;
      public long androidId = 0L;
      public boolean availableIfOwned = false;
      public long channelId = 0L;
      public int deviceRestriction = 1;
      public FilterRules.FilterEvaluationInfo filterInfo = null;
      public boolean hasAndroidId = false;
      public boolean hasAvailableIfOwned = false;
      public boolean hasChannelId = false;
      public boolean hasDeviceRestriction = false;
      
      public PerDeviceAvailabilityRestriction()
      {
        this.cachedSize = -1;
      }
      
      public static PerDeviceAvailabilityRestriction[] emptyArray()
      {
        if (_emptyArray == null) {}
        synchronized (InternalNano.LAZY_INIT_LOCK)
        {
          if (_emptyArray == null) {
            _emptyArray = new PerDeviceAvailabilityRestriction[0];
          }
          return _emptyArray;
        }
      }
      
      protected final int computeSerializedSize()
      {
        int i = super.computeSerializedSize();
        if ((this.hasAndroidId) || (this.androidId != 0L)) {
          i += 8 + CodedOutputByteBufferNano.computeTagSize(10);
        }
        if ((this.deviceRestriction != 1) || (this.hasDeviceRestriction)) {
          i += CodedOutputByteBufferNano.computeInt32Size(11, this.deviceRestriction);
        }
        if ((this.hasChannelId) || (this.channelId != 0L)) {
          i += CodedOutputByteBufferNano.computeInt64Size(12, this.channelId);
        }
        if (this.filterInfo != null) {
          i += CodedOutputByteBufferNano.computeMessageSize(15, this.filterInfo);
        }
        if ((this.hasAvailableIfOwned) || (this.availableIfOwned)) {
          i += 1 + CodedOutputByteBufferNano.computeTagSize(22);
        }
        return i;
      }
      
      public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
        throws IOException
      {
        if ((this.hasAndroidId) || (this.androidId != 0L)) {
          paramCodedOutputByteBufferNano.writeFixed64(10, this.androidId);
        }
        if ((this.deviceRestriction != 1) || (this.hasDeviceRestriction)) {
          paramCodedOutputByteBufferNano.writeInt32(11, this.deviceRestriction);
        }
        if ((this.hasChannelId) || (this.channelId != 0L)) {
          paramCodedOutputByteBufferNano.writeInt64(12, this.channelId);
        }
        if (this.filterInfo != null) {
          paramCodedOutputByteBufferNano.writeMessage(15, this.filterInfo);
        }
        if ((this.hasAvailableIfOwned) || (this.availableIfOwned)) {
          paramCodedOutputByteBufferNano.writeBool(22, this.availableIfOwned);
        }
        super.writeTo(paramCodedOutputByteBufferNano);
      }
    }
  }
  
  public static final class AvailabilityProblem
    extends MessageNano
  {
    private static volatile AvailabilityProblem[] _emptyArray;
    public boolean hasProblemType = false;
    public String[] missingValue = WireFormatNano.EMPTY_STRING_ARRAY;
    public int problemType = 1;
    
    public AvailabilityProblem()
    {
      this.cachedSize = -1;
    }
    
    public static AvailabilityProblem[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new AvailabilityProblem[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.problemType != 1) || (this.hasProblemType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.problemType);
      }
      if ((this.missingValue != null) && (this.missingValue.length > 0))
      {
        int j = 0;
        int k = 0;
        for (int m = 0; m < this.missingValue.length; m++)
        {
          String str = this.missingValue[m];
          if (str != null)
          {
            j++;
            k += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + k + j * 1;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.problemType != 1) || (this.hasProblemType)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.problemType);
      }
      if ((this.missingValue != null) && (this.missingValue.length > 0)) {
        for (int i = 0; i < this.missingValue.length; i++)
        {
          String str = this.missingValue[i];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(2, str);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class FilterEvaluationInfo
    extends MessageNano
  {
    public FilterRules.RuleEvaluation[] ruleEvaluation = FilterRules.RuleEvaluation.emptyArray();
    
    public FilterEvaluationInfo()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.ruleEvaluation != null) && (this.ruleEvaluation.length > 0)) {
        for (int j = 0; j < this.ruleEvaluation.length; j++)
        {
          FilterRules.RuleEvaluation localRuleEvaluation = this.ruleEvaluation[j];
          if (localRuleEvaluation != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localRuleEvaluation);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.ruleEvaluation != null) && (this.ruleEvaluation.length > 0)) {
        for (int i = 0; i < this.ruleEvaluation.length; i++)
        {
          FilterRules.RuleEvaluation localRuleEvaluation = this.ruleEvaluation[i];
          if (localRuleEvaluation != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localRuleEvaluation);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class Rule
    extends MessageNano
  {
    private static volatile Rule[] _emptyArray;
    public int availabilityProblemType = 1;
    public String comment = "";
    public double[] doubleArg = WireFormatNano.EMPTY_DOUBLE_ARRAY;
    public boolean hasAvailabilityProblemType = false;
    public boolean hasComment = false;
    public boolean hasIncludeMissingValues = false;
    public boolean hasKey = false;
    public boolean hasNegate = false;
    public boolean hasOperator = false;
    public boolean hasResponseCode = false;
    public boolean includeMissingValues = false;
    public int key = 1;
    public long[] longArg = WireFormatNano.EMPTY_LONG_ARRAY;
    public boolean negate = false;
    public int operator = 1;
    public int responseCode = 1;
    public String[] stringArg = WireFormatNano.EMPTY_STRING_ARRAY;
    public long[] stringArgHash = WireFormatNano.EMPTY_LONG_ARRAY;
    public Rule[] subrule = emptyArray();
    
    public Rule()
    {
      this.cachedSize = -1;
    }
    
    private static Rule[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Rule[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.hasNegate) || (this.negate)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(1);
      }
      if ((this.operator != 1) || (this.hasOperator)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.operator);
      }
      if ((this.key != 1) || (this.hasKey)) {
        i += CodedOutputByteBufferNano.computeInt32Size(3, this.key);
      }
      if ((this.stringArg != null) && (this.stringArg.length > 0))
      {
        int n = 0;
        int i1 = 0;
        for (int i2 = 0; i2 < this.stringArg.length; i2++)
        {
          String str = this.stringArg[i2];
          if (str != null)
          {
            n++;
            i1 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + i1 + n * 1;
      }
      if ((this.longArg != null) && (this.longArg.length > 0))
      {
        int k = 0;
        for (int m = 0; m < this.longArg.length; m++) {
          k += CodedOutputByteBufferNano.computeRawVarint64Size(this.longArg[m]);
        }
        i = i + k + 1 * this.longArg.length;
      }
      if ((this.doubleArg != null) && (this.doubleArg.length > 0)) {
        i = i + 8 * this.doubleArg.length + 1 * this.doubleArg.length;
      }
      if ((this.subrule != null) && (this.subrule.length > 0)) {
        for (int j = 0; j < this.subrule.length; j++)
        {
          Rule localRule = this.subrule[j];
          if (localRule != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(7, localRule);
          }
        }
      }
      if ((this.responseCode != 1) || (this.hasResponseCode)) {
        i += CodedOutputByteBufferNano.computeInt32Size(8, this.responseCode);
      }
      if ((this.hasComment) || (!this.comment.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(9, this.comment);
      }
      if ((this.stringArgHash != null) && (this.stringArgHash.length > 0)) {
        i = i + 8 * this.stringArgHash.length + 1 * this.stringArgHash.length;
      }
      if ((this.availabilityProblemType != 1) || (this.hasAvailabilityProblemType)) {
        i += CodedOutputByteBufferNano.computeInt32Size(12, this.availabilityProblemType);
      }
      if ((this.hasIncludeMissingValues) || (this.includeMissingValues)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(13);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.hasNegate) || (this.negate)) {
        paramCodedOutputByteBufferNano.writeBool(1, this.negate);
      }
      if ((this.operator != 1) || (this.hasOperator)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.operator);
      }
      if ((this.key != 1) || (this.hasKey)) {
        paramCodedOutputByteBufferNano.writeInt32(3, this.key);
      }
      if ((this.stringArg != null) && (this.stringArg.length > 0)) {
        for (int n = 0; n < this.stringArg.length; n++)
        {
          String str = this.stringArg[n];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(4, str);
          }
        }
      }
      if ((this.longArg != null) && (this.longArg.length > 0)) {
        for (int m = 0; m < this.longArg.length; m++) {
          paramCodedOutputByteBufferNano.writeInt64(5, this.longArg[m]);
        }
      }
      if ((this.doubleArg != null) && (this.doubleArg.length > 0)) {
        for (int k = 0; k < this.doubleArg.length; k++) {
          paramCodedOutputByteBufferNano.writeDouble(6, this.doubleArg[k]);
        }
      }
      if ((this.subrule != null) && (this.subrule.length > 0)) {
        for (int j = 0; j < this.subrule.length; j++)
        {
          Rule localRule = this.subrule[j];
          if (localRule != null) {
            paramCodedOutputByteBufferNano.writeMessage(7, localRule);
          }
        }
      }
      if ((this.responseCode != 1) || (this.hasResponseCode)) {
        paramCodedOutputByteBufferNano.writeInt32(8, this.responseCode);
      }
      if ((this.hasComment) || (!this.comment.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(9, this.comment);
      }
      if ((this.stringArgHash != null) && (this.stringArgHash.length > 0)) {
        for (int i = 0; i < this.stringArgHash.length; i++) {
          paramCodedOutputByteBufferNano.writeFixed64(10, this.stringArgHash[i]);
        }
      }
      if ((this.availabilityProblemType != 1) || (this.hasAvailabilityProblemType)) {
        paramCodedOutputByteBufferNano.writeInt32(12, this.availabilityProblemType);
      }
      if ((this.hasIncludeMissingValues) || (this.includeMissingValues)) {
        paramCodedOutputByteBufferNano.writeBool(13, this.includeMissingValues);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class RuleEvaluation
    extends MessageNano
  {
    private static volatile RuleEvaluation[] _emptyArray;
    public boolean[] actualBoolValue = WireFormatNano.EMPTY_BOOLEAN_ARRAY;
    public double[] actualDoubleValue = WireFormatNano.EMPTY_DOUBLE_ARRAY;
    public long[] actualLongValue = WireFormatNano.EMPTY_LONG_ARRAY;
    public String[] actualStringValue = WireFormatNano.EMPTY_STRING_ARRAY;
    public FilterRules.Rule rule = null;
    
    public RuleEvaluation()
    {
      this.cachedSize = -1;
    }
    
    public static RuleEvaluation[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new RuleEvaluation[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.rule != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.rule);
      }
      if ((this.actualStringValue != null) && (this.actualStringValue.length > 0))
      {
        int m = 0;
        int n = 0;
        for (int i1 = 0; i1 < this.actualStringValue.length; i1++)
        {
          String str = this.actualStringValue[i1];
          if (str != null)
          {
            m++;
            n += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
          }
        }
        i = i + n + m * 1;
      }
      if ((this.actualLongValue != null) && (this.actualLongValue.length > 0))
      {
        int j = 0;
        for (int k = 0; k < this.actualLongValue.length; k++) {
          j += CodedOutputByteBufferNano.computeRawVarint64Size(this.actualLongValue[k]);
        }
        i = i + j + 1 * this.actualLongValue.length;
      }
      if ((this.actualBoolValue != null) && (this.actualBoolValue.length > 0)) {
        i = i + 1 * this.actualBoolValue.length + 1 * this.actualBoolValue.length;
      }
      if ((this.actualDoubleValue != null) && (this.actualDoubleValue.length > 0)) {
        i = i + 8 * this.actualDoubleValue.length + 1 * this.actualDoubleValue.length;
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.rule != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.rule);
      }
      if ((this.actualStringValue != null) && (this.actualStringValue.length > 0)) {
        for (int m = 0; m < this.actualStringValue.length; m++)
        {
          String str = this.actualStringValue[m];
          if (str != null) {
            paramCodedOutputByteBufferNano.writeString(2, str);
          }
        }
      }
      if ((this.actualLongValue != null) && (this.actualLongValue.length > 0)) {
        for (int k = 0; k < this.actualLongValue.length; k++) {
          paramCodedOutputByteBufferNano.writeInt64(3, this.actualLongValue[k]);
        }
      }
      if ((this.actualBoolValue != null) && (this.actualBoolValue.length > 0)) {
        for (int j = 0; j < this.actualBoolValue.length; j++) {
          paramCodedOutputByteBufferNano.writeBool(4, this.actualBoolValue[j]);
        }
      }
      if ((this.actualDoubleValue != null) && (this.actualDoubleValue.length > 0)) {
        for (int i = 0; i < this.actualDoubleValue.length; i++) {
          paramCodedOutputByteBufferNano.writeDouble(5, this.actualDoubleValue[i]);
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.FilterRules
 * JD-Core Version:    0.7.0.1
 */