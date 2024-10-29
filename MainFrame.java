import java.awt.Dimension;
import javax.swing.JFrame;

public class MainFrame {
    public static void main(String[] args) {
        QuizModel q1 = new QuizModel();
        
        CurrentQuizModel q2 = new CurrentQuizModel();
        ScreenSwitcher shownFrame = new ScreenSwitcher();
        shownFrame.addView("q1", new QuizView(q1, q2, shownFrame));
        shownFrame.showView("q1");
        
        shownFrame.setVisible(true);        
        
    }
}
