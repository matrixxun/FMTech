package org.keyczar;

import java.util.concurrent.ConcurrentHashMap;
import org.keyczar.interfaces.Stream;

final class StreamCache<T extends Stream>
{
  private final ConcurrentHashMap<KeyczarKey, StreamQueue<T>> cacheMap = new ConcurrentHashMap();
  
  final StreamQueue<T> getQueue(KeyczarKey paramKeyczarKey)
  {
    StreamQueue localStreamQueue1 = (StreamQueue)this.cacheMap.get(paramKeyczarKey);
    StreamQueue localStreamQueue2;
    if (localStreamQueue1 != null) {
      localStreamQueue2 = localStreamQueue1;
    }
    StreamQueue localStreamQueue3;
    do
    {
      return localStreamQueue2;
      localStreamQueue2 = new StreamQueue();
      localStreamQueue3 = (StreamQueue)this.cacheMap.putIfAbsent(paramKeyczarKey, localStreamQueue2);
    } while (localStreamQueue3 == null);
    return localStreamQueue3;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.StreamCache
 * JD-Core Version:    0.7.0.1
 */