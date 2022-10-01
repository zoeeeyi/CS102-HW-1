package Apps.Admin;

import java.util.*;

import DataHolders.*;

interface AdminInterface {
    void ViewAllCourses(List<Course> _courseList);
    ArrayList<String> ViewAllCoursesFull(List<Course> _courseList);
    void ViewAllCourseRegisterdStudents(Map<String, Course> _courseMap, List<Course> _courseList, Map<String, Student> _studentMap);
    void CheckStudentRegisteredCourse(Map<String, Student> _studentMap, Map<String, Course> _courseMap);
    void SortCoursesByStudentNum(List<Course> _courseList);
    void GetInformationOfCourse(Map<String, Course> _courseMap, Map<String, Student> _studentMap);
    boolean CreateCourse(Map<String, Course> _courseMap, List<Course> _courseList);
    boolean DeleteCourse(Map<String, Course> _courseMap, List<Course> _courseList, List<Student> _studentList);
    boolean EditCourse(Map<String, Course> _courseMap, List<Course> _courseList, List<Student> _studentList, Map<String, Student> _studentMap);
    boolean RegisterStudent(Map<String, Student> _studentMap, List<Student> _studentList);
}
