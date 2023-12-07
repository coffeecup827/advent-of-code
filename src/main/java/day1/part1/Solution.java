package day1.part1;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

//Answer: 54597
public class Solution {
  private static final String DAY = "1";
  private static final String PART = "1";
  private static final Logger logger = Logger.getLogger(Solution.class.getName());

  public static void main(String[] args) throws Exception {
    BufferedReader reader = null;
    try {
      Path path = Paths.get("src/main/java/day" + DAY + "/part" + PART + "/input.txt");

      reader = Files.newBufferedReader(path);
      ArrayList<String> inputs = reader.lines().collect(Collectors.toCollection(ArrayList::new));

      if (!inputs.isEmpty()) {
        solution(inputs);
      }

    } catch (Exception e) {
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
    for (String input : inputs) {
      sum += Integer.parseInt(findFirstInteger(input) + findLastInteger(input));
    }

    logger.info("Calibration: " + sum);

  }

  private static String findFirstInteger(String input) throws IOException {
    for (int i = 0; i < input.length(); i++) {
      if (Character.isDigit(input.charAt(i))) {
        return String.valueOf(input.charAt(i));
      }
    }
    throw new IOException("Integer Not Found");
  }

  private static String findLastInteger(String input) throws IOException {
    for (int i = input.length() - 1; i >= 0; i--) {
      if (Character.isDigit(input.charAt(i))) {
        return String.valueOf(input.charAt(i));
      }
    }
    throw new IOException("Integer Not Found");
  }

}