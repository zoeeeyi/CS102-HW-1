package Apps;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import DataHolders.*;

public class CourseRegistrationSystem {
    private static Scanner m_scanner = new Scanner(System.in);
    private static List<Student> m_studentList = new ArrayList<>();
    private static List<Course> m_courseList = new ArrayList<>();
    private static Map<String, Course> m_courseMap = new HashMap<String, Course>();
    private static Map<String, Student> m_studentMap = new HashMap<String,Student>();
    private static Apps.Admin.AdminApp m_adminApp = new Apps.Admin.AdminApp();
    private static Apps.Student.StudentApp m_studentApp = new Apps.Student.StudentApp();

    public static void main(String[] args) {
        //Load files
        if (!Deserialize()) {
            return;
        }
        UpdateCourseMap();
        UpdateStudentMap();

        //Get user
        UserParent _user = CredentialCheck();
        if (_user == null){
            System.out.println("There's something wrong during your login.");
            return;
        }

        //Give options to users
        while(true){
            try{
                int _userOption;
                System.out.println(" ");
                System.out.println("**************************");
                System.out.println("What would you like to do today?");
                if (_user.getIdentity().equals("Student")){
                    //Student Menu
                    System.out.println("1. View all courses");
                    System.out.println("2. View all courses that are not full");
                    System.out.println("3. View all courses that you registered");
                    System.out.println("4. Register to a course");
                    System.out.println("5. Withdraw from a course");
                    System.out.println("6. Exit");
    
                    //Take student input
                    _userOption = m_scanner.nextInt();
                    m_scanner.nextLine();
                    if (_userOption == 6){
                        break;
                    }
                    switch(_userOption){
                        case 1:
                            m_studentApp.ViewAllCourses(m_courseList);
                            break;
    
                        case 2:
                            m_studentApp.ViewAllCoursesNotFull(m_courseList);
                            break;
    
                        case 3:
                            m_studentApp.ViewAllCoursesThatYouRegistered((Student) _user, m_courseMap);
                            break;
    
                        case 4:
                            boolean _registered = m_studentApp.RegisterOnCourse((Student) _user, m_courseMap, m_courseList);
                            if (_registered) {
                                UpdateStudent((Student)_user);
                                UpdateCourseMap();
                            }
                            break;
    
                        case 5:
                            boolean _withdrawed = m_studentApp.WithdrawFromCourse((Student) _user, m_courseMap, m_courseList);
                            if (_withdrawed) {
                                UpdateStudent((Student)_user);
                                UpdateCourseMap();
                            }
                            break;
    
                        default:
                            System.out.println("Not a valid choice, please try again");
                            break;
                    }
                }
    
                if (_user.getIdentity().equals("Admin")){
                    System.out.println("1. Get Reports");
                    System.out.println("2. Course Management");
                    System.out.println("3. Exit");
                    _userOption = m_scanner.nextInt();
                    m_scanner.nextLine();
                    if (_userOption == 1){
                        while(true){
                            System.out.println(" ");
                            System.out.println("**************************");            
                            System.out.println("Get Reports Options");
                            System.out.println("1. View all courses");
                            System.out.println("2. View all courses that are full");
                            System.out.println("3. Write to a file of the list of courses that are full");
                            System.out.println("4. View all registered student of a course");
                            System.out.println("5. View the list of courses that a given student is being registered on");
                            System.out.println("6. Sort courses based on current number of students");
                            System.out.println("7. Exit");
                            _userOption = m_scanner.nextInt();
                            m_scanner.nextLine();
                            if (_userOption == 7){
                                break;
                            }
                            switch(_userOption){
                                case 1:
                                    m_adminApp.ViewAllCourses(m_courseList);
                                    break;
    
                                case 2:
                                    m_adminApp.ViewAllCoursesFull(m_courseList);
                                    break;
    
                                case 3:
                                    ArrayList<String> _fullCourseList = m_adminApp.ViewAllCoursesFull(m_courseList);
                                    if (_fullCourseList != null){
                                        ExportFullCourses(_fullCourseList);
                                    }
                                    break;
    
                                case 4:
                                    m_adminApp.ViewAllCourseRegisterdStudents(m_courseMap, m_courseList, m_studentMap);
                                    break;
    
                                case 5:
                                    m_adminApp.CheckStudentRegisteredCourse(m_studentMap, m_courseMap);
                                    break;
    
                                case 6:
                                    m_adminApp.SortCoursesByStudentNum(m_courseList);
                                    break;
    
                                default:
                                    System.out.println("Not a valid choice, please try again");
                                    break;
                            }
                        }
                    } else if (_userOption == 2){
                        while(true){
                            System.out.println(" ");
                            System.out.println("**************************");            
                            System.out.println("Course Management Options");
                            System.out.println("1. Create a new course");
                            System.out.println("2. Delete a course");
                            System.out.println("3. Edit a course");
                            System.out.println("4. Display information for a given course");
                            System.out.println("5. Register a student");
                            System.out.println("6. Exit");
                            _userOption = m_scanner.nextInt();
                            m_scanner.nextLine();
                            if (_userOption == 6){
                                break;
                            }
                            switch(_userOption){
                                case 1:
                                    boolean _created = m_adminApp.CreateCourse(m_courseMap, m_courseList);
                                    if(_created) UpdateCourseMap();
                                    break;
    
                                case 2:
                                    boolean _deleted = m_adminApp.DeleteCourse(m_courseMap, m_courseList, m_studentList);
                                    if(_deleted) UpdateCourseMap();
                                    break;
    
                                case 3:
                                    boolean _edited = m_adminApp.EditCourse(m_courseMap, m_courseList, m_studentList, m_studentMap);
                                    if(_edited) UpdateCourseMap();
                                    break;
    
                                case 4:
                                    m_adminApp.GetInformationOfCourse(m_courseMap, m_studentMap);
                                    break;
    
                                case 5:
                                    boolean _registered = m_adminApp.RegisterStudent(m_studentMap, m_studentList);
                                    if (_registered) {
                                        UpdateStudentMap();
                                    }
                                    break;
    
                                default:
                                    System.out.println("Not a valid choice, please try again");
                                    break;
                            }
                        }
                    } else if (_userOption == 3){
                        break;
                    } else {
                        System.out.println("Not a valid choice, please try again");
                        break;
                    }
    
                }
            } catch (InputMismatchException e){
                System.out.println("Wrong input type! Try again!");
                m_scanner.nextLine();
                continue;
            }

        }

        //Wrap up
        System.out.println("Saving files...");
        boolean _saveFileStatus = Serialize(m_courseList, m_studentList);
        if (_saveFileStatus) {
            System.out.println("Save complete!");
        } else {
            System.out.println("Something went wrong");
        }
        
        m_scanner.close();
        System.out.println("Thanks for using Course Registraton System. Programmed by Zoey Tang.");

    }

