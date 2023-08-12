package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * The WolfScheduler system provides a way for a student to determine 
 * which course schedule may be best for them in an upcoming semester.
 * 
 * @author Riya Gunda 
 */
public abstract class Activity implements Conflict {

	/**
	 * Constant for the upper hour
	 */
	public static final int UPPER_HOUR = 24;
	
	/**
	 * Constant for the upper minute
	 */
	public static final int UPPER_MIN = 60;
	
	/** Course's title. */
	private String title;
	
	/** Course's meeting days */
	private String meetingDays;
	
	/** Course's starting time */
	private int startTime;
	
	/** Course's ending time */
	private int endTime;

	/**
	 * The Activity class' constructor
	 * Initializes the value for title and the meeting string
	 * @param title of the course
	 * @param meetingDays of the course 
	 * @param startTime of the course in 24 hour format
	 * @param endTime of the course in 24 hour format
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }
	
	/**
	 * Returns the Course's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title
	 * @param title the title to set
	 * @throws IllegalArgumentException with the message "Invalid title" if
	 * the title parameter is null or empty
	 */
	public void setTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		if(title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		this.title = title;
	}

	/**
	 * Returns the Course's meeting days
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Course's start time
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's end time
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the values for the Course's meeting days, start time, and end time
	 * @param meetingDays meeting days for the course 
	 * @param startTime start time for the course
	 * @param endTime end time for the course
	 * @throws IllegalArgumentException if the parameters are invalid:
	 * 									- meetingDays is null or empty
	 *                                  - startTime is greater than endTime
	 *                                  - startTime or endTime is outside the valid range of 0000 to 2359
	 *                                  - if meetingDays is "A" (arranged), startTime and endTime must be 0
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if(meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if(startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if(startTime > 2359 || startTime < 0 || endTime > 2359 || endTime < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if("A".equals(meetingDays)) {
			if(startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			this.startTime = 0;
			this.endTime = 0;
		} else {	
			int stHour = (int) startTime / 100;
			int stMin = startTime % 100;
			int etHour = (int) endTime / 100;
			int etMin = endTime % 100;
			
			if(stHour < 0 || stHour >= UPPER_HOUR || etHour < 0 || etHour >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			if(stMin < 0 || stMin >= UPPER_MIN || etMin < 0 || etMin >= UPPER_MIN) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			this.startTime = startTime;
			this.endTime = endTime;
		}
		this.meetingDays = meetingDays;		
	}

	/**
	 * Returns a formatted string with the meeting days and time of the activity
	 * @return String of course timings and days
	 */
	public String getMeetingString() {
		String mtString = "";
		
		// Converting military time to standard time
		int stHour = (int) startTime / 100;
		int stMin = startTime % 100;
		int etHour = (int) endTime / 100;
		int etMin = endTime % 100;
		
		String stHourConv = "";
		String etHourConv = "";
		
		String stMinConv = "";
		String etMinConv = "";
		
		if(stMin < 10) {
			stMinConv = "0" + stMin;
		} else {
	        stMinConv = "" + stMin;
	    }
		if(etMin < 10) {
			etMinConv = "0" + etMin;
		} else {
	        etMinConv = "" + etMin;
	    }
		
		if(stHour - 12 == 12) {
			stHourConv = "0:" + stMinConv + "AM";
		} else if (stHour == 12) {
			stHourConv = stHour + ":" + stMinConv + "PM";
		} else if(stHour > 12) {
			stHourConv = (stHour - 12) + ":" + stMinConv + "PM";
		} else {
			stHourConv = stHour + ":" + stMinConv + "AM";
		}
		
		
		if(etHour - 12 == 12) {
			etHourConv = "0:" + etMinConv + "AM";
		} else if (etHour == 12) {
			etHourConv = etHour + ":" + etMinConv + "PM";
		} else if(etHour > 12) {
			etHourConv = (etHour - 12) + ":" + etMinConv + "PM";
		} else {
			etHourConv = etHour + ":" + etMinConv + "AM";
		}
		
		if("A".equals(meetingDays)) {
			mtString = "Arranged";
		} else {
			mtString = this.meetingDays + " " + stHourConv + "-" + etHourConv;
		}
			
		return mtString;
	}
	
	/**
	 * Checks to see if the activity is being added again
	 * @param activity to be checked
	 * @return boolean value on whether 
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * This overridden method from the Conflict interface that checks to see if the activity
	 * to be added to the schedule is conflicting with another activity that is already
	 * on the schedule.
	 * @param possibleConflictingActivity to which we are comparing to the schedule
	 * @throws ConflictException when the activity timings are overlapping / a conflict is 
	 * detected
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		//Activity a = new Event(this.title, this.meetingDays, this.startTime, this.endTime, "");
		boolean flag = false; 
		for(int i = 0; i < meetingDays.length(); i++) {
			char day = meetingDays.charAt(i);
			if(day == 'A') {
				break; 
			}
			for(int j = 0; j < possibleConflictingActivity.meetingDays.length(); j++) {
				if(day == possibleConflictingActivity.meetingDays.charAt(j)) {
					flag = true;
					break;
				}
			}
		}
		
		if(flag) {
			if(this.getStartTime() == possibleConflictingActivity.getEndTime() || 
					this.getEndTime() == possibleConflictingActivity.getStartTime()) {
				throw new ConflictException("Schedule conflict."); 
			}
			
			if (this.getStartTime() >= possibleConflictingActivity.getStartTime()
					&& this.getEndTime() <= possibleConflictingActivity.getEndTime()) {
				throw new ConflictException();
			}
			
			if(this.getStartTime() <= possibleConflictingActivity.getStartTime()
					&& this.getEndTime() >= possibleConflictingActivity.getEndTime()) {
				throw new ConflictException();
			}
			
			if(this.getStartTime() >= possibleConflictingActivity.getStartTime() && this.getStartTime() <=
					possibleConflictingActivity.getEndTime()) {
				throw new ConflictException("Schedule conflict.");
			}
			
			if(this.getEndTime() >= possibleConflictingActivity.getStartTime() && this.getEndTime() <=
					possibleConflictingActivity.getEndTime()) {
				throw new ConflictException("Schedule conflict.");
			}
		}
	}

	/**
	 * Generated a hash code for Course using all fields.
	 * @return hash code for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * @param obj of the Object to compare
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	/**
	 * Outputs a string array of length 4 with the courses title, meeting string, and event details
	 * @return String array
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Outputs a string array of length 7 with the courses title, meeting string, and event details
	 * @return String array
	 */
	public abstract String[] getLongDisplayArray();

}