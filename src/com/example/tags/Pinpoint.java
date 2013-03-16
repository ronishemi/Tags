package com.example.tags;

public class Pinpoint {
	private Integer pin_id; // id number
	private float xPos; // x-coordinate
	private float yPos; // y-coordinate
	private String text; // text of point
	private boolean show; // status show/not show
	
	// Default constructor
	public Pinpoint() {
	}

	// constructors
	public Pinpoint(Integer pin_id, float xPos, float yPos, String text) {
		super();
		this.pin_id = pin_id;
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = text;

	}

	public Pinpoint(float xPos, float yPos, String text, boolean show) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = text;
		this.show = show;
	}

	public Pinpoint(float xPos, float yPos, String text) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = text;

	}

	public Pinpoint(float xPos, float yPos, boolean flag) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = null;
		this.show = flag;
	}

	// Getters and Setters
	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Integer getPin_id() {
		return pin_id;
	}

	public void setPin_id(Integer pin_id) {
		this.pin_id = pin_id;
	}

	// To string method
	@Override
	public String toString() {
		return "Pinpoint [pin_id=" + pin_id + ", xPos=" + xPos + ", yPos="
				+ yPos + ", text=" + text + ", show=" + show + "]";
	}

}
