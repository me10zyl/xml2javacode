package net.xicp.zyl_me.xml2javacode;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.dom4j.DocumentException;
import java.awt.FlowLayout;

public class UIMain extends JFrame {
	private JPanel contentPane;
	private JTextField filePathTextFeild;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIMain frame = new UIMain();
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
	public UIMain() {
		setTitle("XML2JavaCodeGenerator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 519, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		filePathTextFeild = new JTextField();
		filePathTextFeild.setBounds(145, 5, 243, 24);
		contentPane.add(filePathTextFeild);
		filePathTextFeild.setColumns(10);
		JLabel label = new JLabel("\u6587\u4EF6\u5730\u5740");
		label.setBounds(27, 8, 72, 18);
		contentPane.add(label);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(27, 42, 436, 189);
		contentPane.add(scrollPane_1);
		final JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		JPanel panel = new JPanel();
		panel.setBounds(27, 256, 449, 88);
		contentPane.add(panel);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u590D\u5236\u2193", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		final JPanel buttonsPanel = new JPanel();
		scrollPane.setViewportView(buttonsPanel);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JButton button_1 = new JButton("\u6DF1\u5EA60\u5143\u7D20");
		buttonsPanel.add(button_1);
		JButton btnTree = new JButton("Tree");
		btnTree.setBounds(402, 4, 85, 27);
		contentPane.add(btnTree);
		final JCheckBox depthcheckBox = new JCheckBox("\u662F\u5426\u6253\u5370\u6DF1\u5EA6");
		depthcheckBox.setSelected(true);
		depthcheckBox.setBounds(330, 232, 133, 27);
		contentPane.add(depthcheckBox);
		
		final JCheckBox isPrintAttributecheckBox = new JCheckBox("\u662F\u5426\u6DFB\u52A0\u5C5E\u6027\u4F5C\u4E3A\u5B57\u6BB5");
		isPrintAttributecheckBox.setBounds(124, 232, 200, 27);
		contentPane.add(isPrintAttributecheckBox);
		btnTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = filePathTextFeild.getText();
				final CodeGenerator codeGenerator = new CodeGenerator();
				try {
					final File file = new File(text);
					String xml2javacodeSimple = codeGenerator.xml2javacodeSimple(file, depthcheckBox.isSelected(),isPrintAttributecheckBox.isSelected());
					textArea.setText(xml2javacodeSimple);
					int maxDepth = codeGenerator.getMaxDepth(file);
					if(maxDepth > 0)
					{
						buttonsPanel.removeAll();
					}
					for (int i = 0; i <= maxDepth; i++) {
						JButton btn = new JButton("深度" + i + "元素");
						btn.setActionCommand(""+i);
						buttonsPanel.add(btn);
						btn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								try {
									String text = codeGenerator.xml2javacodeByDepth(file, Integer.parseInt(e.getActionCommand()),isPrintAttributecheckBox.isSelected());
									StringSelection stsel = new StringSelection(text);
									Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
								} catch (NumberFormatException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
									e1.printStackTrace();
								} catch (DocumentException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
									e1.printStackTrace();
								} catch (ElementNotFoundException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
									e1.printStackTrace();
								}
							}
						});
					}
					if(maxDepth > 0)
					{
						JButton btn = new JButton("所有深度元素");
						btn.addActionListener(new ActionListener(){

							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								try {
									String text = codeGenerator.xml2javacode(file,isPrintAttributecheckBox.isSelected());
									StringSelection stsel = new StringSelection(text);
									Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
								} catch (DocumentException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
								} catch (ElementNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
								}
							}
							
						});
						buttonsPanel.add(btn);
					}
					buttonsPanel.validate();
					buttonsPanel.repaint();
					UIMain.this.validate();
					UIMain.this.repaint();
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
					e1.printStackTrace();
				} catch (ElementNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(UIMain.this, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		this.setLocationRelativeTo(null);
	}
}
