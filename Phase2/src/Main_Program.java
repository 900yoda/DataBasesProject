import java.util.Scanner;;

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
		default:
			System.out.println("\nsorry, but that is not an option\n");
		}
	}
}

class LoginPage extends Page{
	public void Action(Display display){
		String username;
		String password;
		
		System.out.println("Username:  ");
		username = input.nextLine();
		System.out.println("Password:  ");
		password = input.nextLine();
	}
}

class CreateNewAccountPage extends Page{
	public void Action(Display display){
		String firstName;
		String lastName;
		String address;
		String phoneNumber;
		String userName;
		String password;
		String passwordVerify;
		
		System.out.println("---Please enter corisponding information---");
		System.out.print("First Name:   ");
		firstName = input.nextLine();
		System.out.print("Last Name:   ");
		lastName = input.nextLine();
		System.out.print("Address:   ");
		address = input.nextLine();
		System.out.print("Phone Number:   ");
		phoneNumber = input.nextLine();
		
		System.out.print("username:  ");
		userName = input.nextLine();
		System.out.print("password:  ");
		password = input.nextLine();
		System.out.print("password verify:  ");
		passwordVerify = input.nextLine();
		
		
	}
	
	private void SwitchPage(int choice, Display display){
		switch(choice){
		case 1:
			display.page = new LoginPage();
			break;
		case 2:
			display.page = new CreateNewAccountPage();
			break;
		default:
			System.out.println("\nsorry, but that is not an option\n");
		}
	}
}

public class Main_Program {

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
