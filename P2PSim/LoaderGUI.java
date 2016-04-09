package P2PSim;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoaderGUI extends JFrame {

	private JPanel contentPane;
	public JProgressBar progressBar;
	public JTextPane textPane;
	private JScrollPane scrollPane;
	private JLabel lblLog;
	public JButton btnCancel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoaderGUI frame = new LoaderGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoaderGUI() {

		setTitle("Loading Progress");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 411, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		progressBar = new JProgressBar();
		contentPane.add(progressBar, BorderLayout.NORTH);
		
		btnCancel = new JButton("Cancel");

		contentPane.add(btnCancel, BorderLayout.SOUTH);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		lblLog = new JLabel("Log");
		lblLog.setFont(new Font("Tahoma", Font.BOLD, 11));
		scrollPane.setColumnHeaderView(lblLog);
		
		setVisible(true);		// These two statements increase performances significanatly.
		this.setLocationRelativeTo(null);	//works only after pack() or resize() - SetVisible invokes this...
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void addMsg(String in){
		textPane.setText(textPane.getText() + "\n" + in);
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	}
}
