
public class Timer {
	public static double getTime() {
		// Get time and convert to seconds | Jesus
		return (double)System.nanoTime() / (double)1000000000L;
		
	}
}
