package day4.part1;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// Answer: 21105
public class Solution {

  private static final String DAY = "4";
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
    for (String input: inputs) {
      String[] cards = input.split(":")[1].strip().split("\\|");
      HashSet<String> winningNumbers = Arrays.stream(cards[0].strip().split(" +"))
          .collect(Collectors.toCollection(HashSet::new));
      HashSet<String> myNumbers = Arrays.stream(cards[1].strip().split(" +"))
          .collect(Collectors.toCollection(HashSet::new));
      winningNumbers.retainAll(myNumbers);
      sum += (int) Math.pow(2, (double) winningNumbers.size() - 1);
    }

    logger.info(String.valueOf(sum));
  }

}