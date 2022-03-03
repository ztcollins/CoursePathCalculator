//--== CS400 Project Three File Header ==--
//Name: AiJing Wu
//Email: awu53@wisc.edu
//Team: Red
//Group: CH
//TA: Harper
//Lecturer: Florian Heimerl
//Notes to Grader: NA

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

interface CoursePathCalculatorBackEndInterface<CourseInterface> {    
	public boolean insertVertex(Course newCourse);
	public boolean removeVertex(Course oldCourse);
	public boolean insertEdge(Course courseA, Course courseB);
	public boolean removeEdge(Course courseA, Course courseB);
	public boolean containsVertex(Course course);
	public boolean containsEdge(Course courseA, Course courseB);
	public int getCredit(Course courseA, Course courseB);
	public List<Course> shortestPath(Course courseA, Course courseB);
	public int getPathCostWithoutRequisite(Course courseA, Course courseB);
	public int getPathCostWithRequisite(Course courseA, Course courseB);
	public Course searchCourse(String name);
	public boolean isEmpty();
}

public class CoursePathCalculatorBackEnd<CourseInterface> implements CoursePathCalculatorBackEndInterface<CourseInterface> {

	protected class courseVertex {
		public Course course;
		LinkedList<courseEdge> followingCourses;

		public courseVertex(Course course) {
			if (course == null) {
				throw new NullPointerException("Input has null value");
			}
			this.course = course;
			this.followingCourses = new LinkedList<courseEdge>();
		}
	}

	protected class courseEdge {
		public Course prerequisite;
		public Course requisite;
		public int creditNeeded; //Note that this is A.credits + previousCredits

		public courseEdge(Course A, Course B) {
			this.prerequisite = A;
			this.requisite = B;
			this.creditNeeded = A.getCredits();
		}
	}

	//Use HashMap to store vertices and edges
	//Key = Course
	//Value = other courses the key could lead to
	HashMap<String, courseVertex> courseMap;
	public CoursePathCalculatorBackEnd() {
		courseMap = new HashMap<String, courseVertex>();
	}
	
	private String getCourseString(Course course) {
		//get the String of full course name from course, e.g. "CS400"
		return course.getName().concat(Integer.toString(course.getCourseNumber()));
	}

	@Override
	public boolean insertVertex(Course newCourse) {
		if (newCourse == null) {
			throw new NullPointerException("Input has null value");
		}
		if (containsVertex(newCourse)) {
			return false;
		}
		else {
			courseVertex newV = new courseVertex(newCourse);
			courseMap.put(getCourseString(newCourse), newV);
			return true;
		}
	}

	@Override
	public boolean removeVertex(Course oldCourse) {
		if (oldCourse == null) {
			throw new NullPointerException("Input has null value");
		}
		if (!containsVertex(oldCourse)) {
			return false;
		}
		else {
			courseMap.remove(getCourseString(oldCourse));
			return true;
		}
	}

	@Override
	public boolean insertEdge(Course courseA, Course courseB) {
		if (courseA == null || courseB == null) {
			throw new NullPointerException("Input has null value");
		}
		else if (courseA.equals(courseB)) {
			return false;
		}
		else if (courseA.getCourseNumber() >= courseB.getCourseNumber()) {
			return false;
		}
		else {
			String nameA = getCourseString(courseA);
			String nameB = getCourseString(courseB);
			if (!courseMap.containsKey(nameA) || !courseMap.containsKey(nameB)) {
				throw new IllegalArgumentException("Please insert the courses before insert the edge");
			}
			courseMap.get(nameA).followingCourses.add(new courseEdge(courseA, courseB));
			return true;
		}
	}

	@Override
	public boolean removeEdge(Course courseA, Course courseB) {
		if (!containsEdge(courseA, courseB)) {
			return false;
		}
		else {
			LinkedList<courseEdge> list = courseMap.get(getCourseString(courseA)).followingCourses;
			for (courseEdge ce : list) {
				if (ce.requisite.equals(courseB)) {
					list.remove(ce);
				}
			}
			return true;
		}
	}

