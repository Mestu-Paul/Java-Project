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
	private JTextField StringVaccin;
	private JTextField StringDate;

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
	String name,session,department,vaccinated,date,user_password="nopass";
	boolean verified=false;
	Vector v_name = new Vector();
	Vector v_session = new Vector();
	Vector v_dept = new Vector();
	Vector v_vaccin = new Vector();
	Vector v_date = new Vector();
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
			s=s+Replace((String)(v_dept.elementAt(i)),' ','$')+"\n"+Replace((String)(v_vaccin.elementAt(i)),' ','$')+"\n"+Replace((String)(v_date.elementAt(i)),' ','$')+"\n";
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
			v_vaccin.clear();v_date.clear();
			for(int i=0; i<temp.size(); i+=5) {
				String s=Replace((String)(temp.elementAt(i)),'$',' '); v_name.add(s);
				s=Replace((String)(temp.elementAt(i+1)),'$',' '); v_session.add(s);
				s=Replace((String)(temp.elementAt(i+2)),'$',' ');v_dept.add(s);
				s=Replace((String)(temp.elementAt(i+3)),'$',' ');v_vaccin.add(s);
				s=Replace((String)(temp.elementAt(i+4)),'$',' ');v_date.add(s);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void fill_up() {
		StringName.setText("");StringSession.setText("");StringDept.setText("");
		StringVaccin.setText("");StringDate.setText("");
		StringName.requestFocus();
	}
	private void ReadInput() throws IOException {
		Take_Input_From_DataBase("DataBase.txt");
		name = StringName.getText();		
		session = StringSession.getText();	
		department = StringDept.getText();	
		vaccinated = StringVaccin.getText();
		vaccinated = vaccinated.toUpperCase();
		date = StringDate.getText();
		if(vaccinated.equals("YES")==false && vaccinated.equals("NO")==false) {
			JOptionPane.showMessageDialog(null, "Vaccinated must be only YES or NO\n");
			return;
		}
		if(vaccinated.equals("YES")==false)date="N/A";
		else if(date.length()==0) {
			JOptionPane.showMessageDialog(null, "Write your vaccination date\n");
			return;
		}
		v_dept.add(department);v_session.add(session);v_name.add(name);
		v_vaccin.add(vaccinated);v_date.add(date);
		Store_Input_To_DataBase("DataBase.txt");
		fill_up();
		if(verified)DisplayTable();
	}
	private void DisplayTable() {
		DefaultTableModel Df = (DefaultTableModel) table.getModel();
		Vector V = new Vector();
		V.add("Name");V.add("Session");V.add("Department");V.add("Vaccinated");V.add("Date");
		Df.setRowCount(0);
		Df.addRow(V);
		Take_Input_From_DataBase("DataBase.txt");
		for(int i=0; i<v_name.size(); i++) {
			Vector v = new Vector();
			String s = (String) v_name.elementAt(i);v.add(s);
			s = (String) v_session.elementAt(i);v.add(s);
			s = (String) v_dept.elementAt(i);v.add(s);
			s = (String) v_vaccin.elementAt(i);v.add(s);
			s = (String) v_date.elementAt(i);v.add(s);
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
		
		JLabel __3 = new JLabel("Vaccinated:");
		__3.setBounds(22, 110, 92, 15);
		panel.add(__3);
		
		JLabel __4 = new JLabel("Date:");
		__4.setBounds(22, 140, 92, 15);
		panel.add(__4);
		
		StringSession = new JTextField();
		StringSession.setColumns(10);
		StringSession.setBounds(140, 43, 136, 19);
		panel.add(StringSession);
		
		StringDept = new JTextField();
		StringDept.setColumns(10);
		StringDept.setBounds(140, 78, 136, 19);
		panel.add(StringDept);
		
		StringVaccin = new JTextField();
		StringVaccin.setColumns(10);
		StringVaccin.setBounds(140, 108, 136, 19);
		panel.add(StringVaccin);
		
		StringDate = new JTextField();
		StringDate.setColumns(10);
		StringDate.setBounds(140, 138, 136, 19);
		panel.add(StringDate);
		
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
		add.setBounds(22, 198, 73, 40);
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
				vaccinated = StringVaccin.getText();
				vaccinated = vaccinated.toUpperCase();
				if(vaccinated.equals("YES")==false && vaccinated.equals("NO")==false) {
					JOptionPane.showMessageDialog(null, "Vaccinated must be only YES or NO\n");
					return;
				}
				v_dept.set(index,department);v_session.set(index,session);v_name.set(index,name);
				v_vaccin.set(index,vaccinated);date = StringDate.getText();
				if(vaccinated.equals("YES")==false)date="N/A";
				else if(date.length()==0) {
					JOptionPane.showMessageDialog(null, "Write your vaccination date\n");
				}
				v_date.set(index,date);
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
		btnEdit.setBounds(107, 198, 73, 40);
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
				System.out.println("index 1 "+index+" "+v_name.size());
				index=index-1;
				System.out.println("index 1 "+index+" "+v_name.size());
				for(int i=index+1; i<v_name.size(); i++,index++) {
					v_name.set(index,v_name.elementAt(i));
					v_session.set(index,v_session.elementAt(i));
					v_dept.set(index,v_dept.elementAt(i));
					v_vaccin.set(index,v_vaccin.elementAt(i));
					v_date.set(index,v_date.elementAt(i));
				}
				index = v_name.size()-1;
				//System.out.println("index "+index+" "+v_name.size());
				v_name.remove(index);v_session.remove(index);v_dept.remove(index);
				v_vaccin.remove(index);v_date.remove(index);
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
		btnDelete.setBounds(192, 198, 84, 40);
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
		showData.setBounds(55, 250, 183, 25);
		panel.add(showData);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel df = (DefaultTableModel) table.getModel();
				int index = table.getSelectedRow();
				System.out.println("mouse index "+index);
				StringName.setText(df.getValueAt(index,0).toString());
				StringSession.setText(df.getValueAt(index,1).toString());
				StringDept.setText(df.getValueAt(index,2).toString());
				StringVaccin.setText(df.getValueAt(index,3).toString());
				StringDate.setText(df.getValueAt(index,4).toString());
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Session", "Department", "Vaccinated", "Date"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		table.setColumnSelectionAllowed(true);
		table.setBounds(312, 48, 434, 279);
		frame.getContentPane().add(table);
		fill_up();
	}
}
