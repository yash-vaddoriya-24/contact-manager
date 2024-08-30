package org.example;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ContactAppTest {
    private ContactManager contactManager;
    @BeforeAll
    public static void setUpAll(){
        System.out.println("Should print Before All Tests");
    }
    @BeforeEach
    public void setUp() {
        System.out.println("Instantiating ContactManager");
        contactManager = new ContactManager();
    }
    @Test
    @DisplayName("Should Create Contact")
    public void shouldCreateContact() {
        contactManager.addContact("John","null","0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is null")
    public void shouldNotCreateContactWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null,"Doe","0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is null")
    public void shouldNotCreateContactWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, ()->{
            contactManager.addContact("John",null,"0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldNotCreateContactWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, ()->{
            contactManager.addContact("John","Doe","null");
        });
    }

    @Test
    @DisplayName("Should Create Contact")
    @EnabledOnOs(value= OS.WINDOWS, disabledReason = "Should Run only on MAC")
    public void shouldCreateOnMac(){
        contactManager.addContact("John","Doe","0123456789");
        assertTrue(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }
    @Test
    @DisplayName("Phone Number should start with 0")
    public void shouldTestPhoneNumberFormat() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Nested
    class ReapatedTests{
        @DisplayName("Repeat Contact Creation Test 5 Times")
        @RepeatedTest(value = 5, name="Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
        public void shouldTestContactCreationRepeatedly(){
            contactManager.addContact("John","Doe","0123456789");
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }
    }
    @Nested
    class ParametrizedTests{
        @DisplayName("Phone Number should match the required Format")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456798", "0123456897"})
        public void shouldTestPhoneNumberFormat(String phoneNumber){
            contactManager.addContact("John","Doe",phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }
        @DisplayName("CSV Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456798", "0123456897"})
        public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    @DisplayName("Method source case-phone number should match the required format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberUsingMethodSource(String phoneNumber){
        contactManager.addContact("John","Doe",phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList(){
        return Arrays.asList("0123456789", "0123456798", "0123456897");
    }
    @AfterEach
    public void tearDown() {
        contactManager = null;
    }
    @AfterAll
    public static void tearDownAll(){
        System.out.println("After All Tests");
    }

}
