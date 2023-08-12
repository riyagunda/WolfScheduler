/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;


/**
 * The Event class represents an event activity that extends the Activity class.
 * It contains and sets information about the event details to be added to the schedule
 * @author Riya Gunda
 */
public class Event extends Activity {
	/**
	 * Private field event details
	 */
	private String eventDetails;

	/**
	 * Event constructor 
	 * @param title of the course
	 * @param meetingDays of the course
	 * @param startTime of the course
	 * @param endTime of the course 
	 * @param eventDetails of the course
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		setEventDetails(eventDetails);
	}

	/**
	 * Getter for event details variable
	 * @return String of event details
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Setter for event details variable and runs checks to see if it null
	 * @param eventDetails of the course
	 * @throws IllegalArgumentException with the message "Invalid event details." if the details
	 * are invalid
	 */
	public void setEventDetails(String eventDetails) {
		if(eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		
		this.eventDetails = eventDetails;
	}

	/**
	 * Outputs a string array of length 4 with the courses title and meeting string
	 * @return String array
	 */
	public String[] getShortDisplayArray() {
		String[] dispArr = new String[4];
		
		dispArr[0] = "";
		dispArr[1] = "";
		dispArr[2] = getTitle();
		dispArr[3] = getMeetingString();
		
		return dispArr;
	}

	/**
	 * Outputs a string array of length 7 with the courses title, meeting string, and event details
	 * @return String array
	 */
	public String[] getLongDisplayArray() {
		String[] dispArr = new String[7];
		
		dispArr[0] = "";
		dispArr[1] = "";
		dispArr[2] = getTitle();
		dispArr[3] = "";
		dispArr[4] = "";
		dispArr[5] = getMeetingString();
		dispArr[6] = eventDetails;
		
		return dispArr;
	}
	
	/**
	 * Overriding the method from the Activity class, checks for validity of meeting days and times
	 * before calling the super method
	 * @param meetingDays of the course
	 * @param startTime of the course
	 * @param endTime of the course
	 * @throws IllegalArgumentException with the message "Invalid meeting days and times." if the meetignDays
	 * string is null, empty, 'A', not M,T,W,H,F,S, or U, start time is greater than end time, start and end 
	 * times exceed the 24 hour clock values
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if("A".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times."); 
		}
		
		if(meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if(startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if(startTime > 2359 || startTime < 0 || endTime > 2359 || endTime < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
			
		int countM = 0;
		int countT = 0;
		int countW = 0;
		int countH = 0;
		int countF = 0;
		int countS = 0;
		int countU = 0;
		
		for(int i = 0; i < meetingDays.length(); i++) {
			String md = meetingDays.substring(i, i + 1);
			if("M".equals(md)) {
				countM++;
			} else if("T".equals(md)) {
				countT++;
			} else if("W".equals(md)) {
				countW++;
			} else if("H".equals(md)) {
				countH++;
			} else if("F".equals(md)) {
				countF++;
			} else if("S".equals(md)) {
				countS++;	
			} else if("U".equals(md)) {
				countU++;
			} 			else {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		
		if(countM > 1 || countT > 1 || countW > 1 || countH > 1 || countF > 1 || countS > 1 
				|| countU > 1) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	/**
	 * Checks if the activity being added is already existing in the schedule
	 * @param activity to be added
	 * @return boolean true if the activity does not exist
	 */
	@Override
    public boolean isDuplicate(Activity activity) {
        if (activity instanceof Event) { 
            Event otherEvent = (Event) activity;
            return getTitle().equals(otherEvent.getTitle());
        }
        return false;
    }
	
	/**
	 * Returns a comma separated value String of Event and Activity fields.
	 * @return String representation of Event and Activity
	 */
	@Override
	public String toString() {
		return getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime() +
				"," + getEventDetails();
	}
}
