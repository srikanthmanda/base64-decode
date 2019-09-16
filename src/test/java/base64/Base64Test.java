package base64;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;

/*
### Notes

JUnit test classes are set-up once for each `@Test` method, by default.
Due to this, it is not advisable to have ordered tests or interdependent tests.
Accordingly, `@Test` methods canNOT be `static`.
For the same reason, `@BeforeEach` and `@AfterEach`, canNOT be `static`.


`@BeforeAll` method has to be `static` as it runs before the test class is instantiated.
But it can be not `static` if the JUnit test class' @TestInstance is PER_CLASS


By convention, test classes and their members are `package-private` (the default, and hence no access modifier).


* __assume*__ methods enable conditional execution of tests.
* `assertAll` can run a set of asserts.
* `@Disabled` disables running the annotated test. (Doesn't affect non-test methods.)
* `@RepeatedTest` repeats a test the number of times passed as the annotation argument.
* `@DisplayName` can be used to label tests.
* `@Tag` can be used tag tests, which can be used include or exclude tests using supported runners.


`TestInfo` interface can be used to access the `Tag` and `DisplayName` properties.


`TestReporter` interface can be used to redirect messages to JUnit console.

*/

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Base64 Tests:")
class Base64Test {
	// TestInstance life-cycle set to PER_CLASS as all main class methods are static

	TestInfo testInfo;
	TestReporter testRrptr;

	@BeforeAll
	static void startTests() {
		System.out.println("Starting tests of Base64 class ...");
	}

//	@BeforeEach
//	void setUp() {
//		System.out.println("Starting a test of Base64 class ...");
//		Base64 classUnderTest = new Base64();
//		All methods of Base64 are static. So no need to setUp.
//	}

	@BeforeEach
	void init(TestInfo testInfo, TestReporter testRrptr) {
		testRrptr.publishEntry("Running " + testInfo.getDisplayName() + " of tag(s) " + testInfo.getTags());
	}

	@AfterAll
	static void endTests() {
		System.out.println("Tests of Base64 class completed.");
	}

	@Test
	@DisplayName("Conversion of Base64 characters to their 6 bit binary representations using a native API")
	@Tag("Unit")
	void to6BitBinSeqNativeTest() {
		assertThrows(InvalidBase64Character.class, () -> Base64.to6BitBinSeqNative(64));
	}

	@Test
	@DisplayName("Conversion of Base64 characters to their 6 bit binary representations:")
	@Tag("Unit")
	void to6BitBinSeqTest() {
		assertEquals(Base64.to6BitBinSeq(00), "000000");
		assertEquals(Base64.to6BitBinSeq(01), "000001");

		assertAll(() -> assertEquals(Base64.to6BitBinSeq(02), "000010"),
				() -> assertEquals(Base64.to6BitBinSeq(03), "000011"),
				() -> assertEquals(Base64.to6BitBinSeq(04), "000100"),
				() -> assertEquals(Base64.to6BitBinSeq(07), "000111"),
				// () -> assertEquals(Base64.to6BitBinSeq(08), "001000"),
				() -> assertEquals(Base64.to6BitBinSeq(15), "001111"),
				() -> assertEquals(Base64.to6BitBinSeq(16), "010000"),
				() -> assertEquals(Base64.to6BitBinSeq(31), "011111"),
				() -> assertEquals(Base64.to6BitBinSeq(32), "100000"),
				() -> assertEquals(Base64.to6BitBinSeq(63), "111111"));

	}

	@Nested
	@DisplayName("Conversion of 8 bit binary representation strings to the characters they represent: ")
	@Tag("Unit")
	class toCharTest {

		@Test
		@DisplayName("Positive Scenarios: ")
		void toCharPositiveTests() {
			assertEquals(Base64.toChar("01111110"), '~');
			assertEquals(Base64.toChar("01100000"), '`');
		}

		@Test
		@DisplayName("Negative Scenarios: ")
		void toCharNegativeTests() {
			assertNotEquals(Base64.toChar("01100000"), '0');
		}
	}

	@Test
	@DisplayName("Decoding Base64 string:")
	@Tag("Integration")
	void decodeTest() {
		assertThrows(InvalidBase64Character.class, () -> Base64.decode("-"));

		assertEquals(Base64.decode("cGxlYXN1cmUu"), "pleasure.");
		assertEquals(Base64.decode("YXN1cmUu"), "asure.");
//		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhc3VyZS4="), "any carnal pleasure.");
//		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhc3VyZQ=="), "any carnal pleasure");
//		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhc3Vy"), "any carnal pleasur");
//		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhc3U="), "any carnal pleasu");
//		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhcw=="), "any carnal pleas");
	}

	@Disabled
	@Test
	@DisplayName("Encoding Base64 string:")
	@Tag("Integration")
	void encodeTest() {
		fail("Test cases yet to be written");
	}
}
