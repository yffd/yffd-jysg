package com.yffd.jysg.java.v7.jvm.p6;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GCTest {
	
	public static void main(String[] args) {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
            System.out.println(garbageCollectorMXBean.getName());
        }

    }
}
