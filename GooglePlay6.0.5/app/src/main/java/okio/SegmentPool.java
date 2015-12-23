package okio;

final class SegmentPool
{
  static final SegmentPool INSTANCE = new SegmentPool();
  long byteCount;
  private Segment next;
  
  final void recycle(Segment paramSegment)
  {
    if ((paramSegment.next != null) || (paramSegment.prev != null)) {
      throw new IllegalArgumentException();
    }
    try
    {
      if (2048L + this.byteCount > 65536L) {
        return;
      }
      this.byteCount = (2048L + this.byteCount);
      paramSegment.next = this.next;
      paramSegment.limit = 0;
      paramSegment.pos = 0;
      this.next = paramSegment;
      return;
    }
    finally {}
  }
  
  final Segment take()
  {
    try
    {
      if (this.next != null)
      {
        Segment localSegment = this.next;
        this.next = localSegment.next;
        localSegment.next = null;
        this.byteCount -= 2048L;
        return localSegment;
      }
      return new Segment();
    }
    finally {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     okio.SegmentPool
 * JD-Core Version:    0.7.0.1
 */