import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        ArrayList<Integer> playersUniqueOrder = new ArrayList<>();

        System.out.println("You will write your names in one line");
        String playersName = scanner.nextLine();
        playersName = playersName.trim();
        String[] splitNames = playersName.split("\\s+");

        int[][] playersOrderPoints = new int[2][splitNames.length];
        int amountPlayers = 0;
        while(amountPlayers < splitNames.length) {
            int generateOrders = random.nextInt((splitNames.length - 1) - 0 + 1) + 0;
            if(!playersUniqueOrder.contains(generateOrders)) {
                playersUniqueOrder.add(generateOrders);
                playersOrderPoints[0][amountPlayers] = generateOrders;
                amountPlayers++;
            }
        }

        System.out.println("The game will go in this order! ");
        for(int i = 0; i < splitNames.length; i++) {
            int order = playersOrderPoints[0][i];
            System.out.print((i + 1) + ". ");
            System.out.println(splitNames[order]);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String baseWords = "benevolent vivid gentle lively tidy calm cheerful sincere grateful simple";
        String wordsDescription = "Kind and generous, expressing goodwill and a desire to do good for others. Bright, intense, and full of life, often used to describe colors or images that are strikingly clear." +
                "Soft and mild in manner or behavior, showing kindness and a considerate approach.Full of energy and enthusiasm, vibrant and animated. Neat and organized, keeping things in order and free from clutter." +
                "Peaceful and quiet, without disturbance or agitation.Happy, positive, and optimistic in demeanor, spreading joy to others.Genuine and honest, expressing true feelings or intentions." +
                " Appreciative and thankful for something received or experienced.Easy to understand or do, not complicated or complex.";
        String[] splitBaseWords = baseWords.split("\\s+");
        String[] splitWordsDescription = wordsDescription.split("\\.+");

        int playersTurn = 0;
        for(int i = 0; i < splitBaseWords.length; i++) {
            String outputWord = splitBaseWords[i];
            System.out.println(outputWord);
            char[] word = outputWord.toCharArray();
            String[] hiddenWord = new String[outputWord.length()];
            for(int a = 0; a < hiddenWord.length; a++) {
                hiddenWord[a] = "■";
            }
            while(true) {
                System.out.println("\nPlayers' dask:\n");
                for(int a = 0; a < splitNames.length; a++) {
                    int order = playersOrderPoints[0][a];
                    System.out.print((a + 1) + ". ");
                    if(playersTurn == a) {
                        System.out.print("\uD83E\uDC82 ");
                        System.out.println(splitNames[order].toUpperCase() + ":   [" + playersOrderPoints[1][a] + "]");
                    } else {
                        System.out.println(splitNames[order].toLowerCase() + ":   [" + playersOrderPoints[1][a] + "]");
                    }
                }
                System.out.println("\nDescription of the word: \n" + splitWordsDescription[i] + "\n");
                for(int k = 0; k < hiddenWord.length; k++) {
                    System.out.print(hiddenWord[k] + " ");
                }
                System.out.println();
                String playersGuess = scanner.nextLine();
                int indexOfSubstring = outputWord.indexOf(playersGuess);
                if(playersGuess.length() == 1) {
                    if(indexOfSubstring == -1) {
                        if(playersTurn == splitNames.length - 1) {
                            playersTurn = 0;
                        } else {
                            playersTurn++;
                        }
                    } else {
                        while(indexOfSubstring != -1) {
                            if(indexOfSubstring != -1) {
                                playersOrderPoints[1][playersTurn] += 10;
                                outputWord = outputWord.replaceFirst(playersGuess, "");
                            }
                            for(int j = 0; j < hiddenWord.length; j++) {
                                if(word[j] == playersGuess.charAt(0)) {
                                    hiddenWord[j] = playersGuess;
                                }
                            }
                            indexOfSubstring = outputWord.indexOf(playersGuess);
                        }
                    }
                    clearScreen();
                } else {
                    if(splitBaseWords[i].equalsIgnoreCase(playersGuess)) {
                        playersOrderPoints[1][playersTurn] += 10 * outputWord.length();
                        for(int j = 0; j < hiddenWord.length; j++) {
                            hiddenWord[j] = String.valueOf(playersGuess.charAt(j));
                        }
                        outputWord = "";
                    } else {
                        if(playersTurn == splitNames.length - 1) {
                            playersTurn = 0;
                        } else {
                            playersTurn++;
                        }
                    }
                    clearScreen();
                }
                if(outputWord.length() == 0) {
                    clearScreen();
                    break;
                }
            }
            System.out.print("\n\nCongratulations, you guessed the word correctly.\nThe word was:  ");
            for(int k = 0; k < hiddenWord.length; k++) {
                System.out.print(hiddenWord[k] + " ");
            }
            System.out.println("\n");
        }

        System.out.println("Players points:\n");
        for(int i = 0; i < splitNames.length; i++) {
            int order = playersOrderPoints[0][i];
            System.out.print((i + 1) + ". ");
            System.out.println(splitNames[order] + ":   " + playersOrderPoints[1][i]);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}