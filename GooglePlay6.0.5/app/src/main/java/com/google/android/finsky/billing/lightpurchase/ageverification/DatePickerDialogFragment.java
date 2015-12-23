package com.google.android.finsky.billing.lightpurchase.ageverification;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Calendar;

public final class DatePickerDialogFragment
  extends DialogFragment
{
  private DatePickerDialog mDatePickerDialog;
  
  public static DatePickerDialogFragment newInstance(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      throw new IllegalStateException("Calendar is not set");
    }
    DatePickerDialogFragment localDatePickerDialogFragment = new DatePickerDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("DatePickerDialogFragment.calendar", paramCalendar);
    localDatePickerDialogFragment.setArguments(localBundle);
    return localDatePickerDialogFragment;
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Fragment localFragment = this.mTarget;
    final DatePickerDialog.OnDateSetListener localOnDateSetListener1;
    DatePickerDialog.OnDateSetListener localOnDateSetListener2;
    if ((localFragment instanceof DatePickerDialog.OnDateSetListener))
    {
      localOnDateSetListener1 = (DatePickerDialog.OnDateSetListener)localFragment;
      if (Build.VERSION.SDK_INT < 16) {
        break label175;
      }
      localOnDateSetListener2 = null;
      label29:
      if (paramBundle != null) {
        break label182;
      }
      Calendar localCalendar = (Calendar)this.mArguments.getSerializable("DatePickerDialogFragment.calendar");
      this.mDatePickerDialog = new DatePickerDialog(getActivity(), localOnDateSetListener2, localCalendar.get(1), localCalendar.get(2), localCalendar.get(5));
    }
    for (;;)
    {
      if (Build.VERSION.SDK_INT >= 16)
      {
        this.mDatePickerDialog.setButton(-1, getActivity().getString(17039370), new DialogInterface.OnClickListener()
        {
          @TargetApi(11)
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            if (localOnDateSetListener1 != null)
            {
              DatePicker localDatePicker = DatePickerDialogFragment.this.mDatePickerDialog.getDatePicker();
              localOnDateSetListener1.onDateSet(localDatePicker, localDatePicker.getYear(), localDatePicker.getMonth(), localDatePicker.getDayOfMonth());
              return;
            }
            FinskyLog.wtf("No listener found.", new Object[0]);
          }
        });
        this.mDatePickerDialog.setButton(-2, getActivity().getString(17039360), new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
        });
      }
      return this.mDatePickerDialog;
      FragmentActivity localFragmentActivity = getActivity();
      if ((localFragmentActivity instanceof DatePickerDialog.OnDateSetListener))
      {
        localOnDateSetListener1 = (DatePickerDialog.OnDateSetListener)localFragmentActivity;
        break;
      }
      localOnDateSetListener1 = null;
      break;
      label175:
      localOnDateSetListener2 = localOnDateSetListener1;
      break label29;
      label182:
      this.mDatePickerDialog = new DatePickerDialog(getActivity(), localOnDateSetListener2, 0, 0, 0);
      this.mDatePickerDialog.onRestoreInstanceState(paramBundle.getBundle("DatePickerDialogFragment.calendar"));
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBundle("DatePickerDialogFragment.calendar", this.mDatePickerDialog.onSaveInstanceState());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.ageverification.DatePickerDialogFragment
 * JD-Core Version:    0.7.0.1
 */