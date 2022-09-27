package Apps;

import java.util.*;
import Data.*;

public class Utilities {

    protected boolean CheckIfClassExist(Map<String, Course> _courseMap, String _courseId, int _courseSection){
        String _courseUniqueId = _courseId + Integer.toString(_courseSection);
        if (_courseMap.containsKey(_courseUniqueId)){
            System.out.println("This course already exists!");
            return true;
        } else {
            System.out.println("This course does not exist!");
            return false;
        }
    }

    protected int GetCourseListIndex(List<Course> _courseList, String _uniqueId){
        int _index = -1;
        for (Course _c : _courseList){
            if (_c.getCourseUniqueId() == _uniqueId){
                _index = _courseList.indexOf(_c);
                break;
            }
        }
        return _index;
    }
}
