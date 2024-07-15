import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Library manager = new Library();
        Scanner scanner = new Scanner(System.in);

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
                                    "\t 4) Show Author's work \n" +
                                    "\t 5) Add customer \n" +
                                    "\t 6) Show all customers \n" +
                                    "\t 7) Return to previous list \n");

                    in = scanner.nextInt();
                    if (in == 7) {
                        break;
                    }
                    switch (in) {
                        case 1:
                            System.out.println("Enter author details: First name, Last name and age \n");
                            manager.getAuthors().add(new Author(scanner.next(), scanner.next(), scanner.nextInt()));
                            System.out.println("Author has been added successfully.");
                            break;
                        case 2:
                            System.out.println("Enter book details: Book name, Author details, Release date and Genre \n");
                            manager.addBookByAuthorName(scanner.next(), manager.getAuthor(scanner.next(), scanner.next(), scanner.nextInt()), scanner.next(), scanner.next());
                            System.out.println("Book has been added successfully.");
                            break;
                        case 3:
                            System.out.println("Enter book details: Book name, Author details, Release date and genre \n");
                            manager.removeBook(scanner.next(), manager.getAuthor(scanner.next(), scanner.next(), scanner.nextInt()), scanner.next(), scanner.next());
                            System.out.println("Book has been removed successfully.");
                            break;
                        case 4:
                            System.out.println("Enter author details: First name, Last name and age \n");
                            System.out.println(manager.getAuthor(scanner.next(), scanner.next(), scanner.nextInt()));
                            break;
                        case 5:
                            System.out.println("Enter customer details: First name, Last name and age\n");
                            manager.getCustomers().add(new Customer(scanner.next(), scanner.next(), scanner.nextInt()));
                            System.out.println("Customer has been added successfully.");
                            break;
                        case 6:
                            System.out.println("Showing all customers: \n");
                            System.out.println(manager.getCustomers().toString());
                            break;
                        default:
                            break;
                    }
                }
            } else if (in == 2) {
                    System.out.println("\t Welcome to the library ! \n" +
                            "Please enter your name and age:\n");
                    Customer customer = new Customer(scanner.next(), scanner.next(), scanner.nextInt());
                    if (manager.checkCustomer(customer) != null) {
                        while (true){
                        System.out.println(
                                        "Hello, " + customer.getFirstName() + " How can we help you today ? \n" +
                                        "\t 1) Borrow a book \n" +
                                        "\t 2) Return a book \n" +
                                        "\t 3) Show borrowed books \n" +
                                        "\t 4) Show Authors and their works \n" +
                                        "\t 5) Create an account \n" +
                                        "\t 6) Return to previous list\n");

                        in = scanner.nextInt();
                        switch (in) {
                            case 1:
                                System.out.println("Enter book details:");
                                Book brwBook = manager.borrowBook(new Book(scanner.next(), scanner.next(), scanner.next()));
                                try {
                                    customer.getBorrowedBooks().add(brwBook);
                                    System.out.println("Enter return date:");
                                    customer.getBorrowedBooks().getLast().setReturnDate(scanner.next());
                                    System.out.println("The specified book: " + brwBook.getName()+" has been borrowed for customer:" + customer.getFirstName() + "with id: " +customer.getCustomerId());
                                } catch (NullPointerException e){
                                    System.out.println("Sorry, this book is unavailable");
                                }
                                break;
                            case 2:
                                System.out.println("Enter book details:");
                                Book rtrnBook = manager.returnBook(new Book(scanner.next(), scanner.next(), scanner.next()));
                                try {
                                    customer.getBorrowedBooks().remove(rtrnBook);
                                    System.out.println("The specified book: " +rtrnBook.getName() +" has been returned by customer: " + customer.getFirstName() +"\n");
                                } catch (NullPointerException e){
                                    System.out.println("Sorry, this book is unavailable");
                                }
                                break;
                            case 3:
                                System.out.println("Borrowed books for customer: \n" + customer.getFirstName() + " " + customer.getLastName());
                                for (int i=0; i<customer.getBorrowedBooks().size(); i++){
                                    System.out.println(customer.getBorrowedBooks().get(i) + " Return date: " + customer.getBorrowedBooks().get(i).getReturnDate());
                                }
                                break;
                            case 4:
                                System.out.println("All authors and their books: \n"+ manager.getAuthors().toString());
                                break;
                            case 5:
                                System.out.println("Please enter account details: First name, Last name and age \n");
                                manager.getCustomers().add(new Customer(scanner.next(), scanner.next(), scanner.nextInt()));
                                System.out.println("Account has been created successfully.");
                                break;
                            default:
                                break;
                        }
                    }

                } else {
                        System.out.println("Such customer doesn't exist, please create an account first");
                        System.out.println("Please enter account details: First name, Last name and age \n");
                        manager.getCustomers().add(new Customer(scanner.next(), scanner.next(), scanner.nextInt()));
                    }

            } else if (in == 3){
                break;
            }
        }
    }
}