package com.uwindsor.notekeeper;

import com.uwindsor.notekeeper.ui.SignIn;

import java.awt.*;
import java.io.IOException;

public class Application {
   // private static Drive service;

    public static void main(String args[]) throws IOException {
        //service = GoogleDriveService.getInstance();

//        if(service != null) {
//            // Initial setup
//            setup();
//        }

        EventQueue.invokeLater(() -> {
            try {
                SignIn window = new SignIn();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

//    private static void setup() throws IOException {
//        // Setup root directory
//        File f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR);
//        Constants.NOTE_KEEPER_DIR_ID = f.getId();
//        // Setup notes directory
//        f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR_ID, Constants.NOTES_DIR);
//        Constants.NOTES_DIR_ID = f.getId();
//        // Setup encrypted directory
//        f = GoogleDriveClient.createIfNotExists(service, Constants.NOTE_KEEPER_DIR_ID, Constants.ENCRYPTED_NOTES_DIR);
//        Constants.ENCRYPTED_NOTE_DIR_ID = f.getId();
//    }
}
