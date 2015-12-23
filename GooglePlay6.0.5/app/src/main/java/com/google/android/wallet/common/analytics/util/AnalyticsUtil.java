package com.google.android.wallet.common.analytics.util;

import android.util.Log;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.analytics.WebViewPageLoadEvent;
import com.google.android.wallet.analytics.events.WalletBackgroundEvent;
import com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventDispatcher;
import com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventListener;
import com.google.android.wallet.instrumentmanager.pub.analytics.events.InstrumentManagerClickEvent;
import com.google.android.wallet.instrumentmanager.pub.analytics.events.InstrumentManagerImpressionEvent;
import java.util.ArrayList;
import java.util.List;

public final class AnalyticsUtil
{
  private static void addChildrenToTreeWithInjection(WalletUiElement paramWalletUiElement, List<UiNode> paramList, int paramInt1, int paramInt2)
  {
    if (((paramList == null) || (paramList.isEmpty())) && ((paramInt2 == -1) || (paramWalletUiElement.elementId != paramInt1))) {
      return;
    }
    ArrayList localArrayList = new ArrayList();
    if ((paramInt2 != -1) && (paramWalletUiElement.elementId == paramInt1)) {
      localArrayList.add(new WalletUiElement(paramInt2));
    }
    if (paramList != null)
    {
      int i = 0;
      int j = paramList.size();
      while (i < j)
      {
        UiNode localUiNode = (UiNode)paramList.get(i);
        WalletUiElement localWalletUiElement = cloneElement(localUiNode.getUiElement());
        localArrayList.add(localWalletUiElement);
        addChildrenToTreeWithInjection(localWalletUiElement, localUiNode.getChildren(), paramInt1, paramInt2);
        i++;
      }
    }
    paramWalletUiElement.children = localArrayList;
  }
  
  private static WalletUiElement cloneElement(WalletUiElement paramWalletUiElement)
  {
    return new WalletUiElement(paramWalletUiElement.elementId, paramWalletUiElement.integratorLogToken);
  }
  
  public static void createAndSendClickEvent(UiNode paramUiNode, int paramInt)
  {
    createAndSendClickEvent(paramUiNode, -1, 1622);
  }
  
  public static void createAndSendClickEvent(UiNode paramUiNode, int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new WalletUiElement(paramInt2));
    if (paramInt1 != -1) {
      localArrayList.add(new WalletUiElement(paramInt1));
    }
    for (UiNode localUiNode = paramUiNode; localUiNode != null; localUiNode = localUiNode.getParentUiNode()) {
      localArrayList.add(localUiNode.getUiElement());
    }
    InstrumentManagerClickEvent localInstrumentManagerClickEvent = new InstrumentManagerClickEvent(localArrayList);
    if (InstrumentManagerAnalyticsEventDispatcher.sListener != null) {
      InstrumentManagerAnalyticsEventDispatcher.sListener.onClickEvent(localInstrumentManagerClickEvent);
    }
    while (!Log.isLoggable("ImAnalyticsDispatcher", 3)) {
      return;
    }
    Log.d("ImAnalyticsDispatcher", "No listener found for sending click event from the clicked element " + ((WalletUiElement)localInstrumentManagerClickEvent.clickPath.get(0)).elementId);
  }
  
  public static void createAndSendImpressionEvent(UiNode paramUiNode, int paramInt)
  {
    for (UiNode localUiNode = paramUiNode; localUiNode.getParentUiNode() != null; localUiNode = localUiNode.getParentUiNode()) {}
    WalletUiElement localWalletUiElement = cloneElement(localUiNode.getUiElement());
    addChildrenToTreeWithInjection(localWalletUiElement, localUiNode.getChildren(), paramUiNode.getUiElement().elementId, paramInt);
    InstrumentManagerImpressionEvent localInstrumentManagerImpressionEvent = new InstrumentManagerImpressionEvent(localWalletUiElement);
    if (InstrumentManagerAnalyticsEventDispatcher.sListener != null) {
      InstrumentManagerAnalyticsEventDispatcher.sListener.onImpressionEvent(localInstrumentManagerImpressionEvent);
    }
    while (!Log.isLoggable("ImAnalyticsDispatcher", 3)) {
      return;
    }
    Log.d("ImAnalyticsDispatcher", "No listener found for sending the following impression event " + localInstrumentManagerImpressionEvent.toString());
  }
  
  public static void createAndSendRequestSentBackgroundEvent(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    InstrumentManagerAnalyticsEventDispatcher.sendBackgroundEvent(new WalletBackgroundEvent(paramInt1, 0, null, -1L, -1L, paramInt2, paramArrayOfByte));
  }
  
  public static void createAndSendResponseReceivedBackgroundEvent(int paramInt1, int paramInt2, String paramString, long paramLong1, long paramLong2, byte[] paramArrayOfByte)
  {
    InstrumentManagerAnalyticsEventDispatcher.sendBackgroundEvent(new WalletBackgroundEvent(paramInt1, paramInt2, paramString, paramLong1, paramLong2, -1, paramArrayOfByte));
  }
  
  public static void createAndSendResponseReceivedBackgroundEvent(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    createAndSendResponseReceivedBackgroundEvent(paramInt1, paramInt2, null, -1L, -1L, paramArrayOfByte);
  }
  
  public static void createAndSendWebViewPageLoadBackgroundEvent(int paramInt, WebViewPageLoadEvent paramWebViewPageLoadEvent, byte[] paramArrayOfByte)
  {
    InstrumentManagerAnalyticsEventDispatcher.sendBackgroundEvent(new WalletBackgroundEvent(paramInt, paramWebViewPageLoadEvent, paramArrayOfByte));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.analytics.util.AnalyticsUtil
 * JD-Core Version:    0.7.0.1
 */