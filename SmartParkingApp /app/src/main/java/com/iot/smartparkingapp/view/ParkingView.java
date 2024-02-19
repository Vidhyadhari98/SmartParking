package com.iot.smartparkingapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.iot.smartparkingapp.api.response.Parking;

public class ParkingView extends View {

    private final Paint paint;
    private final Paint textPaint;
    private final Paint borderPaint;

    private Parking parking;

    public ParkingView(Context c) {
        this(c, null);
    }

    public ParkingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int boxWidth = width / 2;
        int boxHeight = height / 2;

        paint.setColor(Color.GREEN);
        for (int index = 0; index < parking.getSensors().size(); index++) {
            int row = index / 2;
            int col = index % 2;

            int left = col * boxWidth;
            int top = row * boxHeight;
            int right = left + boxWidth;
            int bottom = top + boxHeight;
            if (parking.getSensors().get(index).isOccupied()) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.GREEN);
            }

            canvas.drawRect(left, top, right, bottom, paint);
            canvas.drawRect(left, top, right, bottom, borderPaint);
            float textX = (left + right) / 2f;
            float textY = (top + bottom) / 2f + getTextHeight() / 2f;
            canvas.drawText("Parking " + (index + 1), textX, textY, textPaint);
        }
    }

    private float getTextHeight() {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
