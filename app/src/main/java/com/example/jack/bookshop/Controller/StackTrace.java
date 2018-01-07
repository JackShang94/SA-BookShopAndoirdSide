package com.example.jack.bookshop.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by jack on 12/21/2017.
 */

public class StackTrace {
    public static String trace(Exception ex) {
        StringWriter outStream = new StringWriter();
        ex.printStackTrace(new PrintWriter(outStream));
        return outStream.toString();
    }
}
