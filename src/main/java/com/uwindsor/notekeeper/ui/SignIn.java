package com.uwindsor.notekeeper.ui;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.uwindsor.notekeeper.drive.GoogleDriveClient;
import com.uwindsor.notekeeper.drive.GoogleDriveService;
import com.uwindsor.notekeeper.util.Constants;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.io.IOException;

public class SignIn {

    private static Drive service;
    public JFrame frame;

    /**
      * Create the application.
      */
            public SignIn() {
        initialize();
    }

    private static void setup() throws IOException {
        // Setup root directory
        File f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR);
        Constants.NOTE_KEEPER_DIR_ID = f.getId();
        // Setup notes directory
        f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR_ID, Constants.NOTES_DIR);
        Constants.NOTES_DIR_ID = f.getId();
        // Setup encrypted directory
        f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR_ID, Constants.ENCRYPTED_NOTES_DIR);
        Constants.ENCRYPTED_NOTE_DIR_ID = f.getId();
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
            service = GoogleDriveService.getInstance();
            if(service != null) {
                try {
                    setup();
                    MainWindow mainWindow = new MainWindow();
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