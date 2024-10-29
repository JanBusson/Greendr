import java.util.ArrayList;

import javax.swing.JPanel;

public class QuizModel extends JPanel{
    private ArrayList<String> questionTextList;
    private ArrayList<ArrayList<String>> answersTextList;

    public QuizModel() {
        // Initialize the lists as instance variables
        questionTextList = new ArrayList<>();
        answersTextList = new ArrayList<>();

        // Add questions
        questionTextList.add("1. How often do you use public transportation instead of driving?");
        questionTextList.add("2. How do you typically handle your food waste?");
        questionTextList.add("3. How energy-efficient is your home?");
        questionTextList.add("4. How often do you purchase second-hand or recycled products?");
        questionTextList.add("5. What is your approach to water conservation?");
        questionTextList.add("6. How do you handle recycling at home?");
        questionTextList.add("7. How often do you choose sustainable or eco-friendly brands when shopping?");
        questionTextList.add("8. How do you manage your energy consumption at home?");
        questionTextList.add("9. How do you handle clothing and textile waste?");
        questionTextList.add("10. How frequently do you support or engage in community sustainability initiatives?");

        // Add answers
        ArrayList<String> answers1 = new ArrayList<>();
        answers1.add("Every day");
        answers1.add("A few times a week");
        answers1.add("Occasionally");
        answers1.add("Rarely");
        answers1.add("Never");
        answersTextList.add(answers1);

        ArrayList<String> answers2 = new ArrayList<>();
        answers2.add("Compost it");
        answers2.add("Use it to feed animals");
        answers2.add("Recycle through a food waste collection service");
        answers2.add("Throw it in the regular trash");
        answers2.add("Ignore it completely");
        answersTextList.add(answers2);

        ArrayList<String> answers3 = new ArrayList<>();
        answers3.add("Fully equipped with energy saving appliances and insulation");
        answers3.add("Mostly equipped with energy saving appliances");
        answers3.add("Some energy saving features");
        answers3.add("Few energy saving features");
        answers3.add("No energy saving features");
        answersTextList.add(answers3);

        ArrayList<String> answers4 = new ArrayList<>();
        answers4.add("Very frequently");
        answers4.add("Often");
        answers4.add("Sometimes");
        answers4.add("Rarely");
        answers4.add("Never");
        answersTextList.add(answers4);

        ArrayList<String> answers5 = new ArrayList<>();
        answers5.add("Take short showers, always save water");
        answers5.add("Usually take measures to conserve water");
        answers5.add("Occasionally try to conserve water");
        answers5.add("Rarely think about water conservation");
        answers5.add("Never consider water conservation");
        answersTextList.add(answers5);

        ArrayList<String> answers6 = new ArrayList<>();
        answers6.add("Diligently separate and recycle all recyclable materials");
        answers6.add("Recycle most of the time");
        answers6.add("Recycle occasionally");
        answers6.add("Rarely recycle");
        answers6.add("Never recycle");
        answersTextList.add(answers6);

        ArrayList<String> answers7 = new ArrayList<>();
        answers7.add("Always");
        answers7.add("Most of the time");
        answers7.add("Sometimes");
        answers7.add("Rarely");
        answers7.add("Never");
        answersTextList.add(answers7);

        ArrayList<String> answers8 = new ArrayList<>();
        answers8.add("Regularly monitor and reduce usage");
        answers8.add("Often try to reduce usage");
        answers8.add("Occasionally think about it");
        answers8.add("Rarely consider it");
        answers8.add("Never think about it");
        answersTextList.add(answers8);

        ArrayList<String> answers9 = new ArrayList<>();
        answers9.add("Donate, repurpose, or recycle all textiles");
        answers9.add("Donate or recycle most textiles");
        answers9.add("Occasionally donate or recycle");
        answers9.add("Rarely consider it");
        answers9.add("Never consider it");
        answersTextList.add(answers9);

        ArrayList<String> answers10 = new ArrayList<>();
        answers10.add("Very frequently");
        answers10.add("Often");
        answers10.add("Sometimes");
        answers10.add("Rarely");
        answers10.add("Never");
        answersTextList.add(answers10);
    }

    public String getQuestion(int index) {
        return questionTextList.get(index);
    }

    public ArrayList<String> getAnswers(int index) {
        return answersTextList.get(index);
    }

    public int getNumberOfQuestions() {
        return questionTextList.size();
    }
}
