import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.metal.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

public class TextEditor extends JFrame implements ActionListener {
  
  JTextArea textArea;
  JScrollPane verticalScrollPane;
	JLabel fontLabel;
  JSpinner fontSizeSpinner;
	JButton fontColorButton; 
	JComboBox fontBox;
  JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JRadioButton theme1;
	JRadioButton theme2;
	JRadioButton theme3;

	JMenuBar menuBar;
	JMenu fileMenu, editorMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	JMenuItem aboutItem;
	
	@SuppressWarnings("unchecked")
  TextEditor() {
    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Highland Editor");
    this.setSize(500, 500);
    this.setLayout(new FlowLayout());
    this.setLocationRelativeTo(null);

		try {
			
			//Themes
			//javax.swing.plaf.nimbus.NimbusLookAndFeel
			//com.sun.java.swing.plaf.motif.MotifLookAndFeel
			//com.sun.java.swing.plaf.gtk.GTKLookAndFeel

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			MetalLookAndFeel.setCurrentTheme(new OceanTheme());

		} catch (Exception e) {

			System.out.println("Error initializing theme.");

		}

    textArea = new JTextArea();
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setFont(new Font("SansSerif", Font.PLAIN, 18));
    
    verticalScrollPane = new JScrollPane(textArea);
    verticalScrollPane.setPreferredSize(new Dimension(450, 450));
    verticalScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		fontLabel = new JLabel("Font Size: ");
    
    fontSizeSpinner = new JSpinner();
    fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
    fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override 
			public void stateChanged(ChangeEvent e) {

				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));

			}

		});

		fontColorButton = new JButton("Text Color");
		fontColorButton.addActionListener(this);

		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("SansSerif");

		// Menu Bar

			menuBar = new JMenuBar();
			fileMenu = new JMenu("File");
			editorMenu = new JMenu("Highland Editor");
			openItem = new JMenuItem("Open");
			saveItem = new JMenuItem("Save");
			exitItem = new JMenuItem("Exit");
			aboutItem = new JMenuItem("About");

			fileMenu.setMnemonic(KeyEvent.VK_F);
			editorMenu.setMnemonic(KeyEvent.VK_E);
			openItem.setMnemonic(KeyEvent.VK_O);
			saveItem.setMnemonic(KeyEvent.VK_S);
			exitItem.setMnemonic(KeyEvent.VK_E);
			aboutItem.setMnemonic(KeyEvent.VK_A);

			openItem.addActionListener(this);
			saveItem.addActionListener(this);
			exitItem.addActionListener(this);
			aboutItem.addActionListener(this);

			fileMenu.add(openItem);
			fileMenu.add(saveItem);
			fileMenu.add(exitItem);
			editorMenu.add(aboutItem);
			menuBar.add(fileMenu);
			menuBar.add(editorMenu);

		// /Menu Bar

		this.setJMenuBar(menuBar);
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(fontBox);
    this.add(verticalScrollPane);
    this.setVisible(true);
    
  }
  
  @Override public void actionPerformed(ActionEvent e) {
    
    if (e.getSource() == fontColorButton) {

			JColorChooser colorChooser = new JColorChooser();

			Color color = colorChooser.showDialog(null, "Choose a Color", Color.black);

			textArea.setForeground(color);

		}

		if (e.getSource() == fontBox) {

			textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));

		}

		if (e.getSource() == openItem) {

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("C:\\"));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			fileChooser.setFileFilter(filter);

			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {

				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;

				try {

					fileIn = new Scanner(file);
					if (file.isFile()) {

						while(fileIn.hasNextLine()) {

							String line = fileIn.nextLine() + "\n";
							textArea.append(line);

						}

					}

				} catch (FileNotFoundException e1) {

					e1.printStackTrace();

				} finally {

					fileIn.close();

				}

			}

		}    

		if (e.getSource() == saveItem) {

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("C:\\"));

			int response = fileChooser.showSaveDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) {

				File file;
				PrintWriter fileOut = null;

				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {

					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());

				} catch (FileNotFoundException e2) {

					e2.printStackTrace();

				} finally {

					fileOut.close();

				}

			}
			
		}    

		if (e.getSource() == aboutItem) {

			JOptionPane.showMessageDialog(null, "Author: Highland Editor \n Version: 1.0 \n Open Source: https://github.com/Highland-OS/Highland-Editor", "About JEditor", JOptionPane.INFORMATION_MESSAGE);

		}

		if (e.getSource() == exitItem) {

			System.exit(0);
			
		}    
    
  }
  
}
