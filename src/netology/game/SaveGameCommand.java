package netology.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveGameCommand implements Command {

    private final String savePath;
    private final GameProgress gameProgress;

    public SaveGameCommand(String savePath, GameProgress gameProgress) {
        this.savePath = savePath;
        this.gameProgress = gameProgress;
    }

    @Override
    public void execute() throws Exception {

        File file = new File(this.savePath);
        if (file.exists()) {
            throw new RuntimeException("File " + this.savePath + " already exists");
        }

        FileOutputStream fileOutputStream = new FileOutputStream(this.savePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.gameProgress);
        objectOutputStream.close();
        fileOutputStream.close();
    }
}