    @SuppressWarnings("unchecked")
    static boolean Deserialize(){
        boolean _success = true;
		try{
            //Read course file
            FileInputStream fis = new FileInputStream("Data/CourseList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            m_courseList = (List<Course>) ois.readObject();
            for (int i = 0; i<m_courseList.size(); i++){
                m_courseList.get(i).UpdateCurrentStudentNum();
            }
            ois.close();
            fis.close();
            System.out.println("Previous save successfully loaded!");
        } catch(IOException ioe) {
            System.out.println("No course list file found, reading from CSV");
            _success = readCSV();
        } catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
            _success = false;
        }

        try{
            //Read student file
            FileInputStream fis = new FileInputStream("Data/StudentList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            m_studentList = (List<Student>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e){
            System.out.println("No student list file found, we will create a new one when you close the app.");
        } catch(IOException ioe) {
            System.out.println(ioe);
            _success = false;
        } catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
            _success = false;
        }
        return _success;
    }

    static boolean Serialize(List<Course> _courseList, List<Student> _studentList){
        boolean _successPart1 = false;
        String _fileName = "Data/CourseList.ser";
        while(true){
            try {
                //FileOutput Stream writes data to a file
                FileOutputStream fos = new FileOutputStream(_fileName, false);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                
                //Writes the specific object to the OOS
                oos.writeObject(_courseList);

                _successPart1 = true;
                //Close both streams
                oos.close();
                fos.close();
                break;
            } catch (FileNotFoundException fnfe){
                File _newFile = new File(_fileName);
                try{
                    _newFile.createNewFile();
                    continue;
                } catch (IOException e){
                    System.out.println("Create new course list file failed!");
                    break;
                }
            } catch (IOException ioe) {
                System.out.println("File created, but saving course list failed!");
                break;
            }
        }

        boolean _successPart2 = false;
        _fileName = "Data/StudentList.ser";
        while (true){
            try {
                //FileOutput Stream writes data to a file
                FileOutputStream fos = new FileOutputStream(_fileName, false);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                
                //Writes the specific object to the OOS
                oos.writeObject(_studentList);
                
                _successPart2 = true;
                //Close both streams
                oos.close();
                fos.close();
                break;
            } catch (FileNotFoundException fnfe){
                File _newFile = new File(_fileName);
                try{
                    _newFile.createNewFile();
                    continue;
                } catch (IOException e){
                    System.out.println("Create new student list file failed!");
                    break;
                }
            } catch (IOException ioe) {
                System.out.println("File created, but saving student list failed!");
                break;
            }
        }

        return (_successPart1 && _successPart2);
    }

