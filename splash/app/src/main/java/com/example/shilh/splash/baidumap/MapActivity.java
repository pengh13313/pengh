package com.example.shilh.splash.baidumap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.shilh.splash.R;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements View.OnClickListener{

    public LocationClient mLocationClient;
    private Button btnPosition;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        try {
            SDKInitializer.initialize(getApplicationContext());//该句报错
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_map);
        mapView = (MapView)findViewById(R.id.baidu_map_view) ;
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        btnPosition = (Button) LayoutInflater.from(MapActivity.this).inflate(R.layout.fragment_my_photo, null).findViewById(R.id.bt_map);
        //btnPosition = (Button)findViewById(R.id.bt_map);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);//在我当前的位置显示定位图标

        //运行时权限处理 判断有无授权
        // 没被授权则添加到List集合中，最后将List转换为数组，
        // 再调用ActivityCompat.re
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        //申请权限
        if(!permissionList.isEmpty()){
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permission,1);
        }else {
            requestLocation();
        }
    }

    //将地图移动到我当前的位置
    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());//将BDLocation对象中的地理位置信息取出
                                                                                    // 并封装到LatLng对象中
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);//缩放级别
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);//5秒更新一次位置
        option.setIsNeedAddress(true);//允许获取当前详细的地址信息
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0) {
                    for(int result : grantResults) {
                        if(result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation
                    || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append(location.getCity()).append(location.getDistrict()).append(location.getStreet());
//                    currentPosition.append("纬度：").append(location.getLatitude()).append("\n");
//                    currentPosition.append("经线：").append(location.getLongitude()).append("\n");
//                    currentPosition.append("定位方式：");
//                    if (location.getLocType() == BDLocation.TypeGpsLocation) {
//                        currentPosition.append("GPS");
//                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//                        currentPosition.append("网络");
//                    }
                    btnPosition.setText(currentPosition);
                }
            });
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
