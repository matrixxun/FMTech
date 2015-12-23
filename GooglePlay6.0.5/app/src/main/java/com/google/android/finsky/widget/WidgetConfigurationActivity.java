package com.google.android.finsky.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(16)
public class WidgetConfigurationActivity
  extends AppCompatActivity
{
  private String getCorpusName(int paramInt)
  {
    return getIntent().getStringExtra("name_" + paramInt);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130969185);
    setResult(0);
    Intent localIntent = getIntent();
    setTitle(localIntent.getIntExtra("dialog_title", 2131361848));
    GridView localGridView = (GridView)findViewById(2131755218);
    List localList = ((DfeToc)localIntent.getParcelableExtra("dfeToc")).getCorpusList();
    ArrayList localArrayList = Lists.newArrayList(localList.size());
    if (localIntent.getBooleanExtra("enableMultiCorpus", true))
    {
      Toc.CorpusMetadata localCorpusMetadata1 = new Toc.CorpusMetadata();
      localCorpusMetadata1.backend = 0;
      localCorpusMetadata1.hasBackend = true;
      localCorpusMetadata1.name = getCorpusName(0);
      localCorpusMetadata1.hasName = true;
      localArrayList.add(localCorpusMetadata1);
    }
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Toc.CorpusMetadata localCorpusMetadata2 = (Toc.CorpusMetadata)localIterator.next();
      if (localIntent.getBooleanExtra("backend_" + localCorpusMetadata2.backend, true))
      {
        String str = getCorpusName(localCorpusMetadata2.backend);
        if (!TextUtils.isEmpty(str))
        {
          localCorpusMetadata2.name = str;
          localCorpusMetadata2.hasName = true;
        }
        localArrayList.add(localCorpusMetadata2);
      }
    }
    localGridView.setNumColumns(Math.min(localArrayList.size(), 3));
    localGridView.setAdapter(new CorpusAdapter(this, localArrayList, localIntent.getIntExtra("appWidgetId", -1)));
  }
  
  private static final class CorpusAdapter
    extends BaseAdapter
  {
    private final Activity mActivity;
    private final ActivityManager mActivityManager;
    private final int mAppWidgetId;
    private final List<Toc.CorpusMetadata> mCorpora;
    
    public CorpusAdapter(Activity paramActivity, List<Toc.CorpusMetadata> paramList, int paramInt)
    {
      this.mActivity = paramActivity;
      this.mCorpora = paramList;
      this.mAppWidgetId = paramInt;
      this.mActivityManager = ((ActivityManager)this.mActivity.getSystemService("activity"));
    }
    
    private int getBackend(int paramInt)
    {
      return getItem(paramInt).backend;
    }
    
    private Toc.CorpusMetadata getItem(int paramInt)
    {
      return (Toc.CorpusMetadata)this.mCorpora.get(paramInt);
    }
    
    public final int getCount()
    {
      return this.mCorpora.size();
    }
    
    public final long getItemId(int paramInt)
    {
      return getBackend(paramInt);
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = LayoutInflater.from(this.mActivity).inflate(2130969186, paramViewGroup, false);
      }
      WidgetConfigurationActivity.Holder localHolder = (WidgetConfigurationActivity.Holder)paramView.getTag();
      if (localHolder == null) {
        localHolder = new WidgetConfigurationActivity.Holder(paramView);
      }
      final int i = getBackend(paramInt);
      Toc.CorpusMetadata localCorpusMetadata = getItem(paramInt);
      localHolder.name.setText(localCorpusMetadata.name);
      int j = this.mActivityManager.getLauncherLargeIconDensity();
      int k = WidgetUtils.getBackendIcon(i);
      Resources localResources = this.mActivity.getResources();
      Resources.Theme localTheme = this.mActivity.getTheme();
      Drawable localDrawable;
      if (Build.VERSION.SDK_INT >= 21) {
        localDrawable = localResources.getDrawableForDensity(k, j, localTheme);
      }
      for (;;)
      {
        localHolder.icon.setImageDrawable(localDrawable);
        localHolder.container.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            Intent localIntent = new Intent();
            localIntent.putExtra("backend", i);
            localIntent.putExtra("appWidgetId", WidgetConfigurationActivity.CorpusAdapter.this.mAppWidgetId);
            WidgetConfigurationActivity.CorpusAdapter.this.mActivity.setResult(-1, localIntent);
            WidgetConfigurationActivity.CorpusAdapter.this.mActivity.finish();
          }
        });
        return paramView;
        if (Build.VERSION.SDK_INT >= 15) {
          localDrawable = localResources.getDrawableForDensity(k, j);
        } else {
          localDrawable = localResources.getDrawable(k);
        }
      }
    }
  }
  
  private static final class Holder
  {
    final ViewGroup container;
    final ImageView icon;
    final TextView name;
    
    public Holder(View paramView)
    {
      this.container = ((ViewGroup)paramView.findViewById(2131755218));
      this.name = ((TextView)paramView.findViewById(2131755791));
      this.icon = ((ImageView)paramView.findViewById(2131756230));
      paramView.setTag(this);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.WidgetConfigurationActivity
 * JD-Core Version:    0.7.0.1
 */