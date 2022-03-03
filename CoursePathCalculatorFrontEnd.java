// --== CS400 Project Three File Header ==--
// Name: Xindi Tang
// Email: xtang89@wisc.edu
// Team: Red
// Group: CH
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

interface CoursePathCalculatorFrontEndInterface {
  public void run(CoursePathCalculatorBackEndInterface engine);
  // Course Path Calculator Menu:
  // 1. Overview
  // 2. Search for class information
  // 3. Prompt user to input a class that has been taken and another class that wants to take,
  // then display the shortest path if exists
  // 4. Remove a class that doesn't want to take
  // 5. Quit Course Path Calculator
}


class CoursePathCalculatorFrontEndPlaceholder implements CoursePathCalculatorFrontEndInterface {
  public void run(CoursePathCalculatorBackEndInterface engine) {
    System.out.println("This front end has not been implemented yet.");
  }
}


public class CoursePathCalculatorFrontEnd implements CoursePathCalculatorFrontEndInterface {
  Scanner in = new Scanner(System.in);
  boolean status = true; // true == run, false == stop

  public void run(CoursePathCalculatorBackEndInterface engine) {
    String input = "";
    while (status) {
      printMenu();
      input = in.next();
      switch (input) {
        case "1":
          overview(engine);
          break;
        case "2":
          searchCourse(engine);
          break;
        case "3":
          shortestPath(engine);
          break;
        case "4":
          removeCourse(engine);
          break;
        case "5":
          exit();
          break;
        default:
          System.out.println("Input cannot be understood. Please try again.");
          break;
      }
    }
  }

  private void removeCourse(CoursePathCalculatorBackEndInterface engine) {
    System.out.println("Please enter a course that you'd like to avoid in your path:");
    System.out.println("(e.g. CS200)");
    String course = in.next();
    if (engine.searchCourse(course) != null) {
      Course course_r = engine.searchCourse(course);
      engine.removeVertex(course_r);
      System.out.println(course + " has been removed.");
    } else {
      System.out.println("Sorry. This class does not count for CS requirement.");
    }
  }

  private void shortestPath(CoursePathCalculatorBackEndInterface engine) {
    System.out.println("Please enter a course that you've taken:");
    System.out.println("(e.g. CS200)");
    String course_start = in.next();
    if (engine.searchCourse(course_start) != null) {
      Course course_s = engine.searchCourse(course_start);
      System.out.println("Please enter a course that you want to take:");
      String course_end = in.next();
      if (engine.searchCourse(course_end) != null) {
        Course course_e = engine.searchCourse(course_end);
        System.out.println("Shortest path from " + course_start + " to " + course_end + " :");
        String path = engine.printer(engine.shortestPath(course_s, course_e));
        System.out.println(path);
        System.out.println("Credits needed for this path is "
            + engine.getPathCostWithoutRequisite(course_s, course_e)
            + ", excluding the starting course.");
        System.out.println(
            "Credits needed for this path is " + engine.getPathCostWithRequisite(course_s, course_e)
                + ", including the starting course.");
      } else {
        System.out.println("Sorry. This class does not count for CS requirement.");
      }
    } else {
      System.out.println("Sorry. This class does not count for CS requirement.");
    }
  }

  private void searchCourse(CoursePathCalculatorBackEndInterface engine) {
    // search for a class, return its credits, rating etc.
    System.out.println("Please enter a course you want to know:");
    String course = in.next();
    if (engine.searchCourse(course) != null) {
      System.out.println(engine.searchCourse(course).toString());
    }
  }

  private void exit() {
    status = false;
    System.out.println("Thank you for using CS Course Path Calculator! See you next time!");
  }

  private void overview(CoursePathCalculatorBackEndInterface engine) {
    // check if there is data loaded in courseMap
    System.out.println("This application can be used to find the optimal course path for you.");
    if (engine.isEmpty()) {
      System.out.println("Course data needs to be imported to use the application.");
    } else {
      System.out.println("It is now ready to use!");
    }
  }

  private void printMenu() {
    System.out.println("Welcome to CS Course Path Calculator!");
    System.out.println("Menu: (enter a number to indicate your selection)");
    System.out.println("1. Overview");
    System.out.println("2. Search for course information");
    System.out.println("3. Find the optimal course path for a course you want to take");
    System.out.println("4. Remove a course that you don't want to take");
    System.out.println("5. Quit");
  }

}


