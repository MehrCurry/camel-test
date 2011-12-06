package de.gzockoll.prototype.camel.encashment;

public interface MessageHandler {
	void showMessage(String message);

	void showWarning(String message, Throwable t);

	void showError(String message, Throwable t);
}
