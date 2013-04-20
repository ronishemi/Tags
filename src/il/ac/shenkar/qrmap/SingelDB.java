package il.ac.shenkar.qrmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.content.Context;
import android.widget.Toast;

public class SingelDB {

	
	// Properties
	private static final String TAG = "SingelDB";
	private static SingelDB singelDB;
	private static List<Pinpoint> array;
	private Context _context;
	private static DatabaseHandler entry;
	
	private ParseObject parseObject;
	private Pinpoint pin;
	private Integer choice;
	private Integer position; 
	
	// Constructor
	private SingelDB(Context context) {
		_context = context;
		array = new ArrayList<Pinpoint>();
		entry = new DatabaseHandler(context);
		array = entry.getAllPinpoints();

	}

	// Get instance from database
	static SingelDB getInstance(Context context) {
		if (singelDB == null) {
			singelDB = new SingelDB(context);
		}
		return singelDB;
	}

	// Get entry
	public DatabaseHandler getEntry() {
		return entry;
	}

	// Get items list
	public List<Pinpoint> getList() {
		return array;
	}
	// Add to Parse
	
		public void addToParse(Pinpoint p) {
				if(!Constants.isOnline()){
					Toast.makeText(_context, "Missing Network Connection", Toast.LENGTH_SHORT).show();
					Constants.inOperation = false;
					return ;
				}
			pin = new Pinpoint(p);
				
			ParseACL parseACL =	new ParseACL(ParseUser.getCurrentUser());
			parseACL.setPublicReadAccess(true);
			ParseObject pinPointObject = new ParseObject("PinPointObject");		
			
			pinPointObject.put("QRid",Constants.QRid);				
			pinPointObject.put("User", Constants.username);
			pinPointObject.put("xPos", p.getxPos());
			pinPointObject.put("yPos", p.getyPos());
			pinPointObject.put("Text", p.getText());	
			pinPointObject.put("maxW", p.getMaxW());
			pinPointObject.put("maxH", p.getMaxH());	
			pinPointObject.setACL(parseACL);	
			pinPointObject.put("Deleted", false);
			parseObject=pinPointObject;
			
			pinPointObject.saveInBackground(new SaveCallback() {
				  public void done(ParseException e) {
					  if (e == null) {
						  pin.setParseId(parseObject.getObjectId());
						  addListPin(pin);
					      } else {
					    	  QRMap.myView.delLastPinpoint();
					    	  QRMap.myView.inValid();
					    	  Toast.makeText(_context,"QR already exist!", Toast.LENGTH_SHORT).show();
					      }
				  }
				});
		
			}


		// Add pinpoint to List new point
		public boolean addListPin(Pinpoint pinpoint) {
			if(!Constants.isOnline()){
				Toast.makeText(_context, "Missing Network Connection", Toast.LENGTH_SHORT).show();
				Constants.inOperation = false;
				return false ;
			}
		singelDB.getEntry().addPinpoint(new Pinpoint(pinpoint));
			if (array.isEmpty())
				pinpoint.setPin_id(1);
			else{
				if(singelDB.getEntry().getPinpoint(array.size() + 1) != null)
					pinpoint.setPin_id(singelDB.getEntry()
						.getPinpoint(array.size() + 1).getPin_id());
				}
			Constants.inOperation = false;	
			if(pinpoint != null){
				if(!Constants.endInserting)
				 QRMap.myView.addPinpoint(Constants.pinpoint);
				Constants.endInserting = true;
				QRMap.myView.inValid();
				return SingelDB.array.add(pinpoint);
			}
			QRMap.myView.inValid();
			return false;
		}
			
		
		
