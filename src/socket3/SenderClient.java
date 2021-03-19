package socket3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SenderClient {
	private Socket socket;
	
	private SenderClient(InetAddress serverAddress, int serverPort) throws Exception {
		this.socket = new Socket(serverAddress, serverPort);
	}

	private void start() throws IOException, InterruptedException {
		String input;
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		DataInputStream in = new DataInputStream(this.socket.getInputStream());
		byte[] buffer = new byte[50];

		while (true) {
			String pedro = GenerateRandomBitString();
			System.out.println("Sent: "+pedro);
			out.print(pedro);
			out.flush();
			int bytesRead = 0;
			bytesRead = in.read(buffer);
			input = new String(buffer, 0, bytesRead);
//			System.out.println(input);
			if (input.contentEquals("1")) {
//				System.out.println("Giusto");
				break;
			}
//			else {
//				System.out.println("Sbagliato");
//				break;
//			}
		}
		
		System.out.println("Client terminate.");
		socket.close();
	}
	
	private String GenerateRandomBitString() {
		String result = "";
		
		int sum = 0;
		for(int i = 0; i < 32; i++) {
			int randN = (int) Math.round(Math.random());
			result += Integer.toString(randN);
			sum += randN;
		}
		
		return result += (sum%2);
	}
	
	private String GenerateRandomBitStringTEST() {
		String result = "";
		
		for(int i = 0; i < 32; i++) {
			int randN = (int) Math.round(Math.random());
			result += Integer.toString(randN);
		}
		
		return result;
	}

	public static void main(String[] args) throws Exception {
		SenderClient client = new SenderClient(InetAddress.getByName("localhost"),7575);

		System.out.println("Connected to: " + client.socket.getInetAddress());
		client.start();

	}

}
