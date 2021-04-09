package com.vikas.desktopapplications.bulkxmlgenerator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class UIGenerator  implements ActionListener{
	
	static JLabel l;
	UIGenerator(){}
	
	public static void createFileChooser() {
        JFrame f = new JFrame("Upload Schema File.");
        f.setSize(400, 400);
        f.setVisible(true);
  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        l = new JLabel("no file selected");
        	
        JPanel gui = new JPanel(new BorderLayout());
        
        final JPanel gridButton = new JPanel(new GridLayout(0,1,10,10));
        
        gridButton.add(l);
        
        JButton button1 = new JButton("validate");
        JButton button2 = new JButton("open");
         
        UIGenerator f1 = new UIGenerator();
        
        button1.addActionListener(f1);
        button2.addActionListener(f1);
        
        gridButton.add(button1);
        gridButton.add(button2);
        
        JPanel gridConstrain = new JPanel(new BorderLayout());
        gridConstrain.add(gridButton, BorderLayout.NORTH);
        
        gui.add(gridConstrain, BorderLayout.CENTER);
        
        f.add(gui);
  
        f.show();
    }
	
	
	public void actionPerformed(ActionEvent evt)
    {    	
        String com = evt.getActionCommand();
  
        if (com.equals("save")) {        	
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());            
            int r = j.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION){
                l.setText(j.getSelectedFile().getAbsolutePath());
            }else
                l.setText("the user cancelled the operation");
        }
        else {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION){
                l.setText(j.getSelectedFile().getAbsolutePath());
            }
            else
                l.setText("the user cancelled the operation");
        }
    }

}
