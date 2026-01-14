package sis;

import java.sql.*;


public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/students_info"; // change 'studentdb' to your DB name
    private static final String USER = "root";  // your DB username
    private static final String PASS = "12345";  // your DB password
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8+ driver
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
        }
    }

    // Get a Connection object
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static boolean insertUser(String email, String password, String mobile, int course_id) {
        String sql = "INSERT INTO users(email, password_hash, mobile_number,course_id) VALUES(?,?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, mobile);
            ps.setInt(4,course_id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkCredentials(String email, String password) {
        String sql = "SELECT 1 FROM users WHERE email=? AND password_hash=?";
        try (Connection con = getConnection(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean userExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
    
    // --- ENUM Mapping Helper Methods (Fixes Data Truncation Errors) ---

    /**
     * Maps UI category strings (e.g., "Programming") to DB ENUM (e.g., "SOFTWARE_ENGINEERING")
     */
    private static String mapCategoryToEnum(String uiCategory) {
        // Based on DB ENUM: 'WEB_DEVELOPMENT','MOBILE_DEVELOPMENT','AI_ML','DATA_SCIENCE','CYBER_SECURITY','NETWORKING','UI_UX','GRAPHIC_DESIGN','BUSINESS','SOFTWARE_ENGINEERING','OTHER'
        return switch (uiCategory) {
            case "Programming" -> "SOFTWARE_ENGINEERING"; 
            case "Design" -> "GRAPHIC_DESIGN"; 
            case "Data Science" -> "DATA_SCIENCE";
            case "Business" -> "BUSINESS";
            case "Marketing" -> "OTHER"; // Best guess mapping
            default -> "OTHER"; 
        };
    }
    
    /**
     * Maps UI duration strings (e.g., "1 Month") to DB ENUM (e.g., "HOURS_20")
     */
    private static String mapDurationToEnum(String uiDuration) {
        // Based on DB ENUM: 'HOURS_10','HOURS_20','HOURS_30','HOURS_40','HOURS_50_PLUS'
        return switch (uiDuration) {
            case "1 Week" -> "HOURS_10";
            case "1 Month" -> "HOURS_20"; 
            case "6 Months" -> "HOURS_40";
            case "1 Year" -> "HOURS_50_PLUS";
            default -> "HOURS_10"; 
        };
    }

    /**
     * Maps UI cost strings (e.g., "Free") to DB ENUM (e.g., "25000LKR")
     */
    private static String mapCostToEnum(String uiCost) {
        // Based on DB ENUM: '25000LKR','50000LKR','75000LKR','100000LKR','125000LKR','150000LKR'
        return switch (uiCost) {
            case "Free" -> "25000LKR"; // Mapping Free to the lowest listed price in the DB
            case "Paid - Basic" -> "50000LKR";
            case "Paid - Premium" -> "75000LKR";
            case "Subscription" -> "100000LKR"; 
            default -> "25000LKR"; 
        };
    }
    
    /**
     * Maps UI mode strings (e.g., "In-person") to DB ENUM (OFFLINE, ONLINE, HYBRID).
     */
    private static String mapModeToEnum(String uiMode) {
        // Based on DB ENUM: 'ONLINE','OFFLINE','HYBRID'
        String upperMode = uiMode.toUpperCase();
        return switch (upperMode) {
            case "IN-PERSON", "OFFLINE" -> "OFFLINE";
            case "ONLINE", "SELF-PACED" -> "ONLINE"; 
            case "HYBRID" -> "HYBRID";
            default -> "ONLINE";
        };
    }

    // --- Insert Course Method ---

    public static boolean insertCourse(String courseName, String level, String category, String mode, String duration, 
                                       String language, String targetAudience, String cost, String description, 
                                       String popularity, String prerequisites) {
        
        // Apply all necessary mappings and uppercase transformations
        String dbLevel = level.toUpperCase();
        String dbCategory = mapCategoryToEnum(category);
        String dbMode = mapModeToEnum(mode);
        String dbDuration = mapDurationToEnum(duration);
        String dbLanguage = language.toUpperCase();
        String dbTargetAudience = targetAudience.toUpperCase().replace(' ', '_');
        String dbCost = mapCostToEnum(cost); 
        String dbPopularity = popularity.toUpperCase();

        // SQL statement 
        String sql = "INSERT INTO courses ("
                   + "course_name, level, category, mode, duration_hours, language, " 
                   + "target_audience, cost, description, popularity, prerequisites"
                   + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                System.err.println("Course Insert Failed: No DB Connection!");
                return false;
            }

            // Set parameters using the mapped values
            ps.setString(1, courseName);
            ps.setString(2, dbLevel);
            ps.setString(3, dbCategory);
            ps.setString(4, dbMode); 
            ps.setString(5, dbDuration);
            ps.setString(6, dbLanguage);
            ps.setString(7, dbTargetAudience);
            ps.setString(8, dbCost); 
            ps.setString(9, description);
            ps.setString(10, dbPopularity);
            ps.setString(11, prerequisites);

            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Course Inserted Successfully.");
            }
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Insert Course SQL Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Insert Course General Error: " + e.getMessage());
            return false;
        }
    }

    // --- Search Course Method (for Suggest_Course.java) ---
    
    /**
     * Searches the courses table based on optional filtering criteria.
     * Note: The caller (Suggest_Course) is responsible for closing the ResultSet and Connection.
     */
    public static ResultSet searchCourses(String level, String category, String mode, String duration) {
        
        StringBuilder sql = new StringBuilder("SELECT course_name FROM courses WHERE 1=1");
        java.util.List<String> params = new java.util.ArrayList<>();

        // Dynamically build the WHERE clause based on non-empty filters
        if (level != null && !level.trim().isEmpty()) {
            sql.append(" AND level = ?");
            params.add(level.toUpperCase());
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND category = ?");
            params.add(mapCategoryToEnum(category)); 
        }
        if (mode != null && !mode.trim().isEmpty()) {
            sql.append(" AND mode = ?");
            params.add(mapModeToEnum(mode)); 
        }
        if (duration != null && !duration.trim().isEmpty()) {
            sql.append(" AND duration_hours = ?");
            params.add(mapDurationToEnum(duration)); 
        }

        try {
            Connection con = getConnection();
            if (con == null) return null;

            PreparedStatement ps = con.prepareStatement(sql.toString());
            
            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            System.out.println("Executing Search SQL: " + sql.toString());
            return ps.executeQuery();

        } catch (SQLException e) {
            System.err.println("Search Courses SQL Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Search Courses General Error: " + e.getMessage());
            return null;
        }
    }
}
