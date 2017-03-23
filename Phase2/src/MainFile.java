import java.util.Scanner;
import java.sql.*;

class Display{
	Page page;
	boolean go;
	public Display(){
		page = new WelcomePage();
		go = true;
	}
	
	public void ShowPage(){
		page.Action(this);
	}
}

class PageErrors{
	public void IntegerError(){
		System.out.println("\nPlease enter an integer\n");
	}
}

class Page{
	protected static Scanner input;
	protected static PageErrors errors;
	protected static String user;
	protected static Connector conn;
	
	public Page(){
		input = new Scanner(System.in);
		errors = new PageErrors();
	}
	
	public void Action(Display display){
		
	}
	
	public void SetUser(String name){
		user = name;
	}
}

class WelcomePage extends Page{

	public void Action(Display display){
		System.out.println("-----Welcome-----");
		System.out.println("Please login or create a new account");
		System.out.println("1. Login");
		System.out.println("2. Create New Accout");
		System.out.println("3. Quit");
		System.out.print("Please enter corrisponding number:  ");
		int choice = 0;
		try{
			choice = input.nextInt();
		}
		
		catch(Exception e){
			errors.IntegerError();
			return;
		}
		
		SwitchPage(choice, display);
	}
	
	private void SwitchPage(int choice, Display display){
		switch(choice){
		case 1:
			display.page = new LoginPage();
			break;
		case 2:
			display.page = new CreateNewAccountPage();
			break;
		case 3:
			display.go = false;
			break;
		default:
			System.out.println("\nsorry, but that is not an option\n");
		}
	}
}

class LoginPage extends Page{
	public void Action(Display display){
		String username;
		String password;
		ResultSet rs;
		int choice = 1;
		try{
			conn = new Connector();
			boolean message = false;
			do{
				if(message){
					System.out.println("Username or password is invalid, do you want to exit?");
					System.out.println("1: yes");
					System.out.println("anything else: no");
					String answer = input.nextLine();
					if(answer.equals("1")){
						choice = 0;
						break;
					}
						
					
				}
				System.out.print("Username:  ");
				username = input.nextLine();
				System.out.print("Password:  ");
				password = input.nextLine();
				
				rs=conn.stmt.executeQuery("Select Users.password from Users where Users.username = '"+username+"';");
				message = true;
			}while(!rs.next() || !rs.getString(0).equals(password));

	   		conn.closeConnection();
		}

		catch(Exception e){
			
		}
		
		SwitchPage(choice, display);
	}
	
	private void SwitchPage(int choice, Display display){
		switch(choice){
		case 0:
			display.page = new WelcomePage();
			break;
		case 1:
			display.page = new MainPage();
			break;
		default:
			System.out.println("\nsorry, but that is not an option\n");
		}
	}
}

class CreateNewAccountPage extends Page{
	public void Action(Display display){
		String firstName;
		String lastName;
		String address;
		String phoneNumber;
		String userName = "";
		String password;
		String passwordVerify;
		
		System.out.println("---Please enter corisponding information---");
		System.out.print("First Name:   ");
		firstName = input.nextLine();
		System.out.print("Last Name:   ");
		lastName = input.nextLine();
		System.out.print("Address (Format- [house number] [street name] [City name],[State name] [zip code]):   ");
		address = input.nextLine();
		System.out.print("Phone Number:   ");
		phoneNumber = input.nextLine();
		
		ResultSet rs;
		try{
			conn = new Connector();
			boolean message = false;
			do{
				if(message){
					System.out.println("username is already taken please enter a different one");
				}
				System.out.print("username:  ");
				userName = input.nextLine();
				
       		 	rs=conn.stmt.executeQuery("Select * from Users where Users.username = '"+userName+"';");
				message = true;
			}while(userName.equals("") || rs.next());

			message = false;
			do{
				if(message){
					System.out.println("please make sure passwords are the same");

				}
				System.out.print("password:  ");
				password = input.nextLine();
				System.out.print("password verify:  ");
				passwordVerify = input.nextLine();
			}while(!password.equals(passwordVerify));
			
			String sql = "INSERT INTO Users VALUES(?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.con.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setString(1,userName);
			
			pstmt.setString(2,firstName);
			pstmt.setString(3,lastName);
			pstmt.setString(4,password);
			pstmt.setString(5,address);
			pstmt.setString(6,phoneNumber);

			pstmt.executeUpdate();
			conn.closeConnection();
		} 

			
		catch(Exception e){
			e.printStackTrace();

		}
		SetUser(userName);
		display.page = new MainPage();
	}
}

