package org.driftwood.panel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ReaderPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JLabel title;
	private final JButton openButton;
	private final JFileChooser fileChooser;
	private final JTextArea previewText;
	private final JScrollPane previewTextScroll;

	public ReaderPanel() {
		super(new GridBagLayout());

		this.title = new JLabel("Reader", SwingConstants.CENTER);
		this.title.setFont(new Font(this.title.getFont().getName(), this.title.getFont().getStyle(), 20));

		this.openButton = new JButton("Browse");
		this.openButton.addActionListener(this);

		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		this.previewText = new JTextArea(10, 80);
		this.previewText.setMargin(new Insets(5, 5, 5, 5));
		this.previewText.setEditable(false);
		this.previewTextScroll = new JScrollPane(this.previewText);
		this.previewTextScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		GridBagConstraints titleLayout = new GridBagConstraints();
		titleLayout.fill = GridBagConstraints.HORIZONTAL;
		titleLayout.gridx = 0;
		titleLayout.gridy = 0;
		titleLayout.weightx = 1;
		titleLayout.weighty = 0;
		titleLayout.ipadx = 80;
		titleLayout.ipady = 10;
		titleLayout.gridwidth = 3;
		titleLayout.gridheight = 1;
		titleLayout.insets = new Insets(10, 10, 10, 10);
		titleLayout.anchor = GridBagConstraints.PAGE_START;
		super.add(this.title, titleLayout);

		GridBagConstraints openButtonLayout = new GridBagConstraints();
		openButtonLayout.fill = GridBagConstraints.NONE;
		openButtonLayout.gridx = 0;
		openButtonLayout.gridy = 1;
		openButtonLayout.weightx = 0;
		openButtonLayout.weighty = 0;
		openButtonLayout.ipadx = 20;
		openButtonLayout.ipady = 10;
		openButtonLayout.gridwidth = 1;
		openButtonLayout.gridheight = 1;
		openButtonLayout.insets = new Insets(10, 10, 10, 10);
		super.add(this.openButton, openButtonLayout);

		GridBagConstraints previewTextLayout = new GridBagConstraints();
		previewTextLayout.fill = GridBagConstraints.BOTH;
		previewTextLayout.gridx = 0;
		previewTextLayout.gridy = 2;
		previewTextLayout.weightx = 1;
		previewTextLayout.weighty = 1;
		previewTextLayout.ipadx = 80;
		previewTextLayout.ipady = 10;
		previewTextLayout.gridwidth = 3;
		previewTextLayout.gridheight = 1;
		previewTextLayout.insets = new Insets(10, 10, 10, 10);
		super.add(this.previewTextScroll, previewTextLayout);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.openButton) {
			int returnVal = this.fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.previewText.setText(null);
				File file = this.fileChooser.getSelectedFile();
				List<String> lines = new ArrayList<>();
				try {
					lines = Files.readAllLines(file.toPath());
				} catch (IOException e) {
					this.appendText(e.getMessage());
				}
				for (String line : lines) {
					this.appendText(line);
				}
			}
		}
	}

	private void appendText(String text) {
		this.previewText.append(text + "\n");
	}
}
