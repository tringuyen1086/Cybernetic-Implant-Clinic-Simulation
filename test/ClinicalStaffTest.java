import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import clinic.ClinicalStaff;
import clinic.EducationLevel;
import clinic.JobTitle;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link ClinicalStaff} class.
 * This class tests the functionality of ClinicalStaff
 * including equality, description,
 * and retrieval of attributes like job title, education level, and NPI.
 */
public class ClinicalStaffTest {

  private ClinicalStaff physician1;
  private ClinicalStaff physician2;
  private ClinicalStaff nurse;

  /**
   * Setup method to initialize {@link ClinicalStaff} objects before each test.
   */
  @Before
  public void setUp() {
    physician1 = new ClinicalStaff(
            "Amy",
            "Anguish",
            JobTitle.PHYSICIAN,         // Use enum for JobTitle
            EducationLevel.DOCTORAL,    // Use enum for EducationLevel
            "1234567890");              // Remove "Dr." - NPI only

    physician2 = new ClinicalStaff(
            "Benny",
            "Bruise",
            JobTitle.PHYSICIAN,         // Use enum for JobTitle
            EducationLevel.DOCTORAL,    // Use enum for EducationLevel
            "0333444555");              // Remove "Dr." - NPI only

    nurse = new ClinicalStaff(
            "Camila",
            "Crisis",
            JobTitle.NURSE,             // Use enum for JobTitle
            EducationLevel.MASTERS,     // Use enum for EducationLevel
            "2224443338");              // Remove "Nurse" - NPI only
  }

  /**
   * Test the {@code getDescription} method
   * Verifies the description of the ClinicalStaff object
   * is formatted correctly.
   * The description should include the full name,
   * job title, education level, and NPI.
   */
  @Test
  public void testGetDescription() {
    String expectedDescription = "Dr. Amy Anguish - job title: physician - "
            + "education level: doctoral - Identifier: NPI (1234567890)";
    assertEquals("The description should match the expected format.",
            expectedDescription, physician1.getDescription());
  }

  /**
   * Test the {@code getJobTitle} method to
   * verify that the job title is retrieved correctly.
   */
  @Test
  public void testGetJobTitle() {
    assertEquals(JobTitle.PHYSICIAN, physician1.getJobTitle());
    assertEquals(JobTitle.PHYSICIAN, physician2.getJobTitle());
    assertEquals(JobTitle.NURSE, nurse.getJobTitle());
  }

  /**
   * Test the {@code getEducationLevel} method
    to verify that the education level is retrieved correctly.
   */
  @Test
  public void testGetEducationLevel() {
    assertEquals("doctoral", physician1.getEducationLevel());
    assertEquals("doctoral", physician2.getEducationLevel());
    assertEquals("masters", nurse.getEducationLevel());
  }

  /**
   * Test the {@code getStaffRole} method to
   * verify that the staff role is returned correctly.
   * The role should always be "Clinical Staff" for ClinicalStaff objects.
   */
  @Test
  public void testGetStaffRole() {
    assertEquals("Clinical Staff", physician1.getStaffRole());
  }

  /**
   * Test the {@code getNpi} method to verify that the NPI is retrieved correctly.
   */
  @Test
  public void testGetNpi() {
    assertEquals("1234567890", physician1.getNpi());
    assertEquals("0333444555", physician2.getNpi());
    assertEquals("2224443338", nurse.getNpi());
  }

  /**
   * Test the {@code equals} and {@code hashCode} methods
   * Verifies that two {@link ClinicalStaff} objects
    with the same attributes are equal and have the same hash code,
    while objects with different attributes are not equal
   and have different hash codes.
   */
  @Test
  public void testEqualsAndHashCode() {
    ClinicalStaff samePhysician = new ClinicalStaff(
            "Amy",
            "Anguish",
            JobTitle.PHYSICIAN,        // Use enum for JobTitle
            EducationLevel.DOCTORAL,   // Use enum for EducationLevel
            "1234567890");             // NPI, no "Dr." title

    ClinicalStaff differentPhysician = new ClinicalStaff(
            "Benny",
            "Bruise",
            JobTitle.PHYSICIAN,        // Use enum for JobTitle
            EducationLevel.DOCTORAL,   // Use enum for EducationLevel
            "0333444555");             // NPI, no "Dr." title

    // Test that two identical objects are equal
    assertTrue("Objects should be equal",
            physician1.equals(samePhysician));
    assertEquals("Hash codes should be equal",
            physician1.hashCode(), samePhysician.hashCode());

    // Test that different objects are not equal
    assertFalse("Objects should not be equal",
            physician1.equals(differentPhysician));
    assertNotEquals("Hash codes should not be equal",
            physician1.hashCode(), differentPhysician.hashCode());

    // Test inequality with null and different class
    assertFalse(physician1.equals(null)); // Null case
    assertFalse(physician1.equals(new Object())); // Different type
  }
}