package org.driftwood.panel;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ReaderPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JButton openButton;
	private final JFileChooser fileChooser;
	private final JTextArea previewText;
	private final JScrollPane previewTextScroll;
	
	public ReaderPanel() {
		super(new BorderLayout());
		
		this.openButton = new JButton("Browse");
		this.openButton.addActionListener(this);
		
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		this.previewText = new JTextArea(5, 20);
		this.previewText.setMargin(new Insets(5, 5, 5, 5));
		this.previewText.setEditable(false);
		this.previewTextScroll = new JScrollPane(this.previewText);
		
		super.add(this.openButton, BorderLayout.PAGE_START);
		super.add(this.previewTextScroll, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.openButton) {
			int returnVal = this.fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = this.fileChooser.getSelectedFile();
				List<String> lines = new ArrayList<>();
				try {
					lines = Files.readAllLines(file.toPath());
				} catch (IOException e) {
					this.appendText(e.getMessage());
				}
				for(String line : lines) {
					this.appendText(line);
				}
			}
		}
	}
	
	private void appendText(String text) {
		this.previewText.append(text + "\n");
	}
}
