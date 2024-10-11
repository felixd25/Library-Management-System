//^You might need to remove that package above in order to run the program, it's just the folder this program was built in.
/*
Book.java
By Colin and Felix (Kin Ho)
January 20 2024
Java 8, Eclipse Neon 1.a (4.6.1)
Problem Definition – 
Input - 
Output – 
*/
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Book implements Serializable{
	
	private int bookNum = 0;
	private String ISBN;
	private String title;
	private String author;
	private boolean issued = false;
	private int dateDue = 0;
	private String borrower = null;
	
	
	public Book(){
		
	}
	
	public Book(String ISBN, String title, String author){
		this.ISBN = ISBN;
		this.title = title;
		this.author = author;
	}
	
	public void setISBN(String input){
		ISBN = input;
	}
	
	public String getISBN(){
		return ISBN;
	}
	
	public void setTitle(String input){
		title = input;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setAuthor(String input){
		author = input;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public int getDateDue(){
		return dateDue;
	}
	
	public void setDateDue(int input){
		dateDue = input;
	}
	
	public void setBorrower(String input){
		borrower = input;
	}
	
	public String getBorrower(){
		return borrower;
	}
	
	public boolean getIssued(){
		return issued;
	}
	
	public void setIssued(boolean input){
		issued = input;
	}
	
	public int getBookNum(){
		return bookNum;
	}
	
	
	public void addBook(Book newBook){
		try{
			File f1 = new File("Library");  //Creating a folder using mkdir() method  
			if(f1.mkdir()){  
				System.out.println("Library Folder is created successfully");  
			}else{  
				System.out.println("Library Folder already created"); 
			}
			
			File a = new File("Library//" + newBook.ISBN + ".txt");
			bookNum = 1;
			while(a.exists() && !a.isDirectory()){
				a = new File("Library//" + newBook.ISBN + "(" + bookNum + ").txt");
				bookNum++;
			}
			FileOutputStream f = new FileOutputStream(a);
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(newBook);
			
			o.close();
			f.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editBook(Book b){
		try {
			System.out.println("editing");
			File f1 = new File("Library");  //Creating a folder using mkdir() method  
			if(f1.mkdir()){  
				System.out.println("Library Folder is created successfully");  
			}else{  
				System.out.println("Library Folder already created"); 
			}
			File a;
			if (b.bookNum > 1){
				a = new File("Library//" + b.ISBN + "(" + b.bookNum + ").txt");
			} else {
				a = new File("Library//" + b.ISBN + ".txt");
			}
			if (a.exists()){
				System.out.println("I AM FINEEEEE");
			}
			FileOutputStream f = new FileOutputStream(a);
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(b);
			
			o.close();
			f.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Book b = new Book("123456","Test","Tester");
		b.addBook(b);
		System.out.println(b.bookNum);
		Book[] lib = createBookList();
	}
	
	
	@Override
	public String toString() {
		return "ISBN: " + ISBN + "\nAuthor: " + author + "\nTitle: " + title;
	}
	
	public static Book getBookInfo(String ISBN){
		try{
			
			FileInputStream fi = new FileInputStream(new File("Library//" + ISBN + ".txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			Book b = (Book) oi.readObject();
			oi.close();
			fi.close();
			return b;
			
		} catch (FileNotFoundException e) {
			System.out.println("Info not found");
			return null;
		} catch (IOException e) {
			System.out.println("No info");
			return null;
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No info");
			return null;
		}
	}
	
	public static Book[] createBookList(){
		File path = new File("Library");

	    File [] files = path.listFiles();
	    
	    Book[] bookList = new Book[files.length];
	    FileInputStream fi = null;
	    ObjectInputStream oi = null;
			
		for (int i = 0; i < files.length; i++){
		    try {
				fi = new FileInputStream(new File("Library//" + files[i].getName()));
				oi = new ObjectInputStream(fi);
			    bookList[i] = (Book) oi.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
		try {
			oi.close();
			fi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return bookList;
	}
	
	public static Book[] createBorrowList(String borrower){
		File path = new File("Library");

	    File [] files = path.listFiles();
	    
	    ArrayList<Book> borrows = new ArrayList<Book>();
	    FileInputStream fi = null;
	    ObjectInputStream oi = null;
			
		for (int i = 0; i < files.length; i++){
		    try {
				fi = new FileInputStream(new File("Library//" + files[i].getName()));
				oi = new ObjectInputStream(fi);
			    Book bookCheck = (Book) oi.readObject();
			    if (bookCheck.borrower == null)
			    	continue;
			    if(bookCheck.borrower.equals(borrower)){
			    	borrows.add(bookCheck);
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
		try {
			oi.close();
			fi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Book[] bookList = new Book[borrows.size()];
		for(int i = 0; i < bookList.length; i++){
			bookList[i] = borrows.get(i);
		}
	    return bookList;
	}
	
	public static Book borrowBook(Book b, String borrow){
		b.borrower = borrow;
		b.issued = true;
		b.dateDue = (int) (Instant.now().getEpochSecond() + 1209600);
		return b;
	}
	    
	public static Book returnBook(Book b){
		b.borrower = null;
		b.issued = false;
		b.dateDue = 0;
		return b;
	}
}