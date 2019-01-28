package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

public class UserImport implements Serializable {
    private String userid;

    private String phone;

    private String password;

    private String sessionid;

    private Date logintime;

    private String username;

    private String usedname;

    private Byte livestatus;

    private Byte sex;

    private Integer ismarry;

    private String mateid;

    private String matename;

    /**
     * 配偶类型
     */
    private String matetypestr;

    private String pid;

    private String pname;

    private String familyid;

    private String familyname;

    private String branchid;

    private String branchname;

    private Byte isdirect;

    private Byte isborn;

    private Integer genlevel;

    private String brotherpos;

    private Byte status;

    private Byte deleteflag;

    private String imgurl;

    private String idcard;

    private String pinyinfirst;

    private String pinyinfull;

    private Date dietime;

    private String fixplace;

    private String remarkelse;

    private String createid;

    private Date createtime;

    private String updateid;

    private Date updatetime;

    /**
     * 导入的excel唯一标识
     */
    private String excelid;

    private static final long serialVersionUID = 1L;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid == null ? null : sessionid.trim();
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUsedname() {
        return usedname;
    }

    public void setUsedname(String usedname) {
        this.usedname = usedname == null ? null : usedname.trim();
    }

    public Byte getLivestatus() {
        return livestatus;
    }

    public void setLivestatus(Byte livestatus) {
        this.livestatus = livestatus;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Integer getIsmarry() {
        return ismarry;
    }

    public void setIsmarry(Integer ismarry) {
        this.ismarry = ismarry;
    }

    public String getMateid() {
        return mateid;
    }

    public void setMateid(String mateid) {
        this.mateid = mateid == null ? null : mateid.trim();
    }

    public String getMatename() {
        return matename;
    }

    public void setMatename(String matename) {
        this.matename = matename == null ? null : matename.trim();
    }

    public String getMatetypestr() {
        return matetypestr;
    }

    public void setMatetypestr(String matetypestr) {
        this.matetypestr = matetypestr == null ? null : matetypestr.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid == null ? null : familyid.trim();
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname == null ? null : familyname.trim();
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid == null ? null : branchid.trim();
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname == null ? null : branchname.trim();
    }

    public Byte getIsdirect() {
        return isdirect;
    }

    public void setIsdirect(Byte isdirect) {
        this.isdirect = isdirect;
    }

    public Byte getIsborn() {
        return isborn;
    }

    public void setIsborn(Byte isborn) {
        this.isborn = isborn;
    }

    public Integer getGenlevel() {
        return genlevel;
    }

    public void setGenlevel(Integer genlevel) {
        this.genlevel = genlevel;
    }

    public String getBrotherpos() {
        return brotherpos;
    }

    public void setBrotherpos(String brotherpos) {
        this.brotherpos = brotherpos == null ? null : brotherpos.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(Byte deleteflag) {
        this.deleteflag = deleteflag;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPinyinfirst() {
        return pinyinfirst;
    }

    public void setPinyinfirst(String pinyinfirst) {
        this.pinyinfirst = pinyinfirst == null ? null : pinyinfirst.trim();
    }

    public String getPinyinfull() {
        return pinyinfull;
    }

    public void setPinyinfull(String pinyinfull) {
        this.pinyinfull = pinyinfull == null ? null : pinyinfull.trim();
    }

    public Date getDietime() {
        return dietime;
    }

    public void setDietime(Date dietime) {
        this.dietime = dietime;
    }

    public String getFixplace() {
        return fixplace;
    }

    public void setFixplace(String fixplace) {
        this.fixplace = fixplace == null ? null : fixplace.trim();
    }

    public String getRemarkelse() {
        return remarkelse;
    }

    public void setRemarkelse(String remarkelse) {
        this.remarkelse = remarkelse == null ? null : remarkelse.trim();
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdateid() {
        return updateid;
    }

    public void setUpdateid(String updateid) {
        this.updateid = updateid == null ? null : updateid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getExcelid() {
        return excelid;
    }

    public void setExcelid(String excelid) {
        this.excelid = excelid == null ? null : excelid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", phone=").append(phone);
        sb.append(", password=").append(password);
        sb.append(", sessionid=").append(sessionid);
        sb.append(", logintime=").append(logintime);
        sb.append(", username=").append(username);
        sb.append(", usedname=").append(usedname);
        sb.append(", livestatus=").append(livestatus);
        sb.append(", sex=").append(sex);
        sb.append(", ismarry=").append(ismarry);
        sb.append(", mateid=").append(mateid);
        sb.append(", matename=").append(matename);
        sb.append(", matetypestr=").append(matetypestr);
        sb.append(", pid=").append(pid);
        sb.append(", pname=").append(pname);
        sb.append(", familyid=").append(familyid);
        sb.append(", familyname=").append(familyname);
        sb.append(", branchid=").append(branchid);
        sb.append(", branchname=").append(branchname);
        sb.append(", isdirect=").append(isdirect);
        sb.append(", isborn=").append(isborn);
        sb.append(", genlevel=").append(genlevel);
        sb.append(", brotherpos=").append(brotherpos);
        sb.append(", status=").append(status);
        sb.append(", deleteflag=").append(deleteflag);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", idcard=").append(idcard);
        sb.append(", pinyinfirst=").append(pinyinfirst);
        sb.append(", pinyinfull=").append(pinyinfull);
        sb.append(", dietime=").append(dietime);
        sb.append(", fixplace=").append(fixplace);
        sb.append(", remarkelse=").append(remarkelse);
        sb.append(", createid=").append(createid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updateid=").append(updateid);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", excelid=").append(excelid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}