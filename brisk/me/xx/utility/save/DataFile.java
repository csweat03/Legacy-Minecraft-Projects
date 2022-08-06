package me.xx.utility.save;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class DataFile {

	private static String dir = "Brisk";
	
	public static List<String> read(final File inputFile) {
		final List<String> readContent = new ArrayList();
		try {
			final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
			String str;
			while ((str = in.readLine()) != null) {
				readContent.add(str);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readContent;
	}

	public static void write(final File outputFile, final List<String> writeContent) {
		try {
			final Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
			for (final String outputLine : writeContent) {
				out.write(String.valueOf(outputLine) + System.getProperty("line.separator"));
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File getDirectory() {
		final File file = new File(Minecraft.getMinecraft().mcDataDir, dir);
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	public static File getSubDirectory(String name) {
		final File file = new File(Minecraft.getMinecraft().mcDataDir, dir + "/" + name + "/");
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	public static File getFile(final String name, File directory) {
		final File file = new File(directory, String.format("%s.cfg", name));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static File getFile(final String name) {
		final File file = new File(getDirectory(), String.format("%s.cfg", name));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
