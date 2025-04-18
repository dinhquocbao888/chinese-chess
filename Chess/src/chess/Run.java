package chess;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Run {
	public static void main(String[] args)
	{
		JButton startButton = new JButton("play");
		JFrame frame = new JFrame("BÀN CỜ TƯỚNG");
		JFrame button = new JFrame("Bàn Cờ Tướng");
		JPanel panel = new JPanel();
		panel.add(startButton);
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(new Table());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);//Câu lệnh dùng để canh giữa màn hình
				button.setVisible(false);
				frame.setVisible(true);
				
			}
		});
		
		button.add(panel,BorderLayout.CENTER);
		button.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button.pack();
		button.setLocationRelativeTo(null);//Câu lệnh dùng để canh giữa màn hình
		button.setVisible(true);
		
	}
}
