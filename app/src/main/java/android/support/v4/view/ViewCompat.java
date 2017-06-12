package android.support.v4.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * @author Kido
 */

public class ViewCompat {
    /**
     * Horizontal layout direction of this view is from Left to Right.
     */
    public static final int LAYOUT_DIRECTION_LTR = 0;

    /**
     * Horizontal layout direction of this view is from Right to Left.
     */
    public static final int LAYOUT_DIRECTION_RTL = 1;

    private static final int SIXTY_FPS_INTERVAL = 1000 / 60;

    private static final long FAKE_FRAME_TIME = 10;


    private ViewCompat() {
    }

    interface ViewCompatImpl {
        void postOnAnimation(View view, Runnable runnable);

        void postInvalidateOnAnimation(View view);

        void setElevation(View view, float elevation);

        int getLayoutDirection(View view);

        boolean isLaidOut(View view);

        void setPaddingRelative(View view, int start, int top, int end, int bottom);

    }

    static class BaseViewCompatImpl implements ViewCompatImpl {

        @Override
        public void postOnAnimation(View view, Runnable runnable) {
            view.postDelayed(runnable, getFrameTime());
        }

        @Override
        public void postInvalidateOnAnimation(View view) {
            view.invalidate();
        }

        @Override
        public void setElevation(View view, float elevation) {
        }

        @Override
        public int getLayoutDirection(View view) {
            return LAYOUT_DIRECTION_LTR;
        }

        @Override
        public boolean isLaidOut(View view) {
            return view.getWidth() > 0 && view.getHeight() > 0;
        }

        @Override
        public void setPaddingRelative(View view, int start, int top, int end, int bottom) {
            view.setPadding(start, top, end, bottom);
        }

        long getFrameTime() {
            return FAKE_FRAME_TIME;
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static class JBViewCompatImpl extends BaseViewCompatImpl {

        @Override
        public void postOnAnimation(View view, Runnable runnable) {
            view.postOnAnimation(runnable);
        }

        @Override
        public void postInvalidateOnAnimation(View view) {
            view.postInvalidateOnAnimation();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    static class JBMr1ViewCompatImpl extends JBViewCompatImpl {
        @Override
        public int getLayoutDirection(View view) {
            return view.getLayoutDirection();
        }

        @Override
        public void setPaddingRelative(View view, int start, int top, int end, int bottom) {
            view.setPaddingRelative(start, top, end, bottom);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    static class KitkatViewCompatImpl extends JBMr1ViewCompatImpl {
        @Override
        public boolean isLaidOut(View view) {
            return view.isLaidOut();
        }
    }
    // Other versions impl...

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static class LollipopViewCompatImpl extends JBMr1ViewCompatImpl {

        @Override
        public void setElevation(View view, float elevation) {
            view.setElevation(elevation);
        }
    }


    /*************************  ********************************/

    static final ViewCompatImpl IMPL;

    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.LOLLIPOP) {
            IMPL = new LollipopViewCompatImpl();
        } else if (version >= Build.VERSION_CODES.KITKAT) {
            IMPL = new KitkatViewCompatImpl();
        } else if (version >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            IMPL = new JBMr1ViewCompatImpl();
        } else if (version >= Build.VERSION_CODES.JELLY_BEAN) {
            IMPL = new JBViewCompatImpl();
        } else {
            IMPL = new BaseViewCompatImpl();
        }

    }


    public static void postOnAnimation(View view, Runnable runnable) {
        IMPL.postOnAnimation(view, runnable);
    }

    public static void postInvalidateOnAnimation(View view) {
        IMPL.postInvalidateOnAnimation(view);
    }

    public static void setElevation(View view, float elevation) {
        IMPL.setElevation(view, elevation);
    }

    public static int getLayoutDirection(View view) {
        return IMPL.getLayoutDirection(view);
    }

    public static boolean isLaidOut(View view) {
        return IMPL.isLaidOut(view);
    }

    public static void setPaddingRelative(View view, int start, int top, int end, int bottom) {
        IMPL.setPaddingRelative(view, start, top, end, bottom);
    }
}
