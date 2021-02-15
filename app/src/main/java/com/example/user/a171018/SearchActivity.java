package com.example.user.a171018;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    Spinner sp;
    Button wordBtn;
    EditText et;
    ListView listView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sp = (Spinner)findViewById(R.id.search_spinner);
        wordBtn = (Button)findViewById(R.id.search_info);
        et = (EditText)findViewById(R.id.search_edit);
        listView = (ListView)findViewById(R.id.search_listview);

        NameSearch();

        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        String[] spArr = {"제품명", "제품코드", "회사명", "국가명"};
        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_spinner_dropdown_item, spArr);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spAdapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                switch (position) {
                    case 0:
                        wordBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                NameSearch();
                            }
                        });
                        break;
                    case 1:
                        wordBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                SNSearch();
                            }
                        });
                        break;
                    case 2:
                        wordBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                CompanySearch();
                            }
                        });
                        break;
                    case 3:
                        wordBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                NationSearch();
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void NameSearch () {
        adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.search_listview);
        listView.setAdapter(adapter);

        String searchWord;
        searchWord = et.getText().toString();

        Iterator<String> itr = DataThread.nameList.iterator();

        while (itr.hasNext()) {
            for(int i = 0; i < DataThread.nameList.size(); i++) {
                int a = 0;
                String temp = itr.next();
                if(temp.matches(".*"+searchWord+".*")) {
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WareHouse_product_Image/"+DataThread.codeList.get(i)+".jpg";
                    File imgFile = new File(dirPath);
                    adapter.addItem(BitmapFactory.decodeFile(imgFile.getAbsolutePath()), DataThread.nameList.get(i), DataThread.typeList.get(i), DataThread.priceList.get(i));
                }
            }
        }
        Toast t = Toast.makeText(getApplicationContext(),"검색을 완료 했습니다.",Toast.LENGTH_LONG);
        t.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ListView", adapter.listViewItemList.get(position).getTitle());
                Intent intent = new Intent(SearchActivity.this, InfoActivity.class);
                intent.putExtra("Name",adapter.listViewItemList.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    public void SNSearch () {
        adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.search_listview);
        listView.setAdapter(adapter);

        String searchWord;
        searchWord = et.getText().toString();

        Iterator<String> itr = DataThread.SNList.iterator();

        while (itr.hasNext()) {
            for(int i = 0; i < DataThread.SNList.size(); i++) {
                int a = 0;
                String temp = itr.next();
                if(temp.matches(".*"+searchWord+".*")) {
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WareHouse_product_Image/"+DataThread.codeList.get(i)+".jpg";
                    File imgFile = new File(dirPath);
                    adapter.addItem(BitmapFactory.decodeFile(imgFile.getAbsolutePath()), DataThread.nameList.get(i), DataThread.typeList.get(i), DataThread.priceList.get(i));
                }
            }
        }
        Toast t = Toast.makeText(getApplicationContext(),"검색을 완료 했습니다.",Toast.LENGTH_LONG);
        t.show();
    }

    public void CompanySearch () {
        adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.search_listview);
        listView.setAdapter(adapter);

        String searchWord;
        searchWord = et.getText().toString();

        Iterator<String> itr = DataThread.companyList.iterator();

        while (itr.hasNext()) {
            for(int i = 0; i < DataThread.companyList.size(); i++) {
                int a = 0;
                String temp = itr.next();
                if(temp.matches(".*"+searchWord+".*")) {
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WareHouse_product_Image/"+DataThread.codeList.get(i)+".jpg";
                    File imgFile = new File(dirPath);
                    adapter.addItem(BitmapFactory.decodeFile(imgFile.getAbsolutePath()), DataThread.nameList.get(i), DataThread.typeList.get(i), DataThread.priceList.get(i));
                }
            }
        }
        Toast t = Toast.makeText(getApplicationContext(),"검색을 완료 했습니다.",Toast.LENGTH_LONG);
        t.show();
    }

    public void NationSearch () {
        adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.search_listview);
        listView.setAdapter(adapter);

        String searchWord;
        searchWord = et.getText().toString();

        Iterator<String> itr = DataThread.nationList.iterator();

        while (itr.hasNext()) {
            for(int i = 0; i < DataThread.nationList.size(); i++) {
                int a = 0;
                String temp = itr.next();
                if(temp.matches(".*"+searchWord+".*")) {
                    a = DataThread.nationList.indexOf(temp);

                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WareHouse_product_Image/"+DataThread.codeList.get(i)+".jpg";
                    File imgFile = new File(dirPath);
                    adapter.addItem(BitmapFactory.decodeFile(imgFile.getAbsolutePath()), DataThread.nameList.get(i), DataThread.typeList.get(i), DataThread.priceList.get(i));
                }
            }
        }
        Toast t = Toast.makeText(getApplicationContext(),"검색을 완료 했습니다.",Toast.LENGTH_LONG);
        t.show();
    }
}
