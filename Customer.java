import java.util.ArrayList;

public class Customer extends Person  {

    private int customerId;
    private final ArrayList <Integer> borrowedBook;

    Customer(String firstName, String lastName, int age,int customerId) {
        super(firstName,lastName, age);
        borrowedBook = new ArrayList<>();
        this.customerId = customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setBorrowedBook(int borrowedBook) {
        this.borrowedBook.add(borrowedBook);
    }

    public ArrayList<Book> getBorrowedBooks() {
             ArrayList<Book> brwdBooks = new ArrayList<>();
             for (int i=0; i< LibraryServices.books.size(); i++){
                 for (Integer integer : borrowedBook) {
                     if (LibraryServices.books.get(i).getBookId() == integer) {
                         brwdBooks.add(LibraryServices.books.get(i));
                         brwdBooks.getLast().setStatus("Not Available");
                     }
                 }
             }
             return brwdBooks;
    }

    public int getCustomerId() {
        return customerId;
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
        String s1 = "Customer name:" + this.getFirstName() + " " + this.getLastName() + "\n Customer age: " + this.getAge() + "\n Customer ID: " + this.getCustomerId();
        String s2;
        try {
          s2 =   "\n Borrowed books:" + this.getBorrowedBooks();

        }catch (NullPointerException e) {
            s2 = "\n Customer has not borrowed any books.";
        }
        return s1+s2;
    }
}
