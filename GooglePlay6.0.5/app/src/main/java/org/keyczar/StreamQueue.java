package org.keyczar;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.keyczar.interfaces.Stream;

final class StreamQueue<T extends Stream>
  extends ConcurrentLinkedQueue<T>
{}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.StreamQueue
 * JD-Core Version:    0.7.0.1
 */