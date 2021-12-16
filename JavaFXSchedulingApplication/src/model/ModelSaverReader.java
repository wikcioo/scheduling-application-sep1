package model;

import utilities.Logger;

import java.io.*;

/**
 * This class is used to read and write the Model to the binary file for persistence purposes.
 * It saves it in res/saved-data/models.bin.
 */
public class ModelSaverReader {
    private static final String filename = "res/saved-data/models.bin";

    /**
     * Writes the model to the memory in a binary format.
     *
     * @param model the model to be saved to the memory
     */
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

    /**
     * Reads the Model from the memory.
     *
     * @return the model that was read from the memory
     */
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