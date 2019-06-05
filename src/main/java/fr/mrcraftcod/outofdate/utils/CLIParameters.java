package fr.mrcraftcod.outofdate.utils;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.PathConverter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Parameters for the command line.
 */
public class CLIParameters{
	@Parameter(names = {
			"--db-file",
			"-db"
	}, description = "Path to the database file to use", converter = PathConverter.class)
	private Path dbPath = Paths.get("outofdate.db");
	
	public CLIParameters(){
	}
	
	public Path getDbPath(){
		return dbPath;
	}
}
