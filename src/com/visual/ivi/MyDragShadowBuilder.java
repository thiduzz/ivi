package com.visual.ivi;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

class MyDragShadowBuilder extends View.DragShadowBuilder {
	  private static Drawable shadow;
	  
	  public MyDragShadowBuilder(View v) {
		  	super(v);
	   		shadow = ((ImageView) v).getDrawable();
	       }
	  
	  @Override
	       public void onProvideShadowMetrics (Point size, Point touch){
	           int width = getView().getWidth();
	           int height = getView().getHeight();

	           shadow.setBounds(0, 0, width, height);
	           size.set(width, height);
	           touch.set(width / 2, height / 2);
	       }

	       @Override
	       public void onDrawShadow(Canvas canvas) {
	          shadow.draw(canvas);
	       }
	  
	 }