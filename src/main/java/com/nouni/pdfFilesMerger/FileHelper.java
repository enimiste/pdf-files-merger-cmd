package com.nouni.pdfFilesMerger;

import java.io.File;

public class FileHelper {
	/**
	 * Checks if the file/folder given as inputFile in the FileData object has a
	 * given extension or not
	 * 
	 * @param file
	 * @param exts
	 * @return
	 */
	public static boolean hasOneOfExtensions(File file, String... exts) {
		for (String ext : exts) {
			if (file.toPath().toString().toLowerCase().endsWith("." + ext))
				return true;
		}
		return false;
	}
}
