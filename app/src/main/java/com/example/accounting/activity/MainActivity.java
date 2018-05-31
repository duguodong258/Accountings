package com.example.accounting.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accounting.R;
import com.example.accounting.widget.FlexibleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FlexibleView.ScrollListener {

    //目前预算 总额:1600 购物:200 餐饮:1000 交通：100 其他:200
    private TextView tvTotalMoney;

    private ProgressBar pbShop;
    private TextView tvShopTotal;

    private ProgressBar pbEat;
    private TextView tvEatTotal;

    private ProgressBar pbTraffic;
    private TextView tvTrafficTotal;

    private ProgressBar pbOther;
    private TextView tvOtherTotal;


    private LinearLayout llEditSpend;
    private EditText etSpend;
    private SharedPreferences.Editor mEdit;
    private List<Integer> mDatas = new ArrayList<>();
    private HashMap<String,Integer> map;
    private int currentPosition;
    private FlexibleView mFlexibleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initData();
    }


    private void initView() {
        mFlexibleView = (FlexibleView) findViewById(R.id.flexibleView);
        mFlexibleView.setScrollListener(this);
        tvTotalMoney = (TextView) findViewById(R.id.tv_total_money);
        tvTotalMoney.setOnClickListener(this);
        pbShop = (ProgressBar) findViewById(R.id.pb_shop);
        tvShopTotal = (TextView) findViewById(R.id.tv_shop_total);
        findViewById(R.id.iv_shop_edit).setOnClickListener(this);
        pbEat = (ProgressBar) findViewById(R.id.pb_eat);
        tvEatTotal = (TextView) findViewById(R.id.tv_eat_total);
        findViewById(R.id.iv_eat_edit).setOnClickListener(this);
        pbTraffic = (ProgressBar) findViewById(R.id.pb_traffic);
        tvTrafficTotal = (TextView) findViewById(R.id.tv_traffic_total);
        findViewById(R.id.iv_traffic_edit).setOnClickListener(this);
        pbOther = (ProgressBar) findViewById(R.id.pb_other);
        tvOtherTotal = (TextView) findViewById(R.id.tv_other_total);
        findViewById(R.id.iv_other_edit).setOnClickListener(this);
        llEditSpend = (LinearLayout) findViewById(R.id.ll_edit_spend);
        llEditSpend.setOnClickListener(this);
        etSpend = (EditText) findViewById(R.id.et_spend);
        findViewById(R.id.btn_sure).setOnClickListener(this);
    }


    private void initData() {
        map = new HashMap<>();
        SharedPreferences sp = getSharedPreferences("spend", MODE_PRIVATE);
        mEdit = sp.edit();
        int total = sp.getInt("total", 101000);
        int shop = sp.getInt("shop", 30000);
        int eat = sp.getInt("eat", 20000);
        int traffic = sp.getInt("traffic", 1000);
        int other = sp.getInt("other", 50000);

        map.put("total",total);
        map.put("shop",shop);
        map.put("eat",eat);
        map.put("traffic",traffic);
        map.put("other",other);

        tvTotalMoney.setText("剩余: "+ total + " ¥");
        tvShopTotal.setText("" + shop + " ¥");
        tvEatTotal.setText("" + eat + " ¥");
        tvTrafficTotal.setText("" + traffic + " ¥");
        tvOtherTotal.setText("" + other + " ¥");

        int shopProgress = (int) (100 * ((float) shop / 30000));
        pbShop.setProgress(shopProgress);

        int eatProgress = (int) (100 * ((float) eat / 20000));
        pbEat.setProgress(eatProgress);

        int trafficProgress = (int) (100 * ((float) eat / 1000));
        pbTraffic.setProgress(trafficProgress);

        int otherProgress = (int) (100 * ((float) eat / 50000));
        pbOther.setProgress(otherProgress);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_total_money :
                llEditSpend.setVisibility(View.VISIBLE);
                currentPosition = 1;
                Intent intent = new Intent(this, MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_shop_edit :
                llEditSpend.setVisibility(View.VISIBLE);
                currentPosition = 2;
                break;
            case R.id.iv_eat_edit :
                llEditSpend.setVisibility(View.VISIBLE);
                currentPosition = 3;
                break;
            case R.id.iv_traffic_edit :
                llEditSpend.setVisibility(View.VISIBLE);
                currentPosition = 4;
                break;
            case R.id.iv_other_edit :
                llEditSpend.setVisibility(View.VISIBLE);
                currentPosition = 5;
                break;
            case R.id.ll_edit_spend :
                llEditSpend.setVisibility(View.GONE);
                break;
            case R.id.btn_sure :
                saveEdit();
                break;
        }
    }

    private void saveEdit() {
        llEditSpend.setVisibility(View.GONE);
        String money = etSpend.getText().toString();
        if(TextUtils.isEmpty(money)){
            return;
        }
        int spendMoney = Integer.parseInt(money);
        int total = map.get("total");
        total = total - spendMoney;
        mEdit.putInt("total",total);
        map.put("total",total);
        tvTotalMoney.setText("剩余: "+ total + " ¥");

        switch (currentPosition) {
            case 1 :
                mEdit.putInt("total",spendMoney);
                tvTotalMoney.setText("剩余: "+ spendMoney + " ¥");
                Toast.makeText(this,"总额改为: " + spendMoney,Toast.LENGTH_SHORT).show();
                break;
            case 2 :
                int shop = map.get("shop");
                int newShop = shop - spendMoney;
                mEdit.putInt("shop",newShop);
                map.put("shop",newShop);
                tvShopTotal.setText("" + newShop + " ¥");
                Toast.makeText(this,"购物总额剩余:" + newShop,Toast.LENGTH_SHORT).show();
                break;
            case 3 :
                int eat = map.get("eat");
                int newEat = eat - spendMoney;
                mEdit.putInt("eat",newEat);
                map.put("eat",newEat);
                tvEatTotal.setText("" + newEat + " ¥");
                Toast.makeText(this," 餐饮总额剩余:" + newEat,Toast.LENGTH_SHORT).show();
                break;
            case 4:
                int traffic = map.get("traffic");
                int newTraffic = traffic - spendMoney;
                mEdit.putInt("traffic",newTraffic);
                map.put("traffic",newTraffic);
                tvTrafficTotal.setText("" + newTraffic + " ¥");
                Toast.makeText(this," 交通总额剩余:" + newTraffic,Toast.LENGTH_SHORT).show();
                break;
            case 5 :
                int other = map.get("other");
                int newOther = other - spendMoney;
                mEdit.putInt("other",newOther);
                map.put("other",newOther);
                tvOtherTotal.setText("" + newOther + " ¥");
                Toast.makeText(this," 其他总额剩余:" + newOther,Toast.LENGTH_SHORT).show();
                break;
        }
        mEdit.commit();
        etSpend. setText("");
        setProgress();
    }


    private void setProgress(){
        int shop = map.get("shop");
        int eat = map.get("eat");
        int traffic = map.get("traffic");
        int other = map.get("other");

        int shopProgress = (int) (100 * ((float) shop / 30000));
        pbShop.setProgress(shopProgress);

        int eatProgress = (int) (100 * ((float) eat / 20000));
        pbEat.setProgress(eatProgress);

        int trafficProgress = (int) (100 * ((float) traffic / 1000));
        pbTraffic.setProgress(trafficProgress);

        int otherProgress = (int) (100 * ((float) other / 50000));
        pbOther.setProgress(otherProgress);

    }

    @Override
    public void scrollFinish() {

    }

    @Override
    public void scrolling(int scrollDistance) {

    }
}
