package day1.part2;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Solution {

  private static final String DAY = "1";
  private static final String PART = "2";
  private static final Logger logger = Logger.getLogger(Solution.class.getName());

  private static final Boolean test = true;

  public static void main(String[] args) throws IOException {
    BufferedReader reader = null;
    try {
      Path path = Paths.get("src/main/java/day" + DAY + "/part" + PART + "/input.txt");

      reader = Files.newBufferedReader(path);
      ArrayList<String> inputs = reader.lines().collect(Collectors.toCollection(ArrayList::new));
      solution(inputs);
    } catch (IOException e) {
      logger.warning("Failed to read file");
      e.printStackTrace();
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }

  private static void solution (ArrayList<String> inputs) throws IOException {

    int sum = 0;
    HashMap<String, String> digits = new HashMap<>();
    fillDigits(digits);

    ArrayList<String> straightKeys = new ArrayList<>(digits.keySet());
    ArrayList<String> reversedKeys = digits.keySet().stream().map(Solution::reverseString)
        .collect(Collectors.toCollection(ArrayList::new));

    HashMap<Character, ArrayList<String>> straightMatchMap = createMatchMap(straightKeys);
    HashMap<Character, ArrayList<String>> reversedMatchMap = createMatchMap(reversedKeys);

    int count = 1;
    for (String input: inputs) {

      System.out.println("count " + count + ";  Digits " + digits.get(findFirstMatch(input, straightMatchMap)) + " " +
          digits.get(reverseString(findFirstMatch(reverseString(input), reversedMatchMap)))
          + "; Input " + input);

      sum += Integer.parseInt(
          digits.get(findFirstMatch(input, straightMatchMap))
              + digits.get(reverseString(findFirstMatch(reverseString(input), reversedMatchMap))));
      count++;
      System.out.println("-----------\n");
    }

    logger.info("Calibration: " + sum);
  }

  private static String findFirstMatch(String input, HashMap<Character, ArrayList<String>> matchMap) throws IOException {


    ArrayList<ArrayList<String>> possibleMatches = new ArrayList<>();

    for (int inputIndex = 0; inputIndex < input.length(); inputIndex++) {
      Character currentChar = input.charAt(inputIndex);
      System.out.println(input);

      // check if the current character is start of a new match
      // if yes add it to the matches at start and shift other matches to their respective next indices
      // else add null to start as we have to shift everything by one index any ways

      // Note: Adding to index 0 also means that the first character matches
      // this also means that the position of the matches list tells the number of characters that matched
      // if its index+1 matches its size, that means its a match!
      possibleMatches.add(0, matchMap.getOrDefault(currentChar, null));

      // looping through all the matches we have to check if something satisfies
      for (int possibleMatchIndex = 0; possibleMatchIndex < possibleMatches.size(); possibleMatchIndex++) {
        ArrayList<String> matches = possibleMatches.get(possibleMatchIndex);

        // when the index has a list and non null, loop through it
        if (matches != null) {
          List<Integer> indexesToRemove = new ArrayList<>();

          for (int matchIndex = 0; matchIndex < matches.size(); matchIndex++) {
            String match = matches.get(matchIndex);
            // check if the current index character is a match
            if (match.charAt(possibleMatchIndex) == input.charAt(inputIndex)) {
              // check if its a full match, if yes return it
              if (match.length() == possibleMatchIndex + 1) {
                return match;
              }
            } else {
              // if its not a match remove from matches
              indexesToRemove.add(matchIndex);
            }
          }
          removeIndexes(matches, indexesToRemove);
        }

        // if the list is empty null it
        if(matches != null && matches.isEmpty()) {
          possibleMatches.set(possibleMatchIndex, null);
        }

      }

      removeNullsInTheEnd(possibleMatches);
    }

    throw new IOException("No Matches Found");
  }

  private static void removeIndexes(ArrayList<String> matches, List<Integer> indexesToRemove) {
    int indicesRemoved = 0;
    for (Integer index : indexesToRemove) {
      matches.remove(index - indicesRemoved);
      indicesRemoved++;
    }
  }

  private static void removeNullsInTheEnd(ArrayList<ArrayList<String>> possibleMatches) {

    for (int i = possibleMatches.size() - 1; i >= 0; i--) {
      if (possibleMatches.get(i) != null) {
        return;
      }
      possibleMatches.remove(i);
    }

  }

  private static HashMap<Character, ArrayList<String>> createMatchMap(ArrayList<String> keys) {
    HashMap<Character, ArrayList<String>> matchMap = new HashMap<>();
    for (String key : keys) {
      Character firstLetter = key.charAt(0);
      matchMap.computeIfAbsent(firstLetter, k -> new ArrayList<>());
      matchMap.get(firstLetter).add(key);
    }
    return matchMap;
  }


  private static String reverseString(String string) {
    StringBuilder reversedString = new StringBuilder(string);
    return reversedString.reverse().toString();
  }

  private static void fillDigits(HashMap<String, String> digits) {
    digits.put("1", "1");
    digits.put("one", "1");
    digits.put("2", "2");
    digits.put("two", "2");
    digits.put("3", "3");
    digits.put("three", "3");
    digits.put("4", "4");
    digits.put("four", "4");
    digits.put("5", "5");
    digits.put("five", "5");
    digits.put("6", "6");
    digits.put("six", "6");
    digits.put("7", "7");
    digits.put("seven", "7");
    digits.put("8", "8");
    digits.put("eight", "8");
    digits.put("9", "9");
    digits.put("nine", "9");
  }

}