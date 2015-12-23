package com.google.android.finsky.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.support.v4.app.Fragment;
import com.google.android.finsky.activities.DetailsDataBasedFragment;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.DocV2;
import java.io.UnsupportedEncodingException;

public final class Nfc
{
  @TargetApi(10)
  private static abstract class BaseNfcHandler
    implements Nfc.NfcHandler
  {
    protected final DetailsDataBasedFragment mFragment;
    protected final NfcAdapter mNfcAdapter;
    
    private BaseNfcHandler(DetailsDataBasedFragment paramDetailsDataBasedFragment)
    {
      this.mFragment = paramDetailsDataBasedFragment;
      this.mNfcAdapter = NfcAdapter.getDefaultAdapter(paramDetailsDataBasedFragment.getActivity());
    }
    
    protected final NdefMessage createMessage()
    {
      Document localDocument = this.mFragment.mDocument;
      String str;
      if (localDocument != null)
      {
        str = localDocument.mDocument.shareUrl;
        try
        {
          byte[] arrayOfByte3 = str.getBytes("UTF-8");
          arrayOfByte1 = arrayOfByte3;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          for (;;)
          {
            byte[] arrayOfByte2;
            byte[] arrayOfByte1 = str.getBytes();
          }
        }
        arrayOfByte2 = new byte[1 + arrayOfByte1.length];
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 1, arrayOfByte1.length);
        return new NdefMessage(new NdefRecord[] { new NdefRecord(1, new byte[] { 85 }, new byte[0], arrayOfByte2) });
      }
      return null;
    }
  }
  
  @TargetApi(10)
  private static final class GingerbreadMr1NfcHandler
    extends Nfc.BaseNfcHandler
  {
    private boolean mEnabled = false;
    
    private GingerbreadMr1NfcHandler(DetailsDataBasedFragment paramDetailsDataBasedFragment)
    {
      super((byte)0);
    }
    
    private void setPushMessage()
    {
      if ((!this.mEnabled) && (this.mFragment.mResumed))
      {
        NdefMessage localNdefMessage = createMessage();
        if (localNdefMessage != null)
        {
          this.mNfcAdapter.enableForegroundNdefPush(this.mFragment.getActivity(), localNdefMessage);
          this.mEnabled = true;
        }
      }
    }
    
    public final void onDataChanged()
    {
      setPushMessage();
    }
    
    public final void onPause()
    {
      if (this.mEnabled)
      {
        this.mNfcAdapter.disableForegroundNdefPush(this.mFragment.getActivity());
        this.mEnabled = false;
      }
    }
    
    public final void onResume()
    {
      setPushMessage();
    }
  }
  
  @TargetApi(14)
  private static final class IcsNfcHandler
    extends Nfc.BaseNfcHandler
    implements NfcAdapter.CreateNdefMessageCallback
  {
    private IcsNfcHandler(DetailsDataBasedFragment paramDetailsDataBasedFragment)
    {
      super((byte)0);
    }
    
    private void setCallback(NfcAdapter.CreateNdefMessageCallback paramCreateNdefMessageCallback)
    {
      if (!this.mFragment.canChangeFragmentManagerState()) {}
      while (this.mNfcAdapter == null) {
        return;
      }
      this.mNfcAdapter.setNdefPushMessageCallback(paramCreateNdefMessageCallback, this.mFragment.getActivity(), new Activity[0]);
    }
    
    public final NdefMessage createNdefMessage(NfcEvent paramNfcEvent)
    {
      return createMessage();
    }
    
    public final void onDataChanged()
    {
      setCallback(this);
    }
    
    public final void onPause()
    {
      setCallback(null);
    }
    
    public final void onResume()
    {
      setCallback(this);
    }
  }
  
  public static abstract interface NfcHandler
  {
    public abstract void onDataChanged();
    
    public abstract void onPause();
    
    public abstract void onResume();
  }
  
  private static final class NoopNfcHandler
    implements Nfc.NfcHandler
  {
    public final void onDataChanged() {}
    
    public final void onPause() {}
    
    public final void onResume() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.Nfc
 * JD-Core Version:    0.7.0.1
 */