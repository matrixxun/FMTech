package android.support.v7.internal.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.styleable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;

public class ListMenuItemView
  extends LinearLayout
  implements MenuView.ItemView
{
  private Drawable mBackground;
  private CheckBox mCheckBox;
  private Context mContext;
  private boolean mForceShowIcon;
  private ImageView mIconView;
  private LayoutInflater mInflater;
  private MenuItemImpl mItemData;
  private int mMenuType;
  private boolean mPreserveIconSpacing;
  private RadioButton mRadioButton;
  private TextView mShortcutView;
  private int mTextAppearance;
  private Context mTextAppearanceContext;
  private TextView mTitleView;
  
  public ListMenuItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ListMenuItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MenuView, paramInt, 0);
    this.mBackground = localTypedArray.getDrawable(R.styleable.MenuView_android_itemBackground);
    this.mTextAppearance = localTypedArray.getResourceId(R.styleable.MenuView_android_itemTextAppearance, -1);
    this.mPreserveIconSpacing = localTypedArray.getBoolean(R.styleable.MenuView_preserveIconSpacing, false);
    this.mTextAppearanceContext = paramContext;
    localTypedArray.recycle();
  }
  
  private LayoutInflater getInflater()
  {
    if (this.mInflater == null) {
      this.mInflater = LayoutInflater.from(this.mContext);
    }
    return this.mInflater;
  }
  
  private void insertCheckBox()
  {
    this.mCheckBox = ((CheckBox)getInflater().inflate(R.layout.abc_list_menu_item_checkbox, this, false));
    addView(this.mCheckBox);
  }
  
  private void insertRadioButton()
  {
    this.mRadioButton = ((RadioButton)getInflater().inflate(R.layout.abc_list_menu_item_radio, this, false));
    addView(this.mRadioButton);
  }
  
  private void setShortcut$25d965e(boolean paramBoolean)
  {
    if ((paramBoolean) && (this.mItemData.shouldShowShortcut())) {}
    char c;
    String str;
    for (int i = 0;; i = 8)
    {
      if (i == 0)
      {
        TextView localTextView = this.mShortcutView;
        c = this.mItemData.getShortcut();
        if (c != 0) {
          break;
        }
        str = "";
        localTextView.setText(str);
      }
      if (this.mShortcutView.getVisibility() != i) {
        this.mShortcutView.setVisibility(i);
      }
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder(MenuItemImpl.sPrependShortcutLabel);
    switch (c)
    {
    default: 
      localStringBuilder.append(c);
    }
    for (;;)
    {
      str = localStringBuilder.toString();
      break;
      localStringBuilder.append(MenuItemImpl.sEnterShortcutLabel);
      continue;
      localStringBuilder.append(MenuItemImpl.sDeleteShortcutLabel);
      continue;
      localStringBuilder.append(MenuItemImpl.sSpaceShortcutLabel);
    }
  }
  
  public MenuItemImpl getItemData()
  {
    return this.mItemData;
  }
  
  public final void initialize$6b732f7b(MenuItemImpl paramMenuItemImpl)
  {
    this.mItemData = paramMenuItemImpl;
    this.mMenuType = 0;
    int i;
    int j;
    if (paramMenuItemImpl.isVisible())
    {
      i = 0;
      setVisibility(i);
      setTitle(paramMenuItemImpl.getTitleForItemView(this));
      setCheckable(paramMenuItemImpl.isCheckable());
      boolean bool1 = paramMenuItemImpl.shouldShowShortcut();
      paramMenuItemImpl.getShortcut();
      if (!bool1) {
        break label152;
      }
      boolean bool2 = this.mItemData.shouldShowShortcut();
      j = 0;
      if (!bool2) {
        break label152;
      }
    }
    char c;
    String str;
    for (;;)
    {
      if (j == 0)
      {
        TextView localTextView = this.mShortcutView;
        c = this.mItemData.getShortcut();
        if (c != 0) {
          break label159;
        }
        str = "";
        localTextView.setText(str);
      }
      if (this.mShortcutView.getVisibility() != j) {
        this.mShortcutView.setVisibility(j);
      }
      setIcon(paramMenuItemImpl.getIcon());
      setEnabled(paramMenuItemImpl.isEnabled());
      return;
      i = 8;
      break;
      label152:
      j = 8;
    }
    label159:
    StringBuilder localStringBuilder = new StringBuilder(MenuItemImpl.sPrependShortcutLabel);
    switch (c)
    {
    default: 
      localStringBuilder.append(c);
    }
    for (;;)
    {
      str = localStringBuilder.toString();
      break;
      localStringBuilder.append(MenuItemImpl.sEnterShortcutLabel);
      continue;
      localStringBuilder.append(MenuItemImpl.sDeleteShortcutLabel);
      continue;
      localStringBuilder.append(MenuItemImpl.sSpaceShortcutLabel);
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    setBackgroundDrawable(this.mBackground);
    this.mTitleView = ((TextView)findViewById(R.id.title));
    if (this.mTextAppearance != -1) {
      this.mTitleView.setTextAppearance(this.mTextAppearanceContext, this.mTextAppearance);
    }
    this.mShortcutView = ((TextView)findViewById(R.id.shortcut));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if ((this.mIconView != null) && (this.mPreserveIconSpacing))
    {
      ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
      LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)this.mIconView.getLayoutParams();
      if ((localLayoutParams.height > 0) && (localLayoutParams1.width <= 0)) {
        localLayoutParams1.width = localLayoutParams.height;
      }
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public final boolean prefersCondensedTitle()
  {
    return false;
  }
  
  public void setCheckable(boolean paramBoolean)
  {
    if ((!paramBoolean) && (this.mRadioButton == null) && (this.mCheckBox == null)) {}
    label50:
    do
    {
      return;
      Object localObject1;
      Object localObject2;
      if (this.mItemData.isExclusiveCheckable())
      {
        if (this.mRadioButton == null) {
          insertRadioButton();
        }
        localObject1 = this.mRadioButton;
        localObject2 = this.mCheckBox;
        if (!paramBoolean) {
          break label138;
        }
        ((CompoundButton)localObject1).setChecked(this.mItemData.isChecked());
        if (!paramBoolean) {
          break label131;
        }
      }
      for (int i = 0;; i = 8)
      {
        if (((CompoundButton)localObject1).getVisibility() != i) {
          ((CompoundButton)localObject1).setVisibility(i);
        }
        if ((localObject2 == null) || (((CompoundButton)localObject2).getVisibility() == 8)) {
          break;
        }
        ((CompoundButton)localObject2).setVisibility(8);
        return;
        if (this.mCheckBox == null) {
          insertCheckBox();
        }
        localObject1 = this.mCheckBox;
        localObject2 = this.mRadioButton;
        break label50;
      }
      if (this.mCheckBox != null) {
        this.mCheckBox.setVisibility(8);
      }
    } while (this.mRadioButton == null);
    label131:
    label138:
    this.mRadioButton.setVisibility(8);
  }
  
  public void setChecked(boolean paramBoolean)
  {
    if (this.mItemData.isExclusiveCheckable()) {
      if (this.mRadioButton == null) {
        insertRadioButton();
      }
    }
    for (Object localObject = this.mRadioButton;; localObject = this.mCheckBox)
    {
      ((CompoundButton)localObject).setChecked(paramBoolean);
      return;
      if (this.mCheckBox == null) {
        insertCheckBox();
      }
    }
  }
  
  public void setForceShowIcon(boolean paramBoolean)
  {
    this.mForceShowIcon = paramBoolean;
    this.mPreserveIconSpacing = paramBoolean;
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    int i;
    if ((this.mItemData.mMenu.mOptionalIconsVisible) || (this.mForceShowIcon))
    {
      i = 1;
      if ((i != 0) || (this.mPreserveIconSpacing)) {
        break label39;
      }
    }
    label39:
    while ((this.mIconView == null) && (paramDrawable == null) && (!this.mPreserveIconSpacing))
    {
      return;
      i = 0;
      break;
    }
    if (this.mIconView == null)
    {
      this.mIconView = ((ImageView)getInflater().inflate(R.layout.abc_list_menu_item_icon, this, false));
      addView(this.mIconView, 0);
    }
    if ((paramDrawable != null) || (this.mPreserveIconSpacing))
    {
      ImageView localImageView = this.mIconView;
      if (i != 0) {}
      for (;;)
      {
        localImageView.setImageDrawable(paramDrawable);
        if (this.mIconView.getVisibility() == 0) {
          break;
        }
        this.mIconView.setVisibility(0);
        return;
        paramDrawable = null;
      }
    }
    this.mIconView.setVisibility(8);
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    if (paramCharSequence != null)
    {
      this.mTitleView.setText(paramCharSequence);
      if (this.mTitleView.getVisibility() != 0) {
        this.mTitleView.setVisibility(0);
      }
    }
    while (this.mTitleView.getVisibility() == 8) {
      return;
    }
    this.mTitleView.setVisibility(8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.ListMenuItemView
 * JD-Core Version:    0.7.0.1
 */