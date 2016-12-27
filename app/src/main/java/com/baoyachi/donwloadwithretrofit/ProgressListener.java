package com.baoyachi.donwloadwithretrofit;

/**
 * 日期：16/3/17 20:37
 * <p>
 * 描述：
 * 修复：
 */
public interface ProgressListener
{
    void update(long bytesRead, long contentLength, boolean done);
}
