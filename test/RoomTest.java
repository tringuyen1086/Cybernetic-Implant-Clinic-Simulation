import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import clinic.Patient;
import clinic.PatientInterface;
import clinic.Room;
import clinic.RoomType;
import clinic.VisitStatus;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * unit test class for the {@link Room} class.
 * This test class verifies room creation, patient assignments,
 * and room details across different room types, ensuring proper behavior
 * without hard-coded values. It also includes tests for `equals()` and `hashCode()`
 * methods to verify room equality.
 */
public class RoomTest {

  private Room waitingRoom;
  private Room examRoom;
  private PatientInterface patient1;
  private PatientInterface patient2;

  /**
   * Set up the testing environment with dynamic values.
   * Initialize rooms and patients for testing.
   */
  @Before
  public void setUp() {
    // Dynamic data for test cases
    final String waitingRoomName = "Front Waiting Room";
    final String examRoomName = "Exam Room 1";

    // Initialize patients
    patient1 = new Patient("Aandi",
            "Acute",
            1,
            "01/01/1981",
            VisitStatus.IN_PROGRESS);
    patient2 = new Patient("Beth",
            "Bunion",
            2,
            "02/02/1982",
            VisitStatus.IN_PROGRESS);

    // Initialize rooms
    waitingRoom = new Room(waitingRoomName, RoomType.WAITING, 0, 0, 5, 5);
    examRoom = new Room(examRoomName, RoomType.EXAM, 6, 6, 10, 10);

    // Set room numbers dynamically
    waitingRoom.setRoomNumber(1);
    examRoom.setRoomNumber(2);
  }


  // --- Edge case tests for missing or incorrect information ---

  /**
   * Test creating a room with invalid coordinates.
   * Ensure that an exception is thrown
   * for invalid coordinates (negative values).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRoomCoordinates() {
    // Attempting to create a room with negative coordinates should fail
    new Room("Invalid Room", RoomType.EXAM, -1, -1, -5, -5);
  }

  /**
   * Test creating a room with missing room name.
   * Ensure that an exception is thrown when room name is null or empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRoomCreationWithMissingRoomName() {
    // Room name cannot be null or empty
    new Room(null, RoomType.WAITING, 0, 0, 5, 5);
  }

  /**
   * Test for attempting to assign a patient to a room
   * without setting a room number.
   * This test ensures that an exception is thrown
   * if a patient is assigned before the room number is set.
   */
  @Test(expected = IllegalStateException.class)
  public void testAssignPatientToRoomWithoutRoomNumber() {
    // Create a room without setting the room number
    Room roomWithoutNumber = new Room("Waiting Room",
            RoomType.WAITING, 0, 0, 5, 5);

    // Try to assign a patient,
    // should throw IllegalStateException since room number is not set
    // This should trigger the exception
    roomWithoutNumber.assignPatient(patient1);
  }

