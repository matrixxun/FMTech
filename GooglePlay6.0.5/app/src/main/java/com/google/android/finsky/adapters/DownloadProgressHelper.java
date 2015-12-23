package com.google.android.finsky.adapters;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.receivers.Installer.InstallerProgressReport;
import com.google.android.finsky.utils.UiUtils;

public final class DownloadProgressHelper
{
  private static CharSequence sDownloadStatusFormatBytes;
  private static CharSequence sDownloadStatusFormatPercents;
  
  public static void configureDownloadProgressUi(Context paramContext, Installer.InstallerProgressReport paramInstallerProgressReport, TextView paramTextView1, TextView paramTextView2, ProgressBar paramProgressBar)
  {
    if (sDownloadStatusFormatPercents == null) {
      sDownloadStatusFormatPercents = Html.fromHtml(paramContext.getString(2131362982));
    }
    if (sDownloadStatusFormatBytes == null) {
      sDownloadStatusFormatBytes = Html.fromHtml(paramContext.getString(2131362104));
    }
    paramTextView2.setText(" ");
    paramTextView1.setText(" ");
    if (paramInstallerProgressReport.installerState != 2)
    {
      paramProgressBar.setIndeterminate(true);
      if (paramInstallerProgressReport.installerState == 3) {
        paramTextView1.setText(2131362250);
      }
      while (!UiUtils.isAndroidTv()) {
        return;
      }
      paramTextView1.setText(2131362805);
      return;
    }
    int i;
    int j;
    if ((paramInstallerProgressReport.bytesCompleted > 0L) && (paramInstallerProgressReport.bytesTotal > 0L) && (paramInstallerProgressReport.bytesCompleted <= paramInstallerProgressReport.bytesTotal))
    {
      i = 1;
      if (i == 0) {
        break label184;
      }
      j = (int)(100L * paramInstallerProgressReport.bytesCompleted / paramInstallerProgressReport.bytesTotal);
      paramProgressBar.setIndeterminate(false);
      paramProgressBar.setProgress(j);
    }
    int k;
    for (;;)
    {
      k = paramInstallerProgressReport.downloadStatus;
      if (k != 195) {
        break label196;
      }
      paramTextView1.setText(2131362102);
      return;
      i = 0;
      break;
      label184:
      paramProgressBar.setIndeterminate(true);
      j = 0;
    }
    label196:
    if (k == 196)
    {
      paramTextView1.setText(2131362103);
      return;
    }
    if (i != 0)
    {
      CharSequence localCharSequence1 = sDownloadStatusFormatPercents;
      CharSequence[] arrayOfCharSequence1 = new CharSequence[1];
      arrayOfCharSequence1[0] = Integer.toString(j);
      paramTextView2.setText(TextUtils.expandTemplate(localCharSequence1, arrayOfCharSequence1));
      CharSequence localCharSequence2 = sDownloadStatusFormatBytes;
      CharSequence[] arrayOfCharSequence2 = new CharSequence[2];
      arrayOfCharSequence2[0] = Formatter.formatFileSize(paramContext, paramInstallerProgressReport.bytesCompleted);
      arrayOfCharSequence2[1] = Formatter.formatFileSize(paramContext, paramInstallerProgressReport.bytesTotal);
      paramTextView1.setText(TextUtils.expandTemplate(localCharSequence2, arrayOfCharSequence2));
      return;
    }
    paramTextView1.setText(2131362100);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.DownloadProgressHelper
 * JD-Core Version:    0.7.0.1
 */