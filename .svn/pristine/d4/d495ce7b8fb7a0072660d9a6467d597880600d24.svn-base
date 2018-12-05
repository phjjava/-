package com.jp.entity;

import java.util.ArrayList;
import java.util.List;

public class UserfuncgroupQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public UserfuncgroupQuery() {
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

        public Criteria andUserfuncgroupidIsNull() {
            addCriterion("userfuncgroupid is null");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidIsNotNull() {
            addCriterion("userfuncgroupid is not null");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidEqualTo(String value) {
            addCriterion("userfuncgroupid =", value, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidNotEqualTo(String value) {
            addCriterion("userfuncgroupid <>", value, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidGreaterThan(String value) {
            addCriterion("userfuncgroupid >", value, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidGreaterThanOrEqualTo(String value) {
            addCriterion("userfuncgroupid >=", value, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidLessThan(String value) {
            addCriterion("userfuncgroupid <", value, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidLessThanOrEqualTo(String value) {
            addCriterion("userfuncgroupid <=", value, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidLike(String value) {
            addCriterion("userfuncgroupid like", value, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidNotLike(String value) {
            addCriterion("userfuncgroupid not like", value, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidIn(List<String> values) {
            addCriterion("userfuncgroupid in", values, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidNotIn(List<String> values) {
            addCriterion("userfuncgroupid not in", values, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidBetween(String value1, String value2) {
            addCriterion("userfuncgroupid between", value1, value2, "userfuncgroupid");
            return (Criteria) this;
        }

        public Criteria andUserfuncgroupidNotBetween(String value1, String value2) {
            addCriterion("userfuncgroupid not between", value1, value2, "userfuncgroupid");
            return (Criteria) this;
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

        public Criteria andFungroupidIsNull() {
            addCriterion("fungroupid is null");
            return (Criteria) this;
        }

        public Criteria andFungroupidIsNotNull() {
            addCriterion("fungroupid is not null");
            return (Criteria) this;
        }

        public Criteria andFungroupidEqualTo(String value) {
            addCriterion("fungroupid =", value, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidNotEqualTo(String value) {
            addCriterion("fungroupid <>", value, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidGreaterThan(String value) {
            addCriterion("fungroupid >", value, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidGreaterThanOrEqualTo(String value) {
            addCriterion("fungroupid >=", value, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidLessThan(String value) {
            addCriterion("fungroupid <", value, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidLessThanOrEqualTo(String value) {
            addCriterion("fungroupid <=", value, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidLike(String value) {
            addCriterion("fungroupid like", value, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidNotLike(String value) {
            addCriterion("fungroupid not like", value, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidIn(List<String> values) {
            addCriterion("fungroupid in", values, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidNotIn(List<String> values) {
            addCriterion("fungroupid not in", values, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidBetween(String value1, String value2) {
            addCriterion("fungroupid between", value1, value2, "fungroupid");
            return (Criteria) this;
        }

        public Criteria andFungroupidNotBetween(String value1, String value2) {
            addCriterion("fungroupid not between", value1, value2, "fungroupid");
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