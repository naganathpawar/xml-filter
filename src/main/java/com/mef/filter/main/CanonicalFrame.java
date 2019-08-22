package com.mef.filter.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mef.filter.main.service.ReadXML;

/**
 * @author naganathpawar
 *
 */
@Service
public class CanonicalFrame {
	@Autowired
	ReadXML readXML;
	public static final Logger logger = LoggerFactory.getLogger(CanonicalFrame.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createMEFModifyForm() {
		JFrame frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// create JScrollPane
		JTextArea txtArea = new JTextArea();
		JScrollPane pane = new JScrollPane(txtArea);
		pane.setBounds(0, 0, screenSize.width / 2 + 200, screenSize.height);

		JPanel jPanel = new JPanel();
		jPanel.setBounds((screenSize.width / 2) + 210, 0, 300, 600);

		JLabel lblPath = new JLabel("Folder Path : ");
		lblPath.setBounds((screenSize.width / 2) + 220, 30, 100, 35);
		jPanel.add(lblPath);

		final JTextField textPath = new JTextField();
		textPath.setBounds((screenSize.width / 2) + 320, 30, 200, 35);
		jPanel.add(textPath);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds((screenSize.width / 2) + 520, 30, 100, 35);
		jPanel.add(btnBrowse);

		btnBrowse.addActionListener((ActionEvent e) -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileChooser.setAcceptAllFileFilterUsed(false);
			int rVal = fileChooser.showOpenDialog(null);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				textPath.setText(fileChooser.getSelectedFile().toString());

			}
		});

		String[] types = { "commercialBundle", "commercialProduct" };

		JLabel lblType = new JLabel("Select Type : ");
		lblType.setBounds((screenSize.width / 2) + 220, 75, 100, 35);
		jPanel.add(lblType);
		JComboBox typeList = new JComboBox(types);
		typeList.setBounds((screenSize.width / 2) + 320, 75, 200, 35);
		jPanel.add(typeList);

		JLabel lblId = new JLabel("Enter ID : ");
		lblId.setBounds((screenSize.width / 2) + 220, 120, 100, 35);
		jPanel.add(lblId);

		final JTextField textId = new JTextField();
		textId.setBounds((screenSize.width / 2) + 320, 120, 200, 35);
		jPanel.add(textId);

		JButton btnGetBundle = new JButton("Search Id");
		btnGetBundle.setBounds((screenSize.width / 2) + 520, 120, 100, 35);
		jPanel.add(btnGetBundle);

		JLabel label = new JLabel("Please Enter All Fields");
		label.setBounds((screenSize.width / 2) + 220, 165, 300, 35);
		label.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
		label.setForeground(Color.RED);
		label.setVisible(true);
		jPanel.add(label);

		btnGetBundle.addActionListener((ActionEvent e) -> {
			try {
				if (!textPath.getText().isEmpty() && !textId.getText().isEmpty()) {
					label.setText("Wait... It's Fetching Data From Canonical .");
					JOptionPane.showMessageDialog(frame, "Wait... It's Fetching Data From Canonical .");
					Element element = readXML.getNodeDetailsById(textPath.getText(), textId.getText(),
							typeList.getSelectedItem().toString());
					txtArea.setText(element.asXML());
					label.setText("Done");
				} else {
					JOptionPane.showMessageDialog(frame, "Please Select File and Enter Id");
				}
				
			} catch (Exception ex) {
				logger.error("ERROR IN: createMEFModifyForm ",ex);
			}
		});

		JButton btnUpdate = new JButton("Update Now");
		btnUpdate.setBounds((screenSize.width / 2) + 320, 200, 100, 35);
		jPanel.add(btnUpdate);
		btnUpdate.addActionListener((ActionEvent e) -> {
			try {
				if (!textPath.getText().isEmpty() && !textId.getText().isEmpty() && !txtArea.getText().isEmpty()) {
					label.setText("Wait... It's Updating Details into Canonical.");
					JOptionPane.showMessageDialog(frame, "Wait... It's Updating Details into Canonical.");
					readXML.replaceNode(txtArea.getText(), textPath.getText(), textId.getText(),
							typeList.getSelectedItem().toString());
					JOptionPane.showMessageDialog(frame, "Details Updated successfully");
					label.setText("Details Updated");
				} else {
					JOptionPane.showMessageDialog(frame, "Please Select File and Enter Id");
				}
			} catch (Exception ex) {
				logger.error("ERROR IN: createMEFModifyForm ",ex);
			}
		});

		JButton btnBack = new JButton("Back");
		btnBack.setBounds((screenSize.width / 2) + 440, 200, 100, 35);
		btnBack.setBackground(Color.RED);
		btnBack.setOpaque(true);
		jPanel.add(btnBack);
		btnBack.addActionListener((ActionEvent e) ->frame.dispose());
		URL iconUrl = getClass().getResource("overview.png");
		Image myPicture = Toolkit.getDefaultToolkit().getImage(iconUrl);
		ImagePanel imagePanel = new ImagePanel(myPicture);
		imagePanel.setBounds((screenSize.width / 2) + 220, 255, 400, 300);
		jPanel.add(imagePanel);

		jPanel.setLayout(null);
		frame.add(pane);
		frame.add(jPanel);
		frame.setSize(screenSize.width, screenSize.height);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new FrameListener());
		frame.setVisible(true);
		frame.setTitle("Canonical Update Tool");

	}

}

@SuppressWarnings("serial")
class ImagePanel extends JPanel {

	private transient Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(400, 300);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}