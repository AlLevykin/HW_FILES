package main.java;

import netology.game.Command;
import netology.game.InstallCommand;

public class Main {

    private static final String ROOT_PATH = "d:/games";

    public static void main(String[] args) {

        Command command = new InstallCommand(ROOT_PATH);
        try {
            command.execute();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

    }
}
