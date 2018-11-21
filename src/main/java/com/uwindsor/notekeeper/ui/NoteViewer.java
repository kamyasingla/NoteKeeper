package com.uwindsor.notekeeper.ui;
import com.google.common.hash.Hashing;
import com.uwindsor.notekeeper.model.Note;
import com.uwindsor.notekeeper.service.PersistenceService;
import com.uwindsor.notekeeper.util.EncryptDecryptStringWithDES;
import sun.applet.Main;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NoteViewer {

    JFrame frame;
    private Note note;
    private JTextArea txtContent;
    private PersistenceService persistenceService;
    private MainWindow mainWindow;

    /**
     * Create the application.
     */
    NoteViewer(PersistenceService persistenceService, Note note, MainWindow mainWindow) {
        this.persistenceService = persistenceService;
        this.note = note;
        this.mainWindow = mainWindow;
        initialize();
        try {
            txtContent.setText(persistenceService.getNoteContent(note));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 518, 449);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(note.getFileName());

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel panel_1 = new JPanel();
        panel.add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnEdit = new JButton("Edit");
        panel_1.add(btnEdit);
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editClicked((JButton) e.getSource());
            }
        });

        JButton btnDelete = new JButton("Delete");
        panel_1.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClicked();
            }
        });

        if(!note.getEncrypted()) {
            JButton btnEncrypt = new JButton("Encrypt");
            panel_1.add(btnEncrypt);
            btnEncrypt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    encryptClicked();
                }
            });
        }

        JPanel panel_2 = new JPanel();
        panel.add(panel_2, BorderLayout.WEST);

        JPanel panel_3 = new JPanel();
        panel.add(panel_3, BorderLayout.EAST);

        JPanel panel_4 = new JPanel();
        panel.add(panel_4, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, BorderLayout.CENTER);

        txtContent = new JTextArea();
        txtContent.setEditable(false);
        scrollPane.setViewportView(txtContent);
    }

    private void editClicked(JButton btnEdit){
        if(btnEdit.getText().equals("Edit")){
            btnEdit.setText("Save");
            txtContent.setEditable(true);
        }
        else{
            saveNote();
            btnEdit.setText("Edit");
            txtContent.setEditable(false);
        }
    }

    private void saveNote() {
        try {
            persistenceService.saveNoteContent(note, txtContent.getText());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void deleteClicked(){
        try{
            persistenceService.deleteNote(note);
            mainWindow.loadNotes();
            frame.setVisible(false);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void encryptClicked() {
        try {
            String password = persistenceService.getPassword();
            String encryptedString = EncryptDecryptStringWithDES.encrypt("Hello World", password);
            System.out.println(encryptedString);
            String decryptedString = EncryptDecryptStringWithDES.decrypt(encryptedString, password);
            System.out.println(decryptedString);
            String message, uPassword;
            if(password == null) {
                message = "Password is not set. Enter a new password:";
                uPassword = JOptionPane.showInputDialog(frame, message);
                uPassword = persistenceService.savePassword(uPassword);
            } else {
                message = "Enter password:";
                uPassword = JOptionPane.showInputDialog(frame, message);
                String hashedPassword = getPasswordHashed(uPassword);
                while(!password.equals(hashedPassword)) {
                    uPassword = JOptionPane.showInputDialog(frame, "Wrong Password. Try again!");
                    hashedPassword = getPasswordHashed(uPassword);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPasswordHashed(String uPassword) {
        String sha256hex = Hashing.sha256()
                .hashString(uPassword, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }
}
