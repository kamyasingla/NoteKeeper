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
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel btnPanel = new JPanel();
        frame.getContentPane().add(btnPanel, BorderLayout.NORTH);
        btnPanel.setLayout(new BorderLayout(0, 0));

        JButton btnCreate = new JButton("+");
        btnPanel.add(btnCreate, BorderLayout.EAST);

        JPanel borderPanelW = new JPanel();
        frame.getContentPane().add(borderPanelW, BorderLayout.WEST);

        JPanel borderPanelE = new JPanel();
        frame.getContentPane().add(borderPanelE, BorderLayout.EAST);

        JPanel borderPanelS = new JPanel();
        frame.getContentPane().add(borderPanelS, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        Note[] notes = new Note[100];

        for(int i = 0; i < 100; i++) {
            notes[i] = new Note("123", "xyz.txt" + i, new Date(), i % 2 == 0);
        }
        JList<Note> list = new JList<>(notes);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList)e.getSource();
                if(e.getClickCount() == 2) {
                    NoteViewer noteViewer = new NoteViewer(notes[list.getSelectedIndex()]);
                    noteViewer.frame.setVisible(true);
                } else {
                    super.mouseClicked(e);
                }
            }
        });

        list.setCellRenderer(new NoteListItem());
        list.setBackground(Color.WHITE);
        scrollPane.setViewportView(list);
    }
}