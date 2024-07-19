import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DBManager.setupConnection();
        LibraryServices.clearSystem();

        while (true) {
            System.out.println("Choose a program:\n" +
                    "\t 1)Management \n" +
                    "\t 2)Customer\n" +
                    "\t 3)Terminate program \n");

            int in = scanner.nextInt();
            if (in == 1) {
                while (true) {
                    System.out.println(
                            "Please choose one from the following tasks: \n" +
                                    "\t 1) Add an author \n" +
                                    "\t 2) Add a book \n" +
                                    "\t 3) Remove a book \n" +
                                    "\t 4) Get author details and ID \n" +
                                    "\t 5) Show Author's work \n" +
                                    "\t 6) Add customer \n" +
                                    "\t 7) Show all customers \n" +
                                    "\t 8) Return to previous list \n");

                    in = scanner.nextInt();
                    if (in == 8) {
                        break;
                    }
                    switch (in) {
                        case 0:
                            System.out.println("Enter authorization:");
                            String managerName = scanner.next();
                            int password = scanner.nextInt();
                            if (managerName .equals("Adnan") && password == 1234){
                                DBManager.dbInit();
                                break;
                            }
                            System.out.println("Wrong authorization.");
                            break;
                        case 1:
                            System.out.println("Enter author details: First name, Last name and age \n");
                            String authorFName;
                            String authorLName;
                            int authorAge;
                            DBManager.addAuthor(authorFName = scanner.next(), authorLName = scanner.next(), authorAge = scanner.nextInt());
                            System.out.println("Author " + authorFName + " " + authorLName +
                                    " has been added successfully with ID:" + DBManager.fetchAuthorId(authorFName,authorLName,authorAge));
                            break;
                        case 2:
                            System.out.println("Enter book details: Book name, Release date, Genre and author ID \n");
                            String bookName;
                            DBManager.addBook(bookName = scanner.next(), scanner.next(), scanner.next(),scanner.nextInt());
                            System.out.println("Book " + bookName + "has been added successfully.");
                            break;
                        case 3:
                            System.out.println("Enter book details: Book name, Release date, genre and author Id \n");
                            DBManager.removeBook(scanner.next(),scanner.next(),scanner.next(),scanner.nextInt());
                            System.out.println("Book has been removed successfully.");
                            break;
                        case 4:
                            System.out.println("Enter author details: First name, Last name and age \n");
                            String fname;
                            String lname;
                            int age;
                            int authorId = DBManager.fetchAuthorId(fname = scanner.next(),lname = scanner.next(), age = scanner.nextInt());
                            if (authorId != 0){
                                System.out.println("Author's first name: "+ fname +
                                        "\n Author's last name: " + lname +
                                        "\n Author's age: " + age +
                                        "\n Author's ID: " + authorId);
                            }else {
                                System.out.println("Such author doesn't exist.");
                            }
                            break;
                        case 5:
                            try {
                                System.out.println("Enter author ID: \n");
                                int id = scanner.nextInt();
                                System.out.println("Author's books:");
                                DBManager.fetchAuthorBooks(id);
                                break;
                            }catch (NullPointerException e) {
                                System.out.println("Author doesn't have any registered books.");
                                break;
                            }

                        case 6:
                            System.out.println("Enter customer details: First name, Last name and age\n");
                            DBManager.addCustomer(scanner.next(), scanner.next(), scanner.nextInt());
                            System.out.println("Customer has been added successfully.");
                            break;
                        case 7:
                            try {
                                System.out.println("Showing all customers: \n");
                                ArrayList<Customer> tempcustomer = DBManager.fetchCustomers();
                                for (Customer c : tempcustomer){
                                    System.out.println(c.toString());
                                }
                            }catch (NullPointerException e) {
                                System.out.println("No customers are registered in data base.");
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else if (in == 2) {
                    System.out.println("\t Welcome to the library ! \n" +
                            "Please enter your ID:\n" +
                            "0) Return to previous page");
                    int cId = scanner.nextInt();
                    if (cId == 0){
                        break;
                    }
                    if (DBManager.checkCustomer(cId)) {
                        Customer customer = DBManager.fetchCustomer(cId);
                        while (true) {
                            System.out.println(
                                    "Hello, " + customer.getFirstName() + " your ID is: " + customer.getCustomerId() + " How can we help you today ? \n" +
                                            "\t 1) Borrow a book \n" +
                                            "\t 2) Return a book \n" +
                                            "\t 3) Show borrowed books \n" +
                                            "\t 4) Show Authors and their works \n" +
                                            "\t 5) Return to start page\n");
                            in = scanner.nextInt();
                            if (in == 5) {
                                break;
                            }
                            switch (in) {
                                case 1:
                                    System.out.println("Enter book details,author ID and your ID:");
                                    Book brwBook = DBManager.fetchBook(scanner.next(), scanner.next(), scanner.next(), scanner.nextInt());
                                    int customerId = scanner.nextInt();
                                    if (brwBook != null && brwBook.getStatus().equals("Available")) {
                                        System.out.println("Enter return date:");
                                        DBManager.borrowBook(brwBook, customerId, scanner.next(),customer);
                                        System.out.println("The specified book: " + brwBook.getName() + " has been borrowed for customer:" + customer.getFirstName() + "with ID: " + customer.getCustomerId());
                                    } else {
                                        System.out.println("Sorry, this book is unavailable");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Enter book details and author ID and your ID:");
                                    Book rtrnBook = DBManager.fetchBook(scanner.next(), scanner.next(), scanner.next(), scanner.nextInt());
                                    customerId = scanner.nextInt();
                                    if (rtrnBook != null && rtrnBook.getStatus().equals("Not Available")) {
                                        DBManager.returnBook(rtrnBook, customerId,customer);
                                        System.out.println("The specified book: " + rtrnBook.getName() + " has been returned by customer:" + customer.getFirstName() + "with ID: " + customer.getCustomerId());
                                    } else {
                                        System.out.println("Sorry, this book is not borrowed by you");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Borrowed book for customer: \n" + customer.getFirstName() + " " + customer.getLastName());
                                    System.out.println(customer.getBorrowedBooks());
                                    break;
                                case 4:
                                    System.out.println("All authors and their books: \n" + DBManager.fetchAuthors());
                                    break;
                                case 5:
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
            } else if (in == 3){
                DBManager.closConnection();
                break;
            }
        }
    }
}