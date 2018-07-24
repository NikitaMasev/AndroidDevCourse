package com.nikitamasevgmail.moneytracker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.nikitamasevgmail.moneytracker.R;

public class DiagramView extends View {

    private int income;
    private int expense;

    private Paint incomePaint;
    private Paint expensePaint;

    public DiagramView(Context context) {
        this(context,null);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        incomePaint = new Paint();
        expensePaint = new Paint();

        incomePaint.setColor(getResources().getColor(R.color.color_balance_income));
        expensePaint.setColor(getResources().getColor(R.color.color_balance_expenses));

        if (isInEditMode()) {
            income = 19000;
            expense = 4500;
        }
    }

    public void update(int income, int expense) {
        this.income = income;
        this.expense = expense;

        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawPieDiagram(canvas);
        } else {
            drawRectDiagram(canvas);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawPieDiagram(Canvas canvas) {
        if ((expense + income)==0) {
            return;
        }

        float expensesAngle = 360.f * expense / (expense + income);
        float incomeAngle = 360.f * income / (expense + income);

        int space = 10; //space between income and expense
        int size  = Math.min(canvas.getWidth(), canvas.getHeight()) - space * 2;
        int xMargin = (canvas.getWidth() - size) / 2;
        int yMargin = (canvas.getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin, canvas.getWidth() - xMargin - space, canvas.getHeight() - yMargin, 180 - expensesAngle / 2,expensesAngle,true, expensePaint);
        canvas.drawArc(xMargin + space, yMargin, canvas.getWidth() - xMargin + space, canvas.getHeight() - yMargin, 360 - incomeAngle / 2,incomeAngle,true,incomePaint);
    }

    private void drawRectDiagram(Canvas canvas) {
        if ((expense + income)==0) {
            return;
        }

        long max = Math.max(expense,income);
        long expensesHeight = canvas.getHeight() * expense / max;
        long incomeHeight = canvas.getHeight() * income / max;

        int w = getHeight() / 4;

        canvas.drawRect((float) w/1.5f, canvas.getHeight() - expensesHeight, w*3/1.5f,canvas.getHeight(), expensePaint);
        canvas.drawRect((float) 5*w/1.5f,canvas.getHeight() - incomeHeight, w*7/1.5f, canvas.getHeight(),incomePaint);
    }
}
