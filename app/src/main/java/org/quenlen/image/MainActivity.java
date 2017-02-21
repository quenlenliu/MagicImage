package org.quenlen.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.quenlen.magic.MagicImage;


public class MainActivity extends AppCompatActivity {

    private ImageView mPre;
    private ImageView mPost;
    private ImageView mResult;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mPre = (ImageView) findViewById(R.id.pre);
        mPost = (ImageView) findViewById(R.id.post);
        mResult = (ImageView) findViewById(R.id.result);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResult.getVisibility() == View.GONE) {
                    mResult.setVisibility(View.VISIBLE);
                    startProgress();
                } else {
                    mResult.setVisibility(View.GONE);
                }
            }
        });

        mPre.setImageResource(R.mipmap.screen_shot);
        mPost.setImageResource(R.mipmap.wallpaper);


    }

    private void startProgress() {

        new AsyncTask<Context,Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Context... params) {
                Bitmap screenshot = BitmapFactory.decodeResource(getResources(), R.mipmap.screen_shot);
                Bitmap wallpaper = BitmapFactory.decodeResource(getResources(), R.mipmap.wallpaper);
                //wallpaper = Bitmap.createScaledBitmap(wallpaper, screenshot.getWidth(), screenshot.getHeight(), false);

                Bitmap result = getBlurScreen(wallpaper, screenshot);

                return result;
            }


            public float getCurrentX() {
                return 0.5f;
            }


            public Bitmap getBlurScreen(Bitmap wallpaper, Bitmap screenShot) {
                final Rect wallpaperRect = new Rect(0, 0, wallpaper.getWidth(), wallpaper.getHeight());
                final Rect screenShotRect = new Rect(0, 0, screenShot.getWidth(), screenShot.getHeight());
                Rect area = new Rect();
                calculateArea(area, wallpaperRect, screenShotRect);
                wallpaper = Bitmap.createBitmap(wallpaper, area.left, area.top,
                        area.width(), area.height());
                wallpaper = Bitmap.createScaledBitmap(wallpaper, screenShot.getWidth(), screenShot.getHeight(), false);
                Bitmap result = MagicImage.composeBitmap(wallpaper,screenShot);
                MagicImage.gaussianBlur(result);
                return result;
            }

            private void calculateArea(Rect area, final Rect wallpaperRect,final Rect screenShotRect) {
                float scale = Math.max(1f, Math.max(screenShotRect.width() / (float) wallpaperRect.width(),
                        screenShotRect.height() / (float) wallpaperRect.height()));
                scaleRect(wallpaperRect, scale);


                float xOffset = getCurrentX();
                float yOffset = 0.5f;
                // We assume height equals.

                float scaledWidth = wallpaperRect.width() - screenShotRect.width();
                float scaledHeight = wallpaperRect.height() - screenShotRect.height();

                area.left =(int) (xOffset * scaledWidth + 0.5f);
                area.top = (int) (yOffset * scaledHeight);
                area.right = (int)(area.left + screenShotRect.width()  + .5f);
                area.bottom = (int)(area.top + screenShotRect.height() + .5f);
                scaleRect(area, 1.0f / scale);
            }

            void scaleRect(Rect rect, float scale) {
                rect.left *= scale;
                rect.top *= scale;
                rect.bottom *= scale;
                rect.right *= scale;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                mResult.setImageBitmap(bitmap);
            }

        }.execute(getBaseContext());
    }

}
