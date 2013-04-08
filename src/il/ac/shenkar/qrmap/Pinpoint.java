package il.ac.shenkar.qrmap;

public class Pinpoint {
	private Integer pin_id; // id number
	private float xPos; // x-coordinate
	private float yPos; // y-coordinate
	private String text; // text of point
	private float maxW;
	private float maxH;
	private Integer orientation;
	private Integer idQR;
	private String parseId;
	
	// Default constructor
	public Pinpoint() {
	}

	// constructors
	public Pinpoint(Integer pin_id, float xPos, float yPos, String text,float maxW,float maxH,Integer orientation,Integer idQR,String parseId) {
		super();
		this.pin_id = pin_id;
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = text;
		this.maxW = maxW;
		this.maxH = maxH;
		this.orientation = orientation;
		this.idQR = idQR;
		this.parseId = parseId;
	}
	public Pinpoint(float xPos, float yPos, String text,float maxW,float maxH,Integer orientation,Integer idQR,String parseId) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = text;
		this.maxW = maxW;
		this.maxH = maxH;
		this.orientation = orientation;
		this.idQR = idQR;
		this.parseId = parseId;
	}
	public Pinpoint(float xPos, float yPos, String text,float maxW,float maxH,Integer orientation,Integer idQR) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.text = text;
		this.maxW = maxW;
		this.maxH = maxH;
		this.orientation = orientation;
		this.idQR = idQR;
		
	}
	public Pinpoint(Pinpoint pinpoint){
		this.pin_id = pinpoint.getPin_id();
		this.xPos = pinpoint.getxPos();
		this.yPos = pinpoint.getyPos();
		this.text = pinpoint.getText();
		this.maxW = pinpoint.getMaxW();
		this.maxH = pinpoint.getMaxH();
		this.orientation = pinpoint.getOrientation();
		this.idQR = pinpoint.getIdQR();
		this.parseId = pinpoint.getParseId();
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

	public Integer getPin_id() {
		return pin_id;
	}

	public void setPin_id(Integer pin_id) {
		this.pin_id = pin_id;
	}

	public float getMaxW() {
		return maxW;
	}

	public void setMaxW(float maxW) {
		this.maxW = maxW;
	}

	public float getMaxH() {
		return maxH;
	}

	public void setMaxH(float maxH) {
		this.maxH = maxH;
	}

	public Integer getOrientation() {
		return orientation;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}
	
	public Integer getIdQR() {
		return idQR;
	}

	public void setIdQR(Integer idQR) {
		this.idQR = idQR;
	}

	public String getParseId() {
		return parseId;
	}

	public void setParseId(String parseId) {
		this.parseId = parseId;
	}

	@Override
	public String toString() {
		return "Pinpoint [pin_id=" + pin_id + ", xPos=" + xPos + ", yPos="
				+ yPos + ", text=" + text + ", maxW=" + maxW + ", maxH=" + maxH
				+ ", orientation=" + orientation + ", idQR=" + idQR
				+ ", parseId=" + parseId + "]";
	}
		

}
