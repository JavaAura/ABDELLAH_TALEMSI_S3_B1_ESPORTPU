package org.tournoiPlace.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tournoiPlace.service.TournamentService;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        TournamentService tournamentService = context.getBean(TournamentService.class);

        tournamentService.runSampleOperations();
    }
}