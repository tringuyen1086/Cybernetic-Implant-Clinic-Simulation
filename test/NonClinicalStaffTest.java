import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import clinic.CprLevel;
import clinic.EducationLevel;
import clinic.JobTitle;
import clinic.NonClinicalStaff;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link NonClinicalStaff} class.
 * This class verifies the correct behavior of the {@link NonClinicalStaff} class,
 * including equality, hash code, getters, and description formatting.
 */
public class NonClinicalStaffTest {
  private NonClinicalStaff reception;
  private NonClinicalStaff sameReception;
  private NonClinicalStaff differentReception;

  /**
   * Setup method to initialize {@link NonClinicalStaff} objects before each test.
   */
  @Before
  public void setUp() {
    reception = new NonClinicalStaff("Frank",
            "Febrile",
            JobTitle.RECEPTION,
            EducationLevel.ALLIED,
            CprLevel.B);

    sameReception = new NonClinicalStaff("Frank",
            "Febrile",
            JobTitle.RECEPTION,
            EducationLevel.ALLIED,
            CprLevel.B);

    differentReception = new NonClinicalStaff("John",
            "Smith",
            JobTitle.RECEPTION,
            EducationLevel.MASTERS,
            CprLevel.C);
  }

  /**
   * Test the {@code getDescription} method
   * Verifies the description of the NonClinicalStaff object
   * is formatted correctly.
   * The description should include the full name, job title,
   * education level, and CPR level.
   */
  @Test
  public void testGetDescription() {
    String expectedDescription = "Frank Febrile - job title: "
            + "reception - education level: allied - "
            + "Identifier: CPR (B)";
    assertEquals(expectedDescription, reception.getDescription());
  }

  /**
   * Test the {@code getJobTitle} method to
   * verify that the job title is retrieved correctly.
   */
  @Test
  public void testGetJobTitle() {
    assertEquals(JobTitle.RECEPTION,
            reception.getJobTitle());
  }

  /**
   * Test the {@code getEducationLevel} method to
    verify that the education level is retrieved correctly.
   */
  @Test
  public void testGetEducationLevel() {
    assertEquals("allied",
            reception.getEducationLevel());
  }

  /**
   * Test the {@code getStaffRole} method to
   * verify that the staff role is returned correctly.
   * The role should always be "Non-Clinical Staff"
   * for NonClinicalStaff objects.
   */
  @Test
  public void testGetStaffRole() {
    assertEquals("Non-Clinical Staff",
            reception.getStaffRole());
  }

  /**
   * Test the {@code getCprLevel} method to
   * verify that the CPR level is retrieved correctly.
   */
  @Test
  public void testGetCprLevel() {
    assertEquals("B",
            reception.getCprLevel());
  }

  /**
   * Test the {@code equals} and {@code hashCode} methods
   * Verifies that two {@link NonClinicalStaff} objects
    with the same attributes are equal and have the same hash code,
   * while objects with different attributes are not equal
   * and have different hash codes.
   */
  @Test
  public void testEqualsAndHashCode() {

    // Test that objects with the same data should be equal
    // and have the same hash code
    assertTrue(reception.equals(sameReception));
    assertEquals(reception.hashCode(), sameReception.hashCode());

    // Test that objects with different data should not be equal
    // and have different hash codes
    assertFalse(reception.equals(differentReception));
    assertNotEquals(reception.hashCode(), differentReception.hashCode());

    // Test inequality with null and different class
    assertFalse(reception.equals(null)); // Null case
    assertFalse(reception.equals(new Object())); // Different type
  }
}