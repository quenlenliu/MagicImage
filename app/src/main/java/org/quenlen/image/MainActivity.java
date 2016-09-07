package org.quenlen.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import org.quenlen.magic.MagicImage;

public class MainActivity extends AppCompatActivity {

    private ImageView mPre;
    private ImageView mPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPre = (ImageView) findViewById(R.id.pre);
        mPost = (ImageView) findViewById(R.id.post);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256 * bitmap.getHeight() / bitmap.getWidth(), true);
        MagicImage.gaussianBlur(bitmap);
        mPost.setImageBitmap(bitmap);
    }

}
