package WaysideController;

import java.io.*;
import javax.swing.*;


public class TextAreaOutputStream extends OutputStream {
    private JTextArea textControl;

    public TextAreaOutputStream( JTextArea control ) {
        textControl = control;
    }

    public void write( int b ) throws IOException {
        // append the data as characters to the JTextArea control
        textControl.append( String.valueOf( ( char )b ) );
    }
}
