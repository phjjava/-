package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public UserQuery() {
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

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(String value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(String value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(String value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(String value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(String value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(String value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLike(String value) {
            addCriterion("userid like", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotLike(String value) {
            addCriterion("userid not like", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<String> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<String> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(String value1, String value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(String value1, String value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Integer value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Integer value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Integer> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andLivestatusIsNull() {
            addCriterion("livestatus is null");
            return (Criteria) this;
        }

        public Criteria andLivestatusIsNotNull() {
            addCriterion("livestatus is not null");
            return (Criteria) this;
        }

        public Criteria andLivestatusEqualTo(Integer value) {
            addCriterion("livestatus =", value, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusNotEqualTo(Integer value) {
            addCriterion("livestatus <>", value, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusGreaterThan(Integer value) {
            addCriterion("livestatus >", value, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("livestatus >=", value, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusLessThan(Integer value) {
            addCriterion("livestatus <", value, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusLessThanOrEqualTo(Integer value) {
            addCriterion("livestatus <=", value, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusIn(List<Integer> values) {
            addCriterion("livestatus in", values, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusNotIn(List<Integer> values) {
            addCriterion("livestatus not in", values, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusBetween(Integer value1, Integer value2) {
            addCriterion("livestatus between", value1, value2, "livestatus");
            return (Criteria) this;
        }

        public Criteria andLivestatusNotBetween(Integer value1, Integer value2) {
            addCriterion("livestatus not between", value1, value2, "livestatus");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andUsednameIsNull() {
            addCriterion("usedname is null");
            return (Criteria) this;
        }

        public Criteria andUsednameIsNotNull() {
            addCriterion("usedname is not null");
            return (Criteria) this;
        }

        public Criteria andUsednameEqualTo(String value) {
            addCriterion("usedname =", value, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameNotEqualTo(String value) {
            addCriterion("usedname <>", value, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameGreaterThan(String value) {
            addCriterion("usedname >", value, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameGreaterThanOrEqualTo(String value) {
            addCriterion("usedname >=", value, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameLessThan(String value) {
            addCriterion("usedname <", value, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameLessThanOrEqualTo(String value) {
            addCriterion("usedname <=", value, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameLike(String value) {
            addCriterion("usedname like", value, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameNotLike(String value) {
            addCriterion("usedname not like", value, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameIn(List<String> values) {
            addCriterion("usedname in", values, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameNotIn(List<String> values) {
            addCriterion("usedname not in", values, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameBetween(String value1, String value2) {
            addCriterion("usedname between", value1, value2, "usedname");
            return (Criteria) this;
        }

        public Criteria andUsednameNotBetween(String value1, String value2) {
            addCriterion("usedname not between", value1, value2, "usedname");
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

        public Criteria andRemarkelseIsNull() {
            addCriterion("remarkelse is null");
            return (Criteria) this;
        }

        public Criteria andRemarkelseIsNotNull() {
            addCriterion("remarkelse is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkelseEqualTo(String value) {
            addCriterion("remarkelse =", value, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseNotEqualTo(String value) {
            addCriterion("remarkelse <>", value, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseGreaterThan(String value) {
            addCriterion("remarkelse >", value, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseGreaterThanOrEqualTo(String value) {
            addCriterion("remarkelse >=", value, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseLessThan(String value) {
            addCriterion("remarkelse <", value, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseLessThanOrEqualTo(String value) {
            addCriterion("remarkelse <=", value, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseLike(String value) {
            addCriterion("remarkelse like", value, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseNotLike(String value) {
            addCriterion("remarkelse not like", value, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseIn(List<String> values) {
            addCriterion("remarkelse in", values, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseNotIn(List<String> values) {
            addCriterion("remarkelse not in", values, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseBetween(String value1, String value2) {
            addCriterion("remarkelse between", value1, value2, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andRemarkelseNotBetween(String value1, String value2) {
            addCriterion("remarkelse not between", value1, value2, "remarkelse");
            return (Criteria) this;
        }

        public Criteria andBranchidIsNull() {
            addCriterion("branchid is null");
            return (Criteria) this;
        }

        public Criteria andBranchidIsNotNull() {
            addCriterion("branchid is not null");
            return (Criteria) this;
        }

        public Criteria andBranchidEqualTo(String value) {
            addCriterion("branchid =", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotEqualTo(String value) {
            addCriterion("branchid <>", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidGreaterThan(String value) {
            addCriterion("branchid >", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidGreaterThanOrEqualTo(String value) {
            addCriterion("branchid >=", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLessThan(String value) {
            addCriterion("branchid <", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLessThanOrEqualTo(String value) {
            addCriterion("branchid <=", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLike(String value) {
            addCriterion("branchid like", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotLike(String value) {
            addCriterion("branchid not like", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidIn(List<String> values) {
            addCriterion("branchid in", values, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotIn(List<String> values) {
            addCriterion("branchid not in", values, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidBetween(String value1, String value2) {
            addCriterion("branchid between", value1, value2, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotBetween(String value1, String value2) {
            addCriterion("branchid not between", value1, value2, "branchid");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(String value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(String value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(String value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(String value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(String value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(String value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLike(String value) {
            addCriterion("pid like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotLike(String value) {
            addCriterion("pid not like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<String> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<String> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(String value1, String value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(String value1, String value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andBrotherposIsNull() {
            addCriterion("brotherpos is null");
            return (Criteria) this;
        }

        public Criteria andBrotherposIsNotNull() {
            addCriterion("brotherpos is not null");
            return (Criteria) this;
        }

        public Criteria andBrotherposEqualTo(String value) {
            addCriterion("brotherpos =", value, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposNotEqualTo(String value) {
            addCriterion("brotherpos <>", value, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposGreaterThan(String value) {
            addCriterion("brotherpos >", value, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposGreaterThanOrEqualTo(String value) {
            addCriterion("brotherpos >=", value, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposLessThan(String value) {
            addCriterion("brotherpos <", value, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposLessThanOrEqualTo(String value) {
            addCriterion("brotherpos <=", value, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposLike(String value) {
            addCriterion("brotherpos like", value, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposNotLike(String value) {
            addCriterion("brotherpos not like", value, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposIn(List<String> values) {
            addCriterion("brotherpos in", values, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposNotIn(List<String> values) {
            addCriterion("brotherpos not in", values, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposBetween(String value1, String value2) {
            addCriterion("brotherpos between", value1, value2, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andBrotherposNotBetween(String value1, String value2) {
            addCriterion("brotherpos not between", value1, value2, "brotherpos");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andLogintimeIsNull() {
            addCriterion("logintime is null");
            return (Criteria) this;
        }

        public Criteria andLogintimeIsNotNull() {
            addCriterion("logintime is not null");
            return (Criteria) this;
        }

        public Criteria andLogintimeEqualTo(Date value) {
            addCriterion("logintime =", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeNotEqualTo(Date value) {
            addCriterion("logintime <>", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeGreaterThan(Date value) {
            addCriterion("logintime >", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("logintime >=", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeLessThan(Date value) {
            addCriterion("logintime <", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeLessThanOrEqualTo(Date value) {
            addCriterion("logintime <=", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeIn(List<Date> values) {
            addCriterion("logintime in", values, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeNotIn(List<Date> values) {
            addCriterion("logintime not in", values, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeBetween(Date value1, Date value2) {
            addCriterion("logintime between", value1, value2, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeNotBetween(Date value1, Date value2) {
            addCriterion("logintime not between", value1, value2, "logintime");
            return (Criteria) this;
        }

        public Criteria andSessionidIsNull() {
            addCriterion("sessionid is null");
            return (Criteria) this;
        }

        public Criteria andSessionidIsNotNull() {
            addCriterion("sessionid is not null");
            return (Criteria) this;
        }

        public Criteria andSessionidEqualTo(String value) {
            addCriterion("sessionid =", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotEqualTo(String value) {
            addCriterion("sessionid <>", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidGreaterThan(String value) {
            addCriterion("sessionid >", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidGreaterThanOrEqualTo(String value) {
            addCriterion("sessionid >=", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidLessThan(String value) {
            addCriterion("sessionid <", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidLessThanOrEqualTo(String value) {
            addCriterion("sessionid <=", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidLike(String value) {
            addCriterion("sessionid like", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotLike(String value) {
            addCriterion("sessionid not like", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidIn(List<String> values) {
            addCriterion("sessionid in", values, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotIn(List<String> values) {
            addCriterion("sessionid not in", values, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidBetween(String value1, String value2) {
            addCriterion("sessionid between", value1, value2, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotBetween(String value1, String value2) {
            addCriterion("sessionid not between", value1, value2, "sessionid");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andImgurlIsNull() {
            addCriterion("imgurl is null");
            return (Criteria) this;
        }

        public Criteria andImgurlIsNotNull() {
            addCriterion("imgurl is not null");
            return (Criteria) this;
        }

        public Criteria andImgurlEqualTo(String value) {
            addCriterion("imgurl =", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlNotEqualTo(String value) {
            addCriterion("imgurl <>", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlGreaterThan(String value) {
            addCriterion("imgurl >", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlGreaterThanOrEqualTo(String value) {
            addCriterion("imgurl >=", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlLessThan(String value) {
            addCriterion("imgurl <", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlLessThanOrEqualTo(String value) {
            addCriterion("imgurl <=", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlLike(String value) {
            addCriterion("imgurl like", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlNotLike(String value) {
            addCriterion("imgurl not like", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlIn(List<String> values) {
            addCriterion("imgurl in", values, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlNotIn(List<String> values) {
            addCriterion("imgurl not in", values, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlBetween(String value1, String value2) {
            addCriterion("imgurl between", value1, value2, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlNotBetween(String value1, String value2) {
            addCriterion("imgurl not between", value1, value2, "imgurl");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("idcard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("idcard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("idcard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("idcard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("idcard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("idcard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("idcard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("idcard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("idcard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("idcard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("idcard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("idcard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("idcard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("idcard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIsbornIsNull() {
            addCriterion("isborn is null");
            return (Criteria) this;
        }

        public Criteria andIsbornIsNotNull() {
            addCriterion("isborn is not null");
            return (Criteria) this;
        }

        public Criteria andIsbornEqualTo(Integer value) {
            addCriterion("isborn =", value, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornNotEqualTo(Integer value) {
            addCriterion("isborn <>", value, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornGreaterThan(Integer value) {
            addCriterion("isborn >", value, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornGreaterThanOrEqualTo(Integer value) {
            addCriterion("isborn >=", value, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornLessThan(Integer value) {
            addCriterion("isborn <", value, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornLessThanOrEqualTo(Integer value) {
            addCriterion("isborn <=", value, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornIn(List<Integer> values) {
            addCriterion("isborn in", values, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornNotIn(List<Integer> values) {
            addCriterion("isborn not in", values, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornBetween(Integer value1, Integer value2) {
            addCriterion("isborn between", value1, value2, "isborn");
            return (Criteria) this;
        }

        public Criteria andIsbornNotBetween(Integer value1, Integer value2) {
            addCriterion("isborn not between", value1, value2, "isborn");
            return (Criteria) this;
        }

//        public Criteria andDietimeIsNull() {
//            addCriterion("dietime is null");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeIsNotNull() {
//            addCriterion("dietime is not null");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeEqualTo(Date value) {
//            addCriterion("dietime =", value, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeNotEqualTo(Date value) {
//            addCriterion("dietime <>", value, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeGreaterThan(Date value) {
//            addCriterion("dietime >", value, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeGreaterThanOrEqualTo(Date value) {
//            addCriterion("dietime >=", value, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeLessThan(Date value) {
//            addCriterion("dietime <", value, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeLessThanOrEqualTo(Date value) {
//            addCriterion("dietime <=", value, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeIn(List<Date> values) {
//            addCriterion("dietime in", values, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeNotIn(List<Date> values) {
//            addCriterion("dietime not in", values, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeBetween(Date value1, Date value2) {
//            addCriterion("dietime between", value1, value2, "dietime");
//            return (Criteria) this;
//        }
//
//        public Criteria andDietimeNotBetween(Date value1, Date value2) {
//            addCriterion("dietime not between", value1, value2, "dietime");
//            return (Criteria) this;
//        }

        public Criteria andFixplaceIsNull() {
            addCriterion("fixplace is null");
            return (Criteria) this;
        }

        public Criteria andFixplaceIsNotNull() {
            addCriterion("fixplace is not null");
            return (Criteria) this;
        }

        public Criteria andFixplaceEqualTo(String value) {
            addCriterion("fixplace =", value, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceNotEqualTo(String value) {
            addCriterion("fixplace <>", value, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceGreaterThan(String value) {
            addCriterion("fixplace >", value, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceGreaterThanOrEqualTo(String value) {
            addCriterion("fixplace >=", value, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceLessThan(String value) {
            addCriterion("fixplace <", value, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceLessThanOrEqualTo(String value) {
            addCriterion("fixplace <=", value, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceLike(String value) {
            addCriterion("fixplace like", value, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceNotLike(String value) {
            addCriterion("fixplace not like", value, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceIn(List<String> values) {
            addCriterion("fixplace in", values, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceNotIn(List<String> values) {
            addCriterion("fixplace not in", values, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceBetween(String value1, String value2) {
            addCriterion("fixplace between", value1, value2, "fixplace");
            return (Criteria) this;
        }

        public Criteria andFixplaceNotBetween(String value1, String value2) {
            addCriterion("fixplace not between", value1, value2, "fixplace");
            return (Criteria) this;
        }

        public Criteria andIsdirectIsNull() {
            addCriterion("isdirect is null");
            return (Criteria) this;
        }

        public Criteria andIsdirectIsNotNull() {
            addCriterion("isdirect is not null");
            return (Criteria) this;
        }

        public Criteria andIsdirectEqualTo(Integer value) {
            addCriterion("isdirect =", value, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectNotEqualTo(Integer value) {
            addCriterion("isdirect <>", value, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectGreaterThan(Integer value) {
            addCriterion("isdirect >", value, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectGreaterThanOrEqualTo(Integer value) {
            addCriterion("isdirect >=", value, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectLessThan(Integer value) {
            addCriterion("isdirect <", value, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectLessThanOrEqualTo(Integer value) {
            addCriterion("isdirect <=", value, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectIn(List<Integer> values) {
            addCriterion("isdirect in", values, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectNotIn(List<Integer> values) {
            addCriterion("isdirect not in", values, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectBetween(Integer value1, Integer value2) {
            addCriterion("isdirect between", value1, value2, "isdirect");
            return (Criteria) this;
        }

        public Criteria andIsdirectNotBetween(Integer value1, Integer value2) {
            addCriterion("isdirect not between", value1, value2, "isdirect");
            return (Criteria) this;
        }

        public Criteria andGenlevelIsNull() {
            addCriterion("genlevel is null");
            return (Criteria) this;
        }

        public Criteria andGenlevelIsNotNull() {
            addCriterion("genlevel is not null");
            return (Criteria) this;
        }

        public Criteria andGenlevelEqualTo(Integer value) {
            addCriterion("genlevel =", value, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelNotEqualTo(Integer value) {
            addCriterion("genlevel <>", value, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelGreaterThan(Integer value) {
            addCriterion("genlevel >", value, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("genlevel >=", value, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelLessThan(Integer value) {
            addCriterion("genlevel <", value, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelLessThanOrEqualTo(Integer value) {
            addCriterion("genlevel <=", value, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelIn(List<Integer> values) {
            addCriterion("genlevel in", values, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelNotIn(List<Integer> values) {
            addCriterion("genlevel not in", values, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelBetween(Integer value1, Integer value2) {
            addCriterion("genlevel between", value1, value2, "genlevel");
            return (Criteria) this;
        }

        public Criteria andGenlevelNotBetween(Integer value1, Integer value2) {
            addCriterion("genlevel not between", value1, value2, "genlevel");
            return (Criteria) this;
        }

        public Criteria andFamilynameIsNull() {
            addCriterion("familyname is null");
            return (Criteria) this;
        }

        public Criteria andFamilynameIsNotNull() {
            addCriterion("familyname is not null");
            return (Criteria) this;
        }

        public Criteria andFamilynameEqualTo(String value) {
            addCriterion("familyname =", value, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameNotEqualTo(String value) {
            addCriterion("familyname <>", value, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameGreaterThan(String value) {
            addCriterion("familyname >", value, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameGreaterThanOrEqualTo(String value) {
            addCriterion("familyname >=", value, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameLessThan(String value) {
            addCriterion("familyname <", value, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameLessThanOrEqualTo(String value) {
            addCriterion("familyname <=", value, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameLike(String value) {
            addCriterion("familyname like", value, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameNotLike(String value) {
            addCriterion("familyname not like", value, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameIn(List<String> values) {
            addCriterion("familyname in", values, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameNotIn(List<String> values) {
            addCriterion("familyname not in", values, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameBetween(String value1, String value2) {
            addCriterion("familyname between", value1, value2, "familyname");
            return (Criteria) this;
        }

        public Criteria andFamilynameNotBetween(String value1, String value2) {
            addCriterion("familyname not between", value1, value2, "familyname");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstIsNull() {
            addCriterion("pinyinFirst is null");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstIsNotNull() {
            addCriterion("pinyinFirst is not null");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstEqualTo(String value) {
            addCriterion("pinyinFirst =", value, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstNotEqualTo(String value) {
            addCriterion("pinyinFirst <>", value, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstGreaterThan(String value) {
            addCriterion("pinyinFirst >", value, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstGreaterThanOrEqualTo(String value) {
            addCriterion("pinyinFirst >=", value, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstLessThan(String value) {
            addCriterion("pinyinFirst <", value, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstLessThanOrEqualTo(String value) {
            addCriterion("pinyinFirst <=", value, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstLike(String value) {
            addCriterion("pinyinFirst like", value, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstNotLike(String value) {
            addCriterion("pinyinFirst not like", value, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstIn(List<String> values) {
            addCriterion("pinyinFirst in", values, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstNotIn(List<String> values) {
            addCriterion("pinyinFirst not in", values, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstBetween(String value1, String value2) {
            addCriterion("pinyinFirst between", value1, value2, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfirstNotBetween(String value1, String value2) {
            addCriterion("pinyinFirst not between", value1, value2, "pinyinfirst");
            return (Criteria) this;
        }

        public Criteria andPinyinfullIsNull() {
            addCriterion("pinyinFull is null");
            return (Criteria) this;
        }

        public Criteria andPinyinfullIsNotNull() {
            addCriterion("pinyinFull is not null");
            return (Criteria) this;
        }

        public Criteria andPinyinfullEqualTo(String value) {
            addCriterion("pinyinFull =", value, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullNotEqualTo(String value) {
            addCriterion("pinyinFull <>", value, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullGreaterThan(String value) {
            addCriterion("pinyinFull >", value, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullGreaterThanOrEqualTo(String value) {
            addCriterion("pinyinFull >=", value, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullLessThan(String value) {
            addCriterion("pinyinFull <", value, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullLessThanOrEqualTo(String value) {
            addCriterion("pinyinFull <=", value, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullLike(String value) {
            addCriterion("pinyinFull like", value, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullNotLike(String value) {
            addCriterion("pinyinFull not like", value, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullIn(List<String> values) {
            addCriterion("pinyinFull in", values, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullNotIn(List<String> values) {
            addCriterion("pinyinFull not in", values, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullBetween(String value1, String value2) {
            addCriterion("pinyinFull between", value1, value2, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPinyinfullNotBetween(String value1, String value2) {
            addCriterion("pinyinFull not between", value1, value2, "pinyinfull");
            return (Criteria) this;
        }

        public Criteria andPnameIsNull() {
            addCriterion("pname is null");
            return (Criteria) this;
        }

        public Criteria andPnameIsNotNull() {
            addCriterion("pname is not null");
            return (Criteria) this;
        }

        public Criteria andPnameEqualTo(String value) {
            addCriterion("pname =", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotEqualTo(String value) {
            addCriterion("pname <>", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameGreaterThan(String value) {
            addCriterion("pname >", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameGreaterThanOrEqualTo(String value) {
            addCriterion("pname >=", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLessThan(String value) {
            addCriterion("pname <", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLessThanOrEqualTo(String value) {
            addCriterion("pname <=", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameLike(String value) {
            addCriterion("pname like", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotLike(String value) {
            addCriterion("pname not like", value, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameIn(List<String> values) {
            addCriterion("pname in", values, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotIn(List<String> values) {
            addCriterion("pname not in", values, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameBetween(String value1, String value2) {
            addCriterion("pname between", value1, value2, "pname");
            return (Criteria) this;
        }

        public Criteria andPnameNotBetween(String value1, String value2) {
            addCriterion("pname not between", value1, value2, "pname");
            return (Criteria) this;
        }

        public Criteria andMateidIsNull() {
            addCriterion("mateid is null");
            return (Criteria) this;
        }

        public Criteria andMateidIsNotNull() {
            addCriterion("mateid is not null");
            return (Criteria) this;
        }

        public Criteria andMateidEqualTo(String value) {
            addCriterion("mateid =", value, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidNotEqualTo(String value) {
            addCriterion("mateid <>", value, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidGreaterThan(String value) {
            addCriterion("mateid >", value, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidGreaterThanOrEqualTo(String value) {
            addCriterion("mateid >=", value, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidLessThan(String value) {
            addCriterion("mateid <", value, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidLessThanOrEqualTo(String value) {
            addCriterion("mateid <=", value, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidLike(String value) {
            addCriterion("mateid like", value, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidNotLike(String value) {
            addCriterion("mateid not like", value, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidIn(List<String> values) {
            addCriterion("mateid in", values, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidNotIn(List<String> values) {
            addCriterion("mateid not in", values, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidBetween(String value1, String value2) {
            addCriterion("mateid between", value1, value2, "mateid");
            return (Criteria) this;
        }

        public Criteria andMateidNotBetween(String value1, String value2) {
            addCriterion("mateid not between", value1, value2, "mateid");
            return (Criteria) this;
        }

        public Criteria andMatenameIsNull() {
            addCriterion("matename is null");
            return (Criteria) this;
        }

        public Criteria andMatenameIsNotNull() {
            addCriterion("matename is not null");
            return (Criteria) this;
        }

        public Criteria andMatenameEqualTo(String value) {
            addCriterion("matename =", value, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameNotEqualTo(String value) {
            addCriterion("matename <>", value, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameGreaterThan(String value) {
            addCriterion("matename >", value, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameGreaterThanOrEqualTo(String value) {
            addCriterion("matename >=", value, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameLessThan(String value) {
            addCriterion("matename <", value, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameLessThanOrEqualTo(String value) {
            addCriterion("matename <=", value, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameLike(String value) {
            addCriterion("matename like", value, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameNotLike(String value) {
            addCriterion("matename not like", value, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameIn(List<String> values) {
            addCriterion("matename in", values, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameNotIn(List<String> values) {
            addCriterion("matename not in", values, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameBetween(String value1, String value2) {
            addCriterion("matename between", value1, value2, "matename");
            return (Criteria) this;
        }

        public Criteria andMatenameNotBetween(String value1, String value2) {
            addCriterion("matename not between", value1, value2, "matename");
            return (Criteria) this;
        }

        public Criteria andBranchnameIsNull() {
            addCriterion("branchname is null");
            return (Criteria) this;
        }

        public Criteria andBranchnameIsNotNull() {
            addCriterion("branchname is not null");
            return (Criteria) this;
        }

        public Criteria andBranchnameEqualTo(String value) {
            addCriterion("branchname =", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameNotEqualTo(String value) {
            addCriterion("branchname <>", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameGreaterThan(String value) {
            addCriterion("branchname >", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameGreaterThanOrEqualTo(String value) {
            addCriterion("branchname >=", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameLessThan(String value) {
            addCriterion("branchname <", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameLessThanOrEqualTo(String value) {
            addCriterion("branchname <=", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameLike(String value) {
            addCriterion("branchname like", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameNotLike(String value) {
            addCriterion("branchname not like", value, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameIn(List<String> values) {
            addCriterion("branchname in", values, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameNotIn(List<String> values) {
            addCriterion("branchname not in", values, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameBetween(String value1, String value2) {
            addCriterion("branchname between", value1, value2, "branchname");
            return (Criteria) this;
        }

        public Criteria andBranchnameNotBetween(String value1, String value2) {
            addCriterion("branchname not between", value1, value2, "branchname");
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