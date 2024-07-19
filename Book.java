import java.util.ArrayList;
import java.util.Date;

public class Book implements LibraryServices{

    private final String name;
    private final String dateOfPublish;
    private final String type;
    private String status;
    private String returnDate;
    private final int authorId;
    private final int bookId;


    Book (int bookId,String name , String dateOfPublish, String bookType , int author){
        this.name = name;
        this.dateOfPublish  = dateOfPublish;
        this.type = bookType;
        this.bookId = bookId;
        this.status = "Available";
        this.authorId = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateOfPublish() {
        return dateOfPublish;
    }

    public String getName() {
        return name;
    }

    public int getBookId() {
        return bookId;
    }

    public String getType() {
        return type;
    }

    public int getAuthorId() {
        return authorId;
    }

    @Override
    public boolean equals(Object o) {
        try {
            return (this.name.equalsIgnoreCase(((Book)o).getName()) && this.dateOfPublish.equalsIgnoreCase(((Book) o).getDateOfPublish()) && this.getType().equalsIgnoreCase(((Book) o).getType()));
        }
        catch (ClassCastException e){
            return false;
        }
    }

    @Override
    public String toString() {
        return "Book name: " +this.name + " Book type: " + this.type + " Date of publishing: " + "Author: " + this.authorId + this.dateOfPublish + " ["+ this.status + "]\n";
    }
}
