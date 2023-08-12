/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * This interface checks for possible schedule conflicts and avoids them by throwing ConflictExceptions
 * @author Riya Gunda
 */
public interface Conflict {
	/**
	 * This method compares the timings of the activity to be added to the courses and activities
	 * to the ones that are already in the schedule to see if they are causing any schedule conflicts
	 * @param possibleConflictingActivity holds the activity to be added to the schedule
	 * @throws ConflictException when the event or course to be added clashes with the already existing 
	 * schedule
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
