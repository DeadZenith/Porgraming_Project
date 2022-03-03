import java.util.Scanner;
import java.util.Properties;
import model.BookCollection;
import model.*;
import userinterface.LibrarianView;

public class testMain {
    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        Properties prop1 = new Properties();
        //Properties prop2 = new Properties();
        String temp;
        //LibrarianView();
        String response;
        do {
            System.out.println("Would You Like to add a (B)ook or (P)");
            response = input.nextLine();
            if (response.equals("B")) {
                System.out.println("----------------------------------------");
                System.out.println("Book");
                System.out.println("Book Title");
                String info1 = input.nextLine();
                System.out.println("Author");
                String info2 = input.nextLine();
                System.out.println("Publication Year");
                String info3 = input.nextLine();
                ;
                prop1.setProperty("bookTitle", info1);
                prop1.setProperty("author", info2);
                prop1.setProperty("pubYear", info3);
                prop1.setProperty("status", "Active");
                Book book1 = new Book(prop1);
                book1.update();
                System.out.println("----------------------------------------");
            }
            if (response.equals("P")) {
                System.out.println("----------------------------------------");
                System.out.println("Patron");
                System.out.println("Patron Name");
                String info1 = input.nextLine();
                System.out.println("Address");
                String info2 = input.nextLine();
                System.out.println("City");
                String info3 = input.nextLine();
                System.out.println("stateCode");
                String info4 = input.nextLine();
                System.out.println("Zip");
                String info5 = input.nextLine();
                System.out.println("Email");
                String info6 = input.nextLine();
                System.out.println("Date Of Birth");
                String info7 = input.nextLine();
                prop1.setProperty("name", info1);
                prop1.setProperty("address", info2);
                prop1.setProperty("city", info3);
                prop1.setProperty("stateCode", info4);
                prop1.setProperty("zip", info5);
                prop1.setProperty("email", info6);
                prop1.setProperty("dateOfBirth", info7);
                prop1.setProperty("status", "Active");
                Patron patron1 = new Patron(prop1);
                patron1.update();
                System.out.println("----------------------------------------");
                System.out.println("Would you like to add another (B)ook or (P)atron ?");
            }
        } while (response.equals("B") || response.equals("P"));
        System.out.println("----------------------------------------");
        BookCollection BC1 = new BookCollection();
        PatronCollection PC1 = new PatronCollection();
        System.out.println("If you would like to make a query please select one of the bellow");
        System.out.print("1.Find Books Before a Specific Year \n" +
                "2.Find Books after a Specific Year \n" +
                "3.Find Books by Title (3)\n" +
                "4.Find Books with Specific Name of Author(4) \n" +
                "5.Find Patrons Born Before a Year(5)\n" +
                "6.Find Patrons Born After a Year (6)\n" +
                "7.Find Patrons by Zipcode\n" +
                "8.Find Patrons By Name\n" +
                "0.To Quit\n");

        int querySelection = input.nextInt();
        while (querySelection != 0) {
            if (querySelection == 1) {
                System.out.println("Find all books before what year?");
                String year = input.nextLine();
                BC1.findBooksOlderThanDate(year);
                BC1.display();
                System.out.println("Please enter your next selection or 0 to quit");
                querySelection = input.nextInt();
            }
            if (querySelection == 2) {
                System.out.println("Find all books after what year?");
                String year = input.nextLine();
                BC1.findBooksNewerThanDate(year);
                BC1.display();
                System.out.println("Please enter your next selection or 0 to quit");
                querySelection = input.nextInt();
            }
            if (querySelection == 3) {
                System.out.println("Find all books matching what title?");
                String title = input.nextLine();
                BC1.findBooksWithTitleLike(title);
                BC1.display();
                System.out.println("Please enter your next selection or 0 to quit");
                querySelection = input.nextInt();
            }
            if (querySelection == 4) {
                System.out.println("Find all books by which author?");
                String auth = input.nextLine();
                BC1.findBooksWithAuthorLike(auth);
                BC1.display();
                System.out.println("Please enter your next selection or 0 to quit");
                querySelection = input.nextInt();
            }
            if (querySelection == 5) {
                System.out.println("Find all patrons born before what year?");
                String year = input.nextLine();
                PC1.findPatronsOlderThan(year);
                PC1.display();
                System.out.println("Please enter your next selection or 0 to quit");
                querySelection = input.nextInt();
            }
            if (querySelection == 6) {
                System.out.println("Find all Patrons born after what year?");
                String year = input.nextLine();
                PC1.findPatronsYoungerThan(year);
                PC1.display();
                System.out.println("Please enter your next selection or 0 to quit");
                querySelection = input.nextInt();
            }
            if (querySelection == 7) {
                System.out.println("Find all Patrons who live in what ZipCode?");
                String zip = input.nextLine();
                PC1.findPatronsAtZipCode(zip);
                PC1.display();
                System.out.println("Please enter your next selection or 0 to quit");
                querySelection = input.nextInt();
            }
            if (querySelection == 8) {
                System.out.println("Find all matching what Name?");
                String nam = input.nextLine();
                PC1.findPatronsWithNameLike(nam);
                PC1.display();
                System.out.println("Please enter your next selection or 0 to quit");
                querySelection = input.nextInt();
            }
        }
        System.out.println("Thank You have a good day");
    }
}
