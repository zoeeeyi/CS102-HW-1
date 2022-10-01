package Apps;

import java.util.*;

import DataHolders.*;

public class Utilities {
    protected void ViewAllCourses(List<Course> _courseList){
        System.out.println("Course List:");
        for (Course _course : _courseList) {
            System.out.println(_course.getCourseId() + ", Section: " + _course.getSectionNum()
            + ", Current Student No: " + _course.getCurrentStudentNum() + ", Maximum Student Allowed: " 
            + _course.getMaxStudentNum() + ", Name:" + _course.getCourseName());
        }
    }

    protected boolean CheckIfClassExist(Map<String, Course> _courseMap, String _courseId, int _courseSection){
        String _courseUniqueId = _courseId + Integer.toString(_courseSection);
        if (_courseMap.containsKey(_courseUniqueId)){
            System.out.println("This course exists!");
            return true;
        } else {
            System.out.println("This course does not exist yet!");
            return false;
        }
    }

    protected int GetCourseListIndex(List<Course> _courseList, String _uniqueId){
        int _index = -1;
        for (Course _c : _courseList){
            if (_c.getCourseUniqueId().equals(_uniqueId)){
                _index = _courseList.indexOf(_c);
                break;
            }
        }
        return _index;
    }
}
