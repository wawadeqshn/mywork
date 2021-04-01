package com.zetyun.mywork.util;

import java.util.Random;
import java.util.Set;
/**
 * @description: 生成指定区间的随机数
 * @author: dingxy
 * @create: 2021-04-01 10:14:10
 **/
public interface RandomUtil {

    Random random = new Random();

    /**
     * @param min
     * @param max
     * @return Random number
     */

    static int getRandomIntInRange(int min, int max) {
        return random.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    /**
     * 生成排除{@code exclude} 在内的随机数
     *
     * @param min
     * @param max
     * @param exclude
     * @return Random number
     */
    static int getRandomIntInRangeWithExclude(int min, int max, Set<Integer> exclude) {
        if (min == max) {
            throw new IllegalArgumentException("min and max can not equal");
        }
        return random.ints(min, (max + 1)).filter((r) -> !exclude.contains(r)).limit(1).findFirst().getAsInt();
    }

    /**
     * @param min
     * @param max
     * @return Random number string
     */
    static String getRandomStringInRange(int min, int max) {
        return String.valueOf(random.ints(min, (max + 1)).limit(1).findFirst().getAsInt());
    }

    /*static void main(String[] args) {

        for (; ; ) {
            System.out.println(getRandomStringInRange(10000000, 99999999));
            System.out.println(getRandomIntInRange(100, 999));
        }
    }*/
}