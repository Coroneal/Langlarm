package com.example.test.langlarm.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.langlarm.R;
import com.example.test.langlarm.alarm.Alarm;
import com.example.test.langlarm.alarm.AlarmService;
import com.example.test.langlarm.gags.Gag;
import com.example.test.langlarm.gags.GagsProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WakeUpActivity extends Activity {

    private MediaPlayer mMediaPlayer;
    private Button stopAlarmButton;
    private Button napButton;
    private TextView gagText;
    private TextView gagTextTranslated;
    private TextView currentTime;
    private Gag gag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_wake_up);

        stopAlarmButton = (Button) findViewById(R.id.stopAlarmButton);
        napButton = (Button) findViewById(R.id.napButton);
        gagText = (TextView) findViewById(R.id.gagText);
        gagTextTranslated = (TextView) findViewById(R.id.gagTextTranslated);
        currentTime = (TextView) findViewById(R.id.currentTime);
        gagTextTranslated.setVisibility(View.GONE);
        gag = GagsProvider.getInstance().getRandomGag();
        gagText.setText(gag.getEn());
        currentTime.setText(new SimpleDateFormat("kk:mm").format(new Date()));
        initStopAlarmButton();
        initNapButton();
        playSound(this, getAlarmUri());
    }

    private void initNapButton() {
        napButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_around_center_point);
                animation.setAnimationListener(new Animation.AnimationListener(){

                    @Override
                    public void onAnimationStart(Animation animation){}

                    @Override
                    public void onAnimationRepeat(Animation animation){}

                    @Override
                    public void onAnimationEnd(Animation animation){
                        finish();
                    }
                });
                napButton.startAnimation(animation);
                mMediaPlayer.stop();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 10);
                AlarmService.startAlarm(getBaseContext(), new Alarm(calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true));
                return false;
            }
        });
    }

    private void initStopAlarmButton() {
        stopAlarmButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_around_center_point);
                animation.setAnimationListener(new Animation.AnimationListener(){

                    @Override
                    public void onAnimationStart(Animation animation){}

                    @Override
                    public void onAnimationRepeat(Animation animation){}

                    @Override
                    public void onAnimationEnd(Animation animation){
                        finish();
                    }
                });
                stopAlarmButton.startAnimation(animation);
                mMediaPlayer.stop();
                return false;
            }
        });
    }

    public void onClickGag(View view) {
        gagTextTranslated.setText(gag.getPl());
        gagTextTranslated.setVisibility(View.VISIBLE);
        stopAlarmButton.setEnabled(true);
    }

    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    private Uri getAlarmUri() {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
}