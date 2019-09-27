package com.yffd.jysg.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApp {
    private static Logger LOG = LoggerFactory.getLogger(MyApp.class);

    public static void main(String[] args) throws Exception {
//        LOG.info("测试》》》》》》》》》》》");
//        if (true) throw new Exception("测试");
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            LOG.info("测试123》》》》》》》》》》》", e);
        }

    }
}
