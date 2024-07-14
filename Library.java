import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Library {

    private ArrayList<Author> authors = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }
    public Customer checkCustomer (Customer customer){
        for (int i=0; i<customers.size(); i++){
            if(customers.get(i).getFirstName().equals(customer.getFirstName()) && customers.get(i).getLastName().equals(customer.getLastName()) && customers.get(i).getAge() == customer.getAge()){
                return customers.get(i);
            }
        }
        return null;
    }
    public Author getAuthor(String firstName, String lastName ,int age) {
        Author tempAuthor = new Author(firstName,lastName,age);
        for (int i=0; i<authors.size(); i++){
            if (authors.get(i).equals(tempAuthor)){
                return authors.get(i);
            }
        }
        authors.add(new Author(firstName,lastName,age));
        return authors.getLast();
    }


    public void addBook(String bookName , Author author , String dateOfPublish, String bookType) {

        int flag =0;
        for (int i = 0; i < authors.size(); i++){
            if (authors.get(i).equals(author)){
                flag =1;
                author.addBook(bookName,dateOfPublish,bookType);
            }
        }
        if (flag == 0){
            authors.add(author);
            author.addBook(bookName,dateOfPublish,bookType);
        }
    }

    public void removeBook (String bookName , Author author , String dateOfPublish, String bookType) {
        int flag =0;
        for (int i = 0; i < authors.size(); i++){
            if (authors.get(i).equals(author)){
                flag =1;
                author.removeBook(bookName,dateOfPublish,bookType);
            }
        }
        if (flag == 0){
            System.out.println ("This book can't be removed as it doesn't exist");
        }
    }

    public Book borrowBook (Book book, Author author){

        for (int i = 0; i < authors.size(); i++){
            if (authors.get(i).equals(author)){
                for (int j = 0; j < authors.get(i).getAuthorBooks().size(); j++){
                    if(authors.get(i).getAuthorBooks().get(j).equals(book) && authors.get(i).getAuthorBooks().get(j).getStatus().equals("Available") ){
                        authors.get(i).getAuthorBooks().get(j).setStatus("Not Available");
                        return authors.get(i).getAuthorBooks().get(j);
                    }
                    else {
                        System.out.println("Sorry,this book is currently unavailable");
                    }
                }
            }
        }
        return null;
    }


    public Book returnBook (Book book, Author author){

        for (int i = 0; i < authors.size(); i++){
            if (authors.get(i).equals(author)){
                for (int j = 0; j < authors.get(i).getAuthorBooks().size(); j++){
                    if(authors.get(i).getAuthorBooks().get(j).equals(book) && authors.get(i).getAuthorBooks().get(j).getStatus().equals("Not Available") ){
                        authors.get(i).getAuthorBooks().get(j).setStatus("Available");
                        return authors.get(i).getAuthorBooks().get(j);
                    }
                    else {
                        System.out.println("Sorry,this book is un-acceptable");
                        return null;
                    }
                }
            }
        }
        return null;
    }
}

