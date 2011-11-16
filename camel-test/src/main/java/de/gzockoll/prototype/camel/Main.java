package de.gzockoll.prototype.camel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
    	context.getManagementStrategy().getManagementAgent().setCreateConnector(true);

        context.addRoutes(new MyRouteBuilder());

        context.setTracing(true);
        context.start();
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowEventHandler(context));
        frame.pack();
        frame.setVisible(true);
    }
    
    private static class WindowEventHandler extends WindowAdapter {
    	private CamelContext context;

		public WindowEventHandler(CamelContext context) {
			this.context=context;
		}

		@Override
    	public void windowClosing(WindowEvent e) {
    		super.windowClosing(e);
            try {
				context.stop();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				System.exit(0);
			}
    	}
    }
}
