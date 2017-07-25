package st.zlei.com.imageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by zl on 2017/7/24.
 */

public class FileCache extends Cache {

    //文件目录
    private static final String LOCAL_CACHE_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/Cache";
    private static final String TAG = "ImageDown";

    @Override
    Bitmap get(String url) {
        try {
            File cacheFile = new File(LOCAL_CACHE_PATH, MD5Encoder.encode(url));

            if (cacheFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        cacheFile));
                Log.e(TAG, "get: 图片来源文件");
                return bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    void put(String url, Bitmap bitmap) {
        File dir = new File(LOCAL_CACHE_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            boolean b = dir.mkdirs();// 创建文件夹
            if (!b){
                Log.e(TAG, "put: 创建文件夹失败");
            }

        }

        try {
            String fileName = MD5Encoder.encode(url);

            File cacheFile = new File(dir, fileName);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(
                    cacheFile));// 参1:图片格式;参2:压缩比例0-100; 参3:输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
