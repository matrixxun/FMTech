package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.AgeVerificationActivity;
import com.google.android.finsky.layout.PlaylistControlButtons;
import com.google.android.finsky.protos.ArtistDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import java.util.List;

public class SongListModuleLayout
  extends LinearLayout
{
  boolean mHasBinded;
  TextView mHeader;
  PlaylistControlButtons mSongListControl;
  LinearLayout mSongsContainer;
  TextView mSubHeader;
  
  public SongListModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  static boolean isSongListMature(List<Document> paramList)
  {
    for (int i = 0; i < paramList.size(); i++) {
      if (((Document)paramList.get(i)).mDocument.mature) {
        return true;
      }
    }
    return false;
  }
  
  static boolean shouldShowArtistNames(Document paramDocument, List<Document> paramList)
  {
    String str;
    if (paramDocument.getDisplayArtist() != null) {
      str = paramDocument.getDisplayArtist().name;
    }
    for (int i = 0;; i++)
    {
      if (i >= paramList.size()) {
        break label78;
      }
      if (!TextUtils.equals(str, ((Document)paramList.get(i)).mDocument.creator))
      {
        return true;
        str = ((Document)paramList.get(0)).mDocument.creator;
        break;
      }
    }
    label78:
    return false;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeader = ((TextView)findViewById(2131755487));
    this.mSubHeader = ((TextView)findViewById(2131755614));
    this.mSongsContainer = ((LinearLayout)findViewById(2131756135));
    this.mSongListControl = ((PlaylistControlButtons)findViewById(2131756134));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.SongListModuleLayout
 * JD-Core Version:    0.7.0.1
 */