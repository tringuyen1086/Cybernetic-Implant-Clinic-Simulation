### About/Overview

This project is a simulation model of a Cybernetic Implant Clinic. 
The clinic includes various rooms such as waiting rooms, exam rooms, and procedure rooms. 
The system models interactions between clinic staff (both clinical and non-clinical) and patients, 
providing functionality to manage room assignments, staff-to-patient assignments, 
and patient movements in and out of the clinic. 
The clinic data is initialized through a text file, allowing for flexible configuration of the clinic’s setup.

The project uses Java to simulate a clinic's functionality, 
following good object-oriented design principles, and is tested using JUnit.



### List of Features

* [x] Load Clinic Data: Reads clinic data from a structured text file (clinicfile.txt)
which includes room configurations, patient details, and staff assignments.
* [x] Register Patients: Adds a new patient to the clinic and places them in a waiting room by default. 
* [x] Register Staff: Adds a new clinical or non-clinical staff member to the clinic, 
assigning them a specific role and title. 
* [x] Assign Patients to Rooms: Allows for moving patients between rooms. 
While multiple patients can occupy a waiting room, only one patient can occupy exam and procedure rooms. 
* [x] Assign Staff to Patients: Supports assigning one or more staff members 
(e.g., doctors, nurses) to individual patients for care. 
* [x] Display Clinic Overview: Shows a seating chart that includes the current room 
assignments for patients and the staff responsible for them. 
* [x] Send Patients Home: Moves patients out of the clinic system and removes them from room and staff assignments. 
* [x] Deactivate Staff: Deactivates staff members, making them unavailable for future patient care assignments.



### How to Run

