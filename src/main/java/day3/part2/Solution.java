package day3.part2;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// Answer:
public class Solution {

  private static final String DAY = "3";
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

  private static void solution (ArrayList<String> inputs) {
    ArrayList<ArrayList<Integer>> gearRatios = new ArrayList<>();
    int gearSum = 0;

    for (int inputIndex = 0; inputIndex < inputs.size(); inputIndex++) {
      String currentLine = inputs.get(inputIndex);
      for (int currentLineIndex = 0; currentLineIndex < currentLine.length(); currentLineIndex++) {
        if (currentLine.charAt(currentLineIndex) == '*') {
          ArrayList<Integer> gearRatio = findGearRatio(inputs, inputIndex, currentLineIndex);
          if (!gearRatio.isEmpty() && gearRatio.size() > 1) {
            gearRatios.add(gearRatio);
          }
        }
      }
    }

    System.out.println(gearRatios);
    gearSum = gearRatios.stream().mapToInt(x -> x.get(0) * x.get(1)).sum();
    logger.info(String.valueOf(gearSum));
  }

  private static ArrayList<Integer> findGearRatio(ArrayList<String> inputs, int inputIndex, int currentLineIndex) {
    ArrayList<Integer> gearRatio = new ArrayList<>();
    int topNumber = -1;
    int bottomNumber = -1;
    int leftNumber = -1;
    int rightNumber = -1;


    if (inputIndex - 1 >= 0) {
      topNumber = findFullNumberAt(inputs.get(inputIndex - 1), currentLineIndex);
      if (topNumber != -1) {
        gearRatio.add(topNumber);
      }
    }
    if (inputIndex + 1 < inputs.size()) {
      bottomNumber = findFullNumberAt(inputs.get(inputIndex + 1), currentLineIndex);
      if (bottomNumber != -1) {
        gearRatio.add(bottomNumber);
      }
    }

    if (currentLineIndex > 0) {
      leftNumber = findFullNumberAt(inputs.get(inputIndex), currentLineIndex - 1);
      if (leftNumber != -1) {
        gearRatio.add(leftNumber);
      }
    }
    if (currentLineIndex + 1 < inputs.get(inputIndex).length()) {
      rightNumber = findFullNumberAt(inputs.get(inputIndex), currentLineIndex + 1);
      if (rightNumber != -1) {
        gearRatio.add(rightNumber);
      }
    }

    return gearRatio;
  }

  private static int findFullNumberAt(String str, int index) {

    int numberAtIndex =
        Character.isDigit(str.charAt(index)) ? index :
            Character.isDigit(str.charAt(index + 1)) ? index + 1 :
                Character.isDigit(str.charAt(index - 1)) ? index - 1 :
                    -1;

    int firstIndex = numberAtIndex;
    int lastIndex = -1;

    if (firstIndex != -1) {
      // numberAtIndex becomes at the start index of the number
      while (firstIndex > 0) {
        if (!Character.isDigit(str.charAt(firstIndex - 1))) {
          break;
        }
        firstIndex--;
      }
      lastIndex = findNumbersLastIndex(firstIndex, str);
      return Integer.parseInt(str.substring(firstIndex, lastIndex + 1));
    }
    return -1;
  }

  private static int findNumbersLastIndex(int startIndex, String input) {
    for (int i = startIndex; i < input.length(); i++) {
      if (!Character.isDigit(input.charAt(i))) {
        return i - 1;
      }
    }
    return input.length() - 1;
  }

}