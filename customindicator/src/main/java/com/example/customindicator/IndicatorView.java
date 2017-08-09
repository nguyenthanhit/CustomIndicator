package com.example.customindicator;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Admin on 8/4/2017.
 */

public class IndicatorView extends View implements IndicatorInterface,ViewPager.OnPageChangeListener {

    private static final long DEFAULT_ANIMATE_DURATION = 200;

    private static final int DEFAULT_RADIUS_SELECTED = 20;

    private static final int DEFAULT_RADIUS_UNSELECTED = 15;

    private static final int DEFAULT_DISTANCE = 40;

    private ViewPager viewPager;

    private Dot[] dots;

    private long animateDuaration = DEFAULT_ANIMATE_DURATION;

    private int radiusSelected = DEFAULT_RADIUS_SELECTED;

    private int radiusUnSelected = DEFAULT_RADIUS_UNSELECTED;

    private  int distance = DEFAULT_DISTANCE;

    private int colorSelected;

    private int colorUnSelected;

    private int currentPosition;

    private int beforePosition;

    private ValueAnimator animatorZoomIn;

    private ValueAnimator animatorZoomOut;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void initDot(int count) throws PagesLessException {
        if (count < 2 ) throw new PagesLessException();

        dots = new Dot[count];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new Dot();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.IndicatorView);
        this.radiusSelected = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_th_radius_selected,DEFAULT_RADIUS_SELECTED);
        this.radiusUnSelected = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_th_radius_unselected,DEFAULT_RADIUS_UNSELECTED);
        this.distance = typedArray.getInt(R.styleable.IndicatorView_th_distance,DEFAULT_DISTANCE);
        this.colorSelected = typedArray.getColor(R.styleable.IndicatorView_th_color_selected, Color.parseColor("#ffffff"));
        this.colorUnSelected = typedArray.getColor(R.styleable.IndicatorView_th_color_unselected, Color.parseColor("#ffffff"));
        typedArray.recycle();

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        beforePosition = currentPosition;
        currentPosition = position;

        if (beforePosition == currentPosition) {
            beforePosition = currentPosition + 1;
        }

        dots[currentPosition].setColor(colorSelected);
        dots[beforePosition].setColor(colorUnSelected);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animateDuaration);

        animatorZoomIn = ValueAnimator.ofInt(radiusUnSelected, radiusSelected);
        animatorZoomIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int positionPerform = currentPosition;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newRadius = (int) valueAnimator.getAnimatedValue();
                changeNewRadius(positionPerform, newRadius);
            }
        });

        animatorZoomOut = ValueAnimator.ofInt(radiusSelected, radiusUnSelected);
        animatorZoomOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int positionPerform = beforePosition;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newRadius = (int) valueAnimator.getAnimatedValue();
                changeNewRadius(positionPerform, newRadius);
            }
        });

        animatorSet.play(animatorZoomIn).with(animatorZoomOut);
        animatorSet.start();
    }

    private void changeNewRadius(int pos, int newRadius) {
        if (dots[pos].getCurrentRadius() != newRadius ) {
            dots[pos].setCurrentRadius(newRadius);
            dots[pos].setAlpla(newRadius * 255 / radiusSelected);
            invalidate();
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setViewPager(ViewPager viewPager) throws PagesLessException {
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(this);
        initDot(viewPager.getAdapter().getCount());
        onPageSelected(0);
    }

    @Override
    public void setAnimateDuration(long duration) {
        animateDuaration = duration;
    }

    @Override
    public void setRadiusSelected(int radius) {
        radiusSelected = radius;
    }

    @Override
    public void setRadiusUnSelected(int radius) {
        radiusUnSelected = radius;
    }

    @Override
    public void setDistanceDot(int distance) {
        this.distance = distance;
    }

    @Override
    protected void onDraw(Canvas canvas) {
       for (Dot dot : dots) {
           dot.draw(canvas);
       }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desireHeight = 2 * radiusSelected;

        int width;
        int height;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = widthSize;
        } else {
            width = 0;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            height = Math.min(desireHeight,heightSize);
        } else {
            height = desireHeight;
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        float yCenter = getHeight() / 2;
        int d = distance + 2 * radiusUnSelected;
        int firstX = getWidth()/2  - ((dots.length -1) * d /2  );
        for (int i = 0; i < dots.length; i++) {
            dots[i].setCenter(i == 0 ? firstX : firstX + d * i,yCenter);
            dots[i].setCurrentRadius( i == currentPosition ? radiusSelected : radiusUnSelected);
            dots[i].setColor( i == currentPosition ? colorSelected : colorUnSelected);
            dots[i].setAlpla(i == currentPosition ? 255 : radiusUnSelected * 255 / radiusSelected);
        }
    }
}
