package com.uwindsor.notekeeper.ui;

import com.uwindsor.notekeeper.model.Note;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;
import java.util.Date;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 384, 456);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 379, 430);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnCreate = new JButton("+");
		btnCreate.setBounds(312, 6, 61, 29);
		panel_1.add(btnCreate);

		Note[] notes = new Note[10];
		notes[0] = new Note("123", "xyz.txt", new Date(), false);
        notes[1] = new Note("123", "xyz.txt", new Date(), false);
        notes[2] = new Note("123", "xyz.txt", new Date(), false);
		
		JList<Note> list = new JList<>(notes);
		list.setCellRenderer(new NoteListItem());
		list.setBackground(Color.WHITE);
        list.setBounds(6, 62, 367, 346);
		list.setVisibleRowCount(5);
		panel_1.add(list);
	}
}
