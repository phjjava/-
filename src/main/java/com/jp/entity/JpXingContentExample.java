package com.jp.entity;

import java.util.ArrayList;
import java.util.List;

public class JpXingContentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JpXingContentExample() {
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

        public Criteria andXingidIsNull() {
            addCriterion("xingid is null");
            return (Criteria) this;
        }

        public Criteria andXingidIsNotNull() {
            addCriterion("xingid is not null");
            return (Criteria) this;
        }

        public Criteria andXingidEqualTo(String value) {
            addCriterion("xingid =", value, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidNotEqualTo(String value) {
            addCriterion("xingid <>", value, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidGreaterThan(String value) {
            addCriterion("xingid >", value, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidGreaterThanOrEqualTo(String value) {
            addCriterion("xingid >=", value, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidLessThan(String value) {
            addCriterion("xingid <", value, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidLessThanOrEqualTo(String value) {
            addCriterion("xingid <=", value, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidLike(String value) {
            addCriterion("xingid like", value, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidNotLike(String value) {
            addCriterion("xingid not like", value, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidIn(List<String> values) {
            addCriterion("xingid in", values, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidNotIn(List<String> values) {
            addCriterion("xingid not in", values, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidBetween(String value1, String value2) {
            addCriterion("xingid between", value1, value2, "xingid");
            return (Criteria) this;
        }

        public Criteria andXingidNotBetween(String value1, String value2) {
            addCriterion("xingid not between", value1, value2, "xingid");
            return (Criteria) this;
        }

        public Criteria andDiccodeIsNull() {
            addCriterion("diccode is null");
            return (Criteria) this;
        }

        public Criteria andDiccodeIsNotNull() {
            addCriterion("diccode is not null");
            return (Criteria) this;
        }

        public Criteria andDiccodeEqualTo(String value) {
            addCriterion("diccode =", value, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeNotEqualTo(String value) {
            addCriterion("diccode <>", value, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeGreaterThan(String value) {
            addCriterion("diccode >", value, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeGreaterThanOrEqualTo(String value) {
            addCriterion("diccode >=", value, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeLessThan(String value) {
            addCriterion("diccode <", value, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeLessThanOrEqualTo(String value) {
            addCriterion("diccode <=", value, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeLike(String value) {
            addCriterion("diccode like", value, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeNotLike(String value) {
            addCriterion("diccode not like", value, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeIn(List<String> values) {
            addCriterion("diccode in", values, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeNotIn(List<String> values) {
            addCriterion("diccode not in", values, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeBetween(String value1, String value2) {
            addCriterion("diccode between", value1, value2, "diccode");
            return (Criteria) this;
        }

        public Criteria andDiccodeNotBetween(String value1, String value2) {
            addCriterion("diccode not between", value1, value2, "diccode");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
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