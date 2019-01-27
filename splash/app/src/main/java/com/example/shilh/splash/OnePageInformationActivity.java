package com.example.shilh.splash;

import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class OnePageInformationActivity extends AppCompatActivity {


    private Button backbutton;
    private TextView moname;
    private TextView motime;
    private TextView motype;
    private TextView msize;
    private TextView mlength;
    private TextView mwidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_page_information);
        backbutton=(Button)findViewById(R.id.oback);
        moname=(TextView) findViewById(R.id.oname);
        motime=(TextView) findViewById(R.id.otime);
        motype=(TextView) findViewById(R.id.otype);
        msize=(TextView) findViewById(R.id.osize);
        mlength=(TextView) findViewById(R.id.length);
        mwidth=(TextView) findViewById(R.id.width);
        final String oneimage=getIntent().getStringExtra("onepage");
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        moname.setText("路径名："+oneimage);
        try {
            ExifInterface exifInterface=new ExifInterface(oneimage);
            String time=exifInterface.getAttribute(ExifInterface.TAG_DATETIME );
            if(time==null)
            {
                motime.setText("时间：无记录");
            }else{
                motime.setText("时间："+time);
            }

            String type=exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            if(type==null)
            {
                motype.setText("设备型号：无记录");
            }else{
                motype.setText("设备型号："+type);
            }
            String size=exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
            if(size==null)
            {
                msize.setText("白平衡：无记录");
            }else{
                msize.setText("白平衡："+size);
            }
            String l=exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            mlength.setText("图片长："+l);
            String w=exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH );
            mwidth.setText("图片宽："+w);
            } catch (IOException e) {
            e.printStackTrace();
            }


    }
}
