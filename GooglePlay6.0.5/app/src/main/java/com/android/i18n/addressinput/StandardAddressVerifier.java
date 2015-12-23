package com.android.i18n.addressinput;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StandardAddressVerifier
{
  private static final VerifierRefiner DEFAULT_REFINER = new VerifierRefiner();
  protected final Map<AddressField, List<AddressProblemType>> mProblemMap;
  protected final VerifierRefiner mRefiner;
  protected final FieldVerifier mRootVerifier;
  
  public StandardAddressVerifier(FieldVerifier paramFieldVerifier)
  {
    this(paramFieldVerifier, localVerifierRefiner);
  }
  
  private StandardAddressVerifier(FieldVerifier paramFieldVerifier, VerifierRefiner paramVerifierRefiner)
  {
    this.mRootVerifier = paramFieldVerifier;
    this.mRefiner = paramVerifierRefiner;
    this.mProblemMap = StandardChecks.PROBLEM_MAP;
  }
  
  protected static void postVerify$7ff6b0f8() {}
  
  protected final boolean verifyField(LookupKey.ScriptType paramScriptType, FieldVerifier paramFieldVerifier, AddressField paramAddressField, String paramString, AddressProblems paramAddressProblems)
  {
    List localList = (List)this.mProblemMap.get(paramAddressField);
    if (localList == null) {
      localList = Collections.emptyList();
    }
    Iterator localIterator = localList.iterator();
    if (localIterator.hasNext())
    {
      AddressProblemType localAddressProblemType = (AddressProblemType)localIterator.next();
      String str1 = Util.trimToNull(paramString);
      int i;
      switch (FieldVerifier.1.$SwitchMap$com$android$i18n$addressinput$AddressProblemType[localAddressProblemType.ordinal()])
      {
      default: 
        throw new RuntimeException("Unknown problem: " + localAddressProblemType);
      case 1: 
        i = 0;
        if (str1 != null)
        {
          boolean bool5 = paramFieldVerifier.mPossibleFields.contains(paramAddressField);
          i = 0;
          if (!bool5) {
            i = 1;
          }
        }
        label164:
        if (i != 0) {
          paramAddressProblems.mProblems.put(paramAddressField, localAddressProblemType);
        }
        if (i != 0) {
          break;
        }
      }
      for (int j = 1; j == 0; j = 0)
      {
        return false;
        boolean bool4 = paramFieldVerifier.mRequired.contains(paramAddressField);
        i = 0;
        if (!bool4) {
          break label164;
        }
        i = 0;
        if (str1 != null) {
          break label164;
        }
        i = 1;
        break label164;
        i = 0;
        if (str1 == null) {
          break label164;
        }
        String str2 = Util.trimToNull(str1);
        Util.checkNotNull(str2);
        boolean bool3;
        if (paramScriptType == null) {
          if ((paramFieldVerifier.mCandidateValues == null) || (paramFieldVerifier.mCandidateValues.containsKey(str2.toLowerCase()))) {
            bool3 = true;
          }
        }
        for (;;)
        {
          if (bool3) {
            break label461;
          }
          i = 1;
          break;
          bool3 = false;
          continue;
          if (paramScriptType == LookupKey.ScriptType.LATIN) {}
          HashSet localHashSet;
          for (String[] arrayOfString1 = paramFieldVerifier.mLatinNames;; arrayOfString1 = paramFieldVerifier.mLocalNames)
          {
            localHashSet = new HashSet();
            if (arrayOfString1 == null) {
              break;
            }
            int n = arrayOfString1.length;
            for (int i1 = 0; i1 < n; i1++) {
              localHashSet.add(arrayOfString1[i1].toLowerCase());
            }
          }
          if (paramFieldVerifier.mKeys != null)
          {
            String[] arrayOfString2 = paramFieldVerifier.mKeys;
            int k = arrayOfString2.length;
            for (int m = 0; m < k; m++) {
              localHashSet.add(arrayOfString2[m].toLowerCase());
            }
          }
          if ((localHashSet.size() == 0) || (str2 == null)) {
            bool3 = true;
          } else {
            bool3 = localHashSet.contains(str1.toLowerCase());
          }
        }
        label461:
        i = 0;
        break label164;
        i = 0;
        if (str1 == null) {
          break label164;
        }
        Pattern localPattern2 = paramFieldVerifier.mFormat;
        i = 0;
        if (localPattern2 == null) {
          break label164;
        }
        boolean bool2 = paramFieldVerifier.mFormat.matcher(str1).matches();
        i = 0;
        if (bool2) {
          break label164;
        }
        i = 1;
        break label164;
        i = 0;
        if (str1 == null) {
          break label164;
        }
        Pattern localPattern1 = paramFieldVerifier.mMatch;
        i = 0;
        if (localPattern1 == null) {
          break label164;
        }
        boolean bool1 = paramFieldVerifier.mMatch.matcher(str1).lookingAt();
        i = 0;
        if (bool1) {
          break label164;
        }
        i = 1;
        break label164;
      }
    }
    return true;
  }
  
  private final class Verifier
    implements Runnable
  {
    private AddressData mAddress;
    private DataLoadListener mListener;
    private AddressProblems mProblems;
    
    Verifier(AddressData paramAddressData, AddressProblems paramAddressProblems, DataLoadListener paramDataLoadListener)
    {
      this.mAddress = paramAddressData;
      this.mProblems = paramAddressProblems;
      this.mListener = paramDataLoadListener;
    }
    
    public final void run()
    {
      FieldVerifier localFieldVerifier = StandardAddressVerifier.this.mRootVerifier;
      String str1 = this.mAddress.mLanguageCode;
      LookupKey.ScriptType localScriptType = null;
      if (str1 != null) {
        if (!Util.isExplicitLatinScript(this.mAddress.mLanguageCode)) {
          break label388;
        }
      }
      label388:
      for (localScriptType = LookupKey.ScriptType.LATIN;; localScriptType = LookupKey.ScriptType.LOCAL)
      {
        StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.COUNTRY, this.mAddress.mPostalCountry, this.mProblems);
        if (this.mProblems.isEmpty())
        {
          localFieldVerifier = localFieldVerifier.refineVerifier(this.mAddress.mPostalCountry);
          StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.ADMIN_AREA, this.mAddress.mAdministrativeArea, this.mProblems);
          if (this.mProblems.isEmpty())
          {
            localFieldVerifier = localFieldVerifier.refineVerifier(this.mAddress.mAdministrativeArea);
            StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.LOCALITY, this.mAddress.mLocality, this.mProblems);
            if (this.mProblems.isEmpty())
            {
              localFieldVerifier = localFieldVerifier.refineVerifier(this.mAddress.mLocality);
              StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.DEPENDENT_LOCALITY, this.mAddress.mDependentLocality, this.mProblems);
              if (this.mProblems.isEmpty()) {
                localFieldVerifier = localFieldVerifier.refineVerifier(this.mAddress.mDependentLocality);
              }
            }
          }
        }
        String[] arrayOfString = new String[2];
        arrayOfString[0] = this.mAddress.mAddressLine1;
        arrayOfString[1] = this.mAddress.mAddressLine2;
        String str2 = Util.joinAndSkipNulls("\n", arrayOfString);
        StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.POSTAL_CODE, this.mAddress.mPostalCode, this.mProblems);
        StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.STREET_ADDRESS, str2, this.mProblems);
        StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.SORTING_CODE, this.mAddress.mSortingCode, this.mProblems);
        StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.ORGANIZATION, this.mAddress.mOrganization, this.mProblems);
        StandardAddressVerifier.this.verifyField(localScriptType, localFieldVerifier, AddressField.RECIPIENT, this.mAddress.mRecipient, this.mProblems);
        StandardAddressVerifier.postVerify$7ff6b0f8();
        this.mListener.dataLoadingEnd();
        return;
      }
    }
  }
  
  public static final class VerifierRefiner {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.StandardAddressVerifier
 * JD-Core Version:    0.7.0.1
 */