import java.net.*;
import java.util.Scanner;
import java.io.*;

public class TCPClient {
	public static void main(String args[]) {
		// arguments supply message and hostname
		Socket s = null;
		try (Scanner input = new Scanner(System.in)) {
			try {
				Boolean condition = true;
				while (condition) {
					int serverPort = 7999;
					String IP = "127.0.0.1";
					s = new Socket(IP, serverPort);
					DataInputStream in = new DataInputStream(s.getInputStream());
					DataOutputStream out = new DataOutputStream(s.getOutputStream());
					System.out.print("Escreva sua Mensagem: ");
					String msg = input.next();
					out.writeUTF(msg); // UTF is a string encoding see Sn. 4.4
					String data = in.readUTF(); // read a line of data from the stream
					if (data != "404") {
						System.out.println("Mensagem Recebida" + data);
						condition = true;
					} else {
						condition = false;
					}
				}

			} catch (UnknownHostException e) {
				System.out.println("Socket:" + e.getMessage());
			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("readline:" + e.getMessage());
			} finally {
				if (s != null)
					try {
						s.close();
					} catch (IOException e) {
						System.out.println("close:" + e.getMessage());
					}
			}
		}
	}
}
