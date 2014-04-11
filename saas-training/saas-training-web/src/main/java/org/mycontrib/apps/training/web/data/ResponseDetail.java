package org.mycontrib.apps.training.web.data;

public class ResponseDetail {
	private String questionText;
	private Integer questionNumber;
	private String goodResponseText;
	private String goodResponseChoice; //"A", "B" ou "A,C" , ....
	private boolean good;
	private String responseChoice;
	
	
	public ResponseDetail() {
		super();
	}
	
	
	
	public ResponseDetail(String questionText, Integer questionNumber,
			String goodResponseText, String goodResponseChoice, boolean good,
			String responseChoice) {
		super();
		this.questionText = questionText;
		this.questionNumber = questionNumber;
		this.goodResponseText = goodResponseText;
		this.goodResponseChoice = goodResponseChoice;
		this.good = good;
		this.responseChoice = responseChoice;
	}



	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public Integer getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getGoodResponseText() {
		return goodResponseText;
	}
	public void setGoodResponseText(String goodResponseText) {
		this.goodResponseText = goodResponseText;
	}
	public String getGoodResponseChoice() {
		return goodResponseChoice;
	}
	public void setGoodResponseChoice(String goodResponseChoice) {
		this.goodResponseChoice = goodResponseChoice;
	}
	public boolean isGood() {
		return good;
	}
	public void setGood(boolean good) {
		this.good = good;
	}
	public String getResponseChoice() {
		return responseChoice;
	}
	public void setResponseChoice(String responseChoice) {
		this.responseChoice = responseChoice;
	}
	
	
}
