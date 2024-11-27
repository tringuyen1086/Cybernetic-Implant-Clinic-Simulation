package clinic;

/**
 * The {@code ActivationStatus} enum represents
 * the activation state of a staff member in the clinic.
 * This status indicates whether a staff member is actively working
 * in the clinic or has been deactivated.
 *
 * It can have two possible values:
 *
 * {@link #ACTIVE} - The staff member is currently active
 * and able to perform their duties.
 * {@link #DEACTIVATED} - The staff member has been deactivated
 * and is no longer working.
 *
 */
public enum ActivationStatus {

  /**
   * Indicates that the staff member is currently active.
   */
  ACTIVE,

  /**
   * Indicates that the staff member has been deactivated.
   */
  DEACTIVATED;
}
