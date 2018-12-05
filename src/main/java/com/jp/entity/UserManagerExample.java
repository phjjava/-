package com.jp.entity;

import java.util.ArrayList;
import java.util.List;

public class UserManagerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public UserManagerExample() {
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

        public Criteria andPostidIsNull() {
            addCriterion("postid is null");
            return (Criteria) this;
        }

        public Criteria andPostidIsNotNull() {
            addCriterion("postid is not null");
            return (Criteria) this;
        }

        public Criteria andPostidEqualTo(String value) {
            addCriterion("postid =", value, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidNotEqualTo(String value) {
            addCriterion("postid <>", value, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidGreaterThan(String value) {
            addCriterion("postid >", value, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidGreaterThanOrEqualTo(String value) {
            addCriterion("postid >=", value, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidLessThan(String value) {
            addCriterion("postid <", value, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidLessThanOrEqualTo(String value) {
            addCriterion("postid <=", value, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidLike(String value) {
            addCriterion("postid like", value, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidNotLike(String value) {
            addCriterion("postid not like", value, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidIn(List<String> values) {
            addCriterion("postid in", values, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidNotIn(List<String> values) {
            addCriterion("postid not in", values, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidBetween(String value1, String value2) {
            addCriterion("postid between", value1, value2, "postid");
            return (Criteria) this;
        }

        public Criteria andPostidNotBetween(String value1, String value2) {
            addCriterion("postid not between", value1, value2, "postid");
            return (Criteria) this;
        }

        public Criteria andPostnameIsNull() {
            addCriterion("postname is null");
            return (Criteria) this;
        }

        public Criteria andPostnameIsNotNull() {
            addCriterion("postname is not null");
            return (Criteria) this;
        }

        public Criteria andPostnameEqualTo(String value) {
            addCriterion("postname =", value, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameNotEqualTo(String value) {
            addCriterion("postname <>", value, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameGreaterThan(String value) {
            addCriterion("postname >", value, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameGreaterThanOrEqualTo(String value) {
            addCriterion("postname >=", value, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameLessThan(String value) {
            addCriterion("postname <", value, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameLessThanOrEqualTo(String value) {
            addCriterion("postname <=", value, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameLike(String value) {
            addCriterion("postname like", value, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameNotLike(String value) {
            addCriterion("postname not like", value, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameIn(List<String> values) {
            addCriterion("postname in", values, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameNotIn(List<String> values) {
            addCriterion("postname not in", values, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameBetween(String value1, String value2) {
            addCriterion("postname between", value1, value2, "postname");
            return (Criteria) this;
        }

        public Criteria andPostnameNotBetween(String value1, String value2) {
            addCriterion("postname not between", value1, value2, "postname");
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

        public Criteria andEbnameIsNull() {
            addCriterion("ebname is null");
            return (Criteria) this;
        }

        public Criteria andEbnameIsNotNull() {
            addCriterion("ebname is not null");
            return (Criteria) this;
        }

        public Criteria andEbnameEqualTo(String value) {
            addCriterion("ebname =", value, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameNotEqualTo(String value) {
            addCriterion("ebname <>", value, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameGreaterThan(String value) {
            addCriterion("ebname >", value, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameGreaterThanOrEqualTo(String value) {
            addCriterion("ebname >=", value, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameLessThan(String value) {
            addCriterion("ebname <", value, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameLessThanOrEqualTo(String value) {
            addCriterion("ebname <=", value, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameLike(String value) {
            addCriterion("ebname like", value, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameNotLike(String value) {
            addCriterion("ebname not like", value, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameIn(List<String> values) {
            addCriterion("ebname in", values, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameNotIn(List<String> values) {
            addCriterion("ebname not in", values, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameBetween(String value1, String value2) {
            addCriterion("ebname between", value1, value2, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbnameNotBetween(String value1, String value2) {
            addCriterion("ebname not between", value1, value2, "ebname");
            return (Criteria) this;
        }

        public Criteria andEbtypeIsNull() {
            addCriterion("ebtype is null");
            return (Criteria) this;
        }

        public Criteria andEbtypeIsNotNull() {
            addCriterion("ebtype is not null");
            return (Criteria) this;
        }

        public Criteria andEbtypeEqualTo(Integer value) {
            addCriterion("ebtype =", value, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeNotEqualTo(Integer value) {
            addCriterion("ebtype <>", value, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeGreaterThan(Integer value) {
            addCriterion("ebtype >", value, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ebtype >=", value, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeLessThan(Integer value) {
            addCriterion("ebtype <", value, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeLessThanOrEqualTo(Integer value) {
            addCriterion("ebtype <=", value, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeIn(List<Integer> values) {
            addCriterion("ebtype in", values, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeNotIn(List<Integer> values) {
            addCriterion("ebtype not in", values, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeBetween(Integer value1, Integer value2) {
            addCriterion("ebtype between", value1, value2, "ebtype");
            return (Criteria) this;
        }

        public Criteria andEbtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ebtype not between", value1, value2, "ebtype");
            return (Criteria) this;
        }

        public Criteria andIsmanagerIsNull() {
            addCriterion("ismanager is null");
            return (Criteria) this;
        }

        public Criteria andIsmanagerIsNotNull() {
            addCriterion("ismanager is not null");
            return (Criteria) this;
        }

        public Criteria andIsmanagerEqualTo(Integer value) {
            addCriterion("ismanager =", value, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerNotEqualTo(Integer value) {
            addCriterion("ismanager <>", value, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerGreaterThan(Integer value) {
            addCriterion("ismanager >", value, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerGreaterThanOrEqualTo(Integer value) {
            addCriterion("ismanager >=", value, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerLessThan(Integer value) {
            addCriterion("ismanager <", value, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerLessThanOrEqualTo(Integer value) {
            addCriterion("ismanager <=", value, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerIn(List<Integer> values) {
            addCriterion("ismanager in", values, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerNotIn(List<Integer> values) {
            addCriterion("ismanager not in", values, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerBetween(Integer value1, Integer value2) {
            addCriterion("ismanager between", value1, value2, "ismanager");
            return (Criteria) this;
        }

        public Criteria andIsmanagerNotBetween(Integer value1, Integer value2) {
            addCriterion("ismanager not between", value1, value2, "ismanager");
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