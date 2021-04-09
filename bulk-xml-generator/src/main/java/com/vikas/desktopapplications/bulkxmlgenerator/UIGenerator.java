package com.vikas.desktopapplications.bulkxmlgenerator;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public class UIGenerator  extends JFrame implements ActionListener {
	
	private Container c;
	private JLabel name;
	private JTextField tname;
	private JButton sub;
	private JButton reset;
	private JLabel res;
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
        }
        else {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION){
                tname.setText(j.getSelectedFile().getAbsolutePath());
                res.setText("File uploaded successfully");
            }
            else {
            	tname.setText("");
                res.setText("the user cancelled the operation");
            }
        }
    }

}
