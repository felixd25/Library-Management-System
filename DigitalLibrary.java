//^You might need to remove that package above in order to run the program, it's just the folder this program was built in.
/*
DigitalLibrary.java
By Colin and Felix (Kin Ho)
January 20 2024
Java 8, Eclipse Neon 1.a (4.6.1)
Problem Definition – 
Input - 
Output – 
*/
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import java.time.Instant;

class PTextField extends JTextField{
	public PTextField(final String proptText) {
        super(proptText);
        addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if(getText().isEmpty()) {
                    setText(proptText);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if(getText().equals(proptText)) {
                    setText("");
                }
            }
        });

    }
}

class Account{
	String name;
	String password;
}

public class DigitalLibrary {
	public class MyListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList list, Object library, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, library, index, isSelected, cellHasFocus);
            Book label = (Book) library;
            String title = label.getTitle();
            String author = label.getAuthor();
            String ISBN = label.getISBN();
            int dueDate = label.getDateDue();
            
            String labelText = "<html>Title " + title + "<br/>author: " + author + "<br/>ISBN: " + ISBN + "<br/>dueDate: " + dueDate;
            setText(labelText);

            return this;
        }

    }
	static Font titf = new Font("Roboto", Font.BOLD, 65);
	static Font basef = new Font("Roboto", Font.PLAIN, 15);
	static Font mainWinf = new Font("Roboto", Font.BOLD, 28);
	
	static Account curUser = new Account();
	static JFrame f = new JFrame();
	
	static Book[] library;
	static Book[] borrowing;
	static Book[] searching;
	
	public static void main(String[] args) throws IOException{
		System.out.println("Current Time: " + Instant.now().getEpochSecond());
		library = Book.createBookList();
		loginPage();
	}
	
	static JLabel titleLabel = new JLabel("Digital Library");
	static PTextField userField = new PTextField("Username");
	static PTextField passField = new PTextField("Password");
	static JLabel errorText = new JLabel("I'm ERROR TEXT");
	static JButton loginButton = new JButton("Login");
	static JButton nAButton = new JButton("New Account");
	
	public static void loginPage(){
		File accounts = new File("accounts");
		if (accounts.mkdir())
			System.out.println("Created folder.");
		else 
			System.out.println("Account folder exists.");
		
		f.setSize(1000, 650); //1000 650
		f.setLayout(null);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setTitle("Digital Library");
		
		titleLabel.setSize(1000, 100);
		titleLabel.setLocation(0, 100);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(titf);
		f.add(titleLabel);
		
		userField.setSize(250, 30);
		userField.setHorizontalAlignment(SwingConstants.CENTER);
		userField.setLocation(375, 300);
		f.add(userField);
		
		passField.setSize(250, 30);
		passField.setHorizontalAlignment(SwingConstants.CENTER);
		passField.setLocation(375, 350);
		f.add(passField);
		
		errorText.setSize(250, 200);
		errorText.setHorizontalAlignment(SwingConstants.CENTER);
		errorText.setLocation(375, 170);
		errorText.setFont(basef);
		errorText.setForeground(Color.RED);
		errorText.setVisible(false);
		f.add(errorText);
		
		loginButton.setBounds(460, 400, 80, 20);
		
		nAButton.setBounds(440, 430, 120, 20);
		
		loginButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File currentLogin = new File("accounts\\" + userField.getText() + ".log");
				if (currentLogin.exists() && !userField.getText().equals("ADMIN")){
					try {
						BufferedReader input = new BufferedReader(new FileReader(currentLogin.getPath()));
						String curPass = input.readLine();
						input.close();
						if (passField.getText().equals(curPass)){
							curUser.name = userField.getText();
							refreshAccountInfo(currentLogin);
							transitionMain();
						} else {
							errorText.setVisible(true);
							errorText.setText("Password is incorrect!");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if(userField.getText().equals("ADMIN")){
					try {
						curUser.name = "ADMIN";
						refreshAccountInfo(currentLogin);
						transitionMain();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} else{
					errorText.setVisible(true);
					errorText.setText("Account does not exist!");
				}
			}
		});
		f.add(loginButton);
		
		
		nAButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File currentLogin = new File("accounts\\" + userField.getText() + ".log");
				try {
					if (currentLogin.createNewFile()){
						PrintWriter output = new PrintWriter(new FileWriter(currentLogin.getPath()));
						output.println(passField.getText());
						output.close();
						curUser.name = userField.getText();
						refreshAccountInfo(currentLogin);
						transitionMain();
					} else {
						errorText.setVisible(true);
						errorText.setText("Account already exists!");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		f.add(nAButton);
		
		f.setVisible(true);
	}
	
	public static void refreshAccountInfo(File f) throws IOException{
		BufferedReader fileRead = new BufferedReader(new FileReader(f.getPath()));
		String pass = fileRead.readLine();
		curUser.password = pass;
		fileRead.close();
	}
	
	public static void transitionMain(){
		f.remove(titleLabel);
		f.remove(userField);
		f.remove(passField);
		f.remove(errorText);
		f.remove(loginButton);
		f.remove(nAButton);
		if(!curUser.name.equals("ADMIN"))
			mainWindow();
		else
			adminWindow();
	}
	
	static JLabel systemName = new JLabel();
	static PTextField searchingBox = new PTextField("Search for a book...");
	static JButton searchButton = new JButton("Search");
	static JButton returnButton = new JButton("Return Book");
	static JPanel searchPanel = new JPanel();
	static JTextArea searchingBox1 = new JTextArea();
	static JPanel bookList = new JPanel();
	static JScrollPane scrollBookList = new JScrollPane(bookList); 
	static String optionList[]={"Title","Author","ISBN"};        
	static JComboBox searchOption=new JComboBox(optionList);    
	static JList<String> infoList;
	static JScrollPane sInfoList;

	
	public static void mainWindow(){
		borrowing = Book.createBorrowList(curUser.name);
		systemName.setSize(600,35);
		systemName.setFont(mainWinf);
		systemName.setLocation(20,20);
		systemName.setText("Library Books System");
		
		searchingBox.setSize(200,25);
		searchingBox.setLocation(90,0);
		
		searchOption.setBounds(0,0,90,25);
		
		searchButton.setSize(100,25);
		searchButton.setLocation(300,0);
		searchButton.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {
	        	
	        	int option = searchOption.getSelectedIndex();  
	        	String inputText = searchingBox.getText();
	        	ArrayList<Book> tempList = new ArrayList<Book>();
	        	switch (option){
	        	case 0:
	        		for(int i = 0; i < library.length; i++){
	        			if (library[i].getTitle().toLowerCase().contains(inputText.toLowerCase())){
	        				//System.out.println(library[i].getTitle() + library[i].getAuthor());
	        				tempList.add(library[i]);
	        			}
	        		}
	        		break;
	        		
	        	case 1:
	        		for(int i = 0; i < library.length; i++){
	        			if (library[i].getAuthor().toLowerCase().contains(inputText.toLowerCase())){
	        				//System.out.println(library[i].getTitle());
	        				tempList.add(library[i]);
	        			}	
	        		}
	        		break;
        		
				case 2:
					for(int i = 0; i < library.length; i++){
						if ((library[i].getISBN().toLowerCase().contains(inputText.toLowerCase()))){
							//System.out.println(library[i].getTitle());
							tempList.add(library[i]);
						}
					}
					break;
	        	}
	        	Book[] tempArray = new Book[tempList.size()];
	        	for (int a = 0; a < tempArray.length; a++){
	        		tempArray[a] = tempList.get(a);
	        	}
	        	searching = tempArray;
	        	refreshSearch();
	        }	
		});  
		
		searchPanel.setLayout(null);
		searchPanel.setSize(400,25);
		searchPanel.setLocation(380,systemName.getY() + 28 - searchPanel.getHeight());

		searchingBox1.setSize(200,300);
		searchingBox1.setLocation(0,0);
		
		bookList.setLayout(null);
		bookList.setLayout(new GridLayout(0, 2));
		//for isbn, loop through book properties, 
		for (int i = 0; i < library.length; i++) {
			if (library[i].getIssued())
				continue;
			JButton label = new JButton(library[i].getTitle() + " By " + library[i].getAuthor());
			int order = i;
			label.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int a = JOptionPane.showConfirmDialog(f, "Would you like to borrow this book?", "Borrow Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (a == JOptionPane.OK_OPTION){
						library[order].setBorrower(curUser.name);
						borrowBook(library[order]);
					}
				}
			});
	        bookList.add(label);
	    }
	
		searchPanel.add(searchingBox);
		searchPanel.add(searchButton);
		searchPanel.add(searchOption);
		scrollBookList.setSize(searchPanel.getX() + searchPanel.getWidth(),550);
		scrollBookList.setLocation(systemName.getX(),systemName.getY() + systemName.getHeight());
		
		DefaultListModel<String> ownedBook = new DefaultListModel<>();
		ArrayList<Book> ownedBookObject = new ArrayList<Book>();
		
		for (int i = 0; i < borrowing.length; i++) { 
			if(curUser.name.equals(borrowing[i].getBorrower())){
				Date borrowDate = Date.from(Instant.ofEpochSecond(borrowing[i].getDateDue()));
				String borrowStatus = Instant.now().getEpochSecond() > Instant.ofEpochSecond(borrowing[i].getDateDue()).getEpochSecond() ? "OVERDUE" : "Reading";
				ownedBookObject.add(borrowing[i]);
				ownedBook.addElement("<html>Title: " + borrowing[i].getTitle()
						+ "<br>Author: " + borrowing[i].getAuthor()
						+ "<br>ISBN: " + borrowing[i].getISBN()
						+ "<br>Due Date: " + borrowDate
						+ "<br>Status: " + borrowStatus
						+ "<br> ");
				int order = i;
				
			}
	    }
		infoList = new JList<>(ownedBook);
		
		sInfoList = new JScrollPane(infoList);
		sInfoList.setSize(175,470);
		sInfoList.setLocation(scrollBookList.getX() + scrollBookList.getWidth() + 10, scrollBookList.getY());
		
		returnButton.setSize(175,70);
		returnButton.setLocation(810, 535);
		returnButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(borrowing.length > 0) {
					ownedBookObject.get(infoList.getSelectedIndex()).toString();
					returnBook(ownedBookObject.get(infoList.getSelectedIndex()));
					library = Book.createBookList();
					refreshMain();
					refreshScreen();
				}
				
		
			}
		});
		
		

		f.add(systemName);
		f.add(searchPanel);
		f.add(scrollBookList);
		f.add(sInfoList);
		f.add(returnButton);
		
		refreshScreen();
		
	}
	
	public static void adminWindow(){
		System.out.println("ADMIN ACCESS");
		systemName.setSize(600,35);
		systemName.setFont(mainWinf);
		systemName.setLocation(20,20);
		systemName.setText("Library Books System (ADMIN)");
		
		searchingBox.setSize(200,25);
		searchingBox.setLocation(0,0);
		
		searchButton.setText("+ Add Book By ISBN");
		searchButton.setSize(150,25);
		searchButton.setLocation(230,0);
		searchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String bookInfo;
				try {
					bookInfo = ISBN_API.getContent(searchingBox.getText());
					Book b = new Book(searchingBox.getText(), ISBN_API.getInfo(bookInfo, "title"), ISBN_API.getArrayInfo(bookInfo, "author_name"));
					b.addBook(b);
					System.out.println(b.getTitle() + " Added to the Library");
					library = Book.createBookList();
					refreshList();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		searchPanel.setLayout(null);
		searchPanel.setSize(400,25);
		searchPanel.setLocation(450,systemName.getY() + 28 - searchPanel.getHeight());

		searchingBox1.setSize(200,300);
		searchingBox1.setLocation(0,0);
		
		bookList.setLayout(null);
		bookList.setLayout(new GridLayout(0, 1));
		//for isbn, loop through book properties, 
		for (int i = 0; i < library.length; i++) { 
			JLabel label = new JLabel(library[i].getTitle() + " by " + library[i].getAuthor());
	        bookList.add(label);
	    }
	
		searchPanel.add(searchingBox);
		searchPanel.add(searchButton);
		
		scrollBookList.setSize(searchPanel.getX() + searchPanel.getWidth(),550);
		scrollBookList.setLocation(systemName.getX(),systemName.getY() + systemName.getHeight());
		
		f.add(systemName);
		f.add(searchPanel);
		f.add(scrollBookList);
		
		refreshList();
		refreshScreen();
	}
	
	public static void refreshList(){
		bookList.removeAll();
		for (int i = 0; i < library.length; i++) { 
			String status = library[i].getIssued()? "Taken" : "Available";
			JLabel label = new JLabel(library[i].getTitle() + " by " + library[i].getAuthor() + " - Status: " + status);
	        bookList.add(label);
	    }
		refreshScreen();
	}
	
	public static void refreshMain(){
		borrowing = Book.createBorrowList(curUser.name);
		bookList.removeAll();
		for (int i = 0; i < library.length; i++) {
			if (library[i].getIssued())
				continue;
			JButton label = new JButton(library[i].getTitle() + " By " + library[i].getAuthor());
			int order = i;
			label.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int a = JOptionPane.showConfirmDialog(f, "Would you like to borrow this book?", "Borrow Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (a == JOptionPane.OK_OPTION){
						library[order].setBorrower(curUser.name);
						borrowBook(library[order]);
					}
				}
			});
	        bookList.add(label);
	        
	    }
		
		
		f.remove(sInfoList);
		
		DefaultListModel<String> ownedBook = new DefaultListModel<>();
		ArrayList<Book> ownedBookObject = new ArrayList<Book>();
		for (int i = 0; i < borrowing.length; i++) { 
			if(curUser.name.equals(borrowing[i].getBorrower())){
				Date borrowDate = Date.from(Instant.ofEpochSecond(borrowing[i].getDateDue()));
				String borrowStatus = Instant.now().getEpochSecond() > Instant.ofEpochSecond(borrowing[i].getDateDue()).getEpochSecond() ? "OVERDUE" : "Reading";
				ownedBookObject.add(borrowing[i]);
				ownedBook.addElement("<html>Title: " + borrowing[i].getTitle()
						+ "<br>Author: " + borrowing[i].getAuthor()
						+ "<br>ISBN: " + borrowing[i].getISBN()
						+ "<br>Due Date: " + borrowDate
						+ "<br>Status: " + borrowStatus
						+ "<br> ");
			}
	    }
		infoList = new JList<>(ownedBook);
		sInfoList = new JScrollPane(infoList);
		sInfoList.setSize(175,470);
		sInfoList.setLocation(scrollBookList.getX() + scrollBookList.getWidth() + 10, scrollBookList.getY());
		f.add(sInfoList);
		
		returnButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(borrowing.length > 0) {
					ownedBookObject.get(infoList.getSelectedIndex()).toString();
					returnBook(ownedBookObject.get(infoList.getSelectedIndex()));
					library = Book.createBookList();
					refreshMain();
					refreshScreen();
				}
				
		
			}
		});
		refreshScreen();
	}
	
	public static boolean libHas(Book lib, Book[] fro){
		for (int j = 0; j < fro.length; j++){
			if (lib == fro[j])
				return true;
		}
		return false;
	}
	
	public static void refreshSearch(){
		bookList.removeAll();
		libSearch:
		for (int i = 0; i < library.length; i++) {
			if (library[i].getIssued() || (!libHas(library[i], searching)))
				continue libSearch;
			JButton label = new JButton(library[i].getTitle() + " By " + library[i].getAuthor());
			int order = i;
			label.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int a = JOptionPane.showConfirmDialog(f, "Would you like to borrow this book?", "Borrow Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (a == JOptionPane.OK_OPTION){
						library[order].setBorrower(curUser.name);
						borrowBook(library[order]);
					}
				}
			});
	        bookList.add(label);
	    }
		refreshScreen();
	}
	
	public static void refreshScreen(){
		f.setVisible(true);
		f.revalidate();
		f.repaint();
	}
	
	public static void borrowBook(Book b){
		Book.borrowBook(b, curUser.name);
		b.editBook(b);
		refreshMain();
	}
	
	public static void returnBook(Book b){
		Book.returnBook(b);
		b.editBook(b);
		refreshMain();
	}
}