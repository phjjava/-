package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JpBannerQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public JpBannerQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo=pageNo;
        this.startRow = (pageNo-1)*this.pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setStartRow(Integer startRow) {
        this.startRow=startRow;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize=pageSize;
        this.startRow = (pageNo-1)*this.pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setFields(String fields) {
        this.fields=fields;
    }

    public String getFields() {
        return fields;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andBanneridIsNull() {
            addCriterion("bannerid is null");
            return (Criteria) this;
        }

        public Criteria andBanneridIsNotNull() {
            addCriterion("bannerid is not null");
            return (Criteria) this;
        }

        public Criteria andBanneridEqualTo(String value) {
            addCriterion("bannerid =", value, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridNotEqualTo(String value) {
            addCriterion("bannerid <>", value, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridGreaterThan(String value) {
            addCriterion("bannerid >", value, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridGreaterThanOrEqualTo(String value) {
            addCriterion("bannerid >=", value, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridLessThan(String value) {
            addCriterion("bannerid <", value, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridLessThanOrEqualTo(String value) {
            addCriterion("bannerid <=", value, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridLike(String value) {
            addCriterion("bannerid like", value, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridNotLike(String value) {
            addCriterion("bannerid not like", value, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridIn(List<String> values) {
            addCriterion("bannerid in", values, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridNotIn(List<String> values) {
            addCriterion("bannerid not in", values, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridBetween(String value1, String value2) {
            addCriterion("bannerid between", value1, value2, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBanneridNotBetween(String value1, String value2) {
            addCriterion("bannerid not between", value1, value2, "bannerid");
            return (Criteria) this;
        }

        public Criteria andBannernameIsNull() {
            addCriterion("bannername is null");
            return (Criteria) this;
        }

        public Criteria andBannernameIsNotNull() {
            addCriterion("bannername is not null");
            return (Criteria) this;
        }

        public Criteria andBannernameEqualTo(String value) {
            addCriterion("bannername =", value, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameNotEqualTo(String value) {
            addCriterion("bannername <>", value, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameGreaterThan(String value) {
            addCriterion("bannername >", value, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameGreaterThanOrEqualTo(String value) {
            addCriterion("bannername >=", value, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameLessThan(String value) {
            addCriterion("bannername <", value, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameLessThanOrEqualTo(String value) {
            addCriterion("bannername <=", value, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameLike(String value) {
            addCriterion("bannername like", value, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameNotLike(String value) {
            addCriterion("bannername not like", value, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameIn(List<String> values) {
            addCriterion("bannername in", values, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameNotIn(List<String> values) {
            addCriterion("bannername not in", values, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameBetween(String value1, String value2) {
            addCriterion("bannername between", value1, value2, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannernameNotBetween(String value1, String value2) {
            addCriterion("bannername not between", value1, value2, "bannername");
            return (Criteria) this;
        }

        public Criteria andBannerweburlIsNull() {
            addCriterion("bannerweburl is null");
            return (Criteria) this;
        }

        public Criteria andBannerweburlIsNotNull() {
            addCriterion("bannerweburl is not null");
            return (Criteria) this;
        }

        public Criteria andBannerweburlEqualTo(String value) {
            addCriterion("bannerweburl =", value, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlNotEqualTo(String value) {
            addCriterion("bannerweburl <>", value, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlGreaterThan(String value) {
            addCriterion("bannerweburl >", value, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlGreaterThanOrEqualTo(String value) {
            addCriterion("bannerweburl >=", value, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlLessThan(String value) {
            addCriterion("bannerweburl <", value, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlLessThanOrEqualTo(String value) {
            addCriterion("bannerweburl <=", value, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlLike(String value) {
            addCriterion("bannerweburl like", value, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlNotLike(String value) {
            addCriterion("bannerweburl not like", value, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlIn(List<String> values) {
            addCriterion("bannerweburl in", values, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlNotIn(List<String> values) {
            addCriterion("bannerweburl not in", values, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlBetween(String value1, String value2) {
            addCriterion("bannerweburl between", value1, value2, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerweburlNotBetween(String value1, String value2) {
            addCriterion("bannerweburl not between", value1, value2, "bannerweburl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlIsNull() {
            addCriterion("bannerphoneurl is null");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlIsNotNull() {
            addCriterion("bannerphoneurl is not null");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlEqualTo(String value) {
            addCriterion("bannerphoneurl =", value, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlNotEqualTo(String value) {
            addCriterion("bannerphoneurl <>", value, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlGreaterThan(String value) {
            addCriterion("bannerphoneurl >", value, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlGreaterThanOrEqualTo(String value) {
            addCriterion("bannerphoneurl >=", value, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlLessThan(String value) {
            addCriterion("bannerphoneurl <", value, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlLessThanOrEqualTo(String value) {
            addCriterion("bannerphoneurl <=", value, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlLike(String value) {
            addCriterion("bannerphoneurl like", value, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlNotLike(String value) {
            addCriterion("bannerphoneurl not like", value, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlIn(List<String> values) {
            addCriterion("bannerphoneurl in", values, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlNotIn(List<String> values) {
            addCriterion("bannerphoneurl not in", values, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlBetween(String value1, String value2) {
            addCriterion("bannerphoneurl between", value1, value2, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerphoneurlNotBetween(String value1, String value2) {
            addCriterion("bannerphoneurl not between", value1, value2, "bannerphoneurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlIsNull() {
            addCriterion("bannerurl is null");
            return (Criteria) this;
        }

        public Criteria andBannerurlIsNotNull() {
            addCriterion("bannerurl is not null");
            return (Criteria) this;
        }

        public Criteria andBannerurlEqualTo(String value) {
            addCriterion("bannerurl =", value, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlNotEqualTo(String value) {
            addCriterion("bannerurl <>", value, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlGreaterThan(String value) {
            addCriterion("bannerurl >", value, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlGreaterThanOrEqualTo(String value) {
            addCriterion("bannerurl >=", value, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlLessThan(String value) {
            addCriterion("bannerurl <", value, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlLessThanOrEqualTo(String value) {
            addCriterion("bannerurl <=", value, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlLike(String value) {
            addCriterion("bannerurl like", value, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlNotLike(String value) {
            addCriterion("bannerurl not like", value, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlIn(List<String> values) {
            addCriterion("bannerurl in", values, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlNotIn(List<String> values) {
            addCriterion("bannerurl not in", values, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlBetween(String value1, String value2) {
            addCriterion("bannerurl between", value1, value2, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerurlNotBetween(String value1, String value2) {
            addCriterion("bannerurl not between", value1, value2, "bannerurl");
            return (Criteria) this;
        }

        public Criteria andBannerdescIsNull() {
            addCriterion("bannerdesc is null");
            return (Criteria) this;
        }

        public Criteria andBannerdescIsNotNull() {
            addCriterion("bannerdesc is not null");
            return (Criteria) this;
        }

        public Criteria andBannerdescEqualTo(String value) {
            addCriterion("bannerdesc =", value, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescNotEqualTo(String value) {
            addCriterion("bannerdesc <>", value, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescGreaterThan(String value) {
            addCriterion("bannerdesc >", value, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescGreaterThanOrEqualTo(String value) {
            addCriterion("bannerdesc >=", value, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescLessThan(String value) {
            addCriterion("bannerdesc <", value, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescLessThanOrEqualTo(String value) {
            addCriterion("bannerdesc <=", value, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescLike(String value) {
            addCriterion("bannerdesc like", value, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescNotLike(String value) {
            addCriterion("bannerdesc not like", value, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescIn(List<String> values) {
            addCriterion("bannerdesc in", values, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescNotIn(List<String> values) {
            addCriterion("bannerdesc not in", values, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescBetween(String value1, String value2) {
            addCriterion("bannerdesc between", value1, value2, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andBannerdescNotBetween(String value1, String value2) {
            addCriterion("bannerdesc not between", value1, value2, "bannerdesc");
            return (Criteria) this;
        }

        public Criteria andCreateidIsNull() {
            addCriterion("createid is null");
            return (Criteria) this;
        }

        public Criteria andCreateidIsNotNull() {
            addCriterion("createid is not null");
            return (Criteria) this;
        }

        public Criteria andCreateidEqualTo(String value) {
            addCriterion("createid =", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidNotEqualTo(String value) {
            addCriterion("createid <>", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidGreaterThan(String value) {
            addCriterion("createid >", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidGreaterThanOrEqualTo(String value) {
            addCriterion("createid >=", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidLessThan(String value) {
            addCriterion("createid <", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidLessThanOrEqualTo(String value) {
            addCriterion("createid <=", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidLike(String value) {
            addCriterion("createid like", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidNotLike(String value) {
            addCriterion("createid not like", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidIn(List<String> values) {
            addCriterion("createid in", values, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidNotIn(List<String> values) {
            addCriterion("createid not in", values, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidBetween(String value1, String value2) {
            addCriterion("createid between", value1, value2, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidNotBetween(String value1, String value2) {
            addCriterion("createid not between", value1, value2, "createid");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andUpdateidIsNull() {
            addCriterion("updateid is null");
            return (Criteria) this;
        }

        public Criteria andUpdateidIsNotNull() {
            addCriterion("updateid is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateidEqualTo(String value) {
            addCriterion("updateid =", value, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidNotEqualTo(String value) {
            addCriterion("updateid <>", value, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidGreaterThan(String value) {
            addCriterion("updateid >", value, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidGreaterThanOrEqualTo(String value) {
            addCriterion("updateid >=", value, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidLessThan(String value) {
            addCriterion("updateid <", value, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidLessThanOrEqualTo(String value) {
            addCriterion("updateid <=", value, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidLike(String value) {
            addCriterion("updateid like", value, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidNotLike(String value) {
            addCriterion("updateid not like", value, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidIn(List<String> values) {
            addCriterion("updateid in", values, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidNotIn(List<String> values) {
            addCriterion("updateid not in", values, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidBetween(String value1, String value2) {
            addCriterion("updateid between", value1, value2, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdateidNotBetween(String value1, String value2) {
            addCriterion("updateid not between", value1, value2, "updateid");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andFamilyidIsNull() {
            addCriterion("familyid is null");
            return (Criteria) this;
        }

        public Criteria andFamilyidIsNotNull() {
            addCriterion("familyid is not null");
            return (Criteria) this;
        }

        public Criteria andFamilyidEqualTo(String value) {
            addCriterion("familyid =", value, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidNotEqualTo(String value) {
            addCriterion("familyid <>", value, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidGreaterThan(String value) {
            addCriterion("familyid >", value, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidGreaterThanOrEqualTo(String value) {
            addCriterion("familyid >=", value, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidLessThan(String value) {
            addCriterion("familyid <", value, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidLessThanOrEqualTo(String value) {
            addCriterion("familyid <=", value, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidLike(String value) {
            addCriterion("familyid like", value, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidNotLike(String value) {
            addCriterion("familyid not like", value, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidIn(List<String> values) {
            addCriterion("familyid in", values, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidNotIn(List<String> values) {
            addCriterion("familyid not in", values, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidBetween(String value1, String value2) {
            addCriterion("familyid between", value1, value2, "familyid");
            return (Criteria) this;
        }

        public Criteria andFamilyidNotBetween(String value1, String value2) {
            addCriterion("familyid not between", value1, value2, "familyid");
            return (Criteria) this;
        }

        public Criteria andGotypeIsNull() {
            addCriterion("gotype is null");
            return (Criteria) this;
        }

        public Criteria andGotypeIsNotNull() {
            addCriterion("gotype is not null");
            return (Criteria) this;
        }

        public Criteria andGotypeEqualTo(Byte value) {
            addCriterion("gotype =", value, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeNotEqualTo(Byte value) {
            addCriterion("gotype <>", value, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeGreaterThan(Byte value) {
            addCriterion("gotype >", value, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("gotype >=", value, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeLessThan(Byte value) {
            addCriterion("gotype <", value, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeLessThanOrEqualTo(Byte value) {
            addCriterion("gotype <=", value, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeIn(List<Byte> values) {
            addCriterion("gotype in", values, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeNotIn(List<Byte> values) {
            addCriterion("gotype not in", values, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeBetween(Byte value1, Byte value2) {
            addCriterion("gotype between", value1, value2, "gotype");
            return (Criteria) this;
        }

        public Criteria andGotypeNotBetween(Byte value1, Byte value2) {
            addCriterion("gotype not between", value1, value2, "gotype");
            return (Criteria) this;
        }

        public Criteria andDeleteflagIsNull() {
            addCriterion("deleteflag is null");
            return (Criteria) this;
        }

        public Criteria andDeleteflagIsNotNull() {
            addCriterion("deleteflag is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteflagEqualTo(Integer value) {
            addCriterion("deleteflag =", value, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagNotEqualTo(Integer value) {
            addCriterion("deleteflag <>", value, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagGreaterThan(Integer value) {
            addCriterion("deleteflag >", value, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagGreaterThanOrEqualTo(Integer value) {
            addCriterion("deleteflag >=", value, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagLessThan(Integer value) {
            addCriterion("deleteflag <", value, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagLessThanOrEqualTo(Integer value) {
            addCriterion("deleteflag <=", value, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagIn(List<Integer> values) {
            addCriterion("deleteflag in", values, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagNotIn(List<Integer> values) {
            addCriterion("deleteflag not in", values, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagBetween(Integer value1, Integer value2) {
            addCriterion("deleteflag between", value1, value2, "deleteflag");
            return (Criteria) this;
        }

        public Criteria andDeleteflagNotBetween(Integer value1, Integer value2) {
            addCriterion("deleteflag not between", value1, value2, "deleteflag");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}