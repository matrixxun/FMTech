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
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.finsky.activities.SimpleAlertDialog.ConfigurableView;
import com.google.android.finsky.protos.Restore.BackupDocumentInfo;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class SetupWizardRestoreAppsDialogView
  extends LinearLayout
  implements AdapterView.OnItemClickListener, SimpleAlertDialog.ConfigurableView
{
  private ArrayList<Restore.BackupDocumentInfo> mBackupDocumentInfos;
  private ListView mListView;
  private boolean[] mSelectedBackupDocs;
  private int mSelectedBackupDocsCount;
  
  public SetupWizardRestoreAppsDialogView(Context paramContext)
  {
    super(paramContext);
  }
  
  public SetupWizardRestoreAppsDialogView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void configureListView(View paramView)
  {
    this.mListView = ((ListView)paramView.findViewById(2131756120));
    this.mListView.setAdapter(new BackupAppListAdapter(this.mBackupDocumentInfos));
    for (int i = 0; i < this.mSelectedBackupDocs.length; i++) {
      if (this.mSelectedBackupDocs[i] != 0) {
        this.mListView.setItemChecked(i + 1, true);
      }
    }
    ListView localListView = this.mListView;
    if (this.mSelectedBackupDocsCount == this.mSelectedBackupDocs.length) {}
    for (boolean bool = true;; bool = false)
    {
      localListView.setItemChecked(0, bool);
      this.mListView.setOnItemClickListener(this);
      return;
    }
  }
  
  private int getSelectedBackupDocsCount()
  {
    int i = 0;
    boolean[] arrayOfBoolean = this.mSelectedBackupDocs;
    int j = arrayOfBoolean.length;
    for (int k = 0; k < j; k++) {
      if (Boolean.valueOf(arrayOfBoolean[k]).booleanValue()) {
        i++;
      }
    }
    return i;
  }
  
  public final void configureView(Bundle paramBundle)
  {
    getContext();
    ArrayList localArrayList = paramBundle.getParcelableArrayList("SetupWizardAppListDialog.backupDocs");
    boolean[] arrayOfBoolean = paramBundle.getBooleanArray("SetupWizardAppListDialog.selectedBackupDocs");
    this.mSelectedBackupDocs = Arrays.copyOf(arrayOfBoolean, arrayOfBoolean.length);
    this.mSelectedBackupDocsCount = getSelectedBackupDocsCount();
    this.mBackupDocumentInfos = new ArrayList(localArrayList.size());
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      ParcelableProto localParcelableProto = (ParcelableProto)localIterator.next();
      this.mBackupDocumentInfos.add(localParcelableProto.mPayload);
    }
    configureListView((ListView)findViewById(2131756120));
  }
  
  public Bundle getResult()
  {
    Bundle localBundle = new Bundle();
    localBundle.putBooleanArray("SetupWizardAppListDialog.selectedBackupDocs", this.mSelectedBackupDocs);
    return localBundle;
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    int i = 1;
    boolean bool = ((CheckedTextView)paramView).isChecked();
    if (paramInt <= 0)
    {
      for (int n = 0; n < this.mSelectedBackupDocs.length; n++)
      {
        this.mListView.setItemChecked(n + 1, bool);
        this.mSelectedBackupDocs[n] = bool;
      }
      int i1 = 0;
      if (bool) {
        i1 = this.mSelectedBackupDocs.length;
      }
      this.mSelectedBackupDocsCount = i1;
      return;
    }
    this.mSelectedBackupDocs[(paramInt - 1)] = bool;
    int j = this.mSelectedBackupDocsCount;
    ListView localListView;
    if (bool)
    {
      int k = i;
      this.mSelectedBackupDocsCount = (k + j);
      localListView = this.mListView;
      if (this.mSelectedBackupDocsCount != this.mSelectedBackupDocs.length) {
        break label146;
      }
    }
    for (;;)
    {
      localListView.setItemChecked(0, i);
      return;
      int m = -1;
      break;
      label146:
      i = 0;
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      this.mSelectedBackupDocs = localBundle.getBooleanArray("SetupWizardAppListDialog.selectedBackupDocs");
      super.onRestoreInstanceState(localBundle.getParcelable("SetupWizardRestoreAppsDialogView.parent_instance_state"));
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("SetupWizardRestoreAppsDialogView.parent_instance_state", super.onSaveInstanceState());
    localBundle.putBooleanArray("SetupWizardAppListDialog.selectedBackupDocs", this.mSelectedBackupDocs);
    return localBundle;
  }
  
  private final class BackupAppListAdapter
    extends BaseAdapter
  {
    private ArrayList<Restore.BackupDocumentInfo> mAppsList;
    private LayoutInflater mInflater;
    
    public BackupAppListAdapter()
    {
      Object localObject;
      this.mAppsList = localObject;
      this.mInflater = ((LayoutInflater)SetupWizardRestoreAppsDialogView.this.getContext().getSystemService("layout_inflater"));
    }
    
    private String getItem(int paramInt)
    {
      if (paramInt == 0) {
        return SetupWizardRestoreAppsDialogView.this.getResources().getString(2131362735);
      }
      return ((Restore.BackupDocumentInfo)this.mAppsList.get(paramInt - 1)).title;
    }
    
    public final int getCount()
    {
      return 1 + this.mAppsList.size();
    }
    
    public final long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = this.mInflater.inflate(2130969069, paramViewGroup, false);
      }
      ((TextView)paramView).setText(getItem(paramInt));
      return paramView;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.setup.SetupWizardRestoreAppsDialogView
 * JD-Core Version:    0.7.0.1
 */