/*
 * Code to implement a spell check for the following class :-
 * Case (upper/lower) errors: "inSIDE" => "inside"
 * Repeated letters: "jjoobbb" => "job"
 * Incorrect vowels: "weke" => "wake"
 */
import java.io.*;
import java.lang.*;
import java.util.*;


/**
 *
 * @author isingh
 */
public class SpellChecker {
  
    String filePath; 
    Map<String ,String> rwDictionary; //Reduced word Dictionary. Removes repeated letters and converts vowels to '*' e.g. "sh*p" => "Sheep"
    Map<String, String> lcDictionary; // Lower-case Dictionary. e.g. "sheep" => "Sheep"

    public SpellChecker(String path) {
    filePath = path;
    rwDictionary = new HashMap<String, String>();
    lcDictionary = new HashMap<String, String>();
    populateDictionary();
  }

 
  /**
   * Function to populate rwDictionary and lcDictionary using the words in 
   * actual dictionary.
   */ 
  private void populateDictionary() {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new FileInputStream(filePath));
      while(scanner.hasNextLine()) {
        String word = scanner.nextLine();
        rwDictionary.put(reducedWord(word), word);
        lcDictionary.put(word.toLowerCase(), word);
      }
    } catch(IOException e) {
      System.err.println(e.toString());
      e.printStackTrace();
    } finally {
      if(scanner != null) {
        scanner.close();
      }
    }
  }

  /**
   * Function to return suggested spelling of the input word. If no suitable 
   * word is found then "No Suggestion" is returned.
   */
  public String spellcheck(String word) {
    // Check if the world is already in lcDictionary.
    String lcdictionaryLookup = lcDictionary.get(word.toLowerCase());
    if(lcdictionaryLookup != null) {
      return lcdictionaryLookup;
    }
    String reducedWord = reducedWord(word);
    String rwdictionaryLookup = rwDictionary.get(reducedWord);
    if(rwdictionaryLookup != null) {
      return rwdictionaryLookup;
    }
    return "NO SUGGESTION";
  }
  
  /**
   * Function to return the reduced form of the input word. 
   */
  public static String reducedWord(String word) {
      String lcWord = word.toLowerCase();
      String redVowels = convertVowels(lcWord);
      String noDuplicates = removeDuplicate(redVowels);
      return noDuplicates;
  }

  /**
   * Function to change vowels to '*'.
   */
  public static String convertVowels(String word) {
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      if (((c == 'a') || (c == 'e') || (c == 'i') || (c == 'o') || (c == 'u'))){
        sb.append("*");
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * Function to remove duplicate letters if contiguous.
   */  
  public static String removeDuplicate(String word) {
    StringBuffer sb = new StringBuffer();
    if(word.length() <= 1) {
      return word;
    }
    char current = word.charAt(0);
    sb.append(current);
    for(int i = 1; i < word.length(); i++) {
      char c = word.charAt(i);
      if(c != current) {
        sb.append(c);
        current = c;
      }
    }
    return sb.toString();
  }

  /**
   * Entry point of the code. Reads words continuously from stdin.
   */  

  public static void main(String[] args) {
    String filePath = "/usr/share/dict/words";
    SpellChecker sc = new SpellChecker(filePath);
    String line;
    System.out.print("> ");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    try {
      while((line = input.readLine())!= null) {
      String correctSpelling = sc.spellcheck(line);
      System.out.println(correctSpelling);
      System.out.print("> ");
     }
    System.out.println();
   } catch (IOException e){
       e.printStackTrace();
   }
 }
}
