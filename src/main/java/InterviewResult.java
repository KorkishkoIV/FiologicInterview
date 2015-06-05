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
        this.answers.put(category,tempAnswers);
    }

    public Answer[] getAnswersFromCategory(String category){
        return answers.get(category);
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