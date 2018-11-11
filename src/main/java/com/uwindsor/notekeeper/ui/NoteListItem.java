package com.uwindsor.notekeeper.ui;

import com.uwindsor.notekeeper.model.Note;

import javax.swing.*;
import java.awt.*;

public class NoteListItem extends JPanel implements ListCellRenderer<Note>{
	/**
	 * Create the panel.
	 */
	NoteListItem() {
		setLayout(null);
		
		JLabel lblTitle = new JLabel("New label");
		//lblTitle.setBounds(6, 6, 274, 22);
		add(lblTitle);
		
		JLabel lblDate = new JLabel("New label");
		//lblDate.setBounds(164, 40, 116, 16);
		add(lblDate);

	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Note> list, Note value, int index, boolean isSelected, boolean cellHasFocus) {
		return this;
	}
}
