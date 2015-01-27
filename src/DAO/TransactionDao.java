package DAO;

import java.util.List;
import java.util.Map;

import model.QueryConfig;
import model.Transaction;


public interface TransactionDao {
	
	public int addTranscation(Transaction trans);
	
	public Transaction queryTransactionDetail(int transactionId);
	
	public List<Transaction> queryTransactionList(QueryConfig config);
	
	public int processTransaction(Map map);
	
	public int getLatestTransactionId();
	
	public List<Transaction> queryWaitApprovedTransactionList(Map map);

}
