// --== CS400 Project Three File Header ==--
// Name: Zachary Collins
// Email: ztcollins@wisc.edu
// Team: Red
// Group: CH
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: -
interface CourseInterface {
	String getName();
	int getCourseNumber();
	int getRating();
	int getCredits();
	String toString();
}
public class Course implements CourseInterface{
	String name; // CS200 = CS
	int courseNumber; //CS200 = 200
	int rating; // rating from 1-10
	int credits; //CS200 has 4 credits
	
	public Course(String name, int courseNumber, int rating, int credits) {
		this.name = name;
		this.courseNumber = courseNumber;
		this.rating = rating;
		this.credits = credits;
	}
	
	public String toString() {
		
		return "["+name+", "+courseNumber+", "+rating+", "+credits+"]";
	}

	@Override public String getName() {return name;}
	@Override public int getCourseNumber() {return courseNumber;}
	@Override public int getRating() {return rating;}
	@Override public int getCredits() {return credits;}

}
