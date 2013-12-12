public class Logger {
	private static Logger myInstance = new Logger();

	private Logger() {
	}

	public static Logger getLogger() {
		return myInstance;
	}
	
	public synchronized void log(String text) {
		// Write the text to a database or similar...
		try {
			// Simulate that this takes a little while.
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// Don't care.
		}
	}
}
