
package scc.com.ui;

import scc.com.dao.ScenarioDao;
import scc.com.model.ScenarioEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {

    private final Scanner sc;
    private final ScenarioDao scenarioDao;

    public ScenarioMenu(Scanner sc, ScenarioDao scenarioDao) {
        this.sc = sc;
        this.scenarioDao = scenarioDao;
    }

    public void display() {

        try {
            scenarioDao.createTable();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return;
        }

        boolean running = true;

        while (running) {

            System.out.println("\n================================");
            System.out.println(" PET CLINIC VACCINATION LOG");
            System.out.println("================================");
            System.out.println("1. Add Pet Record");
            System.out.println("2. View Vaccination Records");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAddRecord();
                    break;

                case "2":
                    handleViewRecords();
                    break;

                case "3":
                    System.out.println("Exiting system. Thank you!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    private void handleAddRecord() {

        System.out.print("Enter Pet Name: ");
        String petName = sc.nextLine().trim();

        if (petName.isEmpty()) {
            System.out.println("Pet name cannot be empty!");
            return;
        }

        System.out.println("Select Species: [1] DOG [2] CAT [3] BIRD");
        System.out.print("Choose species: ");

        String species;

        switch (sc.nextLine().trim()) {
            case "1":
                species = "DOG";
                break;
            case "2":
                species = "CAT";
                break;
            case "3":
                species = "BIRD";
                break;
            default:
                System.out.println("Invalid species selection!");
                return;
        }

        System.out.println("Select Vaccine: [1] RABIES [2] DHPP [3] FVRCP");
        System.out.print("Choose vaccine: ");

        String vaccine;

        switch (sc.nextLine().trim()) {
            case "1":
                vaccine = "RABIES";
                break;
            case "2":
                vaccine = "DHPP";
                break;
            case "3":
                vaccine = "FVRCP";
                break;
            default:
                System.out.println("Invalid vaccine selection!");
                return;
        }

        System.out.print("Enter Service Fee: ");
        String feeInput = sc.nextLine().trim();

        double fee;

        try {
            fee = Double.parseDouble(feeInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid fee. Please enter a number.");
            return;
        }

        if (!Double.isFinite(fee) || fee < 0) {
            System.out.println("Fee must be a valid non-negative number.");
            return;
        }

        ScenarioEntity record = new ScenarioEntity(
                0, petName, species, vaccine, fee
        );

        try {
            boolean saved = scenarioDao.addRecord(record);

            if (saved) {
                System.out.println("Pet vaccination record saved successfully!");
            } else {
                System.out.println("Record was not saved.");
            }

        } catch (SQLException e) {
            System.out.println("Error saving record: " + e.getMessage());
        }
    }

    private void handleViewRecords() {

        try {
            List<ScenarioEntity> records = scenarioDao.viewRecords();

            System.out.println("\n==============================================================");
            System.out.println("                 PET VACCINATION RECORDS");
            System.out.println("==============================================================");

            System.out.printf(
                    "| %-3s | %-15s | %-8s | %-8s | %9s |%n",
                    "ID", "PET NAME", "SPECIES", "VACCINE", "FEE"
            );

            System.out.println(
                    "+-----+-----------------+----------+----------+-----------+"
            );

            for (ScenarioEntity record : records) {
                System.out.printf(
                        "| %-3d | %-15s | %-8s | %-8s | %9.2f |%n",
                        record.id(),
                        record.petname(),
                        record.species(),
                        record.vaccine(),
                        record.fee()
                );
            }

            System.out.println(
                    "+-----+-----------------+----------+----------+-----------+"
            );

            System.out.println("Total Records Displayed: " + records.size());

        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }
}