class MainPage extends Page{
	public void Action(Display display){
		System.out.println("1. Reserve");
		System.out.println("2. New TH");
		System.out.println("3. Stays");
		System.out.println("4. Favorite TH");
		System.out.println("5. Feedback");
		System.out.println("6. Usefullness recordings");
		System.out.println("7. Trust recordings");
		System.out.println("8. TH Browsing: ");
		System.out.println("9. Useful Feedbacks");
		System.out.println("10. TH Suggestions");
		System.out.println("11. Two Degrees of Separations");
		System.out.println("12. Statistics");
		System.out.println("13. User Awards");
		System.out.println("14. Log Out");
		System.out.println("15. Quit");
		System.out.print("Please enter corrisponding number:  ");
		
		int choice = 0;
		
		try{
			choice = input.nextInt();
		}
		
		catch(Exception e){
			errors.IntegerError();
			return;
		}
		
		SwitchPage(choice, display);
	}
	
	private void SwitchPage(int choice, Display display){
		switch(choice){
		case(1):
			display.page = new ReservePage();
			break;
		case(2):
			display.page = new NewTHPage();
			break;
		case(3):
			display.page = new StaysPage();
			break;
		case(4):
			display.page = new FavoriteTHPage();
			break;
		case(5):
			display.page = new FeedbackPage();
			break;
		case(6):
			display.page = new UseFulnessPage();
			break;
		case(7):
			display.page = new TrustPage();
			break;
		case(8):
			display.page = new THBrowsingPage();
			break;
		case(9):
			display.page = new UseFulFeedBackPage();
			break;
		case(10):
			display.page = new THSuggestionPage();
			break;
		case(11):
			display.page = new TwoDegreesPage();
			break;
		case(12):
			display.page = new StatisticsPage();
			break;
		case(13):
			display.page = new UserAwardsPage();
			break;
		case(14):
			display.page = new WelcomePage();
			break;
		case(15):
			display.go = false;
			break;
		default:
			System.out.println("sorry that is not an option");
		}
	}
}

class ReservePage extends Page{
	public void Action(Display display){
		
	}
}

class NewTHPage extends Page{
	public void Action(Display display){
		
	}
}

class StaysPage extends Page{
	public void Action(Display display){
		
	}
}

class FavoriteTHPage extends Page{
	public void Action(Display display){
		
	}
}

class FeedbackPage extends Page{
	public void Action(Display display){
		
	}
}

class UseFulnessPage extends Page{
	public void Action(Display display){
		
	}
}

class TrustPage extends Page{
	public void Action(Display display){
		
	}
}

class THBrowsingPage extends Page{
	public void Action(Display display){
		
	}
}

class UseFulFeedBackPage extends Page{
	public void Action(Display display){
		
	}
}

class THSuggestionPage extends Page{
	public void Action(Display display){
		
	}
}

class TwoDegreesPage extends Page{
	public void Action(Display display){
		
	}
}

class StatisticsPage extends Page{
	public void Action(Display display){
		
	}
}

class UserAwardsPage extends Page{
	public void Action(Display display){
		
	}
}
public class MainFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display display = new Display();
		while(display.go){
			display.ShowPage();
		}
	}

}