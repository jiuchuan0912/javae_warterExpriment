package view;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import finnal.Book;
import finnal.Borrow;
import finnal.Borrow_list;
import finnal.Read_Data;
import finnal.Save_Data;
import finnal.UpdateBinFile;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;
/**
 * 还书
 * @author LoveBeforT
 *
 */
public class ReturnBook extends JInternalFrame {
	private JTextField StuNum;
	private JTextField BookIsbn;
	private JTextField BorrowTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnBook frame = new ReturnBook();
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
	public ReturnBook() {
		setClosable(true);
		setTitle("办理借书手续");
		setFrameIcon(new ImageIcon(ReturnBook.class.getResource("/image/logo.png")));
		setBounds(100, 100, 540, 300);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("借阅人学号：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label.setBounds(26, 55, 101, 28);
		getContentPane().add(label);
		
		StuNum = new JTextField();
		StuNum.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		StuNum.setColumns(10);
		StuNum.setBounds(131, 50, 118, 36);
		getContentPane().add(StuNum);
		
		JLabel lblisbn = new JLabel("图书ISBN：");
		lblisbn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblisbn.setBounds(266, 52, 88, 28);
		getContentPane().add(lblisbn);
		
		BookIsbn = new JTextField();
		BookIsbn.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		BookIsbn.setColumns(10);
		BookIsbn.setBounds(368, 50, 118, 36);
		getContentPane().add(BookIsbn);
		
		JButton button = new JButton("确定");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(StuNum.getText().trim().equals("") ||
						BookIsbn.getText().trim().equals("") ||
						BorrowTime.getText().trim().equals("")
						){
						JLabel label = new JLabel("输入框不能为空！");
						label.setForeground(Color.RED);
						label.setFont(new Font("幼圆", Font.BOLD, 16));
						UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("幼圆", Font.BOLD, 15)));
						JOptionPane.showMessageDialog(null, label);
						return;
					}
				Borrow_list bDao = new Borrow_list();
				Borrow b = new Borrow(Integer.parseInt(StuNum.getText().trim())
						,Integer.parseInt(BorrowTime.getText().trim())
						,BookIsbn.getText().trim());
				List<Borrow> list = bDao.GetBorrowList();
				if(bDao.ExistBor(list,b)) {
					Read_Data br = new Read_Data("Books");
					List<Book> Booklist = br.Read_Books();
					Save_Data s = new Save_Data("Books");
					for(int i = 0;i<Booklist.size();i++) {
						if(Booklist.get(i).getISBN().equals(BookIsbn.getText().trim()))
							Booklist.get(i).setSurplus(Booklist.get(i).getSurplus()+1);
					}
					s.Save(Booklist);
					JLabel label = new JLabel("还书成功！");
					list = bDao.Remove(list, b);
					bDao.WriteList(list);
					label.setForeground(Color.RED); 
					label.setFont(new Font("幼圆", Font.BOLD, 16));
					UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("幼圆", Font.BOLD, 15)));
					JOptionPane.showMessageDialog(null, label);

					UpdateBinFile up = new UpdateBinFile();
					try {
						up.UpdateBin();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					return;
				}
				JLabel label = new JLabel("借书信息不存在！");
				label.setForeground(Color.RED);
				label.setFont(new Font("幼圆", Font.BOLD, 16));
				UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("幼圆", Font.BOLD, 15)));
				JOptionPane.showMessageDialog(null, label);
				return;
			}
		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		button.setBounds(192, 171, 128, 50);
		getContentPane().add(button);
		
		JLabel label_1 = new JLabel("借阅时间：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setBounds(141, 113, 101, 28);
		getContentPane().add(label_1);
		
		BorrowTime = new JTextField();
		BorrowTime.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		BorrowTime.setColumns(10);
		BorrowTime.setBounds(246, 108, 118, 36);
		getContentPane().add(BorrowTime);

	}
}
