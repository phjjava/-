package com.jp.entity;

import java.util.ArrayList;
import java.util.List;

public class MomentFilterExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	protected Integer pageNo = 1;

	protected Integer startRow;

	protected Integer pageSize = 10;

	protected String fields;

	public MomentFilterExample() {
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

		public Criteria andMomentidIsNull() {
			addCriterion("momentid is null");
			return (Criteria) this;
		}

		public Criteria andMomentidIsNotNull() {
			addCriterion("momentid is not null");
			return (Criteria) this;
		}

		public Criteria andMomentidEqualTo(String value) {
			addCriterion("momentid =", value, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidNotEqualTo(String value) {
			addCriterion("momentid <>", value, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidGreaterThan(String value) {
			addCriterion("momentid >", value, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidGreaterThanOrEqualTo(String value) {
			addCriterion("momentid >=", value, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidLessThan(String value) {
			addCriterion("momentid <", value, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidLessThanOrEqualTo(String value) {
			addCriterion("momentid <=", value, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidLike(String value) {
			addCriterion("momentid like", value, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidNotLike(String value) {
			addCriterion("momentid not like", value, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidIn(List<String> values) {
			addCriterion("momentid in", values, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidNotIn(List<String> values) {
			addCriterion("momentid not in", values, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidBetween(String value1, String value2) {
			addCriterion("momentid between", value1, value2, "momentid");
			return (Criteria) this;
		}

		public Criteria andMomentidNotBetween(String value1, String value2) {
			addCriterion("momentid not between", value1, value2, "momentid");
			return (Criteria) this;
		}

		public Criteria andFilteridIsNull() {
			addCriterion("filterid is null");
			return (Criteria) this;
		}

		public Criteria andFilteridIsNotNull() {
			addCriterion("filterid is not null");
			return (Criteria) this;
		}

		public Criteria andFilteridEqualTo(String value) {
			addCriterion("filterid =", value, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridNotEqualTo(String value) {
			addCriterion("filterid <>", value, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridGreaterThan(String value) {
			addCriterion("filterid >", value, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridGreaterThanOrEqualTo(String value) {
			addCriterion("filterid >=", value, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridLessThan(String value) {
			addCriterion("filterid <", value, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridLessThanOrEqualTo(String value) {
			addCriterion("filterid <=", value, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridLike(String value) {
			addCriterion("filterid like", value, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridNotLike(String value) {
			addCriterion("filterid not like", value, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridIn(List<String> values) {
			addCriterion("filterid in", values, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridNotIn(List<String> values) {
			addCriterion("filterid not in", values, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridBetween(String value1, String value2) {
			addCriterion("filterid between", value1, value2, "filterid");
			return (Criteria) this;
		}

		public Criteria andFilteridNotBetween(String value1, String value2) {
			addCriterion("filterid not between", value1, value2, "filterid");
			return (Criteria) this;
		}

		public Criteria andFiltertypeIsNull() {
			addCriterion("filtertype is null");
			return (Criteria) this;
		}

		public Criteria andFiltertypeIsNotNull() {
			addCriterion("filtertype is not null");
			return (Criteria) this;
		}

		public Criteria andFiltertypeEqualTo(String value) {
			addCriterion("filtertype =", value, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeNotEqualTo(String value) {
			addCriterion("filtertype <>", value, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeGreaterThan(String value) {
			addCriterion("filtertype >", value, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeGreaterThanOrEqualTo(String value) {
			addCriterion("filtertype >=", value, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeLessThan(String value) {
			addCriterion("filtertype <", value, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeLessThanOrEqualTo(String value) {
			addCriterion("filtertype <=", value, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeLike(String value) {
			addCriterion("filtertype like", value, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeNotLike(String value) {
			addCriterion("filtertype not like", value, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeIn(List<String> values) {
			addCriterion("filtertype in", values, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeNotIn(List<String> values) {
			addCriterion("filtertype not in", values, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeBetween(String value1, String value2) {
			addCriterion("filtertype between", value1, value2, "filtertype");
			return (Criteria) this;
		}

		public Criteria andFiltertypeNotBetween(String value1, String value2) {
			addCriterion("filtertype not between", value1, value2, "filtertype");
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