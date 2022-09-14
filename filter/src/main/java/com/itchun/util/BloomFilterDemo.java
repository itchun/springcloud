package com.itchun.util;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

// 介绍: https://zhuanlan.zhihu.com/p/94433082
public class BloomFilterDemo {
    public static void main(String[] args) {
        test2();
    }

    // 默认误报率 0.03
    public static void test1() {
        int total = 1000000; // 总数量
        BloomFilter<CharSequence> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total);

        // 初始化 1000000 条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put("" + i);
        }

        // 判断值是否存在过滤器中
        int count = 0;
        for (int i = 0; i < total + 10000; i++) {
            if (bf.mightContain("" + i)) {
                count++;
            }
        }
        System.out.println("已匹配数量 " + count);

        // 已匹配数量 1000309
        // 出现误报 309
    }

    // 指定误报率 0.002
    public static void test2() {
        int total = 1000000; // 总数量
        BloomFilter<CharSequence> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total, 0.002);

        // 初始化 1000000 条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put("" + i);
        }

        // 判断值是否存在过滤器中
        int count = 0;
        for (int i = 0; i < total + 10000; i++) {
            if (bf.mightContain("" + i)) {
                count++;
            }
        }
        System.out.println("已匹配数量 " + count);

        // 已匹配数量 1000021
        // 出现误报 21
    }
}