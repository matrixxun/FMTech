package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.finsky.layout.play.PlayRatingBar;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.RateReviewHelper;
import com.google.android.play.utils.PlayCorpusUtils;
import java.util.ArrayList;
import java.util.List;

public class RateReviewEditor2
  extends RateReviewEditor
{
  private View.OnFocusChangeListener mCommentFocusListener;
  private InsetDrawable mEditTextInsetDrawable;
  private Drawable mEditTextOriginalBackground;
  private String mPreviousComment;
  private ButtonBar mReviewButtonBar;
  private TextView mReviewTip;
  private boolean mWasRatingAutoSubmitted;
  
  public RateReviewEditor2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RateReviewEditor2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public static String formatComment(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList(2);
    if (!TextUtils.isEmpty(paramString1)) {
      localArrayList.add(paramString1);
    }
    if (!TextUtils.isEmpty(paramString2)) {
      localArrayList.add(paramString2);
    }
    return TextUtils.join("\n", localArrayList);
  }
  
  public final void configure$5fc451fa(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean)
  {
    int i = 2131689770;
    this.mPreviousComment = paramString;
    this.mBackend = paramInt2;
    this.mWasRatingAutoSubmitted = paramBoolean;
    this.mReviewButtonBar.measure(0, 0);
    int j = this.mReviewButtonBar.getMeasuredHeight();
    ((LinearLayout.LayoutParams)this.mReviewButtonBar.getLayoutParams()).setMargins(0, -j, 0, 0);
    this.mEditTextInsetDrawable = new InsetDrawable(this.mEditTextOriginalBackground, 0, 0, 0, j);
    syncRatingDescription(paramInt3);
    configureRatingBar(paramInt3);
    this.mReviewButtonBar.setVisibility(8);
    int k = PlayCorpusUtils.getPrimaryTextColor(getContext(), paramInt2).getDefaultColor();
    this.mReviewButtonBar.setPositiveButtonTextColor(k);
    this.mReviewButtonBar.setNegativeButtonTextColor(getResources().getColor(2131689798));
    this.mReviewButtonBar.setPositiveButtonEnabled(false);
    Resources localResources;
    Object localObject;
    if (paramInt1 == 1)
    {
      this.mCommentView.setVisibility(8);
      localResources = getContext().getResources();
      switch (this.mBackend)
      {
      default: 
        localObject = null;
        label191:
        if (localObject != null) {
          this.mReviewTip.setText((CharSequence)localObject);
        }
        break;
      }
    }
    for (;;)
    {
      this.mCommentView.addTextChangedListener(this.mTextWatcher);
      this.mCommentView.setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public final void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean) {
            if (RateReviewEditor2.this.mReviewButtonBar.getVisibility() != 0)
            {
              RateReviewEditor2.this.mCommentView.setBackgroundDrawable(RateReviewEditor2.this.mEditTextInsetDrawable);
              RateReviewEditor2.this.mReviewButtonBar.setVisibility(0);
              RateReviewEditor2.this.mReviewTip.setVisibility(8);
              RateReviewEditor2.this.post(new Runnable()
              {
                public final void run()
                {
                  ((InputMethodManager)RateReviewEditor2.this.getContext().getSystemService("input_method")).showSoftInput(RateReviewEditor2.this.mCommentView, 1);
                }
              });
            }
          }
          for (;;)
          {
            if (RateReviewEditor2.this.mCommentFocusListener != null) {
              RateReviewEditor2.this.mCommentFocusListener.onFocusChange(paramAnonymousView, paramAnonymousBoolean);
            }
            return;
            if (RateReviewEditor2.this.mReviewButtonBar.getVisibility() == 0)
            {
              RateReviewEditor2.this.mCommentView.setBackgroundDrawable(RateReviewEditor2.this.mEditTextOriginalBackground);
              RateReviewEditor2.this.mReviewButtonBar.setVisibility(8);
              if (!TextUtils.isEmpty(RateReviewEditor2.this.mReviewTip.getText())) {
                RateReviewEditor2.this.mReviewTip.setVisibility(0);
              }
            }
            ((InputMethodManager)RateReviewEditor2.this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(RateReviewEditor2.this.mCommentView.getWindowToken(), 0);
          }
        }
      });
      return;
      this.mCommentView.setText(paramString);
      EditText localEditText = this.mCommentView;
      Context localContext = getContext();
      switch (this.mBackend)
      {
      case 5: 
      default: 
        if (!CorpusResourceUtils.isEnterpriseTheme()) {
          break;
        }
      }
      for (;;)
      {
        int m = i;
        for (;;)
        {
          ViewCompat.setBackgroundTintList(localEditText, localContext.getResources().getColorStateList(m));
          break;
          if (CorpusResourceUtils.isEnterpriseTheme()) {
            m = i;
          } else {
            m = 2131689773;
          }
        }
        i = 2131689776;
        continue;
        i = 2131689791;
        continue;
        i = 2131689782;
        continue;
        i = 2131689788;
        continue;
        i = 2131689785;
      }
      localObject = localResources.getString(2131362620);
      break label191;
      this.mReviewTip.setVisibility(8);
    }
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
    return "";
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mReviewTip = ((TextView)findViewById(2131755991));
    this.mReviewButtonBar = ((ButtonBar)findViewById(2131755992));
    this.mEditTextOriginalBackground = this.mCommentView.getBackground();
  }
  
  protected final void onReviewChanged()
  {
    ButtonBar localButtonBar = this.mReviewButtonBar;
    if (!this.mPreviousComment.equals(getUserComment())) {}
    for (boolean bool = true;; bool = false)
    {
      localButtonBar.setPositiveButtonEnabled(bool);
      super.onReviewChanged();
      return;
    }
  }
  
  public void setClickListener(final ButtonBar.ClickListener paramClickListener)
  {
    this.mReviewButtonBar.setClickListener(new ButtonBar.ClickListener()
    {
      public final void onNegativeButtonClick()
      {
        RateReviewEditor2.access$500(RateReviewEditor2.this, new Runnable()
        {
          public final void run()
          {
            RateReviewEditor2.2.this.val$clickListener.onNegativeButtonClick();
          }
        });
      }
      
      public final void onPositiveButtonClick()
      {
        RateReviewEditor2.access$500(RateReviewEditor2.this, new Runnable()
        {
          public final void run()
          {
            RateReviewEditor2.2.this.val$clickListener.onPositiveButtonClick();
          }
        });
      }
    });
  }
  
  public void setCommentFocusChangeListener(View.OnFocusChangeListener paramOnFocusChangeListener)
  {
    this.mCommentFocusListener = paramOnFocusChangeListener;
  }
  
  protected final void syncRatingDescription(int paramInt)
  {
    Resources localResources = getResources();
    if (this.mWasRatingAutoSubmitted) {
      this.mRatingDescription.setText(2131362632);
    }
    for (;;)
    {
      this.mRatingDescription.setTextColor(localResources.getColor(2131689625));
      return;
      this.mRatingDescription.setText(RateReviewHelper.getRatingDescription(paramInt));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.RateReviewEditor2
 * JD-Core Version:    0.7.0.1
 */