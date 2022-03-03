public class CoursePathTests {

    public static void main(String[] args) {
        
    }

    //Integration manager tests:


    //Back end developer tests:
    @Test
    public void testInsert() {
    	CoursePathCalculatorBackEnd<CourseInterface> backendEngine = new CoursePathCalculatorBackEnd<CourseInterface>();
    	boolean insertNullVertex = false;
    	try {
    		backendEngine.insertVertex(null);
    	}
    	catch (Exception e) {
    		insertNullVertex = true;
    	}
    	
    	boolean insertNullEdge = false;
    	try {
    		backendEngine.insertEdge(null, null);
    	}
    	catch (Exception e) {
    		insertNullEdge = true;
    	}
    	
    	CourseInterface A = new CourseInterface("CS", 400, 5, 3);
    	CourseInterface B = new CourseInterface("CS", 300, 5, 3);
    	boolean insertBeforeFirstCourseExists = false;
    	try {
    		backendEngine.insertEdge(B, A);
    	}
    	catch (Exception e) {
    		insertBeforeFirstCourseExists = true;
    	}
    	
    	backendEngine.insertVertex(A);
    	boolean insertDuplicateVertex = backendEngine.insertVertex(A);
    	boolean insertWrongOrder = backendEngine.insertEdge(A, B);
    	backendEngine.insertEdge(A, B);
    	boolean insertDuplicateEdge = backendEngine.insertEdge(A, B);
    	Assertions.assertTrue(insertNullVertex && insertNullEdge && insertBeforeFirstCourseExists && !insertWrongOrder && !insertDuplicateVertex && !insertDuplicateEdge);
    }
    
    @Test
    public static void testRemove() {
    	CoursePathCalculatorBackEnd<CourseInterface> backendEngine = new CoursePathCalculatorBackEnd<CourseInterface>();
    	boolean removeNullVertex = false;
    	try {
    		backendEngine.removeVertex(null);
    	}
    	catch (Exception e) {
    		removeNullVertex = true;
    	}
    	
    	boolean removeNullEdge = false;
    	try {
    		backendEngine.removeEdge(null, null);
    	}
    	catch (Exception e) {
    		removeNullEdge = true;
    	}
    	
    	CourseInterface A = new CourseInterface("CS", 300, 5, 3);
    	CourseInterface B = new CourseInterface("CS", 400, 5, 3);
    	boolean removeUnexistedVertex = backendEngine.removeVertex(A);
    	backendEngine.insertVertex(A);
    	backendEngine.insertVertex(B);
    	boolean removeUnexistedEdge = backendEngine.removeEdge(A, B);
    	backendEngine.insertEdge(A, B);
    	boolean removeEdge = backendEngine.removeEdge(A, B);
    	boolean removeVertex = backendEngine.removeVertex(B);
    	
    	Assertions.assertTrue(removeNullVertex && removeNullEdge && !removeUnexistedVertex && !removeUnexistedEdge && removeEdge && removeVertex);
    }
    
    @Test
    public static void testPath() {
    	CoursePathCalculatorBackEnd<CourseInterface> backendEngine = new CoursePathCalculatorBackEnd<CourseInterface>();
    	CourseInterface CS100 = new CourseInterface("CS", 100, 5, 3);
    	CourseInterface CS200 = new CourseInterface("CS", 200, 4, 4);
    	CourseInterface CS300 = new CourseInterface("CS", 300, 5, 3);
    	CourseInterface CS400 = new CourseInterface("CS", 400, 5, 3);
    	CourseInterface CS500 = new CourseInterface("CS", 500, 3, 3);
    	CourseInterface CS600 = new CourseInterface("CS", 600, 4, 4);
    	backendEngine.insertVertex(CS100);
    	backendEngine.insertVertex(CS200);
    	backendEngine.insertVertex(CS300);
    	backendEngine.insertVertex(CS400);
    	backendEngine.insertVertex(CS500);
    	backendEngine.insertVertex(CS600);
    	backendEngine.insertEdge(CS100, CS200);
    	backendEngine.insertEdge(CS200, CS300);
    	backendEngine.insertEdge(CS300, CS400);
    	backendEngine.insertEdge(CS400, CS500);
    	backendEngine.insertEdge(CS500, CS600);
    	backendEngine.insertEdge(CS100, CS300);
    	backendEngine.insertEdge(CS400, CS600);
    	
    	String output = backendEngine.printer(backendEngine.shortestPath(CS100, CS600));
    	int cost1 = backendEngine.getPathCostWithoutRequisite(CS100, CS500);
    	int cost2 = backendEngine.getPathCostWithRequisite(CS100, CS500);
    	
    	boolean cannotGetCreditForIndirectEdge = false;
    	try {
    		backendEngine.getCredit(CS200, CS400);
    	}
    	catch (Exception e) {
    		cannotGetCreditForIndirectEdge = true;
    	}
    	
    	int edgeCredit = backendEngine.getCredit(CS200, CS300);
    	Assertions.assertTrue(output.equals("CS100 CS300 CS400 CS600 ") && cost1 == 9 && cost2 == 12 && cannotGetCreditForIndirectEdge && edgeCredit == 4);
    }
    


    //Front end developer tests:


    //Data wrangler's tests:


}

