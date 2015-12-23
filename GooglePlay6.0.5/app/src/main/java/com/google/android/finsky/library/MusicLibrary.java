package com.google.android.finsky.library;

import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.Sets;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Collections;
import java.util.Set;

public final class MusicLibrary
  extends LibraryWithSubscriptions
{
  private static Set<String> sBackendDocidBlacklist = Collections.unmodifiableSet(Sets.newHashSet(((String)G.musicAppSubscriptionBackendDocidBlacklist.get()).split(",")));
  
  public MusicLibrary(LibraryHasher paramLibraryHasher)
  {
    super(2, paramLibraryHasher);
  }
  
  protected final boolean shouldExcludeFromSubscriptionsList(LibraryEntry paramLibraryEntry)
  {
    return (super.shouldExcludeFromSubscriptionsList(paramLibraryEntry)) || (sBackendDocidBlacklist.contains(paramLibraryEntry.mDocId));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.MusicLibrary
 * JD-Core Version:    0.7.0.1
 */