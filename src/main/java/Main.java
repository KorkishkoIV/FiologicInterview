import com.opencsv.CSVReader;
import org.apache.commons.lang3.ObjectUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ivankorksiko on 02/06/15.
 */


public class Main {

    private static int femaleParticipants;
    private static int maleParticipants;
    private static Map<Integer,Integer> ageStat;
    private static Map<String,Integer> professionStat;
    public static final String[] categories = {"Азбука","Байкал","Вертолет","Гагарин","Дон","Елка","Жизнь","Зерно","Империя","Герой",
        "Калина","Лес","Москва","Народ","Орел","Победа","Россия","Спутник","Театр","Ушанка","Хлеб","Царь","Чайка","Шуба","Щи","Юность","Я"};

    public static void main(String[] args) {




//        try {
//            //csv file containing data
//            String strFile = "/Users/ivankorksiko/Downloads/Ассоциативный эксперимент ответы (1) - Ответы на форму (1).csv";
//            CSVReader reader = new CSVReader(new FileReader(strFile));
//            String[] nextLine;
//            int lineNumber = 0;
//            while ((nextLine = reader.readNext()) != null) {
//                lineNumber++;
//                System.out.println("Line # " + lineNumber);
//                if (lineNumber > 1){
//
//                    int age = Integer.parseInt(nextLine[1]);
//                    if (!ageStat.containsKey(age)){
//                        ageStat.put(age,1);
//                    }else{
//                        ageStat.put(age,ageStat.get(age)+1);
//                    }
//
//                    String profession = nextLine[3];
//                    if (!professionStat.containsKey(profession)){
//                        professionStat.put(profession,1);
//                    }else {
//                        professionStat.put(profession,professionStat.get(profession)+1);
//                    }
//
//                    String gender = nextLine[2];
//                    if(gender.equalsIgnoreCase("женский")){
//                        femaleParticipants++;
//                    }
//                    if(gender.equalsIgnoreCase("мужской")){
//                        maleParticipants++;
//                    }
//
//                    for (int index = 4; index < nextLine.length; index++){
//
//                        String answer = nextLine[index].trim().toLowerCase().replaceAll("[^a-zа-я0-9ё -]","");
//                        if (answer.length() == 0){
//                            answer = "-";
//                        }
//                        String category = categories[((index + 1)/5)-1];
//
//                        Map<String,Integer> answerMap = AnswersAdapter.sharedAdapter().getAnswers(category);
//                        if (answerMap.containsKey(answer)){
//                            answerMap.put(answer,answerMap.get(answer)+1);
//                        }else{
//                            answerMap.put(answer,1);
//                        }
//                    }
//
//                    System.out.println("---------------------------ОТЧЕТ---------------------------");
//                    System.out.println("------------------------Оценка пола------------------------");
//                    System.out.println("Мужской________________________________" + maleParticipants);
//                    System.out.println("Женский________________________________" + femaleParticipants);
//                    System.out.println();
//                    System.out.println("----------------------Оценка возраста----------------------");
//                    for(Integer resultAge:ageStat.keySet()){
//                        System.out.println(resultAge + "__________________________" + ageStat.get(resultAge));
//                    }
//                    System.out.println();
//                    System.out.println("------------------Оценка рода деятельности-----------------");
//                    for (String resultProfesion:professionStat.keySet()){
//
//                        System.out.println(resultProfesion + "__________________" + professionStat.get(resultProfesion));
//                    }
//                    System.out.println();
//                    for (String category: categories){
//                        System.out.println("-----------------------------------------------------------------------------");
//                        System.out.println(category.toUpperCase() + ":");
//                        Map<String,Integer> answersMap = AnswersAdapter.sharedAdapter().getAnswers(category);
//                        for (String answer:answersMap.keySet()){
//                            String answerCopy = answer;
//                            while (answerCopy.length() < 75){
//                                answerCopy = answerCopy.concat(" ");
//                            }
//                            System.out.println(answerCopy + answersMap.get(answer));
//                        }
//                        System.out.println("-----------------------------------------------------------------------------");
//                        System.out.println();
//                    }
//                }
//            }
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
    }


