package clinic;

/**
 * The {@link VisitStatus} enum represents the different statuses
 * that a patient's visit to the clinic can have
 * during their treatment process.
 * It helps track the progress of the patient's visit,
 * from ongoing to completion or discharge.
 */
public enum VisitStatus {

  /**
   * The visit is currently in progress.
   * The patient is still undergoing evaluation or treatment.
   */
  IN_PROGRESS,

  /**
   * The visit has been completed.
   * All treatments or evaluations have been finalized.
   */
  COMPLETED,

  /**
   * The visit is awaiting further test results or information.
   * This status indicates the patient is waiting for results
   * before the visit can be completed.
   */
  AWAITING_RESULTS,

  /**
   * The patient has been discharged.
   * The visit is fully concluded, and the patient has been sent home.
   */
  DISCHARGED;
}