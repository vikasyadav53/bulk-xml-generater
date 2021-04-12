package com.vikas.desktopapplications.bulkxmlgenerator;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;

import com.vikas.desktopapplications.bulkxmlgenerator.schemas.Employee;
import com.vikas.desktopapplications.bulkxmlgenerator.schemas.ObjectFactory;

public class UIGenerator extends JFrame implements ActionListener {

	private Container c;
	private JLabel name;
	private JLabel rootname;
	private JLabel outputlogs;
	private JTextField tname;
	private JTextField roottname;
	private JButton sub;
	private JButton reset;
	private JLabel res;
	private String filePath;
	JTextArea textArea;
	JScrollPane scrollableTextArea;
	Employee returnType;
	String objectTypeName;
	Integer counter=0;

	UIGenerator() {
	}

	private static final String BATCH_FILE_PATH = "src/main/resources/";

	public void createFileChooser() {

		setTitle("Builk XML Generator");
		setBounds(300, 90, 700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		c = getContentPane();
		c.setLayout(null);

		name = new JLabel("Uploded File");
		name.setFont(new Font("Arial", Font.PLAIN, 20));
		name.setSize(150, 20);
		name.setLocation(100, 100);
		c.add(name);

		tname = new JTextField();
		tname.setFont(new Font("Arial", Font.PLAIN, 15));
		tname.setSize(290, 20);
		tname.setLocation(270, 100);
		c.add(tname);

		rootname = new JLabel("XML Counts");
		rootname.setFont(new Font("Arial", Font.PLAIN, 20));
		rootname.setSize(150, 20);
		rootname.setLocation(100, 150);
		c.add(rootname);

		roottname = new JTextField();
		roottname.setFont(new Font("Arial", Font.PLAIN, 15));
		roottname.setSize(290, 20);
		roottname.setLocation(270, 150);
		c.add(roottname);
		
		sub = new JButton("Validate Schema");
		sub.setFont(new Font("Arial", Font.PLAIN, 15));
		sub.setSize(100, 20);
		sub.setLocation(150, 200);
		sub.addActionListener(this);
		c.add(sub);

		reset = new JButton("Upload Schema");
		reset.setFont(new Font("Arial", Font.PLAIN, 15));
		reset.setSize(100, 20);
		reset.setLocation(270, 200);
		reset.addActionListener(this);
		c.add(reset);

		res = new JLabel("");
		res.setFont(new Font("Arial", Font.PLAIN, 20));
		res.setSize(500, 25);
		res.setLocation(100, 250);
		c.add(res);

		outputlogs = new JLabel("Output Logs");
		outputlogs.setFont(new Font("Arial", Font.PLAIN, 20));
		outputlogs.setSize(150, 20);
		outputlogs.setLocation(100, 300);
		c.add(outputlogs);

		textArea = new JTextArea(580, 175);
		textArea.setFont(new Font("Arial", Font.PLAIN, 15));

		scrollableTextArea = new JScrollPane(textArea);
		scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setSize(600, 200);
		scrollableTextArea.setLocation(50, 350);
		c.add(scrollableTextArea);

		setVisible(true);
	}

	public synchronized void actionPerformed(ActionEvent evt) {
		String com = evt.getActionCommand();

		if (com.equals("Validate Schema")) {
			if (filePath != null && !filePath.isEmpty()) {
				Boolean isValid = filePath.endsWith("xsd") && null != roottname.getText() && !roottname.getText().isEmpty();
				if (!isValid) {
					res.setText("Invalid File Extension");
				} else {
					StringBuffer output = generateClassesFromXSD();
					output.append("\n");
					try {
						String counterS = roottname.getText();
						counter= Integer.valueOf(counterS);
						manageThreads();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int r = j.showOpenDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) {
				filePath = j.getSelectedFile().getAbsolutePath();
				tname.setText(j.getSelectedFile().getAbsolutePath());
				res.setText("File uploaded successfully");

				if (filePath != null && !filePath.isEmpty()) {
					Boolean isValid = filePath.endsWith("xsd");
					if (!isValid) {
						res.setText("Invalid File Extension");
					} else {
						File source = new File(filePath);
						// File dest = new File("C:/Users/Vikas Yadav/Desktop/cmd_gen/schema.xsd");
						File dest = new File("src/main/resources/schema.xsd");

						try {
							FileUtils.copyFile(source, dest);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			} else {
				tname.setText("");
				res.setText("the user cancelled the operation");
			}
		}
	}
	
	public Integer getCounter() {
		return counter;
	}

	private StringBuffer generateClassesFromXSD() {
		try {
			// Process process = Runtime.getRuntime().exec("cmd /c build.bat", null, new
			// File("C:\\Users\\Vikas Yadav\\Desktop\\cmd_gen"));
			Process process = Runtime.getRuntime().exec("cmd /c build.bat", null, new File(BATCH_FILE_PATH));
			StringBuffer output = new StringBuffer("");

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + " \n");
			}

			reader.close();
			return output;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void manageThreads() {
		Integer counter = this.getCounter();
    	Runtime runtime = Runtime.getRuntime();
    	//int noOfProcessor =runtime.availableProcessors();
    	int noOfProcessor =20000;
    	System.out.println("No of Processor -> " + noOfProcessor);
    	Thread[][] producers = new Thread[noOfProcessor][2];
    	for (int i =0; i<noOfProcessor; i++) {
    		RandomObjectFiller r = new RandomObjectFiller();
    		ObjectXMLGeneratorService oxgService = new ObjectXMLGeneratorService(false, r);
    		XMLGenerator xg = new XMLGenerator(oxgService);
    		ObjectGenerator og = new ObjectGenerator(oxgService);
    		producers[i][0]=new Thread(og);
    		producers[i][1]=new Thread(xg);
    		producers[i][0].start();
    		producers[i][1].start();
    	}
    	
    	for (int i =0; i<noOfProcessor; i++) {
    		try {
    			producers[i][0].join();
				producers[i][1].join();
				System.out.println("File generation complete");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
	}

}
