package clinic;

import java.util.List;

/**
 * The {@code RoomInterface} defines the blueprint for a room in the clinic.
 * It provides methods for managing patients assigned to the room,
 * as well as retrieving detailed information about the room's attributes
 * such as room type, name, coordinates, and assigned patients.
 */
public interface RoomInterface {

  /**
   * Sets the room number for this room, uniquely identifying it within the clinic.
   *
   * @param roomNumber The room number to assign.
   */
  void setRoomNumber(int roomNumber);

  /**
   * Retrieves the unique room number that identifies this room in the clinic.
   *
   * @return The room number as an integer.
   */
  int getRoomNumber();

  /**
   * Retrieves the type of the room (e.g., "waiting", "exam", "procedure").
   *
   * @return A string representing the type of the room.
   */
  public RoomType getRoomType();

  /**
   * Retrieves the name of the room.
   *
   * @return The name of the room.
   */
  String getRoomName();

  /**
   * Retrieves the coordinates of the room in the clinic as a formatted string.
   * The coordinates describe the spatial location of the room.
   *
   * @return A string representation of the room's coordinates
   * in the format "[x1,y1 to x2,y2]".
   */
  String getRoomCoordinates();

  /**
   * Retrieves detailed information about the room,
   * including its type, name, coordinates, assigned patients,
   * and any relevant visit information for the patients.
   * This may also include assigned staff information.
   *
   * @return A string containing the room's details,
    including patient and staff information.
   */
  String getRoomInfo();

  /**
   * Retrieves a list of patients assigned to the room.
   * This list applies to both waiting rooms
   * (which may contain multiple patients)
   * and non-waiting rooms (which should have only one patient).
   *
   * @return A list of {@link PatientInterface} objects representing the patients
   *         assigned to the room. If no patients are assigned,
   *         an empty list is returned.
   */
  List<PatientInterface> getPatients();

  /**
   * Assigns a patient to the room.
   * For waiting rooms, multiple patients can be assigned.
   * For non-waiting rooms, only one patient can be assigned,
   * and an exception will be thrown if the room already has a patient.
   *
   * @param patient The {@link PatientInterface} object representing the patient to assign.
   * @throws IllegalStateException if a non-waiting room already has a patient.
   */
  void assignPatient(PatientInterface patient);

  /**
   * Removes the specified patient from the room.
   * This method can be used to discharge a patient from the room
   * or move them to a different room.
   * In waiting rooms, this removes the patient from the list of assigned patients.
   * In non-waiting rooms, this removes the single assigned patient.
   *
   * @param patient The {@link PatientInterface} object representing the patient to remove.
   */
  void removePatient(PatientInterface patient);
}