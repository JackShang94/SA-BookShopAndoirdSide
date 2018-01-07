package com.example.jack.bookshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.jack.bookshop.Model.Book;

import java.util.List;

public class ListActivity extends android.app.ListActivity {

    String filter = "";
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        if (extras==null){
            showAll();
        }

/*        else if(extras.get("filter") =="showAll"){
            showAll();
        }*/

        else {
            filter = extras.getString("filter");
            new AsyncTask<String, Void, List<Book>>() {
                @Override
                protected List<Book> doInBackground(String... params) {
                    return Book.getBookByCategory(params[0]);
                }

                @Override
                protected void onPostExecute(List<Book> result) {

                    SimpleAdapter adapter =
                            new SimpleAdapter(getApplicationContext(), result,
                                    R.layout.row,
                                    new String[]{"title", "author"},
                                    new int[]{R.id.textView1, R.id.textView2});
                    setListAdapter(adapter);
                }
            }.execute(filter);
        }

        String test = "This is just a test for github";


    }

    @SuppressLint("StaticFieldLeak")
    public void showAll(){
        new AsyncTask<Void, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(Void... params) {
                return Book.listBook();
            }

            @Override
            protected void onPostExecute(List<Book> result) {

                SimpleAdapter adapter =
                        new SimpleAdapter(getApplicationContext(), result,
                                R.layout.row,
                                new String[]{"title", "author"},
                                new int[]{R.id.textView1, R.id.textView2});
                setListAdapter(adapter);
            }
        }.execute();
    }
    @Override
    protected void onListItemClick(ListView l, View v,
                                   int position, long id) {
        Book  b= (Book) getListAdapter().getItem(position);
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", b.get("id"));
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item1:
                Intent intent5 = new Intent(this, ListActivity.class);
                startActivity(intent5);
                return true;
            case R.id.item2:
                Intent intent1 = new Intent(this, ListActivity.class);
                intent1.putExtra("filter", "non-fiction");
                startActivity(intent1);


                return true;
            case R.id.item3:
                Intent intent2 = new Intent(this, ListActivity.class);
                intent2.putExtra("filter", "children");
                startActivity(intent2);

                return true;
            case R.id.item4:
                Intent intent3 = new Intent(this, ListActivity.class);
                intent3.putExtra("filter", "finance");
                startActivity(intent3);

                return true;

            case R.id.item5:
                Intent intent4 = new Intent(this, ListActivity.class);
                intent4.putExtra("filter", "technical");
                startActivity(intent4);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
