package com.awifi.np.admin.entity;

import java.util.ArrayList;
import java.util.List;

public class NPBizSuitRoleCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NPBizSuitRoleCriteria() {
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

        public Criteria andSuitCodeIsNull() {
            addCriterion("suit_code is null");
            return (Criteria) this;
        }

        public Criteria andSuitCodeIsNotNull() {
            addCriterion("suit_code is not null");
            return (Criteria) this;
        }

        public Criteria andSuitCodeEqualTo(String value) {
            addCriterion("suit_code =", value, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeNotEqualTo(String value) {
            addCriterion("suit_code <>", value, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeGreaterThan(String value) {
            addCriterion("suit_code >", value, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeGreaterThanOrEqualTo(String value) {
            addCriterion("suit_code >=", value, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeLessThan(String value) {
            addCriterion("suit_code <", value, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeLessThanOrEqualTo(String value) {
            addCriterion("suit_code <=", value, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeLike(String value) {
            addCriterion("suit_code like", value, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeNotLike(String value) {
            addCriterion("suit_code not like", value, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeIn(List<String> values) {
            addCriterion("suit_code in", values, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeNotIn(List<String> values) {
            addCriterion("suit_code not in", values, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeBetween(String value1, String value2) {
            addCriterion("suit_code between", value1, value2, "suitCode");
            return (Criteria) this;
        }

        public Criteria andSuitCodeNotBetween(String value1, String value2) {
            addCriterion("suit_code not between", value1, value2, "suitCode");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Long value) {
            addCriterion("role_id =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Long value) {
            addCriterion("role_id <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Long value) {
            addCriterion("role_id >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("role_id >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Long value) {
            addCriterion("role_id <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Long value) {
            addCriterion("role_id <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Long> values) {
            addCriterion("role_id in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Long> values) {
            addCriterion("role_id not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Long value1, Long value2) {
            addCriterion("role_id between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Long value1, Long value2) {
            addCriterion("role_id not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andShowLevelIsNull() {
            addCriterion("show_level is null");
            return (Criteria) this;
        }

        public Criteria andShowLevelIsNotNull() {
            addCriterion("show_level is not null");
            return (Criteria) this;
        }

        public Criteria andShowLevelEqualTo(Integer value) {
            addCriterion("show_level =", value, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelNotEqualTo(Integer value) {
            addCriterion("show_level <>", value, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelGreaterThan(Integer value) {
            addCriterion("show_level >", value, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("show_level >=", value, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelLessThan(Integer value) {
            addCriterion("show_level <", value, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelLessThanOrEqualTo(Integer value) {
            addCriterion("show_level <=", value, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelIn(List<Integer> values) {
            addCriterion("show_level in", values, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelNotIn(List<Integer> values) {
            addCriterion("show_level not in", values, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelBetween(Integer value1, Integer value2) {
            addCriterion("show_level between", value1, value2, "showLevel");
            return (Criteria) this;
        }

        public Criteria andShowLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("show_level not between", value1, value2, "showLevel");
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