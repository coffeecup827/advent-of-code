package day2.part2;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Solution {

  private static final String DAY = "2";
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
    int ALLOWED_RED = 12;
    int ALLOWED_GREEN = 13;
    int ALLOWED_BLUE = 14;
    int sum = 0;

    String RED = "red";
    String GREEN = "green";
    String BLUE = "blue";


    for (String input : inputs) {

      int maxRed = 0;
      int maxGreen = 0;
      int maxBlue = 0;

      String revealedCubes = input.split(":")[1].strip();

      List<String> revelations = Arrays.stream(revealedCubes.split(";")).toList();

      for (String revelation: revelations) {
        List<String> cubeValues = Arrays.stream(revelation.split(",")).toList();
        for (String  cube: cubeValues) {
          String[] cubeCount = cube.strip().split(" ");
          int count = Integer.parseInt(cubeCount[0]);
          String cubeColor = cubeCount[1];
          if (Objects.equals(cubeColor, RED)) {
            maxRed = Math.max(maxRed, count);
          } else if (cubeColor.equals(GREEN)) {
            maxGreen = Math.max(maxGreen, count);
          } else if (cubeColor.equals(BLUE)) {
            maxBlue = Math.max(maxBlue, count);
          }
        }
      }

      sum += (maxRed * maxBlue * maxGreen);
    }

    logger.info(String.valueOf(sum));
  }

}