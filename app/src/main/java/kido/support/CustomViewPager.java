package kido.support;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Kido
 */
public class CustomViewPager extends ViewPager {

    private boolean isPagingEnabled = true;
    private Rect mPagingRect = new Rect(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    private boolean isTouchCanScroll = true;

    public CustomViewPager(Context context) {
        super(context);
        init(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isTouchCanScroll = isTouchInPagingRange(event.getX(), event.getY());
        }
        return this.isPagingEnabled && isTouchCanScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isTouchCanScroll = isTouchInPagingRange(event.getX(), event.getY());
        }
        return this.isPagingEnabled && isTouchCanScroll && super.onInterceptTouchEvent(event);
    }

    /**
     * 设置是否响应touch滑动
     *
     * @param enable
     */
    public void setPagingEnabled(boolean enable) {
        this.isPagingEnabled = enable;
    }

    public boolean isPagingEnabled() {
        return this.isPagingEnabled;
    }

    /**
     * 设置响应touch滑动的范围
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setPagingRect(int left, int top, int right, int bottom) {
        mPagingRect.left = left;
        mPagingRect.top = top;
        mPagingRect.right = right;
        mPagingRect.bottom = bottom;
    }

    public void setPagingRect(Rect rect) {
        setPagingRect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public Rect getPagingRect() {
        return mPagingRect;
    }

    private boolean isTouchInPagingRange(float x, float y) {
        return mPagingRect.contains((int) x, (int) y);
    }

}