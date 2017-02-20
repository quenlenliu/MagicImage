package org.quenlen.magic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Provider image process method
 */
public class MagicImage {
    private static final String LIBRARY_NAME = "MagicImage";

    static {
        System.loadLibrary(LIBRARY_NAME);
    }

    public static void gaussianBlur(Bitmap bitmap) {
        gaussianBlur(bitmap, 32);
    }

    public static void gaussianBlur(Bitmap bitmap, int radius) {
        nGaussianBlur(bitmap, radius);
    }

    private static native void nGaussianBlur(Bitmap bitmap, int radius);

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
            result = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.RGB_565);
        }
        Rect resultRect = new Rect(0, 0, result.getWidth(), result.getHeight());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(background, backRect, resultRect, mPaint);
        canvas.drawBitmap(foreground, foreRect, resultRect, mPaint);
        return result;

    }

    private static native void nComposeBitmap(Bitmap result, Bitmap bitmap);
}