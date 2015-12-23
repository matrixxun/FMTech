package okio;

final class Segment
{
  final byte[] data = new byte[2048];
  int limit;
  Segment next;
  int pos;
  Segment prev;
  
  public final Segment pop()
  {
    if (this.next != this) {}
    for (Segment localSegment = this.next;; localSegment = null)
    {
      this.prev.next = this.next;
      this.next.prev = this.prev;
      this.next = null;
      this.prev = null;
      return localSegment;
    }
  }
  
  public final Segment push(Segment paramSegment)
  {
    paramSegment.prev = this;
    paramSegment.next = this.next;
    this.next.prev = paramSegment;
    this.next = paramSegment;
    return paramSegment;
  }
  
  public final void writeTo(Segment paramSegment, int paramInt)
  {
    if (paramInt + (paramSegment.limit - paramSegment.pos) > 2048) {
      throw new IllegalArgumentException();
    }
    if (paramInt + paramSegment.limit > 2048)
    {
      System.arraycopy(paramSegment.data, paramSegment.pos, paramSegment.data, 0, paramSegment.limit - paramSegment.pos);
      paramSegment.limit -= paramSegment.pos;
      paramSegment.pos = 0;
    }
    System.arraycopy(this.data, this.pos, paramSegment.data, paramSegment.limit, paramInt);
    paramSegment.limit = (paramInt + paramSegment.limit);
    this.pos = (paramInt + this.pos);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.Segment
 * JD-Core Version:    0.7.0.1
 */