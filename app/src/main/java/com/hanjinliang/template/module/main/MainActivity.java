package com.hanjinliang.template.module.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.hanjinliang.template.R;
import com.hanjinliang.template.tools.image.MyImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView=findViewById(R.id.imageView);
        MyImageLoader.getInstance()
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523610076829&di=687085808298ecd12fb42db8d8cd535c&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F24%2F50%2F43Q58PICkj4_1024.jpg")
                .error(R.drawable.ic_launcher_background)
                .angle(50)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }
}
