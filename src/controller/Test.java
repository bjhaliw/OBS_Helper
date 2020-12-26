package controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {

	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		String url = "http://localhost:8080/requests/status.xml";

		String authString = ":p";
		org.jsoup.nodes.Element line;
		String string = "blank";
		String title = "<info name=\"title\">";
		String format = "[artist] - [title] - [title] - [artist] Hello how are you ";
		
		ArrayList<String> text = new ArrayList<>();
		ArrayList<String> tags = new ArrayList<>();
		
		tags.add("<info name=\"title\">");
		tags.add("<info name=\"artist\">");
		
		text.add("Artist Name");
		text.add("Song Name");
		
		title = new String(format);
		
		title = title.replaceAll("\\[artist\\]", text.get(0));
		title = title.replaceAll("\\[title\\]", text.get(1));
		
		System.out.println(title);
		
		/*
		 * try { Document document = Jsoup.connect(url).header("Authorization", "Basic "
		 * + javax.xml.bind.DatatypeConverter.printBase64Binary(authString.getBytes())).
		 * get();
		 * 
		 * // Document in String Form String docToString = document.toString();
		 * System.out.println(docToString); System.out.println("\n\n\n");
		 * 
		 * // Does the Document contain the Title tag?
		 * System.out.println(docToString.contains(title));
		 * 
		 * // Remove all crap before the string docToString =
		 * docToString.substring(docToString.indexOf(title)); docToString =
		 * docToString.substring(title.length(), docToString.indexOf("</info>"));
		 * docToString = docToString.trim(); System.out.println(docToString);
		 * 
		 * 
		 * 
		 * 
		 * for (int i = 0; i < document.getAllElements().size(); i++) { line =
		 * document.getAllElements().get(i); string = line.html(); if
		 * (string.contains("<info name=\"title\">")) { string =
		 * string.substring(string.indexOf("<info name=\"title\">")); string =
		 * string.substring(20, string.indexOf("</info>") - 2); string = string.trim();
		 * break; } }
		 * 
		 * 
		 * //System.out.println(string); } catch (MalformedURLException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); } catch (HttpStatusException
		 * e1) {
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}
}
