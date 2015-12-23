package com.google.android.finsky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.protos.SurveyContent;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.hats.HatsUtils;
import com.google.android.play.R.id;
import com.google.android.play.R.layout;
import com.google.android.play.hats.PlayHappinessSurvey;
import com.google.android.play.hats.PlayHappinessSurvey.1;
import com.google.android.play.hats.PlayHappinessSurvey.SurveyEventListener;
import com.google.android.play.hats.PlayHappinessSurveyModel;
import com.google.android.play.hats.PlayHappinessSurveyModel.PlayHappinessSurveyAnswerOption;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.FifeImageView;
import java.util.Iterator;
import java.util.List;

public class SurveyActivity
  extends AppCompatActivity
  implements PlayHappinessSurvey.SurveyEventListener
{
  private int mSurveyContext;
  private long mSurveyId;
  private PlayHappinessSurveyModel mSurveyModel;
  private PlayHappinessSurvey mSurveyView;
  
  public static Intent createIntent(long paramLong, int paramInt, SurveyContent paramSurveyContent)
  {
    Intent localIntent = new Intent(FinskyApp.get(), SurveyActivity.class);
    localIntent.putExtra("SurveyActivity.survey_id", paramLong);
    localIntent.putExtra("SurveyActivity.survey_context", paramInt);
    localIntent.putExtra("SurveyActivity.survey_content", ParcelableProto.forProto(paramSurveyContent));
    return localIntent;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130969129);
    this.mSurveyView = ((PlayHappinessSurvey)findViewById(2131755905));
    Intent localIntent = getIntent();
    this.mSurveyId = localIntent.getLongExtra("SurveyActivity.survey_id", -1L);
    this.mSurveyContext = localIntent.getIntExtra("SurveyActivity.survey_context", -1);
    this.mSurveyModel = new PlayHappinessSurveyModel((SurveyContent)ParcelableProto.getProtoFromIntent(localIntent, "SurveyActivity.survey_content"));
    PlayHappinessSurvey localPlayHappinessSurvey = this.mSurveyView;
    PlayHappinessSurveyModel localPlayHappinessSurveyModel = this.mSurveyModel;
    BitmapLoader localBitmapLoader = FinskyApp.get().mBitmapLoader;
    localPlayHappinessSurvey.mHeaderView.setText(localPlayHappinessSurveyModel.surveyHeader);
    localPlayHappinessSurvey.mQuestionView.setText(localPlayHappinessSurveyModel.surveyQuestion);
    localPlayHappinessSurvey.mAnswerOptionContainer.removeAllViews();
    Iterator localIterator = localPlayHappinessSurveyModel.answerOptionList.iterator();
    do
    {
      if (!localIterator.hasNext()) {
        break;
      }
    } while (((PlayHappinessSurveyModel.PlayHappinessSurveyAnswerOption)localIterator.next()).imageUrl == null);
    for (int i = 1;; i = 0)
    {
      int j = 0;
      while (j < localPlayHappinessSurveyModel.answerOptionList.size())
      {
        PlayHappinessSurveyModel.PlayHappinessSurveyAnswerOption localPlayHappinessSurveyAnswerOption = (PlayHappinessSurveyModel.PlayHappinessSurveyAnswerOption)localPlayHappinessSurveyModel.answerOptionList.get(j);
        View localView = localPlayHappinessSurvey.mLayoutInflater.inflate(R.layout.play_happiness_survey_answer_option, localPlayHappinessSurvey.mAnswerOptionContainer, false);
        FifeImageView localFifeImageView = (FifeImageView)localView.findViewById(R.id.icon);
        if (localPlayHappinessSurveyAnswerOption.imageUrl != null)
        {
          localFifeImageView.setImage(localPlayHappinessSurveyAnswerOption.imageUrl, localPlayHappinessSurveyAnswerOption.supportsFifeUrlOptions, localBitmapLoader);
          ((TextView)localView.findViewById(R.id.text_content)).setText(localPlayHappinessSurveyAnswerOption.content);
          localView.setVisibility(0);
          localView.setOnClickListener(new PlayHappinessSurvey.1(localPlayHappinessSurvey, localPlayHappinessSurveyAnswerOption));
          localPlayHappinessSurvey.mAnswerOptionContainer.addView(localView);
          j++;
        }
        else
        {
          if (i != 0) {}
          for (int k = 4;; k = 8)
          {
            localFifeImageView.setVisibility(k);
            break;
          }
        }
      }
      this.mSurveyView.setSurveyEventListener(this);
      HatsUtils.logSurveyEvent(5, this.mSurveyId, -1, this.mSurveyContext);
      return;
    }
  }
  
  public final void onResponse(PlayHappinessSurveyModel.PlayHappinessSurveyAnswerOption paramPlayHappinessSurveyAnswerOption)
  {
    Toast.makeText(this, 2131362780, 0).show();
    HatsUtils.logSurveyEvent(3, this.mSurveyId, paramPlayHappinessSurveyAnswerOption.answerId, this.mSurveyContext);
    HatsUtils.dismissSurvey(this.mSurveyId, 3);
    finish();
  }
  
  protected void onStart()
  {
    super.onStart();
    FinskyApp localFinskyApp = FinskyApp.get();
    localFinskyApp.getClientMutationCache(localFinskyApp.getCurrentAccountName()).getSurveyStore().mIsSurveyDialogVisible = true;
  }
  
  protected void onStop()
  {
    super.onStop();
    FinskyApp localFinskyApp = FinskyApp.get();
    localFinskyApp.getClientMutationCache(localFinskyApp.getCurrentAccountName()).getSurveyStore().mIsSurveyDialogVisible = false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.SurveyActivity
 * JD-Core Version:    0.7.0.1
 */