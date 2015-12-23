package com.google.android.finsky.utils.hats;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import com.google.android.libraries.happiness.HatsSurveyClient;
import com.google.android.libraries.happiness.HatsSurveyManager;
import com.google.android.libraries.happiness.HatsSurveyParams;
import java.io.IOException;
import java.util.List;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HappinessSurveyController
  implements HatsSurveyClient
{
  private static boolean mHasRequestedCookie = false;
  private static String mZwiebackCookie;
  private final ViewGroup mContainerView;
  private final Activity mFragmentActivity;
  private final FragmentManager mFragmentManager;
  public final String mSiteId;
  private boolean mSkipPrompt = false;
  private HatsSurveyManager mSurveyManager;
  
  public HappinessSurveyController(PageFragment paramPageFragment, String paramString, boolean paramBoolean)
  {
    this.mFragmentActivity = paramPageFragment.getActivity();
    this.mFragmentManager = paramPageFragment.mFragmentManager;
    this.mContainerView = ((ViewGroup)paramPageFragment.getActivity().findViewById(2131755701));
    this.mSiteId = paramString;
    this.mSkipPrompt = paramBoolean;
  }
  
  private void dismissSurvey()
  {
    if (this.mSurveyManager != null) {}
    for (DialogFragment localDialogFragment = this.mSurveyManager.getSurveyDialog();; localDialogFragment = null)
    {
      if ((localDialogFragment != null) && (localDialogFragment.mFragmentManager != null)) {
        localDialogFragment.dismissInternal(false);
      }
      this.mFragmentActivity.setRequestedOrientation(-1);
      removeSurveyPrompt();
      return;
    }
  }
  
  private void lockScreen()
  {
    if (Build.VERSION.SDK_INT >= 18)
    {
      this.mFragmentActivity.setRequestedOrientation(14);
      return;
    }
    switch (((WindowManager)this.mFragmentActivity.getSystemService("window")).getDefaultDisplay().getRotation())
    {
    default: 
      this.mFragmentActivity.setRequestedOrientation(1);
      return;
    case 1: 
      this.mFragmentActivity.setRequestedOrientation(0);
      return;
    case 2: 
      this.mFragmentActivity.setRequestedOrientation(9);
      return;
    }
    this.mFragmentActivity.setRequestedOrientation(8);
  }
  
  private void removeSurveyPrompt()
  {
    if (this.mContainerView == null) {}
    label117:
    for (;;)
    {
      return;
      final View localView = this.mContainerView.findViewById(2131756157);
      if (localView != null)
      {
        if (Build.VERSION.SDK_INT >= 11)
        {
          float[] arrayOfFloat = new float[1];
          arrayOfFloat[0] = localView.getHeight();
          ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(localView, "translationY", arrayOfFloat);
          localObjectAnimator.addListener(new Animator.AnimatorListener()
          {
            public final void onAnimationCancel(Animator paramAnonymousAnimator)
            {
              HappinessSurveyController.this.mContainerView.removeView(localView);
            }
            
            public final void onAnimationEnd(Animator paramAnonymousAnimator)
            {
              HappinessSurveyController.this.mContainerView.removeView(localView);
            }
            
            public final void onAnimationRepeat(Animator paramAnonymousAnimator) {}
            
            public final void onAnimationStart(Animator paramAnonymousAnimator) {}
          });
          localObjectAnimator.start();
        }
        for (;;)
        {
          if (this.mFragmentActivity == null) {
            break label117;
          }
          String str = this.mSiteId;
          PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.lastSurveyActionMs.get(str);
          if (localSharedPreference == null) {
            break;
          }
          localSharedPreference.put(Long.valueOf(System.currentTimeMillis()));
          return;
          this.mContainerView.removeView(localView);
        }
      }
    }
  }
  
  private void showSurveyDialog()
  {
    lockScreen();
    Fragment localFragment = this.mFragmentManager.findFragmentByTag("hats-survey");
    if (localFragment != null) {
      this.mFragmentManager.beginTransaction().remove(localFragment).commit();
    }
    this.mFragmentManager.beginTransaction().add(this.mSurveyManager.getSurveyDialog(), "hats-survey").commit();
  }
  
  private void showSurveyPrompt()
  {
    if ((this.mFragmentActivity == null) || (this.mSurveyManager == null) || (this.mContainerView == null)) {}
    View localView;
    do
    {
      do
      {
        return;
      } while (this.mContainerView.findViewById(2131756157) != null);
      localView = LayoutInflater.from(this.mFragmentActivity).inflate(2130969131, this.mContainerView, false);
      ((TextView)localView.findViewById(2131755173)).setText(2131362782);
      ((TextView)localView.findViewById(2131755291)).setText(2131362781);
      localView.findViewById(2131756158).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          HappinessSurveyController.this.showSurveyDialog();
        }
      });
      localView.findViewById(2131756159).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          HappinessSurveyController.this.removeSurveyPrompt();
        }
      });
      this.mContainerView.addView(localView);
    } while (Build.VERSION.SDK_INT < 11);
    localView.measure(View.MeasureSpec.makeMeasureSpec(this.mContainerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.mContainerView.getHeight(), -2147483648));
    localView.setTranslationY(localView.getMeasuredHeight());
    ObjectAnimator.ofFloat(localView, "translationY", new float[] { 0.0F }).start();
  }
  
  public String getSiteId()
  {
    return this.mSiteId;
  }
  
  public void onSurveyCanceled()
  {
    dismissSurvey();
  }
  
  public void onSurveyComplete(boolean paramBoolean1, boolean paramBoolean2)
  {
    dismissSurvey();
    if (paramBoolean1)
    {
      Activity localActivity = this.mFragmentActivity;
      if (localActivity != null) {
        Toast.makeText(localActivity, 2131362780, 0).show();
      }
    }
  }
  
  public void onSurveyReady()
  {
    if (this.mSkipPrompt)
    {
      showSurveyDialog();
      return;
    }
    showSurveyPrompt();
  }
  
  public void onSurveyResponse(String paramString1, String paramString2) {}
  
  public void onWindowError() {}
  
  public void start()
  {
    if (mZwiebackCookie == null) {
      if (!mHasRequestedCookie)
      {
        Utils.executeMultiThreaded(new LoadZwiebackTask((byte)0), new Void[0]);
        mHasRequestedCookie = true;
      }
    }
    while ((this.mSurveyManager != null) || (this.mFragmentActivity == null)) {
      return;
    }
    HatsSurveyParams localHatsSurveyParams = new HatsSurveyParams(mZwiebackCookie, this.mSiteId);
    this.mSurveyManager = new HatsSurveyManager(this.mFragmentActivity, this, localHatsSurveyParams);
    this.mSurveyManager.requestSurvey();
  }
  
  private final class LoadZwiebackTask
    extends AsyncTask<Void, Void, String>
  {
    private LoadZwiebackTask() {}
    
    private static String doInBackground$606be067()
    {
      CookieSyncManager.createInstance(FinskyApp.get());
      String str1 = CookieManager.getInstance().getCookie("https://www.google.com");
      if (str1 != null)
      {
        String[] arrayOfString = str1.split(";");
        for (int k = 0; k < arrayOfString.length; k++) {
          if (arrayOfString[k].startsWith("NID=")) {
            return str1;
          }
        }
      }
      BasicCookieStore localBasicCookieStore = new BasicCookieStore();
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      BasicHttpContext localBasicHttpContext = new BasicHttpContext();
      localBasicHttpContext.setAttribute("http.cookie-store", localBasicCookieStore);
      HttpGet localHttpGet = new HttpGet("https://www.google.com");
      try
      {
        localDefaultHttpClient.execute(localHttpGet, localBasicHttpContext);
        List localList = localBasicCookieStore.getCookies();
        int i = 0;
        int j = localList.size();
        while (i < j)
        {
          if ("NID".equals(((Cookie)localList.get(i)).getName()))
          {
            String str2 = ((Cookie)localList.get(i)).getValue();
            return str2;
          }
          i++;
        }
        Object[] arrayOfObject;
        return null;
      }
      catch (IOException localIOException)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = localIOException.getMessage();
        FinskyLog.w("Exception making HaTS Zwieback cookie HTTP request: %s", arrayOfObject);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.hats.HappinessSurveyController
 * JD-Core Version:    0.7.0.1
 */