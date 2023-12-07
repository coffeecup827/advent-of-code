package shared;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatternMatcher {

  private final HashMap<Character, ArrayList<String>> matchMap = new HashMap<>();

  public PatternMatcher(List<String> matchWithStrings) {
    createMatchMap(matchWithStrings);
  }

  private void createMatchMap(List<String> keys) {
    for (String key : keys) {
      Character firstLetter = key.charAt(0);
      matchMap.computeIfAbsent(firstLetter, k -> new ArrayList<>());
      matchMap.get(firstLetter).add(key);
    }
  }

  public String findFirstMatch(String input) throws IOException {
    // we create a map with keys that have the same starting letter
    // this is just a optimisation so the lookups for first match is faster
    ArrayList<ArrayList<String>> possibleMatches = new ArrayList<>();

    // we loop through the input
    for (int inputIndex = 0; inputIndex < input.length(); inputIndex++) {
      char currentChar = input.charAt(inputIndex);

      // check if the current character is start of a new match
      // if yes add it to the matches at start and shift other matches to their respective next indices
      // else add null to start as we have to shift everything by one index any ways so that we match the
      // next characters

      // Note: Adding to index 0 also means that the first character matches
      // this also means that the index + 1 of the matches list tells the number of characters that matched
      // if its index+1 matches its size, that means its a match!
      ArrayList<String> list = matchMap.getOrDefault(currentChar, new ArrayList<>());
      possibleMatches.add(0, new ArrayList<>(list));

      // looping through all the matches we have to check if something satisfies
      for (int possibleMatchIndex = 0; possibleMatchIndex < possibleMatches.size(); possibleMatchIndex++) {
        ArrayList<String> matches = possibleMatches.get(possibleMatchIndex);

        List<Integer> indexesToRemove = new ArrayList<>();

        for (int matchIndex = 0; matchIndex < matches.size(); matchIndex++) {
          String match = matches.get(matchIndex);
          // check if the current index character is a match
          if (match.charAt(possibleMatchIndex) == currentChar) {
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

      // this is to avoid unnecessary looping into the empty lists
      removeNullsInTheEnd(possibleMatches);
    }

    throw new IOException("No Matches Found");
  }

  private void removeIndexes(ArrayList<String> matches, List<Integer> indexesToRemove) {
    int indicesRemoved = 0;
    for (Integer index : indexesToRemove) {
      matches.remove(index - indicesRemoved);
      indicesRemoved++;
    }
  }

  private void removeNullsInTheEnd(ArrayList<ArrayList<String>> possibleMatches) {

    for (int i = possibleMatches.size() - 1; i >= 0; i--) {
      if (!possibleMatches.get(i).isEmpty()) {
        return;
      }
      possibleMatches.remove(i);
    }

  }

}
