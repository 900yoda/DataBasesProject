package src;



//the file is modified
import java.sql.*;
import java.util.*;
import java.text.*;


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
	public void DoubleError(){
		System.out.println("\nPlease enter a double\n");
	}
}

class Page{
	protected static Scanner input;
	protected static PageErrors errors;
	protected static String user;
	protected static DateFormat format; 
	
	public Page(){
		input = new Scanner(System.in);
		errors = new PageErrors();
		format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
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
			input.nextLine();
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
		String username = "";
		String password;
		
		int choice = 1;
		try{
			Connector conn = new Connector();
			ResultSet rs = null;
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
			}while(!rs.next() || !rs.getString(1).equals(password));
			
			rs.close();
	   		conn.closeConnection();
		}

		catch(Exception e){
			e.printStackTrace();
		}
		
		user = username;
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
		System.out.print("Address (Format- [house number] [street name] [City name],[State full name] [zip code]):   ");
		address = input.nextLine();
		System.out.print("Phone Number:   ");
		phoneNumber = input.nextLine();
		
		
		try{
			Connector conn = new Connector();
			ResultSet rs = null;
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
			
			rs.close();
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
		System.out.println("\n1. Reserve");
		System.out.println("2. TH Information");
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
			input.nextLine();
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
		System.out.println("\n1. Add New Home");
		System.out.println("2. Update Home");
		System.out.println("3. Go back");
		System.out.println("4. Quit");
		System.out.print("Please enter corrisponding number:  ");
		
		int choice = 0;
		try{
			choice = input.nextInt();
		}
		
		catch(Exception e){
			errors.IntegerError();
			input.nextLine();
			return;
		}
		
		SwitchPage(choice, display);
	}
	
	private void SwitchPage(int choice, Display display){
		switch(choice){
		case 1:
			display.page = new AddNewHomePage();
			break;
		case 2:
			display.page = new UpdateHomePage();
			break;
		case 3:
			display.page = new MainPage();
			break;
		case 4: 
			display.go = false;
			break;
		default:
			System.out.println("Sorry that is not an option");
		}
	}
}

