package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.LruCache;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.drawable;
import android.util.Log;
import android.util.SparseArray;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class TintManager
{
  private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
  private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
  private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
  private static final ColorFilterLruCache COLOR_FILTER_CACHE;
  private static final PorterDuff.Mode DEFAULT_MODE;
  private static final WeakHashMap<Context, TintManager> INSTANCE_CACHE;
  public static final boolean SHOULD_BE_USED;
  private static final int[] TINT_CHECKABLE_BUTTON_LIST;
  private static final int[] TINT_COLOR_CONTROL_NORMAL;
  private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
  private final WeakReference<Context> mContextRef;
  private ColorStateList mDefaultColorStateList;
  private SparseArray<ColorStateList> mTintLists;
  
  static
  {
    if (Build.VERSION.SDK_INT < 21) {}
    for (boolean bool = true;; bool = false)
    {
      SHOULD_BE_USED = bool;
      DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
      INSTANCE_CACHE = new WeakHashMap();
      COLOR_FILTER_CACHE = new ColorFilterLruCache();
      int[] arrayOfInt1 = new int[3];
      arrayOfInt1[0] = R.drawable.abc_textfield_search_default_mtrl_alpha;
      arrayOfInt1[1] = R.drawable.abc_textfield_default_mtrl_alpha;
      arrayOfInt1[2] = R.drawable.abc_ab_share_pack_mtrl_alpha;
      COLORFILTER_TINT_COLOR_CONTROL_NORMAL = arrayOfInt1;
      int[] arrayOfInt2 = new int[12];
      arrayOfInt2[0] = R.drawable.abc_ic_ab_back_mtrl_am_alpha;
      arrayOfInt2[1] = R.drawable.abc_ic_go_search_api_mtrl_alpha;
      arrayOfInt2[2] = R.drawable.abc_ic_search_api_mtrl_alpha;
      arrayOfInt2[3] = R.drawable.abc_ic_commit_search_api_mtrl_alpha;
      arrayOfInt2[4] = R.drawable.abc_ic_clear_mtrl_alpha;
      arrayOfInt2[5] = R.drawable.abc_ic_menu_share_mtrl_alpha;
      arrayOfInt2[6] = R.drawable.abc_ic_menu_copy_mtrl_am_alpha;
      arrayOfInt2[7] = R.drawable.abc_ic_menu_cut_mtrl_alpha;
      arrayOfInt2[8] = R.drawable.abc_ic_menu_selectall_mtrl_alpha;
      arrayOfInt2[9] = R.drawable.abc_ic_menu_paste_mtrl_am_alpha;
      arrayOfInt2[10] = R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha;
      arrayOfInt2[11] = R.drawable.abc_ic_voice_search_api_mtrl_alpha;
      TINT_COLOR_CONTROL_NORMAL = arrayOfInt2;
      int[] arrayOfInt3 = new int[4];
      arrayOfInt3[0] = R.drawable.abc_textfield_activated_mtrl_alpha;
      arrayOfInt3[1] = R.drawable.abc_textfield_search_activated_mtrl_alpha;
      arrayOfInt3[2] = R.drawable.abc_cab_background_top_mtrl_alpha;
      arrayOfInt3[3] = R.drawable.abc_text_cursor_material;
      COLORFILTER_COLOR_CONTROL_ACTIVATED = arrayOfInt3;
      int[] arrayOfInt4 = new int[3];
      arrayOfInt4[0] = R.drawable.abc_popup_background_mtrl_mult;
      arrayOfInt4[1] = R.drawable.abc_cab_background_internal_bg;
      arrayOfInt4[2] = R.drawable.abc_menu_hardkey_panel_mtrl_mult;
      COLORFILTER_COLOR_BACKGROUND_MULTIPLY = arrayOfInt4;
      int[] arrayOfInt5 = new int[10];
      arrayOfInt5[0] = R.drawable.abc_edit_text_material;
      arrayOfInt5[1] = R.drawable.abc_tab_indicator_material;
      arrayOfInt5[2] = R.drawable.abc_textfield_search_material;
      arrayOfInt5[3] = R.drawable.abc_spinner_mtrl_am_alpha;
      arrayOfInt5[4] = R.drawable.abc_spinner_textfield_background_material;
      arrayOfInt5[5] = R.drawable.abc_ratingbar_full_material;
      arrayOfInt5[6] = R.drawable.abc_switch_track_mtrl_alpha;
      arrayOfInt5[7] = R.drawable.abc_switch_thumb_material;
      arrayOfInt5[8] = R.drawable.abc_btn_default_mtrl_shape;
      arrayOfInt5[9] = R.drawable.abc_btn_borderless_material;
      TINT_COLOR_CONTROL_STATE_LIST = arrayOfInt5;
      int[] arrayOfInt6 = new int[2];
      arrayOfInt6[0] = R.drawable.abc_btn_check_material;
      arrayOfInt6[1] = R.drawable.abc_btn_radio_material;
      TINT_CHECKABLE_BUTTON_LIST = arrayOfInt6;
      return;
    }
  }
  
  private TintManager(Context paramContext)
  {
    this.mContextRef = new WeakReference(paramContext);
  }
  
  private static boolean arrayContains(int[] paramArrayOfInt, int paramInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++) {
      if (paramArrayOfInt[j] == paramInt) {
        return true;
      }
    }
    return false;
  }
  
  private static ColorStateList createButtonColorStateList(Context paramContext, int paramInt)
  {
    int[][] arrayOfInt = new int[4][];
    int[] arrayOfInt1 = new int[4];
    int i = ThemeUtils.getThemeAttrColor(paramContext, paramInt);
    int j = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlHighlight);
    arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
    arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorButtonNormal);
    arrayOfInt[1] = ThemeUtils.PRESSED_STATE_SET;
    arrayOfInt1[1] = ColorUtils.compositeColors(j, i);
    arrayOfInt[2] = ThemeUtils.FOCUSED_STATE_SET;
    arrayOfInt1[2] = ColorUtils.compositeColors(j, i);
    arrayOfInt[3] = ThemeUtils.EMPTY_STATE_SET;
    arrayOfInt1[3] = i;
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  public static TintManager get(Context paramContext)
  {
    TintManager localTintManager = (TintManager)INSTANCE_CACHE.get(paramContext);
    if (localTintManager == null)
    {
      localTintManager = new TintManager(paramContext);
      INSTANCE_CACHE.put(paramContext, localTintManager);
    }
    return localTintManager;
  }
  
  public static Drawable getDrawable(Context paramContext, int paramInt)
  {
    if ((arrayContains(TINT_COLOR_CONTROL_NORMAL, paramInt)) || (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, paramInt)) || (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, paramInt)) || (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, paramInt)) || (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, paramInt)) || (arrayContains(TINT_CHECKABLE_BUTTON_LIST, paramInt)) || (paramInt == R.drawable.abc_cab_background_top_material)) {}
    for (int i = 1; i != 0; i = 0) {
      return get(paramContext).getDrawable(paramInt, false);
    }
    return ContextCompat.getDrawable(paramContext, paramInt);
  }
  
  private static PorterDuffColorFilter getPorterDuffColorFilter(int paramInt, PorterDuff.Mode paramMode)
  {
    PorterDuffColorFilter localPorterDuffColorFilter = (PorterDuffColorFilter)COLOR_FILTER_CACHE.get(Integer.valueOf(ColorFilterLruCache.generateCacheKey(paramInt, paramMode)));
    if (localPorterDuffColorFilter == null)
    {
      localPorterDuffColorFilter = new PorterDuffColorFilter(paramInt, paramMode);
      COLOR_FILTER_CACHE.put(Integer.valueOf(ColorFilterLruCache.generateCacheKey(paramInt, paramMode)), localPorterDuffColorFilter);
    }
    return localPorterDuffColorFilter;
  }
  
  private static void setPorterDuffColorFilter(Drawable paramDrawable, int paramInt, PorterDuff.Mode paramMode)
  {
    if (paramMode == null) {
      paramMode = DEFAULT_MODE;
    }
    paramDrawable.setColorFilter(getPorterDuffColorFilter(paramInt, paramMode));
  }
  
  private static boolean shouldMutateBackground(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (;;)
    {
      return true;
      if ((paramDrawable instanceof LayerDrawable))
      {
        if (Build.VERSION.SDK_INT < 16) {
          return false;
        }
      }
      else if ((paramDrawable instanceof InsetDrawable))
      {
        if (Build.VERSION.SDK_INT < 14) {
          return false;
        }
      }
      else if ((paramDrawable instanceof DrawableContainer))
      {
        Drawable.ConstantState localConstantState = paramDrawable.getConstantState();
        if ((localConstantState instanceof DrawableContainer.DrawableContainerState))
        {
          Drawable[] arrayOfDrawable = ((DrawableContainer.DrawableContainerState)localConstantState).getChildren();
          int i = arrayOfDrawable.length;
          for (int j = 0; j < i; j++) {
            if (!shouldMutateBackground(arrayOfDrawable[j])) {
              return false;
            }
          }
        }
      }
    }
  }
  
  public static void tintDrawable(Drawable paramDrawable, TintInfo paramTintInfo, int[] paramArrayOfInt)
  {
    if ((shouldMutateBackground(paramDrawable)) && (paramDrawable.mutate() != paramDrawable)) {
      Log.d("TintManager", "Mutated drawable is not the same instance as the input.");
    }
    label133:
    for (;;)
    {
      return;
      ColorStateList localColorStateList;
      PorterDuff.Mode localMode;
      label65:
      Object localObject;
      if ((paramTintInfo.mHasTintList) || (paramTintInfo.mHasTintMode)) {
        if (paramTintInfo.mHasTintList)
        {
          localColorStateList = paramTintInfo.mTintList;
          if (!paramTintInfo.mHasTintMode) {
            break label104;
          }
          localMode = paramTintInfo.mTintMode;
          localObject = null;
          if (localColorStateList != null)
          {
            localObject = null;
            if (localMode != null) {
              break label112;
            }
          }
          label80:
          paramDrawable.setColorFilter((ColorFilter)localObject);
        }
      }
      for (;;)
      {
        if (Build.VERSION.SDK_INT > 10) {
          break label133;
        }
        paramDrawable.invalidateSelf();
        return;
        localColorStateList = null;
        break;
        label104:
        localMode = DEFAULT_MODE;
        break label65;
        label112:
        localObject = getPorterDuffColorFilter(localColorStateList.getColorForState(paramArrayOfInt, 0), localMode);
        break label80;
        paramDrawable.clearColorFilter();
      }
    }
  }
  
  public final Drawable getDrawable(int paramInt, boolean paramBoolean)
  {
    Context localContext = (Context)this.mContextRef.get();
    Drawable localDrawable;
    if (localContext == null) {
      localDrawable = null;
    }
    label244:
    label248:
    for (;;)
    {
      return localDrawable;
      localDrawable = ContextCompat.getDrawable(localContext, paramInt);
      if (localDrawable != null)
      {
        if (Build.VERSION.SDK_INT >= 8) {
          localDrawable = localDrawable.mutate();
        }
        ColorStateList localColorStateList = getTintList(paramInt);
        if (localColorStateList != null)
        {
          localDrawable = DrawableCompat.wrap(localDrawable);
          DrawableCompat.setTintList(localDrawable, localColorStateList);
          if (paramInt != R.drawable.abc_switch_thumb_material) {
            break label244;
          }
        }
        for (PorterDuff.Mode localMode = PorterDuff.Mode.MULTIPLY;; localMode = null)
        {
          if (localMode == null) {
            break label248;
          }
          DrawableCompat.setTintMode(localDrawable, localMode);
          return localDrawable;
          if (paramInt == R.drawable.abc_cab_background_top_material)
          {
            Drawable[] arrayOfDrawable = new Drawable[2];
            arrayOfDrawable[0] = getDrawable(R.drawable.abc_cab_background_internal_bg, false);
            arrayOfDrawable[1] = getDrawable(R.drawable.abc_cab_background_top_mtrl_alpha, false);
            return new LayerDrawable(arrayOfDrawable);
          }
          if (paramInt == R.drawable.abc_seekbar_track_material)
          {
            LayerDrawable localLayerDrawable = (LayerDrawable)localDrawable;
            setPorterDuffColorFilter(localLayerDrawable.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(localLayerDrawable.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(localLayerDrawable.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated), DEFAULT_MODE);
            return localDrawable;
          }
          if ((tintDrawableUsingColorFilter(paramInt, localDrawable)) || (!paramBoolean)) {
            break;
          }
          return null;
        }
      }
    }
  }
  
  public final ColorStateList getTintList(int paramInt)
  {
    Context localContext = (Context)this.mContextRef.get();
    ColorStateList localColorStateList1;
    if (localContext == null) {
      localColorStateList1 = null;
    }
    label911:
    for (;;)
    {
      return localColorStateList1;
      if (this.mTintLists != null)
      {
        localColorStateList1 = (ColorStateList)this.mTintLists.get(paramInt);
        label38:
        if (localColorStateList1 != null) {
          break label162;
        }
        if (paramInt != R.drawable.abc_edit_text_material) {
          break label164;
        }
        int[][] arrayOfInt13 = new int[3][];
        int[] arrayOfInt14 = new int[3];
        arrayOfInt13[0] = ThemeUtils.DISABLED_STATE_SET;
        arrayOfInt14[0] = ThemeUtils.getDisabledThemeAttrColor(localContext, R.attr.colorControlNormal);
        arrayOfInt13[1] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        arrayOfInt14[1] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlNormal);
        arrayOfInt13[2] = ThemeUtils.EMPTY_STATE_SET;
        arrayOfInt14[2] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated);
        localColorStateList1 = new ColorStateList(arrayOfInt13, arrayOfInt14);
      }
      for (;;)
      {
        if (localColorStateList1 == null) {
          break label911;
        }
        if (this.mTintLists == null) {
          this.mTintLists = new SparseArray();
        }
        this.mTintLists.append(paramInt, localColorStateList1);
        return localColorStateList1;
        localColorStateList1 = null;
        break label38;
        label162:
        break;
        label164:
        if (paramInt == R.drawable.abc_switch_track_mtrl_alpha)
        {
          int[][] arrayOfInt11 = new int[3][];
          int[] arrayOfInt12 = new int[3];
          arrayOfInt11[0] = ThemeUtils.DISABLED_STATE_SET;
          arrayOfInt12[0] = ThemeUtils.getThemeAttrColor(localContext, 16842800, 0.1F);
          arrayOfInt11[1] = ThemeUtils.CHECKED_STATE_SET;
          arrayOfInt12[1] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated, 0.3F);
          arrayOfInt11[2] = ThemeUtils.EMPTY_STATE_SET;
          arrayOfInt12[2] = ThemeUtils.getThemeAttrColor(localContext, 16842800, 0.3F);
          localColorStateList1 = new ColorStateList(arrayOfInt11, arrayOfInt12);
        }
        else
        {
          if (paramInt == R.drawable.abc_switch_thumb_material)
          {
            int[][] arrayOfInt9 = new int[3][];
            int[] arrayOfInt10 = new int[3];
            ColorStateList localColorStateList2 = ThemeUtils.getThemeAttrColorStateList(localContext, R.attr.colorSwitchThumbNormal);
            if ((localColorStateList2 != null) && (localColorStateList2.isStateful()))
            {
              arrayOfInt9[0] = ThemeUtils.DISABLED_STATE_SET;
              arrayOfInt10[0] = localColorStateList2.getColorForState(arrayOfInt9[0], 0);
              arrayOfInt9[1] = ThemeUtils.CHECKED_STATE_SET;
              arrayOfInt10[1] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated);
              arrayOfInt9[2] = ThemeUtils.EMPTY_STATE_SET;
              arrayOfInt10[2] = localColorStateList2.getDefaultColor();
            }
            for (;;)
            {
              localColorStateList1 = new ColorStateList(arrayOfInt9, arrayOfInt10);
              break;
              arrayOfInt9[0] = ThemeUtils.DISABLED_STATE_SET;
              arrayOfInt10[0] = ThemeUtils.getDisabledThemeAttrColor(localContext, R.attr.colorSwitchThumbNormal);
              arrayOfInt9[1] = ThemeUtils.CHECKED_STATE_SET;
              arrayOfInt10[1] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated);
              arrayOfInt9[2] = ThemeUtils.EMPTY_STATE_SET;
              arrayOfInt10[2] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorSwitchThumbNormal);
            }
          }
          if ((paramInt == R.drawable.abc_btn_default_mtrl_shape) || (paramInt == R.drawable.abc_btn_borderless_material))
          {
            localColorStateList1 = createButtonColorStateList(localContext, R.attr.colorButtonNormal);
          }
          else if (paramInt == R.drawable.abc_btn_colored_material)
          {
            localColorStateList1 = createButtonColorStateList(localContext, R.attr.colorAccent);
          }
          else if ((paramInt == R.drawable.abc_spinner_mtrl_am_alpha) || (paramInt == R.drawable.abc_spinner_textfield_background_material))
          {
            int[][] arrayOfInt1 = new int[3][];
            int[] arrayOfInt2 = new int[3];
            arrayOfInt1[0] = ThemeUtils.DISABLED_STATE_SET;
            arrayOfInt2[0] = ThemeUtils.getDisabledThemeAttrColor(localContext, R.attr.colorControlNormal);
            arrayOfInt1[1] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
            arrayOfInt2[1] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlNormal);
            arrayOfInt1[2] = ThemeUtils.EMPTY_STATE_SET;
            arrayOfInt2[2] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated);
            localColorStateList1 = new ColorStateList(arrayOfInt1, arrayOfInt2);
          }
          else if (arrayContains(TINT_COLOR_CONTROL_NORMAL, paramInt))
          {
            localColorStateList1 = ThemeUtils.getThemeAttrColorStateList(localContext, R.attr.colorControlNormal);
          }
          else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, paramInt))
          {
            if (this.mDefaultColorStateList == null)
            {
              int i = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlNormal);
              int j = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated);
              int[][] arrayOfInt7 = new int[7][];
              int[] arrayOfInt8 = new int[7];
              arrayOfInt7[0] = ThemeUtils.DISABLED_STATE_SET;
              arrayOfInt8[0] = ThemeUtils.getDisabledThemeAttrColor(localContext, R.attr.colorControlNormal);
              arrayOfInt7[1] = ThemeUtils.FOCUSED_STATE_SET;
              arrayOfInt8[1] = j;
              arrayOfInt7[2] = ThemeUtils.ACTIVATED_STATE_SET;
              arrayOfInt8[2] = j;
              arrayOfInt7[3] = ThemeUtils.PRESSED_STATE_SET;
              arrayOfInt8[3] = j;
              arrayOfInt7[4] = ThemeUtils.CHECKED_STATE_SET;
              arrayOfInt8[4] = j;
              arrayOfInt7[5] = ThemeUtils.SELECTED_STATE_SET;
              arrayOfInt8[5] = j;
              arrayOfInt7[6] = ThemeUtils.EMPTY_STATE_SET;
              arrayOfInt8[6] = i;
              this.mDefaultColorStateList = new ColorStateList(arrayOfInt7, arrayOfInt8);
            }
            localColorStateList1 = this.mDefaultColorStateList;
          }
          else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, paramInt))
          {
            int[][] arrayOfInt5 = new int[3][];
            int[] arrayOfInt6 = new int[3];
            arrayOfInt5[0] = ThemeUtils.DISABLED_STATE_SET;
            arrayOfInt6[0] = ThemeUtils.getDisabledThemeAttrColor(localContext, R.attr.colorControlNormal);
            arrayOfInt5[1] = ThemeUtils.CHECKED_STATE_SET;
            arrayOfInt6[1] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated);
            arrayOfInt5[2] = ThemeUtils.EMPTY_STATE_SET;
            arrayOfInt6[2] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlNormal);
            localColorStateList1 = new ColorStateList(arrayOfInt5, arrayOfInt6);
          }
          else if (paramInt == R.drawable.abc_seekbar_thumb_material)
          {
            int[][] arrayOfInt3 = new int[2][];
            int[] arrayOfInt4 = new int[2];
            arrayOfInt3[0] = ThemeUtils.DISABLED_STATE_SET;
            arrayOfInt4[0] = ThemeUtils.getDisabledThemeAttrColor(localContext, R.attr.colorControlActivated);
            arrayOfInt3[1] = ThemeUtils.EMPTY_STATE_SET;
            arrayOfInt4[1] = ThemeUtils.getThemeAttrColor(localContext, R.attr.colorControlActivated);
            localColorStateList1 = new ColorStateList(arrayOfInt3, arrayOfInt4);
          }
        }
      }
    }
  }
  
  public final boolean tintDrawableUsingColorFilter(int paramInt, Drawable paramDrawable)
  {
    Context localContext = (Context)this.mContextRef.get();
    if (localContext == null) {}
    for (;;)
    {
      return false;
      PorterDuff.Mode localMode = DEFAULT_MODE;
      int i = -1;
      int k;
      int m;
      if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, paramInt))
      {
        k = R.attr.colorControlNormal;
        m = 1;
      }
      while (m != 0)
      {
        paramDrawable.setColorFilter(getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(localContext, k), localMode));
        if (i != -1) {
          paramDrawable.setAlpha(i);
        }
        return true;
        if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, paramInt))
        {
          k = R.attr.colorControlActivated;
          m = 1;
        }
        else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, paramInt))
        {
          k = 16842801;
          m = 1;
          localMode = PorterDuff.Mode.MULTIPLY;
        }
        else
        {
          int j = R.drawable.abc_list_divider_mtrl_alpha;
          k = 0;
          m = 0;
          if (paramInt == j)
          {
            k = 16842800;
            m = 1;
            i = Math.round(40.799999F);
          }
        }
      }
    }
  }
  
  private static final class ColorFilterLruCache
    extends LruCache<Integer, PorterDuffColorFilter>
  {
    public ColorFilterLruCache()
    {
      super();
    }
    
    static int generateCacheKey(int paramInt, PorterDuff.Mode paramMode)
    {
      return 31 * (paramInt + 31) + paramMode.hashCode();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.TintManager
 * JD-Core Version:    0.7.0.1
 */