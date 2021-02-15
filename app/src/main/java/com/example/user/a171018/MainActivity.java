package com.example.user.a171018;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    private  BackPressCloseHandler backPressCloseHandler;
    private Button input, output, search, log;
    private Intent intent;
    private PrintWriter out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataThread.getInstance();
        backPressCloseHandler = new BackPressCloseHandler(this);
        startConnect();

        input = (Button)findViewById(R.id.input_btn);
        output = (Button)findViewById(R.id.output_btn);
        search = (Button)findViewById(R.id.search_btn);
/*        log = (Button)findViewById(R.id.log_btn);*/

        input.setOnClickListener(setListener);
        output.setOnClickListener(setListener);
        search.setOnClickListener(setListener);
/*        log.setOnClickListener(setListener);*/


    }

    private View.OnClickListener setListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.input_btn:
                    out.println("income");
                    out.flush();
                    IntentIntegrator inputScan = new IntentIntegrator(MainActivity.this);
                    inputScan.setCaptureActivity(CaptureActivityOrientation.class);
                    inputScan.setOrientationLocked(true);
                    inputScan.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    Intent inputIntent = inputScan.createScanIntent();
                    startActivityForResult(inputIntent, 1);
                    break;
                case R.id.output_btn:
                    out.println("outcome");
                    out.flush();
                    IntentIntegrator outputScan = new IntentIntegrator(MainActivity.this);
                    outputScan.setCaptureActivity(CaptureActivityOrientation.class);
                    outputScan.setOrientationLocked(true);
                    outputScan.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    Intent outputIntent = outputScan.createScanIntent();
                    startActivityForResult(outputIntent, 2);
                    break;
                case R.id.search_btn:
                    intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                    break;
/*                case R.id.log_btn:
                    showAlert();
                    break;*/
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1 :
                if (resultCode == Activity.RESULT_OK) {

                    String bNum = data.getStringExtra("SCAN_RESULT");

                    Intent intent = new Intent(MainActivity.this, InputActivity.class);
                    intent.putExtra("bNum", bNum);
                    startActivityForResult(intent, 1);
                }
                break;

            case 2 :
                if (resultCode == Activity.RESULT_OK) {

                    String bNum = data.getStringExtra("SCAN_RESULT");

                    Intent intent = new Intent(MainActivity.this, OutputActivity.class);
                    intent.putExtra("bNum", bNum);
                    startActivityForResult(intent, 2);
                }
                break;
        }
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Access Denied...");
        final TextView tv = new TextView(this);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    public void startConnect(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Code");

        final EditText edit = new EditText(this);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(8);
        edit.setFilters(FilterArray);
        edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(edit);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                String ID = edit.getText().toString();
                out = new PrintWriter(DataThread.getInstance().getWriter(), true);
                out.println(ID);
                out.flush();
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
