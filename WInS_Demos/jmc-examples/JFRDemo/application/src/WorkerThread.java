import javax.management.*;
import java.lang.management.*;


public class WorkerThread extends Thread {
	public final static Logger LOGGER = Logger.getLogger();
	private SLAReport reporter = null;

	public interface SLAReportMBean {
		public long getResponseTime();
	}

	public class SLAReport implements SLAReportMBean {
		private long timer;
		private long result = 0;

		public void startTimer() {
			timer = System.currentTimeMillis(); 
		}
		public void report(){
			result = System.currentTimeMillis() - timer;
		}
		public long getResponseTime() {
			return result;
		}
		public synchronized MBeanInfo getMBeanInfo() {
			return null;
		}

	}

	private int loopCount;

   public WorkerThread(int loopCount, boolean report, int id) {
      super("Workerthread-" + id);
		this.loopCount = loopCount;
		if (report){
			reporter = new SLAReport();
			// Get the platform MBeanServer
			MBeanServer mbs = null;
			ObjectName reporterName = null;

			mbs = ManagementFactory.getPlatformMBeanServer();
			try {
				reporterName = new ObjectName("SimpleAgent:name=SLAReport");
				mbs.registerMBean(reporter, reporterName);
			} catch(Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void run() {

		while (true) {
			int x = 0;
			int y = 0;
			if (reporter != null) reporter.startTimer();
			for (int i = 0; i < loopCount; i++) {
				x += i;
				y %= 510;
				if (x % (this.loopCount/200) == 0) {
					Thread.yield();
				}
			}					
			LOGGER.log("Thread reporting work done");
			if (reporter != null) reporter.report();	
			Thread.yield();
		}
	}
}
