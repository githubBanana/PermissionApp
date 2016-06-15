package com.xs.permissionapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private final int PERMISSION_REQ = 345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button _test = (Button) findViewById(R.id.btn_test);
        _test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断是否有该权限
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                    //用户拒绝该权限时 下次再点击 则会显示相关解释（用户拒绝时 shouldShowRequestPermissionRationale 下次访问返回true）
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(MainActivity.this,"jie shi",Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onClick: shouldShowRequestPermissionRationale true" );
                        return;
                    } else {
                        Log.e(TAG, "onClick: shouldShowRequestPermissionRationale false" );
                    }
                    //申请权限
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQ);
                    Log.e(TAG, "onClick: requestPermissions");
                    return;
                }
                Toast.makeText(MainActivity.this,"had write permission!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void export() {
        final File folder = new File(Environment.getExternalStorageDirectory(),"persiom");
        if (!folder.exists()) {
            try {
                folder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 /*       FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(folder);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Log.e(TAG, "export: " );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case PERMISSION_REQ:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e(TAG, "onRequestPermissionsResult: 申请权限成功!" );
                        Log.e(TAG, "code: "+requestCode+"  "+grantResults[0] );
                        export();
                    }
                    else {
                        Log.e(TAG, "onRequestPermissionsResult: error" );
                        Log.e(TAG, "code: "+requestCode+"  "+grantResults[0] );

                    }
                    break;
            }
        }
}
