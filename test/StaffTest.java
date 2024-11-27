import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import clinic.ClinicalStaff;
import clinic.CprLevel;
import clinic.EducationLevel;
import clinic.Identifier;
import clinic.JobTitle;
import clinic.Staff;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@code Staff} class.
 * This test assumes there is a subclass of {@code Staff}, since {@code Staff} is abstract.
 * We'll mock a simple {@code MockClinicalStaff} and {@code MockNonClinicalStaff} for testing.
 */
public class StaffTest {

  private Staff clinicalStaff;
  private Staff nonClinicalStaff;
  private Staff sameClinicalStaff;
  private Staff sameNonClinicalStaff;
  private Staff differentClinicalStaff;
  private Staff differentNonClinicalStaff;

  /**
   * Setup test instances of Staff subclass (e.g., MockClinicalStaff).
   */
  @Before
  public void setUp() {
    // Initialize clinical staff
    clinicalStaff = new MockClinicalStaff("Amy",
            "Anguish",
            JobTitle.PHYSICIAN,       // Use the JobTitle enum
            EducationLevel.DOCTORAL,  // Use the EducationLevel enum
            "1234567890");

    // Initialize non-clinical staff with CprLevel enum
    nonClinicalStaff = new MockNonClinicalStaff("Frank",
            "Febrile",
            JobTitle.RECEPTION,       // Use the JobTitle enum directly
            EducationLevel.ALLIED,    // Use the EducationLevel enum
            CprLevel.B);              // Use CprLevel enum instead of String

    // Initialize identical clinical staff for equality tests
    sameClinicalStaff = new MockClinicalStaff("Amy",
            "Anguish",
            JobTitle.PHYSICIAN,       // Use the JobTitle enum
            EducationLevel.DOCTORAL,  // Use the EducationLevel enum
            "1234567890");

    // Initialize identical non-clinical staff for equality tests
    sameNonClinicalStaff = new MockNonClinicalStaff("Frank",
            "Febrile",
            JobTitle.RECEPTION,       // Use the JobTitle enum
            EducationLevel.ALLIED,    // Use the EducationLevel enum
            CprLevel.B);              // Use CprLevel enum instead of String

    // Initialize different clinical staff for inequality tests
    differentClinicalStaff = new MockClinicalStaff("Benny",
            "Bruise",
            JobTitle.PHYSICIAN,       // Use the JobTitle enum
            EducationLevel.DOCTORAL,  // Use the EducationLevel enum
            "0333444555");

    // Initialize different non-clinical staff for inequality tests
    differentNonClinicalStaff = new MockNonClinicalStaff("John",
            "Smith",
            JobTitle.RECEPTION,         // Use the JobTitle enum
            EducationLevel.MASTERS,     // Use the EducationLevel enum
            CprLevel.C);                // Use CprLevel enum instead of String
  }

  /**
   * Tests the {@code getJobTitle} method for both Clinical and Non-Clinical staff.
   */
  @Test
  public void testGetJobTitle() {
    // Arrange
    ClinicalStaff staff = new ClinicalStaff("John", "Doe",
            JobTitle.PHYSICIAN, EducationLevel.DOCTORAL, "1234567890");

    // Act
    String jobTitle = String.valueOf(staff.getJobTitle()); // This should now return a String

    // Assert
    assertEquals("PHYSICIAN", jobTitle); // Check for the String representation
  }

  /**
   * Tests the {@code getEducationLevel} method for both Clinical and Non-Clinical staff.
   */
  @Test
  public void testGetEducationLevel() {
    // Clinical Staff
    assertEquals("doctoral",
            clinicalStaff.getEducationLevel());

    // Non-Clinical Staff
    assertEquals("allied",
            nonClinicalStaff.getEducationLevel());
  }

  /**
   * Tests the {@code getStaffRole} method for both Clinical and Non-Clinical staff.
   */
  @Test
  public void testGetStaffRole() {
    // Clinical Staff
    assertEquals("Clinical", clinicalStaff.getStaffRole());

    // Non-Clinical Staff
    assertEquals("Non-Clinical", nonClinicalStaff.getStaffRole());
  }

  /**
   * Tests the {@code getDescription} method.
   */
  @Test
  public void testGetDescription() {
    // Clinical Staff
    String expectedClinicalDescription = "Mock: Amy Anguish - physician - doctoral - "
            + "Clinical Staff (NPI: 1234567890)";
    assertEquals(expectedClinicalDescription, clinicalStaff.getDescription());

    // Non-Clinical Staff
    String expectedNonClinicalDescription = "Frank Febrile - reception - allied - "
            + "Non-Clinical Staff (CPR Level: B)";
    assertEquals(expectedNonClinicalDescription, nonClinicalStaff.getDescription());
  }

