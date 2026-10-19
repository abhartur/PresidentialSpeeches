
/**
 * Write a description of class presidentialSpeeches here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Presidential_Speeches
{
    public static void main(String[] args) throws IOException{
        // Three of the Eras, two per each, repeat the loop for six times.
        // Array is used here
        String[] filenames = {
            "speeches/George_Washington_1789-04-30T13_03_58-04_56_April_30,_1789__First_Inaugural_Address.json",
            "speeches/Thomas_Jefferson_1801-03-04T13_03_58-04_56_March_4,_1801__First_Inaugural_Address.json",
            "speeches/Abraham_Lincoln_1861-03-04T13_03_58-04_56_March_4,_1861__First_Inaugural_Address.json",
            "speeches/Theodore_Roosevelt_1905-03-04T13_00_00-05_00_March_4,_1905__Inaugural_Address.json",
            "speeches/John_F._Kennedy_1961-01-20T13_00_00-05_00_January_20,_1961__Inaugural_Address.json",
            "speeches/Barack_Obama_2009-01-20T13_00_00-05_00_January_20,_2009__Inaugural_Address.json"
        };
        String[] eras = {"Early", "Early", "Middle", "Middle", "Modern", "Modern"};
        for (int x = 0; x < filenames.length; x++){
            String filename = filenames[x];
            System.out.println("Speech " + (x + 1) + " of 6 | Era: " + eras[x]);
            String json = readFile(filename);

            String president = extractJsonValue(json, "president");
            String date = extractJsonValue(json, "date");
            String speechText = extractJsonValue(json, "transcript");

            speechText = speechText.replace("\\r", " ");
            speechText = speechText.replace("\\n", " ");
            speechText = speechText.replace("\\t", " ");

            System.out.println("================================");
            System.out.println("File: " + filename);
            System.out.println("President: " + president);
            System.out.println("Date: " + date);

            analyzeSpeech(speechText);

            System.out.println("================================");
        } // Loop ends
    }


    public static String readFile(String filename) throws IOException{
        File file = new File(filename);
        Scanner fileScan = new Scanner(file, "UTF-8");

        String content = "";

        // If file still has next line, then keep reading the file.
        while (fileScan.hasNextLine()){
            content += fileScan.nextLine() + " ";
        }

        fileScan.close();

        // return the values to where it called
        return content;
    }


    // input json and String"president"，return president name
    public static String extractJsonValue(String json, String key){
        // key is president;
        // keyTarget is "president":
        String keyTarget = "\"" + key + "\":";

        int keyIndex = json.indexOf(keyTarget);

        // If can't find the String, return
        if (keyIndex == -1){
            return "";
        }

        // Find the " at the beginning of the json file 
        int startQuote = json.indexOf("\"", keyIndex + keyTarget.length());

        if (startQuote == -1){
            return "";
        }

        // Find the " at the End of the json file 
        int endQuote = json.indexOf("\"", startQuote + 1);

        // skip " in between the sentence 
        while (endQuote != -1 && json.charAt(endQuote - 1) == '\\'){
            endQuote = json.indexOf("\"", endQuote + 1);
        }

        if (endQuote == -1){
            return "";
        }

        // read word that's between the quote and not include Quote 
        return json.substring(startQuote + 1, endQuote);
    }


    // Count words in the Json file
    public static void analyzeSpeech(String speechText){
        // six counter that's started from zero
        int totalWords = 0;
        int totalLetters = 0;
        int peopleCount = 0;
        int governmentCount = 0;
        int unitedCount = 0;
        int ourCount = 0;
        
       //Scanner from the file not keyboard
        Scanner words = new Scanner(speechText);

        // repeat until no words left in the file
        while (words.hasNext()){
            String word = words.next();

            // lowercase the input so java won't miscount
            word = word.toLowerCase();

            // Replace all punctuation to letters(new things I learn online)
            word = word.replaceAll("[^a-z]", "");

            if (word.length() > 0){
                totalWords++;
                totalLetters += word.length();

                if (word.equals("people")){
                    peopleCount++;
                }else if (word.equals("government")){
                    governmentCount++;
                }else if (word.equals("united")){
                    unitedCount++;
                }else if (word.equals("our")){
                    ourCount++;
                }
            }
        }
        
        // close file
        
        words.close();
        // If can't found any words that we try to find, this run
        if (totalWords == 0){
            System.out.println("No words found. Check the text field.");
            return;
        }
        
        // Print out datas after loops stop
        double averageLength = (double) totalLetters / totalWords;

        System.out.println("Total words: " + totalWords);
        System.out.printf("Average word length: %.2f%n", averageLength);

        printWordResult("people", peopleCount, totalWords);
        printWordResult("government", governmentCount, totalWords);
        printWordResult("united", unitedCount, totalWords);
        printWordResult("our", ourCount, totalWords);
    }
    
    //Method that do the percentagehi
    public static void printWordResult(String word, int count, int totalWords){
        double percentage = count * 100.0 / totalWords;
        System.out.printf("%s: %d occurrences, %.2f%%%n",word, count, percentage);
    }
}


