import java.io.File;
import java.util.Scanner;

// Add JayLayer imports


public class Main {
    public static void main(String[] args) throws InterruptedException {
         Scanner scanner = new Scanner(System.in);
        System.out.println("===== MP3 Player =====");
        String filePath = "C:\\Users\\georg\\MusicPlayer\\src\\ZEZE Remix - Eminem, Tyga, G-Eazy, Chris Brown, Travis Scott,Dr. Dre,50 Cent,Offset [Nitin Randhawa].mp3";

        File file = new File(filePath);


        MP3_Player mp3Player = new MP3_Player(file);
        displayMenu();

        String response = "";
        while (!response.equals("Q")) {
            System.out.print("\nEnter your choice: ");
            response = scanner.nextLine().toUpperCase();

            processUserChoice(response, mp3Player, scanner);
        }

        scanner.close();
        System.out.println("\nThank you for using MP3 Player!");
    }
    private static void displayMenu() {
        System.out.println("\n--- Available Commands ---");
        System.out.println("P = Play");
        System.out.println("S = Stop/Pause");
        System.out.println("R = Reset to beginning");
        System.out.println("J = Jump to a specific second");
        System.out.println("Q = Quit");
        System.out.println("-----------------------");
    }

    public static void processUserChoice(String choice, MP3_Player mp3Player, Scanner scanner) throws InterruptedException {
        switch (choice) {
            case "P":
                System.out.print("▶ Playing music");
                for (int i = 0; i < 3; i++) {
                    System.out.print(".");
                    Thread thread=new Thread();
                    Thread.sleep(1000);
                }
                System.out.println();
                mp3Player.play();
                break;

            case "S":
                System.out.println("⏸ Pausing music...");
                mp3Player.stop();
                break;

            case "R":
                System.out.println("⏮ Resetting to beginning...");
                mp3Player.reset();
                break;

            case "J":
                handleJumpRequest(mp3Player, scanner);
                break;

            case "Q":
                System.out.println("⏹ Closing player...");
                mp3Player.close();
                break;

            default:
                System.out.println("❌ Invalid choice. Please try again.");
                displayMenu();
                break;
        }
    }

    /**
     * Handle the jump request from user
     */
    private static void handleJumpRequest(MP3_Player mp3Player, Scanner scanner) {
        System.out.print("Enter time in seconds: ");
        try {
            double seconds = Double.parseDouble(scanner.nextLine());
            if (seconds < 0) {
                System.out.println("❌ Time cannot be negative.");
                return;
            }

            mp3Player.jumpTo(seconds);
            System.out.println("⏩ Jumped to " + seconds + " seconds");
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number format. Please enter a valid number.");
        }
    }
}