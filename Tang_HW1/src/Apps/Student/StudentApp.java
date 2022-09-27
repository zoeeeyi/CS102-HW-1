package Apps.Student;

import java.util.*;
import Apps.Utilities;
import DataHolders.*;

public class StudentApp extends Utilities implements StudentInterface{
    private Scanner m_scanner = new Scanner(System.in);

    public StudentApp(){
        //Constructor
    }

    @Override
    public void ViewAllCourses(List<Course> _courseList){
        System.out.println("Course List:");
        for (Course _course : _courseList) {
            System.out.print(_course.getCourseId() + " ,");
            System.out.println(_course.getCourseName());
        }
    }

    @Override
    public void ViewAllCoursesNotFull(List<Course> _courseList) {
        System.out.println("Courses that are not full:");
        int _notFullNum = 0;
        for (Course _course : _courseList){
            if (_course.getCurrentStudentNum() < _course.getStudentRegisteredList().size()){
                System.out.print(_course.getCourseId() + " ,");
                System.out.println(_course.getCourseName());
                _notFullNum ++;
            }
        }

        //Check if all the classes are full
        if (_notFullNum == 0){
            System.out.println("All classes are full!");
        }
    }

    @Override
    public void ViewAllCoursesThatYouRegistered(Student _student, Map<String, Course> _courseMap) {
        List<String> _coursesThatYouRegistered = _student.getRegisteredCourseList();
        if (_coursesThatYouRegistered.size() == 0){
            System.out.println("You haven't registered to any course");
            return;
        } else {
            System.out.println("Courses that you have registered:");
        }

        for (String _courseId : _coursesThatYouRegistered) {
            System.out.print(_courseMap.get(_courseId).getCourseId() + " ,");
            System.out.println(_courseMap.get(_courseId).getCourseName() + " ,");
            System.out.println(_courseMap.get(_courseId).getSectionNum() + " ,");
        }
    }

    @Override
    public boolean RegisterOnCourse(Student _student, Map<String, Course> _courseMap, List<Course> _courseList) {
        try{
            //Ask user about the course they want to register to
            System.out.println("Which course would like register to? enter course ID");
            String _courseId = m_scanner.next();
            System.out.println("What will the section number be for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();

            //Check if the course exist
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
                for (String _studentCourseId : _student.getRegisteredCourseList()){
                    if (_courseMap.get(_studentCourseId).getCourseId() == _courseMap.get(_uniqueId).getCourseId()){
                        System.out.println("You have already registered to another section of the class");
                        return false;
                    }
                }
                //Put the student in the course
                int _index = GetCourseListIndex(_courseList, _uniqueId);
                _courseList.get(_index).getStudentRegisteredList().add(_student.getUserName());

                //Put the course into student course list
                _student.getRegisteredCourseList().add(_uniqueId);
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            return false;
        }

    }

    @Override
    public boolean WithdrawFromCourse(Student _student, Map<String, Course> _courseMap, List<Course> _courseList) {
        try{
            //Ask user about the course they want to register to
            System.out.println("Which course would like withdraw? Enter course id");
            String _courseId = m_scanner.next();
            System.out.println("Which section are you in? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            String _uniqueId = _courseId + Integer.toString(_sectionNum);

            //Check if the student have registered the course
            if (!_student.getRegisteredCourseList().contains(_uniqueId)){
                System.out.println("You are not registered to this course of this section!");
                return false;
            } else {
                _student.getRegisteredCourseList().remove(_uniqueId);
                int _index = GetCourseListIndex(_courseList, _uniqueId);
                _courseList.get(_index).getStudentRegisteredList().remove(_student.getUserName());
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            return false;
        }
    }
}
