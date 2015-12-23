package com.google.android.play.hats;

import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.SurveyAnswer;
import com.google.android.finsky.protos.SurveyContent;
import java.util.ArrayList;
import java.util.List;

public final class PlayHappinessSurveyModel
{
  public final List<PlayHappinessSurveyAnswerOption> answerOptionList;
  public final String surveyHeader;
  public final String surveyQuestion;
  
  public PlayHappinessSurveyModel(SurveyContent paramSurveyContent)
  {
    this.surveyHeader = paramSurveyContent.title;
    this.surveyQuestion = paramSurveyContent.question;
    this.answerOptionList = new ArrayList();
    for (SurveyAnswer localSurveyAnswer : paramSurveyContent.answer)
    {
      PlayHappinessSurveyAnswerOption localPlayHappinessSurveyAnswerOption = new PlayHappinessSurveyAnswerOption(localSurveyAnswer.answerType, localSurveyAnswer.description, localSurveyAnswer.icon.imageUrl, localSurveyAnswer.icon.supportsFifeUrlOptions);
      this.answerOptionList.add(localPlayHappinessSurveyAnswerOption);
    }
  }
  
  public static final class PlayHappinessSurveyAnswerOption
  {
    public final int answerId;
    public final String content;
    public final String imageUrl;
    public final boolean supportsFifeUrlOptions;
    
    public PlayHappinessSurveyAnswerOption(int paramInt, String paramString1, String paramString2, boolean paramBoolean)
    {
      this.answerId = paramInt;
      this.content = paramString1;
      this.imageUrl = paramString2;
      this.supportsFifeUrlOptions = paramBoolean;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.hats.PlayHappinessSurveyModel
 * JD-Core Version:    0.7.0.1
 */