package com.jp.entity;

import java.util.ArrayList;
import java.util.List;

public class MationExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public MationExample() {
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

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andNationcodeIsNull() {
			addCriterion("nationcode is null");
			return (Criteria) this;
		}

		public Criteria andNationcodeIsNotNull() {
			addCriterion("nationcode is not null");
			return (Criteria) this;
		}

		public Criteria andNationcodeEqualTo(Integer value) {
			addCriterion("nationcode =", value, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeNotEqualTo(Integer value) {
			addCriterion("nationcode <>", value, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeGreaterThan(Integer value) {
			addCriterion("nationcode >", value, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeGreaterThanOrEqualTo(Integer value) {
			addCriterion("nationcode >=", value, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeLessThan(Integer value) {
			addCriterion("nationcode <", value, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeLessThanOrEqualTo(Integer value) {
			addCriterion("nationcode <=", value, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeIn(List<Integer> values) {
			addCriterion("nationcode in", values, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeNotIn(List<Integer> values) {
			addCriterion("nationcode not in", values, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeBetween(Integer value1, Integer value2) {
			addCriterion("nationcode between", value1, value2, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationcodeNotBetween(Integer value1, Integer value2) {
			addCriterion("nationcode not between", value1, value2, "nationcode");
			return (Criteria) this;
		}

		public Criteria andNationnameIsNull() {
			addCriterion("nationname is null");
			return (Criteria) this;
		}

		public Criteria andNationnameIsNotNull() {
			addCriterion("nationname is not null");
			return (Criteria) this;
		}

		public Criteria andNationnameEqualTo(String value) {
			addCriterion("nationname =", value, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameNotEqualTo(String value) {
			addCriterion("nationname <>", value, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameGreaterThan(String value) {
			addCriterion("nationname >", value, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameGreaterThanOrEqualTo(String value) {
			addCriterion("nationname >=", value, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameLessThan(String value) {
			addCriterion("nationname <", value, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameLessThanOrEqualTo(String value) {
			addCriterion("nationname <=", value, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameLike(String value) {
			addCriterion("nationname like", value, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameNotLike(String value) {
			addCriterion("nationname not like", value, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameIn(List<String> values) {
			addCriterion("nationname in", values, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameNotIn(List<String> values) {
			addCriterion("nationname not in", values, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameBetween(String value1, String value2) {
			addCriterion("nationname between", value1, value2, "nationname");
			return (Criteria) this;
		}

		public Criteria andNationnameNotBetween(String value1, String value2) {
			addCriterion("nationname not between", value1, value2, "nationname");
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