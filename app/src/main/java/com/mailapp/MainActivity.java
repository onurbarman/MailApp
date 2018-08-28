package com.mailapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtAlici,txtKonu,txtMesaj;
    Button btnGonder;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        txtAlici=(EditText)findViewById(R.id.txtAlici);
        txtKonu=(EditText)findViewById(R.id.txtKonu);
        txtMesaj=(EditText)findViewById(R.id.txtMesaj);

        btnGonder=(Button)findViewById(R.id.btnGonder);

        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMailAsyncTask task=new sendMailAsyncTask();
                task.execute();
            }
        });
    }

    private void sendMail(String alici, String konu, String mesaj) {
        if (!alici.isEmpty() || !konu.isEmpty() || !mesaj.isEmpty())
        {
            try {
                GMailSender sender = new GMailSender("sendermail@gmail.com",
                        "password");
                sender.sendMail(konu, mesaj,
                        "sendermail@gmail.com", alici);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        }
        else
        {
            Toast.makeText(MainActivity.this,"Tüm Alanlar Doldurulmalıdır.",Toast.LENGTH_SHORT).show();
        }

    }

    class sendMailAsyncTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            pDialog=new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Mail Gönderiliyor...");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            sendMail(txtAlici.getText().toString(),txtKonu.getText().toString(),txtMesaj.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(MainActivity.this,"Mail Gönderildi.",Toast.LENGTH_SHORT).show();
        }

    }
}
