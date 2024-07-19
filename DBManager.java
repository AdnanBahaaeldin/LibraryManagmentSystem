import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class DBManager implements LibraryServices {

    private static final String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/Library";
    private static final String jdbUsername = "root";
    private static final String jdbcPassword = "#Deblob67";
    private static Connection connection;

    public static void setupConnection() {

        try {
            connection = DriverManager.getConnection(
                    jdbcUrl,
                    jdbUsername,
                    jdbcPassword
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closConnection() {

        try {

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dbInit (){
        try{
            Statement stmnt = connection.createStatement();

            stmnt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmnt.executeUpdate("TRUNCATE TABLE customer");
            stmnt.executeUpdate("TRUNCATE TABLE book");
            stmnt.executeUpdate("TRUNCATE TABLE author");
            stmnt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

            stmnt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static int fetchAuthorId(String authorFName, String authorLName, int authorAge){
        try {
            PreparedStatement Statement  = connection.prepareStatement("SELECT authorId FROM author WHERE authorFName = ? " +
                    "AND authorLName = ? AND authorAge = ?");
            Statement.setString(1,authorFName);
            Statement.setString(2,authorLName);
            Statement.setInt(3,authorAge);
            ResultSet rs = Statement.executeQuery();

           if (rs.next()){
               return rs.getInt("authorId");
           }
           else {
               return 0;
           }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static void fetchAuthorBooks(int authorId){
        try {
            ArrayList <Integer> rtrn = new ArrayList<>();
            Statement statementAuthor = connection.createStatement();
            ResultSet rsAuthor = statementAuthor.executeQuery("SELECT bookName,bookId FROM book WHERE author = " + authorId);
            while (rsAuthor.next()){
                System.out.println("Book name: " + rsAuthor.getString("bookName"));
                System.out.println("Book ID: " + rsAuthor.getInt("bookId"));
            }
        }catch (SQLException e) {
            System.out.println("Author has no more registered books.");
        }
    }

    public static ArrayList<Customer> fetchCustomers() {
        try {
            Customer tempcustomer;
            Statement statementAuthor = connection.createStatement();
            ResultSet rsCustomer = statementAuthor.executeQuery("SELECT * FROM customer");
            ArrayList<Customer> customers = new ArrayList<>();
            ArrayList <Integer> customerBorrowedBook = new ArrayList<>();

            while (rsCustomer.next()) {
                String customerFName = rsCustomer.getString("customerFName");
                String customerLName = rsCustomer.getString("customerLName");
                int customerAge = rsCustomer.getInt("customerAge");
                int customerId = rsCustomer.getInt("customerId");
                customers.add(tempcustomer = new Customer(customerFName, customerLName, customerAge,customerId));
                tempcustomer.setCustomerId(customerId);

                Statement innerStmnt = connection.createStatement();
                ResultSet rsInner = innerStmnt.executeQuery("SELECT borrowedBook FROM customer WHERE customerId ="  + customerId);
                while (rsInner.next()){
                    try {
                        customerBorrowedBook.add(rsInner.getInt("borrowedBook"));
                        tempcustomer.setBorrowedBook(customerBorrowedBook.getLast());
                    }catch (NullPointerException e){
                        break;
                    }
                }
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void addAuthor(String authorFName,String authorLName, int authorAge){
        try {
            Statement stmnt = connection.createStatement();
            stmnt.executeUpdate("INSERT INTO author (authorFName,authorLName,authorAge)" +
                    "values( \'"  + authorFName + "\',\'" + authorLName + "\'," + authorAge + ")");

            PreparedStatement pstmnt = connection.prepareStatement("SELECT authorId FROM author WHERE authorFName = ? AND authorLName = ? AND authorAge = ?");
            pstmnt.setString(1,authorFName);
            pstmnt.setString(2,authorLName);
            pstmnt.setInt(3,authorAge);
            ResultSet rs = pstmnt.executeQuery();
            rs.next();
            LibraryServices.authors.add(new Author(authorFName,authorLName,authorAge,rs.getInt("authorId")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCustomer(String customerFName,String customerLName, int customerAge){
        try {
            Statement stmnt = connection.createStatement();
            stmnt.executeUpdate("INSERT INTO customer (customerFName,customerLName,customerAge)" +
                    "values( \'"  + customerFName + "\',\'" + customerLName + "\'," + customerAge + ")");

            PreparedStatement pstmnt = connection.prepareStatement("SELECT customerId FROM customer WHERE customerFName = ? AND customerLName = ? AND customerAge = ?");
            pstmnt.setString(1,customerFName);
            pstmnt.setString(2,customerLName);
            pstmnt.setInt(3,customerAge);
            ResultSet rs = pstmnt.executeQuery();
            rs.next();
            LibraryServices.customers.add(new Customer(customerFName,customerLName,customerAge,rs.getInt("customerId")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//Checks if customer is present in DB
    public static boolean checkCustomer(int customerID) {
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs;
            rs = stmnt.executeQuery("SELECT customerFName FROM customer WHERE customerId =" + customerID);
            return (rs != null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Add book to DB and library services
    public static void addBook(String bookName, String dateOfPublish, String bookType , int authorId) {
        try {
           Statement stmnt = connection.createStatement();
            stmnt.executeUpdate("INSERT INTO book (bookName,dateOfPublish,bookType,bookStatus,author)" +
                    "values( \'"  + bookName + "\',\'" + dateOfPublish + "\',\'" + bookType + "\',\'Available\', " + authorId + ")");

            PreparedStatement pstmnt = connection.prepareStatement("SELECT bookId FROM book WHERE author = ?");
            pstmnt.setInt(1,authorId);
            ResultSet rs = pstmnt.executeQuery();
            rs.next();
            LibraryServices.books.add(new Book(rs.getInt("bookId"),bookName,dateOfPublish,bookType,authorId));

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Removes book from DB and library services
    public static void removeBook(String bookName, String dateOfPublish, String bookType ,int authorId) {
        try {
            Statement stmnt = connection.createStatement();
            stmnt.executeUpdate("DELETE FROM book WHERE bookName =\'" + bookName +
                    "\' AND dateOfPublish =\'" + dateOfPublish +
                    "\' AND bookType =\'" + bookType +
                    "\' AND author =" + authorId);
            LibraryServices.books.removeIf(book -> book.getName().equals(bookName) && book.getDateOfPublish().equals(dateOfPublish) && book.getType().equals(bookType) && book.getAuthorId() == authorId);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Fetches the book if present in DB
    public static Book fetchBook (String bookName, String dateOfPublish, String bookType , int authorId){
        try{
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM book WHERE bookName =\'" + bookName +
                    "\' AND dateOfPublish =\'" + dateOfPublish +
                    "\' AND bookType =\'" + bookType +
                    "\' AND author =" + authorId);
            rs.next();
            String name = rs.getString("bookName");
            String date = rs.getString("dateOfPublish");
            String type = rs.getString("bookType");
            String status = rs.getString("bookStatus");
            int bookId = rs.getInt("bookId");
            int author = rs.getInt("author");
            Book tempBook = new Book (bookId,name,date,type,author);
            tempBook.setStatus(status);
            return (tempBook);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Fetches the customer if present in DB
    public static Customer fetchCustomer (int cId){
        try{
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM customer WHERE customerId =" + cId);
            rs.next();
            int customerId = rs.getInt("customerId");
            for (Customer customer : LibraryServices.customers) {
                if (customer.getCustomerId() == customerId) {
                    return customer;
                }
            }
        }catch (SQLException e){
            System.out.println("Such customer doesn't exist, please create an account first");
        }
        return null;
    }

    //Fetches all and new authors from DB and adds them to authors List
    public static ArrayList<Author> fetchAuthors (){
        try{
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM author");

            while (rs.next()){
                String nameFirst = rs.getString("authorFName");
                String nameLast = rs.getString("authorLName");
                int cAge = rs.getInt("authorAge");
                int authorId = rs.getInt("authorId");

                int flag = 1;
                for (Author author : LibraryServices.authors) {
                    if (author.getAuthorId() == authorId) {
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    authors.add(new Author(nameFirst,nameLast,cAge,authorId));
                }
            }
            return (authors);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Borrows a book from Library DB
    public static void borrowBook (Book book ,int customerId,String returnDate , Customer customer){
        try{
            Statement stmnt = connection.createStatement();
            Statement innerStmnt1 = connection.createStatement();
            Statement innerStmnt2 = connection.createStatement();
                if (book.getStatus().equals("Available")){
                    stmnt.executeUpdate("UPDATE book SET bookStatus = \'Not Available\' WHERE bookId =" + book.getBookId());
                    innerStmnt1.executeUpdate("UPDATE customer SET borrowedBook =" + book.getBookId()
                           +" WHERE customerId = " + customerId);
                    innerStmnt2.executeUpdate("UPDATE book SET returnDate =\'" + returnDate + "\'" +
                            "WHERE bookId = " + book.getBookId());
                    customer.setBorrowedBook(book.getBookId());
                }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Returns a book to Library DB
    public static void returnBook (Book book ,int customerId,Customer customer){
        try{
            Statement stmnt = connection.createStatement();
            Statement innerStmnt1 = connection.createStatement();
            Statement innerStmnt2 = connection.createStatement();
            if (book.getStatus().equals("Not Available")){
                stmnt.executeUpdate("UPDATE book SET bookStatus = \'Available\' WHERE bookId = " + book.getBookId());
                innerStmnt1.executeUpdate("UPDATE customer set borrowedBook = null where customerId =" + customerId);
                innerStmnt2.executeUpdate("UPDATE book set returnDate = \' \' WHERE bookId = " + book.getBookId());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

