package st.zlei.com.imageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zl on 2017/7/24.
 */

public class ImageUtils {

    private ImageView imageView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GOT_BITMAP) {
                imageView.setImageBitmap((Bitmap) msg.obj);
                Log.e(TAG, "handleMessage: 图片来源网络");
            }
        }
    };
    private static final int GOT_BITMAP = 0;

    private static final String TAG = "ImageDown";

    Cache mCache = new MemoryCache();

    //创建一个线程池,线程数量为cpu的核心数
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //注入缓存类型
    public void setImageCache(Cache imageCache) {
        mCache = imageCache;
    }

    public void ShowPic(String url, ImageView imageView) throws IOException {
        this.imageView = imageView;
        Bitmap bitmap = mCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //没有缓存
        LoadPic(url, imageView);
    }

    private void LoadPic(final String url, final ImageView imageView) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = DownLoadImage(url);

                Message message = handler.obtainMessage();
                message.what = GOT_BITMAP;
                message.obj = bitmap;
                handler.sendMessage(message);

                mCache.put(url, bitmap);
            }
        });
    }

    private Bitmap DownLoadImage(String url) {
        Bitmap bitmap = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                byte[] bytes = response.body().bytes();
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.e(TAG, "DownandSet: decodeByteArray");
            } else {
                Log.e(TAG, "DownLoadImage: 从网络下载图片失败--" + response.message());
            }
            if (bitmap == null) {
                Log.e(TAG, "DownandSet: bitmap==null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
