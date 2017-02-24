package proj1;

import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

public class Launch {
	public static void main(String [] args) throws IOException {
		new Launch();
	}
	public Launch() throws IOException{
		try {
			wayside_gui_main WaysideController = new wayside_gui_main();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