    static boolean readCSV()
    {
        boolean _success = false;
        String _filepath = "Data/MyUniversityCoursesFile.csv";
        /*try{
            System.out.println("Please enter the path for course list csv");
            _filepath =  m_scanner.nextLine();
        } catch (InputMismatchException e){
            System.out.println("Not a valid entry");
        }*/
        try {
            // Create an object of _filereader class with CSV _filepath as a parameter.
            FileReader _filereader = new FileReader(_filepath);
            BufferedReader _br = new BufferedReader(_filereader);
            String _newLine;
            ArrayList<Course> _newCourseList = new ArrayList<>();

            //Read the header line
            _br.readLine();

            while ((_newLine = _br.readLine()) != null){
                //Convert a line into list of strings
                String[] _courseInfo = _newLine.split(",");
                System.out.println(_courseInfo);

                //Convert strings to course data
                String _courseName = _courseInfo[0];
                String _courseId = _courseInfo[1];
                int _maxStudentNum = Integer.parseInt(_courseInfo[2]);
                //int _currentStudentNum = Integer.parseInt(_courseInfo[3]);
                List<String> _studentRegisteredList = (_courseInfo[4] != null && !_courseInfo[4].equalsIgnoreCase("NULL")) ? Arrays.asList(_courseInfo[4].split(",")) : new ArrayList<>();
                String _instructor = _courseInfo[5];
                int _sectionNum = Integer.parseInt(_courseInfo[6]);
                String _location = _courseInfo[7];

                //Create course class
                Course _newCourse = new Course(_maxStudentNum, _sectionNum, _courseName, _courseId, 
                _instructor, _location, _studentRegisteredList);
                _newCourseList.add(_newCourse);
            }
            System.out.println("CSV successfully loaded!");
            _success = true;
            m_courseList = _newCourseList;
            _br.close();
            _filereader.close();
        }
        catch (Exception e) {
            System.out.println("CSV loading failed!");
            System.out.println(e);
        }
        return _success;
    }

    private static void ExportFullCourses(ArrayList<String> _fullCourseList){
        String _fileName = "Data/Full Courses.txt";

        while(true){
            try{
                FileWriter fileWriter = new FileWriter(_fileName, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for (String _s: _fullCourseList){
                    bufferedWriter.write(_s);
                    bufferedWriter.newLine();
                }
                System.out.println("Exported file successfully!");
                bufferedWriter.close();
                fileWriter.close();
                break;
            } catch (FileNotFoundException fnfe){
                File _newFile = new File(_fileName);
                try{
                    _newFile.createNewFile();
                }catch (IOException e){
                    System.out.println("Create new course list file failed!");
                    break;
                }
                continue;
            } catch (IOException ioe){
                System.out.println(ioe);
                break;
            }
        }
	}


    private static UserParent CredentialCheck(){
        UserParent _currentUser = null;

        //Getting input from user
        while(true){
            String _userName, _password;
            System.out.println("Would you like to log in? Y/N");
            String _userOption = m_scanner.nextLine();
            if (_userOption.equalsIgnoreCase("N")){
                return null;
            } else if (!_userOption.equalsIgnoreCase("Y")){
                System.out.println("Not a valid option, try again");
                continue;
            }
            System.out.println("Keep in mind! All usernames and passwords in this application are CASE SENSITIVE");
            System.out.println("Please enter your username");
            _userName = m_scanner.nextLine();
            if(!m_studentMap.containsKey(_userName) && !_userName.equalsIgnoreCase("Admin")){
                System.out.println("Sorry, you are not a registered student or Admin. If it's the first case, request Admin to register you");
                continue;
            }

            System.out.println("Please enter your password");
            _password = m_scanner.nextLine();

            if(_userName.equalsIgnoreCase("Admin")) {
                if (!_password.equals("Admin001")){
                    System.out.println("Sorry, wrong password, please try again");
                    continue;    
                } else {
                    _currentUser = new UserParent();
                    return _currentUser;
                }
            }
            
            if (!_password.equals(m_studentMap.get(_userName).getPassword())){
                    System.out.println("Sorry, wrong password, please try again");
                    continue;    
            } else {
                _currentUser = m_studentMap.get(_userName);
                return _currentUser;
            }
        }
    }

    private static void UpdateCourseMap(){
        Map<String, Course> _newMap = new HashMap<String, Course>();

        for (Course _course : m_courseList) {
            _newMap.put(_course.getCourseUniqueId(), _course);
        }

        m_courseMap = _newMap;
    }

    private static void UpdateStudentMap(){
        Map<String, Student> _newMap = new HashMap<String, Student>();

        for (Student _s : m_studentList){
            _newMap.put(_s.getUserName(), _s);
        }

        m_studentMap = _newMap;
    }

    private static void UpdateStudent(Student _student){
        int _index = 0;
        for (Student _s: m_studentList){
            if (_s.getUserName().equals(_student.getUserName())){
                _index = m_studentList.indexOf(_s);
                break;
            }
        }
        m_studentList.set(_index, _student);
    }
}
