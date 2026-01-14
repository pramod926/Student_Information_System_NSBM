package sis;
public class Student {

    // Public fields
    public String studentId;
    public String name;
    public String course_id;
    public String email;
    public String photoPath;

    // Empty constructor
    public Student() {
        
    }

    // Constructor with parameters
    public Student(String studentId, String name, String course_id, String email, String photoPath) {
        this.studentId = studentId;
        this.name = name;
        this.course_id = course_id;
        this.email = email;
        this.photoPath = photoPath;
    }
}