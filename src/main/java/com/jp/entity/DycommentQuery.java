package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DycommentQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public DycommentQuery() {
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

        public Criteria andCommentidIsNull() {
            addCriterion("commentid is null");
            return (Criteria) this;
        }

        public Criteria andCommentidIsNotNull() {
            addCriterion("commentid is not null");
            return (Criteria) this;
        }

        public Criteria andCommentidEqualTo(String value) {
            addCriterion("commentid =", value, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidNotEqualTo(String value) {
            addCriterion("commentid <>", value, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidGreaterThan(String value) {
            addCriterion("commentid >", value, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidGreaterThanOrEqualTo(String value) {
            addCriterion("commentid >=", value, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidLessThan(String value) {
            addCriterion("commentid <", value, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidLessThanOrEqualTo(String value) {
            addCriterion("commentid <=", value, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidLike(String value) {
            addCriterion("commentid like", value, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidNotLike(String value) {
            addCriterion("commentid not like", value, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidIn(List<String> values) {
            addCriterion("commentid in", values, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidNotIn(List<String> values) {
            addCriterion("commentid not in", values, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidBetween(String value1, String value2) {
            addCriterion("commentid between", value1, value2, "commentid");
            return (Criteria) this;
        }

        public Criteria andCommentidNotBetween(String value1, String value2) {
            addCriterion("commentid not between", value1, value2, "commentid");
            return (Criteria) this;
        }

        public Criteria andDyidIsNull() {
            addCriterion("dyid is null");
            return (Criteria) this;
        }

        public Criteria andDyidIsNotNull() {
            addCriterion("dyid is not null");
            return (Criteria) this;
        }

        public Criteria andDyidEqualTo(String value) {
            addCriterion("dyid =", value, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidNotEqualTo(String value) {
            addCriterion("dyid <>", value, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidGreaterThan(String value) {
            addCriterion("dyid >", value, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidGreaterThanOrEqualTo(String value) {
            addCriterion("dyid >=", value, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidLessThan(String value) {
            addCriterion("dyid <", value, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidLessThanOrEqualTo(String value) {
            addCriterion("dyid <=", value, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidLike(String value) {
            addCriterion("dyid like", value, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidNotLike(String value) {
            addCriterion("dyid not like", value, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidIn(List<String> values) {
            addCriterion("dyid in", values, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidNotIn(List<String> values) {
            addCriterion("dyid not in", values, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidBetween(String value1, String value2) {
            addCriterion("dyid between", value1, value2, "dyid");
            return (Criteria) this;
        }

        public Criteria andDyidNotBetween(String value1, String value2) {
            addCriterion("dyid not between", value1, value2, "dyid");
            return (Criteria) this;
        }

        public Criteria andBranchidIsNull() {
            addCriterion("branchid is null");
            return (Criteria) this;
        }

        public Criteria andBranchidIsNotNull() {
            addCriterion("branchid is not null");
            return (Criteria) this;
        }

        public Criteria andBranchidEqualTo(String value) {
            addCriterion("branchid =", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotEqualTo(String value) {
            addCriterion("branchid <>", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidGreaterThan(String value) {
            addCriterion("branchid >", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidGreaterThanOrEqualTo(String value) {
            addCriterion("branchid >=", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLessThan(String value) {
            addCriterion("branchid <", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLessThanOrEqualTo(String value) {
            addCriterion("branchid <=", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidLike(String value) {
            addCriterion("branchid like", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotLike(String value) {
            addCriterion("branchid not like", value, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidIn(List<String> values) {
            addCriterion("branchid in", values, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotIn(List<String> values) {
            addCriterion("branchid not in", values, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidBetween(String value1, String value2) {
            addCriterion("branchid between", value1, value2, "branchid");
            return (Criteria) this;
        }

        public Criteria andBranchidNotBetween(String value1, String value2) {
            addCriterion("branchid not between", value1, value2, "branchid");
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

        public Criteria andImgurlIsNull() {
            addCriterion("imgurl is null");
            return (Criteria) this;
        }

        public Criteria andImgurlIsNotNull() {
            addCriterion("imgurl is not null");
            return (Criteria) this;
        }

        public Criteria andImgurlEqualTo(String value) {
            addCriterion("imgurl =", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlNotEqualTo(String value) {
            addCriterion("imgurl <>", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlGreaterThan(String value) {
            addCriterion("imgurl >", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlGreaterThanOrEqualTo(String value) {
            addCriterion("imgurl >=", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlLessThan(String value) {
            addCriterion("imgurl <", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlLessThanOrEqualTo(String value) {
            addCriterion("imgurl <=", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlLike(String value) {
            addCriterion("imgurl like", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlNotLike(String value) {
            addCriterion("imgurl not like", value, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlIn(List<String> values) {
            addCriterion("imgurl in", values, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlNotIn(List<String> values) {
            addCriterion("imgurl not in", values, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlBetween(String value1, String value2) {
            addCriterion("imgurl between", value1, value2, "imgurl");
            return (Criteria) this;
        }

        public Criteria andImgurlNotBetween(String value1, String value2) {
            addCriterion("imgurl not between", value1, value2, "imgurl");
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

        public Criteria andIsreplyIsNull() {
            addCriterion("isreply is null");
            return (Criteria) this;
        }

        public Criteria andIsreplyIsNotNull() {
            addCriterion("isreply is not null");
            return (Criteria) this;
        }

        public Criteria andIsreplyEqualTo(String value) {
            addCriterion("isreply =", value, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyNotEqualTo(String value) {
            addCriterion("isreply <>", value, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyGreaterThan(String value) {
            addCriterion("isreply >", value, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyGreaterThanOrEqualTo(String value) {
            addCriterion("isreply >=", value, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyLessThan(String value) {
            addCriterion("isreply <", value, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyLessThanOrEqualTo(String value) {
            addCriterion("isreply <=", value, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyLike(String value) {
            addCriterion("isreply like", value, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyNotLike(String value) {
            addCriterion("isreply not like", value, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyIn(List<String> values) {
            addCriterion("isreply in", values, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyNotIn(List<String> values) {
            addCriterion("isreply not in", values, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyBetween(String value1, String value2) {
            addCriterion("isreply between", value1, value2, "isreply");
            return (Criteria) this;
        }

        public Criteria andIsreplyNotBetween(String value1, String value2) {
            addCriterion("isreply not between", value1, value2, "isreply");
            return (Criteria) this;
        }

        public Criteria andTopparentidIsNull() {
            addCriterion("topparentid is null");
            return (Criteria) this;
        }

        public Criteria andTopparentidIsNotNull() {
            addCriterion("topparentid is not null");
            return (Criteria) this;
        }

        public Criteria andTopparentidEqualTo(String value) {
            addCriterion("topparentid =", value, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidNotEqualTo(String value) {
            addCriterion("topparentid <>", value, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidGreaterThan(String value) {
            addCriterion("topparentid >", value, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidGreaterThanOrEqualTo(String value) {
            addCriterion("topparentid >=", value, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidLessThan(String value) {
            addCriterion("topparentid <", value, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidLessThanOrEqualTo(String value) {
            addCriterion("topparentid <=", value, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidLike(String value) {
            addCriterion("topparentid like", value, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidNotLike(String value) {
            addCriterion("topparentid not like", value, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidIn(List<String> values) {
            addCriterion("topparentid in", values, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidNotIn(List<String> values) {
            addCriterion("topparentid not in", values, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidBetween(String value1, String value2) {
            addCriterion("topparentid between", value1, value2, "topparentid");
            return (Criteria) this;
        }

        public Criteria andTopparentidNotBetween(String value1, String value2) {
            addCriterion("topparentid not between", value1, value2, "topparentid");
            return (Criteria) this;
        }

        public Criteria andReluseridIsNull() {
            addCriterion("reluserid is null");
            return (Criteria) this;
        }

        public Criteria andReluseridIsNotNull() {
            addCriterion("reluserid is not null");
            return (Criteria) this;
        }

        public Criteria andReluseridEqualTo(String value) {
            addCriterion("reluserid =", value, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridNotEqualTo(String value) {
            addCriterion("reluserid <>", value, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridGreaterThan(String value) {
            addCriterion("reluserid >", value, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridGreaterThanOrEqualTo(String value) {
            addCriterion("reluserid >=", value, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridLessThan(String value) {
            addCriterion("reluserid <", value, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridLessThanOrEqualTo(String value) {
            addCriterion("reluserid <=", value, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridLike(String value) {
            addCriterion("reluserid like", value, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridNotLike(String value) {
            addCriterion("reluserid not like", value, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridIn(List<String> values) {
            addCriterion("reluserid in", values, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridNotIn(List<String> values) {
            addCriterion("reluserid not in", values, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridBetween(String value1, String value2) {
            addCriterion("reluserid between", value1, value2, "reluserid");
            return (Criteria) this;
        }

        public Criteria andReluseridNotBetween(String value1, String value2) {
            addCriterion("reluserid not between", value1, value2, "reluserid");
            return (Criteria) this;
        }

        public Criteria andRelusernameIsNull() {
            addCriterion("relusername is null");
            return (Criteria) this;
        }

        public Criteria andRelusernameIsNotNull() {
            addCriterion("relusername is not null");
            return (Criteria) this;
        }

        public Criteria andRelusernameEqualTo(String value) {
            addCriterion("relusername =", value, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameNotEqualTo(String value) {
            addCriterion("relusername <>", value, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameGreaterThan(String value) {
            addCriterion("relusername >", value, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameGreaterThanOrEqualTo(String value) {
            addCriterion("relusername >=", value, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameLessThan(String value) {
            addCriterion("relusername <", value, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameLessThanOrEqualTo(String value) {
            addCriterion("relusername <=", value, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameLike(String value) {
            addCriterion("relusername like", value, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameNotLike(String value) {
            addCriterion("relusername not like", value, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameIn(List<String> values) {
            addCriterion("relusername in", values, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameNotIn(List<String> values) {
            addCriterion("relusername not in", values, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameBetween(String value1, String value2) {
            addCriterion("relusername between", value1, value2, "relusername");
            return (Criteria) this;
        }

        public Criteria andRelusernameNotBetween(String value1, String value2) {
            addCriterion("relusername not between", value1, value2, "relusername");
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