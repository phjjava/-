
package com.jp.entity;
import lombok.Data;
/**
 * @author MuYi
 * 流程历史审批记录
 * */
@Data
public class HttpApprovalHistory {

    private String approvalOpinion;//审批意见

    private String approvalUserName;//审批人

    private String approvalTime;//审批时间

    private String agree;//审批状态
    
    private String imgurl;//签名图片地址
    
    private String editorial;//审批人所属编委会

	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	public String getApprovalUserName() {
		return approvalUserName;
	}

	public void setApprovalUserName(String approvalUserName) {
		this.approvalUserName = approvalUserName;
	}

	public String getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public HttpApprovalHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HttpApprovalHistory(String approvalOpinion, String approvalUserName, String approvalTime, String agree,
			String imgurl, String editorial) {
		super();
		this.approvalOpinion = approvalOpinion;
		this.approvalUserName = approvalUserName;
		this.approvalTime = approvalTime;
		this.agree = agree;
		this.imgurl = imgurl;
		this.editorial = editorial;
	}

	@Override
	public String toString() {
		return "HttpApprovalHistory [approvalOpinion=" + approvalOpinion + ", approvalUserName=" + approvalUserName
				+ ", approvalTime=" + approvalTime + ", agree=" + agree + ", imgurl=" + imgurl + ", editorial="
				+ editorial + "]";
	}

	
}
