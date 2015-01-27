package webService;



import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HackService {

	@WebMethod
	public String queryUser(String empId);
	
	@WebMethod
	public int updateUser(String userStr);
	
	@WebMethod
	public int addTransaction(String transStr);
	
	@WebMethod
	public String queryTransactionDetail(int transactionId);
	
	@WebMethod
	public String queryTransactionList(String userId, int status);
	
	@WebMethod
	public int processTransaction(int transactionId, int status);

	@WebMethod
	public String queryWaitApprovedTransactionList(String userId);
	
/*	@WebMethod
	public int processPicture(String data, Receipt receipt);*/

/*	@WebMethod
	public String testPicture(String str);*/
	
}
