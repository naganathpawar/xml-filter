package com.mef.filter.main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mef.filter.main.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mef.filter.main.model.FileDetails;
import com.mef.filter.main.service.FileReader;
import com.mef.filter.main.service.ReadXML;

/**
 * @author naganathpawar
 *
 */
@Service
public class SearchFrame{
	@Autowired
	private FileReader fileReader;
	@Autowired
	ReadXML readXML;
	@Autowired
	AppProperties appProperties;

	JFrame frame ;
	JTable table;
	DefaultTableModel model;
	Font font;
	JTextField textId;
	JTextField textPath;
	JLabel lbl;
	JLabel lblPath;
	// create JButtons to add the action
	JButton btnAdd ;
	JButton btnClr;
	JButton btnCsv ;
	JButton btnDeviceCsv;
	JButton btnBack ;
	JLabel lblCount ;
	JScrollPane pane;
	JButton btnBrowse;
	Object[] row;
	JLabel label;
	/**
	 * 
	 */
	@SuppressWarnings("static-access")
	public void generateJTable() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		table = new JTable();
		Object[] columns = { "File Name", appProperties.getDownload() };
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.setBackground(Color.CYAN);
		table.setForeground(Color.black);
		font = new Font("", 1, 10);
		table.setFont(font);
		table.setRowHeight(30);

		// create JTextFields to hold the value
		textId = new JTextField();
		textPath = new JTextField();
		lbl = new JLabel("Search Count : ");
		lblPath = new JLabel("Folder Path : ");
		// create JButtons to add the action
		btnAdd = new JButton("Search");
		btnClr = new JButton("Clear");
		btnCsv = new JButton("Create csv");
		btnDeviceCsv = new JButton("Create Device csv");
		btnBack = new JButton("Back");

		lblCount = new JLabel("");
		lblCount.setBounds(150, screenSize.height/2+110, 100, 25);
		frame.add(lblCount);

		textId.setBounds(20, screenSize.height/2+20, 200, 50);
		lbl.setBounds(20, screenSize.height/2+100, 100, 50);

		lblPath.setBounds(20, screenSize.height/2+160, 100, 50);
		frame.add(lblPath);
		textPath.setBounds(150, screenSize.height/2+160, 200, 50);
		frame.add(textPath);

		btnAdd.setBounds(250, screenSize.height/2+20, 100, 50);
		btnClr.setBounds(355, screenSize.height/2+20, 100, 50);
		btnCsv.setBounds(460, screenSize.height/2+20, 100, 50);
		btnDeviceCsv.setBounds(565, screenSize.height/2+20, 180, 50);
		btnBack.setBounds(750, screenSize.height/2+20, 100, 50);
		btnBack.setBackground(Color.RED);
		btnBack.setOpaque(true);

		// create JScrollPane
		 pane = new JScrollPane(table);
		pane.setBounds(0, 0, screenSize.width, screenSize.height/2);

		 btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(360, screenSize.height/2+160, 100, 50);
		frame.add(btnBrowse);

		frame.setLayout(null);

		frame.add(pane);
		frame.add(textId);
		frame.add(lbl);
		frame.add(btnAdd);
		frame.add(btnClr);
		frame.add(btnCsv);
		frame.add(btnDeviceCsv);
		frame.add(btnBack);

		row = new Object[3];
		label = new JLabel("");
		label.setBounds(30, screenSize.height/2+69, 300, 25);
		label.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
		label.setForeground(Color.RED);
		label.setVisible(true);
		frame.add(label);
		// =================================================================================
		/**************** Column download file event **************************/
		// =================================================================================
		searchBundles();

		// =================================================================================
		/********************** Clear textField event *********************************/
		// =================================================================================
		btnClr.addActionListener((ActionEvent e) -> {
			model.setRowCount(0);
			lblCount.setText("0");
		});

		// =================================================================================
		/************************
		 * Create CSV for Plans event
		 *******************************/
		// =================================================================================
		createBundleCSV();

		// =================================================================================
		/********************** Create CSV for device *********************************/
		// =================================================================================
		createDeviceCSV();

		// =================================================================================
		/************************** Browse file event *****************************/
		// =================================================================================
		browseFile();
		
		btnBack.addActionListener((ActionEvent e) ->
			frame.dispose()
		);
		
