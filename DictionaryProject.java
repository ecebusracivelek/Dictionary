
package proje2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryProject {
    private LinearProbingHash<String> dictionary;

    public DictionaryProject(int initialCapacity) {
        dictionary = new LinearProbingHash<>(initialCapacity);
    }

    public void loadDictionary(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[\\s\\p{Punct}]");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        // Convert all words to lowercase
                        word = word.toLowerCase();
                        dictionary.insert(word);
                    }
                }
            }
            System.out.println("Dictionary loaded successfully.");
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
    }

    public boolean search(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    public void insert(String word) {
        dictionary.insert(word.toLowerCase());
    }

    public void delete(String word) {
        dictionary.delete(word.toLowerCase());
    }

    public void performSpellCheck(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[\\s\\p{Punct}]");
                for (String word : words) {
                    word = word.toLowerCase();
                    if (!dictionary.contains(word)) {
                        System.out.println("Spelling error: " + word);
                    }
                }
            }
            System.out.println("Spell check completed.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DictionaryProject spellChecker = new DictionaryProject(6000000);
        Scanner scanner = new Scanner(System.in);

        int choice;
        String dictionaryFileName = null;

        do {
            System.out.println("Menu:");
            System.out.println("1. Load Dictionary");
            System.out.println("2. Search for a Word");
            System.out.println("3. Insert a Word");
            System.out.println("4. Delete a Word");
            System.out.println("5. Perform Spell Check");
            System.out.println("0. Exit");
            System.out.print("Your choice (0-5): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (dictionaryFileName == null) {
                        System.out.print("Enter the name of the dictionary file to be added: ");
                        dictionaryFileName = scanner.next();
                    }
                    spellChecker.loadDictionary(dictionaryFileName);
                    break;
                case 2:
                    if (dictionaryFileName == null) {
                        System.out.print("Enter the name of the dictionary file to be added: ");
                        dictionaryFileName = scanner.next();
                    }
                    System.out.print("Enter the word to search: ");
                    String searchWord = scanner.next();
                    boolean result = spellChecker.search(searchWord);
                    System.out.println("Result: " + result);
                    break;
                case 3:
                    if (dictionaryFileName == null) {
                        System.out.print("Enter the name of the dictionary file to be added: ");
                        dictionaryFileName = scanner.next();
                    }
                    System.out.print("Enter the word to insert: ");
                    String addWord = scanner.next();
                    spellChecker.insert(addWord);
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(dictionaryFileName, true))) {
                        writer.write("\n" + addWord);
                        writer.newLine();
                        System.out.println("Word inserted successfully.");
                    } catch (IOException e) {
                        System.err.println("Error writing to file: " + e.getMessage());
                    }
                    System.out.println("Result: " + addWord);
                    break;
                case 4:
                    if (dictionaryFileName == null) {
                        System.out.print("Enter the name of the dictionary file to be added: ");
                        dictionaryFileName = scanner.next();
                    }
                    System.out.print("Enter the word to delete: ");
                    String deleteWord = scanner.next();
                    try {
                            BufferedReader bufferedReader = new BufferedReader(new FileReader(dictionaryFileName));
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("newdict.txt"));

                        String satir;
                        while ((satir = bufferedReader.readLine()) != null) {
                            satir = satir.replaceAll("\\b" + deleteWord + "\\b", "");

                            bufferedWriter.write(satir);
                            bufferedWriter.newLine();
                        }
                        bufferedReader.close();
                        bufferedWriter.close();

                        new File(dictionaryFileName).renameTo(new File("newdict.txt"));
                        
                        new File("dict.txt").renameTo(new File("olddict.txt"));
                        new File("newdict.txt").renameTo(new File("dict.txt"));
                        
                        System.out.println("Deleting the word successfully.");
                    } catch (IOException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Enter the name of the file to check spelling: ");
                    String spellCheckFileName = scanner.next();
                    spellChecker.performSpellCheck(spellCheckFileName);
                    break;
                case 0:
                    System.out.println("Close the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
}



