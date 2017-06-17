package com.ppdl.rocketmen;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RocketService extends Service {
    private int screenWidth, screenHeight;
    private WindowManager mManager;
    final private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private WindowManager.LayoutParams params;
    private View mrocketView;

    private Handler mHander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            params.y= msg.arg1;
            mManager.updateViewLayout(mrocketView,params);
            super.handleMessage(msg);
        }
    };

    @Override
    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        return super.openFileInput(name);
    }

    public RocketService() {
    }

    @Override
    public void onCreate() {
        mManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = mManager.getDefaultDisplay().getWidth();
        screenHeight = mManager.getDefaultDisplay().getHeight();
        System.out.println("开启服务");
        showRocket();
    }

    private void showRocket() {
        //显示火箭
        params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                //| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        params.gravity = Gravity.TOP + Gravity.LEFT;

        mrocketView = View.inflate(RocketService.this, R.layout.rocket_view, null);

        AnimationDrawable animationDrawable = (AnimationDrawable) mrocketView.findViewById(R.id.iv_rocketBg).getBackground();
        animationDrawable.start();
        mManager.addView(mrocketView, params);

        mrocketView.setOnTouchListener(new View.OnTouchListener() {

            int startX,startY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("触摸");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                      startX= (int) event.getRawX();
                      startY= (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX= (int) event.getRawX();
                        int moveY= (int) event.getRawY();

                        int disX=moveX-startX;
                        int disY=moveY-startY;
                        params.x=params.x+disX;
                        params.y=params.y+disY;
                        System.out.println("disX:"+disX+"   disY:"+disY);
                        //容错
                        if(params.x<0){
                            return true;
                        }
                        if(params.y<0){
                           return true;
                        }
                        if(params.x+mrocketView.getWidth()>screenWidth){
                             return true;
                        }
                       if(params.y+mrocketView.getHeight()>screenHeight-22){
                           return true;
                       }



                        mManager.updateViewLayout(mrocketView,params);



                        startX= (int) event.getRawX();
                        startY= (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP:
                         //条件判断火箭起飞
//                       if(params.x>100&&params.x<200&&params.y>350){
//                           System.out.println("起飞");
//                            rocketFly();
//                       }


                    default:
                        break;
                }

                return false;
            }
        });
    }

    private void rocketFly() {
      new Thread(){
          @Override
          public void run() {
              for(int i=0;i<11;i++){
                  int flyDis=350-i*35;
                  try {
                      sleep(50);
                  } catch (InterruptedException e) {

                  }
                  Message msg=Message.obtain();
                  msg.arg1=flyDis;
                  mHander.sendMessage(msg);
              }
          }


      }.start();


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
