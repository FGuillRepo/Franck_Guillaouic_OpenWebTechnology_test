package instagallery.app.com.gallery.Utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import instagallery.app.com.gallery.activity.GalleryActivity;

import static instagallery.app.com.gallery.Application.databaseHandler;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    String filename;
    String RootFilePath= Environment.getExternalStorageDirectory()+ "/InstaGenius/picture";
   private String folder=null;
    public DownloadFileFromURL(){

    }
    /**
     * Before starting background thread
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        createDirIfNotExists(RootFilePath);
    }


    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            String filename_extracted = f_url[0].substring(f_url[0].lastIndexOf("/")+1);


            filename = RootFilePath+ "/" +filename_extracted;
            Log.d("filepic",filename);

                URLConnection ucon = url.openConnection();
                ucon.setReadTimeout(5000);
                ucon.setConnectTimeout(10000);

                InputStream is = ucon.getInputStream();
                BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);

                File file = new File(filename);

                if (!file.exists())
                {
                    file.createNewFile();
                    FileOutputStream outStream = new FileOutputStream(file);
                    byte[] buff = new byte[5 * 1024];

                    int len;
                    while ((len = inStream.read(buff)) != -1)
                    {
                        outStream.write(buff, 0, len);
                    }

                    outStream.flush();
                    outStream.close();
                    inStream.close();
                }

            }catch (Exception e){

        }

        return filename;
    }



    /**
     * After completing background task
     * **/
    @Override
    protected void onPostExecute(String file_url) {
    }


    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return ret;
    }
}