package com.example.marvin.kuwerdas.tuner;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;


public class TunerView extends View {
    public TunerView(Context context) {
        super(context);
        initializeAttributes(null);
        init();
    }

    public TunerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeAttributes(attrs);
        init();
    }

    public TunerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeAttributes(attrs);
        init();
    }

    /**
     * Dots amount
     */
    private int dotAmount;

    /**
     * Drawing tools
     */
    private Paint primaryPaint;
    private Paint startPaint;
    private Paint endPaint;

    /**
     * Animation tools
     */
    private long animationTime;
    private float animatedRadius;
    private boolean isFirstLaunch = true;
    private ValueAnimator startValueAnimator;
    private ValueAnimator endValueAnimator;

    /**
     * Circle size
     */
    private float dotRadius;
    private float bounceDotRadius;
    /**
     * Circle coordinates
     */
    private float xCoordinate;
    private int dotPosition;

    /**
     * Colors
     */
    private int startColor;
    private int endColor;


    private void init() {
        primaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        primaryPaint.setColor(startColor);
        primaryPaint.setStrokeJoin(Paint.Join.ROUND);
        primaryPaint.setStrokeCap(Paint.Cap.ROUND);
        primaryPaint.setStrokeWidth(20);

        startPaint = new Paint(primaryPaint);
        endPaint = new Paint(primaryPaint);
        endPaint.setColor(endColor);

        startValueAnimator = ValueAnimator.ofInt(startColor, endColor);
        startValueAnimator.setDuration(animationTime);
        startValueAnimator.setEvaluator(new ArgbEvaluator());
        startValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startPaint.setColor(startColor);
            }
        });

        endValueAnimator = ValueAnimator.ofInt(endColor, startColor);
        endValueAnimator.setDuration(animationTime);
        endValueAnimator.setEvaluator(new ArgbEvaluator());
        endValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                endPaint.setColor(endColor);
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());

        if (getMeasuredHeight() > getMeasuredWidth()) {
            dotRadius = getMeasuredWidth() / dotAmount / 4;
        } else {
            dotRadius = getMeasuredHeight() / 4;
        }

        bounceDotRadius = dotRadius + (dotRadius / 3);
        float circlesWidth = (dotAmount * (dotRadius * 2)) + dotRadius * (dotAmount - 1);
        xCoordinate = (getMeasuredWidth() - circlesWidth) / 2 + dotRadius;
    }


    private void initializeAttributes(AttributeSet attrs) {
            setDotAmount(5);
            setStartColor(Color.BLACK);
            setEndColor(Color.parseColor("#FAB36E"));
    }

    public void setDotPosition(int dotPosition) {
        this.dotPosition = dotPosition;
        invalidate();
        requestLayout();
    }

    void setDotAmount(int amount) {
        this.dotAmount = amount;
    }

    void setStartColor(int color) {
        this.startColor = color;
    }

    void setEndColor(int color) {
        this.endColor = color;
    }

    private void drawCircles(Canvas canvas, int i, float step, float radius) {
        if (dotPosition == i) {
            drawCircleUp(canvas, step, radius);
        } else {
            if ((i == (dotAmount - 1) && dotPosition == 0 && !isFirstLaunch) || ((dotPosition - 1) == i)) {
                drawCircleDown(canvas, step, radius);
            } else {
                drawCircle(canvas, step);
            }
        }
    }


    /**
     * Circle radius is grow
     * */
    private void drawCircleUp(Canvas canvas, float step, float radius) {
        canvas.drawCircle(
                xCoordinate + step,
                getMeasuredHeight() - 100,
                dotRadius + 10,
                endPaint
        );
    }

    private void drawCircle(Canvas canvas, float step) {
        canvas.drawCircle(
                xCoordinate + step,
                getMeasuredHeight() - 100,
                dotRadius,
                primaryPaint
        );
    }

    /**
     * Circle radius is decrease
     * */
    private void drawCircleDown(Canvas canvas, float step, float radius) {
        canvas.drawCircle(
                xCoordinate + step,
                getMeasuredHeight() - 100,
                bounceDotRadius - 10,
                primaryPaint
        );
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCirclesLeftToRight(canvas,50);
    }

    private void drawCirclesLeftToRight(Canvas canvas, float radius){
        float step = 0;
        for (int i = dotAmount - 1; i >= 0; i--) {
            drawCircles(canvas, i, step, radius);
            step += dotRadius * 3;
        }
    }

}
