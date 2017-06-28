package kido.support.stickyheader.animator;


import kido.support.stickyheader.HeaderAnimator;

public class BaseStickyHeaderAnimator extends HeaderAnimator {

    private float mTranslationRatio;

    @Override
    protected void onAnimatorAttached() {
        //nothing to do
    }

    @Override
    protected void onAnimatorReady() {
        //nothing to do
    }

    @Override
    public void onScroll(int scrolledY) {
        mHeader.setTranslationY(Math.max(scrolledY, getMaxTranslation()));
        mTranslationRatio = calculateTranslationRatio(scrolledY);
    }

    public float getTranslationRatio() {
        return mTranslationRatio;
    }

    private float calculateTranslationRatio(int scrolledY) {
        //TODO check divisor != 0
        return (float) scrolledY / (float) getMaxTranslation();
    }

}
