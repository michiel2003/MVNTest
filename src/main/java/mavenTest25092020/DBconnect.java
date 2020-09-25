package mavenTest25092020;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class DBconnect {
	
	public static void main(String[] args) {
		DBconnect exec = new DBconnect();
		exec.frame();
	}

	private static List<String> getDBinfo(int columnindex, String command) {
		try {
			List<String> dbitems = new ArrayList<String>();
			Connection dbconnect = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\mdecap\\AppData\\Roaming\\DBeaverData\\workspace6\\.metadata\\sample-database-sqlite-1\\Chinook.db");
			PreparedStatement prepstate = dbconnect.prepareStatement(command);
			prepstate.execute();
			ResultSet result = prepstate.getResultSet();
			while(result.next()) {
				String text = result.getString(columnindex);
				dbitems.add(text);
			}
			return dbitems;
		}catch(Exception e) {
			System.out.println("an error ocurred");
		}
		return null;
	}
	
	public void frame() {
		try {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		DefaultListModel listmodel = new DefaultListModel();
		JList list2 = new JList(listmodel);
		JList list1 = new JList(getDBinfo(2, "select * from Artist order by name desc").toArray());
		JScrollPane scrollList1 = new JScrollPane(list1);
		frame.add(panel);
		panel.add(scrollList1);
		panel.add(list2);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		list1.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				listmodel.clear();
				listmodel.addAll(getDBinfo(1, "SELECT Album.Title, Artist.ArtistId from Artist join Album on Album.ArtistId = Artist.ArtistId WHERE Artist.name like '" + list1.getSelectedValue() + "' ORDER by Name ASC;"));
			}
		});
		}
		catch(Exception e) {
			System.out.println("error occured");
		}
	}
	
	

}
