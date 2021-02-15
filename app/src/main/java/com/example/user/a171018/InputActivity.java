package com.example.user.a171018;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;

public class InputActivity extends AppCompatActivity {
    TextView tvName, tvCompany, tvNation, tvSN, tvCount, tvPrice, tvType;
    EditText et;
    Button btn;
    ImageView img;
    int a;
    private PrintWriter out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        out = new PrintWriter(DataThread.getInstance().getWriter(), true);

        tvName = (TextView)findViewById(R.id.input_name);
        tvCompany  = (TextView)findViewById(R.id.input_company);
        tvNation  = (TextView)findViewById(R.id.input_nation);
        tvSN = (TextView)findViewById(R.id.input_SN);
        tvCount = (TextView)findViewById(R.id.input_count);
        tvPrice  = (TextView)findViewById(R.id.input_price);
        tvType  = (TextView)findViewById(R.id.input_type);
        et = (EditText)findViewById(R.id.input_count_edit);
        btn = (Button)findViewById(R.id.input_count_btn);
        img = (ImageView)findViewById(R.id.input_img);

        Intent getResult = getIntent();
        String bNum = getResult.getStringExtra("bNum");

        if(bNum == null) {

        }
        else {
            Iterator<String> itr = DataThread.codeList.iterator();

            while (itr.hasNext()) {
                for(int i = 0; i < DataThread.codeList.size(); i++) {
                    String temp = itr.next();
                    if(temp.matches(bNum)) {
                        a = DataThread.codeList.indexOf(temp);
                        tvName.setText(DataThread.nameList.get(a));
                        tvSN.setText(DataThread.SNList.get(a));
                        tvPrice.setText(DataThread.priceList.get(a));
                        tvCompany.setText(DataThread.companyList.get(a));
                        tvNation.setText(DataThread.nationList.get(a));
                        tvType.setText(DataThread.typeList.get(a));
                        tvCount.setText(DataThread.countList.get(a));

                        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WareHouse_product_Image/"+DataThread.codeList.get(a)+".jpg";

                        File imgFile = new File(dirPath);
                        if(imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            img.setImageBitmap(myBitmap);
                        }
                    }
                }
            }
            if (tvNation.getText().equals("0")) {
                if (itr.equals(bNum)!=true) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(InputActivity.this);
                    alert.setTitle("예외");
                    alert.setMessage("등록이 되지 않은 상품입니다.");
                    alert.setNegativeButton("확인",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert.show();
                }
            }
        }
    }

    public void Input(View v) {
        String countS;
        int count;

        countS = DataThread.countList.get(a);
        count = Integer.parseInt(countS);
        out.println(DataThread.codeList.get(a));
        out.flush();
        Log.e("count", countS);

        String plusCountS;
        int plusCount;

        plusCountS = et.getText().toString();
        if(plusCountS.equals("")) {
            Toast t = Toast.makeText(this, "수량을 입력하거나 물품을 스캔하세요.", Toast.LENGTH_LONG);
            t.show();
        }
        else {
            AlertDialog.Builder alert = new AlertDialog.Builder(InputActivity.this);
            alert.setTitle("입고");
            alert.setMessage(DataThread.nameList.get(a) + "\n"+DataThread.codeList.get(a)+"\n"+et.getText().toString() + "개").setCancelable(false).setPositiveButton("확인",

                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String countS;
                            int count;

                            countS = DataThread.countList.get(a);
                            count = Integer.parseInt(countS);

                            String plusCountS;
                            int plusCount;

                            plusCountS = et.getText().toString();

                            plusCount = Integer.parseInt(plusCountS);

                            int resultCount = count + plusCount;
                            String resultCountS = String.valueOf(resultCount);
                            DataThread.countList.set(a, resultCountS);
                            out.println(String.valueOf(resultCount));
                            out.flush();
                            Log.e("resultCount", DataThread.countList.get(a));
                            Toast.makeText(getApplicationContext(), "update finish", Toast.LENGTH_SHORT).show();
                            tvName.setText(DataThread.nameList.get(a));
                            tvSN.setText(DataThread.SNList.get(a));
                            tvPrice.setText(DataThread.priceList.get(a));
                            tvCompany.setText(DataThread.companyList.get(a));
                            tvNation.setText(DataThread.nationList.get(a));
                            tvType.setText(DataThread.typeList.get(a));
                            tvCount.setText(DataThread.countList.get(a));

                            et.setText("");
                            finish();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    out.println("cancel");
                    finish();
                }
            });
            AlertDialog alertD = alert.create();
            alertD.show();
        }
    }
}
