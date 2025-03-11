package netology.game;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Objects;

public class InstallCommand implements Command {

    private static final String[] subdirectories = {"src/main", "src/test", "res/drawables", "res/vectors", "res/icons", "savegames", "temp"};
    private static final String[] files = {"src/main/Main.java", "src/main/Utils.java", "temp/temp.txt"};
    private static final String logFilePath = "temp/temp.txt";
    private final String rootPath;

    public InstallCommand(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public void execute() throws Exception {
        File rootDir = new File(rootPath);
        StringBuilder log = new StringBuilder();

        if (!rootDir.isDirectory()) {
            throw new RuntimeException("Root directory does not exist");
        }

        if (Objects.requireNonNull(rootDir.list()).length > 0) {
            throw new RuntimeException("Root directory is not empty");
        }

        for (String sub : subdirectories) {
            File subDir = new File(rootPath, sub);
            if (!subDir.mkdirs()) {
                throw new RuntimeException(String.format("Failed to create directory %s", sub));
            }
            log.append(String.format("Created directory %s\n", sub));
        }

        for (String file : files) {
            File fileToCreate = new File(rootPath, file);
            if (!fileToCreate.getParentFile().exists()) {
                throw new RuntimeException(String.format("Failed to create file %s", file));
            }
            if (!fileToCreate.createNewFile()) {
                throw new RuntimeException(String.format("Failed to create file %s", file));
            }
            log.append(String.format("Created file %s\n", file));
        }

        Path logPath = Path.of(rootPath, logFilePath);
        FileWriter writer = new FileWriter(logPath.toString(), false);
        writer.write(log.toString());
        writer.close();

    }
}
