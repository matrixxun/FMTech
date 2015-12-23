package com.google.android.play.utils;

import java.io.File;
import java.util.Comparator;

public final class FileModifiedDateComparator
  implements Comparator<File>
{
  public static final FileModifiedDateComparator INSTANCE = new FileModifiedDateComparator();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.FileModifiedDateComparator
 * JD-Core Version:    0.7.0.1
 */