		// Add to List from parse
	public Pinpoint addListFromParse(float xPos, float yPos, String text,float maxW,float maxH,Integer orientation,Integer idQR,String parseId) {
		
	singelDB.getEntry().addPinpoint(
			new Pinpoint(xPos, yPos, text,maxW,maxH,orientation,idQR,parseId));

	Pinpoint pinpoint = new Pinpoint();
	pinpoint.setxPos(xPos);
	pinpoint.setyPos(yPos);
	pinpoint.setText(text);
	pinpoint.setMaxW(maxW);
	pinpoint.setMaxH(maxH);
	pinpoint.setOrientation(orientation);
	pinpoint.setIdQR(idQR);
	pinpoint.setParseId(parseId);
		if (array.isEmpty())
			pinpoint.setPin_id(1);
		else{
			if(singelDB.getEntry().getPinpoint(array.size() + 1) != null)
				pinpoint.setPin_id(singelDB.getEntry()
					.getPinpoint(array.size() + 1).getPin_id());
			}
				
		if(pinpoint != null){
		//	System.out.println("The ADD method "+array.size());
			 SingelDB.array.add(pinpoint);
			 return pinpoint;
		}
		return null;
	}
	// Delete item
	public boolean delList(int position) {
		if(!Constants.isOnline()){
			Toast.makeText(_context, "Missing Network Connection", Toast.LENGTH_SHORT).show();
			Constants.inOperation = false;
			return false;
		}
		if(array.isEmpty())
			return false;		
		Constants.inOperation = false;
		Constants.inDelOperation = false;
		
		Pinpoint pinpoint = array.get(position);
		if (pinpoint != null) {
		singelDB.getEntry().deletePinpoint(pinpoint);
		pinpoint = SingelDB.array.remove(position);
		}else
			return false;		
		return true;
	}
	public void delFromParse(String parseId,final int position) {
	//delete from parse
			ParseQuery query = new ParseQuery("PinPointObject");
			
			query.getInBackground(parseId, new GetCallback() {
			  public void done(ParseObject object, ParseException e) {
			    if (e == null) {
			      // object will be your object
			    //	System.out.println("found object");
			    	delList(position);
			    	
			    } else {
			      // something went wrong
			    	System.out.println(e.getMessage());
			    }
			  }
			});	
	}		
	
	
	
	public int upDatePinpoint(Pinpoint pinpoint){		
		if(!Constants.isOnline()){
			Toast.makeText(_context, "Missing Network Connection", Toast.LENGTH_SHORT).show();
			Constants.inOperation = false;
			return -1;
		}
		return	singelDB.getEntry().updatePinpoint(pinpoint);
	}
	// Get item details
	public Pinpoint getItemDetails(int index) {
		return SingelDB.array.get(index);
	}
	public Pinpoint getPinpointByQRid(Integer idQR){
		if(idQR >=0 )
		 return getItemDetails(getQRPosition(idQR));
		return null;
	}

	// Get last item details
	public Pinpoint getLastPinpoint() {
		
		if(array.isEmpty())
			return null;
		return SingelDB.array
				.get(singelDB.getEntry().getPinpointCount() - 1);
	}

	// Get position of item
	public int getPosition(Pinpoint pinpoint) {
		int i = 0;
		if(pinpoint == null)
			return -1;
		while ((SingelDB.array.size() >= i)
				&& (pinpoint.getPin_id() != SingelDB.array.get(i).getPin_id())) {
			++i;
		}
		return i;
	}
	//Get position of Pinpoint
	public int getQRPosition(Integer idQR) {
		if(array.isEmpty())
			return -1;
		int i = 0;
		if(idQR == null)
			return -1;
		Iterator<Pinpoint> it = array.iterator();
		while(it.hasNext()){
			if(it.next().getIdQR()==idQR)
				return i;
			++i;
		}
		return -1;
	}
		
