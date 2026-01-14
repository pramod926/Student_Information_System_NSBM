/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sis;

import java.sql.*;
public class StudentDAO {
    // Method to fetch student by ID
    public static Student getStudentByID(String studentId) {
        Student s = new Student();
        try {
            
            Connection con = DBConnection.getConnection(); // your DB connection class
            String sql = "SELECT * FROM students WHERE student_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, studentId);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                s.name = rs.getString("full_name");
                s.email = rs.getString("gmail");
                s.course_id = rs.getString("course_id");
                s.photoPath = rs.getString("photo_path");
                s.studentId = studentId;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
