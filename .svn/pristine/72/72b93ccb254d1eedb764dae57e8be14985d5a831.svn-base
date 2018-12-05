package com.jp.entity;

import java.util.ArrayList;
import java.util.List;

public class FungroupQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public FungroupQuery() {
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

        public Criteria andFuncgroupidIsNull() {
            addCriterion("funcgroupid is null");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidIsNotNull() {
            addCriterion("funcgroupid is not null");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidEqualTo(String value) {
            addCriterion("funcgroupid =", value, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidNotEqualTo(String value) {
            addCriterion("funcgroupid <>", value, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidGreaterThan(String value) {
            addCriterion("funcgroupid >", value, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidGreaterThanOrEqualTo(String value) {
            addCriterion("funcgroupid >=", value, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidLessThan(String value) {
            addCriterion("funcgroupid <", value, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidLessThanOrEqualTo(String value) {
            addCriterion("funcgroupid <=", value, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidLike(String value) {
            addCriterion("funcgroupid like", value, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidNotLike(String value) {
            addCriterion("funcgroupid not like", value, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidIn(List<String> values) {
            addCriterion("funcgroupid in", values, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidNotIn(List<String> values) {
            addCriterion("funcgroupid not in", values, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidBetween(String value1, String value2) {
            addCriterion("funcgroupid between", value1, value2, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupidNotBetween(String value1, String value2) {
            addCriterion("funcgroupid not between", value1, value2, "funcgroupid");
            return (Criteria) this;
        }

        public Criteria andFuncidIsNull() {
            addCriterion("funcid is null");
            return (Criteria) this;
        }

        public Criteria andFuncidIsNotNull() {
            addCriterion("funcid is not null");
            return (Criteria) this;
        }

        public Criteria andFuncidEqualTo(String value) {
            addCriterion("funcid =", value, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidNotEqualTo(String value) {
            addCriterion("funcid <>", value, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidGreaterThan(String value) {
            addCriterion("funcid >", value, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidGreaterThanOrEqualTo(String value) {
            addCriterion("funcid >=", value, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidLessThan(String value) {
            addCriterion("funcid <", value, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidLessThanOrEqualTo(String value) {
            addCriterion("funcid <=", value, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidLike(String value) {
            addCriterion("funcid like", value, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidNotLike(String value) {
            addCriterion("funcid not like", value, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidIn(List<String> values) {
            addCriterion("funcid in", values, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidNotIn(List<String> values) {
            addCriterion("funcid not in", values, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidBetween(String value1, String value2) {
            addCriterion("funcid between", value1, value2, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncidNotBetween(String value1, String value2) {
            addCriterion("funcid not between", value1, value2, "funcid");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameIsNull() {
            addCriterion("funcgroupname is null");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameIsNotNull() {
            addCriterion("funcgroupname is not null");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameEqualTo(String value) {
            addCriterion("funcgroupname =", value, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameNotEqualTo(String value) {
            addCriterion("funcgroupname <>", value, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameGreaterThan(String value) {
            addCriterion("funcgroupname >", value, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameGreaterThanOrEqualTo(String value) {
            addCriterion("funcgroupname >=", value, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameLessThan(String value) {
            addCriterion("funcgroupname <", value, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameLessThanOrEqualTo(String value) {
            addCriterion("funcgroupname <=", value, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameLike(String value) {
            addCriterion("funcgroupname like", value, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameNotLike(String value) {
            addCriterion("funcgroupname not like", value, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameIn(List<String> values) {
            addCriterion("funcgroupname in", values, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameNotIn(List<String> values) {
            addCriterion("funcgroupname not in", values, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameBetween(String value1, String value2) {
            addCriterion("funcgroupname between", value1, value2, "funcgroupname");
            return (Criteria) this;
        }

        public Criteria andFuncgroupnameNotBetween(String value1, String value2) {
            addCriterion("funcgroupname not between", value1, value2, "funcgroupname");
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