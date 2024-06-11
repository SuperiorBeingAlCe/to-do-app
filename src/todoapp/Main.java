package todoapp;

import javax.swing.SwingUtilities;

import todoapp.gui.TodoGui;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoGui();
            }
        });
	}

}
