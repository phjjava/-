package com.jp.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Branch extends BranchKey implements Serializable {
    /**
     * 分支名称
     */
    private String branchname;

    /**
     * 是否停用
     */
    private Integer status;

    /**
     * 所在省名称
     */
    private String area;

    /**
     * 所在省编码
     */
    private String areacode;

    /**
     * 分支起始人ID
     */
    private String beginuserid;

    /**
     * 起始人姓名
     */
    private String beginname;

    /**
     * 上级分支ID
     */
    private String parentid;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 所在市编码
     */
    private String citycode;

    /**
     * 所在市名称
     */
    private String cityname;

    /**
     * 所在县编码
     */
    private String xcode;

    /**
     * 所在县名称
     */
    private String xname;
    
    /**
     * 详细地址
     */
    private String address;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 创建人
     */
    private String createid;

    /**
     * 更细时间
     */
    private Date updatetime;

    /**
     * 更新人
     */
    private String updateid;
    
    private Integer usercount;
    
    private String userid;
    
    /**
	 * 第几世
	 */
	private String genlevel;

	public String getGenlevel() {
		return genlevel;
	}

	public void setGenlevel(String genlevel) {
		this.genlevel = genlevel;
	}
	
	private Long start;// 页数
    
    private Long count;// 每页条数
    
   

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	private static final long serialVersionUID = 1L;

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname == null ? null : branchname.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }

    public String getBeginuserid() {
        return beginuserid;
    }

    public void setBeginuserid(String beginuserid) {
        this.beginuserid = beginuserid == null ? null : beginuserid.trim();
    }

    public String getBeginname() {
        return beginname;
    }

    public void setBeginname(String beginname) {
        this.beginname = beginname == null ? null : beginname.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public String getXcode() {
        return xcode;
    }

    public void setXcode(String xcode) {
        this.xcode = xcode == null ? null : xcode.trim();
    }

    public String getXname() {
        return xname;
    }

    public void setXname(String xname) {
        this.xname = xname == null ? null : xname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateid() {
        return updateid;
    }
    

    public Integer getUsercount() {
		return usercount;
	}

	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}

	public void setUpdateid(String updateid) {
        this.updateid = updateid == null ? null : updateid.trim();
    }
	
    public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", branchname=").append(branchname);
        sb.append(", status=").append(status);
        sb.append(", area=").append(area);
        sb.append(", areacode=").append(areacode);
        sb.append(", beginuserid=").append(beginuserid);
        sb.append(", beginname=").append(beginname);
        sb.append(", parentid=").append(parentid);
        sb.append(", sort=").append(sort);
        sb.append(", citycode=").append(citycode);
        sb.append(", cityname=").append(cityname);
        sb.append(", xcode=").append(xcode);
        sb.append(", xname=").append(xname);
        sb.append(", createtime=").append(createtime);
        sb.append(", createid=").append(createid);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", updateid=").append(updateid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}