A. Download the milestone-2-tringuyen1086.jar File [here](https://github.com/tringuyen1086/Cybernetic-Implant-Clinic-Simulation/blob/main/res/milestone-2-tringuyen1086.jar)
- Per Professor's instruction, the .jar file is save in the res directory.
- Click on the .jar file to download it to your local machine.

B. Ensure Java is Installed:
- Before running the .jar file, make sure that you have Java installed on your machine.
* [x] Windows: 
* Open Command Prompt (cmd) and type the following command: ```java -version```
* If Java is installed, it will display the Java version. If not, you need to install it from the 
* [offical Java download page here](https://www.oracle.com/java/technologies/downloads/#java11)
* [x] macOS / Linux: 
* Open the terminal and type the following command: ```java -version```
* If Java is not installed, install it using:
* For macOS: Use ```brew``` (if installed)
```
brew install --cask temurin
* ```
* For Linux: Use the package manager for your distribution
```
sudo apt install default-jre  # Ubuntu/Debian
sudo yum install java-11-openjdk  # CentOS/RedHat

```
C. Run the .jar File:
Once you have Java installed, follow these steps:

* [x] Windows:

1. Open the folder where the .jar file was downloaded.
2. Hold down the Shift key, then right-click inside the folder.
3. Select Open PowerShell window here or Open Command window here (depending on your version of Windows).
4. Type the following command:
```
java -jar milestone-2-tringuyen1086.jar <input-source>
```
Note: the command line ``` java -jar your-file-name.jar``` is use to open the .jar file.
Make sure to replace ```your-file-name.jar``` with the actual name of the ```.jar``` file 

* [x] macOS / Linux:

1. Open the terminal.
2. Navigate to the directory where the .jar file is located. For example
```
cd ~/Downloads
```
- Run the following command.
```
java -jar your-file-name.jar [filePath or "URL"]
```
- Note: Replace [filePath or "URL"] with one of these options
  - local path with or without " " (e.g: C:\Users\yourName\Downloads\clinicfile.txt or "C:\Users\yourName\Downloads\clinicfile.txt")
  - URL with " " (e.g: "https://drive.google.com/uc?export=download&id=19o_vIAUEXyqZr951AIK7jECKzwHCQdzq")

- To run milestone-2-tringuyen1086.jar:
```java -jar milestone-2-tringuyen1086.jar <input-source>```
- Replace <input-source> with path from any input source
- Gitbash: Assume that you save the input file in C:\Users\yourName\Downloads\clinicfile.txt
- ```java -jar milestone-2-tringuyen1086.jar /c/Users/yourName/Downloads/clinicfile.txt```
- Terminal within Intellij: Assume that you save the input file C:\Users\yourName\Downloads\clinicfile.txt
- ```java -jar milestone-2-tringuyen1086.jar C:\Users\yourName\Downloads\clinicfile.txt```
- Alternative:
- ```java -jar milestone-2-tringuyen1086.jar "C:\Users\yourName\Downloads\clinicfile.txt"```
- Input source as URL:
- ```java -jar milestone-2-tringuyen1086.jar "URL"```
- Example of running jar file from URL file (e.g. https://drive.google.com/uc?export=download&id=19o_vIAUEXyqZr951AIK7jECKzwHCQdzq)
- ```java -jar milestone-2-tringuyen1086.jar "https://drive.google.com/uc?export=download&id=19o_vIAUEXyqZr951AIK7jECKzwHCQdzq"```

D. Note: For those who are Intellij users, .jar file can be created by the following steps:

```
File - Project Structure - Project Setting - Artifacts + Click (plus sign) - Jar - From modules with dependencies
 - Select a Main Class (the one with main() method) if you need to make the jar runnable.
 - Select Extract to the target Jar 
 - Click `OK`
 - Build - Build Artifact
 ```

* [x] source: https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-idea-properly

### How to Use the Program

1. Load Clinic Data:
- Upon execution, the program will automatically load the clinic configuration from the provided file 
and display the initial seating chart.

2. Register New Patients or Staff:
- The program demonstrates how new patients and staff are registered, with output reflecting updated seating charts 
and staff listings.

3. Manual Adjustments:
- Modify the text file (clinicfile.txt) to simulate different clinic setups and rerun the program for varying outputs.

### Example Runs

```
```
java -jar milestone-2-tringuyen1086.jar C:\Users\tring\Downloads\clinicfile2.txt     
Clinic data loaded successfully from: C:\Users\tring\Downloads\clinicfile2.txt       

Choose an option:
1. Register a new patient and add a visit record
2. Register an existing patient and enter a visit record
3. Register a clinical staff member
4. Assign a patient to a room
5. Assign a clinical staff member to a patient
6. Assign multiple clinical staff to a patient
7. Display information of all patients
8. Display information of a specific patient
9. Display information about a specific room
10. Display all rooms (Seating Chart)
11. Display all staff (Clinical and Nonclinical)
12. Display Clinical Staff Only
13. Display Non-Clinical Staff Only
14. Search staff by full name or identifier
15. Send a patient home (approved by physician)
16. Exit
Enter your choice: 10
```
```
========== Cybernetic Implant Clinic Testing SEATING CHART ==========

Room 1: WAITING Front Waiting Room [28,0 to 35,5]
------------------------------------------------------
Patient:
        Aandi Acute (Patient ID: 0 , DOB: 1/1/1981)
        No visit records found.
        No staff assigned to this patient.
Patient:
        Doug Derm (Patient ID: 3 , DOB: 4/4/1984)
        No visit records found.
        No staff assigned to this patient.
Patient:
        Greg Gastric (Patient ID: 6 , DOB: 7/7/1987)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 2: EXAM Triage [30,6 to 35,11]
------------------------------------------------------
Patient:
        Beth Bunion (Patient ID: 1 , DOB: 2/2/1982)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 3: WAITING Inside Waiting Room [28,12 to 35,19]
------------------------------------------------------
Patient:
        Clive Cardiac (Patient ID: 2 , DOB: 3/3/1983)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 4: EXAM Exam_1 [30,20 to 35,25]
------------------------------------------------------
Patient:
        Elise Enzyme (Patient ID: 4 , DOB: 5/5/1985)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 5: PROCEDURE Surgical [26,13 to 27,18]
------------------------------------------------------
Patient:
        Fatima Follicle (Patient ID: 5 , DOB: 6/6/1986)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

========== END OF SEATING CHART ==========
```
```
Choose an option:
1. Register a new patient and add a visit record
2. Register an existing patient and enter a visit record
3. Register a clinical staff member
4. Assign a patient to a room
5. Assign a clinical staff member to a patient
6. Assign multiple clinical staff to a patient
7. Display information of all patients
8. Display information of a specific patient
9. Display information about a specific room
10. Display all rooms (Seating Chart)
11. Display all staff (Clinical and Nonclinical)
12. Display Clinical Staff Only
13. Display Non-Clinical Staff Only
14. Search staff by full name or identifier
15. Send a patient home (approved by physician)
16. Exit
Enter your choice: 2
Enter patient name or ID: 0
Selected Patient: Aandi Acute (ID: 0)
Enter the chief complaint: check up
Enter body temperature (in Celsius): 36.2
Added a new visit record for the existing patient: Aandi Acute
New visit record added for existing patient: Aandi Acute (ID: 0)

Choose an option:
1. Register a new patient and add a visit record
2. Register an existing patient and enter a visit record
3. Register a clinical staff member
4. Assign a patient to a room
5. Assign a clinical staff member to a patient
6. Assign multiple clinical staff to a patient
7. Display information of all patients
8. Display information of a specific patient
9. Display information about a specific room
10. Display all rooms (Seating Chart)
11. Display all staff (Clinical and Nonclinical)
12. Display Clinical Staff Only
13. Display Non-Clinical Staff Only
14. Search staff by full name or identifier
15. Send a patient home (approved by physician)
16. Exit
Enter your choice: 5
Enter patient name or ID: 0
Enter clinical staff name or identifier: amy anguish
Assigned Dr. Amy Anguish to patient Aandi Acute

Choose an option:
1. Register a new patient and add a visit record
2. Register an existing patient and enter a visit record
3. Register a clinical staff member
4. Assign a patient to a room
5. Assign a clinical staff member to a patient
6. Assign multiple clinical staff to a patient
7. Display information of all patients
8. Display information of a specific patient
9. Display information about a specific room
10. Display all rooms (Seating Chart)
11. Display all staff (Clinical and Nonclinical)
12. Display Clinical Staff Only
13. Display Non-Clinical Staff Only
14. Search staff by full name or identifier
15. Send a patient home (approved by physician)
16. Exit
Enter your choice: 10
```
```
========== Cybernetic Implant Clinic Testing SEATING CHART ==========

Room 1: WAITING Front Waiting Room [28,0 to 35,5]
------------------------------------------------------
Patient:
        Aandi Acute (Patient ID: 0 , DOB: 1/1/1981)
        Visit Records:
                Registration: 2024-10-23 15:47:49, Chief Complaint: check up, Body Temperature: 36.2 °C
        Assigned Staff:
                Dr. Amy Anguish (NPI: 1234567890)
Patient:
        Doug Derm (Patient ID: 3 , DOB: 4/4/1984)
        No visit records found.
        No staff assigned to this patient.
Patient:
        Greg Gastric (Patient ID: 6 , DOB: 7/7/1987)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 2: EXAM Triage [30,6 to 35,11]
------------------------------------------------------
Patient:
        Beth Bunion (Patient ID: 1 , DOB: 2/2/1982)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 3: WAITING Inside Waiting Room [28,12 to 35,19]
------------------------------------------------------
Patient:
        Clive Cardiac (Patient ID: 2 , DOB: 3/3/1983)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 4: EXAM Exam_1 [30,20 to 35,25]
------------------------------------------------------
Patient:
        Elise Enzyme (Patient ID: 4 , DOB: 5/5/1985)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 5: PROCEDURE Surgical [26,13 to 27,18]
------------------------------------------------------
Patient:
        Fatima Follicle (Patient ID: 5 , DOB: 6/6/1986)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

========== END OF SEATING CHART ==========
```
```
Choose an option:
1. Register a new patient and add a visit record
2. Register an existing patient and enter a visit record
3. Register a clinical staff member
4. Assign a patient to a room
5. Assign a clinical staff member to a patient
6. Assign multiple clinical staff to a patient
7. Display information of all patients
8. Display information of a specific patient
9. Display information about a specific room
10. Display all rooms (Seating Chart)
11. Display all staff (Clinical and Nonclinical)
12. Display Clinical Staff Only
13. Display Non-Clinical Staff Only
14. Search staff by full name or identifier
15. Send a patient home (approved by physician)
16. Exit
Enter your choice:
```

In the above example:

- This example demonstrates how to run the Cybernetic Implant Clinic system
  using the milestone-2-tringuyen1086.jar file
  and load clinic data from a text file.
  The system offers various functionalities that
  allow users to manage patients, clinical staff, rooms, and visit records.
- Steps to Run the Program:
    - Command to Start the Program:
      ```java -jar milestone-2-tringuyen1086.jar C:\Users\tring\Downloads\clinicfile2.txt```
    - This command uses the java -jar command to
      run the milestone-2-tringuyen1086.jar executable file,
      which contains the compiled project.
      The path to the clinic data file (C:\Users\tring\Downloads\clinicfile2.txt)
      is provided as an argument.
      The program will load the clinic data from this file
      and display a message confirming the successful load:
        - Clinic data loaded successfully from: C:\Users\tring\Downloads\clinicfile2.txt
    - Main Menu: After loading the clinic data, the system presents a menu with 16 options
      that allow the user to interact with the clinic system.
      Users can perform various tasks, such as registering patients and clinical staff,
      assigning rooms and staff, and displaying information about patients and rooms.
    - Selection Option 10 (Display all rooms - Seating Chart):
      In this example, the user selects option 10 to view the seating chart of all rooms.
      The system then displays the current status of the clinic,
      showing which patients are in each room, their patient ID, date of birth,
      and any visit records or staff assignments.
        - The chart lists all rooms, showing details about patients in waiting rooms, exam rooms,
          and procedure rooms, along with any available information
          regarding their visits or staff assignments.
    - Repeating Menu: After displaying the seating chart, the system returns to the main menu,
      allowing the user to select another option to continue interacting with the system:
- Purpose of This Example:
    - Usage of Command Line Interface (CLI):
      This example demonstrates how users interact with the system via a command-line interface.
      They can run the program using a .jar file and select various operations from a menu.
    - Seating Chart Overview: By choosing option 10, users can view a seating chart
      that displays the current occupancy of all clinic rooms,
      providing an overview of patient locations and assignments.
    - Realistic Data:
      The example includes sample patients with realistic data such as IDs, names, and dates of birth.
      It also reflects room assignment rules, where waiting rooms can have multiple patients,
      while exam and procedure rooms typically host one patient at a time.
      This example helps the user understand how to run the program,
      interact with its features, and view the seating chart,
      ensuring smooth clinic operations management.

### Design/Model Changes

A. Initial Design: Originally, room assignments allowed only a one-to-one mapping for all room types.
B. Previous Design (Milestone 1 Design): Adjusted to allow multiple patients in waiting rooms, 
improving the clinic's flexibility in handling patient traffic.
* [x]  Redesigned User Interface: Seating Chart
- The Seating Chart in the Cybernetic Implant Clinic program has been redesigned for enhanced clarity and readability. 
- The new layout ensures that the information is presented in an organized and structured manner, 
making it easier to view the current state of the clinic at a glance.
* [x] Key Improvements:
1. Room Information:
- Each room is clearly labeled with its name, type (e.g., Waiting Room, Exam Room, Procedure Room), 
and coordinates for spatial representation. 
- Room details are presented first, followed by the list of occupants and staff.
2. Patient Details:
- If a room has patients, each patient is displayed with their full name and date of birth (DOB) in a new line, 
ensuring that patient information is easy to read and identify.
- If no patient is assigned, it will explicitly display: "No patients assigned."
3. Assigned Staff Members:
- For rooms with patients, staff members assigned to each patient are displayed with their:
+Full Name.
- Job Title (e.g. physician, nurse, reception)
- Role (e.g. Clinical Staff or Non-Clinical Staff)
- NPI (for clinical staff) or CPR Level (for non-clinical staff)
4. Compact yet Informative Layout:
- The redesigned layout provides a compact yet informative view of the clinic's state, 
allowing you to see the room occupancy and the staff assignments in one cohesive view
without overwhelming the reader with too much data in one section.
If a room has patients, each patient is displayed with their full name and date of birth (DOB) in a new line, 
ensuring that patient information is easy to read and identify.

C. Current Design: 
- The Current Design builds upon the improvements made in Milestone 1 
by further enhancing the functionality and clarity of the Cybernetic Implant Clinic's system. 
The focus of Milestone 2 is to improve how visit records, staff assignments, 
and room management are handled, along with enhancing the display of patient and room information. 
Key changes include the addition of patient visit records, 
improved room management logic, and refined staff role handling.

- Key Improvements in the Current Design:
 1. Room Information:
   - Same as Milestone 1: Each room remains clearly labeled with its name, type 
   (e.g., Waiting Room, Exam Room, Procedure Room), and coordinates.
   New Improvement: Along with room details and occupants, 
   the system now displays room visit records for each patient assigned to a room, 
   providing comprehensive information about the current patient visit in each room.

 2. Patient Details with Visit Records:
   - New in Milestone 2: Each patient in a room now has detailed information about their visit, 
   including:
     - Visit Record: Displays the patient's chief complaint, body temperature 
     (with precision using the TemperatureUnit enum), 
     and the registration date and time of the visit.
     - Clear Display: If no visit record exists for a patient, 
     it is explicitly stated: "No visit records found," 
     ensuring transparency in the information provided.
     This further enhances the readability and completeness of patient information 
     compared to the previous design.
 3. Enhanced Staff Role Handling:
  - More Detailed Staff Information: 
  In the current design, staff members assigned to patients 
  are shown with additional details about their roles:
    - For Clinical Staff: The title "Dr." or "Nurse" is added before their names 
    based on their role (physician or nurse). 
    Their NPI (National Provider Identifier) is displayed, 
    giving more context about their credentials. 
    - For Non-Clinical Staff: Their full name, job title (e.g., receptionist), 
    and CPR level are displayed, making non-clinical staff management more informative. 
    - Assignment to Multiple Patients: 
    The system now allows assigning multiple clinical staff members to a patient 
    - and properly displays all assigned staff for each patient.
 4. Expanded Room Management Logic:
    - Handling Multiple Patients: 
    The design retains the flexibility of allowing multiple patients in waiting rooms, 
    while ensuring that other room types (e.g., exam, procedure) 
    adhere to a one-patient-per-room rule. 
    - Room Assignment with Visit Information: 
    The room assignment logic has been improved to ensure that when a patient is assigned to a room, 
    their visit record is also linked to the room. 
    This creates a seamless connection between the patient's visit history and room occupancy. 
    - Clear Discharge Handling: When a patient is sent home, their discharge is properly reflected, 
    and they are removed from the room assignment, which was a limitation in previous designs.
 5. Enhanced User Interface:
    - The Seating Chart retains the compact and organized layout from Milestone 1 
    but is now more informative. The chart includes patient visit records 
    and detailed staff assignments, offering a complete view of the clinic's current state 
    without overwhelming the user. 
    - Error Handling Improvements: 
    Clear messages are displayed when no patients or no staff are assigned to a room, 
    improving the system's clarity and reducing confusion.
    Example of Changes in the Seating Chart Display:
```
Choose an option:
1. Register a new patient and add a visit record
2. Register an existing patient and enter a visit record
3. Register a clinical staff member
4. Assign a patient to a room
5. Assign a clinical staff member to a patient
6. Assign multiple clinical staff to a patient
7. Display information of all patients
8. Display information of a specific patient
9. Display information about a specific room
10. Display all rooms (Seating Chart)
11. Display all staff (Clinical and Nonclinical)
12. Display Clinical Staff Only
13. Display Non-Clinical Staff Only
14. Search staff by full name or identifier
15. Send a patient home (approved by physician)
16. Exit
Enter your choice: 10

```
```
========== Cybernetic Implant Clinic Testing SEATING CHART ==========

Room 1: WAITING Front Waiting Room [28,0 to 35,5]
------------------------------------------------------
Patient:
        Aandi Acute (Patient ID: 0 , DOB: 1/1/1981)
        Visit Records:
                Registration: 2024-10-23 15:47:49, Chief Complaint: check up, Body Temperature: 36.2 °C
        Assigned Staff:
                Dr. Amy Anguish (NPI: 1234567890)
Patient:
        Doug Derm (Patient ID: 3 , DOB: 4/4/1984)
        No visit records found.
        No staff assigned to this patient.
Patient:
        Greg Gastric (Patient ID: 6 , DOB: 7/7/1987)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 2: EXAM Triage [30,6 to 35,11]
------------------------------------------------------
Patient:
        Beth Bunion (Patient ID: 1 , DOB: 2/2/1982)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 3: WAITING Inside Waiting Room [28,12 to 35,19]
------------------------------------------------------
Patient:
        Clive Cardiac (Patient ID: 2 , DOB: 3/3/1983)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 4: EXAM Exam_1 [30,20 to 35,25]
------------------------------------------------------
Patient:
        Elise Enzyme (Patient ID: 4 , DOB: 5/5/1985)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

Room 5: PROCEDURE Surgical [26,13 to 27,18]
------------------------------------------------------
Patient:
        Fatima Follicle (Patient ID: 5 , DOB: 6/6/1986)
        No visit records found.
        No staff assigned to this patient.
--------------------------------------------

========== END OF SEATING CHART ==========
```

```
Choose an option:
1. Register a new patient and add a visit record
2. Register an existing patient and enter a visit record
3. Register a clinical staff member
4. Assign a patient to a room
5. Assign a clinical staff member to a patient
6. Assign multiple clinical staff to a patient
7. Display information of all patients
8. Display information of a specific patient
9. Display information about a specific room
10. Display all rooms (Seating Chart)
11. Display all staff (Clinical and Nonclinical)
12. Display Clinical Staff Only
13. Display Non-Clinical Staff Only
14. Search staff by full name or identifier
15. Send a patient home (approved by physician)
16. Exit
Enter your choice:
------------------------------------------------------
```

- Summary of Changes:
  - Visit Records: Added to patient display to enhance the information shown in each room.
  - Detailed Staff Assignments: Improved staff role handling, 
  including titles and identifiers for both clinical and non-clinical staff. 
  - Room Assignment Logic: Enhanced to ensure proper patient management 
  and room occupancy based on visit records. 
  - User Interface: Continued focus on a clear and organized layout 
  for the seating chart with additional details, 
  making the clinic's state easier to comprehend at a glance.
  - These updates in the Current Design ensure that the system can handle more complex scenarios, 
  such as visit record tracking and multiple staff assignments, 
  while maintaining ease of use and clarity in the user interface.
- 
### Assumptions

- Patient Room Assignment: It was assumed that only waiting rooms 
could accommodate multiple patients, 
while all other rooms (exam, procedure) are restricted to one patient at a time. 
This helps ensure a structured flow of patient care.

- Staff Registration: It was assumed that only clinical staff (nurses and physicians) 
need to be registered in detail, while non-clinical staff (e.g., receptionists) 
play a supportive role without requiring the same level of registration complexity.

- Identifiers for Staff: The assumption was made that clinical staff would use 
the National Provider Identifier (NPI), while non-clinical staff would use CPR levels, 
handled via the Identifier enum. 
This ensures that the system can easily differentiate between staff types.

- Visit Record Creation: 
It was assumed that each patient would have only one visit record per registration, 
and this visit record would include all relevant details 
such as registration date, body temperature, and chief complaint. 
The record creation applies to both new and returning patients.

- Room Management Logic: When assigning patients to rooms, 
it was assumed that the system would automatically check for room availability 
and ensure no overlap in room assignments (except for waiting rooms), 
preventing double-booking of rooms.

### Limitations
- Limited Staff Role Flexibility: The current implementation focuses on 
clinical staff (nurses and physicians) for detailed registration and assignments. 
Non-clinical staff, such as administrative personnel, 
are not fully integrated into the system beyond their identifier. 
This limits the functionality for tracking non-clinical staff roles 
and their interactions with patients.

- Single Visit Record Handling: 
The system only supports adding one visit record at a time, 
which means it may not yet handle more complex cases 
where patients have multiple visits on the same day or 
require simultaneous visits for different complaints.

- Error Handling for Duplicate Patients: 
The current implementation checks for existing patients by full name and date of birth, 
but it may not fully account for cases where two patients share the same name and birthdate, 
leading to potential misidentification.

- Room Management Constraints: 
The logic assumes that only one patient can be assigned to rooms other than waiting rooms. 
However, in certain real-world scenarios, multiple patients may need to be accommodated in exam 
or procedure rooms under special circumstances, which is not currently supported.

- Staff Assignment to Multiple Patients: 
Although the system allows assigning multiple staff to patients, 
there is limited flexibility in managing cases where clinical staff may be needed 
for multiple patients simultaneously across different rooms. 
This could lead to scheduling or resource allocation issues.

### Citations

1. <Strong>Oracle Java Documentation</Strong>
Oracle, "The Java™ Tutorials,"
Oracle. [Online]. Available: https://docs.oracle.com/javase/tutorial/. [Accessed: 28-Sep-2024].

2. <Strong>GeeksforGeeks Java Resources</Strong>
   GeeksforGeeks, "Introduction to Java," GeeksforGeeks. [Online]. 
Available: https://www.geeksforgeeks.org/introduction-to-java/. [Accessed: 24-Sep-2024].
3. <Strong>Baeldung StringBuilder Article</Strong>
   Baeldung, "StringBuilder in Java," Baeldung. [Online]. 
Available: https://www.baeldung.com/java-stringbuilder. [Accessed: 29-Sep-2024].
