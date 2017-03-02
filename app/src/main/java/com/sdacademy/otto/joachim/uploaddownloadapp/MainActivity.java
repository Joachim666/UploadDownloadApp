package com.sdacademy.otto.joachim.uploaddownloadapp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        uploadFile();
        downloadFile("https://developer.android.com/images/home/nougat_bg_2x.jpg");
    }

    private boolean checkPermissions() {
        int status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (status == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 0);
            return false;
        }
    }

    private void uploadFile() {
        File file = new File("/storage/emulated/0/Download/$RXV37OC.png");
        MediaType mediaType = MediaType.parse("application/octet-stream");
        Request.Builder builder = new Request.Builder();
        String url = "https://content.dropboxapi.com/2/files/upload";
        builder.url(url);
        builder.addHeader("Authorization", "Bearer 1uEZkPo7nYAAAAAAAAAA7F94khBq9ZIKf569jaoQsqEu87Ekgu2PGANzKy791drn");
        builder.addHeader("Content-Type", " application/octet-stream");
        builder.addHeader("Dropbox-API-Arg", " {\"path\":\"/$RXV37OC.png\"}");
        builder.post(RequestBody.create(mediaType, file));
        Request request = builder.build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TEST", "fail", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("TEST", "onResponse " + response.body().string());
            }
        });
    }

    private void downloadFile(String fileUrl) {
        
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(fileUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        downloadManager.enqueue(request);
    }


}
