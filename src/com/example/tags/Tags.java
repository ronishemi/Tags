package com.example.tags;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Tags extends Activity implements OnTouchListener {
	//properties
	private float XposiTion;
	private float YposiTion;
	private float XinDp;
	private float YinDp;
	private float XinPixel;
	private float YinPixel;
	private float deltaX;
	private float deltaY;
	private float deltaX1;
	private float deltaY1;
	private int position;
	private float radius = 1500;
	private MyView myView;
	private EditText editText;
	private Button button;
	private Button delbtn;
	private Pinpoint pinpoint, newpoint;
	private RelativeLayout myLayout;
	private ArrayList<Pinpoint> arrPoints = new ArrayList<Pinpoint>();
	private boolean exist;
	private Boolean firstTime;
	private SharedPreferences sharedPref;
	private InputMethodManager imm;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myView = new MyView(this);
		sharedPref = getSharedPreferences("hasRunBefore", MODE_PRIVATE);
		firstTime = sharedPref.getBoolean("firstTime", false);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.BELOW, myView.getId());
		LayoutInflater inflater = LayoutInflater.from(this);
		myLayout = (RelativeLayout) inflater.inflate(R.layout.activity_main,
				null);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		myLayout.addView(myView);
		setContentView(myLayout);
		//buttons configuration
		editText = (EditText) findViewById(R.id.editText);
		button = (Button) findViewById(R.id.btn);
		delbtn = (Button) findViewById(R.id.delbtn);
		//Delete button
		delbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				arrPoints.remove(position);
				myView.delPinpoint(position);
				button.setText("save");
				myView.bringToFront();
				editText.setText("");
				editText.setEnabled(true);
			}

		});
		//button configuration
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				done();
			}
		});
		editText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub

				if (actionId == EditorInfo.IME_ACTION_DONE) {
					done();
					return true;
				} else {
					return false;
				}
			}
		});
		//On click view text of point/delete exist point
		myView.setOnClickListener(new MyView.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub

				newpoint = getPinpoint();
				if (newpoint != null) {
					Toast.makeText(getApplicationContext(), newpoint.getText(),
							Toast.LENGTH_SHORT).show();
					button.setText("cancel");
					editText.bringToFront();
					editText.setText(newpoint.getText());
					editText.setEnabled(false);
					button.bringToFront();
					delbtn.bringToFront();
				}

			}

		});
		//On long click create new point/edit exist point
		myView.setOnLongClickListener(new MyView.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (!editText.isEnabled())
					return true;
				editText.bringToFront();
				button.bringToFront();
				newpoint = getPinpoint();
				
				if (newpoint == null) {
					pinpoint = new Pinpoint(XposiTion, YposiTion, "stamText",true);
					exist = false;
					myView.addPinpoint(pinpoint);
				} else {
					exist = true;
					editText.setText(newpoint.getText());
				}
				return true;
			}

		});
		myView.setOnTouchListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sharedPref.edit().putBoolean("firstTime", true).commit();

	}
//get position of touch
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		XposiTion = event.getX();
		YposiTion = event.getY();
		return false;
	}
//find flag by it position
	private Pinpoint getPinpoint() {
		Iterator<Pinpoint> it = arrPoints.iterator();
		newpoint = null;
		position = 0;
		int i = 0;
		while (it.hasNext()) {
			pinpoint = it.next();
			// change back to pixels
			XinPixel = MyView.convertDpToPixel(pinpoint.getxPos(),
					Tags.this);
			YinPixel = MyView.convertDpToPixel(pinpoint.getyPos(),
					Tags.this);

			Pinpoint tempPoint = new Pinpoint(XinPixel, YinPixel,
					pinpoint.getText(), pinpoint.isShow());

			// (x-center_x)^2 + (y - center_y)^2 < radius^2.
			float f = (XposiTion - tempPoint.getxPos())
					* (XposiTion - tempPoint.getxPos())
					+ (YposiTion - tempPoint.getyPos())
					* (YposiTion - tempPoint.getyPos());
			//find flag that near to center into specific radius
			if (f < radius) {
				if (newpoint != null) {
					deltaX = (XposiTion - tempPoint.getxPos())
							* (XposiTion - tempPoint.getxPos());
					deltaY = (YposiTion - tempPoint.getyPos())
							* (YposiTion - tempPoint.getyPos());
					deltaX1 = (newpoint.getxPos() - tempPoint.getxPos())
							* (newpoint.getxPos() - tempPoint.getxPos());
					;
					deltaY1 = (newpoint.getyPos() - tempPoint.getyPos())
							* (newpoint.getyPos() - tempPoint.getyPos());
					if ((deltaX1 + deltaY1) < (deltaX + deltaY)) {
						newpoint = tempPoint;
						position = i;
					}
				} else {
					newpoint = tempPoint;
					position = i;
				}

			}
			++i;
		}

		return newpoint;
	}
//method that disappear the keyboard and save message in landscape view
	private void done() {
		if (!editText.isEnabled()) {
			editText.setEnabled(true);
			button.setText("save");
			myView.bringToFront();
			editText.setText("");
		}
		String text = editText.getText().toString();
		if (text != null && text.trim().length() > 0) {

			if (exist) {

				pinpoint = arrPoints.get(position);
				pinpoint.setText(text);
				editText.setText("");

			} else {
				
				pinpoint.setText(text);
				// change pixel to DP
				XinDp = MyView.convertPixelsToDp(pinpoint.getxPos(),
						Tags.this);
				YinDp = MyView.convertPixelsToDp(pinpoint.getyPos(),
						Tags.this);
				pinpoint.setxPos(XinDp);
				pinpoint.setyPos(YinDp);
				arrPoints.add(pinpoint);
				editText.setText("");

			}
			myView.bringToFront();
		}
		imm.hideSoftInputFromWindow(button.getWindowToken(), 0);
	}
}
