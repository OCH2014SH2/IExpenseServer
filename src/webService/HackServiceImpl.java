package webService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import util.HackUtil;
import DAO.ReceiptDao;
import DAO.TransactionDao;
import DAO.UserDao;
import model.QueryConfig;
import model.Receipt;
import model.Transaction;
import model.User;

@WebService(endpointInterface = "webService.HackService")
public class HackServiceImpl implements HackService{
	
	private static Logger logger = Logger.getLogger(HackServiceImpl.class);
	public static final ObjectMapper jacksonMapper = new ObjectMapper();

	@Override
	public String queryUser(String empId) {
		SqlSession session = null;
		String userStr = "";
		try {
			session = HackUtil.getSqlSession();
			
			UserDao userDao = session.getMapper(UserDao.class);
	        
	        User user = userDao.queryUser(empId);
	        
	        userStr = jacksonMapper.writeValueAsString(user);
	        session.commit();
	        session.close();
		} catch (Exception e) {
			logger.error("queryUser() function failed", e);
		}
		
        return userStr;
	}

	@Override
	public int updateUser(String userStr) {
		SqlSession session = null;
		int res = 0;
		try {
			session = HackUtil.getSqlSession();
			
			if (userStr != null & userStr.length() > 0) {
				User user = jacksonMapper.readValue(userStr, User.class);

				UserDao userDao = session.getMapper(UserDao.class);
		        
		        res = userDao.updateUser(user);
			} else {
				logger.info("Object json string is empty or null");
			}		
			
	        session.commit();
	        session.close();
		} catch (Exception e) {
			logger.error("updateUser() function failed", e);
		}
		
        return res;
	}

	@Override
	public int addTransaction(String transStr) {
		SqlSession session = null;
		Transaction trans = null;
		int res = 0;
		try {
			session = HackUtil.getSqlSession();
			
			if (transStr != null & transStr.length() > 0) {
				trans = jacksonMapper.readValue(transStr, Transaction.class);
				TransactionDao transDao = session.getMapper(TransactionDao.class);
				ReceiptDao recDao = session.getMapper(ReceiptDao.class);
		        
		        res = transDao.addTranscation(trans);
		        
		        int latestId = transDao.getLatestTransactionId();
		        List<Receipt> recList = Arrays.asList(trans.getReceiptList());
		        
				for (Receipt rec : recList) {
					rec.setTransactionId(latestId);
					String path = HackUtil.bytesStr2Image(rec.getImgUrl());
					rec.setImgUrl(path);
					recDao.addReceipt(rec);
		        }
			} else {
				logger.info("Object json string is empty or null");
			}
			
	        session.commit();
	        session.close();
		} catch (Exception e) {
			logger.error("addTranscation() function failed", e);
		}
        return res;
	}

	@Override
	public String queryTransactionDetail(int transactionId) {
		SqlSession session = null;
		String transStr = "";
		try {
			session = HackUtil.getSqlSession();
			
			TransactionDao transDao = session.getMapper(TransactionDao.class);
			ReceiptDao recDao = session.getMapper(ReceiptDao.class);
			
	        Transaction trans = transDao.queryTransactionDetail(transactionId);
	        
	        List<Receipt> receiptList = recDao.getReceipt(transactionId);
	        for (Receipt rec : receiptList) {
	        	String bytesStr = HackUtil.image2BytesStr(rec.getImgUrl());
	        	rec.setImgUrl(bytesStr);
	        }
	        Receipt[] receipts = receiptList.toArray(new Receipt[]{});
	        trans.setReceiptList(receipts);
	        
	        transStr = jacksonMapper.writeValueAsString(trans);
	        
	        session.commit();
	        session.close();
	        
		} catch (Exception e) {
			logger.error("queryTransactionDetail() function failed", e);
		}
        
        return transStr;
	}

	@Override
	public String queryTransactionList(String userId, int status) {
		SqlSession session = null;
		String transListStr = "";
		try {
			session = HackUtil.getSqlSession();
			
			TransactionDao transDao = session.getMapper(TransactionDao.class);
	        
			QueryConfig config = new QueryConfig(userId, status);
	        List<Transaction> transList = transDao.queryTransactionList(config);
	        if (status == 99 && transList.size()>0) {
	        	for (Transaction trans : transList) {
	        		trans.setStatus(1);
	        		processTransaction(trans.getId(), trans.getStatus());
	        	}
	        }
	        transListStr = jacksonMapper.writeValueAsString(transList);
	        
	        session.commit();
	        session.close();
	        
		} catch (Exception e) {
			logger.error("queryTransactionList() function failed", e);
		}
		
		return transListStr;
	}

	@Override
	public int processTransaction(int transactionId, int status) {
		SqlSession session = null;
		int res = 0;
		try {
			session = HackUtil.getSqlSession();

			TransactionDao transDao = session.getMapper(TransactionDao.class);
	        
			Map map = new HashMap();
			map.put("id", transactionId);
			map.put("status", status);
	        res = transDao.processTransaction(map);
	        
	        session.commit();
	        session.close();
		} catch (Exception e) {
			logger.error("processTransaction() function failed", e);
		}
        return res;
	}
	
	@Override
	public String queryWaitApprovedTransactionList(String userId) {
		
		SqlSession session = null;
		String transListStr = "";
		try {
			session = HackUtil.getSqlSession();
			
			TransactionDao transDao = session.getMapper(TransactionDao.class);
	        
			//QueryConfig config = new QueryConfig(userId, status);
			Map map = new HashMap();
			map.put("userId", userId);
	        List<Transaction> transList = transDao.queryWaitApprovedTransactionList(map);
	        transListStr = jacksonMapper.writeValueAsString(transList);
	        
	        session.commit();
	        session.close();
	        
		} catch (Exception e) {
			logger.error("queryWaitApprovedTransactionList() function failed", e);
		}
		
		return transListStr;
	}
	
/*	@Override
	public int processPicture(String imgDataStr,Receipt receipt) {
        SqlSession session = null;
        int res = -1;
        String url = null;
		try {
			session = HackUtil.getSqlSession();
			
			ReceiptDao receiptDao = session.getMapper(ReceiptDao.class);
	        
			if (imgDataStr != null && imgDataStr.length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
				String formatDate = sdf.format(new Date());
				String fileName = formatDate + ".jpg";
				url = HackUtil.bytesStr2Image(imgDataStr);
				System.out.println(url);
			}
	        
			if (null == url) {
				logger.info("save picture fail ! ");
			} else {
				receipt.setImgUrl(url);
//				res = receiptDao.updateReceipt(receipt);
			}
			session.commit();
			session.close();
			
		} catch (IOException e) {
			logger.error("processTransaction/getSqlSession() function failed", e);
		}
		
		return res;
	}*/
	
/*	@Override
	public String testPicture(String str) { 
		FileOutputStream fos = null;  
		String fileName = "abcdef.jpg";
	    try{  
//	        String toDir = "C:/Users/yijzhu/Desktop/";   //存储路径  
	    	String toDir = "/images/";
	        byte[] buffer = new BASE64Decoder().decodeBuffer(str);   //对android传过来的图片字符串进行解码   
	        File destDir = new File(toDir);    
	        if(!destDir.exists()) 
	        	destDir.mkdir();  
	        fos = new FileOutputStream(new File(destDir,fileName));   //保存图片  
	        fos.write(buffer);  
	        fos.flush();  
	        fos.close();  
	        return "success:" + toDir;  
	    }catch (Exception e){  
	        e.printStackTrace();  
	        return "failed";  
	    }  
	}*/
}
