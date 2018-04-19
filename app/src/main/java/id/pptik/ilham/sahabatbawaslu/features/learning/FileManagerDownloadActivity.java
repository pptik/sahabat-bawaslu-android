package id.pptik.ilham.sahabatbawaslu.features.learning;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;

public class FileManagerDownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(this, "abc", Toast.LENGTH_SHORT).show();
        Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/myFolder/");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
        {
            startActivity(intent);
        }
        else
        {
            // if you reach this place, it means there is no any file
            // explorer app installed on your device
        }
    }
}
