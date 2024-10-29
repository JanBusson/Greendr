import javax.swing.*;

public class GreendrApp {
	
	public static void main(String[] args) {

                ScreenSwitcher screenSwitcher = new ScreenSwitcher();
                StartView startView = new StartView(screenSwitcher);
                LoginView loginView = new LoginView(screenSwitcher);
                RegisterView registerView = new RegisterView(screenSwitcher);
                HelpView helpView = new HelpView(screenSwitcher);
                WelcomePanel welcomePanel= new WelcomePanel(screenSwitcher);

                // Erstellung von Swipe und LikedUsersPanel erfolgt erst bei WelcomeView

                GeneralInfo1View generalInfo1View = new GeneralInfo1View(screenSwitcher);
                GeneralInfo2View generalInfo2View = new GeneralInfo2View(screenSwitcher);
                GeneralInfo3View generalInfo3View = new GeneralInfo3View(screenSwitcher);
                GeneralInfo4View generalInfo4View = new GeneralInfo4View(screenSwitcher);
                
                FreeTimeView freeTimeView = new FreeTimeView(screenSwitcher);
                DescriptionView descriptionView = new DescriptionView(screenSwitcher);
                HobbiesView hobbiesView = new HobbiesView(screenSwitcher);
                FinishCreateProfileView finishCreateProfileView = new FinishCreateProfileView(screenSwitcher);
                
                QuizModel q1 = new QuizModel();
                CurrentQuizModel q2 = new CurrentQuizModel();
                QuizView quizView = new QuizView(q1,q2,screenSwitcher);
                
                screenSwitcher.addView("generalInfo1View", generalInfo1View);
                screenSwitcher.addView("generalInfo2View", generalInfo2View);
                screenSwitcher.addView("generalInfo3View", generalInfo3View);
                screenSwitcher.addView("generalInfo4View", generalInfo4View);
                screenSwitcher.addView("freeTimeView", freeTimeView);
                screenSwitcher.addView("descriptionView", descriptionView);
                screenSwitcher.addView("hobbiesView", hobbiesView);
                screenSwitcher.addView("finishCreateProfileView", finishCreateProfileView);
                screenSwitcher.addView("quizView", quizView);
                
                
                screenSwitcher.addView("startView", startView);
                screenSwitcher.addView("loginView", loginView);
                screenSwitcher.addView("registerView", registerView);
                
                
                screenSwitcher.addView("helpView", helpView);
                screenSwitcher.addView("generalInfo1View", generalInfo1View);
                screenSwitcher.addView("welcomePanel", welcomePanel);
              

                screenSwitcher.showView("startView");
                screenSwitcher.setVisible(true);
        }
    }
