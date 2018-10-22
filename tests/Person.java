import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class Person extends EasyMockSupport{
	
	@Rule
	public EasyMockRule rule = new EasyMockRule(this);
		
	
	@Mock
	private MufasaAccountCreation john; // 1
	private MufasaAddress userAddress; // 1
	private MufasaBankACC userBank; // 1
	private MufasaTransaction transaction; // 1
	//private Transaction info; // 1
	
	@TestSubject
	//Bank Account
	public BankAccount newBank = new BankAccount();// 2
	//Address
	public	Address newAddress = new Address();// 2
	//Info
	public Transaction info = new Transaction();// 2
	
	@Before
	public void setup(){
		john = EasyMock.createMock(MufasaAccountCreation.class);
		info.setAccount(john);
		replayAll(); // 4
		userAddress = EasyMock.createMock(MufasaAddress.class);
		info.setAddress(userAddress);
		
		userBank = EasyMock.createMock(MufasaBankACC.class);
		info.setBank(userBank);
		
		transaction = EasyMock.createMock(MufasaTransaction.class);
		info.setTransaction(transaction);
		
	}
	@Test
	public void test() throws UserException, ParseException, AddressException, BankAccountException {
		//User
		User newUser = new User();
		
		newUser.setFirstName("isyana");
		newUser.setLastName("sarasvati");
		newUser.setPassword("123asdf!@#", "123asdf!@#");
		newUser.setUsername("isyana21");
		newUser.setPhoneNumber("082162628276");
		newUser.setBirthdate("01/01/1990");
		newUser.setCountry("Russia");
		newUser.setEmail("haha@gmail.com", "haha@gmail.com");
		
		EasyMock.expect(john.getAccInfo()).andReturn(newUser);
		EasyMock.replay(john);
		
		
		
		newAddress.setStreetAddress("Tellervontie");
		newAddress.setCity("Oulu");
		newAddress.setPostalCode("90570");
		newAddress.setCountry("Russia");
		
		EasyMock.expect(userAddress.getAddressInfo()).andReturn(newAddress);
		EasyMock.replay(userAddress);
		
		
		
		newBank.setUserAddress(newAddress);
		newBank.setBankAccountPassword("123asdf!@#", "123asdf!@#");
		newBank.setCardHolderName("isyana sarasvati");
		newBank.setCardNumber("1000000200030004");
		newBank.setExpiryDate("09/21");
		
		EasyMock.expect(userBank.getBankInfo(newUser)).andReturn(newBank);
		EasyMock.replay(userBank);
		
		EasyMock.expect(transaction.getTransactionResult()).andReturn(334);
		EasyMock.replay(transaction);
		String result = info.getTransactionResult();
		
		verifyAll(); // 6
		assertEquals("Transaction Passed", result);
	}
}