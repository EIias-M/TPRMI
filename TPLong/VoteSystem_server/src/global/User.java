package global;

import java.io.Serializable;

public class User implements Serializable {


    public int StudentNumber;
    public String password;

    public User(int snumber, String passw){
        this.StudentNumber=snumber;
        this.password=passw;


    }

    public User() {

    }

    public int getStudentNumber() {
        return StudentNumber;
    }
    public String getPassword() {
        return password;
    }
}
