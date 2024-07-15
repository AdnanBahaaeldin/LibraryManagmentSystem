import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Library implements LibraryServices {

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }
}