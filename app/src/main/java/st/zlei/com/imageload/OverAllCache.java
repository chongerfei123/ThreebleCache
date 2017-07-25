package st.zlei.com.imageload;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;

/**
 * Created by zl on 2017/7/24.
 */

public class OverAllCache extends Cache {
    private static final String TAG = "ImageDown";

    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache = new FileCache();

    @Override
    Bitmap get(String url) throws IOException {
        Bitmap bitmap;
        bitmap = memoryCache.get(url);
        if (bitmap == null){
            bitmap = fileCache.get(url);
            if (bitmap != null){
                memoryCache.put(url,bitmap);
            }
        }
        return bitmap;
    }

    @Override
    void put(String url, Bitmap bitmap) {
        memoryCache.put(url,bitmap);
        fileCache.put(url,bitmap);
    }
}
