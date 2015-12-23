package com.google.android.wallet.analytics;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CreditCardEntryAction
  implements Parcelable
{
  public static final Parcelable.Creator<CreditCardEntryAction> CREATOR = new Parcelable.Creator() {};
  public int cameraInputPreference;
  public int expDateEntryType;
  public boolean expDateOcrEnabled;
  public boolean expDateRecognizedByNfc;
  public boolean expDateRecognizedByOcr;
  public boolean expDateValidationErrorOccurred;
  public int nameEntryType;
  public boolean nameOcrEnabled;
  public boolean nameRecognizedByNfc;
  public boolean nameRecognizedByOcr;
  public boolean nameValidationErrorOccurred;
  public boolean nfcAdapterEnabled;
  public long nfcElapsedTimeMillis;
  public int nfcErrorReason;
  public int nfcExitReason;
  public boolean nfcFeatureEnabled;
  public int nfcInputPreference;
  public int numNfcAttempts = -1;
  public int numOcrAttempts = -1;
  public int ocrExitReason;
  public int panEntryType;
  public boolean panOcrEnabled;
  public boolean panRecognizedByNfc;
  public boolean panRecognizedByOcr;
  public boolean panValidationErrorOccurred;
  
  public CreditCardEntryAction() {}
  
  private CreditCardEntryAction(Parcel paramParcel)
  {
    this.panOcrEnabled = readBooleanFromParcel(paramParcel);
    this.panEntryType = paramParcel.readInt();
    this.panRecognizedByOcr = readBooleanFromParcel(paramParcel);
    this.panValidationErrorOccurred = readBooleanFromParcel(paramParcel);
    this.panRecognizedByNfc = readBooleanFromParcel(paramParcel);
    this.expDateOcrEnabled = readBooleanFromParcel(paramParcel);
    this.expDateEntryType = paramParcel.readInt();
    this.expDateRecognizedByOcr = readBooleanFromParcel(paramParcel);
    this.expDateValidationErrorOccurred = readBooleanFromParcel(paramParcel);
    this.expDateRecognizedByNfc = readBooleanFromParcel(paramParcel);
    this.nameOcrEnabled = readBooleanFromParcel(paramParcel);
    this.nameEntryType = paramParcel.readInt();
    this.nameRecognizedByOcr = readBooleanFromParcel(paramParcel);
    this.nameValidationErrorOccurred = readBooleanFromParcel(paramParcel);
    this.nameRecognizedByNfc = readBooleanFromParcel(paramParcel);
    this.nfcElapsedTimeMillis = paramParcel.readLong();
    this.nfcFeatureEnabled = readBooleanFromParcel(paramParcel);
    this.nfcAdapterEnabled = readBooleanFromParcel(paramParcel);
    this.numOcrAttempts = paramParcel.readInt();
    this.ocrExitReason = paramParcel.readInt();
    this.numNfcAttempts = paramParcel.readInt();
    this.nfcExitReason = paramParcel.readInt();
    this.nfcErrorReason = paramParcel.readInt();
    this.cameraInputPreference = paramParcel.readInt();
    this.nfcInputPreference = paramParcel.readInt();
  }
  
  private static boolean readBooleanFromParcel(Parcel paramParcel)
  {
    return paramParcel.readInt() == 1;
  }
  
  private static void writeBooleanToParcel(Parcel paramParcel, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      paramParcel.writeInt(i);
      return;
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(256);
    localStringBuilder.append("panOcrEnabled: ").append(this.panOcrEnabled).append("\npanEntryType: ").append(this.panEntryType).append("\npanRecognizedByOcr: ").append(this.panRecognizedByOcr).append("\npanValidationErrorOccurred: ").append(this.panValidationErrorOccurred).append("\npanRecognizedByNfc: ").append(this.panRecognizedByNfc).append("\nexpDateOcrEnabled: ").append(this.expDateOcrEnabled).append("\nexpDateEntryType: ").append(this.expDateEntryType).append("\nexpDateRecognizedByOcr: ").append(this.expDateRecognizedByOcr).append("\nexpDateValidationErrorOccurred: ").append(this.expDateValidationErrorOccurred).append("\nexpDateRecognizedByNfc: ").append(this.expDateRecognizedByNfc).append("\nnameOcrEnabled: ").append(this.nameOcrEnabled).append("\nnameEntryType: ").append(this.nameEntryType).append("\nnameRecognizedByOcr: ").append(this.nameRecognizedByOcr).append("\nnameValidationErrorOccurred: ").append(this.nameValidationErrorOccurred).append("\nnameRecognizedByNfc: ").append(this.nameRecognizedByNfc).append("\nnfcElapsedTimeMillis: ").append(this.nfcElapsedTimeMillis).append("\nnfcFeatureEnabled: ").append(this.nfcFeatureEnabled).append("\nnfcAdapterEnabled: ").append(this.nfcAdapterEnabled).append("\nnumOcrAttempts: ").append(this.numOcrAttempts).append("\nocrExitReason: ").append(this.ocrExitReason).append("\nnumNfcAttempts: ").append(this.numNfcAttempts).append("\nnfcExitReason: ").append(this.nfcExitReason).append("\nnfcErrorReason: ").append(this.nfcErrorReason).append("\ncameraInputPreference: ").append(this.cameraInputPreference).append("\nnfcInputPreference: ").append(this.nfcInputPreference);
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    writeBooleanToParcel(paramParcel, this.panOcrEnabled);
    paramParcel.writeInt(this.panEntryType);
    writeBooleanToParcel(paramParcel, this.panRecognizedByOcr);
    writeBooleanToParcel(paramParcel, this.panValidationErrorOccurred);
    writeBooleanToParcel(paramParcel, this.panRecognizedByNfc);
    writeBooleanToParcel(paramParcel, this.expDateOcrEnabled);
    paramParcel.writeInt(this.expDateEntryType);
    writeBooleanToParcel(paramParcel, this.expDateRecognizedByOcr);
    writeBooleanToParcel(paramParcel, this.expDateValidationErrorOccurred);
    writeBooleanToParcel(paramParcel, this.expDateRecognizedByNfc);
    writeBooleanToParcel(paramParcel, this.nameOcrEnabled);
    paramParcel.writeInt(this.nameEntryType);
    writeBooleanToParcel(paramParcel, this.nameRecognizedByOcr);
    writeBooleanToParcel(paramParcel, this.nameValidationErrorOccurred);
    writeBooleanToParcel(paramParcel, this.nameRecognizedByNfc);
    paramParcel.writeLong(this.nfcElapsedTimeMillis);
    writeBooleanToParcel(paramParcel, this.nfcFeatureEnabled);
    writeBooleanToParcel(paramParcel, this.nfcAdapterEnabled);
    paramParcel.writeInt(this.numOcrAttempts);
    paramParcel.writeInt(this.ocrExitReason);
    paramParcel.writeInt(this.numNfcAttempts);
    paramParcel.writeInt(this.nfcExitReason);
    paramParcel.writeInt(this.nfcErrorReason);
    paramParcel.writeInt(this.cameraInputPreference);
    paramParcel.writeInt(this.nfcInputPreference);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.analytics.CreditCardEntryAction
 * JD-Core Version:    0.7.0.1
 */