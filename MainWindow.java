import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MainWindow {

	public static void main(String[] args) {
		JFrame mWin=new JFrame("Library Books Management System");//creating instance of JFrame  
		mWin.setLayout(null);
		mWin.setResizable(false);
		JLabel systemName = new JLabel();
		
		JTextField searchingBox = new JTextField(16);
		JButton searchButton = new JButton("Search");
		
		
		
		systemName.setSize(600,35);
		systemName.setFont(new Font("Times New Roman", Font.BOLD, 28));
		systemName.setLocation(20,20);
		systemName.setText("Library Books System");
		
		searchingBox.setSize(200,25);
		searchingBox.setLocation(0,0);
		
		searchButton.setSize(100,25);
		searchButton.setLocation(230,0);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(null);
		searchPanel.setSize(330,25);
		searchPanel.setLocation(450,systemName.getY() + systemName.getHeight() - searchPanel.getHeight());
		System.out.println(searchPanel.getWidth() + searchPanel.getX());
		
		JPanel bookList = new JPanel();
		bookList.setLayout(new GridLayout(0,3));
		bookList.setSize(330,25);
		bookList.setLocation(450,systemName.getY() + systemName.getHeight() - searchPanel.getHeight()+300);
		
		bookList.add(searchButton);
		bookList.add(searchingBox);
		JScrollPane scrollableBookList = new JScrollPane(bookList);
		scrollableBookList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		

		mWin.add(systemName);
		searchPanel.add(searchingBox);
		searchPanel.add(searchButton);
		
		

		mWin.add(searchPanel);
		mWin.add(bookList);  
		
		
		
		 
	
		mWin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mWin.setSize(1000,650);//400 width and 500 height  
		mWin.setLocationRelativeTo(null);
		mWin.setVisible(true);
		

	}

}