	public void print(){
		Iterator<Pinpoint> it = array.iterator();
		while(it.hasNext()){
			Pinpoint ob = it.next();
			System.out.println(ob.getIdQR());
			System.out.println(ob.getParseId());
			System.out.println(ob.getText());
		}
		
	}
	//update = 1 , delete = 2
	public void upDateAndDeleteParse(Pinpoint pinpoint,final String text,final Integer position,final Integer choice){
		if(!Constants.isOnline()){
			Toast.makeText(_context, "Missing Network Connection", Toast.LENGTH_SHORT).show();
			Constants.inOperation = false;
			return ;
		}		
		this.choice = choice;
		pin = pinpoint;
		
	ParseQuery query = new ParseQuery("PinPointObject");
	
	query.getInBackground(pin.getParseId(), new GetCallback() {
	  public void done(ParseObject object, ParseException e) {
		  Constants.inOperation = false;
	    if (e == null) {
	      // object will be your object
	    //	System.out.println("found object");	    
	    	if(choice == 1)
	    		updateParse(object,text);
	    	else
	    		deleteParse(object,position);
	    		
	    } else {
	      // something went wrong
	    	System.out.println(e.getMessage());
	    }
	  }
	});	
	

	}
	public void updateParseAfterDelete(ParseObject ob,final Pinpoint pinpoint){
		
		parseObject=ob;
		pin = pinpoint;
 		ParseACL parseACL =	new ParseACL(ParseUser.getCurrentUser());
		parseACL.setPublicReadAccess(true);
		parseObject.setACL(parseACL);
		
		parseObject.put("User", Constants.username);
		//
		parseObject.put("xPos", pinpoint.getxPos());
		parseObject.put("yPos", pinpoint.getyPos());
		parseObject.put("Text", pinpoint.getText());	
		parseObject.put("maxW", pinpoint.getMaxW());
		parseObject.put("maxH", pinpoint.getMaxH());
		parseObject.put("Deleted", false);
		//
		 ob.saveInBackground(new SaveCallback() {
			@Override
			public void done(com.parse.ParseException e) {
				// TODO Auto-generated method stub
				Constants.inOperation = false;
				if (e == null) {
					
					pinpoint.setParseId(parseObject.getObjectId());
					addListPin(pin);
				}else{
							  Constants.inOperation = false;
							  QRMap.myView.delLastPinpoint();
							  QRMap.myView.inValid();
					    	  Toast.makeText(_context,"QR already exist!", Toast.LENGTH_SHORT).show();
							//Toast.makeText(_context, "No permission to make changes", Toast.LENGTH_SHORT).show();
					}
			}
			});			 
	}
	public void updateParse(ParseObject ob,final String text){
		parseObject=ob;
		ob.put("Text", text);
		  
		 ob.saveInBackground(new SaveCallback() {
			@Override
			public void done(com.parse.ParseException e) {
				// TODO Auto-generated method stub
				Constants.inOperation = false;
				if (e == null) {
					pin.setText(text);
					pin.setParseId(parseObject.getObjectId());					
					singelDB.upDatePinpoint(pin);
				}else{
					Toast.makeText(_context, "No permission to make changes", Toast.LENGTH_SHORT).show();
					}
			}
			});	
		 
	}
	public void deleteParse(ParseObject ob,final Integer position){
		parseObject=ob;
		this.position = position;
		ParseACL parseACL =	new ParseACL();
		parseACL.setPublicReadAccess(true);
		parseACL.setPublicWriteAccess(true);
		ob.put("Deleted", true);
		ob.setACL(parseACL);
		 ob.saveInBackground(new SaveCallback() {
			@Override
			public void done(com.parse.ParseException e) {
				// TODO Auto-generated method stub
				Constants.inOperation = false;
				Constants.inDelOperation=false;
				if (e == null) {					
					delList(position);
					QRMap.myView.delPinpoint(position);
					QRMap.myView.inValid();
				}else{
					 Toast.makeText(_context, "No permission to make changes", Toast.LENGTH_SHORT).show();
				    }
				}
			});		
		 
		 		 
	}

	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    	} catch(NumberFormatException e) { 
	    		return false; 
	    	}
	    	return true;
	}
	public void addIfExist(Pinpoint pinpoint){
		pin = pinpoint;
		//position = getQRPosition(pin.getIdQR());
		ParseQuery query = new ParseQuery("PinPointObject");
		query.whereEqualTo("QRid", pinpoint.getIdQR());
		query.getFirstInBackground(new GetCallback() {
			  public void done(ParseObject object, ParseException e) {
				  if (e == null) {
				      // Success!
					  if(object.getBoolean("Deleted")){
					     updateParseAfterDelete(object ,pin);
					     
					  }
					  else{
						  Constants.inOperation = false;
						  QRMap.myView.delLastPinpoint();
						  QRMap.myView.inValid();
				    	  Toast.makeText(_context,"QR already exist!", Toast.LENGTH_SHORT).show();
					 }
						  
				    } else {
				      // Failure!
				    	addToParse(pin);
				    	
				    }  
				  
			  }
			});
		
	}
}