	@Override
	public boolean containsVertex(Course course) {
		if (courseMap.containsKey(getCourseString(course))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsEdge(Course courseA, Course courseB) {
		if (!courseMap.containsKey(getCourseString(courseA))) {
			throw new IllegalArgumentException("The first course doesn't exist");
		}
		LinkedList<courseEdge> tempQueue = courseMap.get(getCourseString(courseA)).followingCourses;
		for (courseEdge e : tempQueue) {
			if (e.requisite.equals(courseB)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getCredit(Course courseA, Course courseB) {
		if(courseA == null || courseB == null) {
			throw new NullPointerException("Input has null value");
		}
		courseVertex AVertex = courseMap.get(getCourseString(courseA));
		courseVertex BVertex = courseMap.get(getCourseString(courseB));
		if (AVertex == null || BVertex == null) {
			throw new IllegalArgumentException("The value of credits needed doesn't exist");
		}

		for (courseEdge e : AVertex.followingCourses) {
			if(e.requisite.equals(courseB)) {
				return e.creditNeeded;
			}
		}
		throw new NoSuchElementException("You cannot learn through this path");
	}

	@Override
	public boolean isEmpty() {
		if (courseMap.size() == 0) {
			return true;
		}
		return false;
	}

	protected class coursePath implements Comparable<coursePath> {
		public courseVertex prerequisite; //first course
		public int requiredCredits; //total credits required through the path
		public List<Course> courseSequence; //ordered courses within the path
		public courseVertex requisite; //last course

		public coursePath(courseVertex prerequisite) {
			this.prerequisite = prerequisite;
			this.requiredCredits = 0;
			this.courseSequence = new LinkedList<>();
			this.courseSequence.add(prerequisite.course);
			this.requisite = prerequisite;
		}

		public coursePath(coursePath copyPath, courseEdge extendBy) {
			//Copy the path
			List<Course> newDataSequence = new LinkedList<Course>();
			List<Course> oldDataSequence = copyPath.courseSequence;
			for (int i=0; i<oldDataSequence.size(); i++) {
				newDataSequence.add(oldDataSequence.get(i));
			}
			newDataSequence.add(extendBy.requisite); //extend the path

			this.prerequisite = copyPath.prerequisite; //inherit the prerequisite node from copyPath
			this.requisite = courseMap.get(getCourseString(extendBy.requisite)); //final node of the new path
			this.requiredCredits = copyPath.requiredCredits + extendBy.creditNeeded; //update the overall costs
			this.courseSequence = newDataSequence; //refer courseSequence to a different list
		}

		public int compareTo(coursePath other) {
			int cmp1 = this.requiredCredits - other.requiredCredits; //first use the credits
			int cmp2 = this.requisite.course.getCourseNumber() - other.requisite.course.getCourseNumber(); //Then use the smallest last course number
			if (cmp1 != 0) {
				return cmp1;
			}
			else {
				return cmp2;
			}
		}
	}

	protected coursePath dijkstrasShortestPath(Course prerequisite, Course requisite) {
		//No vertex containing prerequisite or requisite
		if (courseMap == null || courseMap.size() == 0) {
			throw new NoSuchElementException("There is no graph");
		}
		if (!courseMap.containsKey(getCourseString(prerequisite)) || !courseMap.containsKey(getCourseString(requisite))) {
			throw new NoSuchElementException("Either vertex or both vertices do not exist");
		}
		if (prerequisite.equals(requisite)) { //prerequisite and requisite are the same
			return new coursePath(courseMap.get(getCourseString(prerequisite)));
		}

		//preparation
		boolean[] visited = new boolean[courseMap.size()];
		PriorityQueue<coursePath> pathQueue = new PriorityQueue<coursePath>(new Comparator<coursePath>() {
			@Override
			public int compare(coursePath a, coursePath b) {
				if (a.compareTo(b) == 0) { //if still cannot sort a and b, then the one with higher rating of requisite goes first
					return -(a.requisite.course.getRating() - b.requisite.course.getRating());
				}
				else {
					return a.compareTo(b);
				}
			}
		});

		//initialization
		courseVertex pre = courseMap.get(getCourseString(prerequisite));
		coursePath path = new coursePath(pre);
		int preIndex = getIndexofVertex(pre);
		visited[preIndex] = true;

		//loop over the graph
		while (true) {
			path = pathHelper(path, visited, pathQueue);
			if (path == null) {
				throw new NoSuchElementException("There is no path between the required vertices");
			}
			else if (path.requisite.course.equals(requisite)) {
				return path;
			}
			else { //all visited but cannot find path to requisite
				boolean allVisited = true;
				for (int i=0; i<visited.length; i++) {
					if (!visited[i]) {
						allVisited = false;
					}
				}
				if (allVisited) {
					throw new NoSuchElementException("There is no path between the queried courses");
				}
			}
		}
	}

	private int getIndexofVertex(courseVertex v) {
		Iterator<String> vertexIter = courseMap.keySet().iterator();
		String key = getCourseString(v.course);
		for (int i=0; i<courseMap.size(); i++) {
			if (vertexIter.hasNext() && vertexIter.next().equals(key)) {
				return i;
			}
		}
		return -1;
	}

	private coursePath pathHelper(coursePath currPath, boolean[] visited, PriorityQueue<coursePath> pathQueue) {
		//find next possible vertex
		courseVertex pre = currPath.requisite;
		if (!pre.followingCourses.isEmpty()) {
			for (courseEdge edge : pre.followingCourses) {
				int index = getIndexofVertex(courseMap.get(getCourseString(edge.requisite)));
				if (index == -1) {
					throw new NoSuchElementException("There is no such course");
				}
				if (!visited[index]) {
					pathQueue.add(new coursePath(currPath, edge));
				}
			}
		}

		//decide next vertex
		coursePath nextPath = null;
		if (!pathQueue.isEmpty()) {
			nextPath = pathQueue.poll();
			visited[getIndexofVertex(nextPath.requisite)] = true;
		}
		return nextPath;
	}

	public List<Course> shortestPath(Course prerequisite, Course requisite) {
		return dijkstrasShortestPath(prerequisite,requisite).courseSequence;
	}

	public int getPathCostWithoutRequisite(Course prerequisite, Course requisite) {
		// doesn't include the credits of requisite
		return dijkstrasShortestPath(prerequisite, requisite).requiredCredits;
	}
	
	public int getPathCostWithRequisite(Course prerequisite, Course requisite) {
		// include the credits of requisite
		return (getPathCostWithoutRequisite(prerequisite, requisite) + requisite.getCredits());
	}
	
	public Course searchCourse(String name) {
		if (courseMap.containsKey(name)) {
			return courseMap.get(name).course;
		}
		else {
			return null;
		}
	}

	public String printer(List<Course> list) {
		String output = "";
		for (int i=0; i<list.size(); i++) {
			output = output.concat(list.get(i).getName());
			output = output.concat(Integer.toString(list.get(i).getCourseNumber()));
			output = output.concat(" ");
		}
		return output;
	}
}


