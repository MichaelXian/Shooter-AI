package Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
    public static String GENERATION_FILE_PATH = "Generation/generation.txt";
    public static String SAFE_QUIT_FILE_PATH = "quit.txt";
    public static File generationFile = new File(GENERATION_FILE_PATH);
    public static File safeQuitFile = new File(SAFE_QUIT_FILE_PATH);

    /**
     * Increments the generation stored in the generation file
     */
    public static void incrementGeneration() {
        try {
            // Get generation
            Scanner scanner = new Scanner(generationFile);
            // Increment generation
            int generation = scanner.nextInt() + 1;
            System.out.println("Generation" +  generation);
            // Write generation to file
            PrintWriter writer = new PrintWriter(generationFile);
            writer.print(generation);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets the generation stored in the generation file, and returns it
     * @return
     */
    public static int getGeneration() {
        int generation = getInteger(generationFile);
        return generation;
    }


    /**
     * Gets the value in the safeQuit file, and returns it
     * @return
     */
    public static int getSafeQuit() {
        Integer safeQuit = getInteger(safeQuitFile);
        return safeQuit;
    }

    private static Integer getInteger(File safeQuitFile) {
        try {
            // Get generation
            Scanner scanner = new Scanner(safeQuitFile);
            int generation = scanner.nextInt();
            // Return it
            return generation;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
