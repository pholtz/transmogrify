package org.driftwood.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.google.common.collect.Lists;

public class BaseMenu {

	public static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(220, 220, 220));
        menuBar.setPreferredSize(new Dimension(200, 20));
        menuBar.add(BaseMenu.createMenu("Transmogrify", Lists.newArrayList("Read", "Process", "Write")));
        return menuBar;
	}
	
	public static JMenu createMenu(String name, List<String> items) {
		JMenu menu = new JMenu();
		menu.setMnemonic(KeyEvent.VK_A);
		menu.setName(name);
		menu.setText(name);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		for(String item : items) {
			menu.add(BaseMenu.createMenuItem(item));
		}
		return menu;
	}
	
	public static JMenuItem createMenuItem(String name) {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setName(name);
		menuItem.setText(name);
		return menuItem;
	}
}
