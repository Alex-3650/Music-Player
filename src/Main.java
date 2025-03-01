import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        String filePath="";

        File file =new File(filePath);

        try( Scanner scanner = new Scanner(System.in)){

            AudioInputStream audiStream= AudioSystem.getAudioInputStream(file);
           Clip clip =AudioSystem.getClip();
           clip.open(audiStream);


           String response="";
           while (!response.equals("Q")){

               System.out.println("P = Play");
               System.out.println("S = Stop");
               System.out.println("R = Reset");
               System.out.println("J = Jump to a specific second");
               System.out.println("Q = Quit");
               System.out.print("Enter your choice: ");


               response=scanner.nextLine().toUpperCase();
               System.out.println("==================");

               switch (response){

                   case "P"->clip.start();
                   case "S"->clip.stop();
                   case "R"->clip.setMicrosecondPosition(0);
                   case "Q"->clip.close();
                   case "J"->{
                       System.out.print("Enter time in seconds: ");
                       try {
                          double seconds=Double.parseDouble(scanner.nextLine());
                          long microseconds=(long)(seconds * 1_000_000);
                           clip.setMicrosecondPosition(microseconds);
                           System.out.println("Jumped to " + seconds + " seconds");
                       }catch (NumberFormatException e){
                           System.out.println("Invalid number format. Please enter a valid number.");
                       }
                   }
                   default -> System.out.println("Invalid choice");

               }

           }

        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file");
        }
        catch(UnsupportedAudioFileException e){
            System.out.println("Audio file is not supported");
        }

        catch (IOException e){
            System.out.println("Something went wrong");
        }

        catch (LineUnavailableException e) {
            System.out.println("Unable to access audio resource");;
        }
        finally{
            System.out.println("Bye");

        }
    }
}