package com.example.android.assessmentgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by ritik on 6/17/2018.
 */

public class DialogBoxShape extends Shape{
    private static final int    COLOUR       = Color.WHITE;
    private static final float  STROKE_WIDTH = 1.0f;
    private static final float  CORNER = 35.0f;

    private final Paint border = new Paint();
    private final Path path;

    public DialogBoxShape() {
        path   = new Path();

        border.setColor      (COLOUR);
        border.setStyle      (Paint.Style.FILL);
        border.setStrokeWidth(STROKE_WIDTH);
        border.setAntiAlias  (true);
        border.setDither     (true);
        border.setStrokeJoin (Paint.Join.ROUND);
        border.setStrokeCap  (Paint.Cap.ROUND);
    }

    @Override
    protected void onResize(float width, float height) {
        super.onResize(width, height);

        float dx = STROKE_WIDTH/2.0f;
        float dy = STROKE_WIDTH/2.0f;
        float x  = dx;
        float y  = dy;
        float w  = width  - dx;
        float h  = height - dy;

        //RectF arc = new RectF(x,h-2*CORNER,x+2*CORNER,h);

        path.reset();
        path.moveTo(x + CORNER,y);
        path.lineTo(w - CORNER,y);
        path.lineTo(w,y + CORNER);
        path.lineTo(w, h);
        path.lineTo(x + CORNER,h);
        // path.arcTo (arc,90.0f,90.0f);
        path.lineTo(dx,h - CORNER);
        path.lineTo(dx,y);//path.lineTo(dx,y + CORNER);
        path.close();
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        // TODO Auto-generated method stub
        canvas.drawPath(path,border);
    }
}

