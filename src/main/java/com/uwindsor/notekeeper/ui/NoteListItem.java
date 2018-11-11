package com.uwindsor.notekeeper.ui;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;

public class NoteListItem extends JPanel{

	/**
	 * Create the panel.
	 */
	public NoteListItem() {
		setLayout(null);
		
		JLabel lblTitle = new JLabel("New label");
		lblTitle.setBounds(6, 6, 274, 22);
		add(lblTitle);
		
		JLabel lblDate = new JLabel("New label");
		lblDate.setBounds(164, 40, 116, 16);
		add(lblDate);

	}
}
