// import java.util.Scanner;

// public class TrafficSignalNavigation {

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         // Input: Number of signals
//         System.out.print("Enter the number of signals: ");
//         int numberOfSignals = scanner.nextInt();

//         // Input: Time intervals
//         System.out.print("Do the signals have the same time intervals between them? (y or n): ");
//         char sameIntervals = scanner.next().charAt(0);
//         double[] travelTimes = new double[numberOfSignals + 1]; // +1 for the destination segment

//         if (sameIntervals == 'y') {
//             System.out.print("Enter the time interval between each signal: ");
//             double timeInterval = scanner.nextDouble();
//             for (int i = 0; i < travelTimes.length; i++) {
//                 travelTimes[i] = timeInterval;
//             }
//         } else {
//             for (int i = 0; i < numberOfSignals; i++) {
//                 System.out.print("Enter the time to signal #" + (i + 1) + ": ");
//                 travelTimes[i] = scanner.nextDouble();
//             }
//             System.out.print("Enter the time from signal #" + numberOfSignals + " to the destination: ");
//             travelTimes[numberOfSignals] = scanner.nextDouble();
//         }

//         // Input: Red and green light durations
//         System.out.print("Do the signals have the same red and green light durations? (y or n): ");
//         char sameDurations = scanner.next().charAt(0);
//         int[] redDurations = new int[numberOfSignals];
//         int[] greenDurations = new int[numberOfSignals];

//         if (sameDurations == 'y') {
//             System.out.print("Enter the red light duration: ");
//             int redDuration = scanner.nextInt();
//             System.out.print("Enter the green light duration: ");
//             int greenDuration = scanner.nextInt();
//             for (int i = 0; i < numberOfSignals; i++) {
//                 redDurations[i] = redDuration;
//                 greenDurations[i] = greenDuration;
//             }
//         } else {
//             for (int i = 0; i < numberOfSignals; i++) {
//                 System.out.print("Enter the red light duration at signal #" + (i + 1) + ": ");
//                 redDurations[i] = scanner.nextInt();
//                 System.out.print("Enter the green light duration at signal #" + (i + 1) + ": ");
//                 greenDurations[i] = scanner.nextInt();
//             }
//         }

//         // Input: Number of paths
//         System.out.print("Are all signals two-way roads? (y or n): ");
//         char twoWay = scanner.next().charAt(0);
//         int[] paths = new int[numberOfSignals];
//         if (twoWay == 'y') {
//             // All signals are two-way, so 2 paths each
//             for (int i = 0; i < numberOfSignals; i++) {
//                 paths[i] = 2;
//             }
//         } else {
//             for (int i = 0; i < numberOfSignals; i++) {
//                 int pathCount = 0;
//                 do {
//                     System.out.print("Enter the number of paths at signal #" + (i + 1) + " (2-4): ");
//                     pathCount = scanner.nextInt();
//                 } while (pathCount < 2 || pathCount > 4);
//                 paths[i] = pathCount;
//             }
//         }

//         // Input: Travel speed multiplier
//         System.out.print("Enter your travel speed multiplier for road conditions (e.g., 1.0 for normal, 0.8 for slow, 1.2 for fast): ");
//         double speedMultiplier = scanner.nextDouble();

//         // Input: Unexpected delays
//         System.out.print("Did you encounter any unexpected delays? (y or n): ");
//         char unexpectedDelays = scanner.next().charAt(0);
//         int[] delays = new int[numberOfSignals];
//         if (unexpectedDelays == 'y') {
//             for (int i = 0; i < numberOfSignals; i++) {
//                 System.out.print("Enter the delay at signal #" + (i + 1) + " (in seconds): ");
//                 delays[i] = scanner.nextInt();
//                 if (delays[i] < 0) delays[i] = 0;
//             }
//         } else {
//             // No delays
//             for (int i = 0; i < numberOfSignals; i++) {
//                 delays[i] = 0;
//             }
//         }

//         // Compute total time
//         double totalTime = 0.0;

//         for (int i = 0; i < numberOfSignals; i++) {
//             // Travel time to this signal
//             double travelTime = travelTimes[i] * speedMultiplier;
//             totalTime += travelTime;

//             // Add any unexpected delay at this signal
//             totalTime += delays[i];

//             // Now determine if have to wait at the signal due to red light

//             int redDuration = redDurations[i];
//             int greenDuration = greenDurations[i];
//             int cycleDuration = redDuration + greenDuration;

//             int numPaths = paths[i];

//             // Full cycle length for all paths
//             double fullCycle = cycleDuration * numPaths;

//             // Calculate where in the cycle the user arrives
//             double posInCycle = totalTime % fullCycle;

