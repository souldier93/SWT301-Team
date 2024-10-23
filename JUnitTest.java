/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import common.Validation;
import dao.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Rule;
import dao.DoctorDAO;
import entity.Doctor;
import java.util.List;
import org.junit.Before;
import org.junit.rules.ExpectedException;

/**
 *
 * @author PHAM NGOC
 */
public class JUnitTest {

    Validation v = new Validation();
    Connection conn = mock(Connection.class);
    PreparedStatement ps = mock(PreparedStatement.class);
    ResultSet rs = mock(ResultSet.class);

    DBContext db = new DBContext();
    DoctorDAO dao = new DoctorDAO();

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    //test input name in normal case
    @Test
    public void InputName_NormalCase_ExpectSucess() throws Exception {
        String input = "Pham Ngoc";
        String expected = "Pham Ngoc";
        Assert.assertEquals(v.validateInput(input, 50), expected);

    }

    //test input name exceeds max length
    @Test
    public void testInputExceedsMaxLength() throws Exception {
        String input = "This is a string which is longer than 50 characters.";
        String result = v.validateInput(input, 50);
        assertNull(result);
    }

    //test input name is an empty string
    @Test
    public void testInputStringIsEmpty() throws Exception {
        String input = "";
        String result = v.validateInput(input, 50);
        assertNull(result);
    }

    //test date of birth in valid case
    @Test
    public void testValidDates() {
        assertTrue(v.isValidDate("28/02/1995"));
        assertTrue(v.isValidDate("29/02/2024"));
    }

    @Test
    //test date of birth with invalid day
    public void testInvalidDay() {
        assertFalse(v.isValidDate("31/04/2023"));
        assertFalse(v.isValidDate("29/02/2023"));
    }

    @Test
    //test date of birth with invalid month
    public void testInvalidMonth() {
        assertFalse(v.isValidDate("01/13/2020"));
        assertFalse(v.isValidDate("15/00/1023"));
    }

    //test date of birth with invalid format
    @Test
    public void testInvalidFormat() {
        assertFalse(v.isValidDate(""));
        assertFalse(v.isValidDate("asdfgh"));
        assertFalse(v.isValidDate("1997/03/09"));
    }

    //test Specialization - normal case
    public void InputSpecialization_NormalCase_ExpectSucess() throws Exception {
        String input = "Ophthalmology";
        String expected = "Ophthalmology";
        Assert.assertEquals(v.validateInput(input, 255), expected);
    }

    //test Specialization - abnormal case
    @Test
    public void testInputSpecializationIsEmpty() throws Exception {
        String input = "";
        String result = v.validateInput(input, 255);
        assertNull(result);
    }

    //Test Availability in valid case
    @Test
    public void testAvailability_NormalCase() throws Exception {
        assertTrue(v.isValidAvailability(0));
        assertTrue(v.isValidAvailability(1));
        assertTrue(v.isValidAvailability(2));
        assertTrue(v.isValidAvailability(3));
    }

    //Test Availability with invalid case
    @Test
    public void testAvailability_AbnormalCase() throws Exception {
        assertFalse(v.isValidAvailability(100));
    }

    // Tests for isValidEmail function  
    @Test
    public void testValidEmails() {
        assertTrue(v.isValidEmail("test@example.com"));
        assertTrue(v.isValidEmail("user.name+tag+sorting@example.com"));
        assertTrue(v.isValidEmail("user@subdomain.example.com"));
        assertTrue(v.isValidEmail("user.name@example.co.in"));
    }

    @Test
    public void testInvalidEmails() {
        assertFalse(v.isValidEmail("plainaddress"));
        assertFalse(v.isValidEmail("@missingusername.com"));
        assertFalse(v.isValidEmail("username@.com"));
        assertFalse(v.isValidEmail("!def!xyz%abc@example.com"));
    }

    // Tests for isValidPhoneNumber function  
    @Test
    public void testValidPhoneNumbers() {
        assertTrue(v.isValidPhoneNumber("(123)-456-7890"));
        assertTrue(v.isValidPhoneNumber("(000)-123-4567"));
    }

    @Test
    public void testInvalidPhoneNumbers() {
        assertFalse(v.isValidPhoneNumber("123-456-7890"));
        assertFalse(v.isValidPhoneNumber("(123) 456-7890"));
        assertFalse(v.isValidPhoneNumber("(123)-45A-7890"));
        assertFalse(v.isValidPhoneNumber("1234567890"));
        assertFalse(v.isValidPhoneNumber("(12)-345-67890"));
    }

