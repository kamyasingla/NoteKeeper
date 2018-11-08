package com.uwindsor.notekeeper.drive;

import com.google.api.services.drive.Drive;

public class GoogleDriveService {
    private static Drive driveService;

    private GoogleDriveService() {}

    public static Drive getInstance() {
        if(driveService == null) {
            driveService = GoogleDriveClient.getGoogleDriveService();
        }
        return driveService;
    }
}
