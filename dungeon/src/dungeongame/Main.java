package dungeongame;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static final Random random = new Random();
	public static final Scanner scanner = new Scanner(System.in);

	public static int random(int size, int start) {
		return random.nextInt(size) + start;
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
				number = scanner.nextInt();
			} catch (InputMismatchException e) {
				e.printStackTrace();
				scanner.nextLine();
			}
			if (number < 0)
				continue;
			return number;
		}
	}

	public static char selectString(String message) {
		while (true) {
			System.out.printf("%s : ", message);
			char m = scanner.next().charAt(0);
			return m;
		}
	}

	public static void main(String[] args) {

		Game game = new Game();
		game.run();

	}
}
