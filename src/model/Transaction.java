package model;

public class Transaction implements java.io.Serializable {

	private int id;
	private String applicant;
	private String date;
	private double totalAmount;
	private String type;
	private String expireDate = "30";
	private int status = 0;
	private String justification;
	private String approver;
	private Receipt[] receiptList;
	private String receiptsStr;
	
	public String getReceiptsStr() {
		return receiptsStr;
	}
	public void setReceiptsStr(String receiptsStr) {
		this.receiptsStr = receiptsStr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public Receipt[] getReceiptList() {
		return receiptList;
	}
	public void setReceiptList(Receipt[] receiptList) {
		this.receiptList = receiptList;
	}
}
