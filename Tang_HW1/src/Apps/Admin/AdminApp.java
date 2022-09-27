package Apps.Admin;

import java.util.*;

import Apps.Utilities;
import Data.Course;
import Data.Student;

public class AdminApp extends Utilities implements AdminInterface {
    private Scanner m_scanner = new Scanner(System.in);

    public AdminApp() {
        //Constructor
    }

    @Override
    public boolean CreateCourse(Map<String, Course> _courseMap, List<Course> _courseList) {
        try{
            //Ask the admin which course they would like to add
            System.out.println("What course would you like to create? Enter course ID");
            String _courseId = m_scanner.next();
            System.out.println("What will the section number be for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();

            //Check if the course already exist
            if (CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return false;
            }

            //Get inputs
            int _maxStudentNum;
            String _courseName, _instructor, _location;
            System.out.println("Okay to add this course!");
            System.out.println("Course Name?");
            _courseName = m_scanner.next();
            System.out.println("Instructor Name?");
            _instructor = m_scanner.next();
            System.out.println("Location?");
            _location = m_scanner.next();
            System.out.println("Max student number?");
            _maxStudentNum = m_scanner.nextInt();

            //Construct the course
            Course _newCourse = new Course(_maxStudentNum, _sectionNum, _courseName, _courseId, _instructor, _location);
            _courseList.add(_newCourse);
            return true;
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            return false;
        }
    }

    @Override
    public boolean DeleteCourse(Map<String, Course> _courseMap, List<Course> _courseList) {
        try{
            System.out.println("What course would you like to delete? Enter course ID");
            String _courseId = m_scanner.next();
            System.out.println("What is the section number for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();

            //Check if the course exists
            if (!CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return false;
            } else {
                String _uniqueId = _courseId + Integer.toString(_sectionNum);
                _courseList.remove(_courseMap.get(_uniqueId));
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            return false;
        }
    }

    @Override
    public boolean EditCourse(Map<String, Course> _courseMap, List<Course> _courseList) {
        try {
            System.out.println("Which course would you like to edit? Enter course ID");
            String _courseId = m_scanner.next();
            System.out.println("What is the section number for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();

            if(!CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return false;
            } else {
                //Get the modifying class
                String _uniqueId = _courseId + Integer.toString(_sectionNum);
                //Put the student in the course
                int _index = 0;
                for (Course _c : _courseList){
                    if (_c.getCourseUniqueId() == _uniqueId){
                        _index = _courseList.indexOf(_c);
                        break;
                    }
                }                
                Course _oldCourse = _courseList.get(_index);
                _courseList.remove(_index);

                //Get inputs
                int _maxStudentNum;
                String _courseName, _instructor, _location;
                System.out.println("Enter new information about this course");
                System.out.println("Course Name?");
                _courseName = m_scanner.next();
                System.out.println("Instructor Name?");
                _instructor = m_scanner.next();
                System.out.println("Location?");
                _location = m_scanner.next();
                System.out.println("Course section number?");
                _sectionNum = m_scanner.nextInt();
                System.out.println("Max student number?");
                _maxStudentNum = m_scanner.nextInt();    
    
                //Construct the course and transfer the student list to the newly made course object
                Course _newCourse = new Course(_maxStudentNum, _sectionNum, _courseName, _courseId, _instructor, _location);
                _newCourse.setCurrentStudentNum(_oldCourse.getCurrentStudentNum());
                _newCourse.setStudentRegisteredList(_oldCourse.getStudentRegisteredList());

                //Update course information in list
                _courseList.add(_newCourse);
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            return false;
        }
    }

    @Override
    public void GetInformationOfCourse(Map<String, Course> _courseMap) {
        try{
            System.out.println("Which course would you like to get information of? Enter course ID");
            String _courseId = m_scanner.next();
            System.out.println("What is the section number for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();

            if(CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                String _uniqueId = _courseId + Integer.toString(_sectionNum);
                Course _course = _courseMap.get(_uniqueId);
                System.out.println(_course.getCourseName());
                System.out.println(_course.getInstructor());
                System.out.println(_course.getCurrentStudentNum());
                System.out.println(_course.getMaxStudentNum());
                System.out.println(_course.getLocation());
                System.out.println(_course.getStudentRegisteredList().toString());
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
        }
        
        
    }

    @Override
    public boolean RegisterStudentToCourse(List<Course> _courseList, List<Student> _studentList, Map<String,Student> _studentMap, Map<String, Course> _courseMap) {
        try{
            //Check if the student exist
            System.out.println("Which student would you like to register? Enter student's username");
            String _studentUsername = m_scanner.next();
            if (!_studentMap.containsKey(_studentUsername)){
                System.out.println("Student doesn't exist!");
                return false;
            }

            //Check if the course exist
            System.out.println("Which course would you like to register this student to? Enter course ID");
            String _courseId = m_scanner.next();
            System.out.println("What is the section number for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            if (!CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return false;
            } 
            
            String _uniqueId = _courseId + Integer.toString(_sectionNum);
            Course _course = _courseMap.get(_uniqueId);
            //Check if the class is full
            if (_course.getStudentRegisteredList().size() == _course.getMaxStudentNum()){
                System.out.println("Sorry, the course is full!");
                return false;
            } else {
                //Check if the student is in any other section
                for (String _studentCourseId : _studentMap.get(_studentUsername).getRegisteredCourseList()){
                    if (_courseMap.get(_studentCourseId).getCourseId() == _courseMap.get(_uniqueId).getCourseId()){
                        System.out.println("This student has already registered to another section of the class");
                        return false;
                    }
                }

                //Put the student in the course
                int _index = 0;
                for (Course _c : _courseList){
                    if (_c.getCourseUniqueId() == _uniqueId){
                        _index = _courseList.indexOf(_c);
                        break;
                    }
                }
                _courseList.get(_index).getStudentRegisteredList().add(_studentUsername);

                //Put the course into student course list
                for (Student _s: _studentList){
                    if (_s.getUserName() == _studentUsername){
                        _index = _studentList.indexOf(_s);
                        break;
                    }
                }
                _studentList.get(_index).getRegisteredCourseList().add(_uniqueId);
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            return false;
        }
    }
}
