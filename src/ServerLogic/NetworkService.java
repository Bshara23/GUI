package ServerLogic;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NetworkService implements Runnable {

	private final ExecutorService pool;

	public NetworkService(int port, int poolSize) throws IOException {
		pool = Executors.newFixedThreadPool(poolSize);
	}

	public void run() { // run the service
//		try {
//			for (;;) {
//				pool.execute(new Handler(serverSocket.accept()));
//			}
//		} catch (IOException ex) {
//			pool.shutdown();
//		}
	}
}

class Handler implements Runnable {

	public void run() {
		// read and service request on socket
	}
}