package Apps.Admin;

import java.util.*;
import Apps.Utilities;
import DataHolders.Course;
import DataHolders.Student;

public class AdminApp extends Utilities implements AdminInterface {
    private Scanner m_scanner = new Scanner(System.in);

    public AdminApp() {
        //Constructor
    }

    //Report
    @Override
    public void ViewAllCourses(List<Course> _courseList){
        super.ViewAllCourses(_courseList);
    }

    @Override
    public ArrayList<String> ViewAllCoursesFull(List<Course> _courseList){
        System.out.println("Courses that are full:");
        int _fullNum = 0;
        ArrayList<String> _fullCourseList = new ArrayList<String>();
        for (Course _course : _courseList){
            if (_course.getCurrentStudentNum() == _course.getMaxStudentNum()){
                System.out.print(_course.getCourseId() + ", ");
                System.out.print(_course.getCourseName() + ", ");
                System.out.println("Section: " + _course.getSectionNum());
                _fullCourseList.add(_course.getCourseName() + ", " + _course.getCourseId() + ", Section: " + _course.getSectionNum());
                _fullNum ++;
            }
        }

        //Check if all the classes are full
        if (_fullNum == 0){
            System.out.println("All classes are not full!");
            return null;
        } else {
            return _fullCourseList;
        }
    }

