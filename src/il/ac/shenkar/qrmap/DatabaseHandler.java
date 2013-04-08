package il.ac.shenkar.qrmap;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "PinPoints";

	// Todos table name
	private static final String TABLE_POINT = "tags";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_POS_X = "xPos";
	private static final String KEY_POS_Y = "yPos";
	private static final String KEY_TEXT = "text";
	private static final String KEY_MAXW = "maxW";
	private static final String KEY_MAXH = "maxH";
	private static final String KEY_ORIENT = "orientation";
	private static final String KEY_IDQR = "idQR";
	private static final String KEY_PARSE = "parseId";
	

	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_POINT + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_POS_X + " TEXT,"
				+ KEY_POS_Y + " TEXT," + KEY_TEXT + " TEXT," + KEY_MAXW +" TEXT,"
				+ KEY_MAXH + " TEXT," + KEY_ORIENT + " TEXT,"
				+ KEY_IDQR + " TEXT," + KEY_PARSE + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINT);

		// Create tables again
		onCreate(db);

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new pinpoint
	public void addPinpoint(Pinpoint pinpoint) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			
			ContentValues values = new ContentValues();
			values.put(KEY_POS_X, pinpoint.getxPos()); // x point in dp
			values.put(KEY_POS_Y, pinpoint.getyPos()); // y point in dp
			values.put(KEY_TEXT, pinpoint.getText()); // tag text
			values.put(KEY_MAXW, pinpoint.getMaxW()); // max width
			values.put(KEY_MAXH, pinpoint.getMaxH()); // max height
			values.put(KEY_ORIENT, pinpoint.getOrientation()); // orientation type
			values.put(KEY_IDQR, pinpoint.getIdQR()); // QR id in Parse
			values.put(KEY_PARSE, pinpoint.getParseId()); //Parse number
			
			// Inserting Row
			db.insert(TABLE_POINT, null, values);
			db.close(); // Closing database connection
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Getting single contact
	@SuppressWarnings("finally")
	public Pinpoint getPinpoint(Integer id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Pinpoint pinpoint = null;
		try {
			Cursor cursor = db
					.query(TABLE_POINT, new String[] { KEY_ID,KEY_POS_X,KEY_POS_Y, KEY_TEXT,KEY_MAXW,KEY_MAXH,KEY_ORIENT,KEY_IDQR,KEY_PARSE}, KEY_ID + "=?",
							new String[] { String.valueOf(id) }, null, null,null, null);
			if (cursor != null){
				cursor.moveToFirst();
				pinpoint = new Pinpoint(
					Integer.parseInt(cursor.getString(0)),cursor.getFloat(1),cursor.getFloat(2),cursor.getString(3),
					cursor.getFloat(4),cursor.getFloat(5),cursor.getInt(6),cursor.getInt(7),cursor.getString(8));
			// return contact
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(); // Closing database connection
			return pinpoint;
		}
	}

	// Getting All Contacts
	public List<Pinpoint> getAllPinpoints() {
		List<Pinpoint> pinpointList = new ArrayList<Pinpoint>();
		
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_POINT;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Select All Query

		// looping through all rows and adding to list
		if ((cursor != null)&&cursor.moveToFirst()) {
			do {
				Pinpoint pinpoint = new Pinpoint();
				pinpoint.setPin_id(Integer.parseInt(cursor.getString(0)));
				pinpoint.setxPos(cursor.getFloat(1));
				pinpoint.setyPos(cursor.getFloat(2));
				pinpoint.setText(cursor.getString(3));
				pinpoint.setMaxW(cursor.getFloat(4));
				pinpoint.setMaxH(cursor.getFloat(5));
				pinpoint.setOrientation(cursor.getInt(6));
				pinpoint.setIdQR(cursor.getInt(7));
				pinpoint.setParseId(cursor.getString(8));
				// Adding contact to list
				pinpointList.add(pinpoint);
			} while (cursor.moveToNext());
		}
		// return contact list
		cursor.close();
		db.close(); // Closing database connection
		return pinpointList;
	}

	// Updating single contact
	public int updatePinpoint(Pinpoint pinpoint) {
		SQLiteDatabase db = this.getWritableDatabase();
		int result;
		ContentValues values = new ContentValues();
		values.put(KEY_POS_X, pinpoint.getxPos());
		values.put(KEY_POS_Y, pinpoint.getyPos());
		values.put(KEY_TEXT, pinpoint.getText());
		values.put(KEY_MAXW, pinpoint.getMaxW());
		values.put(KEY_MAXH, pinpoint.getMaxH());
		values.put(KEY_ORIENT, pinpoint.getOrientation());
		values.put(KEY_IDQR, pinpoint.getIdQR());
		values.put(KEY_PARSE, pinpoint.getParseId());
		// updating row
		result = db.update(TABLE_POINT, values, KEY_ID + " = ?",
				new String[] { String.valueOf(pinpoint.getPin_id()) });
		db.close(); // Closing database connection
		return result;
	}

	// Deleting single contact
	public void deletePinpoint(Pinpoint pinpoint) {
		if(pinpoint == null)
			return;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_POINT, KEY_ID + " = ?",
					new String[] { String.valueOf(pinpoint.getPin_id()) });
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Getting contacts Count
	public int getPinpointCount() {
		int count = 0;
		
		String countQuery = "SELECT  * FROM " + TABLE_POINT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		count = cursor.getCount();
		cursor.close();
		db.close(); // Closing database connection
		return count;
	}
}

