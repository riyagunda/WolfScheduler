/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Wolf scheduler is a system that provides students with a way
 * to determine which course schedule may be the best for their
 * upcoming semester.
 * @author Riya Gunda
 */

public class Course extends Activity {
	
	/**
	 * Constant for the minimum length for a name
	 */
	public static final int MIN_NAME_LENGTH = 5;
	
	/**
	 * Constant for the maximum length for a name
	 */
	public static final int MAX_NAME_LENGTH = 8;
	
	/**
	 * Constant for the minimum letter count
	 */
	public static final int MIN_LETTER_COUNT = 1;
	
	/**
	 * Constant for the maximum letter count
	 */
	public static final int MAX_LETTER_COUNT = 4;
	
	/**
	 * Constant for the digit count
	 */
	public static final int DIGIT_COUNT = 3;
	
	/**
	 * Constant for the section length
	 */
	public static final int SECTION_LENGTH = 3;
	
	/**
	 * Constant for the maximum credits
	 */
	public static final int MAX_CREDITS = 5;
	
	/**
	 * Constant for the minimum credits
	 */
	public static final int MIN_CREDITS = 1;
	
	/** Course's name. */
	private String name;
	
	/** Course's section. */
	private String section;
	
	/** Course's credit hours */
	private int credits;
	
	/** Course's instructor */
	private String instructorId;
	
	/**
	 * Constructs a Course object with values for all fields.
	 * @param name name of the course
	 * @param title title of the course
	 * @param section section of the course
	 * @param credits credit hours for the course
	 * @param instructorId instructor's unity ID
	 * @param meetingDays meeting days for the course 
	 * @param startTime start time for the course
	 * @param endTime end time for the course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
            int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
        setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
    }

	/**
	 * Creates a Course with the given name, title, section, credits, 
	 * instructorId, and meetingDays for courses that are arranged.
	 * @param name name of the course
	 * @param title title of the course
	 * @param section section of the course
	 * @param credits credit hours for the course
	 * @param instructorId instructor's unity ID
	 * @param meetingDays meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name.  If the name is null, has a length less than 5 or more than 8,
	 * does not contain a space between letter characters and number characters, has less than 1
	 * or more than 4 letter characters, and not exactly three trailing digit characters, an
	 * IllegalArgumentException is thrown.
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		if(name == null || name.length() == 0) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		if(name.length() < MIN_NAME_LENGTH  || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		if(" ".equals(name.substring(0, 1))) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		char test = name.charAt(0);
		if(Character.isDigit(test)) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		boolean space = false;
		int counterL = 0;
		int counterD = 0;
		
		for(int i = 0; i < name.length(); i++) {
			if(!space) {
				if(Character.isLetter(name.charAt(i))) {
					counterL++;
				} else if(name.charAt(i) == ' ') {
					space = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if(space) {
				if(Character.isDigit(name.charAt(i))) {
					counterD++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}
		
		if (counterL < MIN_LETTER_COUNT || counterL > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		if (counterD != 3) {
			throw new IllegalArgumentException("Invalid course name.");
		}	
		
		this.name = name;
	}

	/**
	 * Returns the Course's section
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**Sets the Course's section
	 * @param section the section to set
	 * @throws IllegalArgumentException with the message "Invalid section" 
	 * if the section parameter is null
	 * @throws IllegalArgumentException with the message "Invalid section" 
	 * if the section parameter is not equal to 3
	 * @throws IllegalArgumentException with the message "Invalid section" 
	 * if the section parameter is not a digit
	 */
	public void setSection(String section) {
		if(section == null) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		if(section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		for(int i = 0; i < section.length(); i++) {
			char holder = section.charAt(i);
			if(!Character.isDigit(holder)) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		this.section = section;
	}

	/**
	 * Returns the Course's credits
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits
	 * @param credits the credits to set
	 * @throws IllegalArgumentException with the message "Invalid credits" if the value of
	 * the credits variable is invalid
	 */
	public void setCredits(int credits) {
		if(credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		Integer a = credits;
		if(!(a instanceof Integer)){
			throw new IllegalArgumentException("Invalid credits.");
		}
		
		this.credits = credits;
	}

	/**
	 * Returns the Course's instructor ID
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructor ID
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException with the message "Invalid instructor id." if the 
	 * the instructor id value is null or the string is empty
	 */
	public void setInstructorId(String instructorId) {
		if(instructorId == null || "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Displays an array with the course's name, section, title, and meeting string.
	 * @return a string array
	 */
	public String[] getShortDisplayArray() {
		String[] dispArr = new String[4];
		
		dispArr[0] = getName();
		dispArr[1] = getSection();
		dispArr[2] = getTitle();
		dispArr[3] = getMeetingString();
		
		return dispArr;
	}
	
	/**
	 * Displays an array with the course's name, section, title, credits, instructor ID, and meeting string
	 * @return a string array with 7 elements
	 */
	public String[] getLongDisplayArray() {
		String[] dispArr = new String[7];
		
		dispArr[0] = getName();
		dispArr[1] = getSection();
		dispArr[2] = getTitle();
		dispArr[3] = "" + this.credits;
		dispArr[4] = this.instructorId;
		dispArr[5] = getMeetingString();
		dispArr[6] = "";
		
		return dispArr;
	}
	
	/**
	 * This method checks to see if the same course has already been
	 * added to the schedule
	 * @param activity to be added
	 * @return boolean true if the course has not been added before
	 */
	@Override
    public boolean isDuplicate(Activity activity) {
		 //if (activity instanceof Course) {
	     Course otherCourse = (Course) activity;
	     return this.name.equals(otherCourse.name);
    }
	
	/**
	 * Generated a hash code for Course using all fields.
	 * @return hash code for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * @param obj of the Object to compare
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (!instructorId.equals(other.instructorId))
			return false;
		if (!name.equals(other.name))
			return false;
		else{
			return section.equals(other.section);
		}
		//return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + "," + 
	    getStartTime() + "," + getEndTime(); 
	}
	
	/**
	 * This method is overriding the method from the activity class
	 * @param meetingDays of the course
	 * @param startTime of the course
	 * @param endTime of the course
	 * @throws IllegalArgumentException with the message "Invalid meeting days and times." if:
	 * 										- meetingDays is not M,T,W,H,F or A
	 * 										- meetingDays is A and the strings length is not 1
	 * 										- The same day is repeated more than once in the string
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		int countM = 0;
		int countT = 0;
		int countW = 0;
		int countH = 0;
		int countF = 0;
		int countA = 0;
		
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
			} else if("A".equals(md)) {
				if(meetingDays.length() != 1) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			} else {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		
		if(countM > 1 || countT > 1 || countW > 1 || countH > 1 || countF > 1 || countA > 1) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
}
