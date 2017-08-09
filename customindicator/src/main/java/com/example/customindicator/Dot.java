package com.example.customindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Admin on 8/4/2017.
 */

public class Dot {
    private Paint paint;

    private PointF center;

    private int currentRadius;


    public Dot() {
        paint = new Paint();
        paint.setAntiAlias(true);
        center = new PointF();
    }
    public Dot(Paint paint, PointF center, int currentRadius) {
        this.paint = paint;
        this.center = center;
        this.currentRadius = currentRadius;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setAlpla(int alpla) {
        paint.setAlpha(alpla);
    }

    public void setCenter(float x, float y) {
        center.set(x,y);
    }

    public int getCurrentRadius() {
        return currentRadius;
    }

    public void setCurrentRadius(int currentRadius) {
        this.currentRadius = currentRadius;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, currentRadius, paint);
    }
}
