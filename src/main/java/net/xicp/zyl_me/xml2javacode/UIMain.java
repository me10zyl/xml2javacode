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
import javax.swing.JFileChooser;
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
	private JTextField descriptionFileTextField;

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
		setBounds(100, 100, 503, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		filePathTextFeild = new JTextField();
		filePathTextFeild.setBounds(90, 9, 200, 24);
		contentPane.add(filePathTextFeild);
		filePathTextFeild.setColumns(10);
		JLabel label = new JLabel("\u6587\u4EF6\u5730\u5740");
		label.setBounds(28, 12, 72, 18);
		contentPane.add(label);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(27, 114, 436, 139);
		contentPane.add(scrollPane_1);
		final JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		JPanel panel = new JPanel();
		panel.setBounds(27, 298, 449, 88);
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
		btnTree.setBounds(386, 5, 85, 71);
		contentPane.add(btnTree);
		final JCheckBox depthcheckBox = new JCheckBox("\u662F\u5426\u6253\u5370\u6DF1\u5EA6");
		depthcheckBox.setSelected(true);
		depthcheckBox.setBounds(330, 262, 133, 27);
		contentPane.add(depthcheckBox);
		final JCheckBox isPrintAttributecheckBox = new JCheckBox("\u662F\u5426\u6DFB\u52A0\u5C5E\u6027\u4F5C\u4E3A\u5B57\u6BB5");
		isPrintAttributecheckBox.setBounds(124, 262, 200, 27);
		contentPane.add(isPrintAttributecheckBox);
		JButton scanbutton = new JButton("\u6D4F\u89C8..");
		scanbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int ret = fileChooser.showOpenDialog(UIMain.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					filePathTextFeild.setText(file.getAbsolutePath());
				}
			}
		});
		scanbutton.setBounds(297, 8, 85, 27);
		contentPane.add(scanbutton);
		JLabel label_1 = new JLabel("\u63CF\u8FF0\u6587\u4EF6\u5730\u5740(\u53EF\u9009)");
		label_1.setBounds(27, 53, 153, 18);
		contentPane.add(label_1);
		descriptionFileTextField = new JTextField();
		descriptionFileTextField.setColumns(10);
		descriptionFileTextField.setBounds(177, 50, 118, 24);
		contentPane.add(descriptionFileTextField);
		JButton scanBtn2 = new JButton("\u6D4F\u89C8..");
		scanBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int ret = fileChooser.showOpenDialog(UIMain.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					descriptionFileTextField.setText(file.getAbsolutePath());
				}
			}
		});
		scanBtn2.setBounds(303, 48, 79, 27);
		contentPane.add(scanBtn2);
		JButton descriptionFileFormatDescription = new JButton("\u63CF\u8FF0\u6587\u4EF6\u683C\u5F0F\u8BF4\u660E");
		descriptionFileFormatDescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lineSeparator = System.getProperty("line.separator", "\n");  
				JOptionPane.showMessageDialog(UIMain.this, "字段描述文件格式："+lineSeparator+"${字段}\\s+${字段描述}\\s+.+"+lineSeparator+"如:"+lineSeparator+"code 客户编码"+lineSeparator+"name 客户名称 其他无关紧要的文字"+lineSeparator+"postcode 邮政编码 xxxxxx"+lineSeparator+"复制后的文本将为"+lineSeparator+"/** 客户编码 **/"+lineSeparator+"private String code;"+lineSeparator+"/** 客户名称 **/"+lineSeparator+"private String name;"+lineSeparator+"/** 邮政编码 **/"+lineSeparator+"private String postcode;"+lineSeparator+"Caution:添加描述文件后请重新Tree!");
			}
		});
		descriptionFileFormatDescription.setBounds(177, 84, 286, 27);
		contentPane.add(descriptionFileFormatDescription);
		btnTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = filePathTextFeild.getText();
				final CodeGenerator codeGenerator = new CodeGenerator();
				try {
					final File file = new File(text);
					String xml2javacodeSimple = codeGenerator.xml2javacodeSimple(file, depthcheckBox.isSelected(), isPrintAttributecheckBox.isSelected());
					textArea.setText(xml2javacodeSimple);
					int maxDepth = codeGenerator.getMaxDepth(file);
					if (maxDepth > 0) {
						buttonsPanel.removeAll();
					}
					for (int i = 0; i <= maxDepth; i++) {
						JButton btn = new JButton("深度" + i + "元素");
						btn.setActionCommand("" + i);
						buttonsPanel.add(btn);
						btn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								try {
									String descriptionFilePath = descriptionFileTextField.getText();
									File descriptionFile = null;
									if (!"".equals(descriptionFilePath) && descriptionFilePath != null) {
										descriptionFile = new File(descriptionFilePath);
									}
									String text = codeGenerator.xml2javacodeByDepth(file, Integer.parseInt(e.getActionCommand()), isPrintAttributecheckBox.isSelected(), descriptionFile);
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
					if (maxDepth > 0) {
						JButton btn = new JButton("所有深度元素");
						btn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								try {
									String descriptionFilePath = descriptionFileTextField.getText();
									File descriptionFile = null;
									if (!"".equals(descriptionFilePath) && descriptionFilePath != null) {
										descriptionFile = new File(descriptionFilePath);
									}
									String text = codeGenerator.xml2javacode(Utils.readFile(file), "/*",isPrintAttributecheckBox.isSelected(), descriptionFile);
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
