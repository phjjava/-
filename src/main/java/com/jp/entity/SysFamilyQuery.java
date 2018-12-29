package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysFamilyQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public SysFamilyQuery() {
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

        public Criteria andFamilycodeIsNull() {
            addCriterion("familycode is null");
            return (Criteria) this;
        }

        public Criteria andFamilycodeIsNotNull() {
            addCriterion("familycode is not null");
            return (Criteria) this;
        }

        public Criteria andFamilycodeEqualTo(String value) {
            addCriterion("familycode =", value, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeNotEqualTo(String value) {
            addCriterion("familycode <>", value, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeGreaterThan(String value) {
            addCriterion("familycode >", value, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeGreaterThanOrEqualTo(String value) {
            addCriterion("familycode >=", value, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeLessThan(String value) {
            addCriterion("familycode <", value, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeLessThanOrEqualTo(String value) {
            addCriterion("familycode <=", value, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeLike(String value) {
            addCriterion("familycode like", value, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeNotLike(String value) {
            addCriterion("familycode not like", value, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeIn(List<String> values) {
            addCriterion("familycode in", values, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeNotIn(List<String> values) {
            addCriterion("familycode not in", values, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeBetween(String value1, String value2) {
            addCriterion("familycode between", value1, value2, "familycode");
            return (Criteria) this;
        }

        public Criteria andFamilycodeNotBetween(String value1, String value2) {
            addCriterion("familycode not between", value1, value2, "familycode");
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

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }
        
        ////
        public Criteria andSurnameIsNull() {
            addCriterion("surname is null");
            return (Criteria) this;
        }

        public Criteria andSurnameIsNotNull() {
            addCriterion("surname is not null");
            return (Criteria) this;
        }

        public Criteria andSurnameEqualTo(String value) {
            addCriterion("surname =", value, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameNotEqualTo(String value) {
            addCriterion("surname <>", value, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameGreaterThan(String value) {
            addCriterion("surname >", value, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameGreaterThanOrEqualTo(String value) {
            addCriterion("surname >=", value, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameLessThan(String value) {
            addCriterion("surname <", value, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameLessThanOrEqualTo(String value) {
            addCriterion("surname <=", value, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameLike(String value) {
            addCriterion("surname like", value, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameNotLike(String value) {
            addCriterion("surname not like", value, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameIn(List<String> values) {
            addCriterion("surname in", values, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameNotIn(List<String> values) {
            addCriterion("surname not in", values, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameBetween(String value1, String value2) {
            addCriterion("surname between", value1, value2, "surname");
            return (Criteria) this;
        }

        public Criteria andSurnameNotBetween(String value1, String value2) {
            addCriterion("surname not between", value1, value2, "surname");
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