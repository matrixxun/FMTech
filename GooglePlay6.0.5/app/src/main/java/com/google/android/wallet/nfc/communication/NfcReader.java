package com.google.android.wallet.nfc.communication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.IsoDep;
import android.os.SystemClock;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.util.DataTypeConversionUtil;
import com.google.android.wallet.config.G;
import com.google.android.wallet.nfc.CreditCardNfcResult;
import com.google.android.wallet.nfc.exceptions.TagReadException;
import com.google.android.wallet.nfc.exceptions.TagReadTimeoutException;
import com.google.android.wallet.nfc.exceptions.UnsupportedTagException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class NfcReader
{
  private static final int NFC_TIMEOUT_MS = ((Integer)G.ccNfcReadingTimeoutMs.get()).intValue();
  
  @TargetApi(10)
  private static CreditCardNfcResult aidRead(IsoDep paramIsoDep, String paramString)
    throws IOException, TagReadTimeoutException
  {
    ResponseApdu localResponseApdu = new ResponseApdu(transceive(paramIsoDep, ApduUtil.buildSelectCommand(paramString).command));
    label393:
    if (ApduUtil.matchesStatus(localResponseApdu, ResponseApdu.SUCCESS_SW))
    {
      TlvDatum localTlvDatum1 = TlvParser.parseTlv(localResponseApdu);
      TlvDatum localTlvDatum2 = TlvUtil.findNestedTlv(localTlvDatum1, TlvUtil.PDOL_TAG);
      TlvDatum localTlvDatum3 = TlvUtil.findNestedTlv(localTlvDatum1, TlvUtil.APPLICATION_LABEL_TAG);
      byte[] arrayOfByte = TlvUtil.getAflValue(new ResponseApdu(transceive(paramIsoDep, ApduUtil.buildGpoCommand(TlvUtil.buildPdolData(localTlvDatum2), TlvUtil.getValue(localTlvDatum3).toLowerCase().startsWith("visacard")).command)));
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      int j = arrayOfByte.length;
      while (i < j)
      {
        byte b6 = (byte)(DataTypeConversionUtil.unsignedByteToInt(arrayOfByte[i]) >> 3);
        byte b7 = arrayOfByte[(i + 1)];
        byte b8 = arrayOfByte[(i + 2)];
        byte b9 = arrayOfByte[(i + 3)];
        Afl localAfl2 = new Afl(b7, b8, b6, b9);
        localArrayList.add(localAfl2);
        i += 4;
      }
      int k = localArrayList.size();
      int m = 0;
      byte b5;
      label229:
      CreditCardNfcResult localCreditCardNfcResult;
      if (m < k)
      {
        Afl localAfl1 = (Afl)localArrayList.get(m);
        b5 = localAfl1.startRecord;
        if (b5 <= localAfl1.endRecord)
        {
          localCreditCardNfcResult = readRecord(paramIsoDep, b5, localAfl1.sfi);
          if (localCreditCardNfcResult == null) {}
        }
      }
      for (;;)
      {
        if (localCreditCardNfcResult == null) {
          break label287;
        }
        return localCreditCardNfcResult;
        b5 = (byte)(b5 + 1);
        break label229;
        m++;
        break;
        localCreditCardNfcResult = null;
      }
      label287:
      TlvDatum localTlvDatum4 = TlvUtil.findNestedTlv(localTlvDatum1, TlvUtil.SFI_TAG);
      if (localTlvDatum4 != null)
      {
        byte b3 = (byte)(localTlvDatum4.value[0] >> 3);
        for (byte b4 = 1;; b4 = (byte)(b4 + 1))
        {
          if (b4 > 16) {
            break label348;
          }
          localCreditCardNfcResult = readRecord(paramIsoDep, b4, b3);
          if (localCreditCardNfcResult != null) {
            break;
          }
        }
      }
      label348:
      for (byte b1 = 1;; b1 = (byte)(b1 + 1))
      {
        if (b1 > 31) {
          break label403;
        }
        for (byte b2 = 1;; b2 = (byte)(b2 + 1))
        {
          if (b2 > 16) {
            break label393;
          }
          localCreditCardNfcResult = readRecord(paramIsoDep, b2, b1);
          if (localCreditCardNfcResult != null) {
            break;
          }
        }
      }
    }
    label403:
    return null;
  }
  
  @TargetApi(10)
  public static CreditCardNfcResult readIntent(Intent paramIntent)
    throws IOException, UnsupportedTagException, TagReadTimeoutException, TagReadException
  {
    IsoDep localIsoDep = IsoDep.get((Tag)paramIntent.getParcelableExtra("android.nfc.extra.TAG"));
    if (localIsoDep == null) {
      throw new UnsupportedTagException();
    }
    try
    {
      localIsoDep.connect();
      localIsoDep.setTimeout(NFC_TIMEOUT_MS);
      String[] arrayOfString1 = ApduUtil.SUPPORTED_PSE_AIDS;
      int i = arrayOfString1.length;
      for (int j = 0; j < i; j++)
      {
        ResponseApdu localResponseApdu = new ResponseApdu(transceive(localIsoDep, ApduUtil.buildSelectCommand(arrayOfString1[j]).command));
        CreditCardNfcResult localCreditCardNfcResult2;
        if (ApduUtil.matchesStatus(localResponseApdu, ResponseApdu.SUCCESS_SW))
        {
          TlvDatum localTlvDatum = TlvUtil.findNestedTlv(TlvParser.parseTlv(localResponseApdu), TlvUtil.APPLICATION_IDENTIFIER_TAG);
          if (localTlvDatum != null) {
            localCreditCardNfcResult2 = aidRead(localIsoDep, TlvUtil.getValue(localTlvDatum));
          }
        }
        for (CreditCardNfcResult localCreditCardNfcResult1 = localCreditCardNfcResult2; localCreditCardNfcResult1 != null; localCreditCardNfcResult1 = null) {
          return localCreditCardNfcResult1;
        }
      }
      String[] arrayOfString2 = ApduUtil.SUPPORTED_AIDS;
      int k = arrayOfString2.length;
      for (int m = 0; m < k; m++)
      {
        CreditCardNfcResult localCreditCardNfcResult3 = aidRead(localIsoDep, arrayOfString2[m]);
        if (localCreditCardNfcResult3 != null) {
          return localCreditCardNfcResult3;
        }
      }
      throw new TagReadException();
    }
    finally
    {
      localIsoDep.close();
    }
  }
  
  @TargetApi(10)
  private static CreditCardNfcResult readRecord(IsoDep paramIsoDep, byte paramByte1, byte paramByte2)
    throws IOException, TagReadTimeoutException
  {
    ResponseApdu localResponseApdu = new ResponseApdu(transceive(paramIsoDep, ApduUtil.buildReadCommand(paramByte1, paramByte2).command));
    if (ApduUtil.matchesStatus(localResponseApdu, ResponseApdu.WRONG_LENGTH_LE)) {
      localResponseApdu = new ResponseApdu(transceive(paramIsoDep, ApduUtil.buildReadCommand(paramByte1, paramByte2, localResponseApdu.statusWord2).command));
    }
    if (ApduUtil.matchesStatus(localResponseApdu, ResponseApdu.SUCCESS_SW))
    {
      CreditCardNfcResult localCreditCardNfcResult = TlvUtil.parseCreditCardInfoFromTlv(TlvParser.parseTlv(localResponseApdu));
      if (localCreditCardNfcResult != null) {
        return localCreditCardNfcResult;
      }
    }
    return null;
  }
  
  @TargetApi(10)
  private static byte[] transceive(IsoDep paramIsoDep, byte[] paramArrayOfByte)
    throws IOException, TagReadTimeoutException
  {
    long l = SystemClock.elapsedRealtime();
    try
    {
      byte[] arrayOfByte = paramIsoDep.transceive(paramArrayOfByte);
      return arrayOfByte;
    }
    catch (TagLostException localTagLostException)
    {
      if (SystemClock.elapsedRealtime() - l >= NFC_TIMEOUT_MS) {
        throw new TagReadTimeoutException(localTagLostException);
      }
      throw localTagLostException;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.NfcReader
 * JD-Core Version:    0.7.0.1
 */