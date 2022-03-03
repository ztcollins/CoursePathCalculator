import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CourseTester {

	public static void main(String[] args) throws IOException {
		CourseLoader loader = new CourseLoader();
		loader.loadCourses("/scp/vertices.csv");
		loader.loadEdges("/scp/edges.csv");
		
		
		
		for(int i = 0; i < loader.courses.size(); i++) {
			System.out.println(loader.courses.get(i).toString());
		}
		for(int i = 0; i < loader.edges.size(); i++) {
			System.out.println("("+loader.edges.get(i).getPrerequisite().toString()+", "+loader.edges.get(i).getRequisite().toString()+")");
		}

	}
	
	// Data Wrangler Code Tests
	  @Test
	  public void DataWrangler_loadTest() throws IOException {
	    CourseLoaderInterface a = new CourseLoader();
	    List<Course> b = a.loadCourses("./data/vertices.csv");
	    assertEquals(b.get(0).getName(), "MATH");
	    assertEquals(b.get(1).getName(), "MATH");
	    assertEquals(b.get(2).getName(), "CS");
	    
	    List<Edge> c = a.loadEdges("./data/edges.csv");
	    assertEquals(c.get(0).getPrerequisite().getName(), "CS");
	    assertEquals(c.get(1).getPrerequisite().getName(), "MATH");
	    assertEquals(c.get(2).getPrerequisite().getName(), "CS");
	  }
	  @Test
	  public void DataWrangler_courseTest() {
	    CourseInterface a = new Course("CS", 400, 1, 3);
	    assertEquals(a.getName(), "CS");
	    assertEquals(a.getCourseNumber(), 400);
	    assertEquals(a.getRating(), 1);
	    assertEquals(a.getCredits(), 3);
	  }
	  @Test
	  public void DataWrangler_courseSetterTest() {
	    CourseInterface a = new Course("a", 0, 0, 0);
	    CourseInterface b = new Course("b", 0, 0, 0);
	    assertEquals(a.getName().compareTo(a.getName()), 0);
	    assertNotEquals(a.getName().compareTo(b.getName()), 0);
	  }

}
