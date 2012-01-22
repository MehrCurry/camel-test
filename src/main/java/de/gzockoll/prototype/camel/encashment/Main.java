package de.gzockoll.prototype.camel.encashment;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.camel.CamelContext;
import org.apache.commons.lang.Validate;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.gzockoll.prototype.camel.encashment.service.EncashmentService;
import eu.hansolo.steelseries.gauges.AbstractGauge;

@SuppressWarnings("javadoc")
public class Main implements MessageHandler, ApplicationContextAware {

	private class WindowEventHandler extends WindowAdapter {
		private final CamelContext context;

		public WindowEventHandler(CamelContext context) {
			this.context = context;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			onExit();
		}
	}

	private CamelContext context;
	private JFrame frame;
	@Autowired
	private EncashmentService service;
	private List<AbstractGauge> gauges;

	private ApplicationContext springContext;

	public void run(String[] args) {
		startEncashment();
		showFrame();
	}

	public void setGauges(List<AbstractGauge> gauges) {
		this.gauges = gauges;
	}

	private void startEncashment() {
		Validate.notNull(service);
		service.populateDatabase();
	}

	/**
     *
     */
	private void onExit() {
		System.exit(0);
	}

	/**
     *
     */
	private void showFrame() {
		frame = new JFrame();
		frame.setLayout(new GridLayout(2, 2));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowEventHandler(context));

		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic('x');
		file.add(exitItem);

		// adding action listener to menu items
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onExit();
			}
		});

		JMenuBar bar = new JMenuBar();
		frame.setJMenuBar(bar);
		bar.add(file);
		for (AbstractGauge g : gauges)
			frame.add(g);

		frame.setSize(400, 200);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(frame, message);

	}

	@Override
	public void showWarning(String message, Throwable t) {
		JXErrorPane.showDialog(null, new ErrorInfo("Warning",
				"Something happend", message, null, t, Level.WARNING, null));
	}

	@Override
	public void showError(String message, Throwable t) {
		JXErrorPane.showDialog(null, new ErrorInfo("Error", "Error delivering",
				message, null, t, Level.SEVERE, null));
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.springContext = ctx;
	}
}
