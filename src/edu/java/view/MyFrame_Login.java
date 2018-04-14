package edu.java.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.connect.ProjectDAO;
import edu.java.connect.ProjectDAOImple;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.Map;


public class MyFrame_Login extends JFrame {

	private JPanel contentPane;
	private JTextField textEmail;
	private JPasswordField textPw;
	private JButton btnSignup;
	private ProjectDAO dao;
	private JFrame frame;
	private Main parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame_Login frame = new MyFrame_Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame_Login() {
		dao = ProjectDAOImple.getInstance();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 246, 147);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("E-Mail:");
		label.setBounds(12, 10, 48, 27);
		contentPane.add(label);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(62, 13, 157, 21);
		contentPane.add(textEmail);
		
		JLabel label_1 = new JLabel("PW:");
		label_1.setBounds(32, 47, 28, 27);
		contentPane.add(label_1);
		
		textPw = new JPasswordField();
		textPw.setBounds(62, 50, 157, 21);
		contentPane.add(textPw);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String email = textEmail.getText();
				char[] pw = textPw.getPassword();
				String rpw = String.valueOf(pw);
				
				if(email.equals("")|| rpw.equals("")) {
					JOptionPane.showMessageDialog(frame, "Please enter Login/Password.");
					return;
				}

				Map<String, String> hashMap;
				try {
					hashMap = dao.selectMemberByEmail(email);

					String mname, memail, mpw;

					System.out.println(hashMap.get("mname"));

					if(hashMap.get("mname") != null) {
						mname = hashMap.get("mname");
						memail = hashMap.get("memail");
						mpw = hashMap.get("mpw");
						if(memail.equals(email) && rpw.equals(mpw)) {
							JOptionPane.showMessageDialog(frame, "You are signed in.");
							parent.enableRateButton();
							parent.unVisibleLoginButton();
							parent.visibleLogoutButton();
							parent.setUserName(mname);
							dispose();
						} else {
							JOptionPane.showMessageDialog(frame, "The password is not correct.");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Please enter your ID and password.");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(122, 81, 97, 23);
		contentPane.add(btnLogin);
		
		btnSignup = new JButton("SignUp");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				MyFrame_signUp.showMyDialog();
			}
		});
		btnSignup.setBounds(12, 81, 97, 23);
		contentPane.add(btnSignup);
	}

	public static void showMyDialog(Main window) {
		MyFrame_Login dlg = new MyFrame_Login();
		dlg.parent = window;
		dlg.setVisible(true);
	}
}
