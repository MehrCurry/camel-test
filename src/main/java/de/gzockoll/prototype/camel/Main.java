package de.gzockoll.prototype.camel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.interceptor.DefaultTraceFormatter;
import org.apache.camel.processor.interceptor.Tracer;

@SuppressWarnings("javadoc")
public class Main {

	private class WindowEventHandler extends WindowAdapter {
		private final CamelContext context;

		public WindowEventHandler(CamelContext context) {
			this.context = context;
			context.setTracing(true);
		}

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			onExit();
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new Main().run();
	}

	private CamelContext context;

	public void run() throws Exception {
		context = new DefaultCamelContext();
		context.getManagementStrategy().getManagementAgent()
				.setCreateConnector(true);

		context.addRoutes(new MyRouteBuilder());

		Tracer tracer = new Tracer();
		tracer.setTraceOutExchanges(true);

		// we configure the default trace formatter where we can
		// specify which fields we want in the output
		DefaultTraceFormatter formatter = new DefaultTraceFormatter();
		formatter.setShowOutBody(true);
		formatter.setShowOutBodyType(true);
		formatter.setShowHeaders(true);

		// set to use our formatter
		tracer.setFormatter(formatter);

		context.addInterceptStrategy(tracer);
		context.setTracing(true);
		context.start();
		showFrame();
	}

	/**
     *
     */
	private void onExit() {
		try {
			context.stop();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			System.exit(0);
		}
	}

	/**
     *
     */
	private void showFrame() {
		JFrame frame = new JFrame();
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
}
