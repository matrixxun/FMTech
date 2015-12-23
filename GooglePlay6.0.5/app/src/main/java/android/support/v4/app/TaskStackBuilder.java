package android.support.v4.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public final class TaskStackBuilder
  implements Iterable<Intent>
{
  private static final TaskStackBuilderImpl IMPL = new TaskStackBuilderImplBase();
  private final ArrayList<Intent> mIntents = new ArrayList();
  public final Context mSourceContext;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      IMPL = new TaskStackBuilderImplHoneycomb();
      return;
    }
  }
  
  private TaskStackBuilder(Context paramContext)
  {
    this.mSourceContext = paramContext;
  }
  
  public static TaskStackBuilder create(Context paramContext)
  {
    return new TaskStackBuilder(paramContext);
  }
  
  public final TaskStackBuilder addNextIntent(Intent paramIntent)
  {
    this.mIntents.add(paramIntent);
    return this;
  }
  
  public final TaskStackBuilder addParentStack(ComponentName paramComponentName)
  {
    int i = this.mIntents.size();
    try
    {
      Intent localIntent;
      for (Object localObject = NavUtils.getParentActivityIntent(this.mSourceContext, paramComponentName); localObject != null; localObject = localIntent)
      {
        this.mIntents.add(i, localObject);
        localIntent = NavUtils.getParentActivityIntent(this.mSourceContext, ((Intent)localObject).getComponent());
      }
      return this;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
      throw new IllegalArgumentException(localNameNotFoundException);
    }
  }
  
  public final Iterator<Intent> iterator()
  {
    return this.mIntents.iterator();
  }
  
  public final void startActivities()
  {
    int i = 1;
    if (this.mIntents.isEmpty()) {
      throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
    }
    Intent[] arrayOfIntent = (Intent[])this.mIntents.toArray(new Intent[this.mIntents.size()]);
    arrayOfIntent[0] = new Intent(arrayOfIntent[0]).addFlags(268484608);
    Context localContext = this.mSourceContext;
    int j = Build.VERSION.SDK_INT;
    if (j >= 16) {
      localContext.startActivities(arrayOfIntent, null);
    }
    for (;;)
    {
      if (i == 0)
      {
        Intent localIntent = new Intent(arrayOfIntent[(-1 + arrayOfIntent.length)]);
        localIntent.addFlags(268435456);
        this.mSourceContext.startActivity(localIntent);
      }
      return;
      if (j >= 11) {
        localContext.startActivities(arrayOfIntent);
      } else {
        i = 0;
      }
    }
  }
  
  public static abstract interface SupportParentable
  {
    public abstract Intent getSupportParentActivityIntent();
  }
  
  static abstract interface TaskStackBuilderImpl {}
  
  static final class TaskStackBuilderImplBase
    implements TaskStackBuilder.TaskStackBuilderImpl
  {}
  
  static final class TaskStackBuilderImplHoneycomb
    implements TaskStackBuilder.TaskStackBuilderImpl
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.TaskStackBuilder
 * JD-Core Version:    0.7.0.1
 */