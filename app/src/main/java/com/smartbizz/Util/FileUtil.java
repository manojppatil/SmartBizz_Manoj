package com.smartbizz.Util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    private static final String DOCUMENTS_DIR = "documents";

    /**
     Get a file path from a Uri. This will get the the path for Storage Access
     Framework Documents, as well as the _data field for the MediaStore and
     other file-based ContentProviders.

     @param context The context.
     @param uri The Uri to query.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {

                String fileName = getFilePath(context, uri);
                if (fileName != null) {
                    return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                }

                String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")) {
                    id = id.replaceFirst("raw:", "");
                    File file = new File(id);
                    if (file.exists())
                        return id;
                }

                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);

            } else if (isMediaDocument(uri)) { //MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            } else if (isNewGooglePhotosUri(uri)) {
                String pathUri = uri.getPath();
                String newUri = pathUri.substring(pathUri.indexOf("content"), pathUri.lastIndexOf("/ACTUAL"));
                return getDataColumn(context, Uri.parse(newUri), null, null);
            } else if (isDriveDocument(uri)) {
                return getDriveFilePath(context, uri);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getFilePath(Context context, Uri uri) {

        Cursor cursor = null;
        final String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isValidFileExtension(Context context, String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            if (filePath.endsWith("csv") || filePath.endsWith("xls") || filePath.endsWith("xlsx")) {
                return true;
            }
        }
        Toast.makeText(context, "Unsupported file format.", Toast.LENGTH_LONG).show();
        return false;
    }

    private static void saveFileFromUri(Context context, Uri uri, String destinationPath) {
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            bos = new BufferedOutputStream(new FileOutputStream(destinationPath, false));
            byte[] buf = new byte[1024];
            is.read(buf);
            do {
                bos.write(buf);
            } while (is.read(buf) != -1);
        } catch (IOException e) {
            MyLogger.error(e);
        } finally {
            try {
                if (is != null) is.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                MyLogger.error(e);
            }
        }
    }




    private static String getName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('/');
        return filename.substring(index + 1);
    }


    /**
     Get the value of the data column for this Uri. This is useful for
     MediaStore Uris, and other file-based ContentProviders.

     @param context The context.
     @param uri The Uri to query.
     @param selection (Optional) Filter used in the query.
     @param selectionArgs (Optional) Selection arguments used in the query.
     @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }catch (Exception e){
            MyLogger.error(e);
        }finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     @param uri The Uri to check.
     @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     @param uri The Uri to check.
     @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isNewGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.contentprovider".equals(uri.getAuthority());
    }

    /**
     @param uri The Uri to check.
     @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isDriveDocument(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    /**
     @param uri The Uri to check.
     @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

//    public static boolean isValidFileExtension(Context context, String filePath) {
//        if (!TextUtils.isEmpty(filePath)) {
//            if (filePath.endsWith("png") || filePath.endsWith("jpg") || filePath.endsWith("jpeg") || filePath.endsWith(
//                    "pdf")) {
//                return true;
//            }
//        }
//        Toast.makeText(context, "Unsupported file format.", Toast.LENGTH_LONG).show();
//        return false;
//    }

    public static String getFileExtensionType(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            final String[] strings = filePath.split("\\.");
            if (CollectionUtil.length(strings) > 1) {
                final String extension = strings[strings.length - 1];
                if (!TextUtils.isEmpty(extension)) {
                    if ("png".equalsIgnoreCase(extension) || "jpg".equalsIgnoreCase(extension) ||
                            "jpeg".equalsIgnoreCase(extension)) {
                        return "image";
                    }
                    return extension;
                }
            }
        }
        return "";
    }

    public static String getFileExtension(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            final String[] strings = filePath.split("\\.");
            if (CollectionUtil.length(strings) > 1) {
                final String extension = strings[strings.length - 1];
                if (!TextUtils.isEmpty(extension)) {
                    return extension;
                }
            }
        }
        return "";
    }

    public static File saveImageFile(Context context, byte[] bytes) {
        final File file = getTemporaryFile(context);
        if (file == null || bytes == null) {
            return null;
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);

            Bitmap originalImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            originalImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return file;
        } catch (Exception e) {
            MyLogger.error(e);
            return null;
        }
    }

    private static final String TEMP_FILE = "temp.png";

    private static File getTemporaryFile(Context context) {
        File mediaFile = new File(context.getFilesDir() + File.separator + TEMP_FILE);
        if (mediaFile.exists()) {
            mediaFile.delete();
        }
        return mediaFile;
    }
    
    public static byte[] getByteArray(File file){
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
        } catch (FileNotFoundException e) {
           MyLogger.error(e);
        } catch (IOException e) {
            MyLogger.error(e);
        }


        return bytesArray;
    }

    private static String getDriveFilePath(Context context,Uri uri ) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }
}
