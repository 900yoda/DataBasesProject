package src;



//the file is modified
import java.sql.*;
import java.util.*;
import java.text.*;
import java.lang.*;


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
                System.out.println("Notes: enter state/city full names with no spaces (ex:newyorkcity)");
                System.out.print("Address ():   ");
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
                System.out.println("Notes: enter state/city full names with no spaces (ex:newyorkcity)");
                System.out.print("Enter house Address ");
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
                category = input.nextLine().toLowerCase();

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
                ArrayList<Integer> ownedHomes = new ArrayList<Integer>();
                try{
                        Connector conn = new Connector();
                        ResultSet rs=conn.stmt.executeQuery("select th.house_id, th.name, th.address from Temporary_Housing th, Home_Ownership ho where " +
                                        "ho.username = '"+ user + "' and ho.house_id = th.house_id");
                         ResultSetMetaData rsmd = rs.getMetaData();


                         System.out.println();
                         int index = 0;
                         while (rs.next())
                         {
                                 //System.out.print("cname:");
                                 ownedHomes.add(rs.getInt(1));
                                System.out.print(++index+ ": ");
                                 for (int i=2; i<=3;i++)
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
                }while(choice < 1 || choice > ownedHomes.size());

                if(choice == -1){
                        display.page = new NewTHPage();
                        return;
                }

                int home = ownedHomes.get(choice-1);
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
                                                        +               " from Dates_Available"
                                                        +               " where house_id = ?) as d"
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
                        ResultSet rs=conn.stmt.executeQuery("select keyword from Keywords where Keywords.house_id = "+ Integer.toString(home)+";");
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
                                        +               " from Dates_Available"
                                        +               " where house_id = ?) as d";
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
                boolean go = true;
                int choice = -1;
                while(go){
                        do{
                                try{
                                        System.out.println("\n1. Add Favorite TH");
                                        System.out.println("2. Remove Favorite TH");
                                        System.out.println("3. View Favorite TH");
                                        System.out.println("4. Go Back");
                                        System.out.println("5. Quit");
                                        System.out.print("Please select a coorisponding number:  ");
                                        choice = input.nextInt();
                                        input.nextLine();
                                }
                                catch(Exception e){
                                        errors.IntegerError();
                                        input.nextLine();
                                }
                        }while(choice < 1 || choice > 5);

                        switch(choice){
                        case 1:
                                try{
                                        Set<Integer> houses = ViewFavoritesSet();
                                        ArrayList<Integer> visited = GiveHouses();

                                        int favorite = -1;
                                        while(true){
                                                System.out.println("Add a favorite coorisponding with a number (-1 to cancel):  ");
                                                favorite = input.nextInt();
                                                if((favorite >= 1 && favorite <= visited.size()) || favorite == -1)
                                                        break;
                                        }

                                        if(favorite == -1){

                                        }

                                        if(houses.contains(visited.get(favorite-1)))
                                                System.out.println("That is already a favorite");
                                        else{
                                                Connector conn = new Connector();
                                                String sql = "insert into Favorite values(?,?);";
                                                PreparedStatement pstmt = conn.con.prepareStatement(sql);
                                                pstmt.setString(1, user);
                                                pstmt.setInt(2, visited.get(favorite-1));
                                                pstmt.executeUpdate();
                                                conn.closeConnection();
                                        }

                                }

                                catch(IllegalArgumentException e){
                                        errors.IntegerError();
                                        input.nextLine();
                                }
                                catch(Exception e){
                                        e.printStackTrace();
                                }
                                break;
                        case 2:
                                try{
                                        ArrayList<Integer> houses = ViewFavorites();

                                        int favorite = -1;
                                        while(true){
                                                System.out.println("Remove a favorite coorisponding with a number (-1 to cancel):  ");
                                                favorite = input.nextInt();
                                                if((favorite >= 1 && favorite <= houses.size()) || favorite == -1)
                                                        break;
                                        }

                                        if(favorite == -1){

                                        }

                                        else{
                                                Connector conn = new Connector();
                                                String sql = "delete from Favorite where username = ? and house_id = ?";
                                                PreparedStatement pstmt = conn.con.prepareStatement(sql);
                                                pstmt.setString(1, user);
                                                pstmt.setInt(2, houses.get(favorite-1));
                                                pstmt.executeUpdate();
                                                conn.closeConnection();
                                        }

                                }

                                catch(IllegalArgumentException e){
                                        errors.IntegerError();
                                        input.nextLine();
                                }
                                catch(Exception e){
                                        e.printStackTrace();
                                }
                                break;
                        case 3:
                                ViewFavorites();
                                break;
                        case 4:
                                display.page = new MainPage();
                                go = false;
                                break;
                        case 5:
                                display.go = false;
                                go = false;
                                break;
                        }
                }
        }

        private ArrayList<Integer> GiveHouses(){
                ArrayList <Integer> houses = new ArrayList<Integer>();
                try{
                        Connector conn = new Connector();
                        String sql = "select t.house_id, t.name, t.address "
                                        + "from Temporary_Housing t, (select distinct(r.house_id) as house_id "
                                        +                                                       "from Reservations r "
                                        +                                                       "where username = ?) as rh "
                                        + "where rh.house_id = t.house_id";
                        PreparedStatement pstmt = conn.con.prepareStatement(sql);
                        pstmt.setString(1, user);
                        ResultSet rs =  pstmt.executeQuery();
                        System.out.println("\n---Houses Reserved/Visited---");
                        int number = 1;
                        while(rs.next()){
                                houses.add(rs.getInt(1));
                                System.out.println(number + ": " + rs.getString(2) + " " + rs.getString(3));
                                number++;
                        }
                        rs.close();
                        conn.closeConnection();
                }
                catch(Exception e){
                        e.printStackTrace();
                }
                return houses;
        }

        private ArrayList<Integer> ViewFavorites(){
                ArrayList<Integer> houses = new ArrayList<Integer>();
                try{
                        Connector conn = new Connector();
                        String sql = "select t.house_id, t.name, t.address "
                                        + "from Temporary_Housing t, (select distinct(f.house_id) as house "
                                        +                            "from Favorite f "
                                        +                                                       "where f.username = ?) as fh "
                                        + "where fh.house = t.house_id;";
                        PreparedStatement pstmt = conn.con.prepareStatement(sql);
                        pstmt.setString(1, user);
                        ResultSet rs =  pstmt.executeQuery();
                        System.out.println("\n---Favorite Houses---");
                        int number = 1;
                        while(rs.next()){
                                houses.add(rs.getInt(1));
                                System.out.println(number+ ": " + rs.getString(2) + " " + rs.getString(3));
                                number++;
                        }
                        rs.close();
                        conn.closeConnection();
                }
                catch(Exception e){
                        e.printStackTrace();
                }
                return houses;
        }

        private Set<Integer> ViewFavoritesSet(){
                Set<Integer> houses = new HashSet<Integer>();
                try{
                        Connector conn = new Connector();
                        String sql = "select t.house_id, t.name, t.address "
                                        + "from Temporary_Housing t, (select distinct(f.house_id) as house "
                                        +                            "from Favorite f "
                                        +                                                       "where f.username = ?) as fh "
                                        + "where fh.house = t.house_id;";
                        PreparedStatement pstmt = conn.con.prepareStatement(sql);
                        pstmt.setString(1, user);
                        ResultSet rs =  pstmt.executeQuery();
                        System.out.println("\n---Favorite Houses---");
                        int number = 1;
                        while(rs.next()){
                                houses.add(rs.getInt(1));
                                System.out.println(number+ ": " + rs.getString(2) + " " + rs.getString(3));
                                number++;
                        }
                        rs.close();
                        conn.closeConnection();
                }
                catch(Exception e){
                        e.printStackTrace();
                }
                return houses;
        }
}

