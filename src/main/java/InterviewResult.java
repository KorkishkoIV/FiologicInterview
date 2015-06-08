import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivankorksiko on 05/06/15.
 */
public class InterviewResult {

    private int maleParticipants;
    private int femaleParticipants;
    private AgeRow[] aging;
    private ProfessionRow[] professionalism;
    private Map<String, Answer[]> answers;

    public int getMaleParticipants() {
        return maleParticipants;
    }

    public void setMaleParticipants(int maleParticipants) {
        this.maleParticipants = maleParticipants;
    }

    public int getFemaleParticipants() {
        return femaleParticipants;
    }

    public void setFemaleParticipants(int femaleParticipants) {
        this.femaleParticipants = femaleParticipants;
    }

    public AgeRow[] getAging() {
        return aging;
    }

    public void setAging(AgeRow[] aging) {
        this.aging = aging;
    }

    public void setAging(Map<Integer, Integer> ageMap) {
        this.aging = new AgeRow[ageMap.keySet().size()];
        int index = 0;
        for (Integer age: ageMap.keySet()){
            AgeRow tempAgeRow = new AgeRow();
            tempAgeRow.age = age;
            tempAgeRow.count = ageMap.get(age);
            this.aging[index++] = tempAgeRow;
        }

        Arrays.sort(this.aging, new Comparator<AgeRow>() {
            public int compare(AgeRow o1, AgeRow o2) {
                return Integer.compare(o1.age,o2.age);
            }
        });
    }

    public ProfessionRow[] getProfessionalism() {
        return professionalism;
    }

    public void setProfessionalism(ProfessionRow[] professionalism) {
        this.professionalism = professionalism;
    }

    public void setProfessionalism(Map<String,Integer> professionMap) {
        this.professionalism = new ProfessionRow[professionMap.keySet().size()];
        int index = 0;
        for (String profession:professionMap.keySet()){
            ProfessionRow tempProfessionRow = new ProfessionRow();
            tempProfessionRow.profession = profession;
            tempProfessionRow.count = professionMap.get(profession);
            this.professionalism[index++] = tempProfessionRow;
        }
        Arrays.sort(this.professionalism, new Comparator<ProfessionRow>() {
            public int compare(ProfessionRow o1, ProfessionRow o2) {
                return o1.profession.toLowerCase().compareTo(o2.profession.toLowerCase());
            }
        });
    }

    public Map<String, Answer[]> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Answer[]> answers) {
        this.answers = answers;
    }

    public void setAnswersWithMap(Map<String,Map<String, Integer>> answersMap) {
        this.answers = new HashMap<String, Answer[]>();
        for (String category: answersMap.keySet()){
            Map <String,Integer> categoryMap = answersMap.get(category);
            Answer[] tempAnswers = new Answer[categoryMap.keySet().size()];
            int index = 0;
            for (String answer: categoryMap.keySet()){
                Answer tempAnswer = new Answer();
                tempAnswer.answer = answer;
                tempAnswer.count = categoryMap.get(answer);
                tempAnswers[index++] = tempAnswer;
            }
            this.answers.put(category,tempAnswers);
        }
    }

    public void setAnswersForCategory(String category,Map<String,Integer> categoryMap){
        Answer[] tempAnswers = new Answer[categoryMap.keySet().size()];
        int index = 0;
        for (String answer: categoryMap.keySet()){
            Answer tempAnswer = new Answer();
            tempAnswer.answer = answer;
            tempAnswer.count = categoryMap.get(answer);
            tempAnswers[index++] = tempAnswer;
        }
        Arrays.sort(tempAnswers, new Comparator<Answer>() {
            public int compare(Answer o1, Answer o2) {
                if (o1.count == o2.count){
                    return o1.answer.compareTo(o2.answer);
                }
                return -Integer.compare(o1.count,o2.count);
            }
        });
        this.answers.put(category,tempAnswers);

    }

    public Answer[] getAnswersFromCategory(String category){
        return answers.get(category);
    }

    public void saveToFile (File file) throws FileNotFoundException{
        PrintWriter printWriter = new PrintWriter(file);

                    printWriter.println("---------------------------ОТЧЕТ---------------------------");
                    printWriter.println("------------------------Оценка пола------------------------");
                    printWriter.println("Мужской________________________________" + maleParticipants);
                    printWriter.println("Женский________________________________" + femaleParticipants);
                    printWriter.println();
                    if (aging != null) {
                        printWriter.println("----------------------Оценка возраста----------------------");
                        for (AgeRow resultAge : aging) {
                            printWriter.println(resultAge.age + "\t\t\t" + resultAge.count);
                        }
                        printWriter.println();
                    }
        if (professionalism != null) {
            printWriter.println("------------------Оценка рода деятельности-----------------");
            for (ProfessionRow resultProfession : professionalism) {

                String professionCopy = resultProfession.profession;
                while (professionCopy.length() < 50) {
                    professionCopy = professionCopy.concat(" ");
                }
                printWriter.println(professionCopy + resultProfession.count);
            }

            System.out.println();
        }
        if (answers != null) {
            for (String category : answers.keySet()) {
                printWriter.println("-----------------------------------------------------------------------------");
                printWriter.println(category.toUpperCase() + ":");
                Answer[] answersMap = answers.get(category);
                for (Answer answer : answersMap) {
                    String answerCopy = answer.answer;
                    while (answerCopy.length() < 75) {
                        answerCopy = answerCopy.concat(" ");
                    }
                    printWriter.println(answerCopy + answer.count);
                }
                printWriter.println("-----------------------------------------------------------------------------");
                printWriter.println();
            }
        }

        printWriter.close();
    }

}

class AgeRow {
    public int age;
    public int count;
}

class ProfessionRow {
    public String profession;
    public int count;
}

class Answer {
    public String answer;
    public int count;
}