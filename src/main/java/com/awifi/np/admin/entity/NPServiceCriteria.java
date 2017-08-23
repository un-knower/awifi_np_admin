package com.awifi.np.admin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NPServiceCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NPServiceCriteria() {
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

        public Criteria andServiceKeyIsNull() {
            addCriterion("service_key is null");
            return (Criteria) this;
        }

        public Criteria andServiceKeyIsNotNull() {
            addCriterion("service_key is not null");
            return (Criteria) this;
        }

        public Criteria andServiceKeyEqualTo(String value) {
            addCriterion("service_key =", value, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyNotEqualTo(String value) {
            addCriterion("service_key <>", value, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyGreaterThan(String value) {
            addCriterion("service_key >", value, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyGreaterThanOrEqualTo(String value) {
            addCriterion("service_key >=", value, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyLessThan(String value) {
            addCriterion("service_key <", value, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyLessThanOrEqualTo(String value) {
            addCriterion("service_key <=", value, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyLike(String value) {
            addCriterion("service_key like", value, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyNotLike(String value) {
            addCriterion("service_key not like", value, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyIn(List<String> values) {
            addCriterion("service_key in", values, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyNotIn(List<String> values) {
            addCriterion("service_key not in", values, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyBetween(String value1, String value2) {
            addCriterion("service_key between", value1, value2, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceKeyNotBetween(String value1, String value2) {
            addCriterion("service_key not between", value1, value2, "serviceKey");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNull() {
            addCriterion("service_name is null");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNotNull() {
            addCriterion("service_name is not null");
            return (Criteria) this;
        }

        public Criteria andServiceNameEqualTo(String value) {
            addCriterion("service_name =", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotEqualTo(String value) {
            addCriterion("service_name <>", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThan(String value) {
            addCriterion("service_name >", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("service_name >=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThan(String value) {
            addCriterion("service_name <", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThanOrEqualTo(String value) {
            addCriterion("service_name <=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLike(String value) {
            addCriterion("service_name like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotLike(String value) {
            addCriterion("service_name not like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameIn(List<String> values) {
            addCriterion("service_name in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotIn(List<String> values) {
            addCriterion("service_name not in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameBetween(String value1, String value2) {
            addCriterion("service_name between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotBetween(String value1, String value2) {
            addCriterion("service_name not between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceHostIsNull() {
            addCriterion("service_host is null");
            return (Criteria) this;
        }

        public Criteria andServiceHostIsNotNull() {
            addCriterion("service_host is not null");
            return (Criteria) this;
        }

        public Criteria andServiceHostEqualTo(String value) {
            addCriterion("service_host =", value, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostNotEqualTo(String value) {
            addCriterion("service_host <>", value, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostGreaterThan(String value) {
            addCriterion("service_host >", value, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostGreaterThanOrEqualTo(String value) {
            addCriterion("service_host >=", value, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostLessThan(String value) {
            addCriterion("service_host <", value, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostLessThanOrEqualTo(String value) {
            addCriterion("service_host <=", value, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostLike(String value) {
            addCriterion("service_host like", value, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostNotLike(String value) {
            addCriterion("service_host not like", value, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostIn(List<String> values) {
            addCriterion("service_host in", values, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostNotIn(List<String> values) {
            addCriterion("service_host not in", values, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostBetween(String value1, String value2) {
            addCriterion("service_host between", value1, value2, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andServiceHostNotBetween(String value1, String value2) {
            addCriterion("service_host not between", value1, value2, "serviceHost");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameIsNull() {
            addCriterion("create_username is null");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameIsNotNull() {
            addCriterion("create_username is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameEqualTo(String value) {
            addCriterion("create_username =", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameNotEqualTo(String value) {
            addCriterion("create_username <>", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameGreaterThan(String value) {
            addCriterion("create_username >", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("create_username >=", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameLessThan(String value) {
            addCriterion("create_username <", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameLessThanOrEqualTo(String value) {
            addCriterion("create_username <=", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameLike(String value) {
            addCriterion("create_username like", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameNotLike(String value) {
            addCriterion("create_username not like", value, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameIn(List<String> values) {
            addCriterion("create_username in", values, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameNotIn(List<String> values) {
            addCriterion("create_username not in", values, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameBetween(String value1, String value2) {
            addCriterion("create_username between", value1, value2, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCreateUsernameNotBetween(String value1, String value2) {
            addCriterion("create_username not between", value1, value2, "createUsername");
            return (Criteria) this;
        }

        public Criteria andCheckAuthIsNull() {
            addCriterion("check_auth is null");
            return (Criteria) this;
        }

        public Criteria andCheckAuthIsNotNull() {
            addCriterion("check_auth is not null");
            return (Criteria) this;
        }

        public Criteria andCheckAuthEqualTo(String value) {
            addCriterion("check_auth =", value, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthNotEqualTo(String value) {
            addCriterion("check_auth <>", value, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthGreaterThan(String value) {
            addCriterion("check_auth >", value, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthGreaterThanOrEqualTo(String value) {
            addCriterion("check_auth >=", value, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthLessThan(String value) {
            addCriterion("check_auth <", value, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthLessThanOrEqualTo(String value) {
            addCriterion("check_auth <=", value, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthLike(String value) {
            addCriterion("check_auth like", value, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthNotLike(String value) {
            addCriterion("check_auth not like", value, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthIn(List<String> values) {
            addCriterion("check_auth in", values, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthNotIn(List<String> values) {
            addCriterion("check_auth not in", values, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthBetween(String value1, String value2) {
            addCriterion("check_auth between", value1, value2, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andCheckAuthNotBetween(String value1, String value2) {
            addCriterion("check_auth not between", value1, value2, "checkAuth");
            return (Criteria) this;
        }

        public Criteria andServicePortIsNull() {
            addCriterion("service_port is null");
            return (Criteria) this;
        }

        public Criteria andServicePortIsNotNull() {
            addCriterion("service_port is not null");
            return (Criteria) this;
        }

        public Criteria andServicePortEqualTo(String value) {
            addCriterion("service_port =", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortNotEqualTo(String value) {
            addCriterion("service_port <>", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortGreaterThan(String value) {
            addCriterion("service_port >", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortGreaterThanOrEqualTo(String value) {
            addCriterion("service_port >=", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortLessThan(String value) {
            addCriterion("service_port <", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortLessThanOrEqualTo(String value) {
            addCriterion("service_port <=", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortLike(String value) {
            addCriterion("service_port like", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortNotLike(String value) {
            addCriterion("service_port not like", value, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortIn(List<String> values) {
            addCriterion("service_port in", values, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortNotIn(List<String> values) {
            addCriterion("service_port not in", values, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortBetween(String value1, String value2) {
            addCriterion("service_port between", value1, value2, "servicePort");
            return (Criteria) this;
        }

        public Criteria andServicePortNotBetween(String value1, String value2) {
            addCriterion("service_port not between", value1, value2, "servicePort");
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

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andIsMenuIsNull() {
            addCriterion("is_menu is null");
            return (Criteria) this;
        }

        public Criteria andIsMenuIsNotNull() {
            addCriterion("is_menu is not null");
            return (Criteria) this;
        }

        public Criteria andIsMenuEqualTo(Boolean value) {
            addCriterion("is_menu =", value, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuNotEqualTo(Boolean value) {
            addCriterion("is_menu <>", value, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuGreaterThan(Boolean value) {
            addCriterion("is_menu >", value, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_menu >=", value, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuLessThan(Boolean value) {
            addCriterion("is_menu <", value, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuLessThanOrEqualTo(Boolean value) {
            addCriterion("is_menu <=", value, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuIn(List<Boolean> values) {
            addCriterion("is_menu in", values, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuNotIn(List<Boolean> values) {
            addCriterion("is_menu not in", values, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuBetween(Boolean value1, Boolean value2) {
            addCriterion("is_menu between", value1, value2, "isMenu");
            return (Criteria) this;
        }

        public Criteria andIsMenuNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_menu not between", value1, value2, "isMenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuIsNull() {
            addCriterion("has_submenu is null");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuIsNotNull() {
            addCriterion("has_submenu is not null");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuEqualTo(Boolean value) {
            addCriterion("has_submenu =", value, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuNotEqualTo(Boolean value) {
            addCriterion("has_submenu <>", value, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuGreaterThan(Boolean value) {
            addCriterion("has_submenu >", value, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuGreaterThanOrEqualTo(Boolean value) {
            addCriterion("has_submenu >=", value, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuLessThan(Boolean value) {
            addCriterion("has_submenu <", value, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuLessThanOrEqualTo(Boolean value) {
            addCriterion("has_submenu <=", value, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuIn(List<Boolean> values) {
            addCriterion("has_submenu in", values, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuNotIn(List<Boolean> values) {
            addCriterion("has_submenu not in", values, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuBetween(Boolean value1, Boolean value2) {
            addCriterion("has_submenu between", value1, value2, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andHasSubmenuNotBetween(Boolean value1, Boolean value2) {
            addCriterion("has_submenu not between", value1, value2, "hasSubmenu");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIsNull() {
            addCriterion("menu_url is null");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIsNotNull() {
            addCriterion("menu_url is not null");
            return (Criteria) this;
        }

        public Criteria andMenuUrlEqualTo(String value) {
            addCriterion("menu_url =", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotEqualTo(String value) {
            addCriterion("menu_url <>", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlGreaterThan(String value) {
            addCriterion("menu_url >", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlGreaterThanOrEqualTo(String value) {
            addCriterion("menu_url >=", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLessThan(String value) {
            addCriterion("menu_url <", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLessThanOrEqualTo(String value) {
            addCriterion("menu_url <=", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLike(String value) {
            addCriterion("menu_url like", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotLike(String value) {
            addCriterion("menu_url not like", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIn(List<String> values) {
            addCriterion("menu_url in", values, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotIn(List<String> values) {
            addCriterion("menu_url not in", values, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlBetween(String value1, String value2) {
            addCriterion("menu_url between", value1, value2, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotBetween(String value1, String value2) {
            addCriterion("menu_url not between", value1, value2, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNull() {
            addCriterion("menu_name is null");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNotNull() {
            addCriterion("menu_name is not null");
            return (Criteria) this;
        }

        public Criteria andMenuNameEqualTo(String value) {
            addCriterion("menu_name =", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotEqualTo(String value) {
            addCriterion("menu_name <>", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThan(String value) {
            addCriterion("menu_name >", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThanOrEqualTo(String value) {
            addCriterion("menu_name >=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThan(String value) {
            addCriterion("menu_name <", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThanOrEqualTo(String value) {
            addCriterion("menu_name <=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLike(String value) {
            addCriterion("menu_name like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotLike(String value) {
            addCriterion("menu_name not like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameIn(List<String> values) {
            addCriterion("menu_name in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotIn(List<String> values) {
            addCriterion("menu_name not in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameBetween(String value1, String value2) {
            addCriterion("menu_name between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotBetween(String value1, String value2) {
            addCriterion("menu_name not between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andTargetIdIsNull() {
            addCriterion("target_id is null");
            return (Criteria) this;
        }

        public Criteria andTargetIdIsNotNull() {
            addCriterion("target_id is not null");
            return (Criteria) this;
        }

        public Criteria andTargetIdEqualTo(String value) {
            addCriterion("target_id =", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotEqualTo(String value) {
            addCriterion("target_id <>", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThan(String value) {
            addCriterion("target_id >", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdGreaterThanOrEqualTo(String value) {
            addCriterion("target_id >=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThan(String value) {
            addCriterion("target_id <", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLessThanOrEqualTo(String value) {
            addCriterion("target_id <=", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdLike(String value) {
            addCriterion("target_id like", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotLike(String value) {
            addCriterion("target_id not like", value, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdIn(List<String> values) {
            addCriterion("target_id in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotIn(List<String> values) {
            addCriterion("target_id not in", values, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdBetween(String value1, String value2) {
            addCriterion("target_id between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andTargetIdNotBetween(String value1, String value2) {
            addCriterion("target_id not between", value1, value2, "targetId");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudIsNull() {
            addCriterion("template_crud is null");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudIsNotNull() {
            addCriterion("template_crud is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudEqualTo(String value) {
            addCriterion("template_crud =", value, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudNotEqualTo(String value) {
            addCriterion("template_crud <>", value, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudGreaterThan(String value) {
            addCriterion("template_crud >", value, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudGreaterThanOrEqualTo(String value) {
            addCriterion("template_crud >=", value, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudLessThan(String value) {
            addCriterion("template_crud <", value, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudLessThanOrEqualTo(String value) {
            addCriterion("template_crud <=", value, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudLike(String value) {
            addCriterion("template_crud like", value, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudNotLike(String value) {
            addCriterion("template_crud not like", value, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudIn(List<String> values) {
            addCriterion("template_crud in", values, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudNotIn(List<String> values) {
            addCriterion("template_crud not in", values, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudBetween(String value1, String value2) {
            addCriterion("template_crud between", value1, value2, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andTemplateCrudNotBetween(String value1, String value2) {
            addCriterion("template_crud not between", value1, value2, "templateCrud");
            return (Criteria) this;
        }

        public Criteria andCheckSafeIsNull() {
            addCriterion("check_safe is null");
            return (Criteria) this;
        }

        public Criteria andCheckSafeIsNotNull() {
            addCriterion("check_safe is not null");
            return (Criteria) this;
        }

        public Criteria andCheckSafeEqualTo(String value) {
            addCriterion("check_safe =", value, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeNotEqualTo(String value) {
            addCriterion("check_safe <>", value, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeGreaterThan(String value) {
            addCriterion("check_safe >", value, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeGreaterThanOrEqualTo(String value) {
            addCriterion("check_safe >=", value, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeLessThan(String value) {
            addCriterion("check_safe <", value, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeLessThanOrEqualTo(String value) {
            addCriterion("check_safe <=", value, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeLike(String value) {
            addCriterion("check_safe like", value, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeNotLike(String value) {
            addCriterion("check_safe not like", value, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeIn(List<String> values) {
            addCriterion("check_safe in", values, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeNotIn(List<String> values) {
            addCriterion("check_safe not in", values, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeBetween(String value1, String value2) {
            addCriterion("check_safe between", value1, value2, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andCheckSafeNotBetween(String value1, String value2) {
            addCriterion("check_safe not between", value1, value2, "checkSafe");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiIsNull() {
            addCriterion("role_menu_api is null");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiIsNotNull() {
            addCriterion("role_menu_api is not null");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiEqualTo(String value) {
            addCriterion("role_menu_api =", value, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiNotEqualTo(String value) {
            addCriterion("role_menu_api <>", value, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiGreaterThan(String value) {
            addCriterion("role_menu_api >", value, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiGreaterThanOrEqualTo(String value) {
            addCriterion("role_menu_api >=", value, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiLessThan(String value) {
            addCriterion("role_menu_api <", value, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiLessThanOrEqualTo(String value) {
            addCriterion("role_menu_api <=", value, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiLike(String value) {
            addCriterion("role_menu_api like", value, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiNotLike(String value) {
            addCriterion("role_menu_api not like", value, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiIn(List<String> values) {
            addCriterion("role_menu_api in", values, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiNotIn(List<String> values) {
            addCriterion("role_menu_api not in", values, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiBetween(String value1, String value2) {
            addCriterion("role_menu_api between", value1, value2, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRoleMenuApiNotBetween(String value1, String value2) {
            addCriterion("role_menu_api not between", value1, value2, "roleMenuApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiIsNull() {
            addCriterion("role_permission_api is null");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiIsNotNull() {
            addCriterion("role_permission_api is not null");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiEqualTo(String value) {
            addCriterion("role_permission_api =", value, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiNotEqualTo(String value) {
            addCriterion("role_permission_api <>", value, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiGreaterThan(String value) {
            addCriterion("role_permission_api >", value, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiGreaterThanOrEqualTo(String value) {
            addCriterion("role_permission_api >=", value, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiLessThan(String value) {
            addCriterion("role_permission_api <", value, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiLessThanOrEqualTo(String value) {
            addCriterion("role_permission_api <=", value, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiLike(String value) {
            addCriterion("role_permission_api like", value, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiNotLike(String value) {
            addCriterion("role_permission_api not like", value, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiIn(List<String> values) {
            addCriterion("role_permission_api in", values, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiNotIn(List<String> values) {
            addCriterion("role_permission_api not in", values, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiBetween(String value1, String value2) {
            addCriterion("role_permission_api between", value1, value2, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andRolePermissionApiNotBetween(String value1, String value2) {
            addCriterion("role_permission_api not between", value1, value2, "rolePermissionApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiIsNull() {
            addCriterion("menu_tree_api is null");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiIsNotNull() {
            addCriterion("menu_tree_api is not null");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiEqualTo(String value) {
            addCriterion("menu_tree_api =", value, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiNotEqualTo(String value) {
            addCriterion("menu_tree_api <>", value, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiGreaterThan(String value) {
            addCriterion("menu_tree_api >", value, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiGreaterThanOrEqualTo(String value) {
            addCriterion("menu_tree_api >=", value, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiLessThan(String value) {
            addCriterion("menu_tree_api <", value, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiLessThanOrEqualTo(String value) {
            addCriterion("menu_tree_api <=", value, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiLike(String value) {
            addCriterion("menu_tree_api like", value, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiNotLike(String value) {
            addCriterion("menu_tree_api not like", value, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiIn(List<String> values) {
            addCriterion("menu_tree_api in", values, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiNotIn(List<String> values) {
            addCriterion("menu_tree_api not in", values, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiBetween(String value1, String value2) {
            addCriterion("menu_tree_api between", value1, value2, "menuTreeApi");
            return (Criteria) this;
        }

        public Criteria andMenuTreeApiNotBetween(String value1, String value2) {
            addCriterion("menu_tree_api not between", value1, value2, "menuTreeApi");
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

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
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