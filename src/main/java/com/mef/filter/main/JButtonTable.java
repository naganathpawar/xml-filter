package com.mef.filter.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
/**
 * @author Naganath_Pawar
 *
 */

class JButtonTable  implements TableCellRenderer {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JButton jButton=new JButton();
	public JButtonTable() {
		jButton.setOpaque(true);
	}


	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {
		if (isSelected) {
			jButton.setForeground(table.getSelectionForeground());
			jButton.setBackground(table.getSelectionBackground());
		} else {
			jButton.setForeground(table.getForeground());
			jButton.setBackground(UIManager.getColor("Button.background"));
		}
		jButton.setText((value == null) ? "" : value.toString());
		return jButton;
	}
}

/**
 * @version 1.0 11/09/98
 */

class ButtonEditor extends DefaultCellEditor {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger logger = LoggerFactory.getLogger(ButtonEditor.class);
	protected JButton button;

	private String label;

	private boolean isPushed;

	public ButtonEditor(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener((ActionEvent e) ->
				fireEditingStopped()
		);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		if (isPushed) {
			try {
				int choice = JOptionPane.showOptionDialog(null, "Do you want to open this file", "Download",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (choice == JOptionPane.YES_OPTION) {
					File myFile = new File(label);
					Desktop.getDesktop().open(myFile);
				}
			} catch (Exception e) {
				logger.error("getCellEditorValue", e);
			}
		}
		isPushed = false;
		return label;
	}
@Override
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}


}
class FrameListener extends WindowAdapter {
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}
