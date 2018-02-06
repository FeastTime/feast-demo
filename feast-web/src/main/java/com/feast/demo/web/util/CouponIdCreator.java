package com.feast.demo.web.util;

public class CouponIdCreator {

    /**
     * 测试方法
     * @param args 入参
     */
    public static void main(String[] args) {

        CouponIdCreator couponIdCreator = new CouponIdCreator();
        for (int i = 0; i < 300; i++) {

            String id = couponIdCreator.nextId();
            System.out.println(id);

        }
    }

    // 序列最大值
    private final static int MAX_SEQUENCE = 1023;

    // 序列号
    private static long sequence = 0L;


    /**
     * 产生下一个ID
     * @return ID
     */
    public static synchronized String nextId() {

        long currStemp = System.currentTimeMillis();

        sequence++;
        sequence = sequence & MAX_SEQUENCE;

        return Long.toHexString(sequence)+ "" + Long.toHexString(currStemp);
    }
}

