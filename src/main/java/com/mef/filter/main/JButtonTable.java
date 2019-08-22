package com.mef.filter.main;

import com.mef.filter.main.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
/**
 * @author Naganath_Pawar
 *
 */
public class JButtonTable implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	private JButton button;
	public JButtonTable(String text) {
		button = new JButton(text);
	}

	public JButton getButton() {
		return button;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(UIManager.getColor("Button.background"));
		}
		button.setText((value == null) ? "" : value.toString());
		return button;
	}
}

class ButtonEditor extends DefaultCellEditor {

	@Autowired
	transient AppProperties appProperties;

	protected JButton button;

	private String label;

	private boolean isPushed;

	public static final Logger logger = LoggerFactory.getLogger(ButtonEditor.class);

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
				int choice = JOptionPane.showOptionDialog(null, "Do you want to open this file", appProperties.getDownload(),
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				// interpret the user's choice
				if (choice == JOptionPane.YES_OPTION) {
					File myFile = new File(label);
					Desktop.getDesktop().open(myFile);
				}
			} catch (Exception e) {
				logger.error("getCellEditorValue",e);
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
