package ru.itmo.databases.unit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Tests for Dao")
@TestMethodOrder(MethodOrderer.DisplayName.class) // порядок выполнения - по алфавиту @DisplayName
public class DaoTest {
    private Dao dao;

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        System.out.println("@BeforeEach - executes before each test method in this class");
        dao = new Dao();
    }

    // TestClass_TestMethod_ConditionAndExpectedResult
    // TestMethod_Condition_ExpectedResult
    @Test
    public void AddString_Null_ThrowsException() throws TestException {
        String stringToAdd = null;
        assertThrows(TestException.class,
                () -> dao.addString(stringToAdd),
                "AddString_Null_ThrowsException failed");
    }

    @Test
    public void AddString_Empty_ThrowsException() throws TestException {
        String stringToAdd = "";
        assertThrows(TestException.class,
                () -> dao.addString(stringToAdd),
                "AddString_Empty_ThrowsException failed");
    }

    @Test
    public void AddString_NullOrEmpty_ThrowsException() throws TestException {
        assertAll("throws on empty or null",
                () -> assertThrows(TestException.class,
                        () -> dao.addString(null),
                        "null failed"),
                () -> assertThrows(TestException.class,
                        () -> dao.addString(""),
                        "empty failed")
        );
    }

    @Test
    public void AddString_Blank_NotThrowsException() throws TestException {
        assertDoesNotThrow(() -> dao.addString(" "), "blank failed");
    }

    @Test
    public void AddString_Unique_ReturnTrue() {
        assertAll("true on unique string",
                () -> assertTrue(dao.addString("hello"), "hello failed"),
                () -> assertTrue(dao.addString("world"), "world failed"),
                () -> assertTrue(dao.addString("black"), "black failed"),
                () -> assertTrue(dao.addString("sun"), "sun failed"),
                () -> assertTrue(dao.addString("tree"), "tree failed")
        );
    }

    @Test
    public void AddString_NonUnique_ReturnFalse() throws TestException {
        dao.addString("hello");
        dao.addString("world");
        dao.addString("black");
        dao.addString("sun");
        dao.addString("tree");

        assertAll("false on non unique string",
                () -> assertFalse(dao.addString("hello"), "hello failed"),
                () -> assertFalse(dao.addString("world"), "world failed"),
                () -> assertFalse(dao.addString("black"), "black failed"),
                () -> assertFalse(dao.addString("sun"), "sun failed"),
                () -> assertFalse(dao.addString("tree"), "tree failed")
        );
    }

    @Test
    @Disabled("Not Implemented Yet")
    public void NumberOfStrings_Empty_ReturnZero() {
        assertEquals(0, dao.getNumberOfStrings());
    }
}
