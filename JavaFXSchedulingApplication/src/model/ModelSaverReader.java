package model;

import utilities.Logger;

import java.io.*;

public class ModelSaverReader {
    private static final String filename = "res/saved-data/models.bin";

    public static void saveModel(Model model) {
        ObjectOutputStream out = null;
        try {
            File file = new File(filename);
            FileOutputStream fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(model);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error("Exception: " + filename);
        } finally {
            try {
                out.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Model readModel() {
        ObjectInputStream in = null;
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            try {
                Model m = (Model) in.readObject();
                return m;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidClassException e) {
                Logger.error("Couldn't load data: incompatible UID versions, regenerate model.bin");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}