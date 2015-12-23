package com.google.android.wallet.common.address;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.Data;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.util.PermissionDelegate;
import com.google.android.wallet.config.G;
import com.google.location.country.Postaladdress.PostalAddress;
import java.util.ArrayList;
import java.util.regex.Pattern;

public final class DeviceAddressSource
  extends InMemoryAddressSource
{
  private static final Pattern STREET_SEPARATORS = Pattern.compile("[\\r\\n]");
  private final Activity mActivity;
  
  public DeviceAddressSource(Activity paramActivity)
  {
    super("DeviceAddressSource", paramActivity);
    this.mActivity = paramActivity;
  }
  
  private static int checkRemainingSizeAllowance(int paramInt1, int paramInt2)
    throws OutOfMemoryError
  {
    int i = paramInt1 - paramInt2;
    if (i < 0) {
      throw new OutOfMemoryError("Device data exceeds allowed storage for source");
    }
    return i;
  }
  
  private ArrayList<Postaladdress.PostalAddress> getAddresses()
    throws Throwable
  {
    Object localObject2;
    if (!PermissionDelegate.callerAndSelfHavePermission(this.mActivity, "android.permission.READ_CONTACTS"))
    {
      localObject2 = null;
      return localObject2;
    }
    int i = 1024 * (1024 * (((ActivityManager)this.mActivity.getSystemService("activity")).getMemoryClass() / 16));
    if (i == 0) {
      i = 1048576;
    }
    ContentResolver localContentResolver = this.mActivity.getContentResolver();
    String[] arrayOfString1 = { "contact_id", "data1" };
    Cursor localCursor1 = localContentResolver.query(ContactsContract.Data.CONTENT_URI, arrayOfString1, "in_visible_group=1 AND mimetype=?", new String[] { "vnd.android.cursor.item/name" }, "contact_id");
    SparseArray localSparseArray;
    try
    {
      localSparseArray = new SparseArray(localCursor1.getCount());
      if (localCursor1.getCount() > 0)
      {
        int i12 = localCursor1.getColumnIndexOrThrow("contact_id");
        int i13 = localCursor1.getColumnIndexOrThrow("data1");
        while (localCursor1.moveToNext())
        {
          int i14 = localCursor1.getInt(i12);
          String str10 = localCursor1.getString(i13);
          if (!TextUtils.isEmpty(str10)) {
            localSparseArray.append(i14, str10);
          }
        }
      }
    }
    finally
    {
      localCursor1.close();
    }
    String[] arrayOfString2 = { "contact_id", "data4", "data7", "data6", "data8", "data9", "data10", "data5" };
    Cursor localCursor2 = localContentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, arrayOfString2, "in_visible_group=1", null, null);
    for (;;)
    {
      SparseBooleanArray localSparseBooleanArray;
      int i10;
      try
      {
        localObject2 = new ArrayList(localCursor2.getCount());
        localSparseBooleanArray = new SparseBooleanArray(localSparseArray.size());
        if (localCursor2.getCount() > 0)
        {
          int n = localCursor2.getColumnIndexOrThrow("contact_id");
          int i1 = localCursor2.getColumnIndexOrThrow("data4");
          int i2 = localCursor2.getColumnIndexOrThrow("data7");
          int i3 = localCursor2.getColumnIndexOrThrow("data6");
          int i4 = localCursor2.getColumnIndexOrThrow("data8");
          int i5 = localCursor2.getColumnIndexOrThrow("data9");
          int i6 = localCursor2.getColumnIndexOrThrow("data10");
          int i7 = localCursor2.getColumnIndexOrThrow("data5");
          if (localCursor2.moveToNext())
          {
            Postaladdress.PostalAddress localPostalAddress2 = new Postaladdress.PostalAddress();
            int i8 = localCursor2.getInt(n);
            String str2 = trim((String)localSparseArray.get(i8));
            if (!TextUtils.isEmpty(str2)) {
              localPostalAddress2.recipientName = str2;
            }
            if (!localCursor2.isNull(i1))
            {
              String[] arrayOfString3 = STREET_SEPARATORS.split(localCursor2.getString(i1));
              ArrayList localArrayList = new ArrayList(arrayOfString3.length);
              i10 = 0;
              int i11 = arrayOfString3.length;
              if (i10 < i11)
              {
                String str9 = trim(arrayOfString3[i10]);
                if (TextUtils.isEmpty(str9)) {
                  break label889;
                }
                localArrayList.add(str9);
                break label889;
              }
              localPostalAddress2.addressLine = ((String[])localArrayList.toArray(new String[localArrayList.size()]));
            }
            String str3 = trim(localCursor2.getString(i2));
            if (!TextUtils.isEmpty(str3)) {
              localPostalAddress2.localityName = str3;
            }
            String str4 = trim(localCursor2.getString(i3));
            if (!TextUtils.isEmpty(str4)) {
              localPostalAddress2.dependentLocalityName = str4;
            }
            String str5 = trim(localCursor2.getString(i4));
            if (!TextUtils.isEmpty(str5)) {
              localPostalAddress2.administrativeAreaName = str5;
            }
            String str6 = trim(localCursor2.getString(i5));
            if (!TextUtils.isEmpty(str6)) {
              localPostalAddress2.postalCodeNumber = str6;
            }
            String str7 = trim(localCursor2.getString(i6));
            if (!TextUtils.isEmpty(str7)) {
              localPostalAddress2.countryNameCode = str7;
            }
            String str8 = trim(localCursor2.getString(i7));
            if (!TextUtils.isEmpty(str8)) {
              localPostalAddress2.postBoxNumber = str8;
            }
            int i9 = sizeOfPostalAddress(localPostalAddress2);
            i = checkRemainingSizeAllowance(i, i9);
            ((ArrayList)localObject2).add(localPostalAddress2);
            localSparseBooleanArray.put(i8, true);
            continue;
          }
        }
      }
      finally
      {
        localCursor2.close();
      }
      int j = 0;
      int k = localSparseArray.size();
      while (j < k)
      {
        if (!localSparseBooleanArray.get(localSparseArray.keyAt(j)))
        {
          String str1 = (String)localSparseArray.valueAt(j);
          Postaladdress.PostalAddress localPostalAddress1 = new Postaladdress.PostalAddress();
          localPostalAddress1.recipientName = str1;
          int m = sizeOfPostalAddress(localPostalAddress1);
          i = checkRemainingSizeAllowance(i, m);
          ((ArrayList)localObject2).add(localPostalAddress1);
        }
        j++;
      }
      break;
      label889:
      i10++;
    }
  }
  
  private static int sizeOfPostalAddress(Postaladdress.PostalAddress paramPostalAddress)
  {
    return 8 * ((45 + 2 * paramPostalAddress.getCachedSize()) / 8);
  }
  
  private static String trim(String paramString)
  {
    if (paramString != null) {
      return paramString.trim();
    }
    return null;
  }
  
  protected final int getThresholdForField$132f9b()
  {
    return ((Integer)G.deviceAddressSourceThresholdDefault.get()).intValue();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.DeviceAddressSource
 * JD-Core Version:    0.7.0.1
 */