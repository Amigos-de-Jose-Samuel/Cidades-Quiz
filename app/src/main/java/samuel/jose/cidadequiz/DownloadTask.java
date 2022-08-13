package samuel.jose.cidadequiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;

public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
    private Context myContext;
    private ImageView image;
    private ProgressDialog progressDialog;

    public DownloadTask(Context myContext, ImageView image) {
        this.myContext = myContext;
        this.image = image;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(myContext, "Please, wait...", "Downloading image...");
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            bitmap = DownloadRequest.downloadImage(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        image.setImageBitmap(bitmap);
        progressDialog.dismiss();
    }
}
