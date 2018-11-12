package com.uwindsor.notekeeper.ui;

import com.uwindsor.notekeeper.model.Note;

import javax.swing.*;
import java.awt.*;

public class NoteListItem extends JPanel implements ListCellRenderer<Note> { 
    private final JLabel lblTitle, lblDate, lblEncrypted;
    private final ImageIcon icon = new ImageIcon(getClass().getResource("/lock.png"));

    /**
     * Create the panel.
     */
    NoteListItem() {

		setLayout(new BorderLayout());

		JPanel labelPanel = new JPanel();
		add(labelPanel, BorderLayout.CENTER);
		labelPanel.setLayout(new BorderLayout());

        lblTitle = new JLabel("Title");
        labelPanel.add(lblTitle, BorderLayout.NORTH);

        lblDate = new JLabel("Date");
        labelPanel.add(lblDate, BorderLayout.SOUTH);
        setBackground(Color.white);

		JPanel iconPanel = new JPanel();
		add(iconPanel, BorderLayout.EAST);
		iconPanel.setLayout(new BorderLayout());

		lblEncrypted = new JLabel(icon);
		iconPanel.add(lblEncrypted, BorderLayout.CENTER);

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
    	if(!value.getEncrypted()){
    		lblEncrypted.setIcon(null);
		}
		else{
			lblEncrypted.setIcon(icon);
		}
    	setSelected(isSelected);
    	return this;
    }
}