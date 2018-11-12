package com.uwindsor.notekeeper.ui;

import com.uwindsor.notekeeper.model.Note;

import javax.swing.*;
import java.awt.*;

public class NoteListItem extends JPanel implements ListCellRenderer<Note> { 
    private final JLabel lblTitle, lblDate;

    /**
     * Create the panel.
     */
    NoteListItem() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lblTitle = new JLabel("Title");
        add(lblTitle);

        lblDate = new JLabel("Date");
        add(lblDate);
        setBackground(Color.white);
    }

    private void setValues(String title, String date) { 
    	lblTitle.setText(title);
    	lblDate.setText(date); 
    }
    
    private void setSelected(boolean isSelected) { 
    	if(isSelected) setBackground(Color.BLUE);
else setBackground(Color.WHITE);
}

    @Override
    public Component getListCellRendererComponent(JList<? extends Note> list, Note value, int index, boolean isSelected, boolean cellHasFocus) {
    	setValues(value.getFileName(), value.getCreationDate().toString());
    	setSelected(isSelected);
    	return this;
    }
}