    @Test
    public void testEmptyPhoneNumbers() {
        assertFalse(v.isValidPhoneNumber(""));
    }

    @Test
    public void testVaidSearchOption_ExpectedSucessful() {
        assertTrue(v.isValidSearchOption(0));
        assertTrue(v.isValidSearchOption(1));
    }
    
    @Test
    public void testVaidSearchOption_ExpectedFailed() {
        assertFalse(v.isValidSearchOption(10));
    }

    //--------------------------------------------------------------------------
    @Test
    public void testGetDoctorByID() throws Exception {

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt(1)).thenReturn(1);
        when(rs.getString(2)).thenReturn("Dr. John Smith");
        when(rs.getDate(3)).thenReturn(java.sql.Date.valueOf("1980-05-15"));
        when(rs.getString(4)).thenReturn("Cardiology");
        when(rs.getInt(5)).thenReturn(1);
        when(rs.getString(6)).thenReturn("john.smith@example.com");
        when(rs.getString(7)).thenReturn("(123)-456-7890");

        db.getConnection();

        Doctor result = dao.getByID("1");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Dr. John Smith", result.getName());
    }

    @Test
    public void testGetDoctorByID_DoesNotExist() throws Exception {

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);

        DBContext db = new DBContext();
        DoctorDAO dao = new DoctorDAO();
        db.getConnection();

        Doctor result = dao.getByID("999");

        assertNull(result);
    }

    @Test
    public void testGetDoctorByID_AbnormalCase() throws Exception {

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);

        DBContext db = new DBContext();
        DoctorDAO dao = new DoctorDAO();
        db.getConnection();

        Doctor result = dao.getByID("aaaaaa");

        assertNull(result);
    }

    @Test
    public void testInsertDoctor_ExpectedSuccessful() {
        int expected = 1;
        //insert a doctor with valid information
        int rowAffected = dao.insertDoctor("ngoc", "05/07/2007", "none", "1", "pham@gmail.com", "(123)-456-7890");
        assertEquals(expected, rowAffected);
    }

    @Test
    public void testInsertDoctor_ExpectedFailed_NameTooLong() {
        int expected = 0;
        String longName = "This is a string which is longer than 50 characters.";
        int rowAffected = dao.insertDoctor(longName, "05/07/2007", "none", "1", "pham@gmail.com", "(123)-456-7890");
        assertEquals(expected, rowAffected);
    }

    @Test
    public void testInsertDoctor_ExpectedFailed_InvalidDateFormat() {
        int expected = 0;
        int rowAffected = dao.insertDoctor("ngoc", "2007-07-05", "none", "1", "pham@gmail.com", "(123)-456-7890");
        assertEquals(expected, rowAffected);
    }

    @Test
    public void testInsertDoctor_ExpectedFailed_InvalidAvailability() {
        int expected = 0;
        int rowAffected = dao.insertDoctor("ngoc", "05/07/2007", "none", "4", "pham@gmail.com", "(123)-456-7890");
        assertEquals(expected, rowAffected);
    }

    @Test
    public void testInsertDoctor_ExpectedFailed_InvalidEmailFormat() {
        int expected = 0;
        int rowAffected = dao.insertDoctor("ngoc", "05/07/2007", "none", "1", "invalid-email", "(123)-456-7890");
        assertEquals(expected, rowAffected);
    }

    @Test
    public void testInsertDoctor_ExpectedFailed_InvalidMobileFormat() {
        int expected = 0;
        int rowAffected = dao.insertDoctor("ngoc", "05/07/2007", "none", "1", "pham@gmail.com", "1234567890");
        assertEquals(expected, rowAffected);
    }

    @Test
    public void testGetAllDoctor_ExpectedSuccessful() {
        List<Doctor> doctor = dao.getAllDoctor();
        assertNotNull(doctor);
        assertFalse(doctor.isEmpty());
    }

    @Test
    public void testDeleteDoctor_ExpectedSuccessful() {
        int expected = 1;
        //test with existed DoctorID
        int rowAffected = dao.delete("13");
        assertEquals(rowAffected, expected);
    }

    @Test
    public void testDeleteDoctor_ExpectedFailed() {
        int expected = 0;
        //test with non-existed DoctorID
        int rowAffected = dao.delete("1000");
        assertEquals(rowAffected, expected);
    }

    @Test
    public void testDeleteDoctor_ExpectedFailed_AbnormalCase() {
        int expected = 0;
        //test with invalid DoctorID
        assertEquals(dao.delete("aaaaaaaaaa"), expected);
        assertEquals(dao.delete(" "), expected);
    }

    @Test
    public void testUpdate_ExpectedSuccess_ValidData() {
        String id = "19";
        String name = "Pham Ngoc";
        String dob = "01/01/1980";
        String spec = "General Practitioner";
        String availability = "1"; // Available  
        String email = "ngocpham@example.com";
        String mobile = "(123)-456-7890";

        int expectedAffectedRows = 1;
        int rowAffected = dao.update(id, name, dob, spec, availability, email, mobile);

        assertEquals(expectedAffectedRows, rowAffected);
    }

    @Test
    public void testUpdate_ExpectedFailed_InvalidId() {
        String id = "999";
        String name = "Nguyen Van B";
        String dob = "02/02/1985";
        String spec = "Cardiologist";
        String availability = "1";
        String email = "nguyenb@example.com";
        String mobile = "(123)-456-7890";

        int expectedAffectedRows = 0;
        int rowAffected = dao.update(id, name, dob, spec, availability, email, mobile);

        assertEquals(expectedAffectedRows, rowAffected);
    }

    @Test
    public void testUpdate_ExpectedFailed_NameTooLong() {
        String id = "1";
        String name = "Nguyen Van AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"; // 60 characters
        String dob = "01/01/1980";
        String spec = "General Practitioner";
        String availability = "1";
        String email = "nguyena@example.com";
        String mobile = "(123)-456-7890";

        int expectedAffectedRows = 0;
        int rowAffected = dao.update(id, name, dob, spec, availability, email, mobile);

        assertEquals(expectedAffectedRows, rowAffected);
    }

    @Test
    public void testUpdate_ExpectedFailed_InvalidDateOfBirthFormat() {
        String id = "1";
        String name = "Nguyen Van C";
        String dob = "1980/01/01"; // invalid 
        String spec = "Pediatrician";
        String availability = "1";
        String email = "nguyenc@example.com";
        String mobile = "(123)-456-7890";

        int expectedAffectedRows = 0;
        int rowAffected = dao.update(id, name, dob, spec, availability, email, mobile);

        assertEquals(expectedAffectedRows, rowAffected);
    }

    @Test
    public void testUpdate_ExpectedFailed_InvalidAvailability() {
        String id = "1";
        String name = "Nguyen Van E";
        String dob = "01/01/1990";
        String spec = "Surgeon";
        String availability = "5"; // invalid
        String email = "nguyeene@example.com";
        String mobile = "(123)-456-7890";

        int expectedAffectedRows = 0;
        int rowAffected = dao.update(id, name, dob, spec, availability, email, mobile);

        assertEquals(expectedAffectedRows, rowAffected);
    }

    @Test
    public void testUpdate_ExpectedFailed_InvalidEmailFormat() {
        String id = "1";
        String name = "Nguyen Van F";
        String dob = "01/02/1988";
        String spec = "Neurologist";
        String availability = "1";
        String email = "invalid-email"; //invalid
        String mobile = "(123)-456-7890";

        int expectedAffectedRows = 0;
        int rowAffected = dao.update(id, name, dob, spec, availability, email, mobile);

        assertEquals(expectedAffectedRows, rowAffected);
    }

    @Test
    public void testUpdate_InvalidMobileFormat() {
        String id = "1";
        String name = "Nguyen Van G";
        String dob = "10/10/1992";
        String spec = "Orthopedic";
        String availability = "1";
        String email = "nguyeng@example.com";
        String mobile = "1234567890"; //invalid

        int expectedAffectedRows = 0;
        int rowAffected = dao.update(id, name, dob, spec, availability, email, mobile);

        assertEquals(expectedAffectedRows, rowAffected);
    }

    @Test
    public void testSortByDob_ExpectedSuccessful() {
        List<Doctor> doctor = dao.sortByDob();
        assertNotNull(doctor);
        assertFalse(doctor.isEmpty());
    }

    @Test
    public void testSearchByName_ExpectedSucessful() {
        List<Doctor> doctor = dao.searchByName("john");
        assertNotNull(doctor);
        assertFalse(doctor.isEmpty());
    }

    @Test
    public void testSearchByName_ExpectedFailed() {
        List<Doctor> doctor = dao.searchByName("nonexistentname");
        assertNotNull(doctor);
        assertTrue(doctor.isEmpty());
    }

}
