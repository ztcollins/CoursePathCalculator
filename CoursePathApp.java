import java.io.IOException;
import java.util.List;

public class CoursePathApp {
    public static void main(String[] args) throws IOException {
        CoursePathCalculatorBackEnd engine = new CoursePathCalculatorBackEnd();

	CourseLoader ldr = new CourseLoader();
	List<Course> courses = ldr.loadCourses("./data/vertices.csv");
	for (int i = 0; i < courses.size(); i++) {
		engine.insertVertex(courses.get(i));
	}
	List<Edge> edges = ldr.loadEdges("./data/edges.csv");
	for (int i = 0; i < edges.size(); i++) {
		engine.insertEdge(edges.get(i).getPrerequisite(), edges.get(i).getRequisite());
	}

	CoursePathCalculatorFrontEnd chassi = new CoursePathCalculatorFrontEnd();
	chassi.run(engine);
    }
}

