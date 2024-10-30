import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import doctormanagement.DoctorManagement;
import doctormanagement.Doctor;

public class DoctorManagementTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    // Utility method for checking name length (1 to 50 characters)
    private String checkName(String name, int minLength, int maxLength) throws Exception {
        if (name == null || name.trim().isEmpty() || name.length() < minLength || name.length() > maxLength) {
            throw new Exception("You must input in range: " + minLength + " - " + maxLength);
        }
        return name;
    }

    // Utility method for checking date format (dd/MM/yyyy)
    private String checkDate(String date, String dateFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            throw new Exception("Invalid date format. Expected: " + dateFormat);
        }
        return date;
    }

    // Utility method for checking email format
    private String checkEmail(String email) throws Exception {
        if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new Exception("Invalid email format.");
        }
        return email;
    }

    // Utility method for checking phone format
    private String checkPhone(String phone) throws Exception {
        if (!phone.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            throw new Exception("Invalid phone number format. Expected format: (000)-000-0000");
        }
        return phone;
    }

    // Utility method for checking availability (0 to 3)
    private int checkAvailability(String availabilityStr) throws Exception {
        try {
            int availability = Integer.parseInt(availabilityStr);
            if (availability < 0 || availability > 3) {
                throw new Exception("Availability must be between 0 and 3.");
            }
            return availability;
        } catch (NumberFormatException e) {
            throw new Exception("Invalid availability input. Must be an integer between 0 and 3.");
        }
    }

    // Test for inputting a valid doctor name
    @Test
    public void inputValidName_NormalCase_ExpectSuccess() throws Exception {
        String input = "Dr. John Doe";
        String expected = "Dr. John Doe";
        String output = checkName(input, 1, 50);
        Assert.assertEquals(expected, output);
    }

    // Test for inputting a name that exceeds 50 characters
    @Test
    public void inputInvalidName_AbnormalCase_ExpectError() throws Exception {
        String input = "This name is far too long and should not be allowed because it exceeds the fifty character limit.";
        String expectedErrorMessage = "You must input in range: 1 - 50";
        expectedEx.expectMessage(expectedErrorMessage);
        checkName(input, 1, 50);
    }

    // Test for inputting an exact name that is 50 characters long
    @Test
    public void inputExactFiftyCharacterName_NormalCase_ExpectSuccess() throws Exception {
        String input = "abcdefghijklmnopqrstuvwxyzabcdefghi"; // 50 characters
        String expected = input;
        String output = checkName(input, 1, 50);
        Assert.assertEquals(expected, output);
    }

    // Test for inputting an invalid date (wrong format)
    @Test
    public void inputInvalidDateFormat_AbnormalCase_ExpectError() throws Exception {
        String input = "31-12-2000"; // Wrong format
        String expectedErrorMessage = "Invalid date format. Expected: dd/MM/yyyy";
        expectedEx.expectMessage(expectedErrorMessage);
        checkDate(input, "dd/MM/yyyy");
    }

    // Test for inputting a valid date
    @Test
    public void inputValidDate_NormalCase_ExpectSuccess() throws Exception {
        String input = "31/12/2000";
        String expected = "31/12/2000";
        String output = checkDate(input, "dd/MM/yyyy");
        Assert.assertEquals(expected, output);
    }

    // Test for inputting an invalid email format
    @Test
    public void inputInvalidEmail_AbnormalCase_ExpectError() throws Exception {
        String input = "invalid-email@domain";
        String expectedErrorMessage = "Invalid email format.";
        expectedEx.expectMessage(expectedErrorMessage);
        checkEmail(input);
    }

    // Test for inputting a valid email format
    @Test
    public void inputValidEmail_NormalCase_ExpectSuccess() throws Exception {
        String input = "doctor@example.com";
        String expected = "doctor@example.com";
        String output = checkEmail(input);
        Assert.assertEquals(expected, output);
    }

    // Test for inputting a valid phone number
    @Test
    public void inputValidPhone_NormalCase_ExpectSuccess() throws Exception {
        String input = "(123)-456-7890";
        String expected = "(123)-456-7890";
        String output = checkPhone(input);
        Assert.assertEquals(expected, output);
    }

    // Test for inputting an invalid phone number
    @Test
    public void inputInvalidPhone_AbnormalCase_ExpectError() throws Exception {
        String input = "1234567890"; // Incorrect format
        String expectedErrorMessage = "Invalid phone number format. Expected format: (000)-000-0000";
        expectedEx.expectMessage(expectedErrorMessage);
        checkPhone(input);
    }

    // Test for invalid availability string
    @Test
    public void inputInvalidAvailabilityString_AbnormalCase_ExpectError() throws Exception {
        String input = "two"; // Invalid string input
        String expectedErrorMessage = "Invalid availability input. Must be an integer between 0 and 3.";
        expectedEx.expectMessage(expectedErrorMessage);
        checkAvailability(input); // This will throw the custom exception
    }

    // Test for availability below 0
    @Test
    public void inputAvailabilityBelowZero_AbnormalCase_ExpectError() throws Exception {
        String input = "-1"; // Invalid availability
        String expectedErrorMessage = "Availability must be between 0 and 3.";
        expectedEx.expectMessage(expectedErrorMessage);
        checkAvailability(input);
    }

    // Test for availability above 3
    @Test
    public void inputAvailabilityAboveThree_AbnormalCase_ExpectError() throws Exception {
        String input = "4"; // Invalid availability
        String expectedErrorMessage = "Availability must be between 0 and 3.";
        expectedEx.expectMessage(expectedErrorMessage);
        checkAvailability(input);
    }

    // Test for availability with special character
    @Test
    public void inputAvailabilityWithSpecialCharacter_AbnormalCase_ExpectError() throws Exception {
        String input = "!"; // Invalid special character
        String expectedErrorMessage = "Invalid availability input. Must be an integer between 0 and 3.";
        expectedEx.expectMessage(expectedErrorMessage);
        checkAvailability(input);
    }

    // Test for valid availability input
    @Test
    public void inputValidAvailability_NormalCase_ExpectSuccess() throws Exception {
        String input = "2"; // Valid availability
        int expected = 2;
        int output = checkAvailability(input);
        Assert.assertEquals(expected, output);
    }

    // Test for a null name
    @Test
    public void inputNullName_AbnormalCase_ExpectError() throws Exception {
        String input = null; // Null name
        String expectedErrorMessage = "You must input in range: 1 - 50";
        expectedEx.expectMessage(expectedErrorMessage);
        checkName(input, 1, 50);
    }

    // Test for a blank name
    @Test
    public void inputBlankName_AbnormalCase_ExpectError() throws Exception {
        String input = ""; // Blank name
        String expectedErrorMessage = "You must input in range: 1 - 50";
        expectedEx.expectMessage(expectedErrorMessage);
        checkName(input, 1, 50);
    }

    // Test for a name with only spaces
    @Test
    public void inputSpaceName_AbnormalCase_ExpectError() throws Exception {
        String input = "    "; // Name with only spaces
        String expectedErrorMessage = "You must input in range: 1 - 50";
        expectedEx.expectMessage(expectedErrorMessage);
        checkName(input, 1, 50);
    }

    // Test for invalid date value (e.g., February 31)
    @Test
    public void inputInvalidDateValue_AbnormalCase_ExpectError() throws Exception {
        String input = "31/02/2020"; // Invalid date (Feb 31st)
        String expectedErrorMessage = "Invalid date format. Expected: dd/MM/yyyy";
        expectedEx.expectMessage(expectedErrorMessage);
        checkDate(input, "dd/MM/yyyy");
    }

 
}
