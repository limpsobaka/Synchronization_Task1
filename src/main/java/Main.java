import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
  public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
  public static final int ROUTE_LENGTH = 100;
  public static final String LETTERS = "RLRFR";
  public static final int THREAD_COUNT = ROUTE_LENGTH;

  public static void main(String[] args) {
    synchronized (sizeToFreq) {
    for (int i = 0; i < THREAD_COUNT; i++) {
        new Thread(() -> {
          String route = generateRoute(LETTERS, ROUTE_LENGTH);
          int rCount = (int) route.chars()
                  .filter(ch -> ch == 'R')
                  .count();
          if (sizeToFreq.get(rCount) != null) {
            int value = sizeToFreq.get(rCount);
            sizeToFreq.put(rCount, value + 1);
          } else {
            sizeToFreq.put(rCount, 1);
          }
        }
        ).start();
      }
    }

    int maxValueKey = Collections.max(sizeToFreq.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue())
            .getKey();

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
