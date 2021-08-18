package org.driftwood.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BasePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Mode mode;
	private final ReaderPanel readerPanel;
	private final ProcessorPanel processorPanel;
	private final WriterPanel writerPanel;
	private final JButton nextButton;
	private final JPanel buttonPanel;
	
	public BasePanel() {
		super(new BorderLayout());
		
		this.readerPanel = new ReaderPanel();
		this.processorPanel = new ProcessorPanel();
		this.writerPanel = new WriterPanel();
		
		this.nextButton = new JButton("Next");
		this.nextButton.addActionListener(this);
		this.buttonPanel = new JPanel(new BorderLayout());
		this.buttonPanel.add(this.nextButton, BorderLayout.EAST);
		
		this.mode = Mode.READ;
		this.add(this.readerPanel, BorderLayout.CENTER);
		this.add(this.buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.nextButton) {
			if(this.mode == Mode.READ) {
				this.mode = Mode.PROCESS;
				super.remove(this.readerPanel);
				super.add(this.processorPanel);
				super.revalidate();
			} else if(this.mode == Mode.PROCESS) {
				this.mode = Mode.WRITE;
				super.remove(this.processorPanel);
				super.add(this.writerPanel);
				super.revalidate();
			}
		}
	}
	
	public void refresh() {
		switch(this.mode) {
			case READ:
			default:
				super.add(this.readerPanel);
				super.remove(this.processorPanel);
				super.remove(this.writerPanel);
				break;
			case PROCESS:
				super.remove(this.readerPanel);
				super.add(this.processorPanel);
				super.remove(this.writerPanel);
				break;
			case WRITE:
				super.remove(this.readerPanel);
				super.remove(this.processorPanel);
				super.add(this.writerPanel);
				break;
		}
	}
	
	public void changeMode(Mode mode) {
		this.mode = mode;
		this.refresh();
	}
	
	public static enum Mode {
		READ, PROCESS, WRITE;
	}
}
