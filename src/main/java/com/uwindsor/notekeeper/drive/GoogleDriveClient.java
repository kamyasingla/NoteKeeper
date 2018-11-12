package com.uwindsor.notekeeper.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;

public class GoogleDriveClient {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final Collection<String> SCOPES = DriveScopes.all();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleDriveClient.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static Drive getGoogleDriveService() {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static File createDirectory(Drive service, String directoryName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(directoryName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        return service.files().create(fileMetadata)
                .setFields("id")
                .execute();
    }

    private static File createDirectory(Drive service, String parent, String directoryName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(directoryName);
        fileMetadata.setParents(Collections.singletonList(parent));
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        return service.files().create(fileMetadata)
                .setFields("id")
                .execute();
    }

    public static File createIfNotExists(Drive service, String path) throws IOException {
     FileList fileList = service.files()
             .list()
             .setFields("nextPageToken, files(id)")
             .setQ("name='"+ path +"' and trashed = false")
             .execute();
     if(fileList.getFiles().size() > 0) {
         return fileList.getFiles().get(0);
     } else {
         return createDirectory(service, path);
     }
    }

    static File createIfNotExists(Drive service, String parent, String path) throws IOException {
        FileList fileList  = service.files()
                .list()
                .setQ("'"+ parent +"' in parents and name='" + path + "' and trashed = false" )
                .execute();
        if(fileList.getFiles().size() > 0) {
            return fileList.getFiles().get(0);
        } else {
            return createDirectory(service, parent, path);
        }
    }

    static FileList getFiles(Drive service, String parent) throws IOException {
        return service.files()
                .list()
                .setFields("nextPageToken, files(id, name, modifiedTime)")
                .setQ("'"+ parent +"' in parents and trashed = false" )
                .execute();
    }

    static String getFile(Drive service, String fileId) throws IOException {
        InputStream inputStream = service.files()
                .get(fileId)
                .executeMediaAsInputStream();

        return CharStreams.toString(new InputStreamReader(
                inputStream, Charsets.UTF_8));
    }
}
