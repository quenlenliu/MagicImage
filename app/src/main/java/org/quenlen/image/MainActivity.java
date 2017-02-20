package org.quenlen.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
                startProgress();
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
                wallpaper = Bitmap.createScaledBitmap(wallpaper, screenshot.getWidth(), screenshot.getHeight(), false);

                Bitmap result = MagicImage.composeBitmap(wallpaper, screenshot);
                MagicImage.gaussianBlur(result);
                return result;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                mResult.setImageBitmap(bitmap);
            }

        }.execute(getBaseContext());
    }

}
