package com.google.android.finsky.setup;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView.BufferType;

@TargetApi(19)
public class SetupWizardNavBar
  extends Fragment
{
  public Button mBackButton;
  private ViewGroup mNavigationBarView;
  public Button mNextButton;
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Activity localActivity = getActivity();
    TypedArray localTypedArray = getActivity().obtainStyledAttributes(new int[] { 2130772050, 16842800, 16842801 });
    int i = localTypedArray.getResourceId(0, 0);
    int j;
    if (i == 0)
    {
      float[] arrayOfFloat1 = new float[3];
      float[] arrayOfFloat2 = new float[3];
      Color.colorToHSV(localTypedArray.getColor(1, 0), arrayOfFloat1);
      Color.colorToHSV(localTypedArray.getColor(2, 0), arrayOfFloat2);
      if (arrayOfFloat1[2] <= arrayOfFloat2[2]) {
        break label179;
      }
      j = 1;
      if (j == 0) {
        break label185;
      }
    }
    label179:
    label185:
    for (i = 2131558616;; i = 2131558617)
    {
      localTypedArray.recycle();
      this.mNavigationBarView = ((ViewGroup)LayoutInflater.from(new ContextThemeWrapper(localActivity, i)).inflate(2130969106, paramViewGroup, false));
      this.mNextButton = ((Button)this.mNavigationBarView.findViewById(2131755302));
      this.mBackButton = ((Button)this.mNavigationBarView.findViewById(2131755301));
      resetButtonsState();
      return this.mNavigationBarView;
      j = 0;
      break;
    }
  }
  
  public final void resetButtonsState()
  {
    this.mNextButton = ((Button)this.mNavigationBarView.findViewById(2131755302));
    this.mNextButton.setText(2131362741);
    this.mNextButton.setOnClickListener(null);
    this.mNextButton.setEnabled(true);
    this.mBackButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        SetupWizardNavBar.this.getActivity().onBackPressed();
      }
    });
    this.mBackButton.setEnabled(true);
  }
  
  public static class NavButton
    extends Button
  {
    private float mButtonDisabledAlpha;
    
    public NavButton(Context paramContext)
    {
      super();
      readAttributes();
    }
    
    public NavButton(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      readAttributes();
    }
    
    public NavButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
      readAttributes();
    }
    
    @TargetApi(21)
    public NavButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
      super(paramAttributeSet, paramInt1, paramInt2);
      readAttributes();
    }
    
    private void readAttributes()
    {
      TypedValue localTypedValue = new TypedValue();
      getResources().getValue(2131493516, localTypedValue, true);
      this.mButtonDisabledAlpha = localTypedValue.getFloat();
    }
    
    public void setEnabled(boolean paramBoolean)
    {
      super.setEnabled(paramBoolean);
      if (paramBoolean) {}
      for (float f = 1.0F;; f = this.mButtonDisabledAlpha)
      {
        setAlpha(f);
        return;
      }
    }
    
    public void setText(CharSequence paramCharSequence, TextView.BufferType paramBufferType)
    {
      if (getId() == 2131755301) {
        paramCharSequence = "";
      }
      super.setText(paramCharSequence, paramBufferType);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.setup.SetupWizardNavBar
 * JD-Core Version:    0.7.0.1
 */