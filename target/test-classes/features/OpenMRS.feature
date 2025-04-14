#Author: muthusundar044@gmail.com
# Created By:				      Muthu Sundar G
# Creation Date:          12-04-2025
# Last Modified By:
# Last Modification Date:

@Test
Feature: Patient Registration, Attachment Upload, and Deletion in OpenMRS

  Scenario: User registers a new patient, uploads an attachment, ends the visit, and deletes the patient record
    Given user is on the OpenMRS login page
    When user enters username "Admin" and password "Admin123"
    And user clicks on a location and logs in
    Then user should be redirected to the dashboard page and the page should be validated using an assertion
    When user clicks on "Register a patient" menu
    And user enters patient demographics details (Name, Gender, Birthdate) and contact information (Address, Phone number)
    Then user should verify the given Name, Gender, Birthdate, Address, and Phone number are populated correctly on the confirmation page
    When user clicks on Confirm
    Then user should be redirected to the patient details page and the age should be calculated correctly based on the provided Birthdate
    When user clicks on Start Visit and confirms the visit
    And user clicks on Attachment and uploads an attachment with file path "C:\\Users\\a851335\\OneDrive - Eviden\\Pictures\\Screenshots\\TestMS.png" and caption "TestImages"
    Then user should see a toaster message confirming the successful attachment upload
    When user redirects to the Patient details screen
    Then user should verify that the attachment section contains the uploaded attachment
    And user should verify that the recent visit has one entry with the current date and Attachment Upload tag
    When user clicks on the End Visit action at the right-hand side
    And user deletes the patient with reason "Test"
    Then user should see a toaster message confirming the successful deletion of the patient
    When user is redirected to the Find Patient Record menu
    Then user should verify that the deleted patient is not listed in the patient search results
