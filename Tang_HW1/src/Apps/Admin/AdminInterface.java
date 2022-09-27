package Apps.Admin;

import java.util.*;
import Data.*;

interface AdminInterface {
    void GetInformationOfCourse(Map<String, Course> _courseMap);
    boolean CreateCourse(Map<String, Course> _courseMap, List<Course> _courseList);
    boolean DeleteCourse(Map<String, Course> _courseMap, List<Course> _courseList);
    boolean EditCourse(Map<String, Course> _courseMap, List<Course> _courseList);
    boolean RegisterStudentToCourse(List<Course> _courseList, List<Student> _studentList, Map<String,Student> _studentMap, Map<String, Course> _courseMap);
}
