package package1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
public class Frame1 {

	private JFrame frame;
	private JTextField StringName;
	private JTextField StringSession;
	private JTextField StringDept;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	String name,session,department,user_password="nopass";
	boolean verified=false;
	Vector v_name = new Vector();
	Vector v_session = new Vector();
	Vector v_dept = new Vector();
	private JTable table;
	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private String Replace(String var, char cur, char rep) {
		String tmp="";
		for(int i=0; i<var.length(); i++) {
			char ch = var.charAt(i);
			if(ch==cur)ch=rep;
			tmp = tmp+ch;
		}
		return tmp;
	}
	private void Store_Input_To_DataBase(String Store) throws IOException {
		File file = new File(Store);
		FileWriter writer = new FileWriter(file);
		String s="";
		for(int i=0; i<v_name.size(); i++) {
			s=s+Replace((String)(v_name.elementAt(i)),' ','$')+"\n"+Replace((String)(v_session.elementAt(i)),' ','$')+"\n";
			s=s+Replace((String)(v_dept.elementAt(i)),' ','$')+"\n";
		}
		writer.write(s);
		writer.close();
	}
	private void Take_Input_From_DataBase(String Store) {
		File file = new File(Store);
		try {
			Scanner scan = new Scanner(file);
			Vector temp = new Vector();
			while(scan.hasNext()) {
				temp.add((String)(scan.next()));
			}
			v_name.clear();v_session.clear();v_dept.clear();
			for(int i=0; i<temp.size(); i+=3) {
				String s=Replace((String)(temp.elementAt(i)),'$',' '); v_name.add(s);
				s=Replace((String)(temp.elementAt(i+1)),'$',' '); v_session.add(s);
				s=Replace((String)(temp.elementAt(i+2)),'$',' ');v_dept.add(s);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void fill_up() {
		StringName.setText("");StringSession.setText("");StringDept.setText("");
		StringName.requestFocus();
	}
	private void ReadInput() throws IOException {
		Take_Input_From_DataBase("DataBase.txt");
		name = StringName.getText();		
		session = StringSession.getText();	
		department = StringDept.getText();	
		v_dept.add(department);v_session.add(session);v_name.add(name);
		Store_Input_To_DataBase("DataBase.txt");
		fill_up();
		if(verified)DisplayTable();
	}
	private void DisplayTable() {
		DefaultTableModel Df = (DefaultTableModel) table.getModel();
		Vector V = new Vector();
		V.add("Name");V.add("Session");V.add("Department");
		Df.setRowCount(0);
		Df.addRow(V);
		Take_Input_From_DataBase("DataBase.txt");
		for(int i=0; i<v_name.size(); i++) {
			Vector v = new Vector();
			String s = (String) v_name.elementAt(i);v.add(s);
			s = (String) v_session.elementAt(i);v.add(s);
			s = (String) v_dept.elementAt(i);v.add(s);
			Df.addRow(v);
		}
		table.setModel(Df);
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 768, 399);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(180, 12, 66, 15);
		frame.getContentPane().add(lblTitle);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 45, 288, 309);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel _ = new JLabel("Name:");
		_.setBounds(22, 14, 92, 15);
		panel.add(_);
		
		StringName = new JTextField();
		StringName.setBounds(140, 12, 136, 19);
		panel.add(StringName);
		StringName.setColumns(10);
		
		JLabel __1 = new JLabel("Session:");
		__1.setBounds(22, 47, 92, 15);
		panel.add(__1);
		
		JLabel __2 = new JLabel("Department:");
		__2.setBounds(22, 80, 92, 15);
		panel.add(__2);
		
		StringSession = new JTextField();
		StringSession.setColumns(10);
		StringSession.setBounds(140, 43, 136, 19);
		panel.add(StringSession);
		
		StringDept = new JTextField();
		StringDept.setColumns(10);
		StringDept.setBounds(140, 78, 136, 19);
		panel.add(StringDept);
		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ReadInput();
					JOptionPane.showMessageDialog(null,"Your information added\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add.setBounds(34, 122, 73, 40);
		panel.add(add);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verified==false) {
					JOptionPane.showMessageDialog(null,"You are not verified user.\nTo verify click \"Show Data Tabel\"");
					return ;
				}
				DefaultTableModel df = (DefaultTableModel) table.getModel();
				int index = table.getSelectedRow();
				index-=1;
				name = StringName.getText();		
				session = StringSession.getText();	
				department = StringDept.getText();	
				v_dept.set(index,department);v_session.set(index,session);v_name.set(index,name);
				try {
					Store_Input_To_DataBase("DataBase.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				fill_up();
				DisplayTable();
				JOptionPane.showMessageDialog(null,"Your information updated\n");
			}
		});
		btnEdit.setBounds(119, 122, 73, 40);
		panel.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verified==false) {
					JOptionPane.showMessageDialog(null,"You are not verified user.\nTo verify click \"Show Data Tabel\"");
					return ;
				}
				DefaultTableModel df = (DefaultTableModel) table.getModel();
				int index = table.getSelectedRow();
				index=index-1;
				for(int i=index+1; i<v_name.size(); i++,index++) {
					v_name.set(index,v_name.elementAt(i));
					v_session.set(index,v_session.elementAt(i));
					v_dept.set(index,v_dept.elementAt(i));
				}
				index = v_name.size()-1;
				v_name.remove(index);v_session.remove(index);v_dept.remove(index);
				try {
					Store_Input_To_DataBase("DataBase.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				fill_up();
				try {
					Store_Input_To_DataBase("DataBase.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				DisplayTable();
				JOptionPane.showMessageDialog(null,"Your information deleted\n");
			}
		});
		btnDelete.setBounds(204, 122, 84, 40);
		panel.add(btnDelete);
		
		JButton showData = new JButton("Show Data Table");
		showData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verified) {
					DisplayTable();
					return;
				}
				String takePassword = JOptionPane.showInputDialog(null,"Enter user password:\n");
				if(takePassword.equals(user_password)) {
					verified=true;
					DisplayTable();
				}
				else {
					JOptionPane.showMessageDialog(null,"Password does not match");
					return;
				}
			}
		});
		showData.setBounds(70, 174, 183, 25);
		panel.add(showData);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel df = (DefaultTableModel) table.getModel();
				int index = table.getSelectedRow();
				StringName.setText(df.getValueAt(index,0).toString());
				StringSession.setText(df.getValueAt(index,1).toString());
				StringDept.setText(df.getValueAt(index,2).toString());
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		
		table.setColumnSelectionAllowed(true);
		table.setBounds(312, 48, 434, 279);
		frame.getContentPane().add(table);
		fill_up();
	}
}
