package com.ppdl.rocketmen;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private Button bt_stopService,bt_startService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_stopService= (Button) findViewById(R.id.bt_stopService);
        bt_startService= (Button) findViewById(R.id.bt_startService);
        bt_startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this,RocketService.class));
                finish();
            }
        });
        bt_stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRunning=serviceRunning("com.ppdl.rocketmen.RocketService");

                if(isRunning){
                    stopService(new Intent(MainActivity.this,RocketService.class));
                    finish();
                }

            }


        });
    }

    private boolean serviceRunning(String serviceName) {
        ActivityManager acManager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list=acManager.getRunningServices(1000);
       for(ActivityManager.RunningServiceInfo serviceInfo:list){
           if(serviceName.equals(serviceInfo.service.getClassName())){
                return true;
           }
       }
        return false;
    }
}
