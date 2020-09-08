import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Auditorium {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("1. feladat");
        char[][] availability = readInCapacity("src/foglaltsag.txt");
        int[][] categories = readInCategories("src/kategoria.txt");

        printCharFile(availability);
        System.out.println();
        printIntFile(categories);
        System.out.println();

        System.out.println("2. feladat");
        freeOrTaken(readInCapacity("src/foglaltsag.txt"));
        System.out.println();

        System.out.println("3. feladat");
        soldTickets(availability);
        System.out.println();

        System.out.println(soldTicketsPerCategory(availability, categories, 2));
        System.out.println();

        System.out.println("4. feladat");
        mostTicketsSoldInCategory(availability, categories);
        System.out.println();

        System.out.println("5. feladat");
        System.out.println("A színház bevétele az eladott jegyek alapján " +
                incomeAfterSoldTickets(availability, categories) + " Ft.");
        System.out.println();

        System.out.println("6. feladat");
        soloFreeSeats(availability);
        System.out.println();

        System.out.println("7. feladat");
        freeSeatsWithCategories(availability, categories);
        System.out.println();

    }

    public static char[][] readInCapacity(String string) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(string));

        int row = 0;
        int col = 0;

        while (sc.hasNext()) {
            col = sc.nextLine().length();
            row++;
        }

        char[][] availability = new char[row][col];

        sc = new Scanner(new File(string));

        for (int i = 0; i < row; i++) {
            String str = sc.nextLine();
            char[] chars = str.toCharArray();
            int idx = 0;
            for (int j = 0; j < col; j++) {
                availability[i][j] = chars[idx++];
            }
        }


        return availability;
    }

    public static int[][] readInCategories(String string) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(string));

        int row = 0;
        int col = 0;

        while (sc.hasNext()) {
            col = sc.nextLine().length();
            row++;
        }

        sc = new Scanner(new File(string));
        int[][] categories = new int[row][col];

        for (int i = 0; i < row; i++) {
            String string1 = sc.nextLine();
            String[] strings = string1.split("");
            int idx = 0;
            for (int j = 0; j < col; j++) {
                categories[i][j] = Integer.parseInt(strings[idx++]);
            }
        }

        return categories;
    }

    public static void printCharFile(char[][] availability) {
        for (int i = 0; i < availability.length; i++) {
            for (int j = 0; j < availability[i].length; j++) {
                System.out.print(availability[i][j]);
            }
            System.out.println();
        }
    }

    public static void printIntFile(int[][] categories) {
        for (int i = 0; i < categories.length; i++) {
            for (int j = 0; j < categories[i].length; j++) {
                System.out.print(categories[i][j]);
            }
            System.out.println();
        }
    }

    public static void freeOrTaken(char[][] availability) {
        System.out.println("Kérme, írja be a sor és a szék számát.");

        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int chair = sc.nextInt();

        if (availability[row - 1][chair - 1] == 'o') {
            System.out.println("A hely szabad.");
        } else {
            System.out.println("A hely foglalt.");
        }

        sc.close();
    }

    public static void soldTickets(char[][] availability) {
        int count = 0;
        int row = 0;
        int col = 0;
        for (int i = 0; i < availability.length; i++) {
            for (int j = 0; j < availability[i].length; j++) {
                if (availability[i][j] == 'x') {
                    count++;
                }
            }
            row = availability.length;
            col = availability[i].length;
        }
        int allSeats = row * col;
        int percentage = count / (allSeats / 100);
        System.out.println("Az előadásra eddig " + count + " jegyet adtak el, ami a nézőtér " + percentage + "%-a.");
    }

    public static int soldTicketsPerCategory(char[][] availability, int[][] categories, int cat) {
        int count = 0;
        for (int i = 0; i < categories.length; i++) {
            for (int j = 0; j < categories[i].length; j++) {
                if (categories[i][j] == cat && availability[i][j] == 'x') {
                    count++;
                }
            }
        }
        return count;
    }

    public static void mostTicketsSoldInCategory(char[][] availability, int[][] categories) {
        int max = 0;
        int count = 0;
        int cat = 0;
        for (int i = 1; i <= 5; i++) {
            count = soldTicketsPerCategory(availability, categories, i);
            if (count > max) {
                max = count;
                cat = i;
            }
        }
        System.out.println("A legtöbb jegyet a(z) " + cat + ". kategóriában értékesítették.");
    }

    public static int incomeAfterSoldTickets(char[][] availability, int[][] categories) {
        int count = 0;
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            count = soldTicketsPerCategory(availability, categories, i);
            switch (i) {
                case 1 -> sum += count * 5000;
                case 2 -> sum += count * 4000;
                case 3 -> sum += count * 3000;
                case 4 -> sum += count * 2000;
                case 5 -> sum += count * 1500;
            }
        }
        return sum;
    }

    public static void soloFreeSeats(char[][] availability) {
        int count = 0;
        for (int i = 0; i < availability.length; i++) {
            for (int j = 0; j < availability[i].length; j++) {
                if (j < 1 && availability[i][j] == 'o' && availability[i][j+1] == 'x') {
                    System.out.print("s");
                    count++;
                } else if (j > 0 && j < availability[i].length-1 && availability[i][j] == 'o' &&
                        availability[i][j-1] == 'x' && availability[i][j+1] == 'x') {
                    System.out.print("s");
                    count++;
                } else if (j == availability[i].length-1 && availability[i][availability[i].length-1] == 'o' &&
                        availability[i][availability[i].length-2] == 'x') {
                    System.out.print("s");
                    count++;
                } else System.out.print(availability[i][j]);
            }
            System.out.println();
        }
        System.out.println(count + " db egyedülálló üres hely van jelenleg.");
    }

    public static void freeSeatsWithCategories(char[][] availability, int[][] categories) {
        for (int i = 0; i < availability.length; i++) {
            for (int j = 0; j < availability[i].length; j++) {
                if (availability[i][j] == 'o') {
                    System.out.print(categories[i][j]);
                } else {
                    System.out.print(availability[i][j]);
                }
            }
            System.out.println();
        }
    }
}
