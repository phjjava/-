package com.jp.entity;

import java.util.ArrayList;
import java.util.List;

public class FunctionRoleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public FunctionRoleExample() {
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

        public Criteria andFunctionidIsNull() {
            addCriterion("functionid is null");
            return (Criteria) this;
        }

        public Criteria andFunctionidIsNotNull() {
            addCriterion("functionid is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionidEqualTo(String value) {
            addCriterion("functionid =", value, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidNotEqualTo(String value) {
            addCriterion("functionid <>", value, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidGreaterThan(String value) {
            addCriterion("functionid >", value, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidGreaterThanOrEqualTo(String value) {
            addCriterion("functionid >=", value, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidLessThan(String value) {
            addCriterion("functionid <", value, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidLessThanOrEqualTo(String value) {
            addCriterion("functionid <=", value, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidLike(String value) {
            addCriterion("functionid like", value, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidNotLike(String value) {
            addCriterion("functionid not like", value, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidIn(List<String> values) {
            addCriterion("functionid in", values, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidNotIn(List<String> values) {
            addCriterion("functionid not in", values, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidBetween(String value1, String value2) {
            addCriterion("functionid between", value1, value2, "functionid");
            return (Criteria) this;
        }

        public Criteria andFunctionidNotBetween(String value1, String value2) {
            addCriterion("functionid not between", value1, value2, "functionid");
            return (Criteria) this;
        }

        public Criteria andEbidIsNull() {
            addCriterion("ebid is null");
            return (Criteria) this;
        }

        public Criteria andEbidIsNotNull() {
            addCriterion("ebid is not null");
            return (Criteria) this;
        }

        public Criteria andEbidEqualTo(String value) {
            addCriterion("ebid =", value, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidNotEqualTo(String value) {
            addCriterion("ebid <>", value, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidGreaterThan(String value) {
            addCriterion("ebid >", value, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidGreaterThanOrEqualTo(String value) {
            addCriterion("ebid >=", value, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidLessThan(String value) {
            addCriterion("ebid <", value, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidLessThanOrEqualTo(String value) {
            addCriterion("ebid <=", value, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidLike(String value) {
            addCriterion("ebid like", value, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidNotLike(String value) {
            addCriterion("ebid not like", value, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidIn(List<String> values) {
            addCriterion("ebid in", values, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidNotIn(List<String> values) {
            addCriterion("ebid not in", values, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidBetween(String value1, String value2) {
            addCriterion("ebid between", value1, value2, "ebid");
            return (Criteria) this;
        }

        public Criteria andEbidNotBetween(String value1, String value2) {
            addCriterion("ebid not between", value1, value2, "ebid");
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