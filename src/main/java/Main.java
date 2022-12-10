import java.util.*;

public class Main {
  public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
  public static final int ROUTE_LENGTH = 100;
  public static final String LETTERS = "RLRFR";
  public static final char CHAR_TO_SEARCH = 'R';
  public static final int THREAD_COUNT = ROUTE_LENGTH;

  public static void main(String[] args) {

    for (int i = 0; i < THREAD_COUNT; i++) {
      new Thread(() -> {
        String route = generateRoute(LETTERS, ROUTE_LENGTH);
        int rCount = (int) route.chars()
                .filter(ch -> ch == CHAR_TO_SEARCH)
                .count();
        synchronized (sizeToFreq) {
          if (sizeToFreq.get(rCount) != null) {
            int value = sizeToFreq.get(rCount);
            sizeToFreq.put(rCount, ++value);
          } else {
            sizeToFreq.put(rCount, 1);
          }
        }
      }
      ).start();
    }

    int maxValueKey = Collections.max(sizeToFreq.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();

    System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n",
            maxValueKey, sizeToFreq.get(maxValueKey));
    System.out.println("Другие размеры:");
    sizeToFreq.forEach((x, y) -> System.out.printf("- %d (%d раз)\n", x, y));
  }

  public static String generateRoute(String letters, int length) {
    Random random = new Random();
    StringBuilder route = new StringBuilder();
    for (int i = 0; i < length; i++) {
      route.append(letters.charAt(random.nextInt(letters.length())));
    }
    return route.toString();
  }
}
