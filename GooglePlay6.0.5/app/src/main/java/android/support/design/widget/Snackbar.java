package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.support.design.R.anim;
import android.support.design.R.dimen;
import android.support.design.R.id;
import android.support.design.R.layout;
import android.support.design.R.styleable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.widget.ViewDragHelper;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class Snackbar
{
  private static final Handler sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback()
  {
    public final boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return false;
      case 0: 
        Snackbar localSnackbar2 = (Snackbar)paramAnonymousMessage.obj;
        if (localSnackbar2.mView.getParent() == null)
        {
          ViewGroup.LayoutParams localLayoutParams2 = localSnackbar2.mView.getLayoutParams();
          if ((localLayoutParams2 instanceof CoordinatorLayout.LayoutParams))
          {
            Snackbar.Behavior localBehavior1 = new Snackbar.Behavior(localSnackbar2);
            localBehavior1.mAlphaStartSwipeDistance = SwipeDismissBehavior.clamp(0.0F, 0.1F, 1.0F);
            localBehavior1.mAlphaEndSwipeDistance = SwipeDismissBehavior.clamp(0.0F, 0.6F, 1.0F);
            localBehavior1.mSwipeDirection = 0;
            localBehavior1.mListener = new Snackbar.4(localSnackbar2);
            ((CoordinatorLayout.LayoutParams)localLayoutParams2).setBehavior(localBehavior1);
          }
          localSnackbar2.mTargetParent.addView(localSnackbar2.mView);
        }
        localSnackbar2.mView.setOnAttachStateChangeListener(new Snackbar.5(localSnackbar2));
        if (ViewCompat.isLaidOut(localSnackbar2.mView)) {
          localSnackbar2.animateViewIn();
        }
        for (;;)
        {
          return true;
          localSnackbar2.mView.setOnLayoutChangeListener(new Snackbar.6(localSnackbar2));
        }
      }
      Snackbar localSnackbar1 = (Snackbar)paramAnonymousMessage.obj;
      int i = paramAnonymousMessage.arg1;
      int k;
      if (localSnackbar1.mView.getVisibility() == 0)
      {
        ViewGroup.LayoutParams localLayoutParams1 = localSnackbar1.mView.getLayoutParams();
        boolean bool1 = localLayoutParams1 instanceof CoordinatorLayout.LayoutParams;
        int j = 0;
        if (bool1)
        {
          CoordinatorLayout.Behavior localBehavior = ((CoordinatorLayout.LayoutParams)localLayoutParams1).mBehavior;
          boolean bool2 = localBehavior instanceof SwipeDismissBehavior;
          j = 0;
          if (bool2)
          {
            SwipeDismissBehavior localSwipeDismissBehavior = (SwipeDismissBehavior)localBehavior;
            if (localSwipeDismissBehavior.mViewDragHelper == null) {
              break label320;
            }
            k = localSwipeDismissBehavior.mViewDragHelper.mDragState;
            j = 0;
            if (k != 0) {
              j = 1;
            }
          }
        }
        if (j == 0) {
          break label326;
        }
      }
      else
      {
        localSnackbar1.onViewHidden$13462e();
      }
      for (;;)
      {
        return true;
        label320:
        k = 0;
        break;
        label326:
        if (Build.VERSION.SDK_INT >= 14)
        {
          ViewCompat.animate(localSnackbar1.mView).translationY(localSnackbar1.mView.getHeight()).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250L).setListener(new Snackbar.9(localSnackbar1, i)).start();
        }
        else
        {
          Animation localAnimation = android.view.animation.AnimationUtils.loadAnimation(localSnackbar1.mView.getContext(), R.anim.design_snackbar_out);
          localAnimation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
          localAnimation.setDuration(250L);
          localAnimation.setAnimationListener(new Snackbar.10(localSnackbar1, i));
          localSnackbar1.mView.startAnimation(localAnimation);
        }
      }
    }
  });
  private Callback mCallback;
  private final Context mContext;
  private int mDuration;
  final SnackbarManager.Callback mManagerCallback = new SnackbarManager.Callback()
  {
    public final void dismiss(int paramAnonymousInt)
    {
      Snackbar.sHandler.sendMessage(Snackbar.sHandler.obtainMessage(1, paramAnonymousInt, 0, Snackbar.this));
    }
    
    public final void show()
    {
      Snackbar.sHandler.sendMessage(Snackbar.sHandler.obtainMessage(0, Snackbar.this));
    }
  };
  final ViewGroup mTargetParent;
  final SnackbarLayout mView;
  
  private Snackbar(ViewGroup paramViewGroup)
  {
    this.mTargetParent = paramViewGroup;
    this.mContext = paramViewGroup.getContext();
    ThemeUtils.checkAppCompatTheme(this.mContext);
    this.mView = ((SnackbarLayout)LayoutInflater.from(this.mContext).inflate(R.layout.design_layout_snackbar, this.mTargetParent, false));
  }
  
  private static ViewGroup findSuitableParent(View paramView)
  {
    ViewGroup localViewGroup = null;
    for (;;)
    {
      if ((paramView instanceof CoordinatorLayout)) {
        return (ViewGroup)paramView;
      }
      if ((paramView instanceof FrameLayout))
      {
        if (paramView.getId() == 16908290) {
          return (ViewGroup)paramView;
        }
        localViewGroup = (ViewGroup)paramView;
      }
      ViewParent localViewParent;
      if (paramView != null)
      {
        localViewParent = paramView.getParent();
        if (!(localViewParent instanceof View)) {
          break label67;
        }
      }
      label67:
      for (paramView = (View)localViewParent; paramView == null; paramView = null) {
        return localViewGroup;
      }
    }
  }
  
  public static Snackbar make$349db449(View paramView)
  {
    CharSequence localCharSequence = paramView.getResources().getText(2131362688);
    Snackbar localSnackbar = new Snackbar(findSuitableParent(paramView));
    localSnackbar.mView.getMessageView().setText(localCharSequence);
    localSnackbar.mDuration = 0;
    return localSnackbar;
  }
  
  final void animateViewIn()
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      ViewCompat.setTranslationY(this.mView, this.mView.getHeight());
      ViewCompat.animate(this.mView).translationY(0.0F).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250L).setListener(new ViewPropertyAnimatorListenerAdapter()
      {
        public final void onAnimationEnd(View paramAnonymousView)
        {
          if (Snackbar.this.mCallback != null) {}
          SnackbarManager.getInstance().onShown(Snackbar.this.mManagerCallback);
        }
        
        public final void onAnimationStart(View paramAnonymousView)
        {
          Snackbar.SnackbarLayout localSnackbarLayout = Snackbar.this.mView;
          ViewCompat.setAlpha(localSnackbarLayout.mMessageView, 0.0F);
          ViewCompat.animate(localSnackbarLayout.mMessageView).alpha(1.0F).setDuration(180L).setStartDelay(70L).start();
          if (localSnackbarLayout.mActionView.getVisibility() == 0)
          {
            ViewCompat.setAlpha(localSnackbarLayout.mActionView, 0.0F);
            ViewCompat.animate(localSnackbarLayout.mActionView).alpha(1.0F).setDuration(180L).setStartDelay(70L).start();
          }
        }
      }).start();
      return;
    }
    Animation localAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_in);
    localAnimation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    localAnimation.setDuration(250L);
    localAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        if (Snackbar.this.mCallback != null) {}
        SnackbarManager.getInstance().onShown(Snackbar.this.mManagerCallback);
      }
      
      public final void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public final void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    this.mView.startAnimation(localAnimation);
  }
  
  final void onViewHidden$13462e()
  {
    ViewParent localViewParent = this.mView.getParent();
    if ((localViewParent instanceof ViewGroup)) {
      ((ViewGroup)localViewParent).removeView(this.mView);
    }
    SnackbarManager localSnackbarManager = SnackbarManager.getInstance();
    SnackbarManager.Callback localCallback = this.mManagerCallback;
    synchronized (localSnackbarManager.mLock)
    {
      if (localSnackbarManager.isCurrentSnackbarLocked(localCallback))
      {
        localSnackbarManager.mCurrentSnackbar = null;
        if (localSnackbarManager.mNextSnackbar != null) {
          localSnackbarManager.showNextSnackbarLocked();
        }
      }
      return;
    }
  }
  
  public final Snackbar setAction$6ff2c59b(final View.OnClickListener paramOnClickListener)
  {
    CharSequence localCharSequence = this.mContext.getText(2131362690);
    Button localButton = this.mView.getActionView();
    if (TextUtils.isEmpty(localCharSequence))
    {
      localButton.setVisibility(8);
      localButton.setOnClickListener(null);
      return this;
    }
    localButton.setVisibility(0);
    localButton.setText(localCharSequence);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        paramOnClickListener.onClick(paramAnonymousView);
        Snackbar.access$000(Snackbar.this, 1);
      }
    });
    return this;
  }
  
  public final void show()
  {
    SnackbarManager localSnackbarManager = SnackbarManager.getInstance();
    int i = this.mDuration;
    SnackbarManager.Callback localCallback = this.mManagerCallback;
    for (;;)
    {
      synchronized (localSnackbarManager.mLock)
      {
        if (localSnackbarManager.isCurrentSnackbarLocked(localCallback))
        {
          localSnackbarManager.mCurrentSnackbar.duration = i;
          localSnackbarManager.mHandler.removeCallbacksAndMessages(localSnackbarManager.mCurrentSnackbar);
          localSnackbarManager.scheduleTimeoutLocked(localSnackbarManager.mCurrentSnackbar);
          return;
        }
        if (localSnackbarManager.isNextSnackbarLocked(localCallback))
        {
          localSnackbarManager.mNextSnackbar.duration = i;
          if ((localSnackbarManager.mCurrentSnackbar == null) || (!SnackbarManager.cancelSnackbarLocked(localSnackbarManager.mCurrentSnackbar, 4))) {
            break;
          }
          return;
        }
      }
      localSnackbarManager.mNextSnackbar = new SnackbarManager.SnackbarRecord(i, localCallback);
    }
    localSnackbarManager.mCurrentSnackbar = null;
    localSnackbarManager.showNextSnackbarLocked();
  }
  
  final class Behavior
    extends SwipeDismissBehavior<Snackbar.SnackbarLayout>
  {
    Behavior() {}
    
    private boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, Snackbar.SnackbarLayout paramSnackbarLayout, MotionEvent paramMotionEvent)
    {
      if (paramCoordinatorLayout.isPointInChildBounds(paramSnackbarLayout, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())) {
        switch (paramMotionEvent.getActionMasked())
        {
        }
      }
      for (;;)
      {
        return super.onInterceptTouchEvent(paramCoordinatorLayout, paramSnackbarLayout, paramMotionEvent);
        SnackbarManager.getInstance().cancelTimeout(Snackbar.this.mManagerCallback);
        continue;
        SnackbarManager.getInstance().restoreTimeout(Snackbar.this.mManagerCallback);
      }
    }
  }
  
  public static abstract class Callback {}
  
  public static class SnackbarLayout
    extends LinearLayout
  {
    Button mActionView;
    private int mMaxInlineActionWidth;
    private int mMaxWidth;
    TextView mMessageView;
    private OnAttachStateChangeListener mOnAttachStateChangeListener;
    private OnLayoutChangeListener mOnLayoutChangeListener;
    
    public SnackbarLayout(Context paramContext)
    {
      this(paramContext, null);
    }
    
    public SnackbarLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SnackbarLayout);
      this.mMaxWidth = localTypedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
      this.mMaxInlineActionWidth = localTypedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
      if (localTypedArray.hasValue(R.styleable.SnackbarLayout_elevation)) {
        ViewCompat.setElevation(this, localTypedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
      }
      localTypedArray.recycle();
      setClickable(true);
      LayoutInflater.from(paramContext).inflate(R.layout.design_layout_snackbar_include, this);
      ViewCompat.setAccessibilityLiveRegion$5359dc9a(this);
    }
    
    private boolean updateViewsWithinLayout(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = getOrientation();
      boolean bool = false;
      if (paramInt1 != i)
      {
        setOrientation(paramInt1);
        bool = true;
      }
      TextView localTextView;
      if ((this.mMessageView.getPaddingTop() != paramInt2) || (this.mMessageView.getPaddingBottom() != paramInt3))
      {
        localTextView = this.mMessageView;
        if (!ViewCompat.isPaddingRelative(localTextView)) {
          break label82;
        }
        ViewCompat.setPaddingRelative(localTextView, ViewCompat.getPaddingStart(localTextView), paramInt2, ViewCompat.getPaddingEnd(localTextView), paramInt3);
      }
      for (;;)
      {
        bool = true;
        return bool;
        label82:
        localTextView.setPadding(localTextView.getPaddingLeft(), paramInt2, localTextView.getPaddingRight(), paramInt3);
      }
    }
    
    Button getActionView()
    {
      return this.mActionView;
    }
    
    TextView getMessageView()
    {
      return this.mMessageView;
    }
    
    protected void onAttachedToWindow()
    {
      super.onAttachedToWindow();
    }
    
    protected void onDetachedFromWindow()
    {
      super.onDetachedFromWindow();
      if (this.mOnAttachStateChangeListener != null) {
        this.mOnAttachStateChangeListener.onViewDetachedFromWindow$3c7ec8c3();
      }
    }
    
    protected void onFinishInflate()
    {
      super.onFinishInflate();
      this.mMessageView = ((TextView)findViewById(R.id.snackbar_text));
      this.mActionView = ((Button)findViewById(R.id.snackbar_action));
    }
    
    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      if ((paramBoolean) && (this.mOnLayoutChangeListener != null)) {
        this.mOnLayoutChangeListener.onLayoutChange$5b6f797d();
      }
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if ((this.mMaxWidth > 0) && (getMeasuredWidth() > this.mMaxWidth))
      {
        paramInt1 = View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824);
        super.onMeasure(paramInt1, paramInt2);
      }
      int i = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
      int j = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
      if (this.mMessageView.getLayout().getLineCount() > 1) {}
      int n;
      for (int k = 1; (k != 0) && (this.mMaxInlineActionWidth > 0) && (this.mActionView.getMeasuredWidth() > this.mMaxInlineActionWidth); k = 0)
      {
        boolean bool2 = updateViewsWithinLayout(1, i, i - j);
        n = 0;
        if (bool2) {
          n = 1;
        }
        if (n != 0) {
          super.onMeasure(paramInt1, paramInt2);
        }
        return;
      }
      if (k != 0) {}
      for (int m = i;; m = j)
      {
        boolean bool1 = updateViewsWithinLayout(0, m, m);
        n = 0;
        if (!bool1) {
          break;
        }
        n = 1;
        break;
      }
    }
    
    void setOnAttachStateChangeListener(OnAttachStateChangeListener paramOnAttachStateChangeListener)
    {
      this.mOnAttachStateChangeListener = paramOnAttachStateChangeListener;
    }
    
    void setOnLayoutChangeListener(OnLayoutChangeListener paramOnLayoutChangeListener)
    {
      this.mOnLayoutChangeListener = paramOnLayoutChangeListener;
    }
    
    static abstract interface OnAttachStateChangeListener
    {
      public abstract void onViewDetachedFromWindow$3c7ec8c3();
    }
    
    static abstract interface OnLayoutChangeListener
    {
      public abstract void onLayoutChange$5b6f797d();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.design.widget.Snackbar
 * JD-Core Version:    0.7.0.1
 */