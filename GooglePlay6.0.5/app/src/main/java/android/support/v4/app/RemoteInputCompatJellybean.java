package android.support.v4.app;

import android.os.Bundle;

final class RemoteInputCompatJellybean
{
  static Bundle[] toBundleArray(RemoteInputCompatBase.RemoteInput[] paramArrayOfRemoteInput)
  {
    Bundle[] arrayOfBundle;
    if (paramArrayOfRemoteInput == null) {
      arrayOfBundle = null;
    }
    for (;;)
    {
      return arrayOfBundle;
      arrayOfBundle = new Bundle[paramArrayOfRemoteInput.length];
      for (int i = 0; i < paramArrayOfRemoteInput.length; i++)
      {
        RemoteInputCompatBase.RemoteInput localRemoteInput = paramArrayOfRemoteInput[i];
        Bundle localBundle = new Bundle();
        localBundle.putString("resultKey", localRemoteInput.getResultKey());
        localBundle.putCharSequence("label", localRemoteInput.getLabel());
        localBundle.putCharSequenceArray("choices", localRemoteInput.getChoices());
        localBundle.putBoolean("allowFreeFormInput", localRemoteInput.getAllowFreeFormInput());
        localBundle.putBundle("extras", localRemoteInput.getExtras());
        arrayOfBundle[i] = localBundle;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.RemoteInputCompatJellybean
 * JD-Core Version:    0.7.0.1
 */