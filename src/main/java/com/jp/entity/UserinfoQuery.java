package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UserinfoQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public UserinfoQuery() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterionForJDBCDate("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andBackgroundIsNull() {
            addCriterion("background is null");
            return (Criteria) this;
        }

        public Criteria andBackgroundIsNotNull() {
            addCriterion("background is not null");
            return (Criteria) this;
        }

        public Criteria andBackgroundEqualTo(String value) {
            addCriterion("background =", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundNotEqualTo(String value) {
            addCriterion("background <>", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundGreaterThan(String value) {
            addCriterion("background >", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundGreaterThanOrEqualTo(String value) {
            addCriterion("background >=", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundLessThan(String value) {
            addCriterion("background <", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundLessThanOrEqualTo(String value) {
            addCriterion("background <=", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundLike(String value) {
            addCriterion("background like", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundNotLike(String value) {
            addCriterion("background not like", value, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundIn(List<String> values) {
            addCriterion("background in", values, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundNotIn(List<String> values) {
            addCriterion("background not in", values, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundBetween(String value1, String value2) {
            addCriterion("background between", value1, value2, "background");
            return (Criteria) this;
        }

        public Criteria andBackgroundNotBetween(String value1, String value2) {
            addCriterion("background not between", value1, value2, "background");
            return (Criteria) this;
        }

        public Criteria andBirthplaceIsNull() {
            addCriterion("birthplace is null");
            return (Criteria) this;
        }

        public Criteria andBirthplaceIsNotNull() {
            addCriterion("birthplace is not null");
            return (Criteria) this;
        }

        public Criteria andBirthplaceEqualTo(String value) {
            addCriterion("birthplace =", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceNotEqualTo(String value) {
            addCriterion("birthplace <>", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceGreaterThan(String value) {
            addCriterion("birthplace >", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceGreaterThanOrEqualTo(String value) {
            addCriterion("birthplace >=", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceLessThan(String value) {
            addCriterion("birthplace <", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceLessThanOrEqualTo(String value) {
            addCriterion("birthplace <=", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceLike(String value) {
            addCriterion("birthplace like", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceNotLike(String value) {
            addCriterion("birthplace not like", value, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceIn(List<String> values) {
            addCriterion("birthplace in", values, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceNotIn(List<String> values) {
            addCriterion("birthplace not in", values, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceBetween(String value1, String value2) {
            addCriterion("birthplace between", value1, value2, "birthplace");
            return (Criteria) this;
        }

        public Criteria andBirthplaceNotBetween(String value1, String value2) {
            addCriterion("birthplace not between", value1, value2, "birthplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceIsNull() {
            addCriterion("homeplace is null");
            return (Criteria) this;
        }

        public Criteria andHomeplaceIsNotNull() {
            addCriterion("homeplace is not null");
            return (Criteria) this;
        }

        public Criteria andHomeplaceEqualTo(String value) {
            addCriterion("homeplace =", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceNotEqualTo(String value) {
            addCriterion("homeplace <>", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceGreaterThan(String value) {
            addCriterion("homeplace >", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceGreaterThanOrEqualTo(String value) {
            addCriterion("homeplace >=", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceLessThan(String value) {
            addCriterion("homeplace <", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceLessThanOrEqualTo(String value) {
            addCriterion("homeplace <=", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceLike(String value) {
            addCriterion("homeplace like", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceNotLike(String value) {
            addCriterion("homeplace not like", value, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceIn(List<String> values) {
            addCriterion("homeplace in", values, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceNotIn(List<String> values) {
            addCriterion("homeplace not in", values, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceBetween(String value1, String value2) {
            addCriterion("homeplace between", value1, value2, "homeplace");
            return (Criteria) this;
        }

        public Criteria andHomeplaceNotBetween(String value1, String value2) {
            addCriterion("homeplace not between", value1, value2, "homeplace");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andQqIsNull() {
            addCriterion("QQ is null");
            return (Criteria) this;
        }

        public Criteria andQqIsNotNull() {
            addCriterion("QQ is not null");
            return (Criteria) this;
        }

        public Criteria andQqEqualTo(String value) {
            addCriterion("QQ =", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotEqualTo(String value) {
            addCriterion("QQ <>", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThan(String value) {
            addCriterion("QQ >", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThanOrEqualTo(String value) {
            addCriterion("QQ >=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThan(String value) {
            addCriterion("QQ <", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThanOrEqualTo(String value) {
            addCriterion("QQ <=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLike(String value) {
            addCriterion("QQ like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotLike(String value) {
            addCriterion("QQ not like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqIn(List<String> values) {
            addCriterion("QQ in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotIn(List<String> values) {
            addCriterion("QQ not in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqBetween(String value1, String value2) {
            addCriterion("QQ between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotBetween(String value1, String value2) {
            addCriterion("QQ not between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNull() {
            addCriterion("weixin is null");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNotNull() {
            addCriterion("weixin is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinEqualTo(String value) {
            addCriterion("weixin =", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotEqualTo(String value) {
            addCriterion("weixin <>", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThan(String value) {
            addCriterion("weixin >", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThanOrEqualTo(String value) {
            addCriterion("weixin >=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThan(String value) {
            addCriterion("weixin <", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThanOrEqualTo(String value) {
            addCriterion("weixin <=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLike(String value) {
            addCriterion("weixin like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotLike(String value) {
            addCriterion("weixin not like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinIn(List<String> values) {
            addCriterion("weixin in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotIn(List<String> values) {
            addCriterion("weixin not in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinBetween(String value1, String value2) {
            addCriterion("weixin between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotBetween(String value1, String value2) {
            addCriterion("weixin not between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andMailIsNull() {
            addCriterion("mail is null");
            return (Criteria) this;
        }

        public Criteria andMailIsNotNull() {
            addCriterion("mail is not null");
            return (Criteria) this;
        }

        public Criteria andMailEqualTo(String value) {
            addCriterion("mail =", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotEqualTo(String value) {
            addCriterion("mail <>", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailGreaterThan(String value) {
            addCriterion("mail >", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailGreaterThanOrEqualTo(String value) {
            addCriterion("mail >=", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLessThan(String value) {
            addCriterion("mail <", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLessThanOrEqualTo(String value) {
            addCriterion("mail <=", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLike(String value) {
            addCriterion("mail like", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotLike(String value) {
            addCriterion("mail not like", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailIn(List<String> values) {
            addCriterion("mail in", values, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotIn(List<String> values) {
            addCriterion("mail not in", values, "mail");
            return (Criteria) this;
        }

        public Criteria andMailBetween(String value1, String value2) {
            addCriterion("mail between", value1, value2, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotBetween(String value1, String value2) {
            addCriterion("mail not between", value1, value2, "mail");
            return (Criteria) this;
        }

        public Criteria andTelIsNull() {
            addCriterion("tel is null");
            return (Criteria) this;
        }

        public Criteria andTelIsNotNull() {
            addCriterion("tel is not null");
            return (Criteria) this;
        }

        public Criteria andTelEqualTo(String value) {
            addCriterion("tel =", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotEqualTo(String value) {
            addCriterion("tel <>", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThan(String value) {
            addCriterion("tel >", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThanOrEqualTo(String value) {
            addCriterion("tel >=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThan(String value) {
            addCriterion("tel <", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThanOrEqualTo(String value) {
            addCriterion("tel <=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLike(String value) {
            addCriterion("tel like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotLike(String value) {
            addCriterion("tel not like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelIn(List<String> values) {
            addCriterion("tel in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotIn(List<String> values) {
            addCriterion("tel not in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelBetween(String value1, String value2) {
            addCriterion("tel between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotBetween(String value1, String value2) {
            addCriterion("tel not between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andTelseeIsNull() {
            addCriterion("telsee is null");
            return (Criteria) this;
        }

        public Criteria andTelseeIsNotNull() {
            addCriterion("telsee is not null");
            return (Criteria) this;
        }

        public Criteria andTelseeEqualTo(Integer value) {
            addCriterion("telsee =", value, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeNotEqualTo(Integer value) {
            addCriterion("telsee <>", value, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeGreaterThan(Integer value) {
            addCriterion("telsee >", value, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeGreaterThanOrEqualTo(Integer value) {
            addCriterion("telsee >=", value, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeLessThan(Integer value) {
            addCriterion("telsee <", value, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeLessThanOrEqualTo(Integer value) {
            addCriterion("telsee <=", value, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeIn(List<Integer> values) {
            addCriterion("telsee in", values, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeNotIn(List<Integer> values) {
            addCriterion("telsee not in", values, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeBetween(Integer value1, Integer value2) {
            addCriterion("telsee between", value1, value2, "telsee");
            return (Criteria) this;
        }

        public Criteria andTelseeNotBetween(Integer value1, Integer value2) {
            addCriterion("telsee not between", value1, value2, "telsee");
            return (Criteria) this;
        }

        public Criteria andWxseeIsNull() {
            addCriterion("wxsee is null");
            return (Criteria) this;
        }

        public Criteria andWxseeIsNotNull() {
            addCriterion("wxsee is not null");
            return (Criteria) this;
        }

        public Criteria andWxseeEqualTo(Integer value) {
            addCriterion("wxsee =", value, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeNotEqualTo(Integer value) {
            addCriterion("wxsee <>", value, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeGreaterThan(Integer value) {
            addCriterion("wxsee >", value, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeGreaterThanOrEqualTo(Integer value) {
            addCriterion("wxsee >=", value, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeLessThan(Integer value) {
            addCriterion("wxsee <", value, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeLessThanOrEqualTo(Integer value) {
            addCriterion("wxsee <=", value, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeIn(List<Integer> values) {
            addCriterion("wxsee in", values, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeNotIn(List<Integer> values) {
            addCriterion("wxsee not in", values, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeBetween(Integer value1, Integer value2) {
            addCriterion("wxsee between", value1, value2, "wxsee");
            return (Criteria) this;
        }

        public Criteria andWxseeNotBetween(Integer value1, Integer value2) {
            addCriterion("wxsee not between", value1, value2, "wxsee");
            return (Criteria) this;
        }

        public Criteria andQqseeIsNull() {
            addCriterion("qqsee is null");
            return (Criteria) this;
        }

        public Criteria andQqseeIsNotNull() {
            addCriterion("qqsee is not null");
            return (Criteria) this;
        }

        public Criteria andQqseeEqualTo(Integer value) {
            addCriterion("qqsee =", value, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeNotEqualTo(Integer value) {
            addCriterion("qqsee <>", value, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeGreaterThan(Integer value) {
            addCriterion("qqsee >", value, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeGreaterThanOrEqualTo(Integer value) {
            addCriterion("qqsee >=", value, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeLessThan(Integer value) {
            addCriterion("qqsee <", value, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeLessThanOrEqualTo(Integer value) {
            addCriterion("qqsee <=", value, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeIn(List<Integer> values) {
            addCriterion("qqsee in", values, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeNotIn(List<Integer> values) {
            addCriterion("qqsee not in", values, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeBetween(Integer value1, Integer value2) {
            addCriterion("qqsee between", value1, value2, "qqsee");
            return (Criteria) this;
        }

        public Criteria andQqseeNotBetween(Integer value1, Integer value2) {
            addCriterion("qqsee not between", value1, value2, "qqsee");
            return (Criteria) this;
        }

        public Criteria andMailseeIsNull() {
            addCriterion("mailsee is null");
            return (Criteria) this;
        }

        public Criteria andMailseeIsNotNull() {
            addCriterion("mailsee is not null");
            return (Criteria) this;
        }

        public Criteria andMailseeEqualTo(Integer value) {
            addCriterion("mailsee =", value, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeNotEqualTo(Integer value) {
            addCriterion("mailsee <>", value, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeGreaterThan(Integer value) {
            addCriterion("mailsee >", value, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeGreaterThanOrEqualTo(Integer value) {
            addCriterion("mailsee >=", value, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeLessThan(Integer value) {
            addCriterion("mailsee <", value, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeLessThanOrEqualTo(Integer value) {
            addCriterion("mailsee <=", value, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeIn(List<Integer> values) {
            addCriterion("mailsee in", values, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeNotIn(List<Integer> values) {
            addCriterion("mailsee not in", values, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeBetween(Integer value1, Integer value2) {
            addCriterion("mailsee between", value1, value2, "mailsee");
            return (Criteria) this;
        }

        public Criteria andMailseeNotBetween(Integer value1, Integer value2) {
            addCriterion("mailsee not between", value1, value2, "mailsee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeIsNull() {
            addCriterion("remarksee is null");
            return (Criteria) this;
        }

        public Criteria andRemarkseeIsNotNull() {
            addCriterion("remarksee is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkseeEqualTo(Integer value) {
            addCriterion("remarksee =", value, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeNotEqualTo(Integer value) {
            addCriterion("remarksee <>", value, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeGreaterThan(Integer value) {
            addCriterion("remarksee >", value, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeGreaterThanOrEqualTo(Integer value) {
            addCriterion("remarksee >=", value, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeLessThan(Integer value) {
            addCriterion("remarksee <", value, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeLessThanOrEqualTo(Integer value) {
            addCriterion("remarksee <=", value, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeIn(List<Integer> values) {
            addCriterion("remarksee in", values, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeNotIn(List<Integer> values) {
            addCriterion("remarksee not in", values, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeBetween(Integer value1, Integer value2) {
            addCriterion("remarksee between", value1, value2, "remarksee");
            return (Criteria) this;
        }

        public Criteria andRemarkseeNotBetween(Integer value1, Integer value2) {
            addCriterion("remarksee not between", value1, value2, "remarksee");
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