package com.android.i18n.addressinput;

import android.view.View;
import java.util.ArrayList;
import java.util.List;

public final class AddressUIComponent
{
  List<RegionData> mCandidatesList = new ArrayList();
  String mFieldName;
  AddressField mId;
  AddressField mParentId;
  public UIComponent mUiType;
  public View mView;
  
  AddressUIComponent(AddressField paramAddressField)
  {
    this.mId = paramAddressField;
    this.mParentId = null;
    this.mUiType = UIComponent.EDIT;
  }
  
  final void initializeCandidatesList(List<RegionData> paramList)
  {
    this.mCandidatesList = paramList;
    if (paramList.size() > 1) {
      this.mUiType = UIComponent.SPINNER;
    }
    switch (1.$SwitchMap$com$android$i18n$addressinput$AddressField[this.mId.ordinal()])
    {
    default: 
      return;
    case 1: 
      this.mParentId = AddressField.LOCALITY;
      return;
    case 2: 
      this.mParentId = AddressField.ADMIN_AREA;
      return;
    }
    this.mParentId = AddressField.COUNTRY;
  }
  
  public static enum UIComponent
  {
    static
    {
      UIComponent[] arrayOfUIComponent = new UIComponent[2];
      arrayOfUIComponent[0] = EDIT;
      arrayOfUIComponent[1] = SPINNER;
      $VALUES = arrayOfUIComponent;
    }
    
    private UIComponent() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.AddressUIComponent
 * JD-Core Version:    0.7.0.1
 */