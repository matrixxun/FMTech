package com.google.android.play.hats;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.play.R.id;
import com.google.android.play.layout.CardLinearLayout;

public class PlayHappinessSurvey
  extends CardLinearLayout
{
  public LinearLayout mAnswerOptionContainer;
  public TextView mHeaderView;
  public final LayoutInflater mLayoutInflater;
  public TextView mQuestionView;
  private SurveyEventListener mSurveyEventListener;
  
  public PlayHappinessSurvey(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayHappinessSurvey(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mLayoutInflater = LayoutInflater.from(paramContext);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeaderView = ((TextView)findViewById(R.id.play_happiness_survey_header));
    this.mQuestionView = ((TextView)findViewById(R.id.play_happiness_survey_question));
    this.mAnswerOptionContainer = ((LinearLayout)findViewById(R.id.play_happiness_survey_answer_option_container));
  }
  
  public void setSurveyEventListener(SurveyEventListener paramSurveyEventListener)
  {
    this.mSurveyEventListener = paramSurveyEventListener;
  }
  
  public static abstract interface SurveyEventListener
  {
    public abstract void onResponse(PlayHappinessSurveyModel.PlayHappinessSurveyAnswerOption paramPlayHappinessSurveyAnswerOption);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.hats.PlayHappinessSurvey
 * JD-Core Version:    0.7.0.1
 */