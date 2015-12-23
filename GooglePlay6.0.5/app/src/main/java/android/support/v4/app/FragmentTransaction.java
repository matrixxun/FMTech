package android.support.v4.app;

import android.view.View;

public abstract class FragmentTransaction
{
  public abstract FragmentTransaction add(int paramInt, Fragment paramFragment);
  
  public abstract FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString);
  
  public abstract FragmentTransaction add(Fragment paramFragment, String paramString);
  
  public abstract FragmentTransaction addSharedElement(View paramView, String paramString);
  
  public abstract FragmentTransaction addToBackStack(String paramString);
  
  public abstract FragmentTransaction attach(Fragment paramFragment);
  
  public abstract int commit();
  
  public abstract int commitAllowingStateLoss();
  
  public abstract FragmentTransaction detach(Fragment paramFragment);
  
  public abstract FragmentTransaction hide(Fragment paramFragment);
  
  public abstract FragmentTransaction remove(Fragment paramFragment);
  
  public abstract FragmentTransaction replace(int paramInt, Fragment paramFragment);
  
  public abstract FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString);
  
  public abstract FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2);
  
  public abstract FragmentTransaction setTransition$9d93138();
  
  public abstract FragmentTransaction show(Fragment paramFragment);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.FragmentTransaction
 * JD-Core Version:    0.7.0.1
 */