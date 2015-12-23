package com.google.android.finsky.utils.hats;

import android.support.v4.util.LongSparseArray;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.protos.Survey;
import com.google.android.finsky.protos.SurveyResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import java.util.Arrays;
import java.util.List;

public final class SurveyStore
{
  public String mAccountName;
  LongSparseArray<Long> mDismissedSurveyList;
  public boolean mIsSurveyDialogVisible;
  List<Survey> mSurveyList = null;
  
  public SurveyStore(String paramString)
  {
    this.mAccountName = paramString;
    long[] arrayOfLong = Utils.commaUnpackLongs((String)FinskyPreferences.dismissedSurveyIds.get(this.mAccountName).get());
    Arrays.sort(arrayOfLong);
    this.mDismissedSurveyList = new LongSparseArray(arrayOfLong.length);
    for (int i = 0; i < arrayOfLong.length; i++) {
      this.mDismissedSurveyList.append(arrayOfLong[i], null);
    }
  }
  
  final void updateDismissedSurveysPreference()
  {
    long[] arrayOfLong = new long[this.mDismissedSurveyList.size()];
    for (int i = 0; i < this.mDismissedSurveyList.size(); i++) {
      arrayOfLong[i] = this.mDismissedSurveyList.keyAt(i);
    }
    FinskyPreferences.dismissedSurveyIds.get(this.mAccountName).put(Utils.commaPackLongs(arrayOfLong));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.hats.SurveyStore
 * JD-Core Version:    0.7.0.1
 */