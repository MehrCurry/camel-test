package de.gzockoll.prototype.camel.measurement;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
	private static ClassPathXmlApplicationContext springContext;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		springContext = new ClassPathXmlApplicationContext(new String[] {

		"/measurement-beans.xml" });

		Main m = springContext.getBean(Main.class);
		m.run(args);
	}
}
