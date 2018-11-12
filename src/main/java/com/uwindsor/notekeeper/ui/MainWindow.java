package com.uwindsor.notekeeper.ui;

import com.uwindsor.notekeeper.model.Note;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

public class MainWindow {

    public JFrame frame;

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
        Note[] notes = new Note[100];

        for(int i = 0; i < 100; i++) {
            notes[i] = new Note("123", "xyz.txt" + i, new Date(), false);
        }
        JList<Note> list = new JList<>(notes);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList)e.getSource();
                if(e.getClickCount() == 2) {
                    System.out.println("Selected: " + notes[list.getSelectedIndex()].getFileName());
                } else {
                    super.mouseClicked(e);
                }
            }
        });

        list.setCellRenderer(new NoteListItem());
        list.setBackground(Color.WHITE);
        JScrollPane scrollBar = new JScrollPane(list);
        scrollBar.setBounds(6, 62, 367, 346);
        scrollBar.setViewportView(list);
        panel_1.add(scrollBar);
    }
}