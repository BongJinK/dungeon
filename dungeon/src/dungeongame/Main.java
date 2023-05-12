package dungeongame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

public class Main {
	public static final Random random = new Random();

	public static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static final StringBuilder sb = new StringBuilder();

	public static int random(int size, int start) {
		return random.nextInt(size) + start;
	}

	public static void write(String text) {
		try {
			bw.write(text);
			bw.flush();
		} catch (IOException e) {
		}
		Main.sb.setLength(0);
	}

	public static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int selectNumber(String message) {
		while (true) {
			int number = -1;
			try {
				System.out.printf("%s : ", message);
				number = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if (number < 0)
				continue;
			return number;
		}
	}

	public static char selectString(String message) {
		char m = ' ';
		try {
			System.out.printf("%s : ", message);
			m = br.readLine().charAt(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}

	public static void main(String[] args) {

		Game game = new Game();
		game.run();

	}
}
