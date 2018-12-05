package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserworkexpQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public UserworkexpQuery() {
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

        public Criteria andWorkidIsNull() {
            addCriterion("workid is null");
            return (Criteria) this;
        }

        public Criteria andWorkidIsNotNull() {
            addCriterion("workid is not null");
            return (Criteria) this;
        }

        public Criteria andWorkidEqualTo(String value) {
            addCriterion("workid =", value, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidNotEqualTo(String value) {
            addCriterion("workid <>", value, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidGreaterThan(String value) {
            addCriterion("workid >", value, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidGreaterThanOrEqualTo(String value) {
            addCriterion("workid >=", value, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidLessThan(String value) {
            addCriterion("workid <", value, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidLessThanOrEqualTo(String value) {
            addCriterion("workid <=", value, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidLike(String value) {
            addCriterion("workid like", value, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidNotLike(String value) {
            addCriterion("workid not like", value, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidIn(List<String> values) {
            addCriterion("workid in", values, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidNotIn(List<String> values) {
            addCriterion("workid not in", values, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidBetween(String value1, String value2) {
            addCriterion("workid between", value1, value2, "workid");
            return (Criteria) this;
        }

        public Criteria andWorkidNotBetween(String value1, String value2) {
            addCriterion("workid not between", value1, value2, "workid");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andPositionIsNull() {
            addCriterion("position is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("position is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(String value) {
            addCriterion("position =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(String value) {
            addCriterion("position <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(String value) {
            addCriterion("position >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(String value) {
            addCriterion("position >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(String value) {
            addCriterion("position <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(String value) {
            addCriterion("position <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLike(String value) {
            addCriterion("position like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotLike(String value) {
            addCriterion("position not like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<String> values) {
            addCriterion("position in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<String> values) {
            addCriterion("position not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(String value1, String value2) {
            addCriterion("position between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(String value1, String value2) {
            addCriterion("position not between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andDatefromIsNull() {
            addCriterion("datefrom is null");
            return (Criteria) this;
        }

        public Criteria andDatefromIsNotNull() {
            addCriterion("datefrom is not null");
            return (Criteria) this;
        }

        public Criteria andDatefromEqualTo(Date value) {
            addCriterion("datefrom =", value, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromNotEqualTo(Date value) {
            addCriterion("datefrom <>", value, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromGreaterThan(Date value) {
            addCriterion("datefrom >", value, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromGreaterThanOrEqualTo(Date value) {
            addCriterion("datefrom >=", value, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromLessThan(Date value) {
            addCriterion("datefrom <", value, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromLessThanOrEqualTo(Date value) {
            addCriterion("datefrom <=", value, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromIn(List<Date> values) {
            addCriterion("datefrom in", values, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromNotIn(List<Date> values) {
            addCriterion("datefrom not in", values, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromBetween(Date value1, Date value2) {
            addCriterion("datefrom between", value1, value2, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatefromNotBetween(Date value1, Date value2) {
            addCriterion("datefrom not between", value1, value2, "datefrom");
            return (Criteria) this;
        }

        public Criteria andDatetoIsNull() {
            addCriterion("dateto is null");
            return (Criteria) this;
        }

        public Criteria andDatetoIsNotNull() {
            addCriterion("dateto is not null");
            return (Criteria) this;
        }

        public Criteria andDatetoEqualTo(Date value) {
            addCriterion("dateto =", value, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoNotEqualTo(Date value) {
            addCriterion("dateto <>", value, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoGreaterThan(Date value) {
            addCriterion("dateto >", value, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoGreaterThanOrEqualTo(Date value) {
            addCriterion("dateto >=", value, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoLessThan(Date value) {
            addCriterion("dateto <", value, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoLessThanOrEqualTo(Date value) {
            addCriterion("dateto <=", value, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoIn(List<Date> values) {
            addCriterion("dateto in", values, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoNotIn(List<Date> values) {
            addCriterion("dateto not in", values, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoBetween(Date value1, Date value2) {
            addCriterion("dateto between", value1, value2, "dateto");
            return (Criteria) this;
        }

        public Criteria andDatetoNotBetween(Date value1, Date value2) {
            addCriterion("dateto not between", value1, value2, "dateto");
            return (Criteria) this;
        }

        public Criteria andIssecretIsNull() {
            addCriterion("issecret is null");
            return (Criteria) this;
        }

        public Criteria andIssecretIsNotNull() {
            addCriterion("issecret is not null");
            return (Criteria) this;
        }

        public Criteria andIssecretEqualTo(Integer value) {
            addCriterion("issecret =", value, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretNotEqualTo(Integer value) {
            addCriterion("issecret <>", value, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretGreaterThan(Integer value) {
            addCriterion("issecret >", value, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretGreaterThanOrEqualTo(Integer value) {
            addCriterion("issecret >=", value, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretLessThan(Integer value) {
            addCriterion("issecret <", value, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretLessThanOrEqualTo(Integer value) {
            addCriterion("issecret <=", value, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretIn(List<Integer> values) {
            addCriterion("issecret in", values, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretNotIn(List<Integer> values) {
            addCriterion("issecret not in", values, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretBetween(Integer value1, Integer value2) {
            addCriterion("issecret between", value1, value2, "issecret");
            return (Criteria) this;
        }

        public Criteria andIssecretNotBetween(Integer value1, Integer value2) {
            addCriterion("issecret not between", value1, value2, "issecret");
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