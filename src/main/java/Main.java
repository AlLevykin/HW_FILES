package main.java;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.function.Consumer;

import netology.game.*;

public class Main {

    private static final String ROOT_PATH = "d:/games";
    private static final String SAVE_GAMES_PATH = ROOT_PATH + "/savegames";
    private static final int MAX_PERCENT  = 100;
    private static final Double MAX_DISTANCE = 1000.0;

    public static void main(String[] args) {

        Consumer<Integer> saveGames = i -> {
            Random random = new Random();
            GameProgress gameProgress = new GameProgress(
                    random.nextInt(MAX_PERCENT),
                    random.nextInt(MAX_PERCENT),
                    random.nextInt(MAX_PERCENT),
                    random.nextDouble() * MAX_DISTANCE);

            String savePath = SAVE_GAMES_PATH + "/save" + i + ".dat";
            Command saveFirstGameCommand = new SaveGameCommand(savePath, gameProgress);
            try {
                saveFirstGameCommand.execute();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        };

        Command installCommand = new InstallCommand(ROOT_PATH);
        try {
            installCommand.execute();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        for(int i = 1; i < 4; i++) {
            saveGames.accept(i);
        }

        File saveDirectory = new File(SAVE_GAMES_PATH);
        if(saveDirectory.exists()) {
            try {
                String[] fileNames = Files.list(saveDirectory.toPath()).
                        filter(p -> p.getFileName().toString().endsWith(".dat")).
                        map(p -> p.toAbsolutePath().toString()).
                        toArray(String[]::new);

                Command zipFilesCommand = new ZipFilesCommand(SAVE_GAMES_PATH + "/zip.zip", fileNames);
                zipFilesCommand.execute();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
