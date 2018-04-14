package edu.java.view;

import edu.java.connect.ProjectDAO;
import edu.java.connect.ProjectDAOImple;
import edu.java.domain.Member;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class MyFrame_signUp extends JFrame {

	private JFrame frame; 
	private JPanel contentPane;
	private JTextField textEmail;
	private JPasswordField textPw;
	private JTextField textName;

	private ProjectDAO dao;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame_signUp frame = new MyFrame_signUp();
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
	public MyFrame_signUp() {
		dao = ProjectDAOImple.getInstance();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 246, 192);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(12, 10, 48, 27);
		contentPane.add(lblEmail);
		
		JLabel lblPw = new JLabel("PW:");
		lblPw.setBounds(32, 47, 28, 27);
		contentPane.add(lblPw);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(16, 79, 38, 27);
		contentPane.add(lblName);
		
		textEmail = new JTextField();
		textEmail.setBounds(62, 13, 157, 21);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		textPw = new JPasswordField();
		textPw.setBounds(62, 50, 157, 21);
		contentPane.add(textPw);
		
		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(62, 84, 157, 21);
		contentPane.add(textName);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					insertMember();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btnConfirm.setBounds(122, 123, 97, 23);
		contentPane.add(btnConfirm);
	}
	
	private void insertMember() throws SQLException {
		String name = textName.getText();
		String email = textEmail.getText();
		char[] pw = textPw.getPassword();
		
		if(name.equals("")||email.equals("")||pw.length == 0) {
			JOptionPane.showMessageDialog(frame, "회원정보를 입력해주세요.");
			return;
		}
		
		Member m = new Member(name,email,pw);
		int temp = dao.selectByEmail(m);
		if(temp == 1) {
			textName.setText("");
			textEmail.setText("");
			textPw.setText("");
			return;
		}
		int result = dao.insert(m);
		JOptionPane.showMessageDialog(frame, "회원가입이 완료되었습니다.");
		dispose();
		
	}
	
	public static void showMyDialog() {
		MyFrame_signUp dlg = new MyFrame_signUp();
		dlg.setVisible(true);
	}
}
