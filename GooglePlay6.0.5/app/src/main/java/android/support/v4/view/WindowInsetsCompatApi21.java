package android.support.v4.view;

import android.view.WindowInsets;

final class WindowInsetsCompatApi21
  extends WindowInsetsCompat
{
  final WindowInsets mSource;
  
  WindowInsetsCompatApi21(WindowInsets paramWindowInsets)
  {
    this.mSource = paramWindowInsets;
  }
  
  public final int getSystemWindowInsetBottom()
  {
    return this.mSource.getSystemWindowInsetBottom();
  }
  
  public final int getSystemWindowInsetLeft()
  {
    return this.mSource.getSystemWindowInsetLeft();
  }
  
  public final int getSystemWindowInsetRight()
  {
    return this.mSource.getSystemWindowInsetRight();
  }
  
  public final int getSystemWindowInsetTop()
  {
    return this.mSource.getSystemWindowInsetTop();
  }
  
  public final boolean isConsumed()
  {
    return this.mSource.isConsumed();
  }
  
  public final WindowInsetsCompat replaceSystemWindowInsets(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return new WindowInsetsCompatApi21(this.mSource.replaceSystemWindowInsets(paramInt1, paramInt2, paramInt3, paramInt4));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.WindowInsetsCompatApi21
 * JD-Core Version:    0.7.0.1
 */