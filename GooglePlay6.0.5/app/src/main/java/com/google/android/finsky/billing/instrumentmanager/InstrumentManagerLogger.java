package com.google.android.finsky.billing.instrumentmanager;

import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.CreditCardEntryAction;
import com.google.android.finsky.analytics.PlayStore.PlayStoreClickEvent;
import com.google.android.finsky.analytics.PlayStore.PlayStoreImpressionEvent;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.analytics.PlayStore.WebViewPageLoadEvent;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.utils.Lists;
import com.google.android.wallet.analytics.CreditCardEntryAction;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.analytics.WebViewPageLoadEvent;
import com.google.android.wallet.analytics.events.WalletBackgroundEvent;
import com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventListener;
import com.google.android.wallet.instrumentmanager.pub.analytics.events.InstrumentManagerClickEvent;
import com.google.android.wallet.instrumentmanager.pub.analytics.events.InstrumentManagerImpressionEvent;
import java.util.ArrayList;
import java.util.List;

public final class InstrumentManagerLogger
  implements InstrumentManagerAnalyticsEventListener
{
  private final FinskyEventLog mLogger;
  private final PlayStoreUiElementNode mNode;
  
  public InstrumentManagerLogger(PlayStoreUiElementNode paramPlayStoreUiElementNode, FinskyEventLog paramFinskyEventLog)
  {
    this.mNode = paramPlayStoreUiElementNode;
    this.mLogger = paramFinskyEventLog;
    if (paramPlayStoreUiElementNode == null) {
      throw new IllegalArgumentException("node cannot be null");
    }
  }
  
  private PlayStore.PlayStoreUiElement convertUiElement(WalletUiElement paramWalletUiElement)
  {
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = createPlayStoreUiElement(paramWalletUiElement);
    if ((paramWalletUiElement.children != null) && (!paramWalletUiElement.children.isEmpty()))
    {
      int i = paramWalletUiElement.children.size();
      localPlayStoreUiElement.child = new PlayStore.PlayStoreUiElement[i];
      for (int j = 0; j < i; j++) {
        localPlayStoreUiElement.child[j] = convertUiElement((WalletUiElement)paramWalletUiElement.children.get(j));
      }
    }
    return localPlayStoreUiElement;
  }
  
  private static PlayStore.PlayStoreUiElement createPlayStoreUiElement(WalletUiElement paramWalletUiElement)
  {
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(paramWalletUiElement.elementId);
    if ((paramWalletUiElement.integratorLogToken != null) && (paramWalletUiElement.integratorLogToken.length > 0))
    {
      localPlayStoreUiElement.serverLogsCookie = paramWalletUiElement.integratorLogToken;
      localPlayStoreUiElement.hasServerLogsCookie = true;
    }
    return localPlayStoreUiElement;
  }
  
  public final void onBackgroundEvent(WalletBackgroundEvent paramWalletBackgroundEvent)
  {
    FinskyEventLog localFinskyEventLog = this.mLogger;
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(paramWalletBackgroundEvent.backgroundEventType).setExceptionType(paramWalletBackgroundEvent.exceptionType).setServerLatencyMs(paramWalletBackgroundEvent.serverLatencyMs).setClientLatencyMs(paramWalletBackgroundEvent.clientLatencyMs).setErrorCode(paramWalletBackgroundEvent.resultCode);
    if (paramWalletBackgroundEvent.attempts > 0) {
      localBackgroundEventBuilder.setAttempts(paramWalletBackgroundEvent.attempts);
    }
    if ((paramWalletBackgroundEvent.integratorLogToken != null) && (paramWalletBackgroundEvent.integratorLogToken.length > 0)) {
      localBackgroundEventBuilder.setServerLogsCookie(paramWalletBackgroundEvent.integratorLogToken);
    }
    CreditCardEntryAction localCreditCardEntryAction = paramWalletBackgroundEvent.creditCardEntryAction;
    if (localCreditCardEntryAction != null)
    {
      PlayStore.CreditCardEntryAction localCreditCardEntryAction1 = new PlayStore.CreditCardEntryAction();
      localCreditCardEntryAction1.panOcrEnabled = localCreditCardEntryAction.panOcrEnabled;
      localCreditCardEntryAction1.hasPanOcrEnabled = true;
      localCreditCardEntryAction1.panEntryType = localCreditCardEntryAction.panEntryType;
      localCreditCardEntryAction1.hasPanEntryType = true;
      localCreditCardEntryAction1.panRecognizedByOcr = localCreditCardEntryAction.panRecognizedByOcr;
      localCreditCardEntryAction1.hasPanRecognizedByOcr = true;
      localCreditCardEntryAction1.panValidationErrorOccurred = localCreditCardEntryAction.panValidationErrorOccurred;
      localCreditCardEntryAction1.hasPanValidationErrorOccurred = true;
      localCreditCardEntryAction1.panRecognizedByNfc = localCreditCardEntryAction.panRecognizedByNfc;
      localCreditCardEntryAction1.hasPanRecognizedByNfc = true;
      localCreditCardEntryAction1.expDateOcrEnabled = localCreditCardEntryAction.expDateOcrEnabled;
      localCreditCardEntryAction1.hasExpDateOcrEnabled = true;
      localCreditCardEntryAction1.expDateEntryType = localCreditCardEntryAction.expDateEntryType;
      localCreditCardEntryAction1.hasExpDateEntryType = true;
      localCreditCardEntryAction1.expDateRecognizedByOcr = localCreditCardEntryAction.expDateRecognizedByOcr;
      localCreditCardEntryAction1.hasExpDateRecognizedByOcr = true;
      localCreditCardEntryAction1.expDateValidationErrorOccurred = localCreditCardEntryAction.expDateValidationErrorOccurred;
      localCreditCardEntryAction1.hasExpDateValidationErrorOccurred = true;
      localCreditCardEntryAction1.expDateRecognizedByNfc = localCreditCardEntryAction.expDateRecognizedByNfc;
      localCreditCardEntryAction1.hasExpDateRecognizedByNfc = true;
      localCreditCardEntryAction1.nameOcrEnabled = localCreditCardEntryAction.nameOcrEnabled;
      localCreditCardEntryAction1.hasNameOcrEnabled = true;
      localCreditCardEntryAction1.nameEntryType = localCreditCardEntryAction.nameEntryType;
      localCreditCardEntryAction1.hasNameEntryType = true;
      localCreditCardEntryAction1.nameRecognizedByOcr = localCreditCardEntryAction.nameRecognizedByOcr;
      localCreditCardEntryAction1.hasNameRecognizedByOcr = true;
      localCreditCardEntryAction1.nameValidationErrorOccurred = localCreditCardEntryAction.nameValidationErrorOccurred;
      localCreditCardEntryAction1.hasNameValidationErrorOccurred = true;
      localCreditCardEntryAction1.nameRecognizedByNfc = localCreditCardEntryAction.nameRecognizedByNfc;
      localCreditCardEntryAction1.hasNameRecognizedByNfc = true;
      localCreditCardEntryAction1.nfcElapsedTimeMillis = localCreditCardEntryAction.nfcElapsedTimeMillis;
      localCreditCardEntryAction1.hasNfcElapsedTimeMillis = true;
      localCreditCardEntryAction1.nfcFeatureEnabled = localCreditCardEntryAction.nfcFeatureEnabled;
      localCreditCardEntryAction1.hasNfcFeatureEnabled = true;
      localCreditCardEntryAction1.nfcAdapterEnabled = localCreditCardEntryAction.nfcAdapterEnabled;
      localCreditCardEntryAction1.hasNfcAdapterEnabled = true;
      localCreditCardEntryAction1.numOcrAttempts = localCreditCardEntryAction.numOcrAttempts;
      localCreditCardEntryAction1.hasNumOcrAttempts = true;
      localCreditCardEntryAction1.numNfcAttempts = localCreditCardEntryAction.numNfcAttempts;
      localCreditCardEntryAction1.hasNumNfcAttempts = true;
      localCreditCardEntryAction1.ocrExitReason = localCreditCardEntryAction.ocrExitReason;
      localCreditCardEntryAction1.hasOcrExitReason = true;
      localCreditCardEntryAction1.nfcExitReason = localCreditCardEntryAction.nfcExitReason;
      localCreditCardEntryAction1.hasNfcExitReason = true;
      localCreditCardEntryAction1.nfcErrorReason = localCreditCardEntryAction.nfcErrorReason;
      localCreditCardEntryAction1.hasNfcErrorReason = true;
      localCreditCardEntryAction1.cameraInputPreference = localCreditCardEntryAction.cameraInputPreference;
      localCreditCardEntryAction1.hasCameraInputPreference = true;
      localCreditCardEntryAction1.nfcInputPreference = localCreditCardEntryAction.nfcInputPreference;
      localCreditCardEntryAction1.hasNfcInputPreference = true;
      localBackgroundEventBuilder.event.creditCardEntryAction = localCreditCardEntryAction1;
    }
    WebViewPageLoadEvent localWebViewPageLoadEvent = paramWalletBackgroundEvent.webViewPageLoadEvent;
    if (localWebViewPageLoadEvent != null)
    {
      PlayStore.WebViewPageLoadEvent localWebViewPageLoadEvent1 = new PlayStore.WebViewPageLoadEvent();
      localWebViewPageLoadEvent1.url = localWebViewPageLoadEvent.url;
      localWebViewPageLoadEvent1.hasUrl = true;
      localWebViewPageLoadEvent1.startTimestampMs = localWebViewPageLoadEvent.startTimestampMs;
      localWebViewPageLoadEvent1.hasStartTimestampMs = true;
      localWebViewPageLoadEvent1.errorCode = localWebViewPageLoadEvent.errorCode;
      localWebViewPageLoadEvent1.hasErrorCode = true;
      localBackgroundEventBuilder.event.webViewPageLoadEvent = localWebViewPageLoadEvent1;
    }
    localFinskyEventLog.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  public final void onClickEvent(InstrumentManagerClickEvent paramInstrumentManagerClickEvent)
  {
    FinskyEventLog localFinskyEventLog = this.mLogger;
    int i = paramInstrumentManagerClickEvent.clickPath.size();
    if (i == 0) {
      throw new IllegalArgumentException("Click path must have at least one item");
    }
    ArrayList localArrayList = Lists.newArrayList(i + 1);
    for (int j = 0; j < i; j++) {
      localArrayList.add(createPlayStoreUiElement((WalletUiElement)paramInstrumentManagerClickEvent.clickPath.get(j)));
    }
    PlayStoreUiElementNode localPlayStoreUiElementNode = this.mNode;
    do
    {
      localArrayList.add(FinskyEventLog.cloneElement(localPlayStoreUiElementNode.getPlayStoreUiElement()));
      localPlayStoreUiElementNode = localPlayStoreUiElementNode.getParentNode();
    } while (localPlayStoreUiElementNode != null);
    PlayStore.PlayStoreClickEvent localPlayStoreClickEvent = FinskyEventLog.obtainPlayStoreClickEvent();
    localPlayStoreClickEvent.elementPath = ((PlayStore.PlayStoreUiElement[])localArrayList.toArray(new PlayStore.PlayStoreUiElement[localArrayList.size()]));
    localFinskyEventLog.logClickEvent(localPlayStoreClickEvent);
  }
  
  public final void onImpressionEvent(InstrumentManagerImpressionEvent paramInstrumentManagerImpressionEvent)
  {
    FinskyEventLog localFinskyEventLog = this.mLogger;
    ArrayList localArrayList = new ArrayList();
    for (PlayStoreUiElementNode localPlayStoreUiElementNode = this.mNode; localPlayStoreUiElementNode != null; localPlayStoreUiElementNode = localPlayStoreUiElementNode.getParentNode()) {
      localArrayList.add(localPlayStoreUiElementNode.getPlayStoreUiElement());
    }
    PlayStore.PlayStoreUiElement localPlayStoreUiElement1 = FinskyEventLog.pathToTree(localArrayList);
    for (PlayStore.PlayStoreUiElement localPlayStoreUiElement2 = localPlayStoreUiElement1; (localPlayStoreUiElement2.child != null) && (localPlayStoreUiElement2.child.length != 0); localPlayStoreUiElement2 = localPlayStoreUiElement2.child[0]) {}
    if (localPlayStoreUiElement2.type != this.mNode.getPlayStoreUiElement().type) {
      throw new IllegalStateException("Unexpected types in tree: " + localPlayStoreUiElement2.type + " and " + this.mNode.getPlayStoreUiElement().type);
    }
    localPlayStoreUiElement2.child = new PlayStore.PlayStoreUiElement[] { convertUiElement(paramInstrumentManagerImpressionEvent.impressionTree) };
    PlayStore.PlayStoreImpressionEvent localPlayStoreImpressionEvent = FinskyEventLog.obtainPlayStoreImpressionEvent();
    localPlayStoreImpressionEvent.tree = localPlayStoreUiElement1;
    localFinskyEventLog.logImpressionEvent(localPlayStoreImpressionEvent);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.instrumentmanager.InstrumentManagerLogger
 * JD-Core Version:    0.7.0.1
 */