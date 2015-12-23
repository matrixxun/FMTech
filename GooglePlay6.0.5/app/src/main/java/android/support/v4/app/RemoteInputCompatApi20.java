package android.support.v4.app;

import android.app.RemoteInput;
import android.app.RemoteInput.Builder;

final class RemoteInputCompatApi20
{
  static RemoteInput[] fromCompat(RemoteInputCompatBase.RemoteInput[] paramArrayOfRemoteInput)
  {
    RemoteInput[] arrayOfRemoteInput;
    if (paramArrayOfRemoteInput == null) {
      arrayOfRemoteInput = null;
    }
    for (;;)
    {
      return arrayOfRemoteInput;
      arrayOfRemoteInput = new RemoteInput[paramArrayOfRemoteInput.length];
      for (int i = 0; i < paramArrayOfRemoteInput.length; i++)
      {
        RemoteInputCompatBase.RemoteInput localRemoteInput = paramArrayOfRemoteInput[i];
        arrayOfRemoteInput[i] = new RemoteInput.Builder(localRemoteInput.getResultKey()).setLabel(localRemoteInput.getLabel()).setChoices(localRemoteInput.getChoices()).setAllowFreeFormInput(localRemoteInput.getAllowFreeFormInput()).addExtras(localRemoteInput.getExtras()).build();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.RemoteInputCompatApi20
 * JD-Core Version:    0.7.0.1
 */