package com.byzoro.postdata.demo.utils;

import com.byzoro.postdata.demo.config.Config;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
@Order(value=1)
public class PostData implements ApplicationRunner {
    @Resource
    private Config config;
        @Override
        public void run (ApplicationArguments args) throws Exception {
            String[] split = config.getTopic().split(",");
            for (String s:split
            ) {
                Thread thread = new Thread(new SendData(s,config.getFilePath(),config.getPassword(),config.getCertificate(),config.getUrl()));
                thread.start();
            }
        }

}