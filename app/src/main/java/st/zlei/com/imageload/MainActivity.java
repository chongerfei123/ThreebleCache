package st.zlei.com.imageload;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

//图片地址: http://img02.tooopen.com/images/20131206/sy_50419061827.jpg
public class MainActivity extends AppCompatActivity {
    private static String imageURL = "http://diy.qqjay.com/u/files/2012/0209/2f45e7e0d06a69a974949c0872a5ec5a.jpg";
    private Button down_button;
    private Button clear_button;
    private ImageView image;
    private ImageUtils imageUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        down_button = (Button) findViewById(R.id.down_button);
        clear_button = (Button) findViewById(R.id.dclear_button);
        image = (ImageView) findViewById(R.id.image);
        imageUtils = new ImageUtils();
        //这里传入使用的缓存类型
        imageUtils.setImageCache(new Cache() {
            @Override
            Bitmap get(String url) throws IOException {
                return null;
            }

            @Override
            void put(String url, Bitmap bitmap) {

            }
        });

        down_button.setText("显示图片");
        down_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    imageUtils.ShowPic(imageURL, image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(null);
            }
        });
    }
}
