package com.google.android.wallet.common.util;

import android.util.Log;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public final class NetUtils
{
  public static ArrayList<InetAddress> getNonLoopbackInetAddresses()
  {
    localArrayList = new ArrayList();
    try
    {
      Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
      while (localEnumeration1.hasMoreElements())
      {
        Enumeration localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
        while (localEnumeration2.hasMoreElements())
        {
          InetAddress localInetAddress = (InetAddress)localEnumeration2.nextElement();
          if (!localInetAddress.isLoopbackAddress()) {
            localArrayList.add(localInetAddress);
          }
        }
      }
      return localArrayList;
    }
    catch (SocketException localSocketException)
    {
      Log.e("NetUtils", "Unable to retrieve network interfaces", localSocketException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.NetUtils
 * JD-Core Version:    0.7.0.1
 */