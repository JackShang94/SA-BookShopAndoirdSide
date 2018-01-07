package com.example.jack.bookshop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jack.bookshop.Controller.HttpHandler;
import com.example.jack.bookshop.Model.Book;

import java.lang.ref.WeakReference;

public class DetailsActivity extends Activity {

    final static int []view = {R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4,
                                R.id.editText5,R.id.editText6,R.id.editText7};
    final static String []key = {"id", "title", "author", "isbn","category","stock","price"};
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String item = getIntent().getExtras().getString("id");
        new AsyncTask<String, Void, Book>() {
            @Override
            protected Book doInBackground(String... params) {
                return Book.getBookByID(params[0]);
            }

            @Override
            protected void onPostExecute(Book result) {
                for (int i=0; i<view.length; i++) {
                    EditText t = (EditText) findViewById(view[i]);
                    t.setText(result.get(key[i]));
                }
            }
        }.execute(item);
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book b = new Book();
                for (int i=0; i<view.length; i++) {
                    EditText t = (EditText) findViewById(view[i]);
                    b.put(key[i], t.getText().toString());
                }
                new AsyncTask<Book, Void, Void>() {
                    @Override
                    protected Void doInBackground(Book... params) {
                        Book.updateBook(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(b);
            }
        });
    }
}
