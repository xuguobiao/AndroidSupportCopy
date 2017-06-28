package kido.support.stickyheader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kido.support.stickyheader.animator.HeaderStickyAnimator;


public abstract class StickyHeaderBuilder {

    protected final Context mContext;

    protected View mHeader;
    protected int mMinHeight;
    protected HeaderAnimator mAnimator;
    protected boolean mAllowTouchBehindHeader;

    protected StickyHeaderBuilder(final Context context) {
        mContext = context;
        mMinHeight = 0;
        mAllowTouchBehindHeader = false;
    }

    public abstract StickyHeader build();

    public static ListViewBuilder stickTo(final ListView listView) {
        return new ListViewBuilder(listView);
    }

    public StickyHeaderBuilder setHeader(final int idHeader, final ViewGroup view) {
        mHeader = view.findViewById(idHeader);
        return this;
    }

    public StickyHeaderBuilder setHeader(final View header) {
        mHeader = header;
        return this;
    }


    public StickyHeaderBuilder minHeightHeaderDim(final int resDimension) {
        mMinHeight = mContext.getResources().getDimensionPixelSize(resDimension);
        return this;
    }

    /**
     * Deprecated: use {@link #minHeightHeader(int)}
     */
    @Deprecated
    public StickyHeaderBuilder minHeightHeaderPixel(final int minHeight) {
        return minHeightHeader(minHeight);
    }

    public StickyHeaderBuilder minHeightHeader(final int minHeight) {
        mMinHeight = minHeight;
        return this;
    }

    public StickyHeaderBuilder animator(final HeaderAnimator animator) {
        mAnimator = animator;
        return this;
    }

    /**
     * Allows the touch of the views behind the StickyHeader. by default is false
     *
     * @param allow true to allow the touch behind the StickyHeader, false to allow only the scroll.
     */
    public StickyHeaderBuilder allowTouchBehindHeader(boolean allow) {
        mAllowTouchBehindHeader = allow;
        return this;
    }

    public static class ListViewBuilder extends StickyHeaderBuilder {

        private final ListView mListView;

        protected ListViewBuilder(final ListView listView) {
            super(listView.getContext());
            mListView = listView;
        }

        @Override
        public StickyHeaderListView build() {

            //if the animator has not been set, the default one is used
            if (mAnimator == null) {
                mAnimator = new HeaderStickyAnimator();
            }

            final StickyHeaderListView stickyHeaderListView = new StickyHeaderListView(mContext, mListView, mHeader, mMinHeight, mAnimator);
            stickyHeaderListView.build(mAllowTouchBehindHeader);

            return stickyHeaderListView;
        }
    }

}
