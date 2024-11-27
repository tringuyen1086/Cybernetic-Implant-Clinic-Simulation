package clinic;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The {@code ClinicDriver} class serves as the entry point of the application.
 * It demonstrates the operations available in the clinic model,
 * such as registering patients,assigning rooms, displaying seating charts,
 * and managing clinical staff members.
 * This class provides an interactive menu-driven system to showcase key functionalities.
 */
public class ClinicDriver {

  /**
   * The main method that serves as the starting point of the program.
   * It provides a menu for users to interact with the clinic system.
   *
   * @param args Command-line arguments. If provided, the first argument is treated as
   *             the file path to the clinic data. Otherwise, the user will be prompted for input.
   */
  public static void main(String[] args) {

    /*
      Load clinic data from the file path provided as a command-line argument
      terminal: can use the sample path: C:\Users\tring\Downloads\clinicfile2.txt
      terminal:
      method 1: command-line with local file path
      java -jar milestone-1-tringuyen1086.jar C:\Users\tring\Downloads\clinicfile2.txt
      method 2: command-line with local file path (alternative)
      java -jar milestone-1-tringuyen1086.jar "C:\Users\tring\Downloads\clinicfile2.txt"
      method 3: command-line with URL
      "https://drive.google.com/uc?export=download&id=19o_vIAUEXyqZr951AIK7jECKzwHCQdzq"
      gitbash:
      method 1: command-line with local file path
      java -jar milestone-1-tringuyen1086.jar "C:\Users\tring\Downloads\clinicfile2.txt"
      method 2: command-line with URL (replace URL with the provided sample URL)
      java -jar milestone-1-tringuyen1086.jar "URL"
      Sample URL path:
      https://drive.google.com/uc?export=download&id=19o_vIAUEXyqZr951AIK7jECKzwHCQdzq
      Github Project Link: https://github.com/CS5010Fall2024/milestone-1-tringuyen1086
     */

    ClinicImplementation clinic = new ClinicImplementation();
    Scanner scanner = new Scanner(System.in); // Use Scanner for consistent input handling

    if (args.length > 0) {
      String filePath = args[0];
      try {
        clinic.loadClinicData(filePath);
        System.out.printf("Clinic data loaded successfully from: %s%n", filePath);
      } catch (IOException e) {
        System.err.printf("Error loading clinic data from the file path: %s%n", filePath);
        e.printStackTrace();
        return;
      }
    } else {
      System.out.println("No file path provided. "
              + "Please run the program with a file path argument.");
      return;
    }

    // Start the menu-driven system after loading the data
    runMenu(clinic, scanner);
  }


