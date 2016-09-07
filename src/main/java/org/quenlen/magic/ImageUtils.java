package org.quenlen.magic;

import android.graphics.Bitmap;

/**
 * Provider image process method
 */
public class ImageUtils {
    private static final String LIBRARY_NAME = "ImageUtils";

    static {
        System.loadLibrary(LIBRARY_NAME);
    }

    public static void gaussianBlur(Bitmap bitmap) {
        nGaussianBlur(bitmap, 32);
    }

    private static native void nGaussianBlur(Bitmap bitmap, int radius);

    /**
     * Compose bitmap.
     * @param result
     * @param bitmap
     * @return
     */
    public static Bitmap composeBitmap(Bitmap result, Bitmap bitmap) {
        if (result.getWidth() != bitmap.getWidth() || result.getHeight() != bitmap.getHeight()) {
            throw new RuntimeException("Two bitmap's size must equal");
        } else {
            nComposeBitmap(result, bitmap);
        }
        return result;
    }

    private static native void nComposeBitmap(Bitmap result, Bitmap bitmap);
}
