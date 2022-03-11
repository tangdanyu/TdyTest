package com.example.tdytest.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import java.util.Locale;

public class TextToSpeechUtil {
    //需要在application中先调用一次
    // TextToSpeechUtil.getInstance(this).speakText("你好");

    private String TAG = "TextToSpeechUtil";
    private static volatile TextToSpeechUtil singleton;
    private Context context;
    private TextToSpeech textToSpeech;//TTS对象


    public static TextToSpeechUtil getInstance(Context context) {
        if (singleton == null) {
            synchronized (TextToSpeechUtil.class) {
                if (singleton == null) {
                    singleton = new TextToSpeechUtil(context);
                }
            }
        }
        return singleton;
    }

    public TextToSpeechUtil(Context context) {
        this.context = context;
        speakText(null);
    }

    /**
     * 关闭语音合成
     */

    public void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    /**
     * 停止语音合成
     */

    public void stop() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    /**
     * 第二个参数queueMode用于指定发音队列模式，两种模式选择
     * （1）TextToSpeech.QUEUE_FLUSH：该模式下在有新任务时候会清除当前语音任务，执行新的语音任务
     * （2）TextToSpeech.QUEUE_ADD：该模式下会把新的语音任务放到语音任务之后，
     *
     * @param text
     */
    public void speakText(String text) {
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
                        int result = textToSpeech.setLanguage(Locale.CHINA);
                        textToSpeech.setPitch(0.9f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                        textToSpeech.setSpeechRate(1.0f);
                    /*if(result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(context, "TTS暂时不支持这种语音的朗读！",
                                Toast.LENGTH_LONG).show();
                    }else {
                        textToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                        textToSpeech.setSpeechRate(1.0f);
                    }*/
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(text)  ) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
        }

    }


}
