package com.zucchetti.tomcatarchive.fs.auth.impl;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zucchetti.tomcatarchive.fs.auth.IAuthResourceReader;

public class AuthHtpasswdResourceReader implements IAuthResourceReader
{
	private static final String NOT_FOUND = "fakepwd";
	private final Path passwdFilePath;

	public AuthHtpasswdResourceReader(Path passwdFilePath) 
	{
		this.passwdFilePath = passwdFilePath;
	}

	@Override
	public String read(String username)
	{
		try(BufferedReader newBufferedReader = Files.newBufferedReader(passwdFilePath); )
		{
			Pattern entry = Pattern.compile("^([^:]+):(.+)");
			String line = newBufferedReader.lines()
					.filter(l -> l.startsWith(username) && !l.isEmpty() && !l.startsWith("#") && entry.matcher(l).matches())
					.findFirst().orElse(NOT_FOUND);
			if(!line.equals(NOT_FOUND))
			{
				Matcher m = entry.matcher(line);
				m.find();
				return m.group(2);
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