class FeedbackPage extends Page{
        public void Action(Display display){

        }
}

class UseFulnessPage extends Page{
        public void Action(Display display){
        	ArrayList<Integer> feedBacks = new ArrayList<Integer>();
        	String sql = "select opinion_id, house_id, score, thoughts from Feedback where username <> '"+user+"'";
        	try{
            	Connector conn = new Connector();
            	ResultSet rs = conn.stmt.executeQuery(sql);
            	int  number = 1;
            	System.out.println();
            	while(rs.next()){
        			feedBacks.add(rs.getInt(1));
        			System.out.print(number+": house_id:" + rs.getString(2)+ " ");
        			System.out.print("score:" + rs.getString(3)+ " ");
        			System.out.print("thoughts:" + rs.getString(4)+ " ");
            		System.out.println();
            	}
            	int choice = -2;
            	while(true){
            		System.out.print("Enter in coorisponding number for feedback (-1 to cancel):  ");
            		choice = input.nextInt();
            		if(choice == -1 || (choice >= 1 && choice <= feedBacks.size()))
            			break;
            	}
            	
            	if(choice == -1){
            		display.page = new MainPage();
            		return;
            	}
            	
            	int id = feedBacks.get(choice-1);
            	sql = "select opinion_id from Usefulness where rater = ? and opinion_id = ?";
            	PreparedStatement pstmt = conn.con.prepareStatement(sql);
            	
            	pstmt.clearParameters();
            	pstmt.setString(1, user);
            	pstmt.setInt(2, id);
            	
            	rs = pstmt.executeQuery();
            	if(rs.next()){
            		System.out.println("\nYou have already added usefullness to that feedback");
            	}
            	
            	else{
            		while(true){
            			System.out.print("Enter in usefullness for feedback 1-10 (-1 to cancel):  ");
                		choice = input.nextInt();
                		if(choice == -1 || (choice >= 1 && choice <= 10))
                			break;
            		}
            		
                	if(choice == -1){
                		display.page = new MainPage();
                		return;
                	}
            		
                	sql  = "insert into Usefulness values(?,?,?);";
                	pstmt = conn.con.prepareStatement(sql);
                	pstmt.clearParameters();
                	pstmt.setInt(1, id);
                	pstmt.setString(2, user);
                	pstmt.setInt(3, choice);
                	pstmt.executeUpdate();
            	}
            	rs.close();
            	conn.closeConnection();
        	}
            catch(IllegalArgumentException e){
                errors.IntegerError();
                input.nextLine();
            }
        	catch(Exception e){
        		e.printStackTrace();
        	}
        }
}

