package teemo.mastermind;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MastermindGame {
    private static String SUCCESS_RESULT = "++++";

    public static void main(String[] args){
        MastermindService service = new MastermindService();
        service.initializeSecret();

        var scanner = new Scanner(System.in);
        var solutionFound = false;

        Pattern p = Pattern.compile("\\d{4}");

        System.out.println("Hi and welcome to our mastermind game");
        System.out.println("Please enter your suggestion");
        while(!solutionFound){
            var value = scanner.nextLine();

            Matcher m = p.matcher(value);
            if(value.length() != 4 || !m.matches()){
                System.out.println("Your entry is invalid. Please enter exactly 4 digits");
                System.out.println("Please try again");
                continue;
            }

            var result = service.compareInputWithSecret(value);
            if(SUCCESS_RESULT.equals(result)){
                System.out.println("Congratulation, you won the game");
                solutionFound = true;
                continue;
            }

            System.out.println("Sorry, you didn't find the right solution.");
            System.out.println(String.format("Here is your result: %s", result));
            System.out.println("Please try again");
        }
    }
}
