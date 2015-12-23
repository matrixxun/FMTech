package com.google.android.finsky.setup;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.finsky.activities.SimpleAlertDialog.ConfigurableView;
import com.google.android.finsky.protos.Restore.BackupDeviceInfo;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.concurrent.TimeUnit;

public class SetupWizardRestoreDeviceDialogView
  extends LinearLayout
  implements SimpleAlertDialog.ConfigurableView
{
  private int mCurrentDeviceSelection;
  
  public SetupWizardRestoreDeviceDialogView(Context paramContext)
  {
    super(paramContext);
  }
  
  public SetupWizardRestoreDeviceDialogView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void configureView(Bundle paramBundle)
  {
    this.mCurrentDeviceSelection = (1 + paramBundle.getInt("SetupWizardRestoreDeviceDialogView.selectedDevicePosition", 0));
    Parcelable[] arrayOfParcelable = paramBundle.getParcelableArray("SetupWizardRestoreDeviceDialogView.selectedDevices");
    Restore.BackupDeviceInfo[] arrayOfBackupDeviceInfo = new Restore.BackupDeviceInfo[arrayOfParcelable.length];
    for (int i = 0; i < arrayOfParcelable.length; i++) {
      arrayOfBackupDeviceInfo[i] = ((Restore.BackupDeviceInfo)((ParcelableProto)arrayOfParcelable[i]).mPayload);
    }
    ListView localListView = (ListView)findViewById(2131756129);
    localListView.setChoiceMode(1);
    localListView.setAdapter(new BackupDeviceInfoArrayAdapter(getContext(), arrayOfBackupDeviceInfo));
    localListView.setItemChecked(this.mCurrentDeviceSelection, true);
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public final void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        SetupWizardRestoreDeviceDialogView.access$002(SetupWizardRestoreDeviceDialogView.this, paramAnonymousInt);
      }
    });
  }
  
  public Bundle getResult()
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("SetupWizardRestoreDeviceDialogView.selectedDevicePosition", -1 + this.mCurrentDeviceSelection);
    return localBundle;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mCurrentDeviceSelection = localBundle.getInt("SetupWizardRestoreDeviceDialogView.selectedDevicePosition");
      super.onRestoreInstanceState(localBundle.getParcelable("SetupWizardRestoreDeviceDialogView.parent_instance_state"));
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("SetupWizardRestoreDeviceDialogView.parent_instance_state", super.onSaveInstanceState());
    localBundle.putInt("SetupWizardRestoreDeviceDialogView.selectedDevicePosition", this.mCurrentDeviceSelection);
    return localBundle;
  }
  
  private static final class BackupDeviceInfoArrayAdapter
    extends ArrayAdapter<Restore.BackupDeviceInfo>
  {
    private final Restore.BackupDeviceInfo[] mDevices;
    
    public BackupDeviceInfoArrayAdapter(Context paramContext, Restore.BackupDeviceInfo[] paramArrayOfBackupDeviceInfo)
    {
      super(2130969071, paramArrayOfBackupDeviceInfo);
      this.mDevices = paramArrayOfBackupDeviceInfo;
    }
    
    public final int getCount()
    {
      return 1 + this.mDevices.length;
    }
    
    public final int getItemViewType(int paramInt)
    {
      if (paramInt == 0) {
        return 0;
      }
      return 1;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = paramView;
      int i = getItemViewType(paramInt);
      Context localContext = getContext();
      LayoutInflater localLayoutInflater = LayoutInflater.from(localContext);
      if (i == 0)
      {
        if (localView == null)
        {
          localView = localLayoutInflater.inflate(2130969070, paramViewGroup, false);
          ((CheckedTextView)localView).setText(localContext.getResources().getString(2131362752));
        }
        return localView;
      }
      if (localView == null)
      {
        localView = localLayoutInflater.inflate(2130969071, paramViewGroup, false);
        ViewHolder localViewHolder2 = new ViewHolder();
        localViewHolder2.deviceName = ((CheckedTextView)localView.findViewById(2131756040));
        localViewHolder2.usageTime = ((TextView)localView.findViewById(2131756041));
        localView.setTag(localViewHolder2);
      }
      ViewHolder localViewHolder1 = (ViewHolder)localView.getTag();
      Restore.BackupDeviceInfo localBackupDeviceInfo = this.mDevices[(paramInt - 1)];
      localViewHolder1.deviceName.setText(localBackupDeviceInfo.name);
      TextView localTextView = localViewHolder1.usageTime;
      long l = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - localBackupDeviceInfo.lastCheckinTimeMs);
      Resources localResources = localContext.getResources();
      if (l == 0L) {}
      int j;
      Object[] arrayOfObject;
      for (String str = localResources.getString(2131362738);; str = localResources.getQuantityString(2131296276, j, arrayOfObject))
      {
        localTextView.setText(str);
        return localView;
        j = (int)l;
        arrayOfObject = new Object[1];
        arrayOfObject[0] = Long.valueOf(l);
      }
    }
    
    public final int getViewTypeCount()
    {
      return 2;
    }
    
    public static final class ViewHolder
    {
      public CheckedTextView deviceName;
      public TextView usageTime;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.setup.SetupWizardRestoreDeviceDialogView
 * JD-Core Version:    0.7.0.1
 */