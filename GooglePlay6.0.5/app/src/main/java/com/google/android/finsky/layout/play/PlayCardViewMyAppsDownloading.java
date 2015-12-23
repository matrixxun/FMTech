package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.adapters.DownloadProgressHelper;
import com.google.android.finsky.receivers.Installer.InstallerProgressReport;

public class PlayCardViewMyAppsDownloading
  extends PlayCardViewMyApps
{
  private TextView mDownloadingBytes;
  private TextView mDownloadingPercentage;
  private ProgressBar mProgressBar;
  
  public PlayCardViewMyAppsDownloading(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardViewMyAppsDownloading(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mThumbnailAspectRatio = 1.0F;
  }
  
  public final void bindProgress(Installer.InstallerProgressReport paramInstallerProgressReport)
  {
    DownloadProgressHelper.configureDownloadProgressUi(getContext(), paramInstallerProgressReport, this.mDownloadingBytes, this.mDownloadingPercentage, this.mProgressBar);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDownloadingBytes = ((TextView)findViewById(2131755389));
    this.mDownloadingPercentage = ((TextView)findViewById(2131755388));
    this.mProgressBar = ((ProgressBar)findViewById(2131755277));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    measureThumbnailSpanningHeight(paramInt2);
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardViewMyAppsDownloading
 * JD-Core Version:    0.7.0.1
 */