package Apps.Student;

import java.util.*;

import DataHolders.Course;
import DataHolders.Student;

interface StudentInterface {
    void ViewAllCourses(List<Course> _courseList);
    void ViewAllCoursesNotFull(List<Course> _courseList);
    void ViewAllCoursesThatYouRegistered(Student _student, Map<String, Course> _courseMap);
    boolean RegisterOnCourse(Student _student, Map<String, Course> _courseMap, List<Course> _courseList);
    boolean WithdrawFromCourse(Student _student, Map<String, Course> _courseMap, List<Course> _courseList);
}
