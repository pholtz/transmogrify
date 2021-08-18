package org.driftwood.panel;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BasePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JTextArea text;
	private final JScrollPane textScroll;
	private final ReaderPanel readerPanel;
	
	public BasePanel() {
		super(new BorderLayout());
		
		this.text = new JTextArea(5, 20);
		this.text.setMargin(new Insets(5, 5, 5, 5));
		this.text.setEditable(false);
		this.textScroll = new JScrollPane(this.text);
		this.appendText("Initialized!");
		
		this.readerPanel = new ReaderPanel();
//		super.add(this.textScroll);
		super.add(this.readerPanel);
		
	}
	
	public void actionPerformed(ActionEvent event) {
		this.appendText(event.toString());
	}
	
	private void appendText(String text) {
		this.text.append(text + "\n");
	}
}
