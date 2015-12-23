package com.google.android.finsky.api.model;

import android.accounts.Account;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.HashingLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class MultiBulkPreregistrationDetails
  extends MultiDfeBulkDetails
{
  public Map<String, DocumentAndAccounts> mDocumentAndAccountsMap;
  
  public final void addRequests(Libraries paramLibraries)
  {
    Iterator localIterator1 = paramLibraries.getAccountLibraries().iterator();
    while (localIterator1.hasNext())
    {
      AccountLibrary localAccountLibrary = (AccountLibrary)localIterator1.next();
      HashingLibrary localHashingLibrary = localAccountLibrary.getLibrary("u-pl");
      if (localHashingLibrary.size() != 0)
      {
        String str = localAccountLibrary.mAccount.name;
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator2 = localHashingLibrary.iterator();
        while (localIterator2.hasNext()) {
          localArrayList.add(((LibraryEntry)localIterator2.next()).mDocId);
        }
        addRequest(FinskyApp.get().getDfeApi(str), localArrayList, false);
      }
    }
    if (this.mRequests.size() == 0) {
      onDataChanged();
    }
  }
  
  protected final void collateResponses()
  {
    if (this.mDocumentAndAccountsMap != null) {
      FinskyLog.wtf("Unexpected repeat collation", new Object[0]);
    }
    this.mDocumentAndAccountsMap = new HashMap();
    Iterator localIterator1 = this.mRequests.iterator();
    while (localIterator1.hasNext())
    {
      DfeBulkDetails localDfeBulkDetails = (DfeBulkDetails)localIterator1.next();
      List localList = localDfeBulkDetails.getDocuments();
      if (localList != null)
      {
        String str1 = localDfeBulkDetails.mDfeApi.getAccountName();
        Iterator localIterator2 = localList.iterator();
        while (localIterator2.hasNext())
        {
          Document localDocument = (Document)localIterator2.next();
          String str2 = localDocument.mDocument.docid;
          DocumentAndAccounts localDocumentAndAccounts1 = (DocumentAndAccounts)this.mDocumentAndAccountsMap.get(str2);
          if (localDocumentAndAccounts1 == null)
          {
            DocumentAndAccounts localDocumentAndAccounts2 = new DocumentAndAccounts(localDocument, str1);
            this.mDocumentAndAccountsMap.put(str2, localDocumentAndAccounts2);
          }
          else
          {
            localDocumentAndAccounts1.accountNames.add(str1);
          }
        }
      }
    }
  }
  
  public static final class DocumentAndAccounts
  {
    public final List<String> accountNames;
    public final Document document;
    
    public DocumentAndAccounts(Document paramDocument, String paramString)
    {
      this.document = paramDocument;
      this.accountNames = new ArrayList();
      this.accountNames.add(paramString);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.MultiBulkPreregistrationDetails
 * JD-Core Version:    0.7.0.1
 */