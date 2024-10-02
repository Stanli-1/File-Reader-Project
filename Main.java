import java.util.*;
import java.io.*;

// The text chosen is the first letter of "Frankenstein; Or, The Modern Prometheus"
//     Reference: https://www.gutenberg.org/ebooks/84

class Main {
  public static void main(String[] args) {
    // Created a file for the first letter of Frankenstein
    File textFile = new File("Frankenstein.txt");
    // FileReader is for reading streams of characters in the file
    FileReader input;
    // Bufferes files for efficiency, especially for larger files
    BufferedReader readFile;
    // This variable stores a given line of text from the dictionary.out file
    String lineOfText;
    // This variable will keep track of the total words in the letter
    int totalWords = 0;
    // This arraylist will keep track of the longest words in the letter
    //     It is an arraylist because there could be multiple longest words
    ArrayList <String> longestWords = new ArrayList();
    // This is so I can store the first word of the letter as the longest word
    boolean firstWord = true;

/*  
    This try catch is for opening the Frankenstein text file and making a dictionary for it. This will be written to a new file called "dictionary.out". Excpetion handling is used here because checked exceptions, such as FileNotFoundException, can be thrown. 

    As for how a dictionary will be created, I will use a TreeMap. Similar to a dictionary in python, there will be a Key and a value that is accociated to it. The good thing about TreeMaps are that they are automatically sorted alphabetically. In this case, the key will be every unique word, and the value will be how many times it is seen in the letter. Firstly, each line will be converted to an array of strings, split by spaces, so each element is a word. Then, each element needs to be checked if its over 2 letters, or if it contains integers. Then, the program loops through the array and checks if the word is already stored. If it is, incriment it and if it is not, add it to the treemap. I also check if a new word is the longest one yet, if it is I add it to the ArrayList "longestWords". The first word in the letter is automatically stored as the longest word by default, hence the use of the "firstWord" boolean.

*/

    // A new TreeMap called "dictionary" will contain String Integer pairs (Refer to references 2 and 3)
    TreeMap<String, Integer> dictionary = new TreeMap<>();
      
    try {
        input = new FileReader(textFile);
        readFile = new BufferedReader(input);  
        // Creates new file for my dictionary
        File dictionaryOutput = new File("dictionary.out");
        // The second boolean parameter is to indicate whether the program should override or append the "dictionary.out" file that is created on the line above. True = append and False = override ( Refer to references 1 and 8)
        FileWriter myWriter = new FileWriter("dictionary.out", false);
        // Reads line by line through the Frankenstein.txt file
        while ((lineOfText = readFile.readLine()) != null) {
            // Makes everything lowercase
            lineOfText = lineOfText.toLowerCase();
            // Splits the line by spaces and punctuation and puts each word into an array (Reference 5)
            String[] words = lineOfText.split("([.,!?:;'\"-]|\\s)+");

            // Loops through the array "words" 
            for (int i = 0; i < words.length; i++) {
                // Check if the word is over 2 letters long, if it is, skip this word
                if ( words[i].length() <= 2 ) {
                    continue;
                }
                // Check if the word has an integer, if it does, skip the word (Reference 6)
                if ( words[i].contains("0") || words[i].contains("1") || words[i].contains("2") || words[i].contains("3") || words[i].contains("4") || words[i].contains("5") || words[i].contains("6") || words[i].contains("7") || words[i].contains("8") || words[i].contains("9") ) {
                    continue;
                }
                // Store the first word as the longest word by default
                if ( firstWord == true ) {
                    longestWords.add(words[i]);   
                }
                // This will return true if this entry does exist
                //    This means that this is not a new word
                if ( dictionary.containsKey(words[i]) ) {
                    // If word already exists, update it by incrimenting the count by 1
                    dictionary.put(words[i], dictionary.get(words[i]) + 1);
                }
                // Since there is a new word, add it to the dictionary with a count of 1
                else {
                    dictionary.put(words[i], 1);
                    // Also check if it is the longest word yet
                    // If there is a word thats longer, clear and add it
                    if ( longestWords.get(0).length() < words[i].length() ) {
                        longestWords.removeAll(longestWords);
                        longestWords.add(words[i]);
                    }
                    // If its tied with the longest unique, add it
                    else if ( longestWords.get(0).length() == words[i].length() ) {
                        longestWords.add(words[i]);
                    }
                }
                // So the program does not store the second word 
                firstWord = false;
            } //for loop   
        } // While

        // Writes the dictionary into the file "dictionary.out" (Refer to reference 4)
        for ( Map.Entry<String, Integer> entry: dictionary.entrySet() ) {
            myWriter.write( entry.getKey() + " = " + entry.getValue() + "\n" );
            // Counts total amount of words
            totalWords += entry.getValue();
        }
        // Outputs the total amount of words recorded
        myWriter.write("\nThe total word count of this letter is " + totalWords);
        
        // Outputs the longest words into the console 
        System.out.println("\nThe longest word(s) in Frankenstein's Letter 1: ");
        for ( int i = 0; i < longestWords.size(); i++ ) {
            System.out.println(" - " + longestWords.get(i));
        }        
        System.out.println("Longest word(s) character count: " + longestWords.get(0).length() );

        // Closes scanners 
        readFile.close();
        input.close();
        myWriter.close();

        // Prints the the input and output file size (Reference 7)
        System.out.println("\nThe input file is " + textFile.length() + " bytes.");
        System.out.println("The output file is " + dictionaryOutput.length() + " bytes.");
        
    } //try
      
    // This exception will be thrown if the file cannot be found
    catch (FileNotFoundException e) {
        System.out.println("File does not exist or could not be found.");
        System.err.println("FileNotFoundException; " + e.getMessage());
    }
      
    // This exception will be thrown if the file is being used somewhere else
    catch (IOException e) {
        System.out.println("A error has occured.");
        System.err.println("IOExcpetion; " + e.getMessage());
    }
        
  } //main
} //Main