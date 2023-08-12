package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import edu.ncsu.csc216.wolf_scheduler.course.Course;

import java.util.Scanner;
import java.io.FileInputStream;


/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Riya Gunda
 */
public class CourseRecordIO {

    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
    public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
    	Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
        ArrayList<Course> courses = new ArrayList<Course>(); //Create an empty array of Course objects
        while (fileReader.hasNextLine()) { //While we have more lines in the file
            try { //Attempt to do the following
                //Read the line, process it in readCourse, and get the object
                //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
                Course course = readCourse(fileReader.nextLine()); 

                //Create a flag to see if the newly created Course is a duplicate of something already in the list  
                boolean duplicate = false;
                //Look at all the courses in our list
                for (int i = 0; i < courses.size(); i++) {
                    //Get the course at index i
                    Course current = courses.get(i);
                    //Check if the name and section are the same
                    if (course.getName().equals(current.getName()) &&
                            course.getSection().equals(current.getSection())) {
                        //It's a duplicate!
                        duplicate = true;
                        break; //We can break out of the loop, no need to continue searching
                    }
                }
                //If the course is NOT a duplicate
                if (!duplicate) {
                    courses.add(course); //Add to the ArrayList!
                } //Otherwise ignore
            } catch (IllegalArgumentException e) {
                //The line is invalid b/c we couldn't create a course, skip it!
            }
        }
        //Close the Scanner b/c we're responsible with our file handles
        fileReader.close();
        //Return the ArrayList with all the courses we read!
        return courses;
    }
    
    /**
     * Private helper method to add data to set variables
     * @param line line read from the input file
     * @throws IllegalArgumentException if the line is invalid or missing required values
     * @return Course object
     */
    private static Course readCourse(String line) {
    	Scanner sc = new Scanner(line);
    	sc.useDelimiter(",");
    	// check for valid name
    	try {
    		String name = "";
        	String title = "";
        	String section = "";
        	int creditHours = 0;
        	String instructor = "";
        	String meetingDay = "";
        	
        	if(sc.hasNext()) {
				name = sc.next();
			} else {
				sc.close();
				throw new IllegalArgumentException();
			}
			
			if(sc.hasNext()) {
				title = sc.next();
			} else {
				sc.close();
				throw new IllegalArgumentException();
			}
			
			if(sc.hasNext()) {
				section = sc.next();
			} else {
				sc.close();
				throw new IllegalArgumentException();
			}
			
			if(sc.hasNextInt()) {
				creditHours = sc.nextInt();
			} else {
				sc.close();
				throw new IllegalArgumentException();
			}
			
			if(sc.hasNext()) {
				instructor = sc.next();
			} else {
				sc.close();
				throw new IllegalArgumentException();
			}
			
			if(sc.hasNext()) {
				meetingDay = sc.next();
			} else {
				sc.close();
				throw new IllegalArgumentException();
			}
			
			if("A".equals(meetingDay)) {
    			if(sc.hasNext()) {
    				sc.close();
    				throw new IllegalArgumentException();
    			} else {
    				sc.close();
    				Course course = new Course(name, title, section, creditHours, instructor, 
        					meetingDay);
    				return course;
    			}    			
    		} else {
    			int startTime = 0;
            	int endTime = 0;
        		
        		if(sc.hasNextInt()) {
        			startTime = sc.nextInt();
    			} else {
    				sc.close();
    				throw new IllegalArgumentException();
    			}
    			
    			if(sc.hasNextInt()) {
    				endTime = sc.nextInt();
    			} else {
    				sc.close();
    				throw new IllegalArgumentException();
    			}
    			
        		if(sc.hasNext()) {
        			sc.close();
    				throw new IllegalArgumentException();
    			} else {
    				sc.close();
    				Course course = new Course(name, title, section, creditHours, instructor, 
        					meetingDay, startTime, endTime);
    				return course;
    			}
    		}    	
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		}
    	
    }

}