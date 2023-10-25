package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DAO.VraagDAO;
import nl.han.oose.OOAD.Managers.QuizManager;
import nl.han.oose.OOAD.Managers.RegistrationManager;

public class Initializer {

    public void run(ParolaController parolaController) {
        QuizManager quizManager = new QuizManager();
        RegistrationManager registrationManager = new RegistrationManager();
        run(quizManager);
        run(registrationManager);
        parolaController.setQuizManager(quizManager);
        parolaController.setRegistrationManager(registrationManager);

//        QuizDAO quizDAO = new QuizDAO();
        VraagDAO vraagDAO = new VraagDAO();
//        UserDAO userDAO = new UserDAO();
//        parolaController.setQuizDAO(quizDAO);
//        parolaController.setUserDAO(userDAO);
        parolaController.setVraagDAO(vraagDAO);

    }

    public void run(QuizManager quizManager) {
        QuizDAO quizDAO = new QuizDAO();
        quizManager.setQuizDAO(quizDAO);
    }

    public void run(RegistrationManager registrationManager) {
        UserDAO userDAO = new UserDAO();
        registrationManager.setUserDAO(userDAO);
    }

}
