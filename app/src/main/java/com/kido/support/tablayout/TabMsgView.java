package com.kido.support.tablayout;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 用于需要圆角矩形框背景的TextView的情况,减少直接使用TextView时引入的shape资源文件
 *
 * @author Kido
 */
public class TabMsgView extends TextView {
    private Context context;
    private GradientDrawable gd_background = new GradientDrawable();
    private int backgroundColor;
    private int cornerRadius;
    private int strokeWidth;
    private int strokeColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;

    public TabMsgView(Context context) {
        this(context, null);
    }

    public TabMsgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabMsgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isRadiusHalfHeight()) {
            setCornerRadius(getHeight() / 2);
        } else {
            setBgSelector();
        }
    }


    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBgSelector();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = dp2px(cornerRadius);
        setBgSelector();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = dp2px(strokeWidth);
        setBgSelector();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setBgSelector();
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        setBgSelector();
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        setBgSelector();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = this.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    private void setDrawable(GradientDrawable gd, int color, int strokeColor) {
        gd.setColor(color);
        gd.setCornerRadius(cornerRadius);
        gd.setStroke(strokeWidth, strokeColor);
    }

    public void setBgSelector() {
        StateListDrawable bg = new StateListDrawable();

        setDrawable(gd_background, backgroundColor, strokeColor);
        bg.addState(new int[]{-android.R.attr.state_pressed}, gd_background);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//16
            setBackground(bg);
        } else {
            //noinspection deprecation
            setBackgroundDrawable(bg);
        }
    }
}
