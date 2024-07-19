import java.util.ArrayList;

public class Author extends Person implements LibraryServices  {

    private final int authorId;

    Author(String firstName, String lastName, int age,int authorId)
    {
        super(firstName,lastName, age);
        this.authorId = authorId;
    }

    public int getAuthorId() {
        return authorId;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Author details: \n"
                + "-First Name:" + getFirstName() + "\n"
                + "-Last Name:" + getLastName() + "\n"
                + "-Age:" + getAge() + "\n"
                + "Published Books: \n");

        for (Book book : LibraryServices.books) {
            if (book.getAuthorId() == this.authorId){
                s.append(book.getName()).append(" ");
                s.append(book.getBookId());
                s.append("\n");
            }
        }

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
