package com.byzoro.postdata.demo.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class SendData implements Runnable {
    private String topic;
    private String filePath;
    private String password;
    private String certificate;
    private String url;

    public SendData(String topic, String filePath, String password, String certificate, String url) {
        this.topic = topic;
        this.filePath = filePath;
        this.password = password;
        this.certificate = certificate;
        this.url = url;
    }

    @Override
    public void run() {
        String postUrl = url + "/" + topic + "/" + "1";
        String path = filePath + "\\" + topic + ".json";
        if (!filePath.equals("")) {
            File file = new File(path);
            if ( !file.exists()) {
                try {
                    boolean newFile = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File newfile = new File(path);
                FileInputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(newfile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedReader bf = new BufferedReader(new InputStreamReader(fileInputStream));
                // 按行读取字符串
                String str = null;
                while (true) {
                    while (true) {
                        try {
                            str = bf.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (str == null || str.equals("")) {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        try {
                            JSONObject jsonObject = JSONObject.parseObject(str);
                            String s = jsonObject.toJSONString();
                            System.out.println(s);
                            String post = HttpsRequest.sslRequestPostString(s, "UTF-8", password, postUrl, certificate);
                            System.out.println(post);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
