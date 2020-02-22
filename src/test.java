import java.io.*;
import java.util.*;

public class test {

	// Complete the caesarCipher function below.
	static String caesarCipher(String s, int k) {
		String text = s.trim().replaceAll("\\s+", "");
		String Cipher = new String();

		// ------------ Decryption Process ----------
		int Asc = 0;
		System.out.println(text);
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) >= 97 && text.charAt(i) <= 122) {
				Asc = text.charAt(i) + k;
				if (Asc >= 122)
					Asc = (Asc + 96) % 122;
				Cipher += Character.toString(Asc);
			} else if (text.charAt(i) >= 65 && text.charAt(i) <= 90) {
				Asc = text.charAt(i) + k;
				if (Asc >= 90)
					Asc = (Asc + 64) % 90;
				Cipher += Character.toString(Asc);
			} else {
				Cipher += text.charAt(i);
			}
		}
		System.out.println("CipherText: " + Cipher);
		return Cipher;

	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {

		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		String s = scanner.nextLine();

		int k = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		String result = caesarCipher(s, k);
		// ------------ Decryption Process ----------
		int Asc = 0;
		String text = new String();
		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) >= 97 && result.charAt(i) <= 122) {
				Asc = result.charAt(i) - k;
				if (Asc < 97)
					Asc = (Asc + 26);
				text += Character.toString(Asc);
			} else if (result.charAt(i) >= 65 && result.charAt(i) <= 90) {
				Asc = result.charAt(i) - k;
				if (Asc < 65)
					Asc = (Asc + 26);
				text += Character.toString(Asc);
			} else {
				text += result.charAt(i);
			}
		}
		System.out.println("PlainText: " + text);

		scanner.close();
	}
}
