package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FamousPersonExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FamousPersonExample() {
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

        public Criteria andFamousidIsNull() {
            addCriterion("famousid is null");
            return (Criteria) this;
        }

        public Criteria andFamousidIsNotNull() {
            addCriterion("famousid is not null");
            return (Criteria) this;
        }

        public Criteria andFamousidEqualTo(String value) {
            addCriterion("famousid =", value, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidNotEqualTo(String value) {
            addCriterion("famousid <>", value, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidGreaterThan(String value) {
            addCriterion("famousid >", value, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidGreaterThanOrEqualTo(String value) {
            addCriterion("famousid >=", value, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidLessThan(String value) {
            addCriterion("famousid <", value, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidLessThanOrEqualTo(String value) {
            addCriterion("famousid <=", value, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidLike(String value) {
            addCriterion("famousid like", value, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidNotLike(String value) {
            addCriterion("famousid not like", value, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidIn(List<String> values) {
            addCriterion("famousid in", values, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidNotIn(List<String> values) {
            addCriterion("famousid not in", values, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidBetween(String value1, String value2) {
            addCriterion("famousid between", value1, value2, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamousidNotBetween(String value1, String value2) {
            addCriterion("famousid not between", value1, value2, "famousid");
            return (Criteria) this;
        }

        public Criteria andFamoustitleIsNull() {
            addCriterion("famoustitle is null");
            return (Criteria) this;
        }

        public Criteria andFamoustitleIsNotNull() {
            addCriterion("famoustitle is not null");
            return (Criteria) this;
        }

        public Criteria andFamoustitleEqualTo(String value) {
            addCriterion("famoustitle =", value, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleNotEqualTo(String value) {
            addCriterion("famoustitle <>", value, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleGreaterThan(String value) {
            addCriterion("famoustitle >", value, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleGreaterThanOrEqualTo(String value) {
            addCriterion("famoustitle >=", value, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleLessThan(String value) {
            addCriterion("famoustitle <", value, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleLessThanOrEqualTo(String value) {
            addCriterion("famoustitle <=", value, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleLike(String value) {
            addCriterion("famoustitle like", value, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleNotLike(String value) {
            addCriterion("famoustitle not like", value, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleIn(List<String> values) {
            addCriterion("famoustitle in", values, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleNotIn(List<String> values) {
            addCriterion("famoustitle not in", values, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleBetween(String value1, String value2) {
            addCriterion("famoustitle between", value1, value2, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamoustitleNotBetween(String value1, String value2) {
            addCriterion("famoustitle not between", value1, value2, "famoustitle");
            return (Criteria) this;
        }

        public Criteria andFamouscontentIsNull() {
            addCriterion("famouscontent is null");
            return (Criteria) this;
        }

        public Criteria andFamouscontentIsNotNull() {
            addCriterion("famouscontent is not null");
            return (Criteria) this;
        }

        public Criteria andFamouscontentEqualTo(String value) {
            addCriterion("famouscontent =", value, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentNotEqualTo(String value) {
            addCriterion("famouscontent <>", value, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentGreaterThan(String value) {
            addCriterion("famouscontent >", value, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentGreaterThanOrEqualTo(String value) {
            addCriterion("famouscontent >=", value, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentLessThan(String value) {
            addCriterion("famouscontent <", value, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentLessThanOrEqualTo(String value) {
            addCriterion("famouscontent <=", value, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentLike(String value) {
            addCriterion("famouscontent like", value, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentNotLike(String value) {
            addCriterion("famouscontent not like", value, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentIn(List<String> values) {
            addCriterion("famouscontent in", values, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentNotIn(List<String> values) {
            addCriterion("famouscontent not in", values, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentBetween(String value1, String value2) {
            addCriterion("famouscontent between", value1, value2, "famouscontent");
            return (Criteria) this;
        }

        public Criteria andFamouscontentNotBetween(String value1, String value2) {
            addCriterion("famouscontent not between", value1, value2, "famouscontent");
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

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
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