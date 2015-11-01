package fi.ukkosnetti.symprap.reports;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class BooleanPieChart extends View {

    private Float noPercentage;

    public BooleanPieChart(Context context, Long yesses, Long noes) {
        super(context);
        noPercentage = (Float.valueOf(noes)/Float.valueOf(yesses + noes)) * 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF dimensions = new RectF(20, 20, 600, 600);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        canvas.drawArc(dimensions, 0, 360, true, paint);
        paint.setColor(Color.RED);
        canvas.drawArc(dimensions, 0, noPercentage * 3.6f, true, paint);
    }
}
