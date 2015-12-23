package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayRatingBar;
import com.google.android.finsky.layout.play.PlayRatingBar.OnRatingChangeListener;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.RateReviewHelper;
import com.google.android.finsky.utils.UiUtils;

public class RateReviewEditor
  extends MaxWidthLinearLayout
{
  protected int mBackend;
  protected EditText mCommentView;
  protected PlayRatingBar mRatingBar;
  protected TextView mRatingDescription;
  protected ReviewChangeListener mReviewChangeListener;
  TextWatcher mTextWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable) {}
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      RateReviewEditor.this.onReviewChanged();
    }
  };
  private EditText mTitleView;
  
  public RateReviewEditor(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RateReviewEditor(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public static boolean shouldUseReviewsEditorV2()
  {
    return FinskyApp.get().getExperiments().isEnabled(12602636L);
  }
  
  public final void configure(int paramInt1, int paramInt2, String paramString1, boolean paramBoolean, int paramInt3, String paramString2, String paramString3)
  {
    this.mBackend = paramInt2;
    syncRatingDescription(paramInt3);
    configureRatingBar(paramInt3);
    TextView localTextView = (TextView)findViewById(2131755635);
    if (paramBoolean)
    {
      localTextView.setVisibility(0);
      localTextView.setText(paramString1);
      if (paramInt1 != 1) {
        break label90;
      }
      this.mTitleView.setVisibility(8);
      this.mCommentView.setVisibility(8);
    }
    for (;;)
    {
      this.mCommentView.addTextChangedListener(this.mTextWatcher);
      return;
      localTextView.setVisibility(8);
      break;
      label90:
      this.mTitleView.setText(paramString2);
      this.mCommentView.setText(paramString3);
    }
  }
  
  protected final void configureRatingBar(int paramInt)
  {
    this.mRatingBar.configure(paramInt, this.mBackend, new PlayRatingBar.OnRatingChangeListener()
    {
      public final void onRatingChanged(PlayRatingBar paramAnonymousPlayRatingBar, int paramAnonymousInt)
      {
        if (paramAnonymousInt > 0)
        {
          Context localContext = RateReviewEditor.this.getContext();
          Resources localResources = RateReviewEditor.this.getResources();
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(paramAnonymousInt);
          UiUtils.sendAccessibilityEventWithText(localContext, localResources.getQuantityString(2131296257, paramAnonymousInt, arrayOfObject), RateReviewEditor.this.mRatingDescription);
          RateReviewEditor.this.syncRatingDescription(paramAnonymousInt);
        }
        if (RateReviewEditor.this.mReviewChangeListener != null) {
          RateReviewEditor.this.mReviewChangeListener.onRatingChanged();
        }
      }
    });
    this.mRatingBar.setVerticalPadding(2131493501);
  }
  
  public String getUserComment()
  {
    return this.mCommentView.getText().toString().trim();
  }
  
  public int getUserRating()
  {
    return this.mRatingBar.getRating();
  }
  
  public String getUserTitle()
  {
    return this.mTitleView.getText().toString().trim();
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mTitleView != null) {
      this.mTitleView.removeTextChangedListener(this.mTextWatcher);
    }
    this.mCommentView.removeTextChangedListener(this.mTextWatcher);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mRatingBar = ((PlayRatingBar)findViewById(2131755987));
    this.mRatingDescription = ((TextView)findViewById(2131755988));
    this.mTitleView = ((EditText)findViewById(2131755989));
    this.mCommentView = ((EditText)findViewById(2131755990));
  }
  
  protected void onReviewChanged()
  {
    if (this.mReviewChangeListener != null) {
      this.mReviewChangeListener.onReviewChanged();
    }
  }
  
  public void setReviewChangeListener(ReviewChangeListener paramReviewChangeListener)
  {
    this.mReviewChangeListener = paramReviewChangeListener;
  }
  
  public void setUserComment(CharSequence paramCharSequence)
  {
    this.mCommentView.setText(paramCharSequence);
  }
  
  protected void syncRatingDescription(int paramInt)
  {
    Resources localResources = getResources();
    this.mRatingDescription.setText(RateReviewHelper.getRatingDescription(paramInt));
    if (paramInt == 0)
    {
      this.mRatingDescription.setTextColor(localResources.getColor(2131689625));
      return;
    }
    ColorStateList localColorStateList = CorpusResourceUtils.getSecondaryTextColor(getContext(), this.mBackend);
    this.mRatingDescription.setTextColor(localColorStateList);
  }
  
  public static abstract interface ReviewChangeListener
  {
    public abstract void onRatingChanged();
    
    public abstract void onReviewChanged();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.RateReviewEditor
 * JD-Core Version:    0.7.0.1
 */