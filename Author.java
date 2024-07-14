import java.util.ArrayList;
import java.util.Date;

public class Author extends Person {

    private ArrayList<Book> books = new ArrayList<>();

    Author(String firstName, String lastName, int age)
    {
        super(firstName,lastName, age);
    }

    public ArrayList<Book> getAuthorBooks ()
    {
        return books;
    }
    public void addBook(String name , String dateOfPublish, String bookType){
        books.add(new Book(name,dateOfPublish,bookType));
    }

    public void removeBook(String bookName, String dateOfPublish, String bookType) {
        Book tempbook = new Book(bookName,dateOfPublish,bookType) ;
        int flag = 0;
        for (int i=0; i <books.size(); i++){
            if (books.get(i).equals(tempbook)){
                flag = 1;
                books.remove(i);
                System.out.println("Successfully removed the book");
            }
        }
        if (flag == 0){
            System.out.println("This book isn't available for the system to remove");
        }

    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Author details: \n"
                + "-First Name:" + getFirstName() + "\n"
                + "-Last Name:" + getLastName() + "\n"
                + "-Age:" + getAge() + "\n"
                + "Published Books:");

        for (int i=0; i<books.size(); i++){
            s.append(books.get(i).toString());
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        try {
            return (this.getFirstName().equalsIgnoreCase(((Author) o).getFirstName()) && this.getLastName().equalsIgnoreCase(((Author) o).getLastName()) && this.getAge() == ((Author) o).getAge());
        } catch (ClassCastException e){
            return false;
        }
    }
}
