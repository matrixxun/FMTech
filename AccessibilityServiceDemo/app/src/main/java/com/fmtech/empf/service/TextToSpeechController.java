package com.fmtech.empf.service;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/10 15:15
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/10 15:15  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class TextToSpeechController implements TextToSpeech.OnInitListener {

    private static final String TAG = "TextToSpeechController";
    private TextToSpeech myTTS;
    private String textToSpeak;
    private Context context;

    private static TextToSpeechController singleton;

    public static TextToSpeechController getInstance(Context ctx) {
        if (singleton == null)
            singleton = new TextToSpeechController(ctx);
        return singleton;
    }

    private TextToSpeechController(Context ctx) {
        context = ctx;
    }

    public void speak(String text) {
        textToSpeak = text;

        if (myTTS == null) {
            // currently can't change Locale until speech ends
            try {
                // Initialize text-to-speech. This is an asynchronous operation.
                // The OnInitListener (second argument) is called after
                // initialization completes.
                myTTS = new TextToSpeech(context, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sayText();

    }

    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.UK) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.UK);
//            if (myTTS.isLanguageAvailable(Locale.CHINESE) == TextToSpeech.LANG_AVAILABLE) {
//                System.out.println("-------Locale.CHINESE available.");
//                myTTS.setLanguage(Locale.CHINESE);
//            }
//            if (myTTS.isLanguageAvailable(Locale.CHINA) == TextToSpeech.LANG_AVAILABLE) {
//                System.out.println("-------Locale.CHINA available.");
//                myTTS.setLanguage(Locale.CHINA);
//            }
        }

        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (initStatus == TextToSpeech.SUCCESS) {
            int result = myTTS.setLanguage(Locale.UK);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "TTS missing or not supported (" + result + ")");
                // Language data is missing or the language is not supported.
                // showError(R.string.tts_lang_not_available);

            } else {
                // Initialization failed.
                Log.e(TAG, "Error occured， result="+result);
            }

        }
    }

    private void sayText() {
        // ask TTs to say the text
        myTTS.speak(this.textToSpeak, TextToSpeech.QUEUE_FLUSH,  null);
    }

    public void stopTTS() {
        if (myTTS != null) {
            myTTS.shutdown();
            myTTS.stop();
            myTTS = null;
        }
    }

}