package com.uwindsor.notekeeper;

import com.uwindsor.notekeeper.ui.SignIn;

import java.awt.*;
import java.io.IOException;

public class Application {
         // private static Drive service;
    public static void main(String args[]) throws IOException {
        EventQueue.invokeLater(() -> {
            try {
                SignIn window = new SignIn();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}