package day1.part2;


import shared.PatternMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//Answer: 54504
public class Solution {

  private static final String DAY = "1";
  private static final String PART = "2";
  private static final Logger logger = Logger.getLogger(Solution.class.getName());

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

    PatternMatcher straightPatternMatcher = new PatternMatcher(straightKeys);
    PatternMatcher reversedPatternMatcher = new PatternMatcher(reversedKeys);

    for (String input: inputs) {
      sum += Integer.parseInt(
          digits.get(straightPatternMatcher.findFirstMatched(input))
              + digits.get(reverseString(reversedPatternMatcher.findFirstMatched(reverseString(input)))));
    }

    logger.info("Calibration: " + sum);
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