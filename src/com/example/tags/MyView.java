package com.example.tags;

import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;

public class MyView extends View {
	private Bitmap pinpoint;										//point on map
	private ArrayList<Pinpoint> points = new ArrayList<Pinpoint>();	//list of points
	private Drawable mCustomImage;									//image
	//Draw pinpoint and map
	public MyView(Context context) {
		super(context);
		pinpoint = BitmapFactory.decodeResource(getResources(),
				R.drawable.pinpoint);
		mCustomImage = context.getResources().getDrawable(R.drawable.shenkar);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		mCustomImage.setBounds(canvas.getClipBounds());
		mCustomImage.draw(canvas);
		Iterator<Pinpoint> it = points.iterator();
		while (it.hasNext()) {
			Pinpoint p = it.next();
			if (p.isShow())
				canvas.drawBitmap(pinpoint, p.getxPos() - pinpoint.getWidth()
						/ 2, p.getyPos() - pinpoint.getHeight() / 2, null);
		}
		invalidate();
	}
	//Add new point
	public void addPinpoint(Pinpoint pinpoint) {

		points.add(new Pinpoint(pinpoint.getxPos(), pinpoint.getyPos(),
				pinpoint.getText(), pinpoint.isShow()));
	}
	//Delete point from canvas

	public void delPinpoint(int index) {
		points.remove(index);
	}

	/**
	 * This method convents dp unit to equivalent device specific value in
	 * pixels.
	 * 
	 * @param dp
	 *            A value in dp(Device independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent Pixels equivalent to dp according to
	 *         device
	 */
	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	/**
	 * This method converts device specific pixels to device independent pixels.
	 * 
	 * @param px
	 *            A value in px (pixels) unit. Which we need to convert into db
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent db equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;

	}
}
