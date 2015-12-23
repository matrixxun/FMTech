package android.support.v7.widget;

final class OpReorderer
{
  final Callback mCallback;
  
  public OpReorderer(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  static abstract interface Callback
  {
    public abstract AdapterHelper.UpdateOp obtainUpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject);
    
    public abstract void recycleUpdateOp(AdapterHelper.UpdateOp paramUpdateOp);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.OpReorderer
 * JD-Core Version:    0.7.0.1
 */