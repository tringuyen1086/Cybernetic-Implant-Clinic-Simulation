package clinic;

import java.util.Objects;

/**
 * The Abstract {@link Person} class represents a person in the clinic.
 * Both staff and patients are represented by this class.
 */
public abstract class Person {
  protected final String firstName;
  protected final String lastName;

  /**
   * Constructs a {@link Person} object for a person.
   *
   * @param firstName The first name of the person. Cannot be null or empty.
   * @param lastName The last name of the person. Cannot be null or empty.
   * @throws IllegalArgumentException If the first or last name is null or empty.
   */
  public Person(String firstName, String lastName) {
    if (firstName == null || firstName.isBlank()
        || lastName == null || lastName.isBlank()) {
      throw new IllegalArgumentException("First name and last name cannot be null or empty.");
    }
    this.firstName = firstName;
    this.lastName = lastName;
  }

  /**
   * Gets the first name of the person.
   *
   * @return The first name of the person.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Gets the last name of the person.
   *
   * @return The last name of the person.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns the full name of the person.
   *
   * @return The full name, which is a combination of the first and last name.
   */
  public String getFullName() {
    return firstName + " " + lastName;
  }

  /**
   * Abstract method that must be implemented by
    subclasses to provide a detailed description of the person.
   * This might include specific attributes
   * such as job title, role, or patient-specific details.
   *
   * @return A description of the person.
   */
  public abstract String getDescription();

  /**
   * Determines whether this person is equal to another object.
   * Two {@link Person} objects are considered equal if they have the same first and last names.
   *
   * @param o The object to compare to.
   * @return {@code true} if the given object is a {@link Person} with the same
    first and last names, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(firstName, person.firstName)
            && Objects.equals(lastName, person.lastName);
  }

  /**
   * Returns a hash code value for the person.
   * The hash code is generated based on the person's first and last names
   * Ensures that equal persons produce the same hash code.
   *
   * @return The hash code value for this person.
   */
  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
  }
}

