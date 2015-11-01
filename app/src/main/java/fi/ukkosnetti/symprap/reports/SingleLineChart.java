package fi.ukkosnetti.symprap.reports;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

public class SingleLineChart extends View {

    private static final Integer HEIGHT = 500;
    private static final int POINT_RADIUS = 5;
    private static final int TEXT_SIZE = 20;

    private Integer width;
    private Float pointWidth;
    private List<Double> points;

    public SingleLineChart(Context context, Integer width, List<Double> points) {
        super(context);
        this.width = width;
        this.pointWidth = ((Double)Math.floor(width / (points.size() + 1))).floatValue();
        this.points = points;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Double maxValue = getMaxValue();
        Double heightMultiplier = HEIGHT/maxValue;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        drawCoordinates(canvas, maxValue, heightMultiplier, paint);
        drawPoints(canvas, heightMultiplier, paint, ((Double) (maxValue.floatValue() * heightMultiplier)).floatValue());
    }

    private void drawCoordinates(Canvas canvas, Double maxValue, Double heightMultiplier, Paint paint) {
        float maxValueInt = ((Double) Math.floor(maxValue * heightMultiplier)).floatValue();
        paint.setColor(Color.WHITE);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText("" + maxValue.doubleValue(), 0, 20, paint);
        canvas.drawText("0.00", 0, ((Double) Math.floor(maxValue * heightMultiplier)).intValue(), paint);
        canvas.drawLine(pointWidth, 0, pointWidth, maxValueInt, paint);
        canvas.drawLine(pointWidth, maxValueInt, width, maxValueInt, paint);
    }

    private void drawPoints(Canvas canvas, Double heightMultiplier, Paint paint, Float maxValue) {
        for (int i = 1; i < points.size(); i++) {
            paint.setColor(Color.GREEN);
            float startX = pointWidth * i;
            float stopX = pointWidth + startX;
            float startY = maxValue - ((Double)(points.get(i-1) * heightMultiplier)).floatValue();
            float stopY = maxValue - ((Double) (points.get(i) * heightMultiplier)).floatValue();
            canvas.drawLine(startX, startY, stopX, stopY, paint);
            canvas.drawCircle(startX, startY, POINT_RADIUS, paint);
            canvas.drawCircle(stopX, stopY, POINT_RADIUS, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(points.get(i).toString(), stopX + 5, stopY + 20, paint);
        }
    }

    private Double getMaxValue() {
        double max = 0;
        for (Double point : points) {
            max = max > point ? max : Math.ceil(point);
        }
        String maxAsString;
        do {
            max += 1;
            maxAsString = "" + max;
            if (maxAsString.contains(".")) maxAsString = maxAsString.split("\\.")[0];
        } while (!maxAsString.endsWith("0"));
        return max;
    }
}