class TrustPage extends Page{
        public void Action(Display display){

        }
}

class THBrowsingPage extends Page{
        public void Action(Display display){
        	System.out.println("Please enter query below");
        	System.out.print("Useable words: price, state, city, keyword, category, ");
        	System.out.println("sort by, feedback-score, trusted-feedback-score, and, or");
        	System.out.println("Useable symbols: <, >, =");
        	System.out.println("Notes: enter state/city full names with no spaces (ex:newyorkcity)");
        	System.out.println("Note: put variable names then value (ex: state = Texas)");
        	String[] whites = input.nextLine().split("((?<=<)|(?=<)|(?<=>)|(?=>)|(?<==)|(?==)|(?<= )|(?= ))");
        	ArrayList<String> commands = new ArrayList<String>();
        	for(int i = 0; i < whites.length; i++){
        		if(!whites[i].equals(" ")){
        			commands.add(whites[i]);
        		}
        			
        	}
        	
        	String select = "select th.house_id, th.name, th.address, th.phone_number,"
        			+ " th.year_built, th.category, th.price";
        	String from	 = " from Home_Ownership ho, ";
        	String th = "Temporary_Housing th";
        	String where = " where (ho.house_id = th.house_id and ho.username <> '"+user+"')";
        	String more = "";
			String groupBy = " group by th.house_id";
        	
        	
        	
        	boolean moreAdded = false;
        	int keywordsAdded = 0;
        	
        	for(int i = 0; i < commands.size(); i++){
        		String command = commands.get(i).toLowerCase();
        		if(command.equals("<") || command.equals(">") || command.equals("=") || 
        				command.equals("and") || command.equals("or")){
        			more += " "+command;
        		}
        		
        		else if(command.equals("price")){
        			if(moreAdded)
        				more+= " th.price";
        			else{
        				moreAdded = true;
        				more = " and (th.price";
        			}
        		}
        		
        		else if(command.equals("keyword")){
        			String word = " k"+ Integer.toString(keywordsAdded++);
        			from += " Keywords"+word+",";
        			where += "and ("+word+".house_id = th.house_id)";
        			if(moreAdded)
        				more+= word+".keyword";
        			else{
        				moreAdded = true;
        				more = " and ("+ word+".keyword";
        			}
        		}
        		else if (command.equals("category")){
        			if(moreAdded)
        				more+= " th.category";
        			else{
        				moreAdded = true;
        				more = " and (th.category";
        			}
        		}
        		else if(command.equals("state")||command.equals("city")){
        			if(moreAdded)
        				more+= " LOWER(th.address) LIKE '%" +commands.get(i+2).toLowerCase()+"%'";
        			else{
        				moreAdded = true;
        				more = " and (LOWER(th.address) LIKE '%" +commands.get(i+2).toLowerCase()+"%'";
        			}
        			i+=2;
        		}
        		
        		else if(command.equals("sort")){
        			String word = commands.get(i+2).toLowerCase();
        			i+=2;
        			if(word.equals("price")){
        				groupBy += " order by th.price DESC";
        			}
        			if(word.equals("feedback-score")){
        				
        			}
        		}
        		
        		else{
        			if(moreAdded)
        				more+= " '" + command + "'";
        			else{
        				moreAdded = true;
        				more = " and ('" + command + "'";
        			}
        		}
        	}
        	
        	
        	
        	try{
        		Connector conn = new Connector();
        			
        		if(moreAdded)
        			more += ")";
        		ResultSet rs=conn.stmt.executeQuery(select+from+th+where+more+groupBy);
        		int col = rs.getMetaData().getColumnCount();
        		while(rs.next()){
        			for(int i = 1; i <= col; i++)
        				System.out.print(rs.getString(i)+ " ");
        			System.out.println();
        		}
        		rs.close();
        		conn.closeConnection();
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	
        	display.page = new MainPage();
        }
}

class UseFulFeedBackPage extends Page{
        public void Action(Display display){

        }
}

class THSuggestionPage extends Page{
        public void Action(Display display){
        	try{
        		Connector conn = new Connector();
        		conn.stmt.executeUpdate("create temporary table my " 
        				+ "select distinct(m.house_id) as house_id "
        				+ "from Reservations m "
        				+ "where m.username = '"+user+"'; ");
					
        		conn.stmt.executeUpdate("create temporary table other "
        		+ "select distinct(o.username) as username "
        		+ "from Reservations o, my "
        		+ "where o.house_id = my.house_id and o.username <> '"+user+"'; ");
					
        		String sql =  "select r.house_id, count(*) "
        		+ "from other o, Reservations r "
        		+ "where r.username = o.username "
				+ "group by r.house_id "
				+ "having r.house_id not in (select house_id from my) and "
				+ "r.house_id not in (select th.house_id from Home_Ownership th where username = '"+user+"'); ";
					
        		ResultSet rs=conn.stmt.executeQuery(sql);
        		System.out.println("\n---Suggestions---");
        		while(rs.next()){
        			System.out.println("house_id:"+ rs.getString(1)+" visits:"+rs.getString(2));
        		}
        		conn.stmt.executeUpdate("drop temporary table my; ");
        		conn.stmt.executeUpdate("drop temporary table other; ");
        		display.page = new MainPage();
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        }
}

class TwoDegreesPage extends Page{
        public void Action(Display display){

        }
}

class StatisticsPage extends Page{
        public void Action(Display display){
        	try{
        		Connector conn = new Connector();
            	String sql = "select distinct(category) from Temporary_Housing";
            	ResultSet rs= conn.stmt.executeQuery(sql);
            	ArrayList<String> cat = new ArrayList<String>();
            	while(rs.next()){
            		cat.add(rs.getString(1));	
            	}
            	
            	int choice = -2;
            	while(true){
                	System.out.println("\n1: Most Popular Th");
                	System.out.println("2: Most Expensive Th");
                	System.out.println("3: Highest Rated Th");
                	System.out.print("Choose option corrisponding with a number (-1 to cancel): ");
                	choice = input.nextInt();
            		if(choice == -1 || (choice >= 1 && choice <= 3))
            			break;
            	}
            	
            	if(choice == -1){
            		display.page = new MainPage();
            		return;
            	}
            	
            	int count = -2;
            	while(true){
                	System.out.print("How Many Would You  Like To Display:  ");
                	count = input.nextInt();
            		if(count == -1 || count >= 1 )
            			break;
            	}
            	
            	if(count == -1){
            		display.page = new MainPage();
            		return;
            	}
            	
            	PreparedStatement pstmt;
            	switch(choice){
            	
            	case 1:
            		sql = "select h.id, h.amount"
            				+ " from (select count(*) as amount, th.house_id as id "
            				+  		"from Temporary_Housing th, Reservations r"
            				+ 		" where th.category = ? and th.house_id = r.house_id"
            				+ 		" group by th.house_id) as h order by h.amount DESC";
            		pstmt = conn.con.prepareStatement(sql);
                	for(int i = 0; i < cat.size(); i++){
                    	pstmt.clearParameters();
                    	pstmt.setString(1, cat.get(i));
                    	rs = pstmt.executeQuery();
                    	int number = 0;
                    	System.out.println("\nTop TH for " + cat.get(i));
                    	while(rs.next() && number++ < count){
                    		System.out.println("house_id:"+rs.getString(1)+ " visits:"+rs.getString(2));
                    	}
                	}

            		break;
            	case 2:
            		sql = "select h.id, h.amount"
            				+ " from (select th.price as amount, th.house_id as id "
            				+  		"from Temporary_Housing th, Reservations r"
            				+ 		" where th.category = ? and th.house_id = r.house_id"
            				+ 		" group by th.house_id) as h order by h.amount DESC";
            		pstmt = conn.con.prepareStatement(sql);
                	for(int i = 0; i < cat.size(); i++){
                    	pstmt.clearParameters();
                    	pstmt.setString(1, cat.get(i));
                    	rs = pstmt.executeQuery();
                    	int number = 0;
                    	System.out.println("\nTop TH for " + cat.get(i));
                    	while(rs.next() && number++ < count){
                    		System.out.println("house_id:"+rs.getString(1)+ " price:"+rs.getString(2));
                    	}
                	}
            		break;
            	case 3:
            		sql = "select h.id, h.amount"
            				+ " from (select avg(f.score) as amount, th.house_id as id "
            				+  		"from Temporary_Housing th, Feedback f"
            				+ 		" where th.category = ? and th.house_id = f.house_id"
            				+ 		" group by f.house_id) as h order by h.amount DESC";
            		pstmt = conn.con.prepareStatement(sql);
                	for(int i = 0; i < cat.size(); i++){
                    	pstmt.clearParameters();
                    	pstmt.setString(1, cat.get(i));
                    	rs = pstmt.executeQuery();
                    	int number = 0;
                    	System.out.println("\nTop TH for " + cat.get(i));
                    	while(rs.next() && number++ < count){
                    		System.out.println("house_id:"+rs.getString(1)+ " average feedback:"+rs.getString(2));
                    	}
                	}
            		break;
            	}
            	
            	
            	
            	conn.closeConnection();
            	/*System.out.println("");
            	PreparedStatement pstmt = conn.con.prepareStatement(sql);
            	
            	pstmt.clearParameters();
            	pstmt.setString(1, user);
            	pstmt.setInt(2, id);
            	
            	rs = pstmt.executeQuery();*/
        	}
            catch(IllegalArgumentException e){
                errors.IntegerError();
                input.nextLine();
            }
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	
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
