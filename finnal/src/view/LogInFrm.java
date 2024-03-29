package view;


import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import finnal.Borrower;
import finnal.Borrower_Login;
import finnal.Borrower_Save;
import finnal.Manager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class LogInFrm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 001;
	private JPanel contentPane;
	private JTextField UserNameField;
	private JPasswordField UserNumField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInFrm frame = new LogInFrm();
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
	public LogInFrm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LogInFrm.class.getResource("/image/logo.png")));
		setTitle("登录界面");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(contentPane);
		setResizable(false);
		
		File Borrower_data=new File("Borrower");
		
		if(!Borrower_data.exists())
			{
			List<Borrower> Borrower_Data = new ArrayList<>();
			Borrower_Data.add(new Borrower("John","male",20180916,1));
			Borrower_Data.add(new Borrower("Mike","male",20180917,2));
			Borrower_Data.add(new Borrower("Ann","female",20180918,1));
			Borrower_Save B_Save=new Borrower_Save("Borrower");
			B_Save.Save(Borrower_Data);
			JLabel label = new JLabel("借书者数据不存在，已经初始化创建了默认数据！");
			label.setForeground(Color.RED);
			label.setFont(new Font("幼圆", Font.BOLD, 16));
			UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("幼圆", Font.BOLD, 15)));
			JOptionPane.showMessageDialog(null, label);
		}
		
		JLabel lblNewLabel = new JLabel("图书借阅系统");
		lblNewLabel.setIcon(new ImageIcon(LogInFrm.class.getResource("/image/logo.png")));
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		lblNewLabel.setBounds(115, 25, 227, 43);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("用户名:");
		lblNewLabel_1.setIcon(new ImageIcon(LogInFrm.class.getResource("/image/userName.png")));
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		lblNewLabel_1.setBounds(69, 109, 83, 27);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("密码 ：");
		lblNewLabel_2.setIcon(new ImageIcon(LogInFrm.class.getResource("/image/password.png")));
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.BOLD, 18));
		lblNewLabel_2.setBounds(69, 185, 83, 33);
		contentPane.add(lblNewLabel_2);
		
		UserNameField = new JTextField();
		UserNameField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		UserNameField.setBounds(166, 101, 193, 43);
		contentPane.add(UserNameField);
		UserNameField.setColumns(10);
		
		UserNumField = new JPasswordField();
		UserNumField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		UserNumField.setBounds(166, 180, 193, 43);
		contentPane.add(UserNumField);
		UserNumField.setColumns(10);
		
		JButton LogInBtn = new JButton("登录");
		LogInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(UserNumField.getPassword().equals("") ||
					UserNameField.getText().trim().equals("")){
					JLabel label = new JLabel("用户名或学号不能为空！");
					label.setForeground(Color.RED);
					label.setFont(new Font("幼圆", Font.BOLD, 16));
					UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("幼圆", Font.BOLD, 15)));
					JOptionPane.showMessageDialog(null, label);
					return;
				}
				Manager u = new Manager(UserNameField.getText().trim(),UserNumField.getText().trim());
				try {
					u = u.LogIn(u);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(u == null) {
					JLabel label = new JLabel("登录失败！");
					label.setForeground(Color.RED);
					label.setFont(new Font("幼圆", Font.BOLD, 16));
					UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("幼圆", Font.BOLD, 15)));
					JOptionPane.showMessageDialog(null, label);
				}
				else {
					dispose();
					MainFrm main= new MainFrm(u);
					main.setVisible(true);
				}
			}
		});
		UserNumField.addKeyListener(new ControlTheInput());
		LogInBtn.setIcon(new ImageIcon(LogInFrm.class.getResource("/image/login.png")));
		LogInBtn.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		LogInBtn.setBounds(93, 271, 113, 43);
		contentPane.add(LogInBtn);
		
		JButton ResetBtn = new JButton("重置");
		ResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				UserNameField.setText("");
				UserNumField.setText("");
			}
		});
		ResetBtn.setIcon(new ImageIcon(LogInFrm.class.getResource("/image/delete.png")));
		ResetBtn.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		ResetBtn.setBounds(259, 271, 113, 43);
		contentPane.add(ResetBtn);
	}
	private class ControlTheInput extends KeyAdapter {

		public void keyTyped(KeyEvent e) {
			String key="0123456789"+(char)8;
			if(key.indexOf(e.getKeyChar())<0){
				e.consume();//如果不是数字则取消
			}
		}
	}
}
