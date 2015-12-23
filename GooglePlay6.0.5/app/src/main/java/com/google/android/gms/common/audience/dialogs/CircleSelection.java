package com.google.android.gms.common.audience.dialogs;

import android.content.Intent;
import com.google.android.gms.common.people.data.AudienceMember;
import java.util.List;

public final class CircleSelection
{
  public static abstract interface UpdateBuilder
  {
    public abstract Intent build();
    
    public abstract UpdateBuilder setAccountName(String paramString);
    
    public abstract UpdateBuilder setClientApplicationId(String paramString);
    
    public abstract UpdateBuilder setInitialCircles(List<AudienceMember> paramList);
    
    public abstract UpdateBuilder setUpdatePersonId(String paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.audience.dialogs.CircleSelection
 * JD-Core Version:    0.7.0.1
 */