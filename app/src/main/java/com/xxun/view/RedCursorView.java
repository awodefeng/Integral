package com.xxun.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.xxun.xunintegral.R;

public class RedCursorView extends View {

    private int counts = 2; // 被分成了几块
    private float posX ; // 当前X坐标的位置
    private Paint paint;

    public RedCursorView(Context context) {
        super(context);
    }

    public RedCursorView(Context context,AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedCursorView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        // 初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.white));
    }
    /**
     * 设置坐标
     * @param pos 当前的item
     * @param rate 变化率
     */
    public void setXY(int pos, float rate) {
        int single = getMeasuredWidth() / counts;
        posX = pos * single + single * rate;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(getMeasuredHeight());
        int width = getMeasuredWidth() / counts - 2;
        canvas.drawLine(posX, 0, posX + width, 0, paint);
    }
}
