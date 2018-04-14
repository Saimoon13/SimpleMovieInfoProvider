package edu.java.view;

import edu.java.connect.ProjectDAO;
import edu.java.connect.ProjectDAOImple;
import edu.java.domain.Rate;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class MyFrame_Rate extends JFrame {

	private ProjectDAO dao;

	private JPanel contentPane;
	private JTextField textName;
	private Main parent;
	private JFrame frame;

	public MyFrame_Rate(Main parent) {
		this.parent = parent;
		dao = ProjectDAOImple.getInstance();
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 445, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Please, Input text within 30 characters");
		label.setBounds(10, 166, 153, 15);
		contentPane.add(label);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 20, 421, 139);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Review", null, panel, null);
		panel.setLayout(null);
		
		JTextArea textCt = new JTextArea();
		textCt.setBounds(0, 0, 416, 120);
		textCt.setLineWrap(true);
		
		panel.add(textCt);
		
		JLabel label_1 = new JLabel("Name");
		label_1.setBounds(244, 1, 38, 33);
		contentPane.add(label_1);
		
		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				String name = parent.getUserName();
				String contents = textCt.getText();
				if(contents.length()>=30) {
					JOptionPane.showMessageDialog(frame, "Please, Input text within 30 characters");
					return;
				}
				String title = parent.getDbtitleMain();
				
				Rate r = new Rate(name, contents, title);

				try {
					int result = dao.insertRate(r);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(frame, "Input succeed");

				try {
					parent.initializeTable();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				dispose();
			}
		});
		button.setBounds(323, 162, 97, 23);
		contentPane.add(button);
		
		textName = new JTextField();
		textName.setEditable(false);
		textName.setHorizontalAlignment(SwingConstants.CENTER);
		textName.setBounds(294, 6, 122, 23);
		contentPane.add(textName);
		textName.setColumns(10);
		textName.setText(parent.getUserName());
	}

	public static void showMyDialog(Main window) {
		MyFrame_Rate dlg = new MyFrame_Rate(window);
		dlg.setVisible(true);
	}

}
