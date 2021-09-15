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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private final JRadioButton parserDelimiterButton;
	private final JRadioButton parserRegexButton;
	private final JRadioButton parserNoneButton;
	private final ButtonGroup parserButtonGroup;

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
		
		this.parserDelimiterButton = new JRadioButton("Delimited");
		this.parserRegexButton = new JRadioButton("Regular Expression");
		this.parserNoneButton = new JRadioButton("None");
		this.parserButtonGroup = new ButtonGroup();
		this.parserButtonGroup.add(this.parserDelimiterButton);
		this.parserButtonGroup.add(this.parserRegexButton);
		this.parserButtonGroup.add(this.parserNoneButton);
		
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
		previewTextLayout.weightx = 0;
		previewTextLayout.weighty = 0;
		previewTextLayout.ipadx = 40;
		previewTextLayout.ipady = 10;
		previewTextLayout.gridwidth = 3;
		previewTextLayout.gridheight = 1;
		previewTextLayout.insets = new Insets(10, 10, 10, 10);
		super.add(this.previewTextScroll, previewTextLayout);
		
		super.revalidate();
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
	
	public void loadParserInterface() {
		GridBagConstraints parserDelimiterLayout = new GridBagConstraints();
		parserDelimiterLayout.fill = GridBagConstraints.BOTH;
		parserDelimiterLayout.gridx = 0;
		parserDelimiterLayout.gridy = 3;
		parserDelimiterLayout.weightx = 1;
		parserDelimiterLayout.weighty = 1;
		parserDelimiterLayout.ipadx = 20;
		parserDelimiterLayout.ipady = 10;
		parserDelimiterLayout.gridwidth = 1;
		parserDelimiterLayout.gridheight = 1;
		parserDelimiterLayout.insets = new Insets(10, 10, 10, 10);
		super.add(this.parserDelimiterButton, parserDelimiterLayout);
		
		GridBagConstraints parserRegexLayout = new GridBagConstraints();
		parserRegexLayout.fill = GridBagConstraints.BOTH;
		parserRegexLayout.gridx = 0;
		parserRegexLayout.gridy = 3;
		parserRegexLayout.weightx = 1;
		parserRegexLayout.weighty = 1;
		parserRegexLayout.ipadx = 20;
		parserRegexLayout.ipady = 10;
		parserRegexLayout.gridwidth = 1;
		parserRegexLayout.gridheight = 1;
		parserRegexLayout.insets = new Insets(10, 10, 10, 10);
		super.add(this.parserRegexButton, parserRegexLayout);
		
		GridBagConstraints parserNoneLayout = new GridBagConstraints();
		parserNoneLayout.fill = GridBagConstraints.BOTH;
		parserNoneLayout.gridx = 0;
		parserNoneLayout.gridy = 3;
		parserNoneLayout.weightx = 1;
		parserNoneLayout.weighty = 1;
		parserNoneLayout.ipadx = 20;
		parserNoneLayout.ipady = 10;
		parserNoneLayout.gridwidth = 1;
		parserNoneLayout.gridheight = 1;
		parserNoneLayout.insets = new Insets(10, 10, 10, 10);
		super.add(this.parserNoneButton, parserNoneLayout);
		
		super.revalidate();
	}

	private void appendText(String text) {
		this.previewText.append(text + "\n");
	}
}
