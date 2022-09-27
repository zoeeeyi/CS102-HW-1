package Apps;

import java.util.*;
import Data.*;

public class CourseRegistrationSystem {
    private static Scanner m_scanner = new Scanner(System.in);
    private static List<Student> m_studentList = new ArrayList<>();
    private static List<Course> m_courseList = new ArrayList<>();
    private static Map<String, Course> m_courseMap = new HashMap<String, Course>();
    private static Map<String, Student> m_studentMap = new HashMap<String,Student>();
    private static Apps.Admin.AdminApp m_adminApp = new Apps.Admin.AdminApp();
    private static Apps.Student.StudentApp m_studentApp = new Apps.Student.StudentApp();

    public static void main(String[] args) {
        //Setting up user
        String _userName, _fName, _lName, _password;
        System.out.println("Please enter your username");
        _userName = m_scanner.next();
        System.out.println("Please enter your first name");
        _fName = m_scanner.next();
        System.out.println("Please enter your last name");
        _lName = m_scanner.next();
        System.out.println("Please enter your password");
        _password = m_scanner.next();

        UserParent _user;

        if (_userName != "Admin") {
            _user = new Student(_userName, _password, _fName, _lName, "Student");
        } else {
            _user = new UserParent(_userName, _password, _fName, _lName, "Admin");
        }

        //Give options to users
        System.out.println("What would you like to do today?");
        if (_user.getIdentity() == "Student"){
            
        }

    }

    private void UpdateCourseMap(){
        Map<String, Course> _newMap = new HashMap<String, Course>();

        for (Course _course : m_courseList) {
            _newMap.put(_course.getCourseUniqueId(), _course);
        }

        m_courseMap = _newMap;
    }

    private void UpdateStudentMap(){
        Map<String, Student> _newMap = new HashMap<String, Student>();

        for (Student _s : m_studentList){
            _newMap.put(_s.getUserName(), _s);
        }

        m_studentMap = _newMap;
    }
}
