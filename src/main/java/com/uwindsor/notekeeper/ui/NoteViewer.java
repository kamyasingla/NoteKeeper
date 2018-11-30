package com.uwindsor.notekeeper.ui;

import com.google.common.hash.Hashing;
import com.uwindsor.notekeeper.model.Note;
import com.uwindsor.notekeeper.service.PersistenceService;
import com.uwindsor.notekeeper.util.SimpleEncryptDecrypt;

import javax.swing.*;
import java.awt.*;
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
    private JButton btnEncrypt;

    /**
     * Create the application.
     */
    NoteViewer(PersistenceService persistenceService, Note note, MainWindow mainWindow) {
        this.persistenceService = persistenceService;
        this.note = note;
        this.mainWindow = mainWindow;
        initialize();
        try {
            if(note.getEncrypted()) {
                String hashedPassword = checkPassword();
                if(hashedPassword != null)
                    txtContent.setText(persistenceService.decryptNote(note, hashedPassword));
                else
                    frame.setVisible(false);
            } else {
                txtContent.setText(persistenceService.getNoteContent(note));
            }
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
            btnEncrypt = new JButton("Encrypt");
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
            JOptionPane.showMessageDialog(frame, "Note Saved!");
            btnEdit.setText("Edit");
            txtContent.setEditable(false);
        }
    }

    private void saveNote() {
        try {
            if(note.getEncrypted()) {
                String hashedPassword = checkPassword();
                if(hashedPassword != null)
                    persistenceService.saveNoteContent(note,
                            SimpleEncryptDecrypt.encrypt(txtContent.getText(), hashedPassword));
            } else {
                persistenceService.saveNoteContent(note, txtContent.getText());
            }

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
            String hashedPassword = checkPassword();
            if(hashedPassword != null) {
                this.note = persistenceService.encryptNote(note, txtContent.getText(), hashedPassword);
                mainWindow.loadNotes();
                btnEncrypt.setVisible(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String checkPassword() {
        try {
            String password = persistenceService.getPassword();
            if(password == null) {
                String uPassword = showPasswordDialog("Password is not set. Enter a new password:");
                if(!uPassword.isEmpty())
                    return persistenceService.savePassword(uPassword);
                else
                    return null;
            }
            String uPassword = showPasswordDialog("Enter password:");
            if(uPassword.isEmpty())
                return null;
            String hashedPassword = getPasswordHashed(uPassword);
            int tries = 1;
            while(!password.equals(hashedPassword) && tries != 3) {
                uPassword = showPasswordDialog("Wrong Password. Try again!");
                if(uPassword.isEmpty())
                    return null;
                hashedPassword = getPasswordHashed(uPassword);
                tries++;
            }
            if(tries > 2) {
                JOptionPane.showMessageDialog(frame, "Incorrect password entered 3 times please try again");
                return null;
            }
            return hashedPassword;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String showPasswordDialog(String message) {
        JPanel jPanel = new JPanel();
        JLabel label = new JLabel(message);
        JPasswordField pass = new JPasswordField(10);
        jPanel.add(label);
        jPanel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(frame, jPanel, "Password",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if(option == 0) {
            return new String(pass.getPassword());
        }

        return "";
    }

    private String getPasswordHashed(String uPassword) {
        String sha256hex = Hashing.sha256()
                .hashString(uPassword, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }
}
