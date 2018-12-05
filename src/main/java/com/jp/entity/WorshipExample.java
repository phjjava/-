package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorshipExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected String fields;

    public WorshipExample() {
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

        public Criteria andOblationidIsNull() {
            addCriterion("oblationid is null");
            return (Criteria) this;
        }

        public Criteria andOblationidIsNotNull() {
            addCriterion("oblationid is not null");
            return (Criteria) this;
        }

        public Criteria andOblationidEqualTo(String value) {
            addCriterion("oblationid =", value, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidNotEqualTo(String value) {
            addCriterion("oblationid <>", value, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidGreaterThan(String value) {
            addCriterion("oblationid >", value, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidGreaterThanOrEqualTo(String value) {
            addCriterion("oblationid >=", value, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidLessThan(String value) {
            addCriterion("oblationid <", value, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidLessThanOrEqualTo(String value) {
            addCriterion("oblationid <=", value, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidLike(String value) {
            addCriterion("oblationid like", value, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidNotLike(String value) {
            addCriterion("oblationid not like", value, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidIn(List<String> values) {
            addCriterion("oblationid in", values, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidNotIn(List<String> values) {
            addCriterion("oblationid not in", values, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidBetween(String value1, String value2) {
            addCriterion("oblationid between", value1, value2, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationidNotBetween(String value1, String value2) {
            addCriterion("oblationid not between", value1, value2, "oblationid");
            return (Criteria) this;
        }

        public Criteria andOblationIsNull() {
            addCriterion("oblation is null");
            return (Criteria) this;
        }

        public Criteria andOblationIsNotNull() {
            addCriterion("oblation is not null");
            return (Criteria) this;
        }

        public Criteria andOblationEqualTo(String value) {
            addCriterion("oblation =", value, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationNotEqualTo(String value) {
            addCriterion("oblation <>", value, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationGreaterThan(String value) {
            addCriterion("oblation >", value, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationGreaterThanOrEqualTo(String value) {
            addCriterion("oblation >=", value, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationLessThan(String value) {
            addCriterion("oblation <", value, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationLessThanOrEqualTo(String value) {
            addCriterion("oblation <=", value, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationLike(String value) {
            addCriterion("oblation like", value, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationNotLike(String value) {
            addCriterion("oblation not like", value, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationIn(List<String> values) {
            addCriterion("oblation in", values, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationNotIn(List<String> values) {
            addCriterion("oblation not in", values, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationBetween(String value1, String value2) {
            addCriterion("oblation between", value1, value2, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationNotBetween(String value1, String value2) {
            addCriterion("oblation not between", value1, value2, "oblation");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidIsNull() {
            addCriterion("oblationtypeid is null");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidIsNotNull() {
            addCriterion("oblationtypeid is not null");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidEqualTo(String value) {
            addCriterion("oblationtypeid =", value, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidNotEqualTo(String value) {
            addCriterion("oblationtypeid <>", value, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidGreaterThan(String value) {
            addCriterion("oblationtypeid >", value, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidGreaterThanOrEqualTo(String value) {
            addCriterion("oblationtypeid >=", value, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidLessThan(String value) {
            addCriterion("oblationtypeid <", value, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidLessThanOrEqualTo(String value) {
            addCriterion("oblationtypeid <=", value, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidLike(String value) {
            addCriterion("oblationtypeid like", value, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidNotLike(String value) {
            addCriterion("oblationtypeid not like", value, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidIn(List<String> values) {
            addCriterion("oblationtypeid in", values, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidNotIn(List<String> values) {
            addCriterion("oblationtypeid not in", values, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidBetween(String value1, String value2) {
            addCriterion("oblationtypeid between", value1, value2, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeidNotBetween(String value1, String value2) {
            addCriterion("oblationtypeid not between", value1, value2, "oblationtypeid");
            return (Criteria) this;
        }

        public Criteria andOblationtypeIsNull() {
            addCriterion("oblationtype is null");
            return (Criteria) this;
        }

        public Criteria andOblationtypeIsNotNull() {
            addCriterion("oblationtype is not null");
            return (Criteria) this;
        }

        public Criteria andOblationtypeEqualTo(String value) {
            addCriterion("oblationtype =", value, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeNotEqualTo(String value) {
            addCriterion("oblationtype <>", value, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeGreaterThan(String value) {
            addCriterion("oblationtype >", value, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeGreaterThanOrEqualTo(String value) {
            addCriterion("oblationtype >=", value, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeLessThan(String value) {
            addCriterion("oblationtype <", value, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeLessThanOrEqualTo(String value) {
            addCriterion("oblationtype <=", value, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeLike(String value) {
            addCriterion("oblationtype like", value, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeNotLike(String value) {
            addCriterion("oblationtype not like", value, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeIn(List<String> values) {
            addCriterion("oblationtype in", values, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeNotIn(List<String> values) {
            addCriterion("oblationtype not in", values, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeBetween(String value1, String value2) {
            addCriterion("oblationtype between", value1, value2, "oblationtype");
            return (Criteria) this;
        }

        public Criteria andOblationtypeNotBetween(String value1, String value2) {
            addCriterion("oblationtype not between", value1, value2, "oblationtype");
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

        public Criteria andCreatenameIsNull() {
            addCriterion("createname is null");
            return (Criteria) this;
        }

        public Criteria andCreatenameIsNotNull() {
            addCriterion("createname is not null");
            return (Criteria) this;
        }

        public Criteria andCreatenameEqualTo(String value) {
            addCriterion("createname =", value, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameNotEqualTo(String value) {
            addCriterion("createname <>", value, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameGreaterThan(String value) {
            addCriterion("createname >", value, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameGreaterThanOrEqualTo(String value) {
            addCriterion("createname >=", value, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameLessThan(String value) {
            addCriterion("createname <", value, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameLessThanOrEqualTo(String value) {
            addCriterion("createname <=", value, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameLike(String value) {
            addCriterion("createname like", value, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameNotLike(String value) {
            addCriterion("createname not like", value, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameIn(List<String> values) {
            addCriterion("createname in", values, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameNotIn(List<String> values) {
            addCriterion("createname not in", values, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameBetween(String value1, String value2) {
            addCriterion("createname between", value1, value2, "createname");
            return (Criteria) this;
        }

        public Criteria andCreatenameNotBetween(String value1, String value2) {
            addCriterion("createname not between", value1, value2, "createname");
            return (Criteria) this;
        }

        public Criteria andWorshipidIsNull() {
            addCriterion("worshipid is null");
            return (Criteria) this;
        }

        public Criteria andWorshipidIsNotNull() {
            addCriterion("worshipid is not null");
            return (Criteria) this;
        }

        public Criteria andWorshipidEqualTo(String value) {
            addCriterion("worshipid =", value, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidNotEqualTo(String value) {
            addCriterion("worshipid <>", value, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidGreaterThan(String value) {
            addCriterion("worshipid >", value, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidGreaterThanOrEqualTo(String value) {
            addCriterion("worshipid >=", value, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidLessThan(String value) {
            addCriterion("worshipid <", value, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidLessThanOrEqualTo(String value) {
            addCriterion("worshipid <=", value, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidLike(String value) {
            addCriterion("worshipid like", value, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidNotLike(String value) {
            addCriterion("worshipid not like", value, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidIn(List<String> values) {
            addCriterion("worshipid in", values, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidNotIn(List<String> values) {
            addCriterion("worshipid not in", values, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidBetween(String value1, String value2) {
            addCriterion("worshipid between", value1, value2, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipidNotBetween(String value1, String value2) {
            addCriterion("worshipid not between", value1, value2, "worshipid");
            return (Criteria) this;
        }

        public Criteria andWorshipnameIsNull() {
            addCriterion("worshipname is null");
            return (Criteria) this;
        }

        public Criteria andWorshipnameIsNotNull() {
            addCriterion("worshipname is not null");
            return (Criteria) this;
        }

        public Criteria andWorshipnameEqualTo(String value) {
            addCriterion("worshipname =", value, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameNotEqualTo(String value) {
            addCriterion("worshipname <>", value, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameGreaterThan(String value) {
            addCriterion("worshipname >", value, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameGreaterThanOrEqualTo(String value) {
            addCriterion("worshipname >=", value, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameLessThan(String value) {
            addCriterion("worshipname <", value, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameLessThanOrEqualTo(String value) {
            addCriterion("worshipname <=", value, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameLike(String value) {
            addCriterion("worshipname like", value, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameNotLike(String value) {
            addCriterion("worshipname not like", value, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameIn(List<String> values) {
            addCriterion("worshipname in", values, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameNotIn(List<String> values) {
            addCriterion("worshipname not in", values, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameBetween(String value1, String value2) {
            addCriterion("worshipname between", value1, value2, "worshipname");
            return (Criteria) this;
        }

        public Criteria andWorshipnameNotBetween(String value1, String value2) {
            addCriterion("worshipname not between", value1, value2, "worshipname");
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