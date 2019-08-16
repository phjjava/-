package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JpMemberRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JpMemberRecordExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRecordidIsNull() {
            addCriterion("recordid is null");
            return (Criteria) this;
        }

        public Criteria andRecordidIsNotNull() {
            addCriterion("recordid is not null");
            return (Criteria) this;
        }

        public Criteria andRecordidEqualTo(String value) {
            addCriterion("recordid =", value, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidNotEqualTo(String value) {
            addCriterion("recordid <>", value, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidGreaterThan(String value) {
            addCriterion("recordid >", value, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidGreaterThanOrEqualTo(String value) {
            addCriterion("recordid >=", value, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidLessThan(String value) {
            addCriterion("recordid <", value, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidLessThanOrEqualTo(String value) {
            addCriterion("recordid <=", value, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidLike(String value) {
            addCriterion("recordid like", value, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidNotLike(String value) {
            addCriterion("recordid not like", value, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidIn(List<String> values) {
            addCriterion("recordid in", values, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidNotIn(List<String> values) {
            addCriterion("recordid not in", values, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidBetween(String value1, String value2) {
            addCriterion("recordid between", value1, value2, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordidNotBetween(String value1, String value2) {
            addCriterion("recordid not between", value1, value2, "recordid");
            return (Criteria) this;
        }

        public Criteria andRecordnameIsNull() {
            addCriterion("recordname is null");
            return (Criteria) this;
        }

        public Criteria andRecordnameIsNotNull() {
            addCriterion("recordname is not null");
            return (Criteria) this;
        }

        public Criteria andRecordnameEqualTo(String value) {
            addCriterion("recordname =", value, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameNotEqualTo(String value) {
            addCriterion("recordname <>", value, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameGreaterThan(String value) {
            addCriterion("recordname >", value, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameGreaterThanOrEqualTo(String value) {
            addCriterion("recordname >=", value, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameLessThan(String value) {
            addCriterion("recordname <", value, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameLessThanOrEqualTo(String value) {
            addCriterion("recordname <=", value, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameLike(String value) {
            addCriterion("recordname like", value, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameNotLike(String value) {
            addCriterion("recordname not like", value, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameIn(List<String> values) {
            addCriterion("recordname in", values, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameNotIn(List<String> values) {
            addCriterion("recordname not in", values, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameBetween(String value1, String value2) {
            addCriterion("recordname between", value1, value2, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordnameNotBetween(String value1, String value2) {
            addCriterion("recordname not between", value1, value2, "recordname");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyIsNull() {
            addCriterion("recordmoney is null");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyIsNotNull() {
            addCriterion("recordmoney is not null");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyEqualTo(String value) {
            addCriterion("recordmoney =", value, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyNotEqualTo(String value) {
            addCriterion("recordmoney <>", value, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyGreaterThan(String value) {
            addCriterion("recordmoney >", value, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyGreaterThanOrEqualTo(String value) {
            addCriterion("recordmoney >=", value, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyLessThan(String value) {
            addCriterion("recordmoney <", value, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyLessThanOrEqualTo(String value) {
            addCriterion("recordmoney <=", value, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyLike(String value) {
            addCriterion("recordmoney like", value, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyNotLike(String value) {
            addCriterion("recordmoney not like", value, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyIn(List<String> values) {
            addCriterion("recordmoney in", values, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyNotIn(List<String> values) {
            addCriterion("recordmoney not in", values, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyBetween(String value1, String value2) {
            addCriterion("recordmoney between", value1, value2, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordmoneyNotBetween(String value1, String value2) {
            addCriterion("recordmoney not between", value1, value2, "recordmoney");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIsNull() {
            addCriterion("recordtime is null");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIsNotNull() {
            addCriterion("recordtime is not null");
            return (Criteria) this;
        }

        public Criteria andRecordtimeEqualTo(Date value) {
            addCriterion("recordtime =", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotEqualTo(Date value) {
            addCriterion("recordtime <>", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeGreaterThan(Date value) {
            addCriterion("recordtime >", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("recordtime >=", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeLessThan(Date value) {
            addCriterion("recordtime <", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeLessThanOrEqualTo(Date value) {
            addCriterion("recordtime <=", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIn(List<Date> values) {
            addCriterion("recordtime in", values, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotIn(List<Date> values) {
            addCriterion("recordtime not in", values, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeBetween(Date value1, Date value2) {
            addCriterion("recordtime between", value1, value2, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotBetween(Date value1, Date value2) {
            addCriterion("recordtime not between", value1, value2, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordstatusIsNull() {
            addCriterion("recordstatus is null");
            return (Criteria) this;
        }

        public Criteria andRecordstatusIsNotNull() {
            addCriterion("recordstatus is not null");
            return (Criteria) this;
        }

        public Criteria andRecordstatusEqualTo(Integer value) {
            addCriterion("recordstatus =", value, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusNotEqualTo(Integer value) {
            addCriterion("recordstatus <>", value, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusGreaterThan(Integer value) {
            addCriterion("recordstatus >", value, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("recordstatus >=", value, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusLessThan(Integer value) {
            addCriterion("recordstatus <", value, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusLessThanOrEqualTo(Integer value) {
            addCriterion("recordstatus <=", value, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusIn(List<Integer> values) {
            addCriterion("recordstatus in", values, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusNotIn(List<Integer> values) {
            addCriterion("recordstatus not in", values, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusBetween(Integer value1, Integer value2) {
            addCriterion("recordstatus between", value1, value2, "recordstatus");
            return (Criteria) this;
        }

        public Criteria andRecordstatusNotBetween(Integer value1, Integer value2) {
            addCriterion("recordstatus not between", value1, value2, "recordstatus");
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