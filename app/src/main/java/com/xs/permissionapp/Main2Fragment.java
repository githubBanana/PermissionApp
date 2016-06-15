package com.xs.permissionapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.UUID;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-06-14 17:49
 * @email Xs.lin@foxmail.com
 */
public class Main2Fragment extends Fragment {
    private static final String TAG = "Main2Fragment";

    private final int PERMISSION_REQ = 123;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main2_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button _test = (Button) view.findViewById(R.id.btn_permission);
        _test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            getPhoneState();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQ:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(),"onRequestPermissionsResult: 申请成功",Toast.LENGTH_LONG).show();
                    getPhoneState();
                    //do something()
                }
                else {
                    Toast.makeText(getActivity(),"onRequestPermissionsResult: error",Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    private void getPhoneState() {
        //判断是否有该权限
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户拒绝时，下次进来 shouldShowRequestPermissionRationale将返回true，此时开发者可以解释
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(getActivity(),"jie shi",Toast.LENGTH_LONG).show();
                return;
            }

            //申请权限
            requestPermissions(new String[] {Manifest.permission.READ_PHONE_STATE},PERMISSION_REQ);
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        int type = telephonyManager.getPhoneType();
        Toast.makeText(getActivity(),getUUID(getActivity()),Toast.LENGTH_LONG).show();
        Log.e(TAG, "getPhoneState: "+deviceId+" "+type+ " "+getUUID(getActivity()) );
    }

    /**
     * 获取手机设备UUID
     * @param context
     * @return
     */
    public static String getUUID(Context context){
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }
}