		frame.setSize(screenSize.width, screenSize.height);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new FrameListener());
		frame.setVisible(true);
		frame.setTitle("Canonical Search Tool");

	}

	/**
	 * @param path
	 */
	private void openJoptionPane(String path) {
		int choice = JOptionPane.showOptionDialog(null, "CSV File has been created. DO you want to open?", "Open",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (choice == JOptionPane.YES_OPTION) {
			java.io.File myFile = new java.io.File(path);
			try {
				Desktop.getDesktop().open(myFile);
			} catch (IOException ex) {
				JOptionPane.showInputDialog(ex);
			}
		}
	}

	/**
	 *
	 */
	private void searchBundles() {
		btnAdd.addActionListener ( ( ActionEvent e ) -> {

			try {
				if ( textPath.getText ( ) != null && ! textPath.getText ( ).isEmpty ( ) && textId.getText ( ) != null
						&& ! textId.getText ( ).isEmpty ( ) ) {
					label.setText ( "Wait... It's Fetching Data From Canonical ." );
					model.setRowCount ( 0 );

					JOptionPane.showMessageDialog ( frame , "Wait... It's Updating Details into Canonical " );
					List < FileDetails > fileList = readXML.createAndGetFileByIdFromCanonical ( textPath.getText ( ) ,
							textId.getText ( ) );
					for ( int i = 0 ; i < fileList.size ( ) ; i++ ) {
						row[ 0 ] = fileList.get ( i ).getName ( );
						row[ 1 ] = fileList.get ( i ).getPath ( );
						model.addRow ( row );

						table.getColumn ( appProperties.getDownload ( ) ).setCellRenderer ( new JButtonTable ( ) );
						table.getColumn ( appProperties.getDownload ( ) ).setCellEditor ( new ButtonEditor ( new JCheckBox ( ) ) );
					}
					lblCount.setText ( fileList.size ( ) + "" );
					label.setText ( "" );
				} else {
					JOptionPane.showMessageDialog ( frame , appProperties.getValidateMSG ( ) );
				}
			} catch ( Exception ex ) {
				JOptionPane.showInputDialog ( ex );
			}

		} );
	}

	/**
	 *
	 */
	private void createBundleCSV() {
		btnCsv.addActionListener((ActionEvent e) -> {
			try {
				if (textPath.getText() != null && !textPath.getText().isEmpty() && textId.getText() != null
						&& !textId.getText().isEmpty()) {
					label.setText(appProperties.getCsvCreatingMSG());
					JOptionPane.showMessageDialog(frame,  appProperties.getCsvCreatingMSG());
					openJoptionPane(fileReader.createCSVFileForPlan(textPath.getText(), textId.getText()));
					label.setText("");
				}else {
					JOptionPane.showMessageDialog(frame,appProperties.getValidateMSG());
				}
			} catch (Exception ex) {
				JOptionPane.showInputDialog(ex);
			}
		});
	}

	/**
	 *
	 */
	private void createDeviceCSV(){
		btnDeviceCsv.addActionListener((ActionEvent e) -> {
			try {
				if (textPath.getText() != null && !textPath.getText().isEmpty() && textId.getText() != null
						&& !textId.getText().isEmpty()) {
					label.setText(appProperties.getCsvCreatingMSG());
					JOptionPane.showMessageDialog(frame, appProperties.getCsvCreatingMSG());
					openJoptionPane(fileReader.createCSVFileForDevice(textPath.getText(), textId.getText()));
					label.setText("");
				}else {
					JOptionPane.showMessageDialog(frame,  appProperties.getValidateMSG());
				}

			} catch (Exception ex) {
				JOptionPane.showInputDialog(ex);
			}
		});
	}

	/**
	 *
	 */
	private void browseFile() {
		btnBrowse.addActionListener( this::actionPerformed );
	}

	/**
	 * @param e
	 */
	private void actionPerformed ( ActionEvent e ) {
		JFileChooser fileChooser = new JFileChooser ( );
		fileChooser.setFileSelectionMode ( JFileChooser.FILES_AND_DIRECTORIES );
		fileChooser.setAcceptAllFileFilterUsed ( false );
		int rVal = fileChooser.showOpenDialog ( null );
		if ( rVal == JFileChooser.APPROVE_OPTION ) {
			textPath.setText ( fileChooser.getSelectedFile ( ).toString ( ) );
		}
	}
}
