package clinic;

/**
 * The CprLevel enum represents the different levels of CPR certification
 * that a non-clinical staff member in the clinic can have.
 * There are only four possible CPR Levels: A, B, C, BLS:
 *
 * A: Basic CPR certification
 * B: Intermediate CPR certification
 * C: Advanced CPR certification
 * BLS: Basic Life Support certification
 * These levels indicate the type of CPR training the staff has completed.
 */
public enum CprLevel {
  A,
  B,
  C,
  BLS;

  @Override
  public String toString() {
    return this.name();
  }
}
