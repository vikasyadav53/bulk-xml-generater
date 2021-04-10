package com.vikas.desktopapplications.bulkxmlgenerator;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;

public class UIGenerator  extends JFrame implements ActionListener {
	
	private Container c;
	private JLabel name;
	private JTextField tname;
	private JButton sub;
	private JButton reset;
	private JLabel res;
	private String filePath;
	UIGenerator(){}
	
	public void createFileChooser() {
		
		setTitle("Registration Form");
		setBounds(300, 90, 700, 400);
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
		
		sub = new JButton("Validate Schema");
		sub.setFont(new Font("Arial", Font.PLAIN, 15));
		sub.setSize(100, 20);
		sub.setLocation(150, 150);
		sub.addActionListener(this);
		c.add(sub);

		reset = new JButton("Upload Schema");
		reset.setFont(new Font("Arial", Font.PLAIN, 15));
		reset.setSize(100, 20);
		reset.setLocation(270, 150);
		reset.addActionListener(this);
		c.add(reset);
		
		res = new JLabel("");
		res.setFont(new Font("Arial", Font.PLAIN, 20));
		res.setSize(500, 25);
		res.setLocation(100, 200);
		c.add(res);
		
		setVisible(true);
    }
	
	
	public void actionPerformed(ActionEvent evt)
    {    	
        String com = evt.getActionCommand();
  
        if (com.equals("Validate Schema")) {    
        	if (filePath != null && !filePath.isEmpty()) {
        		Boolean isValid = filePath.endsWith("xsd");
        		if (!isValid) {
        			res.setText("Invalid File Extension");
        		} else {
        			generateClassesFromXSD();
        		}
        	}
        }
        else {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION){
            	filePath = j.getSelectedFile().getAbsolutePath();
                tname.setText(j.getSelectedFile().getAbsolutePath());
                res.setText("File uploaded successfully");
                
                if (filePath != null && !filePath.isEmpty()) {
            		Boolean isValid = filePath.endsWith("xsd");
            		if (!isValid) {
            			res.setText("Invalid File Extension");
            		} else {
                        File source = new File(filePath);
                        File dest = new File("C:/Users/Vikas Yadav/Desktop/cmd_gen/schema.xsd");
                        
                        try {
        					FileUtils.copyFile(source, dest);
        				} catch (IOException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
            			generateClassesFromXSD();
            		}
            	}

                
            }
            else {
            	tname.setText("");
                res.setText("the user cancelled the operation");
            }
        }
    }
	
	private static void generateClassesFromXSD() {
    	try {
    		Process process = Runtime.getRuntime().exec("cmd /c build.bat", null, new File("C:\\Users\\Vikas Yadav\\Desktop\\cmd_gen"));
    	 
    	    BufferedReader reader = new BufferedReader(
    	            new InputStreamReader(process.getInputStream()));
    	    String line;
    	    while ((line = reader.readLine()) != null) {
    	        System.out.println(line);
    	    }
    	 
    	    reader.close();
    	 
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }

}
