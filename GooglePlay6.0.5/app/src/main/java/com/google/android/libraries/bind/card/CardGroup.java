package com.google.android.libraries.bind.card;

import android.view.View;
import android.view.View.OnLongClickListener;
import com.google.android.libraries.bind.data.BindingViewGroup;
import com.google.android.libraries.bind.data.DataList;
import com.google.android.libraries.bind.logging.Logd;

public abstract class CardGroup
{
  public static final Logd LOGD = Logd.get(CardGroup.class);
  private static final View.OnLongClickListener editableViewOnLongClickListener = new View.OnLongClickListener()
  {
    public final boolean onLongClick(View paramAnonymousView)
    {
      if ((paramAnonymousView instanceof BindingViewGroup)) {
        return ((BindingViewGroup)paramAnonymousView).startEditingIfPossible();
      }
      return false;
    }
  };
  public final DataList groupList;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.card.CardGroup
 * JD-Core Version:    0.7.0.1
 */