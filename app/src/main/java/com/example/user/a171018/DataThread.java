package com.example.user.a171018;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by User on 2017-10-08.
 */

public class DataThread {
    private static volatile DataThread singletonInstance = new DataThread();

    JSONObject testO;

    static ArrayList<String > codeList  = new ArrayList<>();
    static ArrayList<String >SNList  = new ArrayList<>();
    static ArrayList<String >typeList  = new ArrayList<>();
    static ArrayList<String >nameList  = new ArrayList<>();
    static ArrayList<String >companyList  = new ArrayList<>();
    static ArrayList<String >nationList  = new ArrayList<>();
    static ArrayList<String >priceList  = new ArrayList<>();
    static ArrayList<String >countList  = new ArrayList<>();

    private String logData = "";
    private String productData ="";
    //private String ip = "192.168.43.228";
    private String ip = "172.20.10.4";
    private int port = 7777;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public static DataThread getInstance(){
        if (singletonInstance == null) {
        synchronized (DataThread.class) {
            if (singletonInstance == null) {
                singletonInstance = new DataThread();
            }
        }
    }
        return singletonInstance;
}

    private DataThread() {
        mainConnect.setDaemon(true);
        mainConnect.start();
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    private Thread mainConnect = new Thread(){
        public void run(){
            try {
                setSocket(ip, port);
            } catch (IOException e) {

            }
        }
    };

    public void setSocket(String ip, int port) throws IOException {
        try {
            socket = new Socket(ip, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            recvData.start();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private Thread recvData = new Thread() {
        public void run() {
            try {
                PrintWriter out = new PrintWriter(writer, true);
                Log.d("recvData", "data running");
                while (true) {
                    String data = reader.readLine();
                    Log.d("recvData", data);
                    if(data.equals("data")) {
                        out.println("data");
                        productData = reader.readLine();
                        Log.e("productData", productData);
                        ListCreate();
                    }
                    if(data.equals("log")) {
                        out.println("log");
                        logData = reader.readLine();
                        Log.e("logData", logData);
                    }
                    if(data.equals("image")){
                        out.println("image");
                        get_fileMessage();
                        Log.e("ImageData", "Success!");
                    }
                    out.flush();
                }
            } catch (Exception e) {
                Log.e("recvData", e.toString());
                e.printStackTrace();
            }
        }
    };

    private void get_fileMessage() {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WareHouse_product_Image/";
        File file = new File(dirPath);
        if (!file.exists()) {
            Log.e("makeDir", "not file");
            file.mkdir();
            Log.e("makeDir", "make file");
        }

        Log.e("fileMsg", dirPath);
        try {
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            int filesCount = dis.readInt();
            File[] files = new File[filesCount];
            for (int i = 0; i < filesCount; i++) {
                long fileLength = dis.readLong();
                String fileName = dis.readUTF();
                System.out.println("recvDataName : " + fileName);
                files[i] = new File(dirPath + fileName);
                FileOutputStream fos = new FileOutputStream(files[i]);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                for (int j = 0; j < fileLength; j++)
                    bos.write(bis.read());
                bos.flush();
            }
            //dis.close();
        } catch (Exception e) {
            Log.e("get_fileMessage() ERROR", e.toString());
        }
    }

    protected void ListCreate() {
        testO = new JSONObject();

        try {
            JSONArray testJ = new JSONArray(productData);

            Log.e("testJ.value", testJ.toString());
            Log.e("tetJ ",String.valueOf(testJ.length()));

            for(int i = 0; i < testJ.length(); i++) {
                testO = testJ.getJSONObject(i);

                String codeValue = testO.getString("code");
                String SNValue = testO.getString("SN");
                String typeValue = testO.getString("type");
                String nameValue = testO.getString("name");
                String companyValue = testO.getString("company");
                String nationValue = testO.getString("nation");
                String priceValue = testO.getString("price");
                String countValue = testO.getString("count");

                if(codeList.contains(codeValue)) {
                    codeList.set(i,codeValue);
                    SNList.set(i,SNValue);
                    typeList.set(i,typeValue);
                    nameList.set(i,nameValue);
                    companyList.set(i,companyValue);
                    nationList.set(i,nationValue);
                    priceList.set(i,priceValue);
                    countList.set(i,countValue);
                }
                else {
                    codeList.add(codeValue);
                    SNList.add(SNValue);
                    typeList.add(typeValue);
                    nameList.add(nameValue);
                    companyList.add(companyValue);
                    nationList.add(nationValue);
                    priceList.add(priceValue);
                    countList.add(countValue);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("tetJ ",String.valueOf(nationList.toString()));
    }
}
