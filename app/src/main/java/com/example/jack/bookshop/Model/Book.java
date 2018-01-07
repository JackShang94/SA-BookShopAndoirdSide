package com.example.jack.bookshop.Model;

import com.example.jack.bookshop.Controller.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 12/21/2017.
 */

public class Book extends java.util.HashMap<String,String>{
    final static String host = "http://192.168.75.1:8080/Service1.svc";

    public Book(String id, String title, String author, String isbn, String category, String stock, String price) {
        put("id", id);
        put("title", title);
        put("author", author);
        put("isbn", isbn);
        put("category",category);
        put("stock",stock);
        put("price",price);
    }

    public Book(){}

    public static List<Book> listBook() {
        List<Book> list = new ArrayList<Book>();
        try {
            JSONArray a = HttpHandler.getJSONArrayFromUrl(host+"/Books");
            for (int i=0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                Book c = InitBook(b);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Book InitBook(JSONObject j)
    {
        Book b = null;
        try {
            b = new Book(j.getString("id"), j.getString("title"), j.getString("author"),
                    j.getString("isbn"), j.getString("category"), j.getString("stock"), j.getString("price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;
    }
    public static List<String> listCategories() {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = HttpHandler.getJSONArrayFromUrl(host+"/Categories");
            for (int i=0; i<a.length(); i++) {
                String c = a.getString(i);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Book getBookByID(String id) {
        Book b = null;
        try {
            JSONObject c = HttpHandler.getJSONFromUrl(host+"/Book/"+id);
            b = new Book(
                    Integer.toString(c.getInt("id")),
                    c.getString("title"),
                    c.getString("author"),
                    c.getString("isbn"),
                    Integer.toString(c.getInt("category")),
                    Integer.toString(c.getInt("stock")),
                    Double.toString(c.getDouble("price")));
        } catch (Exception e) {
        }
        return b;
    }

    public static List<Book> getBookByCategory(String category) {
        List<Book> list = new ArrayList<Book>();
        try {
            JSONArray a = HttpHandler.getJSONArrayFromUrl(host+"/Book-category/"+category);
            for (int i=0; i<a.length(); i++) {
                JSONObject j = a.getJSONObject(i);
                Book c = new Book (j.getString("id"), j.getString("title"), j.getString("author"),
                        j.getString("isbn"), j.getString("category"), j.getString("stock"), j.getString("price"));
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Book getBookByTitle(String title) {
        Book result = new Book();
        List<JSONObject> list = new ArrayList<JSONObject>();
        try {
            JSONArray a = HttpHandler.getJSONArrayFromUrl(host+"/Book-title/"+title);
            for (int i=0; i<a.length(); i++) {
                JSONObject c = a.getJSONObject(i);
                list.add(c);
            }
        } catch (Exception e) {
        }
        if (list.isEmpty()!=true){
            result = InitBook(list.get(0));
        }
        return result;
    }

    public static List<String> getBookByAuthor(String author) {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = HttpHandler.getJSONArrayFromUrl(host+"/Book-author/"+author);
            for (int i=0; i<a.length(); i++) {
                String c = a.getString(i);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void updateBook(Book b) {
        JSONObject jbook = new JSONObject();
        try {
            jbook.put("id", Integer.parseInt(b.get("id")));
            jbook.put("title", b.get("title"));
            jbook.put("author", b.get("author"));
            jbook.put("isbn", b.get("isbn"));
            jbook.put("category", Integer.parseInt(b.get("category")));
            jbook.put("stock", Integer.parseInt(b.get("stock")));
            jbook.put("price", Double.parseDouble(b.get("price")));
        } catch (Exception e) {
        }
        String result = HttpHandler.postStream(host+"/Update", jbook.toString());
    }
}
