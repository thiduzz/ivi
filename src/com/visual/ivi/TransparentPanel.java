package com.visual.ivi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TransparentPanel extends LinearLayout {

	private Paint innerPaint, borderPaint;
	  
	  public TransparentPanel(Context context) {
	    super(context);

	    init();
	  }

	  public TransparentPanel(Context context, AttributeSet attrs) {
	    super(context, attrs);

	    init();
	  }
	  
	  private void init()  {
	    innerPaint = new Paint();
	    innerPaint.setARGB(120, 170, 170, 170); // gray
	    innerPaint.setAntiAlias(true);

	    borderPaint = new Paint();
	    borderPaint.setARGB(230, 240, 240, 240);
	    borderPaint.setAntiAlias(true);
	    borderPaint.setStyle(Style.STROKE);
	    borderPaint.setStrokeWidth(2);
	  }
	  
	  @Override
	  protected void dispatchDraw(Canvas canvas) {

	    RectF drawRect = new RectF();
	    drawRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

	    canvas.drawRoundRect(drawRect, 5, 5, innerPaint);
	    canvas.drawRoundRect(drawRect, 5, 5, borderPaint);

	    super.dispatchDraw(canvas);
	  }
}
