package com.uwindsor.notekeeper.ui;

import com.uwindsor.notekeeper.drive.GooglePersistenceService;
import com.uwindsor.notekeeper.service.PersistenceService;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.io.IOException;

public class SignIn {

    private static PersistenceService persistenceService;
    public JFrame frame;

    public SignIn() {
        persistenceService = new GooglePersistenceService();
        initialize();
    }

    /**
      * Initialize the contents of the frame.
      */
            private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 450, 272);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JButton btnNewButton = new JButton("Sign In With Google");
        btnNewButton.addActionListener(e -> {
            if(persistenceService != null) {
                try {
                    persistenceService.setup();
                    MainWindow mainWindow = new MainWindow(persistenceService);
                    mainWindow.frame.setVisible(true);
                    frame.setVisible(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        btnNewButton.setBounds(144, 119, 167, 29);
        panel.add(btnNewButton);

        JLabel lblNotekeeper = new JLabel("NoteKeeper");
        lblNotekeeper.setForeground(Color.DARK_GRAY);
        lblNotekeeper.setFont(new Font("Lucida Grande", Font.BOLD, 22));
        lblNotekeeper.setBounds(157, 20, 180, 36);
        panel.add(lblNotekeeper);
    }
}