  /**
   * Tests both {@code equals} and {@code hashCode} methods to ensure
   * consistency between logically equal objects and their hash codes.
   */
  @Test
  public void testEqualsAndHashCode() {
    // Test equality and hash code consistency for ClinicalStaff
    assertTrue(clinicalStaff.equals(sameClinicalStaff));
    assertEquals(clinicalStaff.hashCode(), sameClinicalStaff.hashCode());

    // Test equality and hash code consistency for Non-Clinical Staff
    assertTrue(nonClinicalStaff.equals(sameNonClinicalStaff));
    assertEquals(nonClinicalStaff.hashCode(), sameNonClinicalStaff.hashCode());

    // Test inequality with different staff members
    assertFalse(clinicalStaff.equals(nonClinicalStaff)); // Different objects
    assertNotEquals(clinicalStaff.hashCode(), nonClinicalStaff.hashCode()); // Different hash codes

    // Test inequality with null and different class
    assertFalse(clinicalStaff.equals(null)); // Null case
    assertFalse(clinicalStaff.equals(new Object())); // Different type
  }

  /**
   * Mock subclass of {@code Staff} for testing purposes.
   */
  public class MockClinicalStaff extends Staff {
    private String npi;

    /**
     * Constructs a mock {@code ClinicalStaff} object for testing purposes.
     *
     * @param firstName       The first name of the staff member.
     * @param lastName        The last name of the staff member.
     * @param jobTitle        The job title of the staff member
     *                        (using the JobTitle enum).
     * @param educationLevel  The education level of the staff member
     *                        (using the EducationLevel enum).
     * @param npi             The National Provider Identifier (NPI)
     *                        of the clinical staff member.
     */
    public MockClinicalStaff(String firstName,
                             String lastName,
                             JobTitle jobTitle,          // Use the JobTitle enum
                             EducationLevel educationLevel,  // Use the EducationLevel enum
                             String npi) {
      super(firstName, lastName, jobTitle, educationLevel, Identifier.NPI, npi);
      this.npi = npi;
    }

    @Override
    public String getFullName() {
      return "Mock: " + super.getFullName();
    }

    /**
     * Returns the NPI (National Provider Identifier) of the staff member.
     *
     * @return The NPI of the staff member.
     */
    public String getMockNpi() {
      return this.npi;
    }

    /**
     * Returns the role of the staff member.
     *
     * @return The role of the staff member as a string, e.g., "Clinical".
     */
    @Override
    public String getStaffRole() {
      return "Clinical";
    }

    /**
     * Returns a description of the ClinicalStaff including
     * the full name, job title, and education level.
     *
     * @return A string description of the ClinicalStaff.
     */
    @Override
    public String getDescription() {
      return getFullName() + " - "
              + jobTitle.getTitle()
              + " - "
              + educationLevel.getLevel()
              + " - Clinical Staff (NPI: "
              + npi + ")";
    }
  }

  /**
   * Mock subclass of {@code Staff} representing non-clinical staff
   * for testing purposes.
   */
  public class MockNonClinicalStaff extends Staff {
    private CprLevel cprLevel;

    /**
     * Constructs a {@code NonClinicalStaff} object with a CPR certification level.
     *
     * @param firstName       The first name of the staff member.
     * @param lastName        The last name of the staff member.
     * @param jobTitle        The job title of the staff member
     *                        (using the JobTitle enum).
     * @param educationLevel  The education level of the staff member
     *                        (using the EducationLevel enum).
     * @param cprLevel        The CPR certification level of
     *                        the non-clinical staff member.
     */
    public MockNonClinicalStaff(String firstName,
                                String lastName,
                                JobTitle jobTitle,          // JobTitle enum
                                EducationLevel educationLevel,  // EducationLevel enum
                                CprLevel cprLevel) {
      super(firstName, lastName, jobTitle, educationLevel,
              Identifier.CPR, cprLevel.name());
      this.cprLevel = cprLevel;
    }

    /**
     * Returns the role of the staff member.
     *
     * @return The role of the staff member as a string, e.g., "Non-Clinical".
     */
    @Override
    public String getStaffRole() {
      return "Non-Clinical";
    }

    /**
     * Returns a description of the NonClinicalStaff including
     * the full name, job title, and education level.
     *
     * @return A string description of the NonClinicalStaff.
     */
    @Override
    public String getDescription() {
      return getFullName()
              + " - "
              + jobTitle.getTitle()
              + " - "
              + educationLevel.getLevel()
              + " - Non-Clinical Staff (CPR Level: "
              + cprLevel + ")";
    }
  }
}
