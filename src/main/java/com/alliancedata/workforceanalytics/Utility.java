package com.alliancedata.workforceanalytics;

/**
 * Contains static application-wide helper methods.
 */
public class Utility
{
	/**
	 * Gets a file's extension as a String.
	 * @param fileName The full path of the file to inspect.
	 * @return A {@code String} object containing the file's extension (without leading period).
	 */
	public static String getFileExtension(String fileName)
	{
		int dotIndex = fileName.lastIndexOf('.');
		int slashIndex = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		String extension = "";

		if (dotIndex > slashIndex)
		{
			extension = fileName.substring(dotIndex + 1);
		}

		return extension;
	}
}
