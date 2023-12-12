package day3.part2;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// Answer: 91031374
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
          if (gearRatio.size() == 2) {
            // if there are more numbers around a gear, its not two part number anymore
            gearRatios.add(gearRatio);
          }
        }
      }
    }

    gearSum = gearRatios.stream().mapToInt(x -> x.get(0) * x.get(1)).sum();
    logger.info(String.valueOf(gearSum));
  }

  private static ArrayList<Integer> findGearRatio(ArrayList<String> inputs, int inputIndex, int currentLineIndex) {
    ArrayList<Integer> gearRatio = new ArrayList<>();

    String currentLine = inputs.get(inputIndex);
    gearRatio.add(getRightNumber(currentLine, currentLineIndex));
    gearRatio.add(getLeftNumber(currentLine, currentLineIndex));

    if (inputIndex - 1 >= 0) {
      String topLine = inputs.get(inputIndex - 1);
      if (Character.isDigit(topLine.charAt(currentLineIndex))) {
        // if yes number is at
        // 2345
        // .*..
        gearRatio.add(findFullNumberAt(topLine, currentLineIndex));
      } else {
        // if not number could be at
        // 2.4.
        // .*..
        gearRatio.add(getRightNumber(topLine, currentLineIndex));
        gearRatio.add(getLeftNumber(topLine, currentLineIndex));
      }
    }

    if (inputIndex + 1 < inputs.size()) {
      String botLine = inputs.get(inputIndex + 1);
      if (Character.isDigit(botLine.charAt(currentLineIndex))) {
        gearRatio.add(findFullNumberAt(botLine, currentLineIndex));
      } else {
        gearRatio.add(getRightNumber(botLine, currentLineIndex));
        gearRatio.add(getLeftNumber(botLine, currentLineIndex));
      }
    }

    while (gearRatio.remove(null));

    return gearRatio;
  }

  private static Integer getLeftNumber(String str, int index) {
    int leftIndex = index - 1;
    // check if top left exists
    if (index != 0 && Character.isDigit(str.charAt(leftIndex))) {
      return findFullNumberAt(str, leftIndex);
    }
    return null;
  }

  private static Integer getRightNumber(String str, int index) {
    int rightIndex = index + 1;
    // check if right exists
    if (index != str.length() - 1 && Character.isDigit(str.charAt(rightIndex))) {
      return  findFullNumberAt(str, rightIndex);
    }
    return null;
  }

  private static int findFullNumberAt(String str, int numberAtIndex) {
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