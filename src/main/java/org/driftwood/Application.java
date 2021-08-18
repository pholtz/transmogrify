package org.driftwood;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.driftwood.menu.BaseMenu;
import org.driftwood.panel.BasePanel;

public class Application {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Transmogrify");
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setJMenuBar(BaseMenu.createMenuBar());
				frame.setContentPane(new BasePanel());
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}