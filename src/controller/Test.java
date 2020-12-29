package controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Test {

	@SuppressWarnings("restriction")
	public static void main(String[] args) {
			
		/*
		 * try { Document document =
		 * Jsoup.connect("https://oldschool.runescape.wiki/w/Exchange:Dragon_scimitar").
		 * get(); System.out.println(document.html()); String output =
		 * document.html().substring(document.html().indexOf("id=\"GEPrice\">")); output
		 * = output.substring("id=\"GEPrice\">".length(), output.indexOf("</span>"));
		 * 
		 * System.out.println(document.html()); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		
		
		String h = "01";
		String m = "13";
		String s = "49";
		
		String format1 = "[hour] - [minute] - [second]";
		String format2 = "[hour]:[minute]:[second]";
		String format3 = "[hour] hours, [minute] minutes, [second] seconds";
		
		h = removeLeadingZeroes(h);	
		
		System.out.println(setValues(h, m, s, format1));
		System.out.println(setValues(h, m, s, format2));
		System.out.println(setValues(h, m, s, format3));
		
		System.out.println();
		
		h = "00";
		
		System.out.println(setValues(h, m, s, format1));
		System.out.println(setValues(h, m, s, format2));
		System.out.println(setValues(h, m, s, format3));
		
		System.out.println();
		
		
		System.out.println(setValues(h, m, s, format1));
		System.out.println(setValues(h, m, s, format2));
		System.out.println(setValues(h, m, s, format3));
		
		System.out.println(ReadAndWrite.removeLeadingZeroes("00055"));
	}
	
	public static String setBlank(String tag, String format) {
		String output = new String(format);
		
		if (output.contains(tag)) {
			output.replace(tag, "");
		}
		
		return output;
	}
	
	public static String setValues(String h, String m, String s, String format) {
		String output = new String(format);
		
		if (output.contains("[hour]")) {
			output = output.replace("[hour]", h);
		}
		
		if (output.contains("[minute]")) {
			output = output.replace("[minute]", m);
		}
		
		if (output.contains("[second]")) {
			output = output.replace("[second]", s);
		}
			
		
		return output;
	}
	
	public static String removeLeadingZeroes(String value) {
		int temp = 0;
		String output = "";
		
		temp = Integer.parseInt(value);
		output = Integer.toString(temp);
			
		return output;
	}
}
