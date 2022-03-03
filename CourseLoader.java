// --== CS400 Project Three File Header ==--
// Name: Zachary Collins
// Email: ztcollins@wisc.edu
// Team: Red
// Group: CH
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: -
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

interface CourseLoaderInterface {
	List<Course> loadCourses(String filePath) throws IOException;
	List<Edge> loadEdges(String filePath) throws IOException;
}

public class CourseLoader implements CourseLoaderInterface{
	int courseSize = 0;
	List<Course> courses = new LinkedList<>();
	List<Edge> edges = new LinkedList<>();
	@Override
	public List<Course> loadCourses(String filePath) throws IOException {
		
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String[] header = reader.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (!header[0].equals("name") || !header[1].equals("courseNumber") || !header[2].equals("rating") || !header[3].equals("credits") || header.length != 4) {
            return null;
        }
        String rawCourseData = reader.readLine();
        while (rawCourseData != null) {
        	//System.out.println(rawCourseData);
            String[] courseData = rawCourseData.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            Course course = new Course(courseData[0], Integer.parseInt(courseData[1]), Integer.parseInt(courseData[2]), Integer.parseInt(courseData[3]));
            courses.add(course);
            courseSize++;
            rawCourseData = reader.readLine();
        }
        return courses;
		//return null;
	}

	@Override
	public List<Edge> loadEdges(String filePath) throws IOException {
		
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String[] header = reader.readLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (!header[0].equals("prerequisite") || !header[1].equals("postrequisite")) {
            return null;
        }
        String rawEdgeData = reader.readLine();
        while (rawEdgeData != null) {
        	//System.out.println(rawEdgeData);
            String[] edgeData = rawEdgeData.split(",(?![^()]*\\))");
            
            Course[] edgeCourses = new Course[2];
            
            for(int i = 0; i < edgeData.length; i++) {
            	String[] edgeDataSplit = edgeData[i].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            	for(int k = 0; k < edgeDataSplit.length; k++) {
            		edgeDataSplit[k] = edgeDataSplit[k].replaceAll("[()]", "");
            	}
            	for(int j = 0; j < courseSize; j++) {
            		if(courses.get(j).getName().equals(edgeDataSplit[0]) && 
            				courses.get(j).getCourseNumber() == Integer.parseInt(edgeDataSplit[1])) {
            			edgeCourses[i] = (Course) courses.get(j);
            		}
            	}
            }
            
            Edge edge = new Edge(edgeCourses[0], edgeCourses[1]);
            edges.add(edge);
            rawEdgeData = reader.readLine();
        }
        return edges;
	}
	

}

class CourseLoaderPlaceholder implements CourseLoaderInterface {

	@Override
	public List<Course> loadCourses(String filePath) throws IOException {
		Course CS200 = new Course("CS", 200, 10, 4);
		Course CS300 = new Course("CS", 300, 6, 3);
		Course MATH240 = new Course("MATH", 240, 5, 3);
		List<Course> list = new LinkedList<>();
		list.add(CS200);
		list.add(CS300);
		list.add(MATH240);
		return list;
	}

	@Override
	public List<Edge> loadEdges(String filePath) throws IOException {
		Course CS200 = new Course("CS", 200, 10, 4);
		Course CS300 = new Course("CS", 300, 6, 3);
		Course MATH240 = new Course("MATH", 240, 5, 3);
		List<CourseInterface> list = new LinkedList<>();
		list.add(CS200);
		list.add(CS300);
		list.add(MATH240);
		
		List<Edge> edgeList = new LinkedList<>();
		Edge edge200300 = new Edge(CS200, CS300);
		edgeList.add(edge200300);
		return edgeList;
	}
	
}
