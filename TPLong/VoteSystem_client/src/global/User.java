package global;

public class User {


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
