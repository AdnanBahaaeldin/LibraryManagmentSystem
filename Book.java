import java.util.Date;

public class Book {

    private String name;
    private Author author;
    private String dateOfPublish;
    private String type;
    private String status;
    private String returnDate;
    private static int bookId = 1;

    Book (String name , String dateOfPublish, String bookType){
        this.name = name;
        this.dateOfPublish  = dateOfPublish;
        this.type = bookType;
        this.status = "Available";
        bookId++;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
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

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setDateOfPublish(String dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
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
        return "Book name: " +this.name + " Book type: " + this.type + " Date of publishing: " + this.dateOfPublish + " ["+ this.status + "]\n";
    }
}
