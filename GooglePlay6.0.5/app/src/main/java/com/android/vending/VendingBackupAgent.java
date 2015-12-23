package com.android.vending;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupManager;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.autoupdate.UpdateChecker;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.services.RestoreService;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.play.utils.config.GservicesValue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VendingBackupAgent
  extends BackupAgentHelper
{
  private static void flushBufferData(BackupDataOutput paramBackupDataOutput, ByteArrayOutputStream paramByteArrayOutputStream, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = paramByteArrayOutputStream.toByteArray();
    paramBackupDataOutput.writeEntityHeader(paramString, arrayOfByte.length);
    paramBackupDataOutput.writeEntityData(arrayOfByte, arrayOfByte.length);
    paramByteArrayOutputStream.reset();
  }
  
  public static void registerWithBackup(Context paramContext)
  {
    if (!((Boolean)VendingPreferences.BACKED_UP.get()).booleanValue()) {
      new BackupManager(paramContext).dataChanged();
    }
  }
  
  private static void writeData(BackupDataOutput paramBackupDataOutput, ByteArrayOutputStream paramByteArrayOutputStream, DataOutputStream paramDataOutputStream, String paramString, boolean paramBoolean)
    throws IOException
  {
    paramDataOutputStream.writeBoolean(paramBoolean);
    flushBufferData(paramBackupDataOutput, paramByteArrayOutputStream, paramString);
  }
  
  public void onBackup(ParcelFileDescriptor paramParcelFileDescriptor1, BackupDataOutput paramBackupDataOutput, ParcelFileDescriptor paramParcelFileDescriptor2)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    long l = ((Long)DfeApiConfig.androidId.get()).longValue();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = FinskyLog.scrubPii(Long.toHexString(l));
    FinskyLog.d("Backing up aid: %s", arrayOfObject);
    localDataOutputStream.writeLong(l);
    flushBufferData(paramBackupDataOutput, localByteArrayOutputStream, "vending");
    writeData(paramBackupDataOutput, localByteArrayOutputStream, localDataOutputStream, "auto_update_enabled", ((Boolean)FinskyPreferences.autoUpdateEnabled.get()).booleanValue());
    writeData(paramBackupDataOutput, localByteArrayOutputStream, localDataOutputStream, "update_over_wifi_only", ((Boolean)FinskyPreferences.autoUpdateWifiOnly.get()).booleanValue());
    writeData(paramBackupDataOutput, localByteArrayOutputStream, localDataOutputStream, "auto_add_shortcuts", ((Boolean)VendingPreferences.AUTO_ADD_SHORTCUTS.get()).booleanValue());
    writeData(paramBackupDataOutput, localByteArrayOutputStream, localDataOutputStream, "notify_updates", ((Boolean)VendingPreferences.NOTIFY_UPDATES.get()).booleanValue());
    writeData(paramBackupDataOutput, localByteArrayOutputStream, localDataOutputStream, "notify_updates_completion", ((Boolean)VendingPreferences.NOTIFY_UPDATES_COMPLETION.get()).booleanValue());
    localDataOutputStream.writeInt(((Integer)FinskyPreferences.contentFilterLevel.get()).intValue());
    flushBufferData(paramBackupDataOutput, localByteArrayOutputStream, "content-filter-level");
    VendingPreferences.BACKED_UP.put(Boolean.valueOf(true));
  }
  
  public void onRestore(BackupDataInput paramBackupDataInput, int paramInt, ParcelFileDescriptor paramParcelFileDescriptor)
    throws IOException
  {
    FinskyLog.d("Entered onRestore", new Object[0]);
    int i = 0;
    Boolean localBoolean1 = null;
    Boolean localBoolean2 = null;
    Boolean localBoolean3 = null;
    while (paramBackupDataInput.readNextHeader())
    {
      int j = paramBackupDataInput.getDataSize();
      byte[] arrayOfByte = new byte[j];
      paramBackupDataInput.readEntityData(arrayOfByte, 0, j);
      DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(arrayOfByte));
      String str1 = paramBackupDataInput.getKey();
      FinskyLog.d("Restoring key %s", new Object[] { str1 });
      if ("vending".equals(str1))
      {
        String str2 = Long.toHexString(localDataInputStream.readLong());
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = FinskyLog.scrubPii(str2);
        FinskyLog.d("Restored aid: %s", arrayOfObject);
        RestoreService.restoreAccounts(this, str2, null);
        VendingPreferences.RESTORED_ANDROID_ID.put(str2);
        i = 1;
      }
      else if ("auto_update_enabled".equals(str1))
      {
        localBoolean2 = Boolean.valueOf(localDataInputStream.readBoolean());
      }
      else if ("auto_update_default".equals(str1))
      {
        localBoolean1 = Boolean.valueOf(localDataInputStream.readBoolean());
      }
      else if ("update_over_wifi_only".equals(str1))
      {
        localBoolean3 = Boolean.valueOf(localDataInputStream.readBoolean());
      }
      else if ("auto_add_shortcuts".equals(str1))
      {
        VendingPreferences.AUTO_ADD_SHORTCUTS.put(Boolean.valueOf(localDataInputStream.readBoolean()));
      }
      else if ("notify_updates".equals(str1))
      {
        VendingPreferences.NOTIFY_UPDATES.put(Boolean.valueOf(localDataInputStream.readBoolean()));
      }
      else if ("notify_updates_completion".equals(str1))
      {
        VendingPreferences.NOTIFY_UPDATES_COMPLETION.put(Boolean.valueOf(localDataInputStream.readBoolean()));
      }
      else if ("content-filter-level".equals(str1))
      {
        FinskyPreferences.contentFilterLevel.put(Integer.valueOf(localDataInputStream.readInt()));
      }
    }
    if (FinskyPreferences.autoUpdateEnabled.exists()) {
      FinskyLog.d("Skip restore auto-update - already set locally.", new Object[0]);
    }
    for (;;)
    {
      if ((localBoolean3 != null) && (FinskyPreferences.autoUpdateWifiOnly.exists())) {
        FinskyPreferences.autoUpdateWifiOnly.put(localBoolean3);
      }
      if (i == 0) {
        FinskyLog.w("Restore completed, no Market aid was found", new Object[0]);
      }
      FinskyLog.d("Finished onRestore", new Object[0]);
      return;
      if (localBoolean2 != null)
      {
        FinskyPreferences.autoUpdateEnabled.put(localBoolean2);
      }
      else if (localBoolean1 != null)
      {
        VendingPreferences.AUTO_UPDATE_BY_DEFAULT.put(localBoolean1);
        final AppStates localAppStates = FinskyApp.get().mAppStates;
        localAppStates.load(new Runnable()
        {
          public final void run()
          {
            UpdateChecker.migrateAutoUpdateSettings(localAppStates);
          }
        });
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.vending.VendingBackupAgent
 * JD-Core Version:    0.7.0.1
 */