    @Override
    public void ViewAllCourseRegisterdStudents(Map<String, Course> _courseMap, List<Course> _courseList, Map<String, Student> _studentMap){
        try{
            System.out.println("Which course? Enter course ID");
            String _courseId = m_scanner.nextLine();
            System.out.println("What will the section number be for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            m_scanner.nextLine();

            //Check if the course exist
            if (!CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return;
            } else {
                String _uniqueId = _courseId + Integer.toString(_sectionNum);
                if (_courseMap.get(_uniqueId).getCurrentStudentNum() == 0){
                    System.out.println("There's no student!");
                    return;
                }
                System.out.println("List of registered students: ");
                List<String> _listOfStudents = _courseMap.get(_uniqueId).getStudentRegisteredList();
                try{
                    for (String _s : _listOfStudents){
                        String _fName, _lName;
                        _fName = _studentMap.get(_s).getFirstName();
                        _lName = _studentMap.get(_s).getLastName();
                        System.out.println(_fName + " " + _lName);
                    }
    
                } catch (Exception e){
                    System.out.println(_listOfStudents);
                }
            }
            
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
        }
    }

    @Override
    public void CheckStudentRegisteredCourse(Map<String, Student> _studentMap, Map<String, Course> _courseMap){
        try{
            //Check if the student exist
            System.out.println("Which student would you like to check? Enter student's username");
            String _studentUsername = m_scanner.nextLine();
            if (!_studentMap.containsKey(_studentUsername)){
                System.out.println("Student doesn't exist!");
                return;
            }

            List<String> _studentRegisteredCourseList = _studentMap.get(_studentUsername).getRegisteredCourseList();
            if (_studentRegisteredCourseList.size() > 0){
                for (String _s : _studentRegisteredCourseList){
                    System.out.println("Name: " + _courseMap.get(_s).getCourseName() + ", Section: " + _courseMap.get(_s).getSectionNum());
                }
            } else {
                System.out.println("Student isn't in any class!");
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
        }
    }

    @Override
    public void SortCoursesByStudentNum(List<Course> _courseList){
        for (int _currentPos = 0; _currentPos < _courseList.size(); _currentPos ++){
            Course _currentMin = _courseList.get(_currentPos);
            int _currentMinPos = _currentPos;
            for (int _i = _currentPos + 1; _i < _courseList.size(); _i ++){
                if (_courseList.get(_i).getCurrentStudentNum() < _currentMin.getCurrentStudentNum()){
                    _currentMin = _courseList.get(_i);
                    _currentMinPos = _i;
                }
            }
            if (_currentMin != _courseList.get(_currentPos)){
                Course _temp = _courseList.get(_currentPos);
                _courseList.set(_currentPos, _currentMin);
                _courseList.set(_currentMinPos, _temp);
            }
        }

        for (Course _course : _courseList){
            System.out.println(_course.getCourseName() + ", " + _course.getCurrentStudentNum());
        }

        System.out.println("Sorted in ascending order!");
    }

    @Override
    public boolean CreateCourse(Map<String, Course> _courseMap, List<Course> _courseList) {
        try{
            //Ask the admin which course they would like to add
            System.out.println("What course would you like to create? Enter course ID");
            String _courseId = m_scanner.nextLine();
            System.out.println("What will the section number be for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            m_scanner.nextLine();

            //Check if the course already exist
            if (CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return false;
            }

            //Get inputs
            int _maxStudentNum;
            String _courseName, _pointernstructor, _location;
            System.out.println("Okay to add this course!");
            System.out.println("Course Name?");
            _courseName = m_scanner.nextLine();
            System.out.println("Instructor Name?");
            _pointernstructor = m_scanner.nextLine();
            System.out.println("Location?");
            _location = m_scanner.nextLine();
            System.out.println("Max student number?");
            _maxStudentNum = m_scanner.nextInt();
            m_scanner.nextLine();

            //Construct the course
            Course _newCourse = new Course(_maxStudentNum, _sectionNum, _courseName, _courseId, _pointernstructor, _location);
            _courseList.add(_newCourse);
            System.out.println("Course created!");
            return true;
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return false;
        }
    }

    @Override
    public boolean DeleteCourse(Map<String, Course> _courseMap, List<Course> _courseList, List<Student> _studentList) {
        try{
            System.out.println("What course would you like to delete? Enter course ID");
            String _courseId = m_scanner.nextLine();
            System.out.println("What is the section number for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            m_scanner.nextLine();

            //Check if the course exists
            if (!CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return false;
            } else {
                String _uniqueId = _courseId + Integer.toString(_sectionNum);
                _courseList.remove(_courseMap.get(_uniqueId));

                //Update information for each student that registered this course
                for (Student _s : _studentList){
                    List<String> _registeredCourseList = _s.getRegisteredCourseList();
                    if (_registeredCourseList.contains(_uniqueId)){
                        _registeredCourseList.remove(_uniqueId);
                    }
                }

                System.out.println("Course deleted!");
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean EditCourse(Map<String, Course> _courseMap, List<Course> _courseList, List<Student> _studentList, Map<String, Student> _studentMap) {
        try {
            System.out.println("Which course would you like to edit? Enter course ID");
            String _courseId = m_scanner.nextLine();
            System.out.println("What is the section number for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            m_scanner.nextLine();

            if(!CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                return false;
            } else {
                //Get the modifying class
                String _uniqueId = _courseId + Integer.toString(_sectionNum);
                //Put the student in the course
                int _pointerndex = 0;
                for (Course _c : _courseList){
                    if (_c.getCourseUniqueId().equals(_uniqueId)){
                        _pointerndex = _courseList.indexOf(_c);
                        break;
                    }
                }                
                Course _oldCourse = _courseList.get(_pointerndex);

                //Get inputs
                int _maxStudentNum;
                String _courseName, _pointernstructor, _location;
                System.out.println("Enter new information about this course");
                
                //Enter new section number and do some essential checks
                System.out.println("Course's new section number?");
                _sectionNum = m_scanner.nextInt();
                m_scanner.nextLine();
                String _newUniqueId = _courseId + Integer.toString(_sectionNum);
                //Check if it's the same section number
                if(!_newUniqueId.equals(_uniqueId)){
                    if(CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                        System.out.println("You can't change to this section number due to duplicates");
                        return false;
                    }
                    System.out.println("You can continue to edit");    
                }

                System.out.println("Course Name?");
                _courseName = m_scanner.nextLine();
                System.out.println("Instructor Name?");
                _pointernstructor = m_scanner.nextLine();
                System.out.println("Location?");
                _location = m_scanner.nextLine();

                //Edit max student number
                while(true){
                    System.out.println("Max student number?");
                    _maxStudentNum = m_scanner.nextInt();
                    m_scanner.nextLine();
                    if (_maxStudentNum < _oldCourse.getCurrentStudentNum()){
                        System.out.println("Sorry, this is lower than the current number of students,");
                        System.out.println("Please try again!");
                        continue;
                    }
                    _oldCourse.setMaxStudentNum(_maxStudentNum);
                    break;
                }

                //Check if the user need to edit student list
                List<Student> _tempNewStudentList = _studentList;
                while(true){
                    System.out.println("Would you like to edit the student list? Y/N");
                    String _userOption = m_scanner.nextLine();
                    if (_userOption.equalsIgnoreCase("Y")){
                        Map<String, Object> _tempNewInfo = EditCourseMember(_oldCourse, _studentList, _studentMap, _courseMap);
                        if (_tempNewInfo != null){
                            _oldCourse = (Course) _tempNewInfo.get("New Course");
                            _tempNewStudentList = (List<Student>) _tempNewInfo.get("New Student List");
                            break;
                        } else {
                            System.out.println("Edit failed!");
                            break;
                        }
                    } else if (_userOption.equalsIgnoreCase("N")){
                        break;
                    } else {
                        System.out.println("Sorry, that's not a valid option, try again!");
                        continue;
                    }
                }

    
                //Construct the course and transfer the student list to the newly made course object
                Course _newCourse = new Course(_maxStudentNum, _sectionNum, _courseName, _courseId, _pointernstructor, _location);
                _newCourse.setCurrentStudentNum(_oldCourse.getCurrentStudentNum());
                _newCourse.setStudentRegisteredList(_oldCourse.getStudentRegisteredList());

                //Update information for each student that registered this course
                _studentList = _tempNewStudentList;
                for (Student _s : _studentList){
                    List<String> _registeredCourseList = _s.getRegisteredCourseList();
                    if (_registeredCourseList.contains(_uniqueId)){
                        _registeredCourseList.remove(_uniqueId);
                        _registeredCourseList.add(_newUniqueId);
                    }
                }

                //Update course information in list
                _courseList.remove(_pointerndex);
                _courseList.add(_newCourse);
                System.out.println("Course updated!");
                return true;
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return false;
        }
    }

    private Map<String, Object> EditCourseMember(Course _course, List<Student> _studentList, Map<String, Student> _studentMap, Map<String, Course> _courseMap){
        int _userOption;
        Map<String, Object> _returnMap = new HashMap<>();
        //Return objects
        Course _newCourse = _course;
        List<Student> _tempStudentList = _studentList;

        try{
            while (true) {
                System.out.println(" ");
                System.out.println("What would you like to do?");
                System.out.println("1. Add a student to the course");
                System.out.println("2. Remove a student from the course");
                System.out.println("3. Exit");
                _userOption = m_scanner.nextInt();
                m_scanner.nextLine();
                if (_userOption == 3){
                    _returnMap.put("New Course", _newCourse);
                    _returnMap.put("New Student List", _tempStudentList);
                    return _returnMap;
                }
                switch(_userOption){
                    case 1:
                        //Check if the class is full
                        if (_newCourse.getCurrentStudentNum() == _newCourse.getMaxStudentNum()){
                            System.out.println("Sorry, the course is full, try other options!");
                        } else {
                            Course _tempCourse = RegisterStudentToCourse(_newCourse, _tempStudentList, _studentMap, _courseMap);
                            if (_tempCourse != null){
                                _newCourse = _tempCourse;
                            }
                        }
                        break;

                    case 2:
                        Course _tempCourse = RemoveStudentFromCourse(_newCourse, _tempStudentList);
                        if (_tempCourse != null){
                            _newCourse = _tempCourse;
                        }
                        break;
                    
                    default:
                        System.out.println("Not a valid choice, try again!");
                        break;
                }
            }
    
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return null;
        }
    }

    @Override
    public void GetInformationOfCourse(Map<String, Course> _courseMap, Map<String, Student> _studentMap) {
        try{
            System.out.println("Which course would you like to get information of? Enter course ID");
            String _courseId = m_scanner.nextLine();
            System.out.println("What is the section number for the course? Enter an integer");
            int _sectionNum = m_scanner.nextInt();
            m_scanner.nextLine();

            if(CheckIfClassExist(_courseMap, _courseId, _sectionNum)){
                String _uniqueId = _courseId + Integer.toString(_sectionNum);
                Course _course = _courseMap.get(_uniqueId);
                System.out.println("Name: " + _course.getCourseName());
                System.out.println("Instructor: " + _course.getInstructor());
                System.out.println("Current Student Number: " + _course.getCurrentStudentNum());
                System.out.println("Max number of students: " + _course.getMaxStudentNum());
                System.out.println("Location: " + _course.getLocation());
                System.out.println("Registerd students: ");
                for (String _s : _course.getStudentRegisteredList()){
                    System.out.println(_studentMap.get(_s).getFirstName() + " " + _studentMap.get(_s).getLastName());
                }
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
        }
        
        
    }

    @Override
    public boolean RegisterStudent(Map<String, Student> _studentMap, List<Student> _studentList){
        try{
            //Check if the student exist
            System.out.println("Enter student's username to register a new student");
            String _studentUsername = m_scanner.nextLine();
            if (_studentMap.containsKey(_studentUsername)){
                System.out.println("Student already exists!");
                return false;
            }

            String _fName, _lName, _password;
            System.out.println("Please enter the first name of the student");
            _fName = m_scanner.nextLine();
            System.out.println("Please enter the last name of the student");
            _lName = m_scanner.nextLine();
            System.out.println("Please create a password for this student");
            _password = m_scanner.nextLine();

            Student _newStudent = new Student(_studentUsername, _password, _fName, _lName, "Student");
            _studentList.add(_newStudent);
            System.out.println("Student created!");
            return true;
        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return false;
        }
    }

    @Override
    public void DisplayAllStudents(List<Student> _studentList){
        System.out.println("Student List: ");
        for (Student _s : _studentList){
            System.out.println("Username: " + _s.getUserName() + ", Name: " + _s.getFirstName() + " " + _s.getLastName());
        }
    }

    private Course RegisterStudentToCourse(Course _course, List<Student> _studentList, Map<String,Student> _studentMap, Map<String, Course> _courseMap) {
        try{
            //Check if the student exist
            System.out.println("Which student would you like to register? Enter student's username");
            String _studentUsername = m_scanner.nextLine();
            if (!_studentMap.containsKey(_studentUsername)){
                System.out.println("Student doesn't exist!");
                return null;
            }

            //Check if the student is in any other section
            for (String _studentCourseId : _studentMap.get(_studentUsername).getRegisteredCourseList()){
                if (_courseMap.get(_studentCourseId).getCourseId().equals(_course.getCourseId())){
                    System.out.println("This student has already registered to one of the section of the class");
                    return null;
                }
            }

            //Put the student in the course
            _course.getStudentRegisteredList().add(_studentUsername);

            //Add course to the student
            int _studentIndex = -1;
            for (Student _s: _studentList){
                if (_s.getUserName().equals(_studentUsername)){
                    _studentIndex = _studentList.indexOf(_s);
                    break;
                }
            }
            _studentList.get(_studentIndex).getRegisteredCourseList().add(_course.getCourseUniqueId());

            //Update current student number
            _course.UpdateCurrentStudentNum();
            System.out.println("Temporarily added the student to the course! Finish course editing to make this effective!");

            return _course;

        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return null;
        }
    }

    private Course RemoveStudentFromCourse(Course _course, List<Student> _studentList) {
        //return objects!
        //1. Course
        //2. Student list //We need to store student index cuz we can't change the student yet in case edit course operation is unsuccessful!

        try{
            //Check if the student exist in the course
            System.out.println("Which student would you like to remove? Enter student's username");
            String _studentUsername = m_scanner.nextLine();
            if (!_course.getStudentRegisteredList().contains(_studentUsername)){
                System.out.println("Student is not in the course!");
                return null;
            }

            //Remove the student from the course
            _course.getStudentRegisteredList().remove(_studentUsername);
            
            //Remove student from course list
            int _studentIndex = -1;
            for (Student _s: _studentList){
                if (_s.getUserName().equals(_studentUsername)){
                    _studentIndex = _studentList.indexOf(_s);
                    break;
                }
            }
            _studentList.get(_studentIndex).getRegisteredCourseList().remove(_course.getCourseUniqueId());

            //Update current student number
            _course.UpdateCurrentStudentNum();
            System.out.println("Temporarily removed the student to the course! Finish course editing to make this effective!");

            return _course;

        } catch (InputMismatchException e){
            System.out.println("Wrong input type!");
            m_scanner.nextLine();
            return null;
        }
    }

}
