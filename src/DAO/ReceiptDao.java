package DAO;

import java.util.List;

import model.Receipt;

public interface ReceiptDao {
	
	public int addReceipt(Receipt receipt);
	
	public int updateReceipt(Receipt receipt);
	
	public int deleteReceipt(int ReceiptId);
	
	public List<Receipt> getReceipt(int transId);
	
	

}
