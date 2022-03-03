// --== CS400 Project Three File Header ==--
// Name: Zachary Collins
// Email: ztcollins@wisc.edu
// Team: Red
// Group: CH
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: -
interface EdgeInterface {
	Course getPrerequisite();
	Course getRequisite();
	int getCreditNeeded();
	String toString();
}
public class Edge implements EdgeInterface{
	//THIS IS TAKEN FROM THE BACKEND FOR USAGE IN THE COURSELOADER
	public Course prerequisite;
	public Course requisite;
	public int creditNeeded; //Note that this is A.credits + previousCredits

	public Edge(Course A, Course B) {
		this.prerequisite = A;
		this.requisite = B;
		//this.creditNeeded = A.getCredits();
	}
	
	public String toString() {
		
		return "["+prerequisite.toString()+", "+requisite.toString()+"]";
	}
	

	@Override public Course getPrerequisite() {return prerequisite;}
	@Override public Course getRequisite() {return requisite;}
	@Override public int getCreditNeeded() {return creditNeeded;}

}
