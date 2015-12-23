package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.id;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public final class AlertDialog
  extends AppCompatDialog
  implements DialogInterface
{
  private AlertController mAlert = new AlertController(getContext(), this, getWindow());
  
  AlertDialog(Context paramContext, int paramInt)
  {
    super(paramContext, resolveDialogTheme(paramContext, paramInt));
  }
  
  static int resolveDialogTheme(Context paramContext, int paramInt)
  {
    if (paramInt >= 16777216) {
      return paramInt;
    }
    TypedValue localTypedValue = new TypedValue();
    paramContext.getTheme().resolveAttribute(R.attr.alertDialogTheme, localTypedValue, true);
    return localTypedValue.resourceId;
  }
  
  public final Button getButton$717182de()
  {
    return this.mAlert.mButtonPositive;
  }
  
  protected final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    AlertController localAlertController = this.mAlert;
    localAlertController.mDialog.getDelegate().requestWindowFeature(1);
    int i;
    ViewGroup localViewGroup1;
    View localView5;
    label120:
    int j;
    label128:
    label235:
    ViewGroup localViewGroup2;
    ViewGroup localViewGroup3;
    label364:
    int k;
    label411:
    label455:
    label499:
    int m;
    label507:
    int n;
    label565:
    label583:
    int i1;
    label601:
    int i2;
    label619:
    Object localObject;
    label688:
    int i4;
    label701:
    int i5;
    label709:
    int i6;
    View localView9;
    View localView10;
    if ((localAlertController.mButtonPanelSideLayout != 0) && (localAlertController.mButtonPanelLayoutHint == 1))
    {
      i = localAlertController.mButtonPanelSideLayout;
      localAlertController.mDialog.setContentView(i);
      View localView1 = localAlertController.mWindow.findViewById(R.id.parentPanel);
      View localView2 = localView1.findViewById(R.id.topPanel);
      View localView3 = localView1.findViewById(R.id.contentPanel);
      View localView4 = localView1.findViewById(R.id.buttonPanel);
      localViewGroup1 = (ViewGroup)localView1.findViewById(R.id.customPanel);
      if (localAlertController.mView == null) {
        break label843;
      }
      localView5 = localAlertController.mView;
      if (localView5 == null) {
        break label878;
      }
      j = 1;
      if ((j == 0) || (!AlertController.canTextInput(localView5))) {
        localAlertController.mWindow.setFlags(131072, 131072);
      }
      if (j == 0) {
        break label884;
      }
      FrameLayout localFrameLayout = (FrameLayout)localAlertController.mWindow.findViewById(R.id.custom);
      localFrameLayout.addView(localView5, new ViewGroup.LayoutParams(-1, -1));
      if (localAlertController.mViewSpacingSpecified) {
        localFrameLayout.setPadding(localAlertController.mViewSpacingLeft, localAlertController.mViewSpacingTop, localAlertController.mViewSpacingRight, localAlertController.mViewSpacingBottom);
      }
      if (localAlertController.mListView != null) {
        ((LinearLayout.LayoutParams)localViewGroup1.getLayoutParams()).weight = 0.0F;
      }
      View localView6 = localViewGroup1.findViewById(R.id.topPanel);
      View localView7 = localViewGroup1.findViewById(R.id.contentPanel);
      View localView8 = localViewGroup1.findViewById(R.id.buttonPanel);
      localViewGroup2 = AlertController.resolvePanel(localView6, localView2);
      localViewGroup3 = AlertController.resolvePanel(localView7, localView3);
      ViewGroup localViewGroup4 = AlertController.resolvePanel(localView8, localView4);
      localAlertController.mScrollView = ((NestedScrollView)localAlertController.mWindow.findViewById(R.id.scrollView));
      localAlertController.mScrollView.setFocusable(false);
      localAlertController.mScrollView.setNestedScrollingEnabled(false);
      localAlertController.mMessageView = ((TextView)localViewGroup3.findViewById(16908299));
      if (localAlertController.mMessageView != null)
      {
        if (localAlertController.mMessage == null) {
          break label894;
        }
        localAlertController.mMessageView.setText(localAlertController.mMessage);
      }
      k = 0;
      localAlertController.mButtonPositive = ((Button)localViewGroup4.findViewById(16908313));
      localAlertController.mButtonPositive.setOnClickListener(localAlertController.mButtonHandler);
      if (!TextUtils.isEmpty(localAlertController.mButtonPositiveText)) {
        break label984;
      }
      localAlertController.mButtonPositive.setVisibility(8);
      localAlertController.mButtonNegative = ((Button)localViewGroup4.findViewById(16908314));
      localAlertController.mButtonNegative.setOnClickListener(localAlertController.mButtonHandler);
      if (!TextUtils.isEmpty(localAlertController.mButtonNegativeText)) {
        break label1009;
      }
      localAlertController.mButtonNegative.setVisibility(8);
      localAlertController.mButtonNeutral = ((Button)localViewGroup4.findViewById(16908315));
      localAlertController.mButtonNeutral.setOnClickListener(localAlertController.mButtonHandler);
      if (!TextUtils.isEmpty(localAlertController.mButtonNeutralText)) {
        break label1037;
      }
      localAlertController.mButtonNeutral.setVisibility(8);
      if (k == 0) {
        break label1065;
      }
      m = 1;
      if (m == 0) {
        localViewGroup4.setVisibility(8);
      }
      if (localAlertController.mCustomTitleView == null) {
        break label1071;
      }
      ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -2);
      localViewGroup2.addView(localAlertController.mCustomTitleView, 0, localLayoutParams);
      localAlertController.mWindow.findViewById(R.id.title_template).setVisibility(8);
      if ((localViewGroup1 == null) || (localViewGroup1.getVisibility() == 8)) {
        break label1263;
      }
      n = 1;
      if ((localViewGroup2 == null) || (localViewGroup2.getVisibility() == 8)) {
        break label1269;
      }
      i1 = 1;
      if ((localViewGroup4 == null) || (localViewGroup4.getVisibility() == 8)) {
        break label1275;
      }
      i2 = 1;
      if ((i2 == 0) && (localViewGroup3 != null))
      {
        View localView11 = localViewGroup3.findViewById(R.id.textSpacerNoButtons);
        if (localView11 != null) {
          localView11.setVisibility(0);
        }
      }
      if ((i1 != 0) && (localAlertController.mScrollView != null)) {
        localAlertController.mScrollView.setClipToPadding(true);
      }
      if (n == 0)
      {
        if (localAlertController.mListView == null) {
          break label1281;
        }
        localObject = localAlertController.mListView;
        if (localObject != null)
        {
          if (i1 == 0) {
            break label1290;
          }
          i4 = 1;
          if (i2 == 0) {
            break label1296;
          }
          i5 = 2;
          i6 = i4 | i5;
          localView9 = localAlertController.mWindow.findViewById(R.id.scrollIndicatorUp);
          localView10 = localAlertController.mWindow.findViewById(R.id.scrollIndicatorDown);
          if (Build.VERSION.SDK_INT < 23) {
            break label1302;
          }
          ViewCompat.setScrollIndicators((View)localObject, i6, 3);
          if (localView9 != null) {
            localViewGroup3.removeView(localView9);
          }
          if (localView10 != null) {
            localViewGroup3.removeView(localView10);
          }
        }
      }
    }
    for (;;)
    {
      ListView localListView = localAlertController.mListView;
      if ((localListView != null) && (localAlertController.mAdapter != null))
      {
        localListView.setAdapter(localAlertController.mAdapter);
        int i3 = localAlertController.mCheckedItem;
        if (i3 >= 0)
        {
          localListView.setItemChecked(i3, true);
          localListView.setSelection(i3);
        }
      }
      return;
      i = localAlertController.mAlertDialogLayout;
      break;
      label843:
      if (localAlertController.mViewLayoutResId != 0)
      {
        localView5 = LayoutInflater.from(localAlertController.mContext).inflate(localAlertController.mViewLayoutResId, localViewGroup1, false);
        break label120;
      }
      localView5 = null;
      break label120;
      label878:
      j = 0;
      break label128;
      label884:
      localViewGroup1.setVisibility(8);
      break label235;
      label894:
      localAlertController.mMessageView.setVisibility(8);
      localAlertController.mScrollView.removeView(localAlertController.mMessageView);
      if (localAlertController.mListView != null)
      {
        ViewGroup localViewGroup5 = (ViewGroup)localAlertController.mScrollView.getParent();
        int i8 = localViewGroup5.indexOfChild(localAlertController.mScrollView);
        localViewGroup5.removeViewAt(i8);
        localViewGroup5.addView(localAlertController.mListView, i8, new ViewGroup.LayoutParams(-1, -1));
        break label364;
      }
      localViewGroup3.setVisibility(8);
      break label364;
      label984:
      localAlertController.mButtonPositive.setText(localAlertController.mButtonPositiveText);
      localAlertController.mButtonPositive.setVisibility(0);
      k = 1;
      break label411;
      label1009:
      localAlertController.mButtonNegative.setText(localAlertController.mButtonNegativeText);
      localAlertController.mButtonNegative.setVisibility(0);
      k |= 0x2;
      break label455;
      label1037:
      localAlertController.mButtonNeutral.setText(localAlertController.mButtonNeutralText);
      localAlertController.mButtonNeutral.setVisibility(0);
      k |= 0x4;
      break label499;
      label1065:
      m = 0;
      break label507;
      label1071:
      localAlertController.mIconView = ((ImageView)localAlertController.mWindow.findViewById(16908294));
      int i7;
      if (!TextUtils.isEmpty(localAlertController.mTitle)) {
        i7 = 1;
      }
      for (;;)
      {
        if (i7 != 0)
        {
          localAlertController.mTitleView = ((TextView)localAlertController.mWindow.findViewById(R.id.alertTitle));
          localAlertController.mTitleView.setText(localAlertController.mTitle);
          if (localAlertController.mIconId != 0)
          {
            localAlertController.mIconView.setImageResource(localAlertController.mIconId);
            break;
            i7 = 0;
            continue;
          }
          if (localAlertController.mIcon != null)
          {
            localAlertController.mIconView.setImageDrawable(localAlertController.mIcon);
            break;
          }
          localAlertController.mTitleView.setPadding(localAlertController.mIconView.getPaddingLeft(), localAlertController.mIconView.getPaddingTop(), localAlertController.mIconView.getPaddingRight(), localAlertController.mIconView.getPaddingBottom());
          localAlertController.mIconView.setVisibility(8);
          break;
        }
      }
      localAlertController.mWindow.findViewById(R.id.title_template).setVisibility(8);
      localAlertController.mIconView.setVisibility(8);
      localViewGroup2.setVisibility(8);
      break label565;
      label1263:
      n = 0;
      break label583;
      label1269:
      i1 = 0;
      break label601;
      label1275:
      i2 = 0;
      break label619;
      label1281:
      localObject = localAlertController.mScrollView;
      break label688;
      label1290:
      i4 = 0;
      break label701;
      label1296:
      i5 = 0;
      break label709;
      label1302:
      if ((localView9 != null) && ((i6 & 0x1) == 0))
      {
        localViewGroup3.removeView(localView9);
        localView9 = null;
      }
      if ((localView10 != null) && ((i6 & 0x2) == 0))
      {
        localViewGroup3.removeView(localView10);
        localView10 = null;
      }
      if ((localView9 != null) || (localView10 != null)) {
        if (localAlertController.mMessage != null)
        {
          localAlertController.mScrollView.setOnScrollChangeListener(new AlertController.2(localAlertController, localView9, localView10));
          localAlertController.mScrollView.post(new AlertController.3(localAlertController, localView9, localView10));
        }
        else if (localAlertController.mListView != null)
        {
          localAlertController.mListView.setOnScrollListener(new AlertController.4(localAlertController, localView9, localView10));
          localAlertController.mListView.post(new AlertController.5(localAlertController, localView9, localView10));
        }
        else
        {
          if (localView9 != null) {
            localViewGroup3.removeView(localView9);
          }
          if (localView10 != null) {
            localViewGroup3.removeView(localView10);
          }
        }
      }
    }
  }
  
  public final boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    AlertController localAlertController = this.mAlert;
    if ((localAlertController.mScrollView != null) && (localAlertController.mScrollView.executeKeyEvent(paramKeyEvent))) {}
    for (int i = 1; i != 0; i = 0) {
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public final boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    AlertController localAlertController = this.mAlert;
    if ((localAlertController.mScrollView != null) && (localAlertController.mScrollView.executeKeyEvent(paramKeyEvent))) {}
    for (int i = 1; i != 0; i = 0) {
      return true;
    }
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    super.setTitle(paramCharSequence);
    this.mAlert.setTitle(paramCharSequence);
  }
  
  public static final class Builder
  {
    public final AlertController.AlertParams P;
    private int mTheme;
    
    public Builder(Context paramContext)
    {
      this(paramContext, AlertDialog.resolveDialogTheme(paramContext, 0));
    }
    
    public Builder(Context paramContext, int paramInt)
    {
      this.P = new AlertController.AlertParams(new ContextThemeWrapper(paramContext, AlertDialog.resolveDialogTheme(paramContext, paramInt)));
      this.mTheme = paramInt;
    }
    
    public final AlertDialog create()
    {
      AlertDialog localAlertDialog = new AlertDialog(this.P.mContext, this.mTheme);
      AlertController.AlertParams localAlertParams = this.P;
      AlertController localAlertController = localAlertDialog.mAlert;
      ListView localListView;
      Object localObject;
      if (localAlertParams.mCustomTitleView != null)
      {
        localAlertController.mCustomTitleView = localAlertParams.mCustomTitleView;
        if (localAlertParams.mMessage != null)
        {
          CharSequence localCharSequence = localAlertParams.mMessage;
          localAlertController.mMessage = localCharSequence;
          if (localAlertController.mMessageView != null) {
            localAlertController.mMessageView.setText(localCharSequence);
          }
        }
        if (localAlertParams.mPositiveButtonText != null) {
          localAlertController.setButton(-1, localAlertParams.mPositiveButtonText, localAlertParams.mPositiveButtonListener, null);
        }
        if (localAlertParams.mNegativeButtonText != null) {
          localAlertController.setButton(-2, localAlertParams.mNegativeButtonText, localAlertParams.mNegativeButtonListener, null);
        }
        if (localAlertParams.mNeutralButtonText != null) {
          localAlertController.setButton(-3, localAlertParams.mNeutralButtonText, localAlertParams.mNeutralButtonListener, null);
        }
        if ((localAlertParams.mItems != null) || (localAlertParams.mCursor != null) || (localAlertParams.mAdapter != null))
        {
          localListView = (ListView)localAlertParams.mInflater.inflate(localAlertController.mListLayout, null);
          if (!localAlertParams.mIsMultiChoice) {
            break label606;
          }
          if (localAlertParams.mCursor != null) {
            break label582;
          }
          localObject = new AlertController.AlertParams.1(localAlertParams, localAlertParams.mContext, localAlertController.mMultiChoiceItemLayout, localAlertParams.mItems, localListView);
          label220:
          localAlertController.mAdapter = ((ListAdapter)localObject);
          localAlertController.mCheckedItem = localAlertParams.mCheckedItem;
          if (localAlertParams.mOnClickListener == null) {
            break label728;
          }
          localListView.setOnItemClickListener(new AlertController.AlertParams.3(localAlertParams, localAlertController));
          label255:
          if (localAlertParams.mOnItemSelectedListener != null) {
            localListView.setOnItemSelectedListener(localAlertParams.mOnItemSelectedListener);
          }
          if (!localAlertParams.mIsSingleChoice) {
            break label754;
          }
          localListView.setChoiceMode(1);
          label284:
          localAlertController.mListView = localListView;
        }
        if (localAlertParams.mView == null) {
          break label791;
        }
        if (!localAlertParams.mViewSpacingSpecified) {
          break label770;
        }
        View localView = localAlertParams.mView;
        int m = localAlertParams.mViewSpacingLeft;
        int n = localAlertParams.mViewSpacingTop;
        int i1 = localAlertParams.mViewSpacingRight;
        int i2 = localAlertParams.mViewSpacingBottom;
        localAlertController.mView = localView;
        localAlertController.mViewLayoutResId = 0;
        localAlertController.mViewSpacingSpecified = true;
        localAlertController.mViewSpacingLeft = m;
        localAlertController.mViewSpacingTop = n;
        localAlertController.mViewSpacingRight = i1;
        localAlertController.mViewSpacingBottom = i2;
      }
      for (;;)
      {
        localAlertDialog.setCancelable(this.P.mCancelable);
        if (this.P.mCancelable) {
          localAlertDialog.setCanceledOnTouchOutside(true);
        }
        localAlertDialog.setOnCancelListener(this.P.mOnCancelListener);
        localAlertDialog.setOnDismissListener(this.P.mOnDismissListener);
        if (this.P.mOnKeyListener != null) {
          localAlertDialog.setOnKeyListener(this.P.mOnKeyListener);
        }
        return localAlertDialog;
        if (localAlertParams.mTitle != null) {
          localAlertController.setTitle(localAlertParams.mTitle);
        }
        if (localAlertParams.mIcon != null)
        {
          Drawable localDrawable = localAlertParams.mIcon;
          localAlertController.mIcon = localDrawable;
          localAlertController.mIconId = 0;
          if (localAlertController.mIconView != null)
          {
            if (localDrawable == null) {
              break label570;
            }
            localAlertController.mIconView.setImageDrawable(localDrawable);
          }
        }
        for (;;)
        {
          if (localAlertParams.mIconId != 0) {
            localAlertController.setIcon(localAlertParams.mIconId);
          }
          if (localAlertParams.mIconAttrId == 0) {
            break;
          }
          int i = localAlertParams.mIconAttrId;
          TypedValue localTypedValue = new TypedValue();
          localAlertController.mContext.getTheme().resolveAttribute(i, localTypedValue, true);
          localAlertController.setIcon(localTypedValue.resourceId);
          break;
          label570:
          localAlertController.mIconView.setVisibility(8);
        }
        label582:
        localObject = new AlertController.AlertParams.2(localAlertParams, localAlertParams.mContext, localAlertParams.mCursor, localListView, localAlertController);
        break label220;
        label606:
        if (localAlertParams.mIsSingleChoice) {}
        for (int j = localAlertController.mSingleChoiceItemLayout;; j = localAlertController.mListItemLayout)
        {
          if (localAlertParams.mCursor == null) {
            break label690;
          }
          Context localContext = localAlertParams.mContext;
          Cursor localCursor = localAlertParams.mCursor;
          String[] arrayOfString = new String[1];
          arrayOfString[0] = localAlertParams.mLabelColumn;
          localObject = new SimpleCursorAdapter(localContext, j, localCursor, arrayOfString, new int[] { 16908308 });
          break;
        }
        label690:
        if (localAlertParams.mAdapter != null)
        {
          localObject = localAlertParams.mAdapter;
          break label220;
        }
        localObject = new AlertController.CheckedItemAdapter(localAlertParams.mContext, j, localAlertParams.mItems);
        break label220;
        label728:
        if (localAlertParams.mOnCheckboxClickListener == null) {
          break label255;
        }
        localListView.setOnItemClickListener(new AlertController.AlertParams.4(localAlertParams, localListView, localAlertController));
        break label255;
        label754:
        if (!localAlertParams.mIsMultiChoice) {
          break label284;
        }
        localListView.setChoiceMode(2);
        break label284;
        label770:
        localAlertController.mView = localAlertParams.mView;
        localAlertController.mViewLayoutResId = 0;
        localAlertController.mViewSpacingSpecified = false;
        continue;
        label791:
        if (localAlertParams.mViewLayoutResId != 0)
        {
          int k = localAlertParams.mViewLayoutResId;
          localAlertController.mView = null;
          localAlertController.mViewLayoutResId = k;
          localAlertController.mViewSpacingSpecified = false;
        }
      }
    }
    
    public final Builder setOnKeyListener(DialogInterface.OnKeyListener paramOnKeyListener)
    {
      this.P.mOnKeyListener = paramOnKeyListener;
      return this;
    }
    
    public final Builder setTitle(CharSequence paramCharSequence)
    {
      this.P.mTitle = paramCharSequence;
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.app.AlertDialog
 * JD-Core Version:    0.7.0.1
 */