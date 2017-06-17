package com.ppdl.rocketmen;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class RockBgActivity extends Activity {
    private ImageView iv_top, iv_bottom;
   private Handler mHander=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           finish();
           super.handleMessage(msg);
       }
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("启动activity");
        setContentView(R.layout.activity_rock_bg);
        iv_top = (ImageView) findViewById(R.id.iv_top);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);
        iv_top.startAnimation(alphaAnimation);
        iv_bottom.startAnimation(alphaAnimation);
        mHander.sendEmptyMessageDelayed(0,1000);
    }
}