    public static void compute (File file, MainWindow mainWindow){
        ageStat = new HashMap<Integer, Integer>();
        professionStat = new HashMap<String, Integer>();
        try {
            //csv file containing data
            String strFile = "/Users/ivankorksiko/Downloads/Ассоциативный эксперимент ответы (1) - Ответы на форму (1).csv";
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] nextLine;
            int lineNumber = 0;
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                System.out.println("Line # " + lineNumber);
                if (lineNumber > 1) {

                    int age = Integer.parseInt(nextLine[1]);
                    if (!ageStat.containsKey(age)) {
                        ageStat.put(age, 1);
                    } else {
                        ageStat.put(age, ageStat.get(age) + 1);
                    }

                    String profession = nextLine[3];
                    if (!professionStat.containsKey(profession)) {
                        professionStat.put(profession, 1);
                    } else {
                        professionStat.put(profession, professionStat.get(profession) + 1);
                    }

                    String gender = nextLine[2];
                    if (gender.equalsIgnoreCase("женский")) {
                        femaleParticipants++;
                    }
                    if (gender.equalsIgnoreCase("мужской")) {
                        maleParticipants++;
                    }

                    for (int index = 4; index < nextLine.length; index++) {

                        String answer = nextLine[index].trim().toLowerCase().replaceAll("[^a-zа-я0-9ё -]", "");
                        if (answer.length() == 0) {
                            answer = "-";
                        }
                        String category = categories[((index + 1) / 5) - 1];

                        Map<String, Integer> answerMap = AnswersAdapter.sharedAdapter().getAnswers(category);
                        if (answerMap.containsKey(answer)) {
                            answerMap.put(answer, answerMap.get(answer) + 1);
                        } else {
                            answerMap.put(answer, 1);
                        }
                    }

                    InterviewResult ir = new InterviewResult();
                    ir.setMaleParticipants(maleParticipants);
                    ir.setFemaleParticipants(femaleParticipants);
                    ir.setAging(ageStat);
                    ir.setProfessionalism(professionStat);
                    ir.setAnswers(new HashMap<String, Answer[]>());
                    for (String category:categories){
                        ir.setAnswersForCategory(category,AnswersAdapter.sharedAdapter().getAnswers(category));
                    }
                    mainWindow.setInterviewResult(ir);

//                   mainWindow.addLine("---------------------------ОТЧЕТ---------------------------");
//                    mainWindow.addLine("------------------------Оценка пола------------------------");
//                    mainWindow.addLine("Мужской________________________________" + maleParticipants);
//                    mainWindow.addLine("Женский________________________________" + femaleParticipants);
//                    mainWindow.addLine();
//                    mainWindow.addLine("----------------------Оценка возраста----------------------");
//                    for (Integer resultAge : ageStat.keySet()) {
//                        mainWindow.addLine(resultAge + "__________________________" + ageStat.get(resultAge));
//                    }
//                    mainWindow.addLine();
//                    mainWindow.addLine("------------------Оценка рода деятельности-----------------");
//                    for (String resultProfesion : professionStat.keySet()) {
//
//                        mainWindow.addLine(resultProfesion + "__________________" + professionStat.get(resultProfesion));
//                    }
//                    mainWindow.addLine();
//                    for (String category : categories) {
//                        mainWindow.addLine("-----------------------------------------------------------------------------");
//                        mainWindow.addLine(category.toUpperCase() + ":");
//                        Map<String, Integer> answersMap = AnswersAdapter.sharedAdapter().getAnswers(category);
//                        for (String answer : answersMap.keySet()) {
//                            String answerCopy = answer;
//                            while (answerCopy.length() < 100) {
//                                answerCopy = answerCopy.concat(" ");
//                            }
//                            mainWindow.addLine(answerCopy + answersMap.get(answer));
//                        }
//                        mainWindow.addLine("-----------------------------------------------------------------------------");
//                        mainWindow.addLine();
//                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mainWindow.complete();
        }

    }

}

class AnswersAdapter
{
    private Map<String,Map<String,Integer>> answers;

    private AnswersAdapter(){
        this.answers = new TreeMap<String, Map<String, Integer>>();
    }

    private static AnswersAdapter sharedAdapter;

    public static AnswersAdapter sharedAdapter(){
        if (sharedAdapter == null){
            sharedAdapter = new AnswersAdapter();
        }
        return sharedAdapter;
    }

    public Map getAnswers (String category){
        if (!answers.containsKey(category)){
            answers.put(category,new TreeMap<String, Integer>());
        }
        return answers.get(category);
    }
}
