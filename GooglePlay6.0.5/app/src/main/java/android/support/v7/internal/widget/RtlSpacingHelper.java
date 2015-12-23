package android.support.v7.internal.widget;

public final class RtlSpacingHelper
{
  public int mEnd = -2147483648;
  public int mExplicitLeft = 0;
  public int mExplicitRight = 0;
  public boolean mIsRelative = false;
  public boolean mIsRtl = false;
  public int mLeft = 0;
  public int mRight = 0;
  public int mStart = -2147483648;
  
  public final void setRelative(int paramInt1, int paramInt2)
  {
    this.mStart = paramInt1;
    this.mEnd = paramInt2;
    this.mIsRelative = true;
    if (this.mIsRtl)
    {
      if (paramInt2 != -2147483648) {
        this.mLeft = paramInt2;
      }
      if (paramInt1 != -2147483648) {
        this.mRight = paramInt1;
      }
    }
    do
    {
      return;
      if (paramInt1 != -2147483648) {
        this.mLeft = paramInt1;
      }
    } while (paramInt2 == -2147483648);
    this.mRight = paramInt2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.RtlSpacingHelper
 * JD-Core Version:    0.7.0.1
 */