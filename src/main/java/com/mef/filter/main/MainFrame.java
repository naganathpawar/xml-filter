package com.mef.filter.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author naganathpawar
 *
 */
@Component
public class MainFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;

	@Autowired
	SearchFrame searchFrame;
	@Autowired
	CanonicalFrame canonicalFrame;

	public static final Logger logger = LoggerFactory.getLogger(MainFrame.class);


	/**
	 *
	 */
	public MainFrame() {
		frame = new JFrame();
		JPanel jPanel = new JPanel();
		JButton l2 = new JButton("SEARCH");
		JButton l3 = new JButton("MODIFY");

		l2.setBounds(80, 120, 100, 50);
		l3.setBounds(220, 120, 100, 50);
		jPanel.setBounds(0, 0, 400, 200);
		l3.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
		l3.setBackground(Color.YELLOW);
		l3.setForeground(Color.BLUE);
		l2.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
		l2.setBackground(Color.YELLOW);
		l2.setForeground(Color.BLUE);
		URL iconUrl = getClass().getResource("canonical.png");
		Image myPicture = Toolkit.getDefaultToolkit().getImage(iconUrl);
		ImagePanel imagePanel = new ImagePanel(myPicture);
		jPanel.add(imagePanel);
		jPanel.setLayout(null);
		frame.add(jPanel);
		imagePanel.add(l2);
		imagePanel.add(l3);
		frame.setTitle("Canonical Search Engine");
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new FrameListener());
		frame.setVisible(true);
		l3.addActionListener((ActionEvent e) ->
				canonicalFrame.createMEFModifyForm()
		);

		l2.addActionListener((ActionEvent e) ->
				searchFrame.generateJTable()
		);

	}

}
