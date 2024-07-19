import java.sql.ResultSet;
import java.util.ArrayList;

public interface LibraryServices  {
     ArrayList<Author> authors =  new ArrayList<>();
     ArrayList<Customer> customers = new ArrayList<>();
     ArrayList<Book> books = new ArrayList<>();

   static void clearSystem(){
          authors.clear();
          customers.clear();
          books.clear();
     }
}
