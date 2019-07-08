package com.jp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BranchQuery {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	protected Integer pageNo = 1;

	protected Integer startRow;

	protected Integer pageSize = 10;

	protected String fields;

	public BranchQuery() {
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
		this.pageNo = pageNo;
		this.startRow = (pageNo - 1) * this.pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.startRow = (pageNo - 1) * this.pageSize;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setFields(String fields) {
		this.fields = fields;
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

		public Criteria andBranchnameIsNull() {
			addCriterion("branchname is null");
			return (Criteria) this;
		}

		public Criteria andBranchnameIsNotNull() {
			addCriterion("branchname is not null");
			return (Criteria) this;
		}

		public Criteria andBranchnameEqualTo(String value) {
			addCriterion("branchname =", value, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameNotEqualTo(String value) {
			addCriterion("branchname <>", value, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameGreaterThan(String value) {
			addCriterion("branchname >", value, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameGreaterThanOrEqualTo(String value) {
			addCriterion("branchname >=", value, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameLessThan(String value) {
			addCriterion("branchname <", value, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameLessThanOrEqualTo(String value) {
			addCriterion("branchname <=", value, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameLike(String value) {
			addCriterion("branchname like", value, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameNotLike(String value) {
			addCriterion("branchname not like", value, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameIn(List<String> values) {
			addCriterion("branchname in", values, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameNotIn(List<String> values) {
			addCriterion("branchname not in", values, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameBetween(String value1, String value2) {
			addCriterion("branchname between", value1, value2, "branchname");
			return (Criteria) this;
		}

		public Criteria andBranchnameNotBetween(String value1, String value2) {
			addCriterion("branchname not between", value1, value2, "branchname");
			return (Criteria) this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("status is null");
			return (Criteria) this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("status is not null");
			return (Criteria) this;
		}

		public Criteria andStatusEqualTo(Integer value) {
			addCriterion("status =", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotEqualTo(Integer value) {
			addCriterion("status <>", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThan(Integer value) {
			addCriterion("status >", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
			addCriterion("status >=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThan(Integer value) {
			addCriterion("status <", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThanOrEqualTo(Integer value) {
			addCriterion("status <=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusIn(List<Integer> values) {
			addCriterion("status in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotIn(List<Integer> values) {
			addCriterion("status not in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusBetween(Integer value1, Integer value2) {
			addCriterion("status between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotBetween(Integer value1, Integer value2) {
			addCriterion("status not between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andAreaIsNull() {
			addCriterion("area is null");
			return (Criteria) this;
		}

		public Criteria andAreaIsNotNull() {
			addCriterion("area is not null");
			return (Criteria) this;
		}

		public Criteria andAreaEqualTo(String value) {
			addCriterion("area =", value, "area");
			return (Criteria) this;
		}

		public Criteria andAreaNotEqualTo(String value) {
			addCriterion("area <>", value, "area");
			return (Criteria) this;
		}

		public Criteria andAreaGreaterThan(String value) {
			addCriterion("area >", value, "area");
			return (Criteria) this;
		}

		public Criteria andAreaGreaterThanOrEqualTo(String value) {
			addCriterion("area >=", value, "area");
			return (Criteria) this;
		}

		public Criteria andAreaLessThan(String value) {
			addCriterion("area <", value, "area");
			return (Criteria) this;
		}

		public Criteria andAreaLessThanOrEqualTo(String value) {
			addCriterion("area <=", value, "area");
			return (Criteria) this;
		}

		public Criteria andAreaLike(String value) {
			addCriterion("area like", value, "area");
			return (Criteria) this;
		}

		public Criteria andAreaNotLike(String value) {
			addCriterion("area not like", value, "area");
			return (Criteria) this;
		}

		public Criteria andAreaIn(List<String> values) {
			addCriterion("area in", values, "area");
			return (Criteria) this;
		}

		public Criteria andAreaNotIn(List<String> values) {
			addCriterion("area not in", values, "area");
			return (Criteria) this;
		}

		public Criteria andAreaBetween(String value1, String value2) {
			addCriterion("area between", value1, value2, "area");
			return (Criteria) this;
		}

		public Criteria andAreaNotBetween(String value1, String value2) {
			addCriterion("area not between", value1, value2, "area");
			return (Criteria) this;
		}

		public Criteria andAreacodeIsNull() {
			addCriterion("areacode is null");
			return (Criteria) this;
		}

		public Criteria andAreacodeIsNotNull() {
			addCriterion("areacode is not null");
			return (Criteria) this;
		}

		public Criteria andAreacodeEqualTo(String value) {
			addCriterion("areacode =", value, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeNotEqualTo(String value) {
			addCriterion("areacode <>", value, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeGreaterThan(String value) {
			addCriterion("areacode >", value, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeGreaterThanOrEqualTo(String value) {
			addCriterion("areacode >=", value, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeLessThan(String value) {
			addCriterion("areacode <", value, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeLessThanOrEqualTo(String value) {
			addCriterion("areacode <=", value, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeLike(String value) {
			addCriterion("areacode like", value, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeNotLike(String value) {
			addCriterion("areacode not like", value, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeIn(List<String> values) {
			addCriterion("areacode in", values, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeNotIn(List<String> values) {
			addCriterion("areacode not in", values, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeBetween(String value1, String value2) {
			addCriterion("areacode between", value1, value2, "areacode");
			return (Criteria) this;
		}

		public Criteria andAreacodeNotBetween(String value1, String value2) {
			addCriterion("areacode not between", value1, value2, "areacode");
			return (Criteria) this;
		}

		public Criteria andBeginuseridIsNull() {
			addCriterion("beginuserid is null");
			return (Criteria) this;
		}

		public Criteria andBeginuseridIsNotNull() {
			addCriterion("beginuserid is not null");
			return (Criteria) this;
		}

		public Criteria andBeginuseridEqualTo(String value) {
			addCriterion("beginuserid =", value, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridNotEqualTo(String value) {
			addCriterion("beginuserid <>", value, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridGreaterThan(String value) {
			addCriterion("beginuserid >", value, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridGreaterThanOrEqualTo(String value) {
			addCriterion("beginuserid >=", value, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridLessThan(String value) {
			addCriterion("beginuserid <", value, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridLessThanOrEqualTo(String value) {
			addCriterion("beginuserid <=", value, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridLike(String value) {
			addCriterion("beginuserid like", value, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridNotLike(String value) {
			addCriterion("beginuserid not like", value, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridIn(List<String> values) {
			addCriterion("beginuserid in", values, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridNotIn(List<String> values) {
			addCriterion("beginuserid not in", values, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridBetween(String value1, String value2) {
			addCriterion("beginuserid between", value1, value2, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginuseridNotBetween(String value1, String value2) {
			addCriterion("beginuserid not between", value1, value2, "beginuserid");
			return (Criteria) this;
		}

		public Criteria andBeginnameIsNull() {
			addCriterion("beginname is null");
			return (Criteria) this;
		}

		public Criteria andBeginnameIsNotNull() {
			addCriterion("beginname is not null");
			return (Criteria) this;
		}

		public Criteria andBeginnameEqualTo(String value) {
			addCriterion("beginname =", value, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameNotEqualTo(String value) {
			addCriterion("beginname <>", value, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameGreaterThan(String value) {
			addCriterion("beginname >", value, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameGreaterThanOrEqualTo(String value) {
			addCriterion("beginname >=", value, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameLessThan(String value) {
			addCriterion("beginname <", value, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameLessThanOrEqualTo(String value) {
			addCriterion("beginname <=", value, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameLike(String value) {
			addCriterion("beginname like", value, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameNotLike(String value) {
			addCriterion("beginname not like", value, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameIn(List<String> values) {
			addCriterion("beginname in", values, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameNotIn(List<String> values) {
			addCriterion("beginname not in", values, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameBetween(String value1, String value2) {
			addCriterion("beginname between", value1, value2, "beginname");
			return (Criteria) this;
		}

		public Criteria andBeginnameNotBetween(String value1, String value2) {
			addCriterion("beginname not between", value1, value2, "beginname");
			return (Criteria) this;
		}

		public Criteria andParentidIsNull() {
			addCriterion("parentid is null");
			return (Criteria) this;
		}

		public Criteria andParentidIsNotNull() {
			addCriterion("parentid is not null");
			return (Criteria) this;
		}

		public Criteria andParentidEqualTo(String value) {
			addCriterion("parentid =", value, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidNotEqualTo(String value) {
			addCriterion("parentid <>", value, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidGreaterThan(String value) {
			addCriterion("parentid >", value, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidGreaterThanOrEqualTo(String value) {
			addCriterion("parentid >=", value, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidLessThan(String value) {
			addCriterion("parentid <", value, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidLessThanOrEqualTo(String value) {
			addCriterion("parentid <=", value, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidLike(String value) {
			addCriterion("parentid like", value, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidNotLike(String value) {
			addCriterion("parentid not like", value, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidIn(List<String> values) {
			addCriterion("parentid in", values, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidNotIn(List<String> values) {
			addCriterion("parentid not in", values, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidBetween(String value1, String value2) {
			addCriterion("parentid between", value1, value2, "parentid");
			return (Criteria) this;
		}

		public Criteria andParentidNotBetween(String value1, String value2) {
			addCriterion("parentid not between", value1, value2, "parentid");
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

		public Criteria andSortEqualTo(String value) {
			addCriterion("sort =", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortNotEqualTo(String value) {
			addCriterion("sort <>", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortGreaterThan(String value) {
			addCriterion("sort >", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortGreaterThanOrEqualTo(String value) {
			addCriterion("sort >=", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortLessThan(String value) {
			addCriterion("sort <", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortLessThanOrEqualTo(String value) {
			addCriterion("sort <=", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortLike(String value) {
			addCriterion("sort like", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortNotLike(String value) {
			addCriterion("sort not like", value, "sort");
			return (Criteria) this;
		}

		public Criteria andSortIn(List<String> values) {
			addCriterion("sort in", values, "sort");
			return (Criteria) this;
		}

		public Criteria andSortNotIn(List<String> values) {
			addCriterion("sort not in", values, "sort");
			return (Criteria) this;
		}

		public Criteria andSortBetween(String value1, String value2) {
			addCriterion("sort between", value1, value2, "sort");
			return (Criteria) this;
		}

		public Criteria andSortNotBetween(String value1, String value2) {
			addCriterion("sort not between", value1, value2, "sort");
			return (Criteria) this;
		}

		public Criteria andCitycodeIsNull() {
			addCriterion("citycode is null");
			return (Criteria) this;
		}

		public Criteria andCitycodeIsNotNull() {
			addCriterion("citycode is not null");
			return (Criteria) this;
		}

		public Criteria andCitycodeEqualTo(String value) {
			addCriterion("citycode =", value, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeNotEqualTo(String value) {
			addCriterion("citycode <>", value, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeGreaterThan(String value) {
			addCriterion("citycode >", value, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeGreaterThanOrEqualTo(String value) {
			addCriterion("citycode >=", value, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeLessThan(String value) {
			addCriterion("citycode <", value, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeLessThanOrEqualTo(String value) {
			addCriterion("citycode <=", value, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeLike(String value) {
			addCriterion("citycode like", value, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeNotLike(String value) {
			addCriterion("citycode not like", value, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeIn(List<String> values) {
			addCriterion("citycode in", values, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeNotIn(List<String> values) {
			addCriterion("citycode not in", values, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeBetween(String value1, String value2) {
			addCriterion("citycode between", value1, value2, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitycodeNotBetween(String value1, String value2) {
			addCriterion("citycode not between", value1, value2, "citycode");
			return (Criteria) this;
		}

		public Criteria andCitynameIsNull() {
			addCriterion("cityname is null");
			return (Criteria) this;
		}

		public Criteria andCitynameIsNotNull() {
			addCriterion("cityname is not null");
			return (Criteria) this;
		}

		public Criteria andCitynameEqualTo(String value) {
			addCriterion("cityname =", value, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameNotEqualTo(String value) {
			addCriterion("cityname <>", value, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameGreaterThan(String value) {
			addCriterion("cityname >", value, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameGreaterThanOrEqualTo(String value) {
			addCriterion("cityname >=", value, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameLessThan(String value) {
			addCriterion("cityname <", value, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameLessThanOrEqualTo(String value) {
			addCriterion("cityname <=", value, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameLike(String value) {
			addCriterion("cityname like", value, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameNotLike(String value) {
			addCriterion("cityname not like", value, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameIn(List<String> values) {
			addCriterion("cityname in", values, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameNotIn(List<String> values) {
			addCriterion("cityname not in", values, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameBetween(String value1, String value2) {
			addCriterion("cityname between", value1, value2, "cityname");
			return (Criteria) this;
		}

		public Criteria andCitynameNotBetween(String value1, String value2) {
			addCriterion("cityname not between", value1, value2, "cityname");
			return (Criteria) this;
		}

		public Criteria andXcodeIsNull() {
			addCriterion("xcode is null");
			return (Criteria) this;
		}

		public Criteria andXcodeIsNotNull() {
			addCriterion("xcode is not null");
			return (Criteria) this;
		}

		public Criteria andXcodeEqualTo(String value) {
			addCriterion("xcode =", value, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeNotEqualTo(String value) {
			addCriterion("xcode <>", value, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeGreaterThan(String value) {
			addCriterion("xcode >", value, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeGreaterThanOrEqualTo(String value) {
			addCriterion("xcode >=", value, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeLessThan(String value) {
			addCriterion("xcode <", value, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeLessThanOrEqualTo(String value) {
			addCriterion("xcode <=", value, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeLike(String value) {
			addCriterion("xcode like", value, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeNotLike(String value) {
			addCriterion("xcode not like", value, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeIn(List<String> values) {
			addCriterion("xcode in", values, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeNotIn(List<String> values) {
			addCriterion("xcode not in", values, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeBetween(String value1, String value2) {
			addCriterion("xcode between", value1, value2, "xcode");
			return (Criteria) this;
		}

		public Criteria andXcodeNotBetween(String value1, String value2) {
			addCriterion("xcode not between", value1, value2, "xcode");
			return (Criteria) this;
		}

		public Criteria andAddressIsNull() {
			addCriterion("address is null");
			return (Criteria) this;
		}

		public Criteria andAddressIsNotNull() {
			addCriterion("address is not null");
			return (Criteria) this;
		}

		public Criteria andAddressEqualTo(String value) {
			addCriterion("address =", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotEqualTo(String value) {
			addCriterion("address <>", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressGreaterThan(String value) {
			addCriterion("address >", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressGreaterThanOrEqualTo(String value) {
			addCriterion("address >=", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLessThan(String value) {
			addCriterion("address <", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLessThanOrEqualTo(String value) {
			addCriterion("address <=", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLike(String value) {
			addCriterion("address like", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotLike(String value) {
			addCriterion("address not like", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressIn(List<String> values) {
			addCriterion("address in", values, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotIn(List<String> values) {
			addCriterion("address not in", values, "address");
			return (Criteria) this;
		}

		public Criteria andAddressBetween(String value1, String value2) {
			addCriterion("address between", value1, value2, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotBetween(String value1, String value2) {
			addCriterion("address not between", value1, value2, "address");
			return (Criteria) this;
		}

		public Criteria andXnameIsNull() {
			addCriterion("xname is null");
			return (Criteria) this;
		}

		public Criteria andXnameIsNotNull() {
			addCriterion("xname is not null");
			return (Criteria) this;
		}

		public Criteria andXnameEqualTo(String value) {
			addCriterion("xname =", value, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameNotEqualTo(String value) {
			addCriterion("xname <>", value, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameGreaterThan(String value) {
			addCriterion("xname >", value, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameGreaterThanOrEqualTo(String value) {
			addCriterion("xname >=", value, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameLessThan(String value) {
			addCriterion("xname <", value, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameLessThanOrEqualTo(String value) {
			addCriterion("xname <=", value, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameLike(String value) {
			addCriterion("xname like", value, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameNotLike(String value) {
			addCriterion("xname not like", value, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameIn(List<String> values) {
			addCriterion("xname in", values, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameNotIn(List<String> values) {
			addCriterion("xname not in", values, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameBetween(String value1, String value2) {
			addCriterion("xname between", value1, value2, "xname");
			return (Criteria) this;
		}

		public Criteria andXnameNotBetween(String value1, String value2) {
			addCriterion("xname not between", value1, value2, "xname");
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