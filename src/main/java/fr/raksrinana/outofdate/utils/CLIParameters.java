package fr.raksrinana.outofdate.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import picocli.CommandLine;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor
@Getter
@CommandLine.Command(name = "outofdate", mixinStandardHelpOptions = true)
public class CLIParameters{
	@CommandLine.Option(names = {
			"-db",
			"--db-file"
	},
			description = "Path to the database file to use")
	private Path dbPath = Paths.get("outofdate.db");
}
