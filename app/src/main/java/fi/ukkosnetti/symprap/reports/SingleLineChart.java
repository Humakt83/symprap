package fi.ukkosnetti.symprap.reports;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import java.util.List;

public class SingleLineChart extends View {

    private final static Integer HEIGHT = 500;
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
        RectF dimensions = new RectF(0, 0, width, HEIGHT);
        Double maxValue = getMaxValue();
        Double heightMultiplier = HEIGHT/maxValue;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawCoordinates(canvas, maxValue, heightMultiplier, paint);
        drawPoints(canvas, heightMultiplier, paint, ((Double)(maxValue.floatValue() * heightMultiplier)).floatValue());
    }

    private void drawCoordinates(Canvas canvas, Double maxValue, Double heightMultiplier, Paint paint) {
        float maxValueInt = ((Double) Math.floor(maxValue * heightMultiplier)).floatValue();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        canvas.drawText(maxValue.toString(), 0, 0, paint);
        canvas.drawText("0.00", 0, ((Double) Math.floor(maxValue * heightMultiplier)).intValue(), paint);
        canvas.drawLine(pointWidth, 0, pointWidth, maxValueInt, paint);
        canvas.drawLine(pointWidth, maxValueInt, width, maxValueInt, paint);
    }

    private void drawPoints(Canvas canvas, Double heightMultiplier, Paint paint, Float maxValue) {
        paint.setColor(Color.GREEN);
        for (int i = 1; i < points.size(); i++) {
            float startX = pointWidth * i;
            float stopX = pointWidth + startX;
            float startY = maxValue - ((Double)(points.get(i-1) * heightMultiplier)).floatValue();
            float stopY = maxValue - ((Double) (points.get(i) * heightMultiplier)).floatValue();
            canvas.drawLine(startX, startY, stopX, stopY, paint);
            canvas.drawCircle(startX, startY, 5, paint);
        }
    }

    private Double nameThisLater(double v) {
        return null;
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
