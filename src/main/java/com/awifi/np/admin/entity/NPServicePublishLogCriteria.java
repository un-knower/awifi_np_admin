package com.awifi.np.admin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NPServicePublishLogCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NPServicePublishLogCriteria() {
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

        public Criteria andServiceIdIsNull() {
            addCriterion("service_id is null");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNotNull() {
            addCriterion("service_id is not null");
            return (Criteria) this;
        }

        public Criteria andServiceIdEqualTo(Integer value) {
            addCriterion("service_id =", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotEqualTo(Integer value) {
            addCriterion("service_id <>", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThan(Integer value) {
            addCriterion("service_id >", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_id >=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThan(Integer value) {
            addCriterion("service_id <", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThanOrEqualTo(Integer value) {
            addCriterion("service_id <=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdIn(List<Integer> values) {
            addCriterion("service_id in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotIn(List<Integer> values) {
            addCriterion("service_id not in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdBetween(Integer value1, Integer value2) {
            addCriterion("service_id between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("service_id not between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceCodeIsNull() {
            addCriterion("service_code is null");
            return (Criteria) this;
        }

        public Criteria andServiceCodeIsNotNull() {
            addCriterion("service_code is not null");
            return (Criteria) this;
        }

        public Criteria andServiceCodeEqualTo(String value) {
            addCriterion("service_code =", value, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeNotEqualTo(String value) {
            addCriterion("service_code <>", value, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeGreaterThan(String value) {
            addCriterion("service_code >", value, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("service_code >=", value, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeLessThan(String value) {
            addCriterion("service_code <", value, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeLessThanOrEqualTo(String value) {
            addCriterion("service_code <=", value, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeLike(String value) {
            addCriterion("service_code like", value, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeNotLike(String value) {
            addCriterion("service_code not like", value, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeIn(List<String> values) {
            addCriterion("service_code in", values, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeNotIn(List<String> values) {
            addCriterion("service_code not in", values, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeBetween(String value1, String value2) {
            addCriterion("service_code between", value1, value2, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andServiceCodeNotBetween(String value1, String value2) {
            addCriterion("service_code not between", value1, value2, "serviceCode");
            return (Criteria) this;
        }

        public Criteria andPublishVersionIsNull() {
            addCriterion("publish_version is null");
            return (Criteria) this;
        }

        public Criteria andPublishVersionIsNotNull() {
            addCriterion("publish_version is not null");
            return (Criteria) this;
        }

        public Criteria andPublishVersionEqualTo(String value) {
            addCriterion("publish_version =", value, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionNotEqualTo(String value) {
            addCriterion("publish_version <>", value, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionGreaterThan(String value) {
            addCriterion("publish_version >", value, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionGreaterThanOrEqualTo(String value) {
            addCriterion("publish_version >=", value, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionLessThan(String value) {
            addCriterion("publish_version <", value, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionLessThanOrEqualTo(String value) {
            addCriterion("publish_version <=", value, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionLike(String value) {
            addCriterion("publish_version like", value, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionNotLike(String value) {
            addCriterion("publish_version not like", value, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionIn(List<String> values) {
            addCriterion("publish_version in", values, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionNotIn(List<String> values) {
            addCriterion("publish_version not in", values, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionBetween(String value1, String value2) {
            addCriterion("publish_version between", value1, value2, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishVersionNotBetween(String value1, String value2) {
            addCriterion("publish_version not between", value1, value2, "publishVersion");
            return (Criteria) this;
        }

        public Criteria andPublishStatusIsNull() {
            addCriterion("publish_status is null");
            return (Criteria) this;
        }

        public Criteria andPublishStatusIsNotNull() {
            addCriterion("publish_status is not null");
            return (Criteria) this;
        }

        public Criteria andPublishStatusEqualTo(Byte value) {
            addCriterion("publish_status =", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusNotEqualTo(Byte value) {
            addCriterion("publish_status <>", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusGreaterThan(Byte value) {
            addCriterion("publish_status >", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("publish_status >=", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusLessThan(Byte value) {
            addCriterion("publish_status <", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusLessThanOrEqualTo(Byte value) {
            addCriterion("publish_status <=", value, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusIn(List<Byte> values) {
            addCriterion("publish_status in", values, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusNotIn(List<Byte> values) {
            addCriterion("publish_status not in", values, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusBetween(Byte value1, Byte value2) {
            addCriterion("publish_status between", value1, value2, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andPublishStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("publish_status not between", value1, value2, "publishStatus");
            return (Criteria) this;
        }

        public Criteria andConfigDateIsNull() {
            addCriterion("config_date is null");
            return (Criteria) this;
        }

        public Criteria andConfigDateIsNotNull() {
            addCriterion("config_date is not null");
            return (Criteria) this;
        }

        public Criteria andConfigDateEqualTo(Date value) {
            addCriterion("config_date =", value, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateNotEqualTo(Date value) {
            addCriterion("config_date <>", value, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateGreaterThan(Date value) {
            addCriterion("config_date >", value, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateGreaterThanOrEqualTo(Date value) {
            addCriterion("config_date >=", value, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateLessThan(Date value) {
            addCriterion("config_date <", value, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateLessThanOrEqualTo(Date value) {
            addCriterion("config_date <=", value, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateIn(List<Date> values) {
            addCriterion("config_date in", values, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateNotIn(List<Date> values) {
            addCriterion("config_date not in", values, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateBetween(Date value1, Date value2) {
            addCriterion("config_date between", value1, value2, "configDate");
            return (Criteria) this;
        }

        public Criteria andConfigDateNotBetween(Date value1, Date value2) {
            addCriterion("config_date not between", value1, value2, "configDate");
            return (Criteria) this;
        }

        public Criteria andTestDateIsNull() {
            addCriterion("test_date is null");
            return (Criteria) this;
        }

        public Criteria andTestDateIsNotNull() {
            addCriterion("test_date is not null");
            return (Criteria) this;
        }

        public Criteria andTestDateEqualTo(Date value) {
            addCriterion("test_date =", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotEqualTo(Date value) {
            addCriterion("test_date <>", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateGreaterThan(Date value) {
            addCriterion("test_date >", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateGreaterThanOrEqualTo(Date value) {
            addCriterion("test_date >=", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateLessThan(Date value) {
            addCriterion("test_date <", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateLessThanOrEqualTo(Date value) {
            addCriterion("test_date <=", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateIn(List<Date> values) {
            addCriterion("test_date in", values, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotIn(List<Date> values) {
            addCriterion("test_date not in", values, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateBetween(Date value1, Date value2) {
            addCriterion("test_date between", value1, value2, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotBetween(Date value1, Date value2) {
            addCriterion("test_date not between", value1, value2, "testDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(Date value) {
            addCriterion("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(Date value) {
            addCriterion("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(Date value) {
            addCriterion("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(Date value) {
            addCriterion("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(Date value) {
            addCriterion("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(Date value) {
            addCriterion("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<Date> values) {
            addCriterion("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<Date> values) {
            addCriterion("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(Date value1, Date value2) {
            addCriterion("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(Date value1, Date value2) {
            addCriterion("check_date not between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNull() {
            addCriterion("publish_date is null");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNotNull() {
            addCriterion("publish_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublishDateEqualTo(Date value) {
            addCriterion("publish_date =", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotEqualTo(Date value) {
            addCriterion("publish_date <>", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThan(Date value) {
            addCriterion("publish_date >", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThanOrEqualTo(Date value) {
            addCriterion("publish_date >=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThan(Date value) {
            addCriterion("publish_date <", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThanOrEqualTo(Date value) {
            addCriterion("publish_date <=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateIn(List<Date> values) {
            addCriterion("publish_date in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotIn(List<Date> values) {
            addCriterion("publish_date not in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateBetween(Date value1, Date value2) {
            addCriterion("publish_date between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotBetween(Date value1, Date value2) {
            addCriterion("publish_date not between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateIsNull() {
            addCriterion("cancel_date is null");
            return (Criteria) this;
        }

        public Criteria andCancelDateIsNotNull() {
            addCriterion("cancel_date is not null");
            return (Criteria) this;
        }

        public Criteria andCancelDateEqualTo(Date value) {
            addCriterion("cancel_date =", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotEqualTo(Date value) {
            addCriterion("cancel_date <>", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateGreaterThan(Date value) {
            addCriterion("cancel_date >", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateGreaterThanOrEqualTo(Date value) {
            addCriterion("cancel_date >=", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLessThan(Date value) {
            addCriterion("cancel_date <", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateLessThanOrEqualTo(Date value) {
            addCriterion("cancel_date <=", value, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateIn(List<Date> values) {
            addCriterion("cancel_date in", values, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotIn(List<Date> values) {
            addCriterion("cancel_date not in", values, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateBetween(Date value1, Date value2) {
            addCriterion("cancel_date between", value1, value2, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andCancelDateNotBetween(Date value1, Date value2) {
            addCriterion("cancel_date not between", value1, value2, "cancelDate");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdIsNull() {
            addCriterion("config_user_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdIsNotNull() {
            addCriterion("config_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdEqualTo(Integer value) {
            addCriterion("config_user_id =", value, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdNotEqualTo(Integer value) {
            addCriterion("config_user_id <>", value, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdGreaterThan(Integer value) {
            addCriterion("config_user_id >", value, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_user_id >=", value, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdLessThan(Integer value) {
            addCriterion("config_user_id <", value, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_user_id <=", value, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdIn(List<Integer> values) {
            addCriterion("config_user_id in", values, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdNotIn(List<Integer> values) {
            addCriterion("config_user_id not in", values, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdBetween(Integer value1, Integer value2) {
            addCriterion("config_user_id between", value1, value2, "configUserId");
            return (Criteria) this;
        }

        public Criteria andConfigUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_user_id not between", value1, value2, "configUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdIsNull() {
            addCriterion("test_user_id is null");
            return (Criteria) this;
        }

        public Criteria andTestUserIdIsNotNull() {
            addCriterion("test_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestUserIdEqualTo(Integer value) {
            addCriterion("test_user_id =", value, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdNotEqualTo(Integer value) {
            addCriterion("test_user_id <>", value, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdGreaterThan(Integer value) {
            addCriterion("test_user_id >", value, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("test_user_id >=", value, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdLessThan(Integer value) {
            addCriterion("test_user_id <", value, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("test_user_id <=", value, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdIn(List<Integer> values) {
            addCriterion("test_user_id in", values, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdNotIn(List<Integer> values) {
            addCriterion("test_user_id not in", values, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdBetween(Integer value1, Integer value2) {
            addCriterion("test_user_id between", value1, value2, "testUserId");
            return (Criteria) this;
        }

        public Criteria andTestUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("test_user_id not between", value1, value2, "testUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNull() {
            addCriterion("check_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNotNull() {
            addCriterion("check_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdEqualTo(Integer value) {
            addCriterion("check_user_id =", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotEqualTo(Integer value) {
            addCriterion("check_user_id <>", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThan(Integer value) {
            addCriterion("check_user_id >", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_user_id >=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThan(Integer value) {
            addCriterion("check_user_id <", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_user_id <=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIn(List<Integer> values) {
            addCriterion("check_user_id in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotIn(List<Integer> values) {
            addCriterion("check_user_id not in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id not between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdIsNull() {
            addCriterion("publish_user_id is null");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdIsNotNull() {
            addCriterion("publish_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdEqualTo(Integer value) {
            addCriterion("publish_user_id =", value, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdNotEqualTo(Integer value) {
            addCriterion("publish_user_id <>", value, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdGreaterThan(Integer value) {
            addCriterion("publish_user_id >", value, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("publish_user_id >=", value, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdLessThan(Integer value) {
            addCriterion("publish_user_id <", value, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("publish_user_id <=", value, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdIn(List<Integer> values) {
            addCriterion("publish_user_id in", values, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdNotIn(List<Integer> values) {
            addCriterion("publish_user_id not in", values, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdBetween(Integer value1, Integer value2) {
            addCriterion("publish_user_id between", value1, value2, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andPublishUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("publish_user_id not between", value1, value2, "publishUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdIsNull() {
            addCriterion("cancel_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdIsNotNull() {
            addCriterion("cancel_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdEqualTo(Integer value) {
            addCriterion("cancel_user_id =", value, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdNotEqualTo(Integer value) {
            addCriterion("cancel_user_id <>", value, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdGreaterThan(Integer value) {
            addCriterion("cancel_user_id >", value, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancel_user_id >=", value, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdLessThan(Integer value) {
            addCriterion("cancel_user_id <", value, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("cancel_user_id <=", value, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdIn(List<Integer> values) {
            addCriterion("cancel_user_id in", values, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdNotIn(List<Integer> values) {
            addCriterion("cancel_user_id not in", values, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdBetween(Integer value1, Integer value2) {
            addCriterion("cancel_user_id between", value1, value2, "cancelUserId");
            return (Criteria) this;
        }

        public Criteria andCancelUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cancel_user_id not between", value1, value2, "cancelUserId");
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