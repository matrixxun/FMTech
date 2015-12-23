package com.google.android.play.search;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.play.R.id;
import com.google.android.play.R.string;

public class PlaySearchPlateTextContainer
  extends FrameLayout
  implements TextWatcher, TextView.OnEditorActionListener, PlaySearchListener
{
  private PlaySearchController mController;
  private final InputMethodManager mInputManager;
  private TextView mSearchBoxActiveTextView;
  private ImageView mSearchBoxIdleText;
  EditText mSearchBoxTextInput;
  private boolean mSuspendTextChangeCallback;
  private PlaySearchVoiceController mVoiceController;
  
  public PlaySearchPlateTextContainer(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlaySearchPlateTextContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlaySearchPlateTextContainer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mInputManager = ((InputMethodManager)paramContext.getSystemService("input_method"));
  }
  
  private void setText(String paramString)
  {
    if ((this.mController.mCurrentSearchMode != 3) || (this.mSearchBoxTextInput.getText().toString().equals(paramString))) {
      return;
    }
    this.mSuspendTextChangeCallback = true;
    this.mSearchBoxTextInput.setText(paramString);
    this.mSearchBoxTextInput.setSelection(paramString.length());
    this.mSuspendTextChangeCallback = false;
  }
  
  private void triggerSearch()
  {
    if (TextUtils.getTrimmedLength(this.mController.mCurrentQuery) > 0) {
      this.mController.onSearch();
    }
  }
  
  public void afterTextChanged(Editable paramEditable) {}
  
  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mVoiceController != null) {
      this.mVoiceController.cancelPendingVoiceSearch(getContext());
    }
  }
  
  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 3)
    {
      triggerSearch();
      return true;
    }
    if (paramKeyEvent != null)
    {
      int i = paramKeyEvent.getKeyCode();
      if ((i == 66) || (i == 160) || (i == 84)) {}
      for (int j = 1;; j = 0)
      {
        if (j == 0) {
          break label71;
        }
        if (paramKeyEvent.getAction() != 1) {
          break;
        }
        triggerSearch();
        return true;
      }
    }
    label71:
    return false;
  }
  
  public void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSearchBoxIdleText = ((ImageView)findViewById(R.id.search_box_idle_text));
    this.mSearchBoxActiveTextView = ((TextView)findViewById(R.id.search_box_active_text_view));
    this.mSearchBoxTextInput = ((EditText)findViewById(R.id.search_box_text_input));
    View.OnClickListener local1 = new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (PlaySearchPlateTextContainer.this.mController != null) {
          PlaySearchPlateTextContainer.this.mController.setMode(3);
        }
      }
    };
    this.mSearchBoxIdleText.setOnClickListener(local1);
    this.mSearchBoxActiveTextView.setOnClickListener(local1);
  }
  
  public final void onModeChanged(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return;
    case 1: 
      this.mSearchBoxIdleText.setVisibility(0);
      this.mSearchBoxActiveTextView.setVisibility(8);
      this.mSearchBoxTextInput.setVisibility(8);
      this.mSearchBoxTextInput.setOnEditorActionListener(null);
      this.mSearchBoxTextInput.setOnClickListener(null);
    }
    for (;;)
    {
      this.mInputManager.hideSoftInputFromWindow(this.mSearchBoxTextInput.getWindowToken(), 0);
      this.mSearchBoxTextInput.removeTextChangedListener(this);
      return;
      this.mSearchBoxIdleText.setVisibility(8);
      this.mSearchBoxActiveTextView.setVisibility(0);
      this.mSearchBoxTextInput.setVisibility(8);
      this.mSearchBoxTextInput.setOnEditorActionListener(null);
      continue;
      this.mSearchBoxIdleText.setVisibility(8);
      this.mSearchBoxActiveTextView.setVisibility(8);
      this.mSearchBoxTextInput.setVisibility(0);
      this.mSearchBoxTextInput.addTextChangedListener(this);
      this.mSearchBoxTextInput.setOnEditorActionListener(this);
      this.mSearchBoxTextInput.setOnClickListener(null);
      this.mSearchBoxTextInput.requestFocus();
      this.mInputManager.showSoftInput(this.mSearchBoxTextInput, 0);
      if (this.mController != null) {
        this.mController.notifyQueryChangeInternal(true);
      }
      setText(this.mController.mCurrentQuery);
      return;
      if (this.mController != null) {
        this.mController.setQueryInternal("", true);
      }
      if ((this.mController != null) && (this.mVoiceController != null))
      {
        PlaySearchVoiceController localPlaySearchVoiceController = this.mVoiceController;
        Context localContext = getContext();
        Intent localIntent1 = new Intent("com.google.android.play.search.VOICE_SEARCH_RESULT");
        localIntent1.setPackage(localContext.getPackageName());
        PendingIntent localPendingIntent = PendingIntent.getBroadcast(localContext, 0, localIntent1, 1073741824);
        Intent localIntent2 = new Intent(PlaySearchVoiceController.sVoiceIntent);
        localIntent2.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        localIntent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", localPendingIntent);
        if (!localPlaySearchVoiceController.mRegistered)
        {
          localContext.registerReceiver(localPlaySearchVoiceController, new IntentFilter("com.google.android.play.search.VOICE_SEARCH_RESULT"));
          localPlaySearchVoiceController.mRegistered = true;
        }
        localContext.startActivity(localIntent2);
      }
    }
  }
  
  public final void onQueryChanged(String paramString, boolean paramBoolean)
  {
    this.mSearchBoxActiveTextView.setText(paramString);
    setText(paramString);
  }
  
  public final void onSearch(String paramString) {}
  
  public final void onSuggestionClicked(PlaySearchSuggestionModel paramPlaySearchSuggestionModel) {}
  
  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((this.mController != null) && (!this.mSuspendTextChangeCallback)) {
      this.mController.setQueryInternal(paramCharSequence.toString(), true);
    }
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    if ((paramBoolean) && (this.mController != null) && (this.mController.mCurrentSearchMode == 4))
    {
      if (this.mVoiceController != null) {
        this.mVoiceController.cancelPendingVoiceSearch(getContext());
      }
      if (!TextUtils.isEmpty(this.mController.mCurrentQuery)) {
        this.mController.onSearch();
      }
    }
    else
    {
      return;
    }
    if (hasFocus())
    {
      this.mController.setMode(3);
      return;
    }
    this.mController.switchToSteadyStateMode();
  }
  
  public void setHint(CharSequence paramCharSequence)
  {
    if (paramCharSequence != null)
    {
      this.mSearchBoxActiveTextView.setHint(paramCharSequence);
      this.mSearchBoxTextInput.setHint(paramCharSequence);
      return;
    }
    this.mSearchBoxActiveTextView.setHint(R.string.play_search_box_hint);
    this.mSearchBoxTextInput.setHint(R.string.play_search_box_hint);
  }
  
  public void setPlaySearchController(PlaySearchController paramPlaySearchController)
  {
    if (this.mController != null) {
      this.mController.removePlaySearchListener(this);
    }
    this.mController = paramPlaySearchController;
    this.mController.addPlaySearchListener(this);
    this.mVoiceController = new PlaySearchVoiceController(this.mController);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.PlaySearchPlateTextContainer
 * JD-Core Version:    0.7.0.1
 */