package com.google.android.finsky.detailspage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.text.TextUtils;
import android.transition.ArcMotion;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.DetailsDataBasedFragment;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.DetailsPartialFadeSection;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.transition.CircleTransition;
import com.google.android.finsky.transition.ReverseContentTransition;
import com.google.android.finsky.transition.ReverseHeroTransition;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.PlayAnimationUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.transition.BaseTransitionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(22)
public final class DetailsFragment2LollipopMR1
  extends DetailsFragment2
{
  private Bitmap mOriginalThumbnailBitmap;
  private Interpolator mRevealTransitionInterpolator;
  private int mSourceHeight;
  private int mSourceLeft;
  private int mSourceTop;
  private int mSourceWidth;
  private FifeImageView mThumbnailInSource;
  
  public static DetailsFragment2LollipopMR1 newInstance(Document paramDocument, String paramString1, String paramString2, String paramString3, boolean paramBoolean, View paramView)
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    DetailsFragment2LollipopMR1 localDetailsFragment2LollipopMR1 = new DetailsFragment2LollipopMR1();
    localDetailsFragment2LollipopMR1.setDfeAccount(paramString3);
    localDetailsFragment2LollipopMR1.setDfeTocAndUrl(localFinskyApp.mToc, paramString1);
    localDetailsFragment2LollipopMR1.setArgument("finsky.DetailsDataBasedFragment.document", paramDocument);
    localDetailsFragment2LollipopMR1.setArgument("finsky.DetailsFragment.continueUrl", paramString2);
    localDetailsFragment2LollipopMR1.setArgument("finsky.DetailsFragment.acquisitionOverride", paramBoolean);
    int i;
    if (paramView != null)
    {
      i = 1;
      if (i != 0)
      {
        String str = paramView.getTransitionName();
        if (!TextUtils.isEmpty(str))
        {
          localDetailsFragment2LollipopMR1.mRevealTransitionSharedName = str;
          if (!str.startsWith("transition_card_details:cover:")) {
            break label228;
          }
          TransitionInflater localTransitionInflater = TransitionInflater.from(localFinskyApp);
          localDetailsFragment2LollipopMR1.mRevealTransitionInterpolator = AnimationUtils.loadInterpolator(localFinskyApp, 17563661);
          Transition localTransition = localTransitionInflater.inflateTransition(2131099648);
          localTransition.setInterpolator(localDetailsFragment2LollipopMR1.mRevealTransitionInterpolator);
          localTransition.addListener(new BaseTransitionListener()
          {
            public final void onTransitionEnd(Transition paramAnonymousTransition)
            {
              if (((ViewGroup)DetailsFragment2LollipopMR1.this.mView == null) || (DetailsFragment2LollipopMR1.this.mModulesAdapter == null)) {}
              Drawable localDrawable;
              do
              {
                return;
                DetailsFragment2LollipopMR1.access$900(DetailsFragment2LollipopMR1.this);
                if (DetailsFragment2LollipopMR1.this.mHeroGraphicView != null) {
                  DetailsFragment2LollipopMR1.this.mHeroGraphicView.unfreezeCorpusFill$1349ef();
                }
                AnimatorSet localAnimatorSet = new AnimatorSet();
                ArrayList localArrayList = new ArrayList();
                Iterator localIterator = DetailsFragment2LollipopMR1.access$300(DetailsFragment2LollipopMR1.this).iterator();
                while (localIterator.hasNext())
                {
                  View localView = (View)localIterator.next();
                  localArrayList.add(PlayAnimationUtils.getFadeAnimator$57852d9d(localView, localView.getAlpha(), 400L));
                }
                localAnimatorSet.playTogether(localArrayList);
                localAnimatorSet.start();
                DetailsFragment2LollipopMR1.this.mIsInImageTransitionPhase = false;
                for (int i = 0; i < DetailsFragment2LollipopMR1.this.mAllModules.size(); i++)
                {
                  FinskyModule localFinskyModule = (FinskyModule)DetailsFragment2LollipopMR1.this.mAllModules.get(i);
                  if ((DetailsFragment2LollipopMR1.this.isModuleReadyForDisplay(localFinskyModule)) && (!DetailsFragment2LollipopMR1.this.mAddedModules.contains(localFinskyModule))) {
                    DetailsFragment2LollipopMR1.this.addModule(localFinskyModule);
                  }
                }
                localDrawable = DetailsFragment2LollipopMR1.access$200(DetailsFragment2LollipopMR1.this);
              } while (localDrawable == null);
              int[] arrayOfInt = new int[2];
              arrayOfInt[0] = localDrawable.getAlpha();
              arrayOfInt[1] = 255;
              ObjectAnimator.ofInt(localDrawable, "alpha", arrayOfInt).setDuration(400L).start();
            }
            
            public final void onTransitionStart(Transition paramAnonymousTransition)
            {
              if (DetailsFragment2LollipopMR1.this.mView == null) {}
              do
              {
                return;
                if ((DetailsFragment2LollipopMR1.this.mOriginalThumbnailBitmap != null) && (!TextUtils.isEmpty(DetailsFragment2LollipopMR1.this.mRevealTransitionSharedName))) {
                  DetailsFragment2LollipopMR1.access$100(DetailsFragment2LollipopMR1.this, DetailsFragment2LollipopMR1.this.mOriginalThumbnailBitmap);
                }
                Drawable localDrawable = DetailsFragment2LollipopMR1.access$200(DetailsFragment2LollipopMR1.this);
                if (localDrawable != null) {
                  localDrawable.setAlpha(0);
                }
                if (DetailsFragment2LollipopMR1.this.mHeroGraphicView != null) {
                  DetailsFragment2LollipopMR1.this.mHeroGraphicView.freezeCorpusFill$1349ef();
                }
                Iterator localIterator = DetailsFragment2LollipopMR1.access$300(DetailsFragment2LollipopMR1.this).iterator();
                while (localIterator.hasNext()) {
                  ((View)localIterator.next()).setAlpha(0.0F);
                }
              } while ((DetailsFragment2LollipopMR1.this.mHeroGraphicView == null) || (TextUtils.isEmpty(DetailsFragment2LollipopMR1.this.mRevealTransitionSharedName)));
              int i = DetailsFragment2LollipopMR1.this.mHeroGraphicView.getMeasuredWidth();
              int j = DetailsFragment2LollipopMR1.this.mHeroGraphicView.getMeasuredHeight();
              DetailsFragment2LollipopMR1.this.mHeaderListLayout.setClipChildren(false);
              int k = DetailsFragment2LollipopMR1.this.mSourceLeft + DetailsFragment2LollipopMR1.this.mSourceWidth / 2 - i / 2;
              int m = DetailsFragment2LollipopMR1.this.mSourceTop - (j - DetailsFragment2LollipopMR1.this.mSourceHeight) / 2;
              DetailsFragment2LollipopMR1.this.mHeroGraphicView.setTranslationX(k);
              DetailsFragment2LollipopMR1.this.mHeroGraphicView.setTranslationY(m);
              AnimatorSet localAnimatorSet = new AnimatorSet();
              localAnimatorSet.setInterpolator(DetailsFragment2LollipopMR1.this.mRevealTransitionInterpolator);
              Animator[] arrayOfAnimator = new Animator[2];
              HeroGraphicView localHeroGraphicView1 = DetailsFragment2LollipopMR1.this.mHeroGraphicView;
              float[] arrayOfFloat1 = new float[2];
              arrayOfFloat1[0] = k;
              arrayOfFloat1[1] = 0.0F;
              arrayOfAnimator[0] = ObjectAnimator.ofFloat(localHeroGraphicView1, "translationX", arrayOfFloat1);
              HeroGraphicView localHeroGraphicView2 = DetailsFragment2LollipopMR1.this.mHeroGraphicView;
              float[] arrayOfFloat2 = new float[2];
              arrayOfFloat2[0] = m;
              arrayOfFloat2[1] = 0.0F;
              arrayOfAnimator[1] = ObjectAnimator.ofFloat(localHeroGraphicView2, "translationY", arrayOfFloat2);
              localAnimatorSet.playTogether(arrayOfAnimator);
              localAnimatorSet.addListener(new AnimatorListenerAdapter()
              {
                public final void onAnimationEnd(Animator paramAnonymous2Animator)
                {
                  if (DetailsFragment2LollipopMR1.this.mHeaderListLayout != null) {
                    DetailsFragment2LollipopMR1.this.mHeaderListLayout.setClipChildren(true);
                  }
                }
              });
              localAnimatorSet.setDuration(400L);
              localAnimatorSet.start();
              float f = 0.5F * (float)Math.sqrt(i * i + j * j);
              ViewAnimationUtils.createCircularReveal(DetailsFragment2LollipopMR1.this.mHeroGraphicView, i / 2, j / 2, 0.0F, f).setDuration(400L).start();
            }
          });
          localTransition.setDuration(400L);
          localDetailsFragment2LollipopMR1.mSharedElementEnterTransition = localTransition;
          localDetailsFragment2LollipopMR1.mEnterTransitionCallback = new SharedElementCallback()
          {
            private boolean mIsOpeningTarget;
            
            public final void onSharedElementEnd$70d861b8$61fb9e66()
            {
              this.mIsOpeningTarget = false;
              if ((DetailsFragment2LollipopMR1.this.mThumbnailInSource != null) && (DetailsFragment2LollipopMR1.this.mView != null))
              {
                DetailsFragment2LollipopMR1.this.mThumbnailInSource.unfreezeImage(false);
                DetailsFragment2LollipopMR1.access$1002(DetailsFragment2LollipopMR1.this, null);
              }
            }
            
            public final void onSharedElementStart$70d861b8(List<String> paramAnonymousList, List<View> paramAnonymousList1)
            {
              int i = 0;
              if (DetailsFragment2LollipopMR1.this.mView == null) {}
              for (boolean bool = true;; bool = false)
              {
                this.mIsOpeningTarget = bool;
                if (!paramAnonymousList1.isEmpty()) {
                  break;
                }
                return;
              }
              int j;
              label47:
              int[] arrayOfInt;
              View localView2;
              int k;
              int m;
              label133:
              int n;
              int i1;
              int i2;
              if (this.mIsOpeningTarget)
              {
                j = 0;
                if (j < paramAnonymousList.size())
                {
                  String str = (String)paramAnonymousList.get(j);
                  View localView1 = (View)paramAnonymousList1.get(j);
                  if (str.startsWith("transition_card_details:cover:"))
                  {
                    DetailsFragment2LollipopMR1.access$1002(DetailsFragment2LollipopMR1.this, (FifeImageView)localView1);
                    DetailsFragment2LollipopMR1.this.mThumbnailInSource.setAlpha(0.0F);
                    arrayOfInt = new int[2];
                    localView2 = localView1;
                    k = 0;
                    m = 0;
                    if (localView2 != null)
                    {
                      n = localView2.getId();
                      if ((n != 2131755454) && (n != 2131755794)) {
                        break label465;
                      }
                      localView2.getLocationInWindow(arrayOfInt);
                      i1 = arrayOfInt[0];
                      i2 = arrayOfInt[1];
                      DetailsFragment2LollipopMR1.access$502(DetailsFragment2LollipopMR1.this, localView2.getMeasuredWidth());
                      DetailsFragment2LollipopMR1.access$702(DetailsFragment2LollipopMR1.this, localView2.getMeasuredHeight());
                    }
                  }
                }
              }
              for (;;)
              {
                if (n != 2131755700)
                {
                  localView2 = (View)localView2.getParent();
                  k = i2;
                  m = i1;
                  break label133;
                }
                k = i2;
                m = i1;
                localView2.getLocationInWindow(arrayOfInt);
                DetailsFragment2LollipopMR1.access$402(DetailsFragment2LollipopMR1.this, m - arrayOfInt[0]);
                DetailsFragment2LollipopMR1.access$602(DetailsFragment2LollipopMR1.this, k - arrayOfInt[1]);
                j++;
                break label47;
                break;
                if (i < paramAnonymousList.size()) {
                  if (!((String)paramAnonymousList.get(i)).startsWith("transition_card_details:cover:")) {}
                }
                for (FifeImageView localFifeImageView = (FifeImageView)paramAnonymousList1.get(i);; localFifeImageView = null)
                {
                  if ((DetailsFragment2LollipopMR1.this.mOriginalThumbnailBitmap != null) && (localFifeImageView != null))
                  {
                    localFifeImageView.mIsFrozen = true;
                    localFifeImageView.setImageBitmap(DetailsFragment2LollipopMR1.this.mOriginalThumbnailBitmap);
                    localFifeImageView.measure(View.MeasureSpec.makeMeasureSpec(localFifeImageView.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(localFifeImageView.getMeasuredHeight(), 1073741824));
                    localFifeImageView.layout(localFifeImageView.getLeft(), localFifeImageView.getTop(), localFifeImageView.getRight(), localFifeImageView.getBottom());
                    if (DetailsFragment2LollipopMR1.this.mThumbnailInSource != null)
                    {
                      DetailsFragment2LollipopMR1.this.mThumbnailInSource.setImageBitmap(DetailsFragment2LollipopMR1.this.mOriginalThumbnailBitmap);
                      DetailsFragment2LollipopMR1.this.mThumbnailInSource.mIsFrozen = true;
                    }
                  }
                  DetailsFragment2LollipopMR1.access$002$38af9f2(DetailsFragment2LollipopMR1.this);
                  return;
                  i++;
                  break;
                }
                label465:
                i1 = m;
                i2 = k;
              }
            }
          };
          Drawable localDrawable = ((ImageView)paramView).getDrawable();
          if (localDrawable != null) {
            localDetailsFragment2LollipopMR1.mOriginalThumbnailBitmap = ((BitmapDrawable)localDrawable).getBitmap();
          }
        }
      }
    }
    for (;;)
    {
      localDetailsFragment2LollipopMR1.mIsInImageTransitionPhase = true;
      return localDetailsFragment2LollipopMR1;
      i = 0;
      break;
      label228:
      int j = paramDocument.mDocument.backendId;
      localDetailsFragment2LollipopMR1.mRevealTransitionInterpolator = AnimationUtils.loadInterpolator(localFinskyApp, 17563661);
      CircleTransition localCircleTransition = new CircleTransition(CorpusResourceUtils.getPrimaryColor(localFinskyApp, j));
      localCircleTransition.setInterpolator(localDetailsFragment2LollipopMR1.mRevealTransitionInterpolator);
      localCircleTransition.setPathMotion(new ArcMotion());
      localCircleTransition.mCircleSizeCap = localFinskyApp.getResources().getDimensionPixelSize(2131493258);
      localCircleTransition.addListener(new BaseTransitionListener()
      {
        public final void onTransitionEnd(Transition paramAnonymousTransition)
        {
          if (((ViewGroup)DetailsFragment2LollipopMR1.this.mView == null) || (DetailsFragment2LollipopMR1.this.mModulesAdapter == null)) {}
          Drawable localDrawable;
          do
          {
            return;
            DetailsFragment2LollipopMR1.access$900(DetailsFragment2LollipopMR1.this);
            if (DetailsFragment2LollipopMR1.this.mHeroGraphicView != null) {
              DetailsFragment2LollipopMR1.this.mHeroGraphicView.unfreezeCorpusFill$1349ef();
            }
            AnimatorSet localAnimatorSet = new AnimatorSet();
            ArrayList localArrayList = new ArrayList();
            Iterator localIterator = DetailsFragment2LollipopMR1.access$300(DetailsFragment2LollipopMR1.this).iterator();
            while (localIterator.hasNext())
            {
              View localView = (View)localIterator.next();
              localArrayList.add(PlayAnimationUtils.getFadeAnimator$57852d9d(localView, localView.getAlpha(), 400L));
            }
            localAnimatorSet.playTogether(localArrayList);
            localAnimatorSet.start();
            DetailsFragment2LollipopMR1.this.mIsInImageTransitionPhase = false;
            for (int i = 0; i < DetailsFragment2LollipopMR1.this.mAllModules.size(); i++)
            {
              FinskyModule localFinskyModule = (FinskyModule)DetailsFragment2LollipopMR1.this.mAllModules.get(i);
              if ((DetailsFragment2LollipopMR1.this.isModuleReadyForDisplay(localFinskyModule)) && (!DetailsFragment2LollipopMR1.this.mAddedModules.contains(localFinskyModule))) {
                DetailsFragment2LollipopMR1.this.addModule(localFinskyModule);
              }
            }
            localDrawable = DetailsFragment2LollipopMR1.access$200(DetailsFragment2LollipopMR1.this);
          } while (localDrawable == null);
          int[] arrayOfInt = new int[2];
          arrayOfInt[0] = localDrawable.getAlpha();
          arrayOfInt[1] = 255;
          ObjectAnimator.ofInt(localDrawable, "alpha", arrayOfInt).setDuration(400L).start();
        }
        
        public final void onTransitionStart(Transition paramAnonymousTransition)
        {
          if (DetailsFragment2LollipopMR1.this.mView == null) {}
          for (;;)
          {
            return;
            if ((DetailsFragment2LollipopMR1.this.mOriginalThumbnailBitmap != null) && (!TextUtils.isEmpty(DetailsFragment2LollipopMR1.this.mRevealTransitionSharedName))) {
              DetailsFragment2LollipopMR1.access$100(DetailsFragment2LollipopMR1.this, DetailsFragment2LollipopMR1.this.mOriginalThumbnailBitmap);
            }
            Drawable localDrawable = DetailsFragment2LollipopMR1.access$200(DetailsFragment2LollipopMR1.this);
            if (localDrawable != null) {
              localDrawable.setAlpha(0);
            }
            if (DetailsFragment2LollipopMR1.this.mHeroGraphicView != null) {
              DetailsFragment2LollipopMR1.this.mHeroGraphicView.freezeCorpusFill$1349ef();
            }
            Iterator localIterator = DetailsFragment2LollipopMR1.access$300(DetailsFragment2LollipopMR1.this).iterator();
            while (localIterator.hasNext()) {
              ((View)localIterator.next()).setAlpha(0.0F);
            }
          }
        }
      });
      localCircleTransition.setDuration(400L);
      localDetailsFragment2LollipopMR1.mSharedElementEnterTransition = localCircleTransition;
      localDetailsFragment2LollipopMR1.mEnterTransitionCallback = new SharedElementCallback()
      {
        public final void onSharedElementEnd$70d861b8$61fb9e66() {}
        
        public final void onSharedElementStart$70d861b8(List<String> paramAnonymousList, List<View> paramAnonymousList1)
        {
          if (DetailsFragment2LollipopMR1.this.mView == null)
          {
            Iterator localIterator = paramAnonymousList1.iterator();
            while (localIterator.hasNext()) {
              ((View)localIterator.next()).setAlpha(0.0F);
            }
          }
        }
      };
      localDetailsFragment2LollipopMR1.mExitTransitionCallback = new SharedElementCallback() {};
    }
  }
  
  protected final boolean isModuleReadyForDisplay(FinskyModule paramFinskyModule)
  {
    if ((this.mIsInImageTransitionPhase) && (!(paramFinskyModule instanceof DisplayDuringTransition))) {
      return false;
    }
    return paramFinskyModule.readyForDisplay();
  }
  
  protected final boolean isRunningSharedCoverTransition()
  {
    return (this.mRevealTransitionSharedName != null) && (this.mRevealTransitionSharedName.startsWith("transition_card_details:cover:"));
  }
  
  public final boolean onBackPressed()
  {
    if (!TextUtils.isEmpty(this.mRevealTransitionSharedName))
    {
      TransitionSet localTransitionSet = new TransitionSet();
      Fade localFade1;
      ReverseContentTransition localReverseContentTransition;
      ArrayList localArrayList;
      int j;
      label135:
      View localView;
      if ((this.mHeroGraphicView != null) && (this.mHeroGraphicView.getTransitionName() == null))
      {
        if (this.mUseWideLayout)
        {
          Fade localFade3 = new Fade();
          localFade3.setInterpolator(this.mRevealTransitionInterpolator);
          localFade3.addTarget(this.mHeroGraphicView);
          localFade3.setDuration(400L);
          localTransitionSet.addTransition(localFade3);
        }
      }
      else
      {
        localFade1 = new Fade();
        localReverseContentTransition = new ReverseContentTransition();
        localReverseContentTransition.setInterpolator(this.mRevealTransitionInterpolator);
        localArrayList = new ArrayList();
        int i = this.mAddedModules.size();
        j = 0;
        if (j >= i) {
          break label260;
        }
        ModulesAdapter.Module localModule = (ModulesAdapter.Module)this.mAddedModules.get(j);
        if (localModule.mModuleViewHolder != null)
        {
          localView = localModule.mModuleViewHolder.itemView;
          if (!(localView instanceof DetailsPartialFadeSection)) {
            break label250;
          }
          ((DetailsPartialFadeSection)localView).addParticipatingChildViewIds(localArrayList);
        }
      }
      for (;;)
      {
        j++;
        break label135;
        ReverseHeroTransition localReverseHeroTransition = new ReverseHeroTransition();
        localReverseHeroTransition.setInterpolator(this.mRevealTransitionInterpolator);
        localReverseHeroTransition.addTarget(this.mHeroGraphicView);
        localReverseHeroTransition.setDuration(600L);
        localTransitionSet.addTransition(localReverseHeroTransition);
        break;
        label250:
        localReverseContentTransition.addTarget(localView);
      }
      label260:
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext()) {
        localFade1.addTarget(((Integer)localIterator.next()).intValue());
      }
      localFade1.setDuration(133L);
      localReverseContentTransition.setDuration(600L);
      Fade localFade2 = new Fade();
      localFade2.addTarget(2131755334);
      localFade2.setDuration(133L);
      localTransitionSet.addTransition(localFade2);
      localTransitionSet.addTransition(localFade1);
      localTransitionSet.addTransition(localReverseContentTransition);
      this.mEnterTransition = localTransitionSet;
    }
    return super.onBackPressed();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    if ((this.mBackgroundView != null) && (!TextUtils.isEmpty(this.mRevealTransitionSharedName)) && (this.mRevealTransitionSharedName.startsWith("transition_generic_circle:"))) {
      this.mBackgroundView.setTransitionName(this.mRevealTransitionSharedName);
    }
    return localView;
  }
  
  public final void onDestroyView()
  {
    this.mOriginalThumbnailBitmap = null;
    this.mThumbnailInSource = null;
    this.mSharedElementEnterTransition = null;
    super.onDestroyView();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.DetailsFragment2LollipopMR1
 * JD-Core Version:    0.7.0.1
 */