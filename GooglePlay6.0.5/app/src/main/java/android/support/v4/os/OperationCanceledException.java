package android.support.v4.os;

public final class OperationCanceledException
  extends RuntimeException
{
  public OperationCanceledException()
  {
    this((byte)0);
  }
  
  private OperationCanceledException(byte paramByte)
  {
    super("The operation has been canceled.");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.os.OperationCanceledException
 * JD-Core Version:    0.7.0.1
 */