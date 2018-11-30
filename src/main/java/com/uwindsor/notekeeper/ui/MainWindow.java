package com.uwindsor.notekeeper.ui;

import com.uwindsor.notekeeper.model.Note;
import com.uwindsor.notekeeper.service.PersistenceService;
import sun.applet.Main;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainWindow {

    public JFrame frame;
    private PersistenceService persistenceService;
    private DefaultListModel<Note> listModel = new DefaultListModel<>();

    /**
     * Create the application.
     */
    public MainWindow(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        initialize();
    }

    public void loadNotes() {
        try {
            listModel.clear();
            persistenceService.getAllNotes()
                    .stream()
                    .sorted(Comparator.comparing(Note::getCreationDate).reversed())
                    .forEach(listModel::addElement);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 384, 456);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        loadNotes();
        JList<Note> list = new JList<>(listModel);

        JPanel btnPanel = new JPanel();
        frame.getContentPane().add(btnPanel, BorderLayout.NORTH);
        btnPanel.setLayout(new BorderLayout(0, 0));

        JButton btnCreate = new JButton("+");
        btnCreate.addActionListener(i -> {
            String fileName = JOptionPane.showInputDialog(frame, "Please enter note name: ");
            try {
                listModel.add(0, persistenceService.createNote(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnPanel.add(btnCreate, BorderLayout.EAST);

        JPanel borderPanelW = new JPanel();
        frame.getContentPane().add(borderPanelW, BorderLayout.WEST);

        JPanel borderPanelE = new JPanel();
        frame.getContentPane().add(borderPanelE, BorderLayout.EAST);

        JPanel borderPanelS = new JPanel();
        frame.getContentPane().add(borderPanelS, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);


        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList)e.getSource();
                if(e.getClickCount() == 2) {
                    NoteViewer noteViewer = new NoteViewer(persistenceService, listModel.get(list.getSelectedIndex()),MainWindow.this);
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