package com.itchun.retryer;

import feign.RetryableException;
import feign.Retryer;

public class DefaultFeignRetry implements Retryer {

    @Override
    public void continueOrPropagate(RetryableException e) {
        throw e;
    }

    @Override
    public Retryer clone() {

        /**
         * period：周期，重试间隔时间，毫秒
         * maxPeriod：最大周期，重试间隔时间按照一定的规则逐渐增大，但不能超过最大周期
         * maxAttempts：最大尝试次数，重试次数，这里2次，是指加上本身主动请求的一次，重试一次，一共两次，所以这里设置1的话，其实就不会重试
         */
        return new Default(5000, Integer.MAX_VALUE, 1);
    }
}
