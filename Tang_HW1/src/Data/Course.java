package Data;
import java.util.*;;

public class Course {
    int maxStudentNum;
    int currentStudentNum = 0;
    int sectionNum;

    String courseUniqueId;
    String courseName;
    String courseId;
    String instructor;
    String location;

    List<String> studentRegisteredList = new ArrayList<String>();

    public Course(int maxStudentNum, int sectionNum, String courseName, String courseId,
            String instructor, String location) {
        this.maxStudentNum = maxStudentNum;
        this.sectionNum = sectionNum;
        this.courseName = courseName;
        this.courseId = courseId;
        this.instructor = instructor;
        this.location = location;
        this.courseUniqueId = this.courseId + Integer.toString(this.sectionNum);
    }

    //Getters and Setters

    public int getMaxStudentNum() {
        return maxStudentNum;
    }

    public void setMaxStudentNum(int maxStudentNum) {
        this.maxStudentNum = maxStudentNum;
    }

    public int getCurrentStudentNum() {
        return currentStudentNum;
    }

    public void setCurrentStudentNum(int currentStudentNum) {
        this.currentStudentNum = currentStudentNum;
    }

    public int getSectionNum() {
        return sectionNum;
    }

    public void setSectionNum(int sectionNum) {
        this.sectionNum = sectionNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getStudentRegisteredList() {
        return studentRegisteredList;
    }

    public void setStudentRegisteredList(List<String> studentRegisteredList) {
        this.studentRegisteredList = studentRegisteredList;
    }

    public String getCourseUniqueId() {
        return courseUniqueId;
    }

    public void setCourseUniqueId(String courseUniqueId) {
        this.courseUniqueId = courseUniqueId;
    }    
}
