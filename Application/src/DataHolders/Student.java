package DataHolders;

import java.util.*;

public class Student extends UserParent{
    List<String> registeredCourseList = new ArrayList<>();

    public Student(String userName, String password, String firstName, String lastName, String identity) {
        super(userName, password, firstName, lastName, identity);
    }

    //Getters and Setters

    public List<String> getRegisteredCourseList() {
        return registeredCourseList;
    }

    public void resetRegisteredCourseList(){
        this.registeredCourseList = new ArrayList<>();
    }

    public void setRegisteredCourseList(List<String> registeredCourseList) {
        this.registeredCourseList = registeredCourseList;
    }

    

}
