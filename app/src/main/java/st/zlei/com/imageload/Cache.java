package st.zlei.com.imageload;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by zl on 2017/7/24.
 */
//缓存的抽象类
public abstract class Cache {
    abstract Bitmap get(String url) throws IOException;
    abstract void put(String url,Bitmap bitmap);
}
