package st.zlei.com.imageload;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.Random;

/**
 * Created by zl on 2017/7/24.
 */

public class MemoryCache extends Cache {
    private static final String TAG = "ImageDown";

    public MemoryCache() {
        this.memortCache = new LruCache<String,Bitmap>((int) (Runtime.getRuntime().maxMemory()/1024/4)){

            // 返回每个对象的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    private LruCache<String,Bitmap> memortCache ;
    @Override
    Bitmap get(String url) {
        Bitmap bitmap = memortCache.get(url);
        if (bitmap != null){
            Log.e(TAG, "get: 图片来源内存" );
            return bitmap;
        }else {
            return null;
        }
    }

    @Override
    void put(String url, Bitmap bitmap) {
        memortCache.put(url,bitmap);
    }
}