//             // Green window for user's path is from redDuration to redDuration + greenDuration
//             double greenStart = redDuration;
//             double greenEnd = redDuration + greenDuration;

//             // If arrival time is outside green window, wait until next green window
//             if (posInCycle < greenStart || posInCycle >= greenEnd) {
//                 double waitTime = (greenStart - posInCycle + fullCycle) % fullCycle;
//                 totalTime += waitTime;
//             }
//             // else, arrival during green light, no wait
//         }

//         // Add travel time from last signal to destination
//         double travelToDestination = travelTimes[numberOfSignals] * speedMultiplier;
//         totalTime += travelToDestination;

//         System.out.println();
//         System.out.printf("The total time to reach the destination is %.0f seconds.%n", totalTime);

//         scanner.close();
//     }
// }


import java.util.Scanner;

public class TrafficSignalNavigation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of signals: ");
        int numberOfSignals = scanner.nextInt();

        int[] travelTimes = new int[numberOfSignals + 1]; // includes travel from last signal to destination
        System.out.print("Do the signals have the same time intervals between them? (y or n): ");
        char sameIntervals = scanner.next().charAt(0);

        if (sameIntervals == 'y') {
            System.out.print("Enter the time interval between each signal: ");
            int interval = scanner.nextInt();
            for (int i = 0; i < travelTimes.length; i++) {
                travelTimes[i] = interval;
            }
        } else {
            for (int i = 0; i < numberOfSignals; i++) {
                System.out.print("Enter the time to signal #" + (i + 1) + ": ");
                travelTimes[i] = scanner.nextInt();
            }
            System.out.print("Enter the time from signal #" + numberOfSignals + " to the destination: ");
            travelTimes[numberOfSignals] = scanner.nextInt();
        }

        int[] red = new int[numberOfSignals];
        int[] green = new int[numberOfSignals];
        System.out.print("Do the signals have the same red and green light durations? (y or n): ");
        char sameDurations = scanner.next().charAt(0);

        if (sameDurations == 'y') {
            System.out.print("Enter the red light duration: ");
            int redDuration = scanner.nextInt();
            System.out.print("Enter the green light duration: ");
            int greenDuration = scanner.nextInt();
            for (int i = 0; i < numberOfSignals; i++) {
                red[i] = redDuration;
                green[i] = greenDuration;
            }
        } else {
            for (int i = 0; i < numberOfSignals; i++) {
                System.out.print("Enter the red light duration at signal #" + (i + 1) + ": ");
                red[i] = scanner.nextInt();
                System.out.print("Enter the green light duration at signal #" + (i + 1) + ": ");
                green[i] = scanner.nextInt();
            }
        }

        int[] paths = new int[numberOfSignals];
        System.out.print("Are all signals two-way roads? (y or n): ");
        char twoWay = scanner.next().charAt(0);

        if (twoWay == 'y') {
            for (int i = 0; i < numberOfSignals; i++) {
                paths[i] = 2;
            }
        } else {
            for (int i = 0; i < numberOfSignals; i++) {
                int count;
                do {
                    System.out.print("Enter the number of paths at signal #" + (i + 1) + " (2 to 4): ");
                    count = scanner.nextInt();
                } while (count < 2 || count > 4);
                paths[i] = count;
            }
        }

        System.out.print("Enter your travel speed multiplier: ");
        double speed = scanner.nextDouble();

        int[] delays = new int[numberOfSignals];
        System.out.print("Did you encounter any unexpected delays? (y or n): ");
        char delayInput = scanner.next().charAt(0);

        if (delayInput == 'y') {
            for (int i = 0; i < numberOfSignals; i++) {
                System.out.print("Enter the delay at signal #" + (i + 1) + " (in seconds): ");
                delays[i] = Math.max(0, scanner.nextInt());
            }
        } else {
            for (int i = 0; i < numberOfSignals; i++) {
                delays[i] = 0;
            }
        }

        double totalTime = 0;

        for (int i = 0; i < numberOfSignals; i++) {
            // Travel time to signal
            totalTime += travelTimes[i] * speed;

            // Add delay
            totalTime += delays[i];

            int totalCycle = (red[i] + green[i]) * paths[i];
            double timeInCycle = totalTime % totalCycle;

            // Green window for path 0 is from 0 to green[i]
            if (timeInCycle > green[i]) {
                totalTime += (totalCycle - timeInCycle);
            }
        }

        // Final leg to destination
        totalTime += travelTimes[numberOfSignals] * speed;

        System.out.println();
        System.out.printf("The total time to reach the destination is %.0f seconds.%n", totalTime);
        scanner.close();
    }
}



