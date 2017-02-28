package org.quenlen.magic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Provider image process method
 */
public class MagicImage {
    private static final String LIBRARY_NAME = "MagicImage";
    private static final String TAG = "MagicImage";
    private static int STATE = 0;
    private final static int MASK_SUCCESS = 1;
    private final static int MASK_FAILURE = 2;


    static {
        try {
            //System.loadLibrary(LIBRARY_NAME);
            //STATE = MASK_SUCCESS;
        } catch (Exception ex) {
            Log.e(TAG, "Auto load library error");
            STATE = MASK_FAILURE;
        }
    }

    public static void loadLibrary(String libraryPath) {
        if (MASK_SUCCESS != STATE) {
            try {
                System.load(libraryPath);
                STATE = MASK_SUCCESS;
            } catch (Exception ex) {
                Log.e(TAG, "Manual load library error");
                STATE = MASK_FAILURE;
            }
        }
    }

    public static void gaussianBlur(Bitmap bitmap) {
        gaussianBlur(bitmap, 32, false);
    }

    public static void gaussianBlur(Bitmap bitmap, boolean ignoreAlpha) {
       nGaussianBlur(bitmap, 32, ignoreAlpha);
    }

    public static void gaussianBlur(Bitmap bitmap, int radius) {
        nGaussianBlur(bitmap, radius, false);
    }

    public static void gaussianBlur(Bitmap bitmap, int radiua, boolean ignoreAlpha) {
        nGaussianBlur(bitmap, radiua, ignoreAlpha);
    }

    private static native void nGaussianBlur(Bitmap bitmap, int radius, boolean ignoreAlpha);

    /**
     * Compose bitmap.
     * @param background
     * @param foreground
     * @return
     */
    public synchronized static Bitmap composeBitmap(@NonNull Bitmap background,@NonNull Bitmap foreground) {
        Rect backRect = new Rect(0, 0, background.getWidth(), background.getHeight());
        Rect foreRect = new Rect(0, 0, foreground.getWidth(), foreground.getHeight());
        return composeBitmap(backRect.width(), backRect.height(), background, backRect, foreground, foreRect);
    }

    private static Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);


    public static Bitmap composeBitmap(int dstWidth, int dstHeight, @NonNull Bitmap background, @NonNull Rect backRect,@NonNull Bitmap foreground, @NonNull Rect foreRect) {
        Bitmap result = background;
        if (background.getWidth() != dstWidth || background.getHeight() != dstHeight) {
            result = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        }
        Rect resultRect = new Rect(0, 0, result.getWidth(), result.getHeight());
        Canvas canvas = new Canvas(result);
        if (result != background) {
            canvas.drawBitmap(background, backRect, resultRect, mPaint);
        }
        canvas.drawBitmap(foreground, foreRect, resultRect, mPaint);
        return result;

    }

    private static native void nComposeBitmap(Bitmap result, Bitmap bitmap);
}