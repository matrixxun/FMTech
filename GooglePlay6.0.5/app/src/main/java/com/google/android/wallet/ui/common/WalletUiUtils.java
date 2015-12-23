package com.google.android.wallet.ui.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.common.util.AndroidUtils;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.common.util.PermissionDelegate;
import com.google.android.wallet.dependencygraph.DependencyGraphManager;
import com.google.android.wallet.dependencygraph.ResultingActionComponent;
import com.google.android.wallet.dependencygraph.TriggerComponent;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.android.wallet.ui.common.validator.AbstractValidator;
import com.google.android.wallet.ui.common.validator.AndValidator;
import com.google.android.wallet.ui.common.validator.PatternValidator;
import com.google.android.wallet.uicomponents.R.anim;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.bool;
import com.google.android.wallet.uicomponents.R.dimen;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.commerce.payments.orchestration.proto.common.DateOuterClass.Date;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldGroupingOuterClass.UiFieldGrouping;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.DateField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.SelectField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.TextField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.TextField.Validation;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public final class WalletUiUtils
{
  private static final SparseIntArray EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID;
  private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
  
  static
  {
    SparseIntArray localSparseIntArray = new SparseIntArray(16);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID = localSparseIntArray;
    localSparseIntArray.put(1, R.attr.internalUicCreditCardAmexLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(2, R.attr.internalUicCreditCardDiscoverLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(3, R.attr.internalUicCreditCardJcbLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(4, R.attr.internalUicCreditCardMastercardLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(5, R.attr.internalUicCreditCardVisaLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(6, R.attr.internalUicCreditCardDinersClubLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(15, R.attr.internalUicCreditCardEloLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(25, R.attr.internalUicCreditCardCartesBancairesLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(27, R.attr.internalUicCreditCardPlaceholderNetworkLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(21, R.attr.internalUicCreditCardUnknownLogoDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(13, R.attr.internalUicCreditCardCvcFrontDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(14, R.attr.internalUicCreditCardCvcBackDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(34, R.attr.internalUicCreditCardCvcFrontLargeDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(35, R.attr.internalUicCreditCardCvcBackLargeDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(45, R.attr.internalUicCarrierBillingDrawable);
    EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.put(26, R.attr.internalUicPaypalLightDrawable);
  }
  
  public static void addComponentToDependencyGraph(Object paramObject, int paramInt, DependencyGraphManager paramDependencyGraphManager, TriggerListener paramTriggerListener)
  {
    if (paramDependencyGraphManager == null) {}
    do
    {
      return;
      if ((paramObject instanceof View)) {
        paramObject = getBaseUiFieldView((View)paramObject);
      }
      ArrayList localArrayList = (ArrayList)paramDependencyGraphManager.mTriggerValueReferenceMap.get(paramInt);
      if ((localArrayList != null) && ((paramObject instanceof TriggerComponent)))
      {
        TriggerComponent localTriggerComponent = (TriggerComponent)paramObject;
        paramDependencyGraphManager.mTriggerComponentMap.put(paramInt, localTriggerComponent);
        localTriggerComponent.setTriggerListener(paramTriggerListener);
        localTriggerComponent.addTriggers(localArrayList);
      }
    } while ((!(paramObject instanceof ResultingActionComponent)) || (!paramDependencyGraphManager.mResultingActionComponentIds.get(paramInt)));
    ResultingActionComponent localResultingActionComponent = (ResultingActionComponent)paramObject;
    paramDependencyGraphManager.mResultingActionComponentMap.put(paramInt, localResultingActionComponent);
  }
  
  public static int[] addGroupedViewsToViewGroup(Context paramContext, List<View> paramList, UiFieldGroupingOuterClass.UiFieldGrouping[] paramArrayOfUiFieldGrouping, ViewGroup paramViewGroup)
  {
    int i = paramList.size();
    int[] arrayOfInt = new int[i];
    int j = 0;
    Object localObject1 = null;
    int k = 0;
    if (k < i)
    {
      arrayOfInt[k] = -1;
      View localView1 = (View)paramList.get(k);
      View localView2 = getBaseUiFieldView(localView1);
      if ((paramArrayOfUiFieldGrouping == null) || (j >= paramArrayOfUiFieldGrouping.length) || (k < paramArrayOfUiFieldGrouping[j].startIndex))
      {
        paramViewGroup.addView(localView1);
        if (fieldTypeSupportsAutoAdvance(localView2)) {
          arrayOfInt[k] = (-1 + paramViewGroup.getChildCount());
        }
      }
      for (;;)
      {
        k++;
        break;
        UiFieldGroupingOuterClass.UiFieldGrouping localUiFieldGrouping = paramArrayOfUiFieldGrouping[j];
        if (!(localView2 instanceof TextView))
        {
          Object[] arrayOfObject2 = new Object[4];
          arrayOfObject2[0] = Integer.valueOf(k);
          arrayOfObject2[1] = localView1.getClass().getName();
          arrayOfObject2[2] = Integer.valueOf(localUiFieldGrouping.startIndex);
          arrayOfObject2[3] = Integer.valueOf(localUiFieldGrouping.endIndex);
          throw new IllegalArgumentException(String.format("Field groupings are only supported for text and date ui fields. index %d view type: %s field grouping: %d-%d", arrayOfObject2));
        }
        Object localObject2;
        int m;
        if (k == localUiFieldGrouping.startIndex)
        {
          if (localUiFieldGrouping.endIndex != 1 + localUiFieldGrouping.startIndex)
          {
            Object[] arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = Integer.valueOf(localUiFieldGrouping.startIndex);
            arrayOfObject1[1] = Integer.valueOf(localUiFieldGrouping.endIndex);
            throw new IllegalArgumentException(String.format("Field groupings must contain exactly 2 fields (%d, %d)", arrayOfObject1));
          }
          localObject2 = new RelativeLayout(paramContext);
          paramViewGroup.addView((View)localObject2);
          m = sanitizeRelativeLayoutVerb(20);
        }
        for (;;)
        {
          ((ViewGroup)localObject2).addView(localView1);
          RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)localView1.getLayoutParams();
          localLayoutParams.width = -2;
          localLayoutParams.addRule(m);
          if ((localView1 instanceof TooltipUiFieldView)) {
            ((TooltipUiFieldView)localView1).setIconHiddenWhenUnfocused(true);
          }
          localView2.setMinimumWidth(paramContext.getResources().getDimensionPixelSize(R.dimen.wallet_uic_field_grouping_text_view_min_width));
          if (Build.VERSION.SDK_INT >= 14) {
            break;
          }
          if (k != localUiFieldGrouping.startIndex) {
            break label434;
          }
          localObject1 = localView2;
          break;
          localObject2 = (ViewGroup)paramViewGroup.getChildAt(-1 + paramViewGroup.getChildCount());
          m = sanitizeRelativeLayoutVerb(21);
          if ((localView1 instanceof TooltipUiFieldView)) {
            ((TooltipUiFieldView)localView1).moveIconToStart();
          }
          if (fieldTypeSupportsAutoAdvance(localView2)) {
            arrayOfInt[k] = (-1 + paramViewGroup.getChildCount());
          }
          j++;
        }
        label434:
        if ((localObject1.isFocusable()) && (localView2.isFocusable()))
        {
          localObject1.setNextFocusDownId(localView2.getId());
          localView2.setNextFocusUpId(localObject1.getId());
          localObject1 = null;
        }
      }
    }
    return arrayOfInt;
  }
  
  public static void advanceFocusForForm(View paramView)
  {
    if (!paramView.isFocusable()) {}
    View localView;
    for (;;)
    {
      return;
      if (Build.VERSION.SDK_INT >= 14) {}
      for (localView = paramView.focusSearch(2); localView != null; localView = paramView.focusSearch(130))
      {
        if (!AndroidUtils.isAccessibilityEnabled(paramView.getContext())) {
          break label64;
        }
        localView.postDelayed(new Runnable()
        {
          public final void run()
          {
            this.val$nextView.requestFocus();
          }
        }, 750L);
        return;
      }
    }
    label64:
    localView.requestFocus();
  }
  
  public static void animateViewAppearing(View paramView, int paramInt1, int paramInt2)
  {
    animateViewAppearing(paramView, paramInt1, 0, -1L, null);
  }
  
  @TargetApi(14)
  public static void animateViewAppearing(final View paramView, int paramInt1, int paramInt2, final long paramLong, Runnable paramRunnable)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      paramView.animate().cancel();
      paramView.setVisibility(0);
      paramView.setTranslationY(paramInt1);
      paramView.setAlpha(0.0F);
      if (paramInt1 < paramInt2) {
        paramView.setTag(R.id.view_is_animating_downwards, Boolean.TRUE);
      }
      ViewPropertyAnimator localViewPropertyAnimator = paramView.animate().alpha(1.0F).translationY(paramInt2);
      long l = paramView.animate().getDuration();
      if (paramLong >= 0L) {
        localViewPropertyAnimator.setDuration(paramLong);
      }
      localViewPropertyAnimator.setListener(new AnimatorListenerAdapter()
      {
        private void onComplete()
        {
          ViewPropertyAnimator localViewPropertyAnimator = paramView.animate().setListener(null);
          if (paramLong >= 0L) {
            localViewPropertyAnimator.setDuration(this.val$previousDuration);
          }
          paramView.setTag(R.id.view_is_animating_downwards, null);
        }
        
        public final void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          onComplete();
        }
        
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          if (this.val$endAnimationRunnable != null) {
            this.val$endAnimationRunnable.run();
          }
          if (Boolean.TRUE == paramView.getTag(R.id.view_is_animating_downwards))
          {
            View localView = paramView.findFocus();
            if (localView != null)
            {
              Rect localRect = new Rect();
              localView.getDrawingRect(localRect);
              localView.requestRectangleOnScreen(localRect);
            }
          }
          onComplete();
        }
      });
      localViewPropertyAnimator.start();
      return;
    }
    paramView.setVisibility(0);
    Animation localAnimation = AnimationUtils.loadAnimation(paramView.getContext(), R.anim.wallet_uic_fade_in);
    if (paramRunnable != null) {
      localAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public final void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          this.val$endAnimationRunnable.run();
        }
        
        public final void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public final void onAnimationStart(Animation paramAnonymousAnimation) {}
      });
    }
    paramView.startAnimation(localAnimation);
  }
  
  public static void animateViewAppearing$249729a5(View paramView, int paramInt, Runnable paramRunnable)
  {
    animateViewAppearing(paramView, paramInt, 0, -1L, paramRunnable);
  }
  
  @TargetApi(14)
  private static void animateViewDisappearingInternal(View paramView, int paramInt1, int paramInt2, final int paramInt3)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      setEditTextDescendentsFocusable(paramView, false);
      paramView.setTranslationY(paramInt1);
      paramView.animate().translationY(paramInt2).alpha(0.0F).setListener(new AnimatorListenerAdapter()
      {
        public final void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          WalletUiUtils.setEditTextDescendentsFocusable(this.val$view, true);
          this.val$view.setVisibility(paramInt3);
          this.val$view.setAlpha(1.0F);
          this.val$view.animate().setListener(null);
        }
      }).start();
      return;
    }
    paramView.startAnimation(AnimationUtils.loadAnimation(paramView.getContext(), R.anim.wallet_uic_fade_out));
    paramView.setVisibility(paramInt3);
  }
  
  public static void animateViewDisappearingToGone(View paramView, int paramInt1, int paramInt2)
  {
    animateViewDisappearingInternal(paramView, paramInt1, paramInt2, 8);
  }
  
  public static void animateViewDisappearingToInvisible$17e143a3(View paramView, int paramInt)
  {
    animateViewDisappearingInternal(paramView, paramInt, 0, 4);
  }
  
  @TargetApi(16)
  public static void announceForAccessibility(View paramView, CharSequence paramCharSequence)
  {
    if (!AndroidUtils.isAccessibilityEnabled(paramView.getContext())) {
      return;
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramView.announceForAccessibility(paramCharSequence);
      return;
    }
    AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(8);
    localAccessibilityEvent.setEnabled(true);
    localAccessibilityEvent.getText().add(paramCharSequence);
    ((AccessibilityManager)paramView.getContext().getSystemService("accessibility")).sendAccessibilityEvent(localAccessibilityEvent);
  }
  
  public static void applyUiFieldSpecificationToFormEditText(UiFieldOuterClass.UiField paramUiField, FormEditText paramFormEditText)
  {
    applyUiFieldSpecificationToFormEditText(paramUiField, paramFormEditText, null);
  }
  
  public static void applyUiFieldSpecificationToFormEditText(UiFieldOuterClass.UiField paramUiField, FormEditText paramFormEditText, Activity paramActivity)
  {
    paramFormEditText.setHint(paramUiField.label);
    boolean bool;
    if (!paramUiField.isOptional)
    {
      bool = true;
      paramFormEditText.setRequired(bool);
      if (paramUiField.dateField == null) {
        break label79;
      }
      ExpDateEditText localExpDateEditText = (ExpDateEditText)paramFormEditText;
      localExpDateEditText.addValidator(new ExpDateValidator(localExpDateEditText, paramUiField.dateField.minDate, paramUiField.dateField.maxDate));
      localExpDateEditText.enableAutoAdvance(localExpDateEditText, localExpDateEditText, true);
    }
    label79:
    label236:
    do
    {
      do
      {
        return;
        bool = false;
        break;
      } while (paramUiField.textField == null);
      paramFormEditText.setTemplateFormattingScheme(paramUiField.textField.templateFormattingScheme);
      paramFormEditText.setValue(paramUiField.textField.initialValue, false);
      paramFormEditText.setMinMaxLength(paramUiField.textField.minLength, paramUiField.textField.maxLength);
      int i;
      int k;
      switch (paramUiField.textField.keyboardLayout)
      {
      default: 
        throw new IllegalArgumentException("TextField.keyboardLayout " + paramUiField.textField.keyboardLayout + " is not supported");
      case 1: 
        i = 1;
        if (paramUiField.textField.isMasked) {
          i = 129;
        }
        int[] arrayOfInt = paramUiField.textField.keyboardLayoutVariation;
        int j = arrayOfInt.length;
        k = 0;
        if (k < j) {
          switch (arrayOfInt[k])
          {
          }
        }
        break;
      case 2: 
      case 3: 
      case 4: 
        for (;;)
        {
          k++;
          break label236;
          i = 2;
          if (paramUiField.textField.isMasked) {
            throw new IllegalArgumentException("Number passwords are not supported");
          }
          if (paramUiField.textField.templateFormattingScheme != null)
          {
            if ((0x12000000 & paramFormEditText.getImeOptions()) != 0) {
              break label417;
            }
            Context localContext = paramFormEditText.getContext();
            if ((localContext.getResources().getBoolean(R.bool.wallet_uic_is_tablet)) || (localContext.getResources().getConfiguration().orientation != 2)) {
              break label417;
            }
          }
          for (int i1 = 1;; i1 = 0)
          {
            if (i1 != 0) {
              i = 1;
            }
            if (Build.VERSION.SDK_INT < 17) {
              break;
            }
            paramFormEditText.setTextDirection(3);
            break;
          }
          i = 33;
          break;
          i = 3;
          if ((TextUtils.isEmpty(paramUiField.textField.initialValue)) && (tryAutoFillPhoneUiField(paramActivity, paramFormEditText))) {
            paramUiField.textField.initialValue = paramFormEditText.getValue();
          }
          if (Build.VERSION.SDK_INT < 17) {
            break;
          }
          paramFormEditText.setTextDirection(3);
          break;
          i |= 0x2000;
          continue;
          i |= 0x1000;
          continue;
          i |= 0x1000;
          continue;
          i |= 0x4000;
          continue;
          i |= 0x2000;
          continue;
          i |= 0x20;
          continue;
          i |= 0x60;
          continue;
          i |= 0x70;
          continue;
          i |= 0x10;
          continue;
          i |= 0x80000;
        }
      }
      Typeface localTypeface = paramFormEditText.getTypeface();
      paramFormEditText.setInputType(i);
      paramFormEditText.setTypeface(localTypeface);
    } while (paramUiField.textField.validation.length <= 0);
    label417:
    AndValidator localAndValidator = new AndValidator(new AbstractValidator[0]);
    for (UiFieldOuterClass.UiField.TextField.Validation localValidation : paramUiField.textField.validation) {
      localAndValidator.add(new PatternValidator(localValidation.errorMessage, Pattern.compile(localValidation.regex)));
    }
    paramFormEditText.addValidator(localAndValidator);
  }
  
  public static void clearFocus(View paramView)
  {
    View localView = paramView.findViewById(R.id.focus_stealer);
    if (localView != null)
    {
      localView.requestFocus();
      return;
    }
    paramView.clearFocus();
  }
  
  public static String createFifeUrl(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i;
    if ((paramBoolean) && (Build.VERSION.SDK_INT >= 18))
    {
      i = 1;
      if (i == 0) {
        break label80;
      }
    }
    label80:
    for (String str = "-rw";; str = "")
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = Integer.valueOf(paramInt1);
      arrayOfObject[2] = Integer.valueOf(paramInt2);
      arrayOfObject[3] = str;
      return String.format(localLocale, "%s=w%d-h%d-e365%s", arrayOfObject);
      i = 0;
      break;
    }
  }
  
  public static UiFieldOuterClass.UiFieldValue createUiFieldValue(View paramView, UiFieldOuterClass.UiField paramUiField)
  {
    View localView = getBaseUiFieldView(paramView);
    UiFieldOuterClass.UiFieldValue localUiFieldValue = new UiFieldOuterClass.UiFieldValue();
    localUiFieldValue.name = paramUiField.name;
    localUiFieldValue.dataToken = paramUiField.dataToken;
    if ((localView instanceof ExpDateEditText))
    {
      ExpDateEditText localExpDateEditText = (ExpDateEditText)localView;
      if (paramUiField.dateField.type != 2) {
        throw new IllegalArgumentException("Unsupported DateField type=" + paramUiField.dateField.type);
      }
      if (paramUiField.isSecure)
      {
        Locale localLocale = Locale.US;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(localExpDateEditText.getExpMonth());
        arrayOfObject[1] = Integer.valueOf(localExpDateEditText.getExpYear());
        localUiFieldValue.secureStringValue = String.format(localLocale, "%02d/%04d", arrayOfObject);
        return localUiFieldValue;
      }
      localUiFieldValue.date = new DateOuterClass.Date();
      localUiFieldValue.date.month = localExpDateEditText.getExpMonth();
      localUiFieldValue.date.year = localExpDateEditText.getExpYear();
      return localUiFieldValue;
    }
    String str;
    if ((localView instanceof FormEditText)) {
      str = ((FormEditText)localView).getValue();
    }
    while (paramUiField.isSecure)
    {
      localUiFieldValue.secureStringValue = str;
      return localUiFieldValue;
      if ((localView instanceof TextView)) {
        str = ((TextView)localView).getText().toString();
      } else if ((localView instanceof SelectFieldView)) {
        str = ((SelectFieldView)localView).getValue();
      } else {
        throw new IllegalArgumentException("Invalid uiField view: " + localView);
      }
    }
    localUiFieldValue.stringValue = str;
    return localUiFieldValue;
  }
  
  public static View createViewForLegalMessage(LayoutInflater paramLayoutInflater, int paramInt, LegalMessageOuterClass.LegalMessage paramLegalMessage, UiNode paramUiNode, ClickSpan.OnClickListener paramOnClickListener)
  {
    InfoMessageOuterClass.InfoMessage localInfoMessage = paramLegalMessage.messageText;
    InfoMessageTextView localInfoMessageTextView = null;
    if (localInfoMessage != null)
    {
      localInfoMessageTextView = (InfoMessageTextView)paramLayoutInflater.inflate(paramInt, null);
      localInfoMessageTextView.setUrlClickListener(paramOnClickListener);
      localInfoMessageTextView.setInfoMessage(paramLegalMessage.messageText);
    }
    if (!TextUtils.isEmpty(paramLegalMessage.viewerUrl))
    {
      WebViewLayout localWebViewLayout = (WebViewLayout)paramLayoutInflater.inflate(R.layout.view_web_view_layout, null);
      localWebViewLayout.loadUrlWhenReady(paramLegalMessage.viewerUrl, null);
      localWebViewLayout.setParentUiNode(paramUiNode);
      if (localInfoMessageTextView != null)
      {
        localInfoMessageTextView.setParentUiNode(localWebViewLayout);
        localWebViewLayout.setHeaderView(localInfoMessageTextView);
      }
      return localWebViewLayout;
    }
    if (localInfoMessageTextView != null)
    {
      localInfoMessageTextView.setParentUiNode(paramUiNode);
      return localInfoMessageTextView;
    }
    throw new IllegalArgumentException("Unsupported legal message configuration.");
  }
  
  public static int embeddedImageUriToDrawableResourceId(Context paramContext, String paramString)
  {
    if (!PaymentUtils.isEmbeddedImageUri(paramString)) {
      throw new IllegalArgumentException("Invalid embedded image URI: " + paramString);
    }
    int i = PaymentUtils.getEmbeddedImageId(paramString);
    int j = EMBEDDED_IMAGE_ID_TO_ATTRIBUTE_RESOURCE_ID.get(i, -1);
    if (j == -1) {
      throw new IllegalArgumentException("Invalid embedded image ID: " + i);
    }
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(new int[] { j });
    int k = localTypedArray.getResourceId(0, -1);
    localTypedArray.recycle();
    if (k == -1) {
      throw new IllegalArgumentException("Theme does not contain the requested embedded image: " + i);
    }
    return k;
  }
  
  private static boolean fieldTypeSupportsAutoAdvance(View paramView)
  {
    if ((paramView instanceof AutoAdvancer)) {}
    Integer localInteger;
    do
    {
      return true;
      localInteger = (Integer)paramView.getTag(R.id.field_type);
    } while ((localInteger != null) && (localInteger.intValue() == 2));
    return false;
  }
  
  public static View getBaseUiFieldView(View paramView)
  {
    while ((paramView instanceof FieldContainer)) {
      paramView = ((FieldContainer)paramView).getInnerFieldView();
    }
    return paramView;
  }
  
  public static Object getInitialValue(UiFieldOuterClass.UiField paramUiField)
  {
    if (paramUiField.textField != null) {
      return paramUiField.textField.initialValue;
    }
    if (paramUiField.dateField != null)
    {
      if (paramUiField.dateField.type != 2) {
        throw new IllegalArgumentException("Date fields only support month and year. type=" + paramUiField.dateField.type);
      }
      return new DateOuterClass.Date();
    }
    if (paramUiField.selectField != null) {
      return paramUiField.selectField.initialValue;
    }
    throw new IllegalArgumentException("UiField is not supported: " + paramUiField);
  }
  
  public static void getLocationRelativeToAncestorView(int[] paramArrayOfInt, View paramView1, View paramView2)
  {
    Arrays.fill(paramArrayOfInt, 0);
    paramArrayOfInt[0] += paramView1.getLeft();
    paramArrayOfInt[1] += paramView1.getTop();
    View localView;
    for (ViewParent localViewParent = paramView1.getParent(); ((localViewParent instanceof View)) && (localViewParent != paramView2); localViewParent = localView.getParent())
    {
      localView = (View)localViewParent;
      paramArrayOfInt[0] -= localView.getScrollX();
      paramArrayOfInt[1] -= localView.getScrollY();
      paramArrayOfInt[0] += localView.getLeft();
      paramArrayOfInt[1] += localView.getTop();
    }
    if (localViewParent != paramView2) {
      throw new IllegalArgumentException("ancestorView not an ancestor of view");
    }
  }
  
  public static void getTouchTarget(Rect paramRect, View paramView, int paramInt1, int paramInt2)
  {
    int i = (paramInt1 - paramView.getHeight()) / 2;
    int j = (paramInt2 - paramView.getWidth()) / 2;
    paramView.getHitRect(paramRect);
    if (j > 0) {}
    for (int k = -j;; k = 0)
    {
      int m = 0;
      if (i > 0) {
        m = -i;
      }
      paramRect.inset(k, m);
      return;
    }
  }
  
  public static CharSequence getUiFieldError(View paramView)
  {
    View localView = getBaseUiFieldView(paramView);
    if ((localView instanceof TextView)) {
      return ((TextView)localView).getError();
    }
    if ((localView instanceof FieldValidatable)) {
      return ((FieldValidatable)localView).getError();
    }
    if ((localView instanceof RegionCodeView)) {
      return null;
    }
    throw new IllegalArgumentException("Invalid uiField view: " + localView);
  }
  
  public static int getViewHeightWithMargins(View paramView)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    boolean bool = localLayoutParams instanceof ViewGroup.MarginLayoutParams;
    int i = 0;
    if (bool)
    {
      ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localLayoutParams;
      i = localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin;
    }
    return i + paramView.getHeight();
  }
  
  public static boolean hideSoftKeyboard(Context paramContext, View paramView)
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)paramContext.getSystemService("input_method");
    boolean bool = false;
    if (localInputMethodManager != null)
    {
      localInputMethodManager.hideSoftInputFromWindow(paramView.getWindowToken(), 0);
      bool = true;
    }
    return bool;
  }
  
  public static boolean isSoftKeyboardObscuringViewWithAdjustPan(Rect paramRect, View paramView, DisplayMetrics paramDisplayMetrics)
  {
    paramView.getWindowVisibleDisplayFrame(paramRect);
    return paramRect.bottom < paramDisplayMetrics.heightPixels;
  }
  
  public static SpannableString linkifyHtml$24df1acc(String paramString)
  {
    Spanned localSpanned = Html.fromHtml(paramString);
    URLSpan[] arrayOfURLSpan = (URLSpan[])localSpanned.getSpans(0, localSpanned.length(), URLSpan.class);
    SpannableString localSpannableString = new SpannableString(localSpanned);
    Linkify.addLinks(localSpannableString, 15);
    int i = arrayOfURLSpan.length;
    for (int j = 0; j < i; j++)
    {
      URLSpan localURLSpan = arrayOfURLSpan[j];
      localSpannableString.setSpan(localURLSpan, localSpanned.getSpanStart(localURLSpan), localSpanned.getSpanEnd(localURLSpan), localSpanned.getSpanFlags(localURLSpan));
    }
    return localSpannableString;
  }
  
  @TargetApi(11)
  public static void playShakeAnimationIfPossible$4709551c(Context paramContext, View paramView)
  {
    if (Build.VERSION.SDK_INT < 11) {
      return;
    }
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "translationX", new float[] { 0.0F, 1.0F });
    localObjectAnimator.setInterpolator(new TimeInterpolator()
    {
      public final float getInterpolation(float paramAnonymousFloat)
      {
        return (float)Math.sin(3.141592653589793D * (2.0D * paramAnonymousFloat) * this.val$shakeCount) * (1.0F - paramAnonymousFloat) * this.val$shakeDelta;
      }
    });
    localObjectAnimator.start();
  }
  
  @TargetApi(17)
  private static int relativeLayoutVerbForDirection(int paramInt, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default: 
      return paramInt;
    case 19: 
      if (paramBoolean) {
        return 7;
      }
      return 5;
    case 21: 
      if (paramBoolean) {
        return 11;
      }
      return 9;
    case 20: 
      if (paramBoolean) {
        return 9;
      }
      return 11;
    case 18: 
      if (paramBoolean) {
        return 5;
      }
      return 7;
    case 17: 
      if (paramBoolean) {
        return 1;
      }
      return 0;
    }
    if (paramBoolean) {
      return 0;
    }
    return 1;
  }
  
  public static void removeRule(RelativeLayout.LayoutParams paramLayoutParams, int paramInt1, int paramInt2)
  {
    boolean bool = true;
    paramLayoutParams.addRule(paramInt1, 0);
    int i;
    if (Build.VERSION.SDK_INT >= 17) {
      switch (paramInt1)
      {
      default: 
        i = 0;
        if (i != 0) {
          if (paramInt2 != 0) {
            break label85;
          }
        }
        break;
      }
    }
    for (;;)
    {
      paramLayoutParams.addRule(relativeLayoutVerbForDirection(paramInt1, bool), 0);
      return;
      i = bool;
      break;
      label85:
      bool = false;
    }
  }
  
  public static void requestFocusAndAnnounceError(View paramView)
  {
    if (paramView.hasFocus()) {
      if ((paramView instanceof FormEditText)) {
        ((FormEditText)paramView).announceError();
      }
    }
    while ((paramView.requestFocus()) || (!(paramView instanceof FormSpinner)))
    {
      do
      {
        return;
      } while (!(paramView instanceof FormSpinner));
      ((FormSpinner)paramView).announceError();
      return;
    }
    ((FormSpinner)paramView).announceError();
  }
  
  public static int sanitizeRelativeLayoutVerb(int paramInt)
  {
    if (Build.VERSION.SDK_INT < 17) {
      paramInt = relativeLayoutVerbForDirection(paramInt, true);
    }
    return paramInt;
  }
  
  public static boolean setAncestorScrollViewFillViewport(View paramView, boolean paramBoolean)
  {
    ViewParent localViewParent;
    if ((paramView instanceof ViewParent))
    {
      localViewParent = (ViewParent)paramView;
      if (localViewParent == null) {
        break label55;
      }
      if (!ScrollView.class.isInstance(localViewParent)) {
        break label45;
      }
    }
    ScrollView localScrollView;
    for (;;)
    {
      localScrollView = (ScrollView)localViewParent;
      if (localScrollView != null) {
        break label60;
      }
      return false;
      localViewParent = paramView.getParent();
      break;
      label45:
      localViewParent = localViewParent.getParent();
      break;
      label55:
      localViewParent = null;
    }
    label60:
    boolean bool = localScrollView.isFillViewport();
    localScrollView.setFillViewport(paramBoolean);
    return bool;
  }
  
  static void setEditTextDescendentsFocusable(View paramView, boolean paramBoolean)
  {
    if (((paramView instanceof EditText)) && (!paramView.hasFocus()))
    {
      paramView.setFocusable(paramBoolean);
      paramView.setFocusableInTouchMode(paramBoolean);
    }
    for (;;)
    {
      return;
      if ((paramView instanceof ViewGroup))
      {
        ViewGroup localViewGroup = (ViewGroup)paramView;
        int i = 0;
        int j = localViewGroup.getChildCount();
        while (i < j)
        {
          setEditTextDescendentsFocusable(localViewGroup.getChildAt(i), paramBoolean);
          i++;
        }
      }
    }
  }
  
  public static void setPhoneNumber(TextView paramTextView, String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {
      paramString = PhoneNumberUtils.formatNumber(paramString);
    }
    if ((paramTextView instanceof FormEditText))
    {
      ((FormEditText)paramTextView).setValue(paramString, false);
      return;
    }
    paramTextView.setText(paramString);
  }
  
  public static void setUiFieldError(View paramView, String paramString)
  {
    View localView = getBaseUiFieldView(paramView);
    if ((localView instanceof FieldValidatable)) {
      ((FieldValidatable)localView).setError(paramString);
    }
    do
    {
      return;
      if ((!(localView instanceof TextView)) && (!(localView instanceof RegionCodeView))) {
        break;
      }
    } while (TextUtils.isEmpty(paramString));
    throw new IllegalArgumentException("TextViews should never have an error message");
    throw new IllegalArgumentException("Invalid uiField view: " + localView);
  }
  
  public static void setViewBackgroundOrHide(View paramView, TypedValue paramTypedValue)
  {
    if ((paramTypedValue == null) || (paramTypedValue.type == 0))
    {
      paramView.setVisibility(8);
      return;
    }
    if ((paramTypedValue.type >= 28) && (paramTypedValue.type <= 31))
    {
      paramView.setBackgroundColor(paramTypedValue.data);
      return;
    }
    paramView.setBackgroundResource(paramTypedValue.resourceId);
  }
  
  public static void setViewsEnabledRecursive(View paramView, boolean paramBoolean)
  {
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int i = 0;
      int j = localViewGroup.getChildCount();
      while (i < j)
      {
        setViewsEnabledRecursive(localViewGroup.getChildAt(i), paramBoolean);
        i++;
      }
    }
    paramView.setEnabled(paramBoolean);
  }
  
  public static boolean showSoftKeyboardOnFirstEditText(View paramView)
  {
    ArrayList localArrayList = paramView.getFocusables(130);
    final EditText localEditText = null;
    int i = 0;
    int j = localArrayList.size();
    for (;;)
    {
      k = 0;
      if (i >= j) {
        break label64;
      }
      View localView = (View)localArrayList.get(i);
      if ((localView instanceof EditText))
      {
        if (localEditText != null) {
          break;
        }
        localEditText = (EditText)localView;
      }
      i++;
    }
    int k = 1;
    label64:
    if (localEditText != null)
    {
      if (!localEditText.requestFocus()) {}
      for (int m = 0; m != 0; m = 1)
      {
        return true;
        if ((k != 0) && ((0xFF & localEditText.getImeOptions()) == 0)) {
          localEditText.setImeOptions(0x5 | localEditText.getImeOptions());
        }
        InputMethodManager localInputMethodManager = (InputMethodManager)localEditText.getContext().getSystemService("input_method");
        if (!localInputMethodManager.showSoftInput(localEditText, 1)) {
          localEditText.postDelayed(new Runnable()
          {
            public final void run()
            {
              this.val$inputMethodManager.showSoftInput(localEditText, 1);
            }
          }, 300L);
        }
      }
    }
    return false;
  }
  
  public static boolean tryAutoFillPhoneUiField(Activity paramActivity, TextView paramTextView)
  {
    if ((paramActivity != null) && (PermissionDelegate.callerAndSelfHavePermission(paramActivity, "android.permission.READ_PHONE_STATE")))
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)paramActivity.getSystemService("phone");
      if (localTelephonyManager != null)
      {
        setPhoneNumber(paramTextView, localTelephonyManager.getLine1Number());
        return true;
      }
    }
    return false;
  }
  
  public static boolean validateUiField(View paramView, boolean paramBoolean)
  {
    View localView = getBaseUiFieldView(paramView);
    if ((localView instanceof FieldValidatable))
    {
      if (paramBoolean) {
        return ((FieldValidatable)localView).validate();
      }
      return ((FieldValidatable)localView).isValid();
    }
    if (((localView instanceof TextView)) || ((localView instanceof RegionCodeView))) {
      return true;
    }
    throw new IllegalArgumentException("Invalid uiField view: " + localView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.WalletUiUtils
 * JD-Core Version:    0.7.0.1
 */