class AddNewHomePage extends Page{
	public void Action(Display display){
		int houseID = 0;
		String name;
		String address;
		String phoneNumber;
		int yearBuilt = 0;
		String category;
		double price = 0;
		System.out.print("Enter house name:  ");
		name = input.nextLine();
		System.out.print("Enter house Address (Format- [house number] [street name] [City name],[State full name] [zip code]):   ");
		address = input.nextLine();
		System.out.print("Enter phone number: ");
		phoneNumber = input.nextLine();
		
		boolean added  = false;
		while(!added){
			try{
				System.out.print("Enter year built:  ");
				yearBuilt = input.nextInt();
				input.nextLine();
				added = true;
			}
			catch(Exception e){
				errors.IntegerError();
				input.nextLine();
			}
		}
		
		System.out.print("Enter a house category: ");
		category = input.nextLine();
		
		added  = false;
		while(!added){
			try{
				System.out.print("Enter price per day: ");
				price = input.nextDouble();
				input.nextLine();
				added = true;
			}
			catch(Exception e){
				errors.DoubleError();
				input.nextLine();
			}
		}
		
		try{
			Connector conn = new Connector();
			ResultSet rs = conn.stmt.executeQuery("Select count(*) from Temporary_Housing;");
			rs.next();
			houseID = rs.getInt(1);
			rs.close();
			String sql = "INSERT INTO Temporary_Housing VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.con.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setInt(1,houseID);
			pstmt.setString(2,name);
			pstmt.setString(3,address);
			pstmt.setString(4,"");
			pstmt.setString(5,phoneNumber);
			pstmt.setInt(6,yearBuilt);
			pstmt.setString(7,category);
			pstmt.setDouble(8,price);

			pstmt.executeUpdate();
			
			sql = "INSERT INTO Home_Ownership VALUES(?,?)";
			pstmt = conn.con.prepareStatement(sql);
			pstmt.clearParameters();
			pstmt.setInt(1,houseID);
			pstmt.setString(2,user);
			pstmt.executeUpdate();
			conn.closeConnection();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Home added, please use the update home option to add dates and keywords");
		display.page = new NewTHPage();
		
	}
	
}

class UpdateHomePage extends Page{
	public void Action(Display display){
		//Set<Integer> ownedHomes = new HashSet<Integer>();
		int[] ownedHomes = null;
		try{
			Connector conn = new Connector();
			ResultSet rs=conn.stmt.executeQuery("select th.house_id, th.name, th.address from Temporary_Housing th, Home_Ownership ho where " +
					"ho.username = '"+ user + "' and ho.house_id = th.house_id");
			 ResultSetMetaData rsmd = rs.getMetaData();
	  		 int numCols = rsmd.getColumnCount();
	  		 ownedHomes = new int[numCols];
	  		 System.out.println();
	  		 int index = 0;
	  		 while (rs.next())
	  		 {
	  			 //System.out.print("cname:"); 
	  			 ownedHomes[index++] = rs.getInt(1);
	  			System.out.print(index+ ": ");
	  			 for (int i=2; i<=numCols;i++)
	  				 System.out.print(rs.getString(i)+"  ");
	  			 System.out.println("");
	  		 }
	  		 
	  		rs.close();
			conn.closeConnection();
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
		int choice = -2;
		do{
			try{
				System.out.print("Please enter number coorisponding to one of your homes id (enter -1 to cancel):  ");
				choice = input.nextInt();
				input.nextLine();
				if(choice == -1)
					break;
			}
			
			catch(Exception e){
				errors.IntegerError();
				input.nextLine();
			}
		}while(choice < 1 || choice > ownedHomes.length);
		
		if(choice == -1){
			display.page = new NewTHPage();
			return;
		}
		
		int home = ownedHomes[choice-1];
		boolean go = true;
		while(go){		
			while(true){
				try{
					System.out.println("\n1. Add Available Dates");
					System.out.println("2. Remove Available Dates");
					System.out.println("3. View Available Dates");
					System.out.println("4. Add KeyWords");
					System.out.println("5. Remove KeyWords");
					System.out.println("6. View Keywords");
					System.out.println("7. View users that have reserved your TH");
					System.out.println("8. Go Back");
					System.out.println("9. Quit");
					System.out.print("Please enter corrisponding number:  ");
					choice = input.nextInt();
					input.nextLine();
					if(choice >= 1 && choice <= 9)
						break;
				}
				catch(Exception e){
					errors.IntegerError();
					input.nextLine();
				}
			}
		

			switch(choice){
			case 1:
				ShowDates(home);
				System.out.println("\nPlease enter in your to date a hyphen and your from date,"); 
				System.out.println("with a space in between each new date interval");
				System.out.print("Example format: mm/dd/yyyy-mm/dd/yyy  mm/dd/yyyy-mm/dd/yyyy ...:  ");
				
				try{
					String[] dates = input.nextLine().split("[ -]+");
					String sql = "select d.from_date, d.until_date"
							+ " from (select from_date, until_date"
							+ 		" from Dates_Available"
							+ 		" where house_id = ?) as d"
							+ " where (STR_TO_DATE(?,'%m/%d/%Y') >= d.from_date and STR_TO_DATE(?,'%m/%d/%Y') <= d.until_date) "
							+ " or (STR_TO_DATE(?,'%m/%d/%Y') >= d.from_date and STR_TO_DATE(?,'%m/%d/%Y') <= d.until_date) "
							+ " or (STR_TO_DATE(?,'%m/%d/%Y') <= d.from_date and STR_TO_DATE(?,'%/%d/%Y') >= d.until_date);";
					
					String sql2 = "insert into Dates_Available values(?,STR_TO_DATE(?,'%m/%d/%Y'),STR_TO_DATE(?,'%m/%d/%Y'));";
					
					Connector conn = new Connector();
					PreparedStatement pstmt = conn.con.prepareStatement(sql);
					PreparedStatement pstmt2 = conn.con.prepareStatement(sql2);
					for(int i = 0; i < dates.length; i+=2){
						java.util.Date date1 = format.parse(dates[i]);
						java.util.Date date2 = format.parse(dates[i+1]);
						
						if(date1.after(date2))
							System.out.println(dates[i]+ " is after "+dates[i+1]+" so it will be ignored");
						else{
							pstmt.clearParameters();
							pstmt.setInt(1, home);
							pstmt.setString(2, dates[i]);
							pstmt.setString(3, dates[i]);
							pstmt.setString(4, dates[i+1]);
							pstmt.setString(5, dates[i+1]);
							pstmt.setString(6, dates[i]);
							pstmt.setString(7, dates[i+1]);
							ResultSet rs = pstmt.executeQuery();
							if(rs.next())
								System.out.println(dates[i]+"-"+dates[i+1]+" interferes with "+format.format(rs.getDate(1))+
										"-"+format.format(rs.getDate(2))+ " so it was not added");
							else{
								pstmt2.clearParameters();
								pstmt2.setInt(1,home);
								pstmt2.setString(2,dates[i]);
								pstmt2.setString(3,dates[i+1]);
								pstmt2.executeUpdate();
								System.out.println(dates[i] + "-"+ dates[i+1]+ " added");
							}
							rs.close();
							
						}
					}
					conn.closeConnection();
				}
				catch(ParseException e){
					System.out.println("Date entered incorrectly");
				}
				catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 2:
				ShowDates(home);
				System.out.println("\nPlease enter in your to date a hyphen and your from date,"); 
				System.out.println("with a space in between each date interval to be deleted");
				System.out.print("Example format: mm/dd/yyyy-mm/dd/yyy  mm/dd/yyyy-mm/dd/yyyy ...:  ");
				try{
					Connector conn = new Connector();
					String[] dates = input.nextLine().split("[ -]+");
					String sql = "delete from Dates_Available where Dates_Available.house_id = ? and"
							+ " Dates_Available.from_date = STR_TO_DATE(?,'%m/%d/%Y') and Dates_Available.until_date = STR_TO_DATE(?,'%m/%d/%Y');";
					PreparedStatement pstmt = conn.con.prepareStatement(sql);
					
					String sql2 = "select * from Dates_Available where Dates_Available.house_id = ? and"
							+ " Dates_Available.from_date = STR_TO_DATE(?,'%m/%d/%Y') and Dates_Available.until_date = STR_TO_DATE(?,'%m/%d/%Y');";
					PreparedStatement pstmt2 = conn.con.prepareStatement(sql2);
					
					for(int i = 0; i < dates.length; i+=2){
						String date1 = dates[i];
						String date2 = dates[i+1];
						pstmt2.clearParameters();
						pstmt2.setInt(1, home);
						pstmt2.setString(2, date1);
						pstmt2.setString(3, date2);
						ResultSet rs = pstmt2.executeQuery();
						if(rs.next()){
							pstmt.clearParameters();
							pstmt.setInt(1,home);
							pstmt.setString(2,date1);
							pstmt.setString(3, date2);
							pstmt.executeUpdate();
							System.out.println(date1 + "-" + date2 + " removed");
						}
							
						else{
							System.out.println(date1 + "-" + date2 + " does not exist so it was ignored");
						}
						rs.close();
					}
					
					conn.closeConnection();


				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				break;
			case 3:
				ShowDates(home);
				break;
			case 4:

				
				try{
					Connector conn = new Connector();
					Set<String> wordSet = GetKeyWords(home);
					System.out.print("\nPlease enter in new home keywords with a space between each new keyword:  ");
					String[] words = input.nextLine().split("[ ]+");
					String sql = "INSERT INTO Keywords VALUES(?,?)";
					PreparedStatement pstmt = conn.con.prepareStatement(sql);

					for(int i = 0; i < words.length; i++){
						String name = words[i];
						if(wordSet.contains(name))
							System.out.println(name + " already exists so it was ignored");
						else{
							pstmt.clearParameters();
							pstmt.setInt(1,home);
							pstmt.setString(2,name);
							pstmt.executeUpdate();
							System.out.println(name + " added");
						}

					}
					
					conn.closeConnection();


				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				break;
			case 5:
				try{
					Connector conn = new Connector();
					Set<String> wordSet = GetKeyWords(home);
					System.out.print("\nPlease enter in keywords to delete with a space between each new keyword:  ");
					String[] words = input.nextLine().split("[ ]+");
					String sql = "delete from Keywords where Keywords.house_id = ? and Keywords.keyword = ?;";
					PreparedStatement pstmt = conn.con.prepareStatement(sql);

					for(int i = 0; i < words.length; i++){
						String name = words[i];
						if(wordSet.contains(name)){
							pstmt.clearParameters();
							pstmt.setInt(1,home);
							pstmt.setString(2,name);
							pstmt.executeUpdate();
							System.out.println(name + " removed");
						}
							
						else{
							System.out.println(name + " does not exist so it was ignored");
						}

					}
					
					conn.closeConnection();


				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				break;
			case 6:
				GetKeyWords(home);
				break;
			case 7:
				try{
					Connector conn = new Connector();
					String sql = "select username, DATE_FORMAT(from_date,'%m/%d/%Y'),date_format(until_date,'%m/%d/%Y'), cost, party_count"
							+ " from Reservations"
							+ " where house_id = ?";
					PreparedStatement pstmt = conn.con.prepareStatement(sql);
					pstmt.clearParameters();
					pstmt.setInt(1, home);
					ResultSet rs = pstmt.executeQuery();
					System.out.println("\n---Current Reservations---");
					while(rs.next()){
						System.out.print("username:"+ rs.getString(1));
						System.out.print(" from:"+ rs.getString(2));
						System.out.print(" until:"+ rs.getString(3));
						System.out.print(" cost:"+ rs.getString(4));
						System.out.println(" party count:"+ rs.getString(5));
						
					}
					rs.close();
					conn.closeConnection();
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 8:
				display.page = new NewTHPage();
				go = false;
				break;
			case 9:
				go = false;
				display.go = false;
			}
		}
		
		
		

	}
	
	private Set<String> GetKeyWords(int home){
		Set<String> words = new HashSet<String>();
		try{
			Connector conn = new Connector();
			System.out.println("\n---Current Key Words---");
			ResultSet rs=conn.stmt.executeQuery("Select keyword from Keywords where Keywords.house_id = "+ Integer.toString(home)+";");
			while(rs.next()){
				String word = rs.getString(1);
				words.add(word);
				System.out.println(word);
			}
			conn.closeConnection();
			rs.close();
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
		return words;
	}
	
	private void ShowDates(int home){
		
		try{
			String sql = "select d.from_date, d.until_date"
					+ " from (select from_date, until_date"
					+ 		" from Dates_Available"
					+ 		" where house_id = ?) as d";
			Connector conn = new Connector();
			PreparedStatement pstmt = conn.con.prepareStatement(sql);
			pstmt.setInt(1, home);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("\n---Current Dates---");
			while(rs.next()){
				System.out.println(format.format(rs.getDate(1)) + "-" + format.format(rs.getDate(2)));
			}
			conn.closeConnection();
			rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

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