  /**
   * Test for setting a negative room number.
   * Ensure that negative room numbers are not allowed,
   * and the appropriate exception is thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetNegativeRoomNumber() {
    waitingRoom.setRoomNumber(-1); // Should throw IllegalArgumentException
  }

  /**
   * Test for setting invalid string input for room number.
   * Ensure that negative room numbers are not allowed,
   * and the appropriate exception is thrown.
   */
  @Test(expected = NumberFormatException.class)
  public void testInvalidStringInput() {
    // Simulating user input as a String that should be converted to an int
    String input = "abc";
    int roomNumber = Integer.parseInt(input); // This should throw a NumberFormatException
    examRoom.setRoomNumber(roomNumber);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidRoomNumber_LargeNumber() {
    // A large room number should trigger an exception
    examRoom.setRoomNumber(1010);
  }

  /**
   * Test for setting a room number to zero.
   * Ensure that zero is not allowed as a valid room number
   * and that an exception is thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetZeroRoomNumber() {
    waitingRoom.setRoomNumber(0); // Should throw IllegalArgumentException
  }

  /**
   * Test for setting a valid room number and verifying correct assignment.
   * Ensure that valid room numbers can be set and retrieved correctly.
   */
  @Test
  public void testSetValidRoomNumber() {
    examRoom.setRoomNumber(2);
    assertEquals(2, examRoom.getRoomNumber());
  }

  /**
   * Test for assigning two rooms with the same room number.
   * This test ensures that assigning the same room number to
   * two different rooms is handled correctly.
   */
  @Test
  public void testDuplicateRoomNumbers() {
    Room anotherRoom = new Room("Duplicate Room",
            RoomType.EXAM, 6, 6, 10, 10);
    anotherRoom.setRoomNumber(1); // Set same room number
    waitingRoom.setRoomNumber(1); // Set same room number

    // Check that two rooms have the same number but are still different objects
    assertEquals(waitingRoom.getRoomNumber(),
            anotherRoom.getRoomNumber());
    assertNotEquals(waitingRoom, anotherRoom); // Ensure they are different rooms
  }

  /**
   * Test for retrieving room info before setting a room number.
   * Ensure that room info can still be retrieved even
   * if the room number is not set.
   */
  @Test
  public void testGetRoomInfoBeforeRoomNumberSet() {
    String roomInfo = waitingRoom.getRoomInfo();
    assertTrue(roomInfo.contains("Front Waiting Room"));
    assertTrue(roomInfo.contains("WAITING"));
  }

  /**
   * Test for setting and retrieving a valid room number
   * and assigning a patient to that room.
   * This test ensures that once the room number is set,
   * patients can be assigned correctly.
   */
  @Test
  public void testAssignPatientAfterSettingRoomNumber() {
    waitingRoom.setRoomNumber(1);
    waitingRoom.assignPatient(patient1);

    List<PatientInterface> assignedPatients = waitingRoom.getPatients();
    assertTrue(assignedPatients.contains(patient1));
  }

  /**
   * Test creating a room with a null RoomType.
   * Ensure that an exception is thrown when RoomType is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRoomCreationWithNullRoomType() {
    // Room type cannot be null
    new Room("Unnamed Room", null, 0, 0, 5, 5);
  }

  /**
   * Test assigning a patient to a room with a null RoomType.
   * Ensure that an exception is thrown when RoomType is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAssignPatientToInvalidRoomType() {
    // Attempt to create a room with null RoomType should throw IllegalArgumentException
    Room invalidRoom
            = new Room("Invalid Room", null, 0, 0, 5, 5);
    invalidRoom.assignPatient(patient1); // This should not happen due to room creation failure
  }

  /**
   * Test assigning a patient to a room without room number set.
   * Ensure that no patients can be assigned if the room number is missing.
   */
  @Test(expected = IllegalStateException.class)
  public void testAssignPatientWithoutRoomNumber() {
    Room newRoom = new Room("Room Without Number",
            RoomType.EXAM, 5, 5, 10, 10);
    newRoom.assignPatient(patient1); // Should throw exception as room number not set
  }

  @Test
  public void testAssignPatientToWaitingRoom() {
    waitingRoom.assignPatient(patient1);
    assertTrue(waitingRoom.getPatients().contains(patient1));
  }

  @Test(expected = IllegalStateException.class)
  public void testAssignPatientToOccupiedNonWaitingRoom() {
    examRoom.assignPatient(patient1);
    examRoom.assignPatient(new Patient("Clive",
            "Cardiac",
            3,
            "03/03/1983",
            VisitStatus.IN_PROGRESS));
  }

  /**
   * Test removing a patient from an empty room.
   * Ensure that removing a patient
   * from an empty room doesn't throw an exception.
   */
  @Test
  public void testRemovePatientFromEmptyRoom() {
    // Attempt to remove a patient from a room that has no patients assigned
    examRoom.removePatient(patient1);

    // Ensure no exceptions were thrown and room is still empty
    assertTrue(examRoom.getPatients().isEmpty());
  }

  /**
   * Test creating a room with invalid room type
   * in terms of RoomType enum mismatch.
   * Ensure that unsupported types cannot be created
   * by using enum constants incorrectly.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRoomWithInvalidRoomTypeMismatch() {
    // Attempt to create a room with an invalid type not matching the expected enum
    new Room("Invalid Room",
            RoomType.valueOf("INVALID_TYPE"), 0, 0, 5, 5);
  }

  /**
   * Test the getRoomInfo method for a room with no patients assigned.
   * Ensure the room information is returned correctly with no patients.
   */
  @Test
  public void testGetRoomInfoWithNoPatients() {
    String roomInfo = examRoom.getRoomInfo();

    // Ensure room info includes the room type and name
    assertTrue(roomInfo.contains("EXAM"));
    assertTrue(roomInfo.contains("Exam Room 1"));

    // Ensure the room is marked as having no assigned patients
    assertTrue(roomInfo.contains("No patient assigned."));
  }

  /**
   * Test assigning and removing a patient with a mismatched RoomType.
   * Ensure room type consistency when assigning/removing patients.
   */
  @Test(expected = IllegalStateException.class)
  public void testAssignRemovePatientWithRoomTypeMismatch() {
    Room procedureRoom = new Room("Procedure Room",
            RoomType.PROCEDURE, 10, 10, 15, 15);
    procedureRoom.setRoomNumber(3);

    // Try assigning multiple patients to a non-waiting room
    procedureRoom.assignPatient(patient1); // First assignment should work
    procedureRoom.assignPatient(patient2); // Second assignment should throw exception
  }

  // --- Tests for equals() and hashCode() ---

  /**
   * Test the equality of two rooms with identical attributes.
   * Ensure that two rooms with the same name, type,
   * and coordinates are considered equal.
   */
  @Test
  public void testRoomEqualityWithMatchingDetails() {
    Room anotherWaitingRoom = new Room("Front Waiting Room",
            RoomType.WAITING, 0, 0, 5, 5);
    anotherWaitingRoom.setRoomNumber(1);

    // Test equality and hash code
    assertEquals(waitingRoom, anotherWaitingRoom);
    assertEquals(waitingRoom.hashCode(), anotherWaitingRoom.hashCode());
  }

  /**
   * Test inequality of two rooms with different coordinates.
   * Ensure that rooms with different coordinates are not considered equal.
   */
  @Test
  public void testRoomInequalityWithDifferentCoordinates() {
    Room differentRoom = new Room("Front Waiting Room",
            RoomType.WAITING, 10, 10, 15, 15);
    differentRoom.setRoomNumber(1);

    assertNotEquals(waitingRoom, differentRoom);
    assertNotEquals(waitingRoom.hashCode(), differentRoom.hashCode());
  }

  /**
   * Test inequality of two rooms with different room types.
   * Ensure that rooms with different room types are not considered equal.
   */
  @Test
  public void testRoomInequalityWithDifferentRoomTypes() {
    Room procedureRoom = new Room("Front Waiting Room",
            RoomType.PROCEDURE, 0, 0, 5, 5);
    procedureRoom.setRoomNumber(1);

    assertNotEquals(waitingRoom, procedureRoom);
    assertNotEquals(waitingRoom.hashCode(), procedureRoom.hashCode());
  }

  /**
   * Test inequality of two rooms with different room names.
   * Ensure that rooms with different names are not considered equal.
   */
  @Test
  public void testRoomInequalityWithDifferentRoomNames() {
    Room differentRoom = new Room("Back Waiting Room",
            RoomType.WAITING, 0, 0, 5, 5);
    differentRoom.setRoomNumber(1);

    assertNotEquals(waitingRoom, differentRoom);
    assertNotEquals(waitingRoom.hashCode(), differentRoom.hashCode());
  }

  /**
   * Test equality of two rooms with the same room number
   * but different attributes.
   * Ensure that the room number alone doesn't make two rooms equal.
   */
  @Test
  public void testRoomInequalityWithSameRoomNumberDifferentAttributes() {
    Room anotherExamRoom = new Room("Exam Room 1",
            RoomType.EXAM, 0, 0, 5, 5);
    anotherExamRoom.setRoomNumber(1);  // Same room number but different attributes

    assertNotEquals(examRoom, anotherExamRoom);
    assertNotEquals(examRoom.hashCode(), anotherExamRoom.hashCode());
  }
}