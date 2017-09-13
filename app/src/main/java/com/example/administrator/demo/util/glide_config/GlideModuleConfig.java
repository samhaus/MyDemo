package com.example.administrator.demo.util.glide_config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.example.administrator.demo.util.DeviceUtil;

import java.io.File;

/**
 * Created by starry on 2016/11/14.
 */

public class
GlideModuleConfig implements GlideModule {

    public static final String DIR_IMAGE_CACHE = "image_cache";
    public static final int DISK_CACHE_SIZE = 100 * 1024 * 1024;

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        if (DeviceUtil.isSDCardEnable()) {
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DIR_IMAGE_CACHE, DISK_CACHE_SIZE));
            builder.setDiskCache(new DiskCache.Factory() {
                @Override
                public DiskCache build() {
                    File cacheLocation = new File(context.getExternalCacheDir(), DIR_IMAGE_CACHE);
                    cacheLocation.mkdirs();
                    return DiskLruCacheWrapper.get(cacheLocation, DISK_CACHE_SIZE);
                }
            });
        } else {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, DIR_IMAGE_CACHE, DISK_CACHE_SIZE));
            builder.setDiskCache(new DiskCache.Factory() {
                @Override
                public DiskCache build() {
                    File cacheLocation = new File(context.getCacheDir(), DIR_IMAGE_CACHE);
                    cacheLocation.mkdirs();
                    return DiskLruCacheWrapper.get(cacheLocation, DISK_CACHE_SIZE);
                }
            });
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
