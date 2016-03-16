package com.company.eleave.rest.dto;

public class ApproverDTO {

  private long id;
  private Long approverId;
  private String startDate;
  private String endDate;

  public long getId() {
      return id;
  }
  
  public void setId(long id) {
      this.id = id;
  }
  
  public Long getApproverId() {
    return approverId;
  }

  public void setApproverId(Long approverId) {
    this.approverId = approverId;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }


}
