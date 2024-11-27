package clinic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code Room} class implements {@link RoomInterface} and represents
 * a room in the clinic. It holds details about the room's name, type, coordinates,
 * and assigned patients.
 */
public class Room implements RoomInterface {
  private RoomType roomType; // RoomType enum
  private final int x1;
  private final int y1;
  private final int x2;
  private final int y2;
  private int roomNumber; // To be set after room creation
  private final String roomName;
  private final List<PatientInterface> patients; // Unified list for all room types

  /**
   * Constructs a new room with the specified name, type, and coordinates.
   *
   * @param roomName  The name of the room as a String.
   * @param roomType  The type of the room (from the {@link RoomType} enum).
   * @param x1        The x-coordinate of the top-left corner of the room.
   * @param y1        The y-coordinate of the top-left corner of the room.
   * @param x2        The x-coordinate of the bottom-right corner of the room.
   * @param y2        The y-coordinate of the bottom-right corner of the room.
   */
  public Room(String roomName, RoomType roomType, int x1, int y1, int x2, int y2) {
    if (roomName == null || roomName.trim().isEmpty()) {
      throw new IllegalArgumentException("Room name cannot be null or empty.");
    }
    if (roomType == null) {
      throw new IllegalArgumentException("Room type cannot be null.");
    }
    if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative.");
    }
    this.roomName = roomName;
    this.roomType = roomType;
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.roomNumber = 0;  // Default to an invalid value until explicitly set
    this.patients = new ArrayList<>(); // Unified list of patients
  }

  /**
   * Sets the room number for this room, uniquely identifying it within the clinic.
   *
   * @param roomNumber The room number to assign.
   */
  @Override
  public void setRoomNumber(int roomNumber) {
    if (roomNumber <= 0 || roomNumber > 100) {
      throw new IllegalArgumentException("Room number must be between 1 and 100.");
    }
    this.roomNumber = roomNumber;
  }

  /**
   * Retrieves the unique room number associated with this room.
   *
   * @return The room number as an integer.
   */
  @Override
  public int getRoomNumber() {
    return roomNumber;
  }

  /**
   * Retrieves the type of the room.
   *
   * @return The type of the room as a string (from the {@link RoomType} enum).
   */
  @Override
  public RoomType getRoomType() {
    return this.roomType;  // Ensure roomType is of type RoomType (enum)
  }

  /**
   * Retrieves the name of the room.
   *
   * @return The name of the room as a string.
   */
  @Override
  public String getRoomName() {
    return roomName;
  }

  /**
   * Retrieves the coordinates of the room in a formatted string.
   *
   * @return A string representing the coordinates of the room.
   */
  @Override
  public String getRoomCoordinates() {
    return "[" + x1 + "," + y1 + " to " + x2 + "," + y2 + "]";
  }

  /**
   * Returns a detailed string representation of the room, including its type,
   * name, coordinates, and assigned patients (if any).
   *
   * @return A string containing the room's details and assigned patients.
   */
  @Override
  public String getRoomInfo() {
    StringBuilder info = new StringBuilder();
    info.append(roomType).append(" ").append(roomName)
            .append(" [").append(x1).append(",").append(y1)
            .append(" to ").append(x2).append(",").append(y2).append("]\n");

    if (!patients.isEmpty()) {
      info.append("Assigned Patients:\n");
      for (PatientInterface patient : patients) {
        if (patient.getVisitStatus() != VisitStatus.DISCHARGED) {
          info.append("\nPatient: ").append(patient.getFullName())
                  .append(", ID: ").append(patient.getPatientId())
                  .append(", Visit Record: ").append(patient.getVisitRecords());
        }
      }
    } else {
      info.append("No patient assigned.");
    }

    return info.toString();
  }

  /**
   * Retrieves the list of patients assigned to this room.
   *
   * @return A list of {@link PatientInterface} objects representing the patients
   *         assigned to the room. If no patients are assigned, the list will be empty.
   */
  @Override
  public List<PatientInterface> getPatients() {
    return this.patients;
  }

  /**
   * Assigns a patient to this room. In waiting rooms, multiple patients
   * can be assigned, while non-waiting rooms can only have one patient.
   *
   * @param patient The patient to assign to the room.
   * @throws IllegalStateException if a non-waiting room already has a patient assigned.
   */
  @Override
  public void assignPatient(PatientInterface patient) {
    if (this.roomNumber <= 0) {
      throw new IllegalStateException("Room number must be set "
              + "before assigning a patient.");
    }
    if (roomType == RoomType.WAITING) {
      patients.add(patient);
    } else if (patients.isEmpty()) {
      patients.add(patient);
    } else {
      throw new IllegalStateException("Non-waiting rooms can only "
              + "have one patient at a time.");
    }
  }

  /**
   * Removes the specified patient from the room.
   *
   * @param patient The patient to remove from the room.
   */
  @Override
  public void removePatient(PatientInterface patient) {
    patients.remove(patient);
  }

  /**
   * Checks if two rooms are equal based on their name and type.
   *
   * @param o The object to compare to.
   * @return {@code true} if the rooms have the same name and type,
    otherwise {@code false}.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Room)) {
      return false;
    }
    Room room = (Room) o;
    return x1 == room.x1
            && y1 == room.y1
            && x2 == room.x2
            && y2 == room.y2
            && roomNumber == room.roomNumber
            && Objects.equals(roomName, room.roomName)
            && roomType == room.roomType;
  }

  /**
   * Generates a hash code for the room based on its name and type.
   *
   * @return The hash code for the room.
   */
  @Override
  public int hashCode() {
    return Objects.hash(roomName, roomType, x1, y1, x2, y2, roomNumber);
  }
}