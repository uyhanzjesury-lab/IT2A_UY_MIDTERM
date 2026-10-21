
package scc.com;

import scc.com.dao.ScenarioDao;
import scc.com.ui.ScenarioMenu;

import java.util.Scanner;

public class Main {

    public static final Scanner sc = new Scanner(System.in);

    private static final ScenarioDao scenarioDao = new ScenarioDao();

    public static void main(String[] args) {

        ScenarioMenu menu = new ScenarioMenu(sc, scenarioDao);
        menu.display();

        sc.close();
    }
}
