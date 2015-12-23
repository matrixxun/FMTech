package com.google.android.vending.verifier;

import com.google.android.finsky.utils.Utils;
import com.google.android.instrumentedzip.ZipEntry;
import com.google.android.instrumentedzip.ZipFile;
import com.google.android.vending.verifier.api.PackageVerificationApi.FileInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ZipAnalyzer
{
  private static final String[] IMPORTANT_FILES = { "classes.dex", "AndroidManifest.xml", "resources.arsc", "META-INF/MANIFEST.MF" };
  
  static PackageVerificationApi.FileInfo[] analyzeZipFile(File paramFile)
    throws IOException, NoSuchAlgorithmException
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    ZipFile localZipFile = new ZipFile(paramFile);
    for (;;)
    {
      int i;
      ZipEntry localZipEntry2;
      int k;
      try
      {
        List localList1 = Collections.unmodifiableList(localZipFile.entryList);
        i = 0;
        if (i >= localList1.size()) {
          break label180;
        }
        localZipEntry2 = (ZipEntry)localList1.get(i);
        localZipFile.verifyAndGetInputStream(localZipEntry2, true);
        String str = localZipEntry2.name;
        String[] arrayOfString = IMPORTANT_FILES;
        k = 0;
        if (k >= arrayOfString.length) {
          break label392;
        }
        if (!arrayOfString[k].equals(str)) {
          break label386;
        }
        m = 1;
        if (m != 0) {
          localArrayList1.add(localZipEntry2);
        } else if (localZipEntry2.verificationErrors != 0) {
          localArrayList2.add(localZipEntry2);
        }
      }
      finally
      {
        Utils.safeClose(localZipFile);
      }
      localArrayList3.add(localZipEntry2);
      break label380;
      label180:
      List localList2 = shuffleAndChoose(localArrayList1, 20);
      List localList3 = shuffleAndChoose(localArrayList2, 10);
      List localList4 = shuffleAndChoose(localArrayList3, 10);
      ArrayList localArrayList4 = new ArrayList(localList2.size() + localList3.size() + localList4.size());
      localArrayList4.addAll(localList2);
      localArrayList4.addAll(localList3);
      localArrayList4.addAll(localList4);
      PackageVerificationApi.FileInfo[] arrayOfFileInfo = new PackageVerificationApi.FileInfo[localArrayList4.size()];
      int j = 0;
      for (;;)
      {
        ZipEntry localZipEntry1;
        InputStream localInputStream;
        if (j < localArrayList4.size())
        {
          localZipEntry1 = (ZipEntry)localArrayList4.get(j);
          localInputStream = null;
        }
        try
        {
          localInputStream = localZipFile.verifyAndGetInputStream(localZipEntry1, false);
          byte[] arrayOfByte = PackageVerificationService.getSha256Hash(localInputStream);
          arrayOfFileInfo[j] = new PackageVerificationApi.FileInfo(localZipEntry1.name, arrayOfByte, localZipEntry1.verificationErrors);
          Utils.safeClose(localInputStream);
          j++;
        }
        finally
        {
          Utils.safeClose(localInputStream);
        }
      }
      return arrayOfFileInfo;
      label380:
      i++;
      continue;
      label386:
      k++;
      continue;
      label392:
      int m = 0;
    }
  }
  
  private static final <T> List<T> shuffleAndChoose(List<T> paramList, int paramInt)
  {
    if (paramList.size() <= paramInt) {
      return paramList;
    }
    Collections.shuffle(paramList);
    return paramList.subList(0, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.ZipAnalyzer
 * JD-Core Version:    0.7.0.1
 */