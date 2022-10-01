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
        super.ViewAllCourses(_courseList);
    }

    @Override
    public void ViewAllCoursesNotFull(List<Course> _courseList) {
        System.out.println("Courses that are not full:");
        int _notFullNum = 0;
        for (Course _course : _courseList){
            if (_course.getCurrentStudentNum() < _course.getMaxStudentNum()){
                System.out.print(_course.getCourseId() + ", ");
                System.out.print("Max Students No: " + _course.getMaxStudentNum() + ", ");
                System.out.print("Current Students No: " + _course.getCurrentStudentNum() + ", ");
                int _remainingSpot = _course.getMaxStudentNum() - _course.getCurrentStudentNum();
                System.out.print("Remaining spots: " + _remainingSpot + ", ");
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
            Course _tempCourse = _courseMap.get(_courseId);
            System.out.print(_tempCourse.getCourseId() + ", ");
            System.out.print(_tempCourse.getCourseName() + ", ");
            System.out.println("Section No." + _tempCourse.getSectionNum());
        }
    }

    @Override
    public boolean RegisterOnCourse(Student _student, Map<String, Course> _courseMap, List<Course> _courseList) {
        try{
            //Ask user about the course they want to register to
            System.out.println("Which course would like register to? enter course ID");
            String _courseId = m_scanner.nextLine();
            System.out.println("What will the section number be for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            m_scanner.nextLine();

            //Check if the course exist
            if (!CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return false;
            }
            
            String _uniqueId = _courseId + Integer.toString(_sectionNum);

            //Check if the student already is in this class
            if (_student.getRegisteredCourseList().contains(_uniqueId)){
                System.out.println("You are already in this course!");
                return false;
            }

            Course _course = _courseMap.get(_uniqueId);
            //Check if the class is full
            if (_course.getCurrentStudentNum() == _course.getMaxStudentNum()){
                System.out.println("Sorry, the course is full!");
                return false;
            } else {
                //Check if the student is in any other section
                for (String _studentCourseId : _student.getRegisteredCourseList()){
                    if (_courseMap.get(_studentCourseId).getCourseId().equals(_course.getCourseId())){
                        System.out.println("You have already registered to one of the SECTIONs of the class, you may only take 1 section per semester");
                        System.out.println("Withdraw from that section if you want to switch sections!");
                        return false;
                    }
                }
                //Put the student in the course
                int _index = GetCourseListIndex(_courseList, _uniqueId);
                _courseList.get(_index).getStudentRegisteredList().add(_student.getUserName());

                //Put the course into student course list
                _student.getRegisteredCourseList().add(_uniqueId);

                //Update current student number
                _courseList.get(_index).UpdateCurrentStudentNum();

                System.out.println("Successfully registered!");
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return false;
        }

    }

    @Override
    public boolean WithdrawFromCourse(Student _student, Map<String, Course> _courseMap, List<Course> _courseList) {
        try{
            //Ask user about the course they want to register to
            System.out.println("Which course would like withdraw? Enter course id");
            String _courseId = m_scanner.nextLine();
            System.out.println("Which section are you in? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            m_scanner.nextLine();
            String _uniqueId = _courseId + Integer.toString(_sectionNum);

            //Check if the student have registered the course
            if (!_student.getRegisteredCourseList().contains(_uniqueId)){
                System.out.println("You are not registered to this course of this section!");
                return false;
            } else {
                _student.getRegisteredCourseList().remove(_uniqueId);
                int _index = GetCourseListIndex(_courseList, _uniqueId);
                _courseList.get(_index).getStudentRegisteredList().remove(_student.getUserName());
                //Update current student number
                _courseList.get(_index).UpdateCurrentStudentNum();

                System.out.println("Successfully withdrawn!");
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return false;
        }
    }
}
