package de.gzockoll.prototype.camel.encashment;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
	private static ClassPathXmlApplicationContext springContext;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		springContext = new ClassPathXmlApplicationContext(new String[] {
				"/amq-beans.xml", "/data-beans.xml", "/control-beans.xml",
				"/camel-beans.xml", "/gui.xml" });

		Main m = springContext.getBean(Main.class);
		m.run(args);
	}
}