  /**
   * Helper method to run the interactive menu for user input after loading the clinic data.
   *
   * @param clinic  The clinic instance loaded with data.
   * @param scanner A {@code Scanner} object for reading user input.
   */
  private static void runMenu(ClinicImplementation clinic, Scanner scanner) {
    boolean exit = false;

    while (!exit) {
      System.out.println("\nChoose an option:");
      System.out.println("1. Register a new patient and add a visit record");
      System.out.println("2. Register an existing patient and enter a visit record");
      System.out.println("3. Register a clinical staff member");
      System.out.println("4. Assign a patient to a room");
      System.out.println("5. Assign a clinical staff member to a patient");
      System.out.println("6. Assign multiple clinical staff to a patient");
      System.out.println("7. Display information of all patients");
      System.out.println("8. Display information of a specific patient");
      System.out.println("9. Display information about a specific room");
      System.out.println("10. Display all rooms (Seating Chart)");
      System.out.println("11. Display all staff (Clinical and Nonclinical)");
      System.out.println("12. Display Clinical Staff Only");
      System.out.println("13. Display Non-Clinical Staff Only");
      System.out.println("14. Search staff by full name or identifier");
      System.out.println("15. Send a patient home (approved by physician)");
      System.out.println("16. Exit");
      System.out.print("Enter your choice: ");

      try {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline after the integer input


        switch (choice) {
          case 1:
            registerNewPatientVisit(clinic, scanner);
            break;
          case 2:
            registerExistingPatientVisit(clinic, scanner);
            break;
          case 3:
            registerClinicalStaff(clinic, scanner);
            break;
          case 4:
            assignPatientToRoom(clinic, scanner);
            break;
          case 5:
            assignSingleClinicalStaffToPatient(clinic, scanner);
            break;
          case 6:
            assignMultipleClinicalStaffToPatient(clinic, scanner);
            break;
          case 7:
            displayAllPatients(clinic);
            break;
          case 8:
            displayPatientInfo(clinic, scanner);
            break;
          case 9:
            displayRoomInfo(clinic, scanner);
            break;
          case 10:
            clinic.displaySeatingChart();
            break;
          case 11:
            listAllStaff(clinic);
            break;
          case 12:
            listClinicalStaffOnly(clinic);
            break;
          case 13:
            listNonClinicalStaffOnly(clinic);
            break;
          case 14:
            searchStaff(clinic, scanner);
            break;
          case 15:
            sendPatientHome(clinic, scanner);
            break;
          case 16:
            exit = true;
            break;
          default:
            System.out.println("Invalid choice. Try again.");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a valid number.");
        scanner.nextLine(); // Clear the invalid input
      }
    }
  }

  /**
   * Registers a new patient and adds a visit record.
   * Automatically assigns the newly registered patient to
   * the primary waiting room.
   * If the patient already exists, a new visit record is added
   * without changing their ID.
   *
   * <p>The method collects details
   * such as first name, last name, date of birth (DOB),
   * chief complaint, and body temperature (in Celsius) from the user.
   * It validates theDOB and temperature inputs
   * and then registers the new patient or updates an existing
   * patient's visit record in the clinic system.</p>
   *
   * @param clinic  The {@link Clinic} instance where the patient is being registered.
   * @param scanner The {@link Scanner} object used to read user input.
   */
  private static void registerNewPatientVisit(Clinic clinic, Scanner scanner) {
    // Collect patient details
    System.out.print("First Name: ");
    String firstName = scanner.nextLine();
    System.out.print("Last Name: ");
    String lastName = scanner.nextLine();

    // Validate Date of Birth (DOB)
    LocalDate dob = validateDateOfBirth(scanner);

    // Collect chief complaint
    System.out.print("Chief Complaint: ");
    String chiefComplaint = scanner.nextLine();

    // Validate body temperature using VisitRecord's validation logic
    double bodyTemperature = validateBodyTemperature(scanner);

    // Register the new patient and add the visit record
    clinic.registerNewPatientVisit(firstName, lastName, dob.toString(),
            LocalDateTime.now(), chiefComplaint, bodyTemperature);
  }

  /**
   * Registers a new visit record for an existing patient.
   * The method collects details
   * such as first name, last name, date of birth (DOB),
   * chief complaint, and body temperature (in Celsius) from the user.
   * It validates the DOB and temperature inputs, and adds the new visit record
   * to the existing patient.
   *
   * @param clinic  The {@link Clinic} instance
   *                where the patient's visit record is being added.
   * @param scanner The {@link Scanner} object used to read user input.
   */
  public static void registerExistingPatientVisit(Clinic clinic, Scanner scanner) {
    // Search for the patient by ID or name
    System.out.print("Enter patient name or ID: ");
    String searchTerm = scanner.nextLine();

    // Find the patient using the helper method in ClinicImplementation
    PatientInterface patient = clinic.searchPatient(searchTerm);

    // If no patient is found, prompt for registering a new patient
    if (patient == null) {
      System.out.println("No existing patient found with the given name or ID.");
      return; // Exit if the patient is not found
    }

    // Display the patient's current information to confirm correct patient selection
/*    System.out.println("Selected Patient: "
            + patient.getFullName()
            + " (ID: " + patient.getPatientId()
            + ")");*/

    StringBuilder sb = new StringBuilder();
    sb.append("Selected Patient: ")
            .append(patient.getFullName())
            .append(" (ID: ")
            .append(patient.getPatientId())
            .append(")");

    System.out.println(sb.toString());

    // Collect details for the new visit
    System.out.print("Enter the chief complaint: ");
    String chiefComplaint = scanner.nextLine();

    // Validate Body Temperature
    Double bodyTemperature = null;
    while (bodyTemperature == null) {
      System.out.print("Enter body temperature (in Celsius): ");
      try {
        bodyTemperature = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
      } catch (InputMismatchException e) {
        System.out.println("Invalid temperature. Please enter a valid number.");
        scanner.nextLine(); // Consume invalid input
      }
    }

    // Use the unified registerPatientAndVisit method to
    // add a visit for the existing patient
    clinic.registerExistingPatientVisit(
            patient.getFirstName(),
            patient.getLastName(),
            patient.getDateOfBirth(),
            LocalDateTime.now(),
            chiefComplaint,
            bodyTemperature
    );

    // Confirm that the visit record has been added
    System.out.printf("New visit record added for existing patient: %s (ID: %s)%n",
            patient.getFullName(), patient.getPatientId());
  }

  /**
   * Helper method to validate the Date of Birth (DOB) entered by the user.
   * The method prompts the user to enter the DOB in the format "MM/dd/yyyy" and validates it.
   * If an invalid date format is entered, the user is prompted to re-enter the date.
   *
   * @param scanner The {@link Scanner} object used to read user input.
   * @return The validated {@link LocalDate} representing the patient's DOB.
   */
  private static LocalDate validateDateOfBirth(Scanner scanner) {
    LocalDate dob = null;
    DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    while (dob == null) {
      System.out.print("Date of Birth (e.g., 01/01/1990): ");
      String dobInput = scanner.nextLine();
      try {
        dob = LocalDate.parse(dobInput, dobFormatter);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please enter in MM/dd/yyyy format.");
      }
    }
    return dob;
  }

  /**
   * Helper method to validate the body temperature entered by the user.
   * The method prompts the user to enter the body temperature in Celsius.
   * If an invalid number is entered, the user is prompted to re-enter the temperature.
   *
   * @param scanner The {@link Scanner} object used to read user input.
   * @return The validated body temperature as a {@code double}.
   */
  private static double validateBodyTemperature(Scanner scanner) {
    Double temperature = null;
    while (temperature == null) {
      System.out.print("Body Temperature (in Celsius): ");
      try {
        temperature = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
      } catch (InputMismatchException e) {
        System.out.println("Invalid temperature. Please enter a valid number.");
        scanner.nextLine();
      }
    }
    return temperature;
  }


  /**
   * Registers a clinical staff member in the clinic.
   *
   * @param clinic  The clinic where the staff member is being registered.
   * @param scanner A {@code Scanner} object to read user input.
   */
  private static void registerClinicalStaff(Clinic clinic, Scanner scanner) {
    System.out.print("First Name: ");
    final String firstName = scanner.nextLine();
    System.out.print("Last Name: ");
    final String lastName = scanner.nextLine();
    System.out.print("Job Title (Physician/Nurse): ");
    final String jobTitleStr = scanner.nextLine();
    System.out.print("Education Level: ");
    final String educationLevelStr = scanner.nextLine();
    System.out.print("NPI: ");
    final String identifier = scanner.nextLine();
    System.out.print("Dr. (physician) or Nurse: ");
    final String title = scanner.nextLine();

    // Convert the job title
    // and education level strings to the appropriate enums
    JobTitle jobTitle;
    EducationLevel educationLevel;

    try {
      jobTitle = JobTitle.valueOf(jobTitleStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid job title. "
              + "Only 'Physician' or 'Nurse' are allowed.");
      return;  // Exit the method if the input is invalid
    }

    try {
      educationLevel = EducationLevel.valueOf(educationLevelStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid education level. "
              + "Only 'Doctoral', 'Masters', or 'Allied' are allowed.");
      return;  // Exit the method if the input is invalid
    }

    // Create a new ClinicalStaff object and register it in the clinic
    clinic.registerClinicalStaff(new ClinicalStaff(firstName,
            lastName,
            jobTitle,
            educationLevel,
            identifier));
    System.out.println("Clinical staff registered.");
  }

  /**
   * Assigns a patient to a room in the clinic.
   *
   * @param clinic  The clinic where the patient is being assigned.
   * @param scanner A {@code Scanner} object to read user input.
   */

  private static void assignPatientToRoom(Clinic clinic, Scanner scanner) {
    System.out.print("Enter room number for patient assignment: ");
    final int roomNumber = scanner.nextInt();
    System.out.print("Enter the patient ID for assignment: ");
    int patientId = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    // Find patient by ID
    PatientInterface patient = clinic.getPatients().get(patientId);

    // Check if the patient is discharged
    if (patient.getVisitStatus() == VisitStatus.DISCHARGED) {
      System.out.println("Error: Cannot assign a room to a discharged patient.");
      return;
    }

    // Assign the room to the patient
    clinic.assignRoom(roomNumber, patientId);
  }

  /**
   * Assigns a clinical staff member to a patient in the clinic.
   *
   * This method allows the user to assign a clinical staff member
   * (such as a physician or nurse) to a specific patient.
   * The patient is first identified by either name or ID,
   * and the clinical staff is identified by either name or their NPI.
   * If the patient has been discharged, or if the staff member is not clinical,
   * the assignment process is halted with an appropriate error message.
   *
   * @param clinic  The clinic instance where the patient
   *                and staff data are managed.
   * @param scanner A {@code Scanner} object to read user input.
   */
  private static void assignSingleClinicalStaffToPatient(Clinic clinic, Scanner scanner) {
    System.out.print("Enter patient name or ID: ");
    String patientSearchTerm = scanner.nextLine();

    // Use the searchPatient method to find the patient in the clinic
    PatientInterface patient = clinic.searchPatient(patientSearchTerm);

    // Exit if no patient is found based on the search term
    if (patient == null) {
      System.out.println("Error: No patient found.");
      return;
    }

    // Check if the patient is already discharged
    if (patient.getVisitStatus() == VisitStatus.DISCHARGED) {
      System.out.println("Error: Cannot assign staff to a discharged patient.");
      return;
    }

    System.out.print("Enter clinical staff name or identifier: ");
    String staffSearchTerm = scanner.nextLine();

    // Use the searchStaff method to find the clinical staff by full name or identifier
    List<Staff> matchedStaff = clinic.searchStaff(staffSearchTerm);

    // Validate if clinical staff found and only one match exists
    if (matchedStaff.isEmpty()) {
      System.out.println("Error: "
              + "No clinical staff found with the given name or identifier.");
      return;
    } else if (matchedStaff.size() > 1) {
      System.out.println("Error: Multiple staff members found. "
              + "Please provide a more specific identifier.");
      return;
    }

    // Retrieve the first matched staff member
    Staff staff = matchedStaff.get(0);

    // Check if the staff found is a clinical staff member
    if (staff instanceof ClinicalStaff) {
      // Ensure that the staff is not already assigned to the patient to avoid duplication
      if (!clinic.getAssignedClinicalStaffForPatient(patient).contains(staff)) {
        clinic.assignClinicalStaffToPatient(patient, (ClinicalStaff) staff);
        System.out.printf("Assigned %s %s to patient %s%n",
                ((ClinicalStaff) staff).getTitlePrefix(),
                staff.getFullName(), patient.getFullName());
      } else {
        System.out.println("Error: This staff member is already assigned to the patient.");
      }
    } else {
      // Print error if the staff is not clinical
      System.out.println("Error: Only clinical staff can be assigned to patients.");
    }
  }

  /**
   * Assigns multiple clinical staff members to a patient.
   * This method prompts the user to
   * enter the number of clinical staff to assign
   * and then validates the input to ensure it does not exceed
   * the total number of available clinical staff.
   * It also ensures that the user
   * provides valid clinical staff names or identifiers.
   * If invalid input is provided, the user is prompted to try again.
   *
   * @param clinic  The {@link Clinic} instance where the staff assignment is being made.
   * @param scanner A {@code Scanner} object to read user input.
   */
  private static void assignMultipleClinicalStaffToPatient(Clinic clinic, Scanner scanner) {
    System.out.print("Enter patient name or ID: ");
    String patientSearchTerm = scanner.nextLine();

    // Use the searchPatient method to find the patient
    PatientInterface patient = clinic.searchPatient(patientSearchTerm);

    if (patient == null) {
      System.out.println("Error: No patient found.");
      return; // Exit if no patient found
    }

    // Get the total number of available clinical staff
    // Use Collectors.toList() instead of toList() (available in Java 16+)
    List<Staff> availableClinicalStaff = clinic.getStaff().stream()
            .filter(s -> s instanceof ClinicalStaff)
            .collect(Collectors.toList());

    long totalClinicalStaff = availableClinicalStaff.size();

    // Get the number of staff already assigned to the patient
    long alreadyAssignedStaff = clinic.getAssignedClinicalStaffForPatient(patient).size();

    // Determine how many more clinical staff can be assigned
    long maxAssignableStaff = totalClinicalStaff - alreadyAssignedStaff;

    if (maxAssignableStaff <= 0) {
      System.out.println("Error: "
              + "All available clinical staff are already assigned to this patient.");
      return;
    }

    // Ask for the number of clinical staff to assign
    int numStaff = -1; // Initialize to an invalid value
    while (numStaff <= 0 || numStaff > maxAssignableStaff) {
      System.out.printf("Enter the number of clinical staff to assign (Max %d): ",
              maxAssignableStaff);
      try {
        numStaff = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (numStaff <= 0) {
          System.out.println("Error: Number of staff must be a positive number.");
        } else if (numStaff > maxAssignableStaff) {
          System.out.println("Error: "
                  + "The number exceeds available clinical staff "
                  + "that can be assigned. Try again.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a valid number.");
        scanner.nextLine(); // Consume invalid input
      }
    }

    // Loop to assign clinical staff to the patient
    for (int i = 0; i < numStaff; i++) {
      boolean staffAssigned = false;
      while (!staffAssigned) {
        System.out.print("Enter clinical staff name or identifier: ");
        String staffSearchTerm = scanner.nextLine();

        // Use the searchStaff method to find the clinical staff by name or identifier
        List<Staff> matchedStaff = clinic.searchStaff(staffSearchTerm);

        // Validate if clinical staff found and only one match exists
        if (matchedStaff.isEmpty()) {
          System.out.println("Error: "
                  + "No clinical staff found with the given name or identifier.");
        } else if (matchedStaff.size() > 1) {
          System.out.println("Error: "
                  + "Multiple staff members found. "
                  + "Please provide a more specific identifier.");
        } else {
          // Retrieve the matched staff member
          Staff staff = matchedStaff.get(0);

          // Check if the found staff is clinical and not already assigned
          if (staff instanceof ClinicalStaff) {
            if (!clinic.getAssignedClinicalStaffForPatient(patient).contains(staff)) {
              clinic.assignClinicalStaffToPatient(patient, (ClinicalStaff) staff);
              System.out.printf("Assigned %s %s to patient %s%n",
                      ((ClinicalStaff) staff).getTitlePrefix(),
                      staff.getFullName(), patient.getFullName());
              staffAssigned = true; // Exit loop once staff is successfully assigned
            } else {
              System.out.println("Error: "
                      + "This staff member is already assigned to the patient.");
            }
          } else {
            System.out.println("Error: Only clinical staff can be assigned to patients.");
          }
        }
      }
    }
  }

  /**
   * Displays details of all patients in the clinic.
   *
   * @param clinic The clinic where patients are registered.
   */
  private static void displayAllPatients(Clinic clinic) {
    List<PatientInterface> patients = clinic.getPatients();

    if (patients.isEmpty()) {
      System.out.println("No patients registered in the clinic.");
      return;
    }

    System.out.println("\nAll Patients in the Clinic:");

    for (PatientInterface patient : patients) {
      if (!(patient.getVisitStatus() == VisitStatus.DISCHARGED)) { // Skip discharged patients
        System.out.println("--------------------------------------------");
        // Reuse method to display individual patient details
        displayPatientDetails(patient, clinic);
        System.out.println("--------------------------------------------\n");
      }
    }
  }

  /**
   * Displays information about a specific patient.
   *
   * @param clinic  The clinic where the patient is registered.
   * @param scanner A {@code Scanner} object to read user input.
   */
  private static void displayPatientInfo(Clinic clinic, Scanner scanner) {
    System.out.print("Enter patient name or ID: ");
    String searchTerm = scanner.nextLine();

    // Use the helper method to validate and find the patient
    PatientInterface patient = clinic.searchPatient(searchTerm);

    if (patient != null) {
      displayPatientDetails(patient, clinic); // Pass the clinic to get staff assignments
    }
  }

  /**
   * Displays information about a specific room.
   *
   * @param clinic  The clinic where the room is located.
   * @param scanner A {@code Scanner} object to read user input.
   */
  private static void displayRoomInfo(Clinic clinic, Scanner scanner) {
    System.out.print("Enter room number: ");
    int roomNumber = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    // Validate room number
    if (roomNumber <= 0 || roomNumber > clinic.getRooms().size()) {
      System.out.println("Invalid room number.");
      return;
    }

    RoomInterface room = clinic.getRooms().get(roomNumber - 1);
    System.out.println("Room Information:");
    System.out.println("\tRoom Number: "
            + roomNumber
            + ", "
            + room.getRoomType()
            + " "
            + room.getRoomName()
            + " "
            + room.getRoomCoordinates());

    // Get patients assigned to the room
    List<? extends PatientInterface> assignedPatients
            = clinic.getRoomAssignments().get(roomNumber);
    if (assignedPatients == null || assignedPatients.isEmpty()) {
      System.out.println("No patients assigned to this room.");
    } else {
      System.out.println("Assigned Patients:");
      for (PatientInterface patient : assignedPatients) {
        System.out.println(String.format("\tPatient: %s, ID: %s, DOB: %s\n\t\t "
                        + "Visit Record: %s", patient.getFullName(),
                patient.getPatientId(), patient.getDateOfBirth(), patient.getVisitRecords()));

        // Display the clinical staff assigned to the patient
        List<ClinicalStaff> assignedStaff
                = clinic.getAssignedClinicalStaffForPatient(patient);
        if (assignedStaff.isEmpty()) {
          System.out.println("\t\tAssigned Staff: No staff assigned to this patient.");
        } else {
          System.out.println("\tAssigned Staff:");
          for (ClinicalStaff staff : assignedStaff) {
            System.out.printf("\t\tStaff: %s %s, Job Title: %s, Education Level: %s%n",
                    staff.getTitlePrefix(), staff.getFullName(),
                    staff.getJobTitle(), staff.getEducationLevel());
          }
        }
      }
    }
  }

  /**
   * Lists all staff members (both clinical and non-clinical) in the clinic.
   * Clinical staff members are displayed with their title
   * (e.g., "Dr." for Physicians or "Nurse" for nurses),
   * and non-clinical staff members are displayed with "Reception" as their title.
   *
   * @param clinic The {@link Clinic} instance containing the list of staff members.
   */
  private static void listAllStaff(Clinic clinic) {
    List<Staff> staffList = clinic.getStaff();
    System.out.println("\nAll Staff Members:");

    for (Staff staff : staffList) {
      if (staff instanceof ClinicalStaff) {
        ClinicalStaff clinicalStaff = (ClinicalStaff) staff;
        // Use the getTitle() method to determine the title (Dr. or Nurse)
        String clinicalTitlePrefix = clinicalStaff.getTitlePrefix();
        // Display information for clinical staff
        System.out.println(String.format("%s %s - job title: %s - education level: %s - NPI: %s",
                clinicalTitlePrefix, clinicalStaff.getFullName(),
                clinicalStaff.getJobTitle(), clinicalStaff.getEducationLevel(),
                clinicalStaff.getNpi()));
      } else if (staff instanceof NonClinicalStaff) {
        NonClinicalStaff nonClinicalStaff = (NonClinicalStaff) staff;
        String nonClinicalTitlePrefix = nonClinicalStaff.getTitlePrefix();
        // Display information for non-clinical staff
        System.out.printf("%s %s - job title: %s - education level: %s - CPR Level: %s%n",
                nonClinicalTitlePrefix, nonClinicalStaff.getFullName(),
                nonClinicalStaff.getJobTitle(), nonClinicalStaff.getEducationLevel(),
                nonClinicalStaff.getCprLevel());
      } else {
        // Handle any unknown staff types
        System.out.printf("Unknown staff type: %s%n", staff.getFullName());
      }
    }
  }

  /**
   * Lists all clinical staff members in the clinic. Clinical staff members are displayed
   * with their title (e.g., "Dr." for Physicians or "Nurse" for nurses) and their NPI.
   *
   * @param clinic The {@link Clinic} instance containing the list of staff members.
   */
  private static void listClinicalStaffOnly(Clinic clinic) {
    List<Staff> staffList = clinic.getStaff();
    System.out.println("\nClinical Staff Members:");

    boolean hasClinicalStaff = false;  // Track if any clinical staff is found

    for (Staff staff : staffList) {
      if (staff instanceof ClinicalStaff) {
        ClinicalStaff clinicalStaff = (ClinicalStaff) staff;
        // Use the getTitle() method to determine the title (Dr. or Nurse)
        String title = clinicalStaff.getTitlePrefix();
        // Display information for clinical staff
        System.out.printf("%s %s - job title: %s - education level: %s - identifier (NPI): %s%n",
                title, clinicalStaff.getFullName(), clinicalStaff.getJobTitle(),
                clinicalStaff.getEducationLevel(), clinicalStaff.getNpi());
        hasClinicalStaff = true;  // At least one clinical staff member found
      }
    }

    if (!hasClinicalStaff) {
      System.out.println("No clinical staff members found.");
    }
  }

  /**
   * Lists all non-clinical staff members in the clinic. Non-clinical staff members are displayed
   * with the title "Reception" and their CPR level.
   *
   * @param clinic The {@link Clinic} instance containing the list of staff members.
   */
  private static void listNonClinicalStaffOnly(Clinic clinic) {
    List<Staff> staffList = clinic.getStaff();
    System.out.println("\nNon-Clinical Staff Members:");
    for (Staff staff : staffList) {
      if (staff instanceof NonClinicalStaff) {
        NonClinicalStaff nonClinicalStaff = (NonClinicalStaff) staff;
        System.out.println(String.format("Reception %s - job title: %s "
                        + "- education level: %s - identifier (CPR level): %s",
                nonClinicalStaff.getFullName(), nonClinicalStaff.getJobTitle(),
                nonClinicalStaff.getEducationLevel(), nonClinicalStaff.getCprLevel()));
      }
    }
  }

  /**
   * Searches for a staff member in the clinic
   * by either their full name or identifier (NPI for clinical staff
   * or CPR level for non-clinical staff).
   * If a match is found, the staff member's details are printed.
   * If multiple matches are found
   * (e.g., multiple non-clinical staff members with the same CPR level),
   * a message is displayed indicating the number of matches,
   * and the details of all matching staff members are printed.
   * If no matches are found, a message indicating no staff found is displayed.
   *
   * <p>
   * This method ensures that in the case
   * where multiple staff members share the same identifier
   * (e.g., non-clinical staff with the same CPR level),
   * all matching staff members are displayed,
   * preventing the method from stopping at the first match.
   * Additionally, the search is case-insensitive
   * for both names and identifiers, allowing flexible user input.
   * </p>
   *
   * @param clinic  The {@link Clinic} instance containing the list of staff members.
   * @param scanner The {@link Scanner} instance used to get input from the user for searching.
   */
  private static void searchStaff(Clinic clinic, Scanner scanner) {
    System.out.print("Enter staff full name or identifier: ");
    String searchTerm = scanner.nextLine();

    // Call the searchStaff method in ClinicImplementation to get the matching staff
    List<Staff> matchedStaff = clinic.searchStaff(searchTerm);

    if (matchedStaff.isEmpty()) {
      System.out.println("No staff found with the given name or identifier.");
    } else if (matchedStaff.size() == 1) {
      // Display details for a single match
      Staff staff = matchedStaff.get(0);
      displayStaffDetails(staff);
    } else {
      // Display details for multiple matches
      System.out.printf("Found %d staff with the provided identifier or name:%n",
              matchedStaff.size());
      for (Staff staff : matchedStaff) {
        displayStaffDetails(staff);
      }
    }
  }

  /**
   * Helper Method to display the details of a staff member.
   *
   * @param staff The {@link Staff} object whose details will be displayed.
   */
  private static void displayStaffDetails(Staff staff) {
    String titlePrefix = (staff instanceof ClinicalStaff)
            ? ((ClinicalStaff) staff).getTitlePrefix()
            : "Reception";

    System.out.printf("%s %s - job title: %s - staff role: %s "
                    + "- education level: %s - identifier: %s%n",
            titlePrefix, staff.getFullName(), staff.getJobTitle(),
            staff.getStaffRole(), staff.getEducationLevel(),
            staff instanceof ClinicalStaff ? ((ClinicalStaff) staff)
                    .getNpi() : ((NonClinicalStaff) staff).getCprLevel());
  }

  /**
   * Sends a patient home with physician approval.
   * The patient is marked as discharged,
   * and their record remains in the system with their original ID.
   *
   * @param clinic  The clinic from which the patient is being discharged.
   * @param scanner A {@code Scanner} object to read user input.
   */
  private static void sendPatientHome(Clinic clinic, Scanner scanner) {
    System.out.print("Enter patient ID or full name: ");
    String searchTerm = scanner.nextLine();

    // Use the searchPatient method to find the patient
    PatientInterface patient = clinic.searchPatient(searchTerm);
    if (patient == null) {
      System.out.println("Error: No patient found with the provided name or ID.");
      return;
    }

    // Check if the patient is already discharged
    if (patient.getVisitStatus() == VisitStatus.DISCHARGED) {
      System.out.println("Error: This patient has already been discharged.");
      return;
    }

    // Input and validate approver's name (Dr. only)
    System.out.print("Enter the name of Dr. (physician) for approval: ");
    String approverName = scanner.nextLine();

    // Search for the approver (must be a physician)
    ClinicalStaff approver = (ClinicalStaff) clinic.getStaff().stream()
            .filter(staff -> staff instanceof ClinicalStaff
                    && staff.getFullName().equalsIgnoreCase(approverName)
                    && ((ClinicalStaff) staff).getJobTitle() == JobTitle.PHYSICIAN)
            .map(staff -> (ClinicalStaff) staff)
            .findFirst()
            .orElse(null);

    if (approver == null) {
      System.out.println("Error: Only physicians (Dr.) can approve a discharge.");
      return;
    }

    // Mark patient as discharged
    patient.setVisitStatus(VisitStatus.DISCHARGED);

    // Remove patient from their assigned room
    clinic.removePatientFromRoom(patient);

    // Confirm that the patient is discharged and still in the system
    System.out.printf("%s has been sent home and marked as discharged, "
                    + "with approval from Dr. %s.%n",
            patient.getFullName(), approver.getFullName());
  }



  /**
   * Finds a patient in the clinic by their full name.
   * The search is case-insensitive.
   * If a matching patient is found, the patient is returned.
   * Otherwise, {@code null} is returned.
   *
   * @param clinic   The {@link Clinic} instance containing the list of patients.
   * @param fullName The full name of the patient to search for.
   * @return The {@link PatientInterface} object representing the patient,
    or {@code null} if no patient is found.
   */
  private static PatientInterface findPatientByName(Clinic clinic, String fullName) {
    for (PatientInterface patient : clinic.getPatients()) {
      if (patient.getFullName().equalsIgnoreCase(fullName)) {
        return patient;
      }
    }
    return null;  // No patient found with this name
  }

  /**
   * Finds a patient in the clinic by their patient ID.
   * If a valid patient ID is provided,
   * the corresponding patient is returned.
   * If the ID is out of bounds, {@code null} is returned.
   *
   * @param clinic    The {@link Clinic} instance containing the list of patients.
   * @param patientId The ID of the patient to search for.
   * @return The {@link PatientInterface} object representing the patient,
    or {@code null} if no patient is found.
   */
  private static PatientInterface findPatientById(Clinic clinic, int patientId) {
    List<PatientInterface> patients = clinic.getPatients();
    if (patientId >= 0 && patientId < patients.size()) {
      return patients.get(patientId);
    }
    return null;  // No patient found with this ID
  }


  /**
   * Helper Method to display the detailed information of the specified patient.
   * This includes the patient's full name, ID, current room assignment, and any other
   * relevant details based on the clinic's patient tracking system.
   *
   * @param patient The {@link PatientInterface} object representing the patient
   *                whose details are to be displayed.
   */
  private static void displayPatientDetails(PatientInterface patient, Clinic clinic) {
    StringBuilder patientDetails = new StringBuilder();
    patientDetails.append("Patient Details:\n")
            .append(String.format("\tFull Name: %s%n", patient.getFullName()))
            .append(String.format("\tPatient ID: %s%n", patient.getPatientId()))
            .append(String.format("\tDate of Birth: %s%n", patient.getDateOfBirth()))
            .append(String.format("\tVisit Records: %s%n", patient.getVisitRecords()));

    System.out.println(patientDetails.toString());

    // Display discharge status
    System.out.printf("\tDischarged: %s%n",
            patient.getVisitStatus() == VisitStatus.DISCHARGED ? "Yes" : "No");

    // Info of room assigned to patient if exist
    RoomInterface assignedRoom = patient.getAssignedRoom();
    if (assignedRoom != null) {
      StringBuilder assignedRoomInfo = new StringBuilder();
      assignedRoomInfo.append("Assigned Room:\n")
              .append(String.format("\tRoom Number: %s%n", assignedRoom.getRoomNumber()))
              .append(String.format("\tRoom Type: %s%n", assignedRoom.getRoomType()))
              .append(String.format("\tRoom Name: %s%n", assignedRoom.getRoomName()));

      System.out.println(assignedRoomInfo.toString());
    } else {
      System.out.println("Assigned Room: None");
    }

    // Display assigned clinical staff
    List<ClinicalStaff> assignedStaff
            = clinic.getAssignedClinicalStaffForPatient(patient);
    if (assignedStaff.isEmpty()) {
      System.out.println("Assigned Staff: None");
    } else {
      System.out.println("Assigned Staff:");
      for (Staff staff : assignedStaff) {
        String title = (staff instanceof ClinicalStaff)
                ? (((ClinicalStaff) staff)
                .getJobTitle() == JobTitle.PHYSICIAN ? "Dr." : "Nurse")
                : "Non-clinical Staff";
        System.out.printf("\t%s %s%n", title, staff.getFullName());
      }
    }
  }
}