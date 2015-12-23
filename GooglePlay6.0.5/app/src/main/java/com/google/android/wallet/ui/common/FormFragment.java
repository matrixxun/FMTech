package com.google.android.wallet.ui.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.common.util.ArrayUtils;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.dependencygraph.DependencyGraphManager;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.List;

public abstract class FormFragment<T extends MessageNano>
  extends BaseWalletUiComponentFragment
  implements UiNode, Form, FormEventListener
{
  public int mCurrentFormIndex = -1;
  public DependencyGraphManager mDependencyGraphManager;
  public T mFormProto;
  public ArrayList<T> mFormProtos;
  private UiNode mParentUiNode;
  public TriggerListener mTriggerListener;
  public boolean mUiEnabled = true;
  
  private static boolean checkThisField(int paramInt, int[] paramArrayOfInt)
  {
    return (paramArrayOfInt == null) || ((paramInt > 100) && (ArrayUtils.contains(paramArrayOfInt, paramInt)));
  }
  
  public static <T extends MessageNano, F extends FormFragment<T>> Bundle createArgsForFormFragment$179723a0(int paramInt, T paramT)
  {
    Bundle localBundle = createArgs(paramInt);
    localBundle.putParcelable("formProto", ParcelableProto.forProto(paramT));
    return localBundle;
  }
  
  public abstract void doEnableUi();
  
  public final void enableUi(boolean paramBoolean)
  {
    this.mUiEnabled = paramBoolean;
    doEnableUi();
  }
  
  public final boolean focusOnFirstErroneousFormField()
  {
    List localList = getFieldsForValidationInOrder();
    int i = 0;
    int j = localList.size();
    while (i < j)
    {
      Object localObject = ((FieldData)localList.get(i)).field;
      if ((localObject instanceof Form))
      {
        if (((Form)localObject).focusOnFirstErroneousFormField()) {
          return true;
        }
      }
      else if ((localObject instanceof FieldValidatable))
      {
        if (!TextUtils.isEmpty(((FieldValidatable)localObject).getError()))
        {
          if ((localObject instanceof View)) {
            WalletUiUtils.requestFocusAndAnnounceError((View)localObject);
          }
          for (;;)
          {
            return true;
            if (!(localObject instanceof OtpFieldFragment)) {
              break;
            }
            WalletUiUtils.requestFocusAndAnnounceError(((OtpFieldFragment)localObject).mOtpField);
          }
          throw new IllegalStateException("Unexpected field type: " + localObject.getClass().getName());
        }
      }
      else if ((localObject instanceof View))
      {
        if (!TextUtils.isEmpty(WalletUiUtils.getUiFieldError((View)localObject)))
        {
          WalletUiUtils.requestFocusAndAnnounceError((View)localObject);
          return true;
        }
      }
      else {
        throw new IllegalStateException("Unexpected field type: " + localObject.getClass().getName());
      }
      i++;
    }
    return false;
  }
  
  public String getButtonBarExpandButtonText()
  {
    return null;
  }
  
  public abstract <S, F extends FieldData<S>> List<F> getFieldsForValidationInOrder();
  
  public UiNode getParentUiNode()
  {
    if (this.mParentUiNode != null) {
      return this.mParentUiNode;
    }
    if (this.mParentFragment != null) {
      return (UiNode)this.mParentFragment;
    }
    return (UiNode)getActivity();
  }
  
  public boolean handleErrorMessageDismissed(String paramString, int paramInt)
  {
    return false;
  }
  
  public final boolean isValid(int[] paramArrayOfInt)
  {
    return validate(paramArrayOfInt, false);
  }
  
  public void notifyFormEvent(int paramInt, Bundle paramBundle)
  {
    if (this.mParentFragment != null)
    {
      ((FormEventListener)this.mParentFragment).notifyFormEvent(paramInt, paramBundle);
      return;
    }
    ((FormEventListener)getActivity()).notifyFormEvent(paramInt, paramBundle);
  }
  
  public void onButtonBarExpandButtonClicked() {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null) {
      this.mUiEnabled = paramBundle.getBoolean("uiEnabled", true);
    }
    Bundle localBundle = this.mArguments;
    if (localBundle.containsKey("formProto"))
    {
      this.mFormProto = ParcelableProto.getProtoFromBundle(localBundle, "formProto");
      return;
    }
    if (localBundle.containsKey("formProtos"))
    {
      this.mFormProtos = ParcelableProto.getProtoArrayListFromBundle(localBundle, "formProtos");
      if (paramBundle != null)
      {
        this.mCurrentFormIndex = paramBundle.getInt("currentFormIndex", -1);
        return;
      }
      this.mCurrentFormIndex = localBundle.getInt("initialFormProtoIndex");
      return;
    }
    throw new IllegalArgumentException("FormFragment cannot be created without required form proto");
  }
  
  public void onResume()
  {
    super.onResume();
    notifyFormEvent(4, Bundle.EMPTY);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("uiEnabled", this.mUiEnabled);
    if ((this.mFormProtos != null) && (this.mCurrentFormIndex >= 0)) {
      paramBundle.putInt("currentFormIndex", this.mCurrentFormIndex);
    }
  }
  
  public void setParentUiNode(UiNode paramUiNode)
  {
    this.mParentUiNode = paramUiNode;
  }
  
  public boolean shouldShowButtonBarExpandButton()
  {
    return false;
  }
  
  public boolean validate(int[] paramArrayOfInt)
  {
    return validate(paramArrayOfInt, true);
  }
  
  public boolean validate(int[] paramArrayOfInt, boolean paramBoolean)
  {
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length == 0)) {
      return true;
    }
    List localList = getFieldsForValidationInOrder();
    boolean bool = true;
    int i = 0;
    int j = localList.size();
    if (i < j)
    {
      FieldData localFieldData = (FieldData)localList.get(i);
      FormValidatable localFormValidatable;
      int[] arrayOfInt;
      if ((localFieldData.field instanceof FormValidatable))
      {
        localFormValidatable = (FormValidatable)localFieldData.field;
        if (checkThisField(localFieldData.uiReference, paramArrayOfInt))
        {
          arrayOfInt = null;
          label86:
          if (!paramBoolean) {
            break label128;
          }
          if ((!localFormValidatable.validate(arrayOfInt)) || (!bool)) {
            break label122;
          }
          bool = true;
        }
      }
      label122:
      do
      {
        FieldValidatable localFieldValidatable;
        do
        {
          do
          {
            do
            {
              for (;;)
              {
                i++;
                break;
                arrayOfInt = paramArrayOfInt;
                break label86;
                bool = false;
              }
            } while (localFormValidatable.isValid(arrayOfInt));
            return false;
          } while (!checkThisField(localFieldData.uiReference, paramArrayOfInt));
          if (!(localFieldData.field instanceof FieldValidatable)) {
            break label218;
          }
          localFieldValidatable = (FieldValidatable)localFieldData.field;
          if (paramBoolean)
          {
            if ((localFieldValidatable.validate()) && (bool)) {}
            for (bool = true;; bool = false) {
              break;
            }
          }
        } while (localFieldValidatable.isValid());
        return false;
        if (!(localFieldData.field instanceof View)) {
          break label282;
        }
        if (paramBoolean)
        {
          if ((WalletUiUtils.validateUiField((View)localFieldData.field, true)) && (bool)) {}
          for (bool = true;; bool = false) {
            break;
          }
        }
      } while (WalletUiUtils.validateUiField((View)localFieldData.field, false));
      label128:
      label218:
      return false;
      label282:
      throw new IllegalStateException("Unexpected field type: " + localFieldData.field.getClass().getName());
    }
    return bool;
  }
  
  public static class FieldData<T>
  {
    public final T field;
    public final Object initialValue;
    public final int uiReference;
    
    public FieldData(int paramInt, Form paramForm)
    {
      this(paramInt, paramForm, null);
    }
    
    public FieldData(int paramInt, T paramT, Object paramObject)
    {
      this.uiReference = paramInt;
      this.field = paramT;
      this.initialValue = paramObject;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.FormFragment
 * JD-Core Version:    0.7.0.1
 */