package com.baoyachi.donwloadwithretrofit;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyachi.donwloadwithretrofit.net.HttpUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements ProgressListener
{

    private File downloadFile;
    ProgressBar progressBar;
    private String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wandou/b1";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        final File file = new File(savePath);
        if(!file.exists())
        {
            file.mkdirs();
        }
        downloadFile = new File(file, "wandoujia_test.apk");


        Button dianwo = (Button) findViewById(R.id.dianwo);
        dianwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
                HttpUtils.createService(MainActivity.this).downloadAPK("http://f5.market.xiaomi.com/download/AppStore/01a3bd5737f2e4fcc0c1939b4798b259b3c31247e/com.supercell.clashroyale.mi.apk").enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        if(response.isSuccessful())
                        {
                            BufferedSink sink = null;
                            //下载文件到本地
                            try
                            {
                                sink = Okio.buffer(Okio.sink(downloadFile));
                                sink.writeAll(response.body().source());
                            } catch(Exception e)
                            {
                                e.printStackTrace();
                            } finally
                            {
                                try
                                {
                                    if(sink != null) sink.close();
                                } catch(IOException e)
                                {
                                    e.printStackTrace();
                                }

                            }
                            Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                            Log.d("下载成功", "isSuccessful");
                        } else
                        {

                            Log.d("---------------------", response.code() + "");

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {
                        Toast.makeText(MainActivity.this, "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("下载失败", t.getMessage());
                    }
                });
            }
        });


    }


    @Override
    public void update(long bytesRead, long contentLength, boolean done)
    {
        System.out.println("bytesRead" + bytesRead);
        System.out.println("contentLength" + contentLength);
        System.out.println("done" + done);
        progressBar.setProgress((int) ((bytesRead * 100) / contentLength));

    }
}
