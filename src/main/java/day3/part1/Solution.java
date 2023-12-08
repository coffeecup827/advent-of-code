package day3.part1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//Answer: 546563
public class Solution {

  private static final String DAY = "3";
  private static final String PART = "1";
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

  private static void solution (ArrayList<String> inputs) {
    int sum = 0;
    int inputArrayIndex = 0;

    for (String input : inputs) {
      int matchedFirstIndex = 0;
      int lastIndex = 0;

      while(lastIndex < input.length()) {
        matchedFirstIndex = findFirstDigitIndex(matchedFirstIndex, input);

        if (matchedFirstIndex != -1) {
          lastIndex = findNumbersLastIndex(matchedFirstIndex + 1, input);
          if(hasAdjacentSymbol(inputs, inputArrayIndex, matchedFirstIndex, lastIndex)) {
            sum += Integer.parseInt(input.substring(matchedFirstIndex, lastIndex + 1));
          }

          matchedFirstIndex = lastIndex + 1;
        } else {
          lastIndex = input.length();
        }
      }
      inputArrayIndex++;
    }

    logger.info(String.valueOf(sum));
  }

  private static boolean hasAdjacentSymbol(ArrayList<String> inputs, int inputArrayIndex, int matchedFirstIndex, int lastIndex) {

    Boolean hasTop = checkTop(inputs, inputArrayIndex, matchedFirstIndex, lastIndex);
    Boolean hasBottom = checkBottom(inputs, inputArrayIndex, matchedFirstIndex, lastIndex);
    Boolean hasLeftSide = checkLeftSide(inputs, inputArrayIndex, matchedFirstIndex);
    Boolean hasRightSide = checkRightSide(inputs, inputArrayIndex, lastIndex);

    return hasTop || hasBottom || hasLeftSide || hasRightSide;
  }

  private static Boolean checkRightSide(ArrayList<String> inputs, int inputArrayIndex, int lastIndex) {
    int rightCharIndex = lastIndex + 1;
    int topLineIndex = inputArrayIndex - 1;
    int bottomLineIndex = inputArrayIndex + 1;

    if (rightCharIndex < inputs.get(inputArrayIndex).length()) {
      Boolean hasTopLineMatch = (topLineIndex >= 0 && isSymbol(inputs.get(topLineIndex).charAt(rightCharIndex)));
      Boolean hasBottomLineMatch = (bottomLineIndex < inputs.size() && isSymbol(inputs.get(bottomLineIndex).charAt(rightCharIndex)));
      Boolean hasInlineMatch = isSymbol(inputs.get(inputArrayIndex).charAt(rightCharIndex));

      return hasBottomLineMatch || hasTopLineMatch || hasInlineMatch;
    }
    return false;
  }

  private static Boolean checkLeftSide(ArrayList<String> inputs, int inputArrayIndex, int matchedFirstIndex) {
    int leftCharIndex = matchedFirstIndex - 1;
    int topLineIndex = inputArrayIndex - 1;
    int bottomLineIndex = inputArrayIndex + 1;

    if (leftCharIndex >= 0) {
      Boolean hasTopLineMatch = (topLineIndex >= 0 && isSymbol(inputs.get(topLineIndex).charAt(leftCharIndex)));
      Boolean hasBottomLineMatch = (bottomLineIndex < inputs.size() && isSymbol(inputs.get(bottomLineIndex).charAt(leftCharIndex)));
      Boolean hasInlineMatch = isSymbol(inputs.get(inputArrayIndex).charAt(leftCharIndex));

      return hasBottomLineMatch || hasTopLineMatch || hasInlineMatch;
    }
    return false;
  }

  private static Boolean checkBottom(ArrayList<String> inputs, int inputArrayIndex, int matchedFirstIndex, int lastIndex) {
    int bottomLineIndex = inputArrayIndex + 1;
    if (bottomLineIndex < inputs.size()) {
      String bottomLine = inputs.get(bottomLineIndex);
      for (int i = matchedFirstIndex; i <= lastIndex; i++) {
        if (isSymbol(bottomLine.charAt(i))) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean isSymbol(char c) {
    return !Character.isDigit(c) && c != '.';
  }

  private static Boolean checkTop(ArrayList<String> inputs, int inputArrayIndex, int matchedFirstIndex, int lastIndex) {
    int topLineIndex = inputArrayIndex - 1;
    if (topLineIndex >= 0) {
      String topLine = inputs.get(topLineIndex);
      for (int i = matchedFirstIndex; i <= lastIndex; i++) {
        if (isSymbol(topLine.charAt(i))) {
          return true;
        }
      }
    }
    return false;
  }

  private static int findNumbersLastIndex(int startIndex, String input) {
    for (int i = startIndex; i < input.length(); i++) {
      if (!Character.isDigit(input.charAt(i))) {
        return i - 1;
      }
    }
    return input.length() - 1;
  }

  private static int findFirstDigitIndex(int startIndex, String input) {
    for (int i = startIndex; i < input.length(); i++) {
      if (Character.isDigit(input.charAt(i))) {
        return i;
      }
    }
    return -1;
  }

}