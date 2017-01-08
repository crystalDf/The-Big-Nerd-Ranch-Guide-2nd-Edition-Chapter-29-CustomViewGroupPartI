package com.star.customviewgroupparti;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int childWidth = 0;
        int childHeight = 0;

        MarginLayoutParams childMarginLayoutParams = null;

        int childLeftTotalHeight = 0;
        int childTopTotalWidth = 0;
        int childRightTotalHeight = 0;
        int childBottomTotalWidth = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            childMarginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();

            if (i == 0 || i == 2) {
                childLeftTotalHeight += childHeight + childMarginLayoutParams.topMargin +
                        childMarginLayoutParams.bottomMargin;
            }

            if (i == 0 || i == 1) {
                childTopTotalWidth += childWidth + childMarginLayoutParams.leftMargin +
                        childMarginLayoutParams.rightMargin;
            }

            if (i == 1 || i == 3) {
                childRightTotalHeight += childHeight + childMarginLayoutParams.topMargin +
                        childMarginLayoutParams.bottomMargin;
            }

            if (i == 2 || i == 3) {
                childBottomTotalWidth += childWidth + childMarginLayoutParams.leftMargin +
                        childMarginLayoutParams.rightMargin;
            }
        }

        width = Math.max(childTopTotalWidth, childBottomTotalWidth);
        height = Math.max(childLeftTotalHeight, childRightTotalHeight);

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
                heightMode == MeasureSpec.EXACTLY ? heightSize: height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childWidth = 0;
        int childHeight = 0;
        MarginLayoutParams childMarginLayoutParams = null;

        int childLeft = 0;
        int childTop = 0;
        int childRight = 0;
        int childBottom = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            childMarginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();

            switch (i) {
                case 0:
                    childLeft = childMarginLayoutParams.leftMargin;
                    childTop = childMarginLayoutParams.topMargin;
                    childRight = childLeft + childWidth;
                    childBottom = childTop + childHeight;
                    break;

                case 1:
                    childTop = childMarginLayoutParams.topMargin;
                    childRight = getMeasuredWidth() - childMarginLayoutParams.rightMargin;
                    childBottom = childTop + childHeight;
                    childLeft = childRight - childWidth;
                    break;

                case 2:
                    childBottom = getMeasuredHeight() - childMarginLayoutParams.bottomMargin;
                    childLeft = childMarginLayoutParams.leftMargin;
                    childTop = childBottom - childHeight;
                    childRight = childLeft + childWidth;
                    break;

                case 3:
                    childRight = getMeasuredWidth() - childMarginLayoutParams.rightMargin;
                    childBottom = getMeasuredHeight() - childMarginLayoutParams.bottomMargin;
                    childLeft = childRight - childWidth;
                    childTop = childBottom - childHeight;
                    break;

            }

            childView.layout(childLeft, childTop, childRight, childBottom);
        }
    }

}
