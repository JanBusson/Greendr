import java.awt.Image;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class sqlUser {
    private static OracleDsSingleton ora = OracleDsSingleton.getInstance();

    public static String getUserLogin(ScreenSwitcher mainWindow) {
    	
    			return mainWindow.getUser();
    }
    public static String getUserNotLiked(String username) {
        ArrayList<String> usernames = new ArrayList<>();
        Random random = new Random();
        String userGender = null;
        
        try (Connection con = ora.getConnection()) {
            // Step 1: Get the gender of the provided username
            String genderQuery = "SELECT gender FROM users WHERE username = ?";
            try (PreparedStatement genderStmt = con.prepareStatement(genderQuery)) {
                genderStmt.setString(1, username);
                try (ResultSet rs = genderStmt.executeQuery()) {
                    if (rs.next()) {
                        userGender = rs.getString("gender");
                    }
                }
            }
            // Step 2: Validate if gender is found
            if (userGender == null) {
                throw new SQLException("User gender not found for username: " + username);
            }

            // Step 3: Determine the opposite gender
            String oppositeGender = userGender.equalsIgnoreCase("male") ? "Female" : "Male";
            //System.out.println(oppositeGender);
            
            // Step 4: Main query to get usernames not liked by the current user and of opposite gender
            String sql = "SELECT u.username " +
                         "FROM users u " +
                         "LEFT JOIN Liked_users l ON u.username = l.likedusername AND l.currusername = ? " +
                         "WHERE u.username != ? AND l.likedusername IS NULL AND u.gender = ?";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username); 
                pstmt.setString(2, username);
                pstmt.setString(3, oppositeGender);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        usernames.add(rs.getString("username"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check if the list is empty
        if (usernames.isEmpty()) {
            return null;
        }

        // Return a random username from the list
        return usernames.get(random.nextInt(usernames.size()));
    }

    public static String getName(String username) {
        String name = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT name FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        name = rs.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getDescription(String username) {
        String description = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT description FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        description = rs.getString("description");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return description;
    }

    public static int getAge(String username) {
        int age = 0;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT age FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        age = rs.getInt("age");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return age;
    }

    public static Image getProfilePicture(String username) {
        Image profilePicture = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT photo FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                    	//External knowledge used -> see 8. BLOB - Oracle Docs file in /quellen
                        Blob blob = rs.getBlob("photo");
                        profilePicture = ImageIO.read(blob.getBinaryStream());
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return profilePicture;
    }
    
    public static String getCity(String username) {
        String city = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT city FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        city = rs.getString("city");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    public static String getLanguages(String username) {
        String languages = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT languages FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        languages = rs.getString("languages");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return languages;
    }

    public static String getInterests(String username) {
        String interests = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT interest_gender FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        interests = rs.getString("interest_gender");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return interests;
    } 
    public static int getEcoScore(String username) {
        int score = 0;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT score FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                    	score = rs.getInt("score");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public static void addLikedUser(String currentUsername, String likedUsername) {
        try (Connection con = ora.getConnection()) {
            // Check if the entry already exists
            String checkSql = "SELECT COUNT(*) FROM liked_users WHERE currusername = ? AND likedusername = ?";
            try (PreparedStatement checkPstmt = con.prepareStatement(checkSql)) {
                checkPstmt.setString(1, currentUsername);
                checkPstmt.setString(2, likedUsername);
                try (ResultSet rs = checkPstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // Entry already exists
                        return; // Exit the method
                    }
                }
            }

            // If the entry does not exist, proceed with the insertion
            String sql = "INSERT INTO liked_users (currusername, likedusername) VALUES (?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, currentUsername);
                pstmt.setString(2, likedUsername);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getLikedUsers(String currentUsername) {
        ArrayList<String> likedUsernames = new ArrayList<>();

        try (Connection con = ora.getConnection()) {

            if (currentUsername != null) {
                String sql = "SELECT lu.likedusername " +
                             "FROM liked_users lu " +
                             "JOIN users u ON lu.likedusername = u.username " +
                             "WHERE lu.currusername = ?";
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setString(1, currentUsername);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            likedUsernames.add(rs.getString("likedusername"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return likedUsernames;
    }


    
    
    public static String getHobbies(String username) {
        String hobbies = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT hobbies FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        hobbies = rs.getString("hobbies");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hobbies;
    }

    public static float getHeight(String username) {
        float height = 0;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT height FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        height = rs.getFloat("height");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return height;
    }

    public static String getJobTitle(String username) {
        String jobTitle = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT JOBTITLE FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        jobTitle = rs.getString("jobtitle");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobTitle;
    }

    public static String getHometown(String username) {
        String hometown = null;
        try (Connection con = ora.getConnection()) {
            String sql = "SELECT HOME_TOWN FROM users WHERE username = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        hometown = rs.getString("home_town");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hometown;
    }
}
