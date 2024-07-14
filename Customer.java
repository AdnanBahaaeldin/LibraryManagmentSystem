import java.util.ArrayList;

public class Customer extends Person  {
    static int customerId = 0;
    int id;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    Customer(String firstName, String lastName, int age) {
        super(firstName,lastName, age);
        this.id = customerId++;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getCustomerId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Customer.class){
            return (this.getFirstName().equalsIgnoreCase(((Customer) o).getFirstName()) && this.getLastName().equalsIgnoreCase(((Customer) o).getLastName()) && this.getAge() == ((Customer) o).getAge() && this.getCustomerId() == ((Customer) o).getCustomerId());
        }
        else return false;
    }

    @Override
    public String toString() {
        return("Customer name:" + this.getFirstName() + " " + this.getLastName() + "\n Customer age: " + this.getAge() + "\n Customer ID: " + this.getCustomerId());
    }
}
