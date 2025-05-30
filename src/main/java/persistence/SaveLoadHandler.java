package persistence;

import model.GameState;

import java.io.*;

public class SaveLoadHandler {
    public void saveGame(final String theFilePath, final GameState theGameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(theFilePath))) {
            oos.writeObject(theGameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameState loadGame(final String theFilePath) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(theFilePath))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}