package com.example.qrmap;


import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {
	
	private FrameLayout flayout;
	private float XposiTion,YposiTion;
	private ImageView item;
	private int mIconIdCounter = 1 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		flayout = (FrameLayout) findViewById(R.id.framelayout);
	
		
	}
	
   public boolean onTouchEvent(MotionEvent event) {
   //  flayout.setVisibility(View.GONE);
     
//       ImageView pinpoint = new ImageView(this);
//       pinpoint.setImageResource(R.drawable.greenpin);
//       pinpoint.setVisibility(View.VISIBLE);
//
//       rlayout.addView(pinpoint);
// View view = findViewById(...) 
	   		
		
    	XposiTion = event.getX();
		YposiTion = event.getY();
		 flayout.addView(addIcon());
		return true;
   }

	private ImageView addIcon(){
	    item = new ImageView(this);
	    
	    item.setImageResource( R.drawable.greenpin );
 	    item.setAdjustViewBounds(true);
	     FrameLayout.LayoutParams params = new FrameLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,Gravity.NO_GRAVITY );
    
	    item.setLayoutParams( params );
	    item.setId( mIconIdCounter );
	   	 System.out.println(flayout.getMeasuredWidth()); 
	   	 System.out.println(flayout.getMeasuredHeight()); 
	   	
	   	 params.leftMargin = (int) XposiTion - item.getDrawable().getMinimumWidth()/2;
	    params.topMargin = (int) YposiTion - 119;
	   
	    ++mIconIdCounter;
	    return item;
	}

	
	
}
