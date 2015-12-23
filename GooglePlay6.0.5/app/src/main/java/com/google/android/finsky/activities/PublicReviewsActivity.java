package com.google.android.finsky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.play.image.FifeImageView;
import java.util.List;

public class PublicReviewsActivity
  extends AppCompatActivity
{
  private ButtonBar mButtonBar;
  private boolean mClickDebounce = false;
  
  public static Intent createIntent(Document paramDocument)
  {
    Intent localIntent = new Intent(FinskyApp.get(), PublicReviewsActivity.class);
    localIntent.putExtra("author", paramDocument);
    return localIntent;
  }
  
  private void finishAsCanceled()
  {
    setResult(0);
    finish();
  }
  
  public void onBackPressed()
  {
    setResult(0);
    super.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130969029);
    Document localDocument = (Document)getIntent().getParcelableExtra("author");
    this.mButtonBar = ((ButtonBar)findViewById(2131755977).findViewById(2131755300));
    this.mButtonBar.setPositiveButtonTitle(2131362418);
    this.mButtonBar.setNegativeButtonTitle(2131361915);
    this.mButtonBar.setClickListener(new ButtonBar.ClickListener()
    {
      public final void onNegativeButtonClick()
      {
        if (PublicReviewsActivity.this.mClickDebounce) {
          return;
        }
        PublicReviewsActivity.access$002$3eb613a4(PublicReviewsActivity.this);
        PublicReviewsActivity.this.setResult(0);
        PublicReviewsActivity.this.finish();
      }
      
      public final void onPositiveButtonClick()
      {
        if (PublicReviewsActivity.this.mClickDebounce) {
          return;
        }
        PublicReviewsActivity.access$002$3eb613a4(PublicReviewsActivity.this);
        Toast.makeText(PublicReviewsActivity.this.getBaseContext(), 2131362198, 0).show();
        FinskyPreferences.acceptedPlusReviews.get(FinskyApp.get().getCurrentAccountName()).put(Boolean.valueOf(true));
        PublicReviewsActivity.this.setResult(-1);
        PublicReviewsActivity.this.finish();
      }
    });
    ((TextView)findViewById(2131756043)).setText(localDocument.mDocument.title);
    FifeImageView localFifeImageView = (FifeImageView)findViewById(2131755993);
    Common.Image localImage = (Common.Image)localDocument.getImages(4).get(0);
    localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    int j = (int)paramMotionEvent.getX();
    int k = (int)paramMotionEvent.getY();
    View localView = getWindow().getDecorView();
    if ((i == 0) && ((j < 0) || (j >= localView.getWidth()) || (k < 0) || (k >= localView.getHeight())))
    {
      finishAsCanceled();
      return true;
    }
    if (i == 4)
    {
      finishAsCanceled();
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.PublicReviewsActivity
 * JD-Core Version:    0.7.0.1
 */