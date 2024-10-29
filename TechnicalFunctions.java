import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class TechnicalFunctions {

	private static OracleDsSingleton ora;
	private static Connection con; 
	private static PreparedStatement pstmt;
	private static RegisterView a;
	private static String string;
	
	public static String getUsername(ScreenSwitcher screenSwitcher) {
		RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
		
		if (registerView.getUsername().length()>0) {
			System.out.println("Hi");
			return registerView.getUsername();
		} else {
			System.out.println("Hi2");
			return registerView.getUsername();
		}
		
	}

	//External knowledge used -> see 3. Icon - Oracle Docs file in /quellen
	public static void updateGeneralInfo1(ScreenSwitcher screenSwitcher, String name, String age, String height, String city, Icon image) {
		RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
		ora = OracleDsSingleton.getInstance();
		string = registerView.getUsername();

		try {
			String query = "UPDATE USERS SET name = ?, age = ?, height = ?, city = ?, photo = ? WHERE username = ?";
			con = ora.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setInt(2, Integer.parseInt(age));
			pstmt.setInt(3, Integer.parseInt(height));
			pstmt.setString(4, city);
			pstmt.setBytes(5, iconToBytes(image));
			pstmt.setString(6, string);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	//External knowledge used -> see 3. Icon - Oracle Docs file in /quellen
	private static byte[] iconToBytes(Icon icon) {
	    if (icon instanceof ImageIcon) {
	        ImageIcon imageIcon = (ImageIcon) icon;
	        BufferedImage bufferedImage = new BufferedImage(
	                imageIcon.getIconWidth(),
	                imageIcon.getIconHeight(),
	                BufferedImage.TYPE_INT_RGB);
	        bufferedImage.getGraphics().drawImage(imageIcon.getImage(), 0, 0, null);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try {
	            ImageIO.write(bufferedImage, "jpg", baos);
	            return baos.toByteArray();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	
	//External knowledge used -> see 3. Icon - Oracle Docs file in /quellen
	public static ImageIcon fetchImage(ScreenSwitcher screenSwitcher) {
	    ora = OracleDsSingleton.getInstance();
	    RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
	    string= registerView.getUsername();
 	    try {
	        String query = "SELECT photo FROM USERS WHERE username = ?";
	        con = ora.getConnection();
	        pstmt = con.prepareStatement(query);
	        pstmt.setString(1, string);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	
	        	 //External knowledge used -> see 8. BLOB - Oracle Docs file in /quellen
	            Blob blob = rs.getBlob("photo");
	            byte[] bytes = blob.getBytes(1, (int) blob.length());
	            return new ImageIcon(bytes);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    return null;
	}
	
	public static void updateGeneralInfo2(ScreenSwitcher screenSwitcher, String gender, String sexuality, String interestGender) {
		ora = OracleDsSingleton.getInstance();
		RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
		string = registerView.getUsername();

		try {
			String query = "UPDATE USERS SET gender = ?, sexuality = ?, interest_gender = ? WHERE username = ?";
			con = ora.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, gender);
			pstmt.setString(2, sexuality);
			pstmt.setString(3, interestGender);
			pstmt.setString(4, string);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public static void updateGeneralInfo3(ScreenSwitcher screenSwitcher, String hometown, String languages) {
		ora = OracleDsSingleton.getInstance();
		RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
		string = registerView.getUsername();

		try {
			String query = "UPDATE USERS SET home_town = ?, languages = ? WHERE username = ?";
			con = ora.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, hometown);
			pstmt.setString(2, languages);
			pstmt.setString(3, string);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public static void updateGeneralInfo4(ScreenSwitcher screenSwitcher, String workPlace, String jobTitle, String Uni_College) {
		ora = OracleDsSingleton.getInstance();
		RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
		string = registerView.getUsername();

		try {
			String query = "UPDATE USERS SET workplace = ?, jobtitle = ?, college_uni = ? WHERE username = ?";
			con = ora.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, workPlace);
			pstmt.setString(2, jobTitle);
			pstmt.setString(3, Uni_College);
			pstmt.setString(4, string);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public static void updateFreeTimeView(ScreenSwitcher screenSwitcher, String drinking, String smoking, String marijuana, String drugs) {
		ora = OracleDsSingleton.getInstance();
		RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
		string = registerView.getUsername();

		try {
			String query = "UPDATE USERS SET drinking = ?, smoking = ?, marijuana = ?, drugs = ? WHERE username = ?";
			con = ora.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, drinking);
			pstmt.setString(2, smoking);
			pstmt.setString(3, marijuana);
			pstmt.setString(4, drugs);
			pstmt.setString(5, string);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public static void updateDescriptionView(ScreenSwitcher screenSwitcher, String description) {
		ora = OracleDsSingleton.getInstance();
		RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
		string = registerView.getUsername();

		try {
			String query = "UPDATE USERS SET description = ? WHERE username = ?";
			con = ora.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setNString(1, description);
			pstmt.setString(2, string);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public static void updateHobbiesView(ScreenSwitcher screenSwitcher, String hobbies) {
		ora = OracleDsSingleton.getInstance();
		RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
		string = registerView.getUsername();

		try {
			String query = "UPDATE USERS SET hobbies = ? WHERE username = ?";
			con = ora.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setNString(1, hobbies);
			pstmt.setString(2, string);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public static void registerUser(String username,String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = OracleDsSingleton.getInstance().getConnection();
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } finally {
            closeResources(conn, pstmt, null);
        }
    }
	
	private static void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static boolean checkUnique(String username, String password) {
        boolean isUnique = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = OracleDsSingleton.getInstance().getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                isUnique = count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }

        return isUnique;
    }
	
	// Checking length, then if it has minimum 1 UpperCase, 1 LowerCase, 1 Number, 1 Special Character
	 	public static boolean isPasswordValid(String password) {
	             if (password.length() < 8) return false;
	             
	             boolean hasUpper = false;
	             boolean hasLower = false;
	             boolean hasDigit = false;
	             boolean hasSpecial = false;
	             
	             for (char c : password.toCharArray()) {
	                 if (Character.isUpperCase(c)) hasUpper = true;
	                 if (Character.isLowerCase(c)) hasLower = true;
	                 if (Character.isDigit(c)) hasDigit = true;
	                 if (!Character.isLetterOrDigit(c)) hasSpecial = true;
	             }
	             
	             return hasUpper && hasLower && hasDigit && hasSpecial;
	         }

	 	//External knowledge used -> see 3. Icon - Oracle Docs file in /quellen
		public static boolean readyToUpdate1(JPanel generalInfoPanel, String name, String age, String height, String city, Icon photo) {
			// Checking if all fields are filled in
			if (name.isEmpty() || age.isEmpty() || height.isEmpty() || city.isEmpty() || photo == null) {
				JOptionPane.showMessageDialog(generalInfoPanel, "All fields are required. Please try again!");
				return false;
			}
			// If age<18 -> exit from the program
			if (Integer.parseInt(age) < 18) {
				JOptionPane.showMessageDialog(generalInfoPanel, "You must be at least 18 years old!");
				System.exit(0);
			// If age>120 - try again)
			} else if (Integer.parseInt(age) > 120) {
				JOptionPane.showMessageDialog(generalInfoPanel, "You are Top-Gun, but we think you are little younger!");
				return false;
			}
			if (!onlyLetters(name)) {
				JOptionPane.showMessageDialog(generalInfoPanel, "You have numbers / special characters in your name - don't make us angry!");
				return false;
				}
			
			if (!onlyLetters(city)) {
				JOptionPane.showMessageDialog(generalInfoPanel, "You have numbers / special characters in your city - don't make us angry!");
				return false;
			}
			
			if (!onlyNumeric(age)) {
				JOptionPane.showMessageDialog(generalInfoPanel, "You have letters / special characters in your age - don't make us angry!");
				return false;
			}
			
			if (!onlyNumeric(height)) {
				JOptionPane.showMessageDialog(generalInfoPanel, "You have letters / special characters in your height - don't make us angry!");
				return false;
			}
			if (Integer.parseInt(height) < 70 || Integer.parseInt(height)>230) {
				JOptionPane.showMessageDialog(generalInfoPanel, "We think you put the wrong height, please check one more time!");
				return false;
			}
			return true;
		}
		
		public static boolean onlyLetters(String str) {
			for (char c : str.toCharArray()) {
		        if (!Character.isLetter(c)) {
		            return false;
		        }
		    }
		    return true;
		}
		
		public static boolean onlyNumeric(String str) {
		    for (char c : str.toCharArray()) {
		        if (!Character.isDigit(c)) {
		            return false;
		        }
		    }
		    return true;
		}
		
		public static boolean readyToUpdate2(JPanel generalInfoPanel, String gender, String sexuality, String interestGender) {
			if (gender == null || sexuality == null || interestGender == null) {
                JOptionPane.showMessageDialog(generalInfoPanel, "All fields are required. Please try again!");
                return false;
            }
			return true;
		}
		
		public static boolean readyToUpdate3(JPanel generalInfoPanel, String homeTown, String[] selectedLanguages) {
			if (homeTown.isEmpty() || selectedLanguages.length == 0) {
                JOptionPane.showMessageDialog(generalInfoPanel, "All fields are required. Please try again!");
                return false;
            }
			if (!onlyLetters(homeTown)) {
				JOptionPane.showMessageDialog(generalInfoPanel, "You have numbers / special characters in your home town - don't make us angry!");
				return false;
			}
			return true;
		}
		
		public static boolean readyToUpdate4(JPanel generalInfoPanel, String workPlace, String jobTitle, String collegeUni) {
			if (workPlace == null || jobTitle == null || collegeUni == null) {
                JOptionPane.showMessageDialog(generalInfoPanel, "All fields are required. Please try again!");
                return false;
            }
			return true;
		}
		
		public static boolean readyFreeTime(JPanel generalInfoPanel, String drinking, String smoking, String marijuana, String drugs) {
			if (drinking.equals("Select") || smoking.equals("Select") || marijuana.equals("Select") || drugs.equals("Select")) {
	            JOptionPane.showMessageDialog(generalInfoPanel, "All fields are required. Please select all options!");
	            return false;
	        }
			return true;
		}
		public static boolean readyDescription(JPanel generalInfoPanel, String description) {
			if (description.isEmpty() || description.equals("Describe yourself in 1 sentence.... Work hard, live harder")) {
	             JOptionPane.showMessageDialog(generalInfoPanel, "Please enter a valid description!");
	             return false;
	         }
			return true;
		}
		public static boolean readyHobbies(JPanel generalInfoPanel, String hobbies) {
			if (hobbies.isEmpty() || hobbies.equals("List your hobbies here...")) {
	            JOptionPane.showMessageDialog(generalInfoPanel, "Please enter your hobbies!");
	            return false;
	        }
			return true;
		}
		
		public static boolean checkExistence(String username) {
            boolean exists = false;
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            

            try {
                conn = OracleDsSingleton.getInstance().getConnection();
                String query = "SELECT COUNT(*) FROM users WHERE username = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt(1);
                    exists = count > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResources(conn, pstmt, rs);
            }

            return exists;
        }
		 
		public static String resetPassword(String username) {
            String newPassword = generateRandomPassword();
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                conn = OracleDsSingleton.getInstance().getConnection();
                String query = "UPDATE users SET password = ? WHERE username = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, newPassword);
                pstmt.setString(2, username);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResources(conn, pstmt, null);
            }

            return newPassword;
        }
		
		private static String generateRandomPassword() {
            int length = 8;
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder password = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                password.append(characters.charAt(random.nextInt(characters.length())));
            }
            return password.toString();
        }

		public static boolean verifyPassword(String username, String password) {
            boolean isVerified = false;
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = OracleDsSingleton.getInstance().getConnection();
                String query = "SELECT password FROM users WHERE username = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    isVerified = password.equals(storedPassword);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResources(conn, pstmt, rs);
            }

            return isVerified;
        }
		
		public static void insertScore(CurrentQuizModel currentQ, ScreenSwitcher screenSwitcher) {
	        Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            RegisterView registerView = (RegisterView) screenSwitcher.getView("registerView");
    		string = registerView.getUsername();
	        try {
	            conn = OracleDsSingleton.getInstance().getConnection();
	            String sql = "UPDATE USERS SET SCORE = ? WHERE username = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setDouble(1, currentQ.getScore());
	            pstmt.setString(2, string);
                rs = pstmt.executeQuery();
	        } catch (SQLException e1) {
				e1.printStackTrace();
			} 
		}

}
