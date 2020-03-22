package com.example.lts_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lts_android.entity.FlaskApiResponseBody;
import com.example.lts_android.entity.SourceFileRequestBody;
import com.example.lts_android.retrofit.RetrofitApis;
import com.example.lts_android.retrofit.RetrofitInstanceGetter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button _fileOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fileOpener=findViewById(R.id.fileOpener);

        _fileOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/plain");
                startActivityForResult(intent,2);

            }
        });



    }


    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filepath="";
        String authority=data.getData().getAuthority();
        if(authority.equals("com.android.externalstorage.documents")){
            String tempId= DocumentsContract.getDocumentId(data.getData());
            String splitArray[]=tempId.split(":");
            String type=splitArray[0];
            String id=splitArray[1];
            if(type.equals("primary")){
                filepath=Environment.getExternalStorageDirectory()+"/"+id;

            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "File doesn't have primary type!",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "File Authority mismatch!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        if(!checkPermission()){
            requestPermission();
        }else{
            File file=new File(filepath);
            StringBuilder stringBuilder=new StringBuilder();
            BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
            String fileContents=stringBuilder.toString();
            //System.out.println(fileContents);


            RetrofitApis retrofitApis= RetrofitInstanceGetter
                    .getRetrofitInstance("http://192.168.43.11:3000")
                    .create(RetrofitApis.class);

            SourceFileRequestBody sourceFileRequestBody=new SourceFileRequestBody();
            sourceFileRequestBody.setSourceFile(fileContents);


            retrofitApis.sendSourceFileToFlask(sourceFileRequestBody).enqueue(new Callback<FlaskApiResponseBody>() {

                @Override
                public void onResponse(Call<FlaskApiResponseBody> call, Response<FlaskApiResponseBody> response) {
                    if(response.body()!=null && response.errorBody()==null){
                        startActivity(new Intent(MainActivity.this,ResultPage.class)
                                .putExtra("data",response.body()));
                    }
                }

                @Override
                public void onFailure(Call<FlaskApiResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "Flask api call failed for main file",Toast.LENGTH_SHORT).show();
                }

            });

        }


    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result==PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to read files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Log.i("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
            break;
        }
    }


}
