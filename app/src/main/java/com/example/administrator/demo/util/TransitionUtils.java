package com.example.administrator.demo.util;

import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * author: lsh
 * Date: 2017-03-08
 * Time: 20:21
 */

public class TransitionUtils {
    private static int MAX_IMAGE_SIZE = (1024 * 1024);
    /**
     * Get a copy of bitmap of given drawable, return null if intrinsic size is zero
     */
    public static Bitmap createDrawableBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width <= 0 || height <= 0) {
            return null;
        }
        float scale = Math.min(1f, ((float)MAX_IMAGE_SIZE) / (width * height));
        if (drawable instanceof BitmapDrawable && scale == 1f) {
            // return same bitmap if scale down not needed
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int bitmapWidth = (int) (width * scale);
        int bitmapHeight = (int) (height * scale);
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Rect existingBounds = drawable.getBounds();
        int left = existingBounds.left;
        int top = existingBounds.top;
        int right = existingBounds.right;
        int bottom = existingBounds.bottom;
        drawable.setBounds(0, 0, bitmapWidth, bitmapHeight);
        drawable.draw(canvas);
        drawable.setBounds(left, top, right, bottom);
        return bitmap;
    }
    /**
     * Creates a Bitmap of the given view, using the Matrix matrix to transform to the local
     * coordinates. <code>matrix</code> will be modified during the bitmap creation.
     *
     * <p>If the bitmap is large, it will be scaled uniformly down to at most 1MB size.</p>
     * @param view The view to create a bitmap for.
     * @param matrix The matrix converting the view local coordinates to the coordinates that
     *               the bitmap will be displayed in. <code>matrix</code> will be modified before
     *               returning.
     * @param bounds The bounds of the bitmap in the destination coordinate system (where the
     *               view should be presented. Typically, this is matrix.mapRect(viewBounds);
     * @return A bitmap of the given view or null if bounds has no width or height.
     */
    public static Bitmap createViewBitmap(View view, Matrix matrix, RectF bounds) {
        Bitmap bitmap = null;
        int bitmapWidth = Math.round(bounds.width());
        int bitmapHeight = Math.round(bounds.height());
        if (bitmapWidth > 0 && bitmapHeight > 0) {
            float scale = Math.min(1f, ((float)MAX_IMAGE_SIZE) / (bitmapWidth * bitmapHeight));
            bitmapWidth *= scale;
            bitmapHeight *= scale;
            matrix.postTranslate(-bounds.left, -bounds.top);
            matrix.postScale(scale, scale);
            bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.concat(matrix);
            view.draw(canvas);
        }
        return bitmap;
    }
    public static class MatrixEvaluator implements TypeEvaluator<Matrix> {
        float[] mTempStartValues = new float[9];
        float[] mTempEndValues = new float[9];
        Matrix mTempMatrix = new Matrix();
        @Override
        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            startValue.getValues(mTempStartValues);
            endValue.getValues(mTempEndValues);
            for (int i = 0; i < 9; i++) {
                float diff = mTempEndValues[i] - mTempStartValues[i];
                mTempEndValues[i] = mTempStartValues[i] + (fraction * diff);
            }
            mTempMatrix.setValues(mTempEndValues);
            return mTempMatrix;
        }
    }
}
