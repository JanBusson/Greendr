import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuizView extends GraphicPanel {
    private JTextArea questionLabel;
    private JRadioButton[] answerButtons;
    private ButtonGroup buttonGroup;
    private JButton continueButton;
    private JButton finishButton;
    private QuizModel quizModel;
    private CurrentQuizModel currentQuizModel;
    private GraphicPanel mainPanel;
    private GraphicPanel questionPanel;
    private GraphicPanel answersPanel;
    private GraphicPanel buttonsPanel;
    private ScreenSwitcher shownFrame;

    public QuizView(QuizModel quizModel, CurrentQuizModel currentQuizModel, ScreenSwitcher screenSwitcher) {
        this.quizModel = quizModel;
        this.currentQuizModel = currentQuizModel;
        this.shownFrame = screenSwitcher;

        initializeComponents();
        layoutComponents();
        addListeners();
        setQuestionSet(currentQuizModel.getIndex());
        setSize(400, 300);
        setVisible(true);
    }

    private void initializeComponents() {
    	//External knowledge used -> see 7. JTextArea - Oracle Docs file in /quellen
        questionLabel = new JTextArea();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        questionLabel.setLineWrap(true);
        questionLabel.setWrapStyleWord(true);
        questionLabel.setEditable(false);
        questionLabel.setOpaque(true);
        questionLabel.setBackground(Color.PINK);
        
        answerButtons = new JRadioButton[5];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            answerButtons[i] = new JRadioButton();
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            buttonGroup.add(answerButtons[i]);
        }

        continueButton = new StyleButton("Continue");
        finishButton = new StyleButton("Finish");
        finishButton.setEnabled(false);

        mainPanel = new GraphicPanel(new GridBagLayout());
        questionPanel = new GraphicPanel();
        answersPanel = new GraphicPanel();
        buttonsPanel = new GraphicPanel();
    }

    private void layoutComponents() {
    	//External knowledge used -> see 2. GridBagLayout - Oracle Docs file in /quellen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        //External knowledge used -> see 4. Insets - Oracle Docs file in /quellen
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        
        questionPanel.setPreferredSize(new Dimension(400, 100)); // Adjust width as needed
        answersPanel.setPreferredSize(new Dimension(400, 200)); // Adjust width as needed
        
        questionPanel.setLayout(new BorderLayout());
        questionPanel.add(new JScrollPane(questionLabel), BorderLayout.CENTER);
        questionPanel.setBackground(Color.PINK);
        
        answersPanel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        for (JRadioButton button : answerButtons) {
            gbc.gridy++;
            answersPanel.add(button, gbc);
        }
        answersPanel.setBackground(Color.PINK);

        buttonsPanel.setLayout(new GridBagLayout());
        gbc.gridy = 0;
        gbc.gridx = 0;
        buttonsPanel.add(continueButton, gbc);
        gbc.gridx = 1;
        buttonsPanel.add(finishButton, gbc);
        buttonsPanel.setBackground(Color.PINK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(questionPanel, gbc);

        gbc.gridy = 1;
        mainPanel.add(answersPanel, gbc);

        gbc.gridy = 2;
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel);
    }

    private void addListeners() {
        continueButton.addActionListener(new HandleContinueButton());
        finishButton.addActionListener(new HandleFinishButton());
    }

    private class HandleContinueButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 5; i++) {
                if (answerButtons[i].isSelected()) {
                    currentQuizModel.IncrementScore(10 - (i * 2.5));
                    break;
                }
            }
            currentQuizModel.incrementIndex();
            if (currentQuizModel.getIndex() < quizModel.getNumberOfQuestions()) {
                setQuestionSet(currentQuizModel.getIndex());
            } else {
                continueButton.setEnabled(false);
                finishButton.setEnabled(true);
            }
        }
    }

    private class HandleFinishButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double finalScore = currentQuizModel.getScore();
            String message = getFinalMessage(finalScore);
            TechnicalFunctions.insertScore(currentQuizModel, shownFrame);
            JOptionPane.showMessageDialog(QuizView.this, "YOUR FINAL SCORE IS: " + finalScore + "\n" + message);
            shownFrame.showView("welcomePanel");
            System.out.println("Finished Eco-Test Succesfully, database updated!");
        }
    }

    private String getFinalMessage(double finalScore) {
        if (finalScore >= 75) {
            return "You are very sustainable, in the top 25% to be exact. You can be proud of yourself!";
        } else if (finalScore >= 50) {
            return "You are definitely in the top half. You can be proud of your sustainable habits, but there is always room for improvement.";
        } else if (finalScore >= 25) {
            return "You can do better for sure! But do not be discouraged. You can always improve whenever, find some ideas on the internet.";
        } else {
            return "You should not be on this app. This is for people who are interested in a sustainable lifestyle.";
        }
    }

    public void setQuestionSet(int index) {
        String question = quizModel.getQuestion(index);
        ArrayList<String> answers = quizModel.getAnswers(index);
        questionLabel.setText(question);
        for (int i = 0; i < answers.size(); i++) {
            answerButtons[i].setText(answers.get(i));
        }
        buttonGroup.clearSelection();
    }

    
}