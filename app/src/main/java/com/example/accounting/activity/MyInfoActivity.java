package com.example.accounting.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.accounting.R;
import com.example.accounting.util.ImageFilter;
import com.example.accounting.widget.FlexibleView;
import com.example.accounting.widget.ZoomInScrollView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author jelly
 * @TIME 2018/3/6
 * @DES ${TODO}
 */

public class MyInfoActivity extends AppCompatActivity implements FlexibleView.ScrollListener {
    private FlexibleView flexibleView;
    private ImageView ivHeadBg;
    private CircleImageView ivHead;
     private String headUrl = "https://www.baidu.com/s?wd=%E4%BB%8A%E6%97%A5%E6%96%B0%E9%B2%9C%E4%BA%8B&tn=SE_PclogoS_8whnvm25&sa=ire_dl_gh_logo&rsv_dl=igh_logo_pcs";
    private ViewGroup.LayoutParams mLayoutParams;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info2);

        final View rlRoot = findViewById(R.id.rl_root);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.center_bg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = ImageFilter.doBlur(mBitmap, 60, false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(mBitmap);

                        rlRoot.setBackgroundDrawable(bitmapDrawable);
                    }
                });
            }
        }).start();

//        flexibleView = (FlexibleView) findViewById(R.id.flexibleView);
//        flexibleView.setScrollListener(this);
//        ivHeadBg = (ImageView) findViewById(R.id.iv_headBg);
//        ivHead = (CircleImageView) findViewById(R.id.iv_head);
//        mLayoutParams = ivHead.getLayoutParams();

//        Glide.with(this).load(headUrl).into(ivHead);

        ZoomInScrollView inScrollView = (ZoomInScrollView) findViewById(R.id.zoomScrollView);

    }

    @Override
    public void scrollFinish() {

    }

    @Override
    public void scrolling(int scrollDistance) {
//        mLayoutParams.height = mLayoutParams.height + scrollDistance / 20;
//        mLayoutParams.width = mLayoutParams.width + scrollDistance / 20;
//        ivHead.setLayoutParams(mLayoutParams);
    }
}
