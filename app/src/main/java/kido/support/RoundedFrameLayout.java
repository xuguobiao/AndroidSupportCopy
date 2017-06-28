package kido.support;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;


/**
 * <p>Create time: 2017/6/28 18:25</p>
 *
 * @author Kido
 */

public class RoundedFrameLayout extends FrameLayout {

    private float mTopLeftRadius;
    private float mTopRightRadius;
    private float mBottomLeftRadius;
    private float mBottomRightRadius;

    private Paint mRoundPaint;
    private Paint mImagePaint;

    public RoundedFrameLayout(Context context) {
        this(context, null);
    }

    public RoundedFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mRoundPaint = new Paint();
        mRoundPaint.setColor(Color.WHITE);
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setStyle(Paint.Style.FILL);
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mImagePaint = new Paint();
        mImagePaint.setXfermode(null);
    }

    public void setRadius(float radius) {
        setRadius(radius, radius, radius, radius);
    }

    public void setRadius(float topLeftRadius, float topRightRadius, float bottomLeftRadius, float bottomRightRadius) {
        mTopLeftRadius = topLeftRadius;
        mTopRightRadius = topRightRadius;
        mBottomLeftRadius = bottomLeftRadius;
        mBottomRightRadius = bottomRightRadius;
    }

    ////实现1
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        int width = getWidth();
//        int height = getHeight();
//        Path path = new Path();
//        path.moveTo(0, mTopLeftRadius);
//        path.arcTo(new RectF(0, 0, mTopLeftRadius * 2, mTopLeftRadius * 2), -180, 90);
//        path.lineTo(width - mTopRightRadius, 0);
//        path.arcTo(new RectF(width - 2 * mTopRightRadius, 0, width, mTopRightRadius * 2), -90, 90);
//        path.lineTo(width, height - mBottomRightRadius);
//        path.arcTo(new RectF(width - 2 * mBottomRightRadius, height - 2 * mBottomRightRadius, width, height), 0, 90);
//        path.lineTo(mBottomLeftRadius, height);
//        path.arcTo(new RectF(0, height - 2 * mBottomLeftRadius, mBottomLeftRadius * 2, height), 90, 90);
//        path.close();
//        canvas.clipPath(path);
//        super.dispatchDraw(canvas);
//    }
    ////实现2
    //    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        drawTopLeft(canvas);//用PorterDuffXfermode
//        drawTopRight(canvas);//用PorterDuffXfermode
//        drawBottomLeft(canvas);//用PorterDuffXfermode
//        drawBottomRight(canvas);//用PorterDuffXfermode
//    }
    ////实现3
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas newCanvas = new Canvas(bitmap);
//        super.dispatchDraw(newCanvas);
//        drawTopLeft(newCanvas);
//        drawTopRight(newCanvas);
//        drawBottomLeft(newCanvas);
//        drawBottomRight(newCanvas);
//        canvas.drawBitmap(bitmap, 0, 0, mImagePaint);
////        invalidate();
//    }

    //实现4
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        canvas.restore();
    }

    private void drawTopLeft(Canvas canvas) {
        if (mTopLeftRadius > 0) {
            Path path = new Path();
            path.moveTo(0, mTopLeftRadius);
            path.lineTo(0, 0);
            path.lineTo(mTopLeftRadius, 0);
            path.arcTo(new RectF(0, 0, mTopLeftRadius * 2, mTopLeftRadius * 2),
                    -90, -90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    private void drawTopRight(Canvas canvas) {
        if (mTopRightRadius > 0) {
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mTopRightRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, mTopRightRadius);
            path.arcTo(new RectF(width - 2 * mTopRightRadius, 0, width,
                    mTopRightRadius * 2), 0, -90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    private void drawBottomLeft(Canvas canvas) {
        if (mBottomLeftRadius > 0) {
            int height = getHeight();
            Path path = new Path();
            path.moveTo(0, height - mBottomLeftRadius);
            path.lineTo(0, height);
            path.lineTo(mBottomLeftRadius, height);
            path.arcTo(new RectF(0, height - 2 * mBottomLeftRadius,
                    mBottomLeftRadius * 2, height), 90, 90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    private void drawBottomRight(Canvas canvas) {
        if (mBottomRightRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mBottomRightRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - mBottomRightRadius);
            path.arcTo(new RectF(width - 2 * mBottomRightRadius, height - 2
                    * mBottomRightRadius, width, height), 0, 90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

}