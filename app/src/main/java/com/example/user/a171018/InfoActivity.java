package com.example.user.a171018;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Iterator;

public class InfoActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvCompany;
    TextView tvNation;
    TextView tvSN;
    TextView tvType;
    TextView tvPrice;
    TextView tvCount;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent getResult = getIntent();
        String productName = getResult.getStringExtra("Name");

        tvName = (TextView)findViewById(R.id.info_name);
        tvCompany = (TextView)findViewById(R.id.info_company);
        tvNation = (TextView)findViewById(R.id.info_nation);
        tvSN = (TextView)findViewById(R.id.info_SN);
        tvType = (TextView)findViewById(R.id.info_type);
        tvPrice = (TextView)findViewById(R.id.info_price);
        tvCount = (TextView)findViewById(R.id.info_count);
        img = (ImageView)findViewById(R.id.info_img);

        Iterator<String> itr = DataThread.nameList.iterator();
        while (itr.hasNext()) {
            for(int i = 0; i < DataThread.nameList.size(); i++) {
                int a;
                String temp = itr.next();
                if(temp.matches(productName)) {
                    a = DataThread.nameList.indexOf(temp);
                    tvCompany.setText(DataThread.companyList.get(a));
                    tvName.setText(DataThread.nameList.get(a));
                    tvNation.setText(DataThread.nationList.get(a));
                    tvSN.setText(DataThread.SNList.get(a));
                    tvPrice.setText(DataThread.priceList.get(a));
                    tvType.setText(DataThread.typeList.get(a));
                    tvCount.setText(DataThread.countList.get(a));

                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WareHouse_product_Image/"+DataThread.codeList.get(a)+".jpg";
                    String test = getFilesDir().getAbsolutePath() + "/WareHouse_product_Image/" + DataThread.codeList.get(a) + ".jpg";
                    File imgFile = new File(dirPath);
                    if(imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        img.setImageBitmap(myBitmap);
                    }

                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
