package simpleInternetLog;

import java.text.ParseException;

/*
 * 
 * Created by Ben Brust 2017
 */
public class InternetCSVEntry extends CSVEntry{
	private boolean localConnected;
	private boolean internetConnected;
	private int programStatus;
	
	public InternetCSVEntry(boolean localConnected, boolean internetConnected, int programStatus){
		super(System.currentTimeMillis());
		this.localConnected = localConnected;
		this.internetConnected = internetConnected;
		this.programStatus = programStatus;
	}
	public InternetCSVEntry(long entryTime, boolean localConnected, boolean internetConnected, int programStatus){
		super(entryTime);
		this.localConnected = localConnected;
		this.internetConnected = internetConnected;
		this.programStatus = programStatus;
	}
	/*
	 * Constructor from CSV line
	 */
	public InternetCSVEntry(String csvEntryLine) throws IncompatibleLineException{
		String[] entries = csvEntryLine.split(",");
		
		if (entries.length != 4){
			throw new IncompatibleLineException();
		}

		try {
			this.setTimestamp(CSVEntry.getTimeFromString(entries[0]));
			this.localConnected = Integer.valueOf(entries[1]) == 1 ? true : false;
			this.internetConnected = Integer.valueOf(entries[2]) == 1 ? true : false;
			this.programStatus = Integer.valueOf(entries[3]);
		} catch (ParseException e) {
			//e.printStackTrace();
			throw new IncompatibleLineException();
		}
		
	}

	public boolean isLocalConnected(){
		return this.localConnected;
	}
	public boolean isInternetConnected(){
		return this.internetConnected;
	}
	public int getProgramStatus(){
		return this.programStatus;
	}
	public boolean isOpening(){
		return this.getProgramStatus() == 1;
	}
	public boolean isClosing(){
		return this.getProgramStatus() == -1;
	}
	
	@Override
	public String getCSVLine() {
		String localString  = this.isLocalConnected()? "1" : "0";
		String internetString  = this.isInternetConnected()? "1" : "0";
		
		return CSVEntry.getCSVTimestamp(this.getTimestamp()) + "," + localString + "," + internetString + "," + String.valueOf(this.getProgramStatus());
	}

	@Override
	public String getCSVHeader() {
		return "Timestamp,Connected to Local,Connected to Internet,Program Status (1-starting 0-normal -1-stopping)";
	}
	
}
