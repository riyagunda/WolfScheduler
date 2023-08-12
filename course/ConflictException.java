/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * The ConflictException class represents an exception that is thrown when a schedule conflict occurs.
 * @author Riya Gunda
 */
public class ConflictException extends Exception {
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ConflictException with the specified error message.
	 * @param message that should be returned when the constructor is called
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new ConflictException with a default error message of "Schedule conflict."
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
}
