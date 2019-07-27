package com.byzoro.postdata.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.byzoro.postdata.demo.config.Config;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.*;

@Component
@Order(value=1)
public class PostData implements ApplicationRunner {
    @Resource
    private Config config;

    public String postData(Config config) {
        String filePath = config.getFilePath();
        if(!filePath.equals("")) {
            File file = new File(filePath);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
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
                        String post = HttpsRequest.sslRequestPostString(s, "UTF-8", config);
                        System.out.println(post);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            return null;
        }
    }
        @Override
        public void run (ApplicationArguments args) throws Exception {
            postData(config);
        }

}