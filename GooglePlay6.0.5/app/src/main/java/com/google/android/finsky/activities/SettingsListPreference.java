package com.google.android.finsky.activities;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.preference.ListPreference;
import android.preference.Preference.BaseSavedState;
import android.text.TextUtils;
import android.util.AttributeSet;

public class SettingsListPreference
  extends ListPreference
{
  public SettingsListPreference(Context paramContext)
  {
    super(paramContext);
  }
  
  public SettingsListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    setEntries(localSavedState.entries);
    setEntryValues(localSavedState.entryValues);
    setValue(localSavedState.value);
    setSummary(localSavedState.summary);
    super.onRestoreInstanceState(localSavedState.getSuperState());
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    SavedState.access$002(localSavedState, getEntries());
    SavedState.access$102(localSavedState, getEntryValues());
    SavedState.access$202(localSavedState, getValue());
    SavedState.access$302(localSavedState, getSummary());
    return localSavedState;
  }
  
  public final void setEntriesAndValues(SettingsListEntry[] paramArrayOfSettingsListEntry)
  {
    int i = paramArrayOfSettingsListEntry.length;
    CharSequence[] arrayOfCharSequence1 = new CharSequence[i];
    CharSequence[] arrayOfCharSequence2 = new CharSequence[i];
    for (int j = 0; j < i; j++)
    {
      SettingsListEntry localSettingsListEntry = paramArrayOfSettingsListEntry[j];
      arrayOfCharSequence1[j] = getContext().getString(localSettingsListEntry.getResource());
      arrayOfCharSequence2[j] = localSettingsListEntry.toString();
    }
    setEntries(arrayOfCharSequence1);
    setEntryValues(arrayOfCharSequence2);
  }
  
  public final void setValueAndUpdateSummary(SettingsListEntry paramSettingsListEntry)
  {
    setValue(paramSettingsListEntry.toString());
    updateListPreferenceSummary();
  }
  
  public final void updateListPreferenceSummary()
  {
    setSummary(getEntry());
  }
  
  private static class SavedState
    extends Preference.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {};
    private CharSequence[] entries;
    private CharSequence[] entryValues;
    private CharSequence summary;
    private String value;
    
    public SavedState(Parcel paramParcel)
    {
      super();
      this.entries = readCharSequenceArray(paramParcel);
      this.entryValues = readCharSequenceArray(paramParcel);
      this.value = paramParcel.readString();
      this.summary = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    private static CharSequence[] readCharSequenceArray(Parcel paramParcel)
    {
      int i = paramParcel.readInt();
      CharSequence[] arrayOfCharSequence = new CharSequence[i];
      for (int j = 0; j < i; j++) {
        arrayOfCharSequence[j] = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
      }
      return arrayOfCharSequence;
    }
    
    private static void writeCharSequenceArray(Parcel paramParcel, CharSequence[] paramArrayOfCharSequence)
    {
      if (paramArrayOfCharSequence != null)
      {
        int i = paramArrayOfCharSequence.length;
        paramParcel.writeInt(i);
        for (int j = 0; j < i; j++) {
          TextUtils.writeToParcel(paramArrayOfCharSequence[j], paramParcel, 0);
        }
      }
      paramParcel.writeInt(-1);
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      writeCharSequenceArray(paramParcel, this.entries);
      writeCharSequenceArray(paramParcel, this.entryValues);
      paramParcel.writeString(this.value);
      TextUtils.writeToParcel(this.summary, paramParcel, 0);
    }
  }
  
  public static abstract interface SettingsListEntry
  {
    public abstract int getResource();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.SettingsListPreference
 * JD-Core Version:    0.7.0.1
 */