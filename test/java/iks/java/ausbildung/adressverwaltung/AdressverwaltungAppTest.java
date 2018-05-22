package iks.java.ausbildung.adressverwaltung;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class AdressverwaltungAppTest {

	@Test
	void test() {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get("test/resources/expectedOutputFile.txt"));
			String expected =  new String(encoded, "ISO-8859-1");
			
			File inputFile = new File("test/resources/inputFile.txt");
			InputStream input = new FileInputStream(inputFile);

//			File outputFile = new File("test/resources/expectedOutputFile.txt");
//			PrintStream output = new PrintStream(outputFile);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream output = new PrintStream(baos, true, "UTF-8");
			
			AdressverwaltungApp app = new AdressverwaltungApp(input, output);
			app.run();
			
			String actual = new String(baos.toByteArray(), StandardCharsets.UTF_8);
			assertEquals(expected, actual);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
