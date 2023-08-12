/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.scheduler;
import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Event;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;
/**
 * This class reads in and stores as a list of all the Course records.
 * @author Riya Gunda 
 */
public class WolfScheduler {
	
	/**
	 * Field for the title of a students schedule
	 */
	public String title;
	
	/**
	 * Field for an array list that holds the catalog of courses available
	 */
	public ArrayList<Course> catalog;
	
	/**
	 * Field for an array list that holds the courses in the schedules
	 */
	public ArrayList<Activity> schedule;
	
	/**
	 * Constructor that sets the value for title and catalog
	 * @param validTestFile name of the input file
	 * @throws IllegalArgumentException if the file name is invalid
	 */
	public WolfScheduler(String validTestFile) {
		schedule = new ArrayList<Activity>();
		this.title = "My Schedule";
		
		try {
			catalog = CourseRecordIO.readCourseRecords(validTestFile);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.");
		}
	}
	
	/**
	 * Creates an array of the course's names, sections, and titles
	 * @return a 2D array of available courses to select from
	 */
	public String[][] getCourseCatalog() {
		String[][] tableArr = new String[catalog.size()][3];
		
		for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            tableArr[i] = c.getShortDisplayArray();
        }
        return tableArr;
	}

	/**
	 * Creates an array of the course's names, sections, and titles in the schedule
	 * @return a 2D array of courses in the schedule
	 */
	public String[][] getScheduledActivities() {
		String[][] tableArr = new String[schedule.size()][4];
		
		for (int i = 0; i < schedule.size(); i++) {
			Activity c = schedule.get(i);
            tableArr[i] = c.getShortDisplayArray();
        }
        return tableArr;
	}

	/**
	 * Creates an array of the course's names, sections, titles, credits, and
	 * meeting days and time in the schedule
	 * @return array of courses in the schedule
	 */
	public String[][] getFullScheduledActivities() {
		String[][] tableArr = new String[schedule.size()][7];
		
		for(int i = 0; i < schedule.size(); i++) {
			Activity c = schedule.get(i);
            tableArr[i] = c.getLongDisplayArray();
		}
		return tableArr;
	}

	/**
	 * Returns a specified course if it is in the catalog
	 * @param name of the course
	 * @param section of the course
	 * @return course object
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for(int i = 0; i < catalog.size(); i++) {
			if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}

	/**
	 * Adds new course to the schedule if it has not been added already and does not cause conflicts
	 * with any courses in the schedule
	 * @param name name of course to be added
	 * @param section of the course to be added
	 * @throws IllegalArgumentException if the course to be added causes a conflict
	 * @return true if the course can be added
	 */
	public boolean addCourseToSchedule(String name, String section) {

		Course courseToAdd = getCourseFromCatalog(name, section);
		if(!catalog.contains(courseToAdd)) {
			return false;
		}
		
		if(schedule.size() == 0) {
			schedule.add(courseToAdd);
			return true;
		} else {			
			boolean dup = false;
			for (Activity activity : schedule) {
	            dup = activity.isDuplicate(courseToAdd);
	            if(dup) {
	            	throw new IllegalArgumentException("You are already enrolled in " + name);
	            }
	        }		    
		}

		try {		
			for(int i = 0; i < schedule.size(); i++) {
				if(schedule.get(i) instanceof Event) {
					continue;
				}
				Activity c = schedule.get(i);
	            c.checkConflict(courseToAdd);
			}
		} catch(ConflictException e) { 
			throw new IllegalArgumentException("The course cannot be added due to a conflict.");
		}
		
	    schedule.add(courseToAdd);
	    return true;
	}

	
	/**
	 * Removes specified course from the schedule
	 * @param idx index value of the course to be removed
	 * @return true if the course can be removed
	 */
	public boolean removeActivityFromSchedule(int idx) throws IndexOutOfBoundsException{
		if (idx >= schedule.size() || idx < 0) {
	        return false;
	    } 
		if (idx < schedule.size()){ 
	        schedule.remove(idx);
	        return true;
	    }
		return false;
	}
	
	/**
	 * Resets the schedule completely 
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Activity>();		
	}

	/**
	 * Returns the title of the schedule
	 * @return title of the schedule
	 */
	public String getScheduleTitle() {
		return title;
	}
	
	/**
	 * Sets the title value for the schedule
	 * @param title for the schedule
	 * @throws IllegalArgumentException with the message "Title cannot be null" if the
	 * title is null
	 */
	public void setScheduleTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
	 * Adds event to schedule if it is not being duplicated
	 * @param eventTitle of the event
	 * @param eventMeetingDays of the event
	 * @param eventStartTime of the event
	 * @param eventEndTime of the event
	 * @param eventDetails of the event
	 * @throws IllegalArgumentException with the message "You have created an event called..." if the 
	 * user tries to add an event that already exists with the same name in the schedule
	 * @throws IllegalArgumentException with the message "The course cannot be added due to a conflict."
	 * if the event to be added causes a timing conflict with an event in the schedule
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime, String eventDetails) {
		Activity otherEvent = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);
	    
		for(Activity runner : schedule) {
			if (otherEvent.isDuplicate(runner)) {
		        throw new IllegalArgumentException("You have already created an event called " + eventTitle);
		    }
		} 
		
		try {		 
			for(int i = 0; i < schedule.size(); i++) {
				if(schedule.get(i) instanceof Course) {
					continue;
				}
				Activity c = schedule.get(i);
	            c.checkConflict(otherEvent);
			}
		} catch(ConflictException e) {
			throw new IllegalArgumentException("The event cannot be added due to a conflict.");
		}
		  
		
	    schedule.add(otherEvent);
	} 
	
	/**
	 * Exports the final schedule into a file
	 * @param fileName to be printed into
	 * @throws IllegalArgumentException if the file name is invalid
	 */
	public void exportSchedule(String fileName) {
		try {
			ActivityRecordIO.writeActivityRecords(fileName, schedule);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");  
		}
		
		
	}
}
