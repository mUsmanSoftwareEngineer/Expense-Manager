package myapps.expensetracker.spendingmanager.google;

import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import myapps.expensetracker.spendingmanager.utilities.ByteSegments;
import myapps.expensetracker.spendingmanager.utilities.FileInputSource;

public class GoogleDriveApiDataRepository {

    private final String FILE_MIME_TYPE = "application/zip";
    private final String APP_DATA_FOLDER_SPACE = "appDataFolder";
    private String dbPath = "/data/data/myapps.expensetracker.spendingmanager/databases/app_database";
    private String dbPathShm = "/data/data/myapps.expensetracker.spendingmanager/databases/app_database-shm";
    private String dbPathWal = "/data/data/myapps.expensetracker.spendingmanager/databases/app_database-wal";

    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    private final Drive mDriveService;

    public GoogleDriveApiDataRepository(Drive driveApi) {
        this.mDriveService = driveApi;
    }

    public Task<Void> uploadFile(@NonNull final java.io.File file, @NonNull final String fileName) {

        return createFile(fileName)
                .continueWithTask(mExecutor, task -> {
                    final String fileId = task.getResult();
                    if (fileId == null) {
                        throw new IOException("Null file id when requesting file upload.");
                    }
                    return writeFile(file, fileId, fileName);
                });
    }

    public Task<Void> downloadFile(@NonNull final java.io.File file, @NonNull final String fileName) {
        return queryFiles()
                .continueWithTask(mExecutor, task -> {
                    final FileList fileList = task.getResult();
                    Log.d("checkFileName",fileList.getFiles().size()+"");
                    if (fileList == null) {
                        throw new IOException("Null file list when requesting file download.");
                    }
                    File currentFile = null;
                    for (File f : fileList.getFiles()) {
                        if (f.getName().equals(fileName)) {
                            currentFile = f;
                            break;
                        }
                    }
                    if (currentFile == null) {
                        throw new IOException("File not found when requesting file download.");
                    }

                    final String fileId = currentFile.getId();

                    return readFile(file, fileId);
                });
    }

    private Task<Void> readFile(
            @NonNull final java.io.File file,
            @NonNull final String fileId
    ) {
        return Tasks.call(mExecutor, () -> {
            String encoded;

            try (InputStream is = mDriveService.files().get(fileId).executeMediaAsInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                encoded = stringBuilder.toString();
            }

            final byte[] decoded = Base64.decode(encoded, Base64.DEFAULT);
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(decoded);
            }

            return null;
        });
    }

    public Task<FileList> queryFiles() {
        return Tasks.call(mExecutor, () -> mDriveService.files().list().setSpaces(APP_DATA_FOLDER_SPACE).execute());
    }

    private Task<Void> writeFile(
            @NonNull final java.io.File file,
            @NonNull final String fileId,
            @NonNull final String fileName
    ) {
        return Tasks.call(mExecutor, () -> {
            File metadata = getMetaData(fileName);

            byte[] bytes = ByteSegments.toByteArray(new FileInputSource(file));
            final String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
            ByteArrayContent contentStream = ByteArrayContent.fromString(FILE_MIME_TYPE, encoded);

            // Update the metadata and contents.
            mDriveService.files().update(fileId, metadata, contentStream).execute();
            return null;
        });
    }




    private Task<String> createFile(@NonNull final String fileName) {
        return Tasks.call(mExecutor, () -> {

            File storageFile = new File();
            storageFile.setParents(Collections.singletonList(APP_DATA_FOLDER_SPACE));
            storageFile.setName(fileName);

            File storageFileShm = new File();
            storageFileShm.setParents(Collections.singletonList(APP_DATA_FOLDER_SPACE));
            storageFileShm.setName(fileName+"-shm");

            File storageFileWal = new File();
            storageFileWal.setParents(Collections.singletonList(APP_DATA_FOLDER_SPACE));
            storageFileWal.setName(fileName+"-wal");

            java.io.File filePath = new java.io.File(dbPath);
            java.io.File filePathShm = new java.io.File(dbPathShm);
            java.io.File filePathWal = new java.io.File(dbPathWal);
            FileContent mediaContent = new FileContent("",filePath);
            FileContent mediaContentShm = new FileContent("",filePathShm);
            FileContent mediaContentWal = new FileContent("",filePathWal);
            File metadata = getMetaData(fileName);
            metadata.setParents(Collections.singletonList(APP_DATA_FOLDER_SPACE));
//            FileContent mediaContent = new FileContent("image/jpeg", filePath);
            File googleFile = mDriveService.files().create(storageFile, mediaContent).execute();
            System.out.printf("Filename: %s File ID: %s \n", googleFile.getName(), googleFile.getId());

            File fileShm = mDriveService.files().create(storageFileShm, mediaContentShm).execute();
            System.out.printf("Filename: %s File ID: %s \n", fileShm.getName(), fileShm.getId());

            File fileWal = mDriveService.files().create(storageFileWal, mediaContentWal).execute();
            System.out.printf("Filename: %s File ID: %s \n", fileWal.getName(), fileWal.getId());
//            File googleFile = mDriveService.files().create(metadata).execute();
            if (googleFile == null || fileShm==null || fileWal==null) {
                throw new IOException("Null result when requesting file creation.");
            }
            return null;
        });
    }

    public void upload(){
        File storageFile = new File();
        storageFile.setParents(Collections.singletonList(APP_DATA_FOLDER_SPACE));
        storageFile.setName("app_database");

        File storageFileShm = new File();
        storageFileShm.setParents(Collections.singletonList(APP_DATA_FOLDER_SPACE));
        storageFileShm.setName("app_database-shm");

        File storageFileWal = new File();
        storageFileWal.setParents(Collections.singletonList(APP_DATA_FOLDER_SPACE));
        storageFileWal.setName("app_database-wal");

        java.io.File filePath = new java.io.File(dbPath);
        java.io.File filePathShm = new java.io.File(dbPathShm);
        java.io.File filePathWal = new java.io.File(dbPathWal);
        FileContent mediaContent = new FileContent("",filePath);
        FileContent mediaContentShm = new FileContent("",filePathShm);
        FileContent mediaContentWal = new FileContent("",filePathWal);
        try {
            File file = mDriveService.files().create(storageFile, mediaContent).execute();
            System.out.printf("Filename: %s File ID: %s \n", file.getName(), file.getId());

            File fileShm = mDriveService.files().create(storageFileShm, mediaContentShm).execute();
            System.out.printf("Filename: %s File ID: %s \n", fileShm.getName(), fileShm.getId());

            File fileWal = mDriveService.files().create(storageFileWal, mediaContentWal).execute();
            System.out.printf("Filename: %s File ID: %s \n", fileWal.getName(), fileWal.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private File getMetaData(@NonNull String fileName) {
        return new File()
                .setMimeType(FILE_MIME_TYPE)
                .setName(fileName);
    }

//    private File getMetaData(@NonNull final String fileName) {
//        return new File()
//                .setMimeType(FILE_MIME_TYPE)
//                .setName(fileName);
//    }

    private File getFile(@NonNull final String fileName) {
        return new File()
                .setMimeType(FILE_MIME_TYPE)
                .setName(fileName);
    }

}
