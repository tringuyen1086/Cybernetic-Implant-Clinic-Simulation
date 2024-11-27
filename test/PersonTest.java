import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import clinic.Person;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link Person} class.
 * Since {@link Person} is abstract, these tests will use a concrete subclass
 * for instantiation.
 */
public class PersonTest {

  private Person person1;
  private Person person2;

  /**
   * A simple subclass of {@link Person} for testing purposes.
   */
  private static class TestPerson extends Person {
    public TestPerson(String firstName, String lastName) {
      super(firstName, lastName);
    }

    @Override
    public String getDescription() {
      return getFullName();
    }
  }

  /**
   * Set up two instances of {@link TestPerson} before each test.
   * {@code person1} and {@code person2} have different names.
   */
  @Before
  public void setUp() {
    person1 = new TestPerson("Aandi", "Acute");
    person2 = new TestPerson("Beth", "Bunion");
  }

  /**
   * Test that the constructor throws an {@link IllegalArgumentException}
   * when passed a {@link null} value for the first name.
   *
   * @throws IllegalArgumentException if the first name is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullFirstName() {
    new TestPerson(null, "Acute");
  }

  /**
   * Test that the constructor throws an {@link IllegalArgumentException}
   * when passed a {@link null} value for the last name.
   *
   * @throws IllegalArgumentException if the last name is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullLastName() {
    new TestPerson("Aandi", null);
  }

  /**
   * Test that {@code getFirstName} correctly returns the first name of the person.
   * Ensures that the first name is set properly via the constructor.
   */
  @Test
  public void testGetFirstName() {
    assertEquals("Aandi", person1.getFirstName());
    assertEquals("Beth", person2.getFirstName());
  }

  /**
   * Test that {@code getLastName} correctly returns the last name of the person.
   * Ensures that the last name is set properly via the constructor.
   */
  @Test
  public void testGetLastName() {
    assertEquals("Acute", person1.getLastName());
    assertEquals("Bunion", person2.getLastName());
  }

  /**
   * Test that {@code getFullName} correctly returns the full name of the person.
   * Combines the first and last name to create the full name.
   */
  @Test
  public void testGetFullName() {
    assertEquals("Aandi Acute", person1.getFullName());
    assertEquals("Beth Bunion", person2.getFullName());
  }

  /**
   * Test that {@code getDescription} provides the expected output for the person.
   * Since {@code getDescription} is abstract in {@code Person},
   * this test uses the {@link TestPerson} subclass to verify the implementation.
   */
  @Test
  public void testGetDescription() {
    assertEquals("Aandi Acute", person1.getDescription());
    assertEquals("Beth Bunion", person2.getDescription());
  }

  /**
   * Test that {@code equals} and {@code hashCode}
    correctly identify equal and unequal {@link Person} objects.
   * Two persons are considered equal if they have the same first and last names.
   * Objects that are equal must also have the same hash code.
   */
  @Test
  public void testEqualsAndHashCode() {
    // Test that two identical objects are equal
    assertTrue(person1.equals(person1));
    assertEquals(person1.hashCode(), person1.hashCode());

    // Test that different objects are not equal
    assertFalse(person1.equals(person2));
    assertNotEquals(person1.hashCode(), person2.hashCode());

    // Test inequality with null and different class
    assertFalse(person1.equals(null)); // Null case
    assertFalse(person1.equals(new Object())); // Different type
  }
}