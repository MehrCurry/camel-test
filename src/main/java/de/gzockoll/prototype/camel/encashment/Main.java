package de.gzockoll.prototype.camel.encashment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.camel.CamelContext;
import org.apache.commons.lang.Validate;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import de.gzockoll.prototype.camel.encashment.service.EncashmentService;

@SuppressWarnings("javadoc")
@Component
public class Main implements MessageHandler {

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

	private static ClassPathXmlApplicationContext springContext;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		springContext = new ClassPathXmlApplicationContext(new String[] {
				"/amq-beans.xml", "/data-beans.xml", "/control-beans.xml",
				"/camel-beans.xml" });
	}

	private CamelContext context;
	private JFrame frame;
	@Autowired
	private EncashmentService service;

	@PostConstruct
	public void run() throws Exception {

		startEncashment();
		showFrame();

	}

	private void startEncashment() {
		Validate.notNull(service);
		service.populateDatabase();
	}

	/**
     *
     */
	private void onExit() {
		springContext.close();
		System.exit(0);
	}

	/**
     *
     */
	private void showFrame() {
		frame = new JFrame();
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

		frame.setSize(200, 200);
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
}
