package com.duduyixia.config.server.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfigDataExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public ConfigDataExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
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

        public Criteria andNamespaceIsNull() {
            addCriterion("namespace is null");
            return (Criteria) this;
        }

        public Criteria andNamespaceIsNotNull() {
            addCriterion("namespace is not null");
            return (Criteria) this;
        }

        public Criteria andNamespaceEqualTo(String value) {
            addCriterion("namespace =", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotEqualTo(String value) {
            addCriterion("namespace <>", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceGreaterThan(String value) {
            addCriterion("namespace >", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceGreaterThanOrEqualTo(String value) {
            addCriterion("namespace >=", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLessThan(String value) {
            addCriterion("namespace <", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLessThanOrEqualTo(String value) {
            addCriterion("namespace <=", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLike(String value) {
            addCriterion("namespace like", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotLike(String value) {
            addCriterion("namespace not like", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceIn(List<String> values) {
            addCriterion("namespace in", values, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotIn(List<String> values) {
            addCriterion("namespace not in", values, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceBetween(String value1, String value2) {
            addCriterion("namespace between", value1, value2, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotBetween(String value1, String value2) {
            addCriterion("namespace not between", value1, value2, "namespace");
            return (Criteria) this;
        }

        public Criteria andEnvIsNull() {
            addCriterion("env is null");
            return (Criteria) this;
        }

        public Criteria andEnvIsNotNull() {
            addCriterion("env is not null");
            return (Criteria) this;
        }

        public Criteria andEnvEqualTo(String value) {
            addCriterion("env =", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotEqualTo(String value) {
            addCriterion("env <>", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvGreaterThan(String value) {
            addCriterion("env >", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvGreaterThanOrEqualTo(String value) {
            addCriterion("env >=", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvLessThan(String value) {
            addCriterion("env <", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvLessThanOrEqualTo(String value) {
            addCriterion("env <=", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvLike(String value) {
            addCriterion("env like", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotLike(String value) {
            addCriterion("env not like", value, "env");
            return (Criteria) this;
        }

        public Criteria andEnvIn(List<String> values) {
            addCriterion("env in", values, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotIn(List<String> values) {
            addCriterion("env not in", values, "env");
            return (Criteria) this;
        }

        public Criteria andEnvBetween(String value1, String value2) {
            addCriterion("env between", value1, value2, "env");
            return (Criteria) this;
        }

        public Criteria andEnvNotBetween(String value1, String value2) {
            addCriterion("env not between", value1, value2, "env");
            return (Criteria) this;
        }

        public Criteria andAppIsNull() {
            addCriterion("app is null");
            return (Criteria) this;
        }

        public Criteria andAppIsNotNull() {
            addCriterion("app is not null");
            return (Criteria) this;
        }

        public Criteria andAppEqualTo(String value) {
            addCriterion("app =", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppNotEqualTo(String value) {
            addCriterion("app <>", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppGreaterThan(String value) {
            addCriterion("app >", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppGreaterThanOrEqualTo(String value) {
            addCriterion("app >=", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppLessThan(String value) {
            addCriterion("app <", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppLessThanOrEqualTo(String value) {
            addCriterion("app <=", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppLike(String value) {
            addCriterion("app like", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppNotLike(String value) {
            addCriterion("app not like", value, "app");
            return (Criteria) this;
        }

        public Criteria andAppIn(List<String> values) {
            addCriterion("app in", values, "app");
            return (Criteria) this;
        }

        public Criteria andAppNotIn(List<String> values) {
            addCriterion("app not in", values, "app");
            return (Criteria) this;
        }

        public Criteria andAppBetween(String value1, String value2) {
            addCriterion("app between", value1, value2, "app");
            return (Criteria) this;
        }

        public Criteria andAppNotBetween(String value1, String value2) {
            addCriterion("app not between", value1, value2, "app");
            return (Criteria) this;
        }

        public Criteria andGroupIsNull() {
            addCriterion("group is null");
            return (Criteria) this;
        }

        public Criteria andGroupIsNotNull() {
            addCriterion("group is not null");
            return (Criteria) this;
        }

        public Criteria andGroupEqualTo(String value) {
            addCriterion("group =", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotEqualTo(String value) {
            addCriterion("group <>", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupGreaterThan(String value) {
            addCriterion("group >", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupGreaterThanOrEqualTo(String value) {
            addCriterion("group >=", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupLessThan(String value) {
            addCriterion("group <", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupLessThanOrEqualTo(String value) {
            addCriterion("group <=", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupLike(String value) {
            addCriterion("group like", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotLike(String value) {
            addCriterion("group not like", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupIn(List<String> values) {
            addCriterion("group in", values, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotIn(List<String> values) {
            addCriterion("group not in", values, "group");
            return (Criteria) this;
        }

        public Criteria andGroupBetween(String value1, String value2) {
            addCriterion("group between", value1, value2, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotBetween(String value1, String value2) {
            addCriterion("group not between", value1, value2, "group");
            return (Criteria) this;
        }

        public Criteria andConfigIsNull() {
            addCriterion("config is null");
            return (Criteria) this;
        }

        public Criteria andConfigIsNotNull() {
            addCriterion("config is not null");
            return (Criteria) this;
        }

        public Criteria andConfigEqualTo(String value) {
            addCriterion("config =", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigNotEqualTo(String value) {
            addCriterion("config <>", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigGreaterThan(String value) {
            addCriterion("config >", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigGreaterThanOrEqualTo(String value) {
            addCriterion("config >=", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigLessThan(String value) {
            addCriterion("config <", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigLessThanOrEqualTo(String value) {
            addCriterion("config <=", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigLike(String value) {
            addCriterion("config like", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigNotLike(String value) {
            addCriterion("config not like", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigIn(List<String> values) {
            addCriterion("config in", values, "config");
            return (Criteria) this;
        }

        public Criteria andConfigNotIn(List<String> values) {
            addCriterion("config not in", values, "config");
            return (Criteria) this;
        }

        public Criteria andConfigBetween(String value1, String value2) {
            addCriterion("config between", value1, value2, "config");
            return (Criteria) this;
        }

        public Criteria andConfigNotBetween(String value1, String value2) {
            addCriterion("config not between", value1, value2, "config");
            return (Criteria) this;
        }

        public Criteria andMd5IsNull() {
            addCriterion("md5 is null");
            return (Criteria) this;
        }

        public Criteria andMd5IsNotNull() {
            addCriterion("md5 is not null");
            return (Criteria) this;
        }

        public Criteria andMd5EqualTo(String value) {
            addCriterion("md5 =", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotEqualTo(String value) {
            addCriterion("md5 <>", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThan(String value) {
            addCriterion("md5 >", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThanOrEqualTo(String value) {
            addCriterion("md5 >=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThan(String value) {
            addCriterion("md5 <", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThanOrEqualTo(String value) {
            addCriterion("md5 <=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Like(String value) {
            addCriterion("md5 like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotLike(String value) {
            addCriterion("md5 not like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5In(List<String> values) {
            addCriterion("md5 in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotIn(List<String> values) {
            addCriterion("md5 not in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Between(String value1, String value2) {
            addCriterion("md5 between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotBetween(String value1, String value2) {
            addCriterion("md5 not between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedIsNull() {
            addCriterion("mark_deleted is null");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedIsNotNull() {
            addCriterion("mark_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedEqualTo(Boolean value) {
            addCriterion("mark_deleted =", value, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedNotEqualTo(Boolean value) {
            addCriterion("mark_deleted <>", value, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedGreaterThan(Boolean value) {
            addCriterion("mark_deleted >", value, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("mark_deleted >=", value, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedLessThan(Boolean value) {
            addCriterion("mark_deleted <", value, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("mark_deleted <=", value, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedIn(List<Boolean> values) {
            addCriterion("mark_deleted in", values, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedNotIn(List<Boolean> values) {
            addCriterion("mark_deleted not in", values, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("mark_deleted between", value1, value2, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andMarkDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("mark_deleted not between", value1, value2, "markDeleted");
            return (Criteria) this;
        }

        public Criteria andBetaIsNull() {
            addCriterion("beta is null");
            return (Criteria) this;
        }

        public Criteria andBetaIsNotNull() {
            addCriterion("beta is not null");
            return (Criteria) this;
        }

        public Criteria andBetaEqualTo(Boolean value) {
            addCriterion("beta =", value, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaNotEqualTo(Boolean value) {
            addCriterion("beta <>", value, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaGreaterThan(Boolean value) {
            addCriterion("beta >", value, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaGreaterThanOrEqualTo(Boolean value) {
            addCriterion("beta >=", value, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaLessThan(Boolean value) {
            addCriterion("beta <", value, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaLessThanOrEqualTo(Boolean value) {
            addCriterion("beta <=", value, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaIn(List<Boolean> values) {
            addCriterion("beta in", values, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaNotIn(List<Boolean> values) {
            addCriterion("beta not in", values, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaBetween(Boolean value1, Boolean value2) {
            addCriterion("beta between", value1, value2, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaNotBetween(Boolean value1, Boolean value2) {
            addCriterion("beta not between", value1, value2, "beta");
            return (Criteria) this;
        }

        public Criteria andBetaMd5IsNull() {
            addCriterion("beta_md5 is null");
            return (Criteria) this;
        }

        public Criteria andBetaMd5IsNotNull() {
            addCriterion("beta_md5 is not null");
            return (Criteria) this;
        }

        public Criteria andBetaMd5EqualTo(String value) {
            addCriterion("beta_md5 =", value, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5NotEqualTo(String value) {
            addCriterion("beta_md5 <>", value, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5GreaterThan(String value) {
            addCriterion("beta_md5 >", value, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5GreaterThanOrEqualTo(String value) {
            addCriterion("beta_md5 >=", value, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5LessThan(String value) {
            addCriterion("beta_md5 <", value, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5LessThanOrEqualTo(String value) {
            addCriterion("beta_md5 <=", value, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5Like(String value) {
            addCriterion("beta_md5 like", value, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5NotLike(String value) {
            addCriterion("beta_md5 not like", value, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5In(List<String> values) {
            addCriterion("beta_md5 in", values, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5NotIn(List<String> values) {
            addCriterion("beta_md5 not in", values, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5Between(String value1, String value2) {
            addCriterion("beta_md5 between", value1, value2, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andBetaMd5NotBetween(String value1, String value2) {
            addCriterion("beta_md5 not between", value1, value2, "betaMd5");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table config_data
     *
     * @mbggenerated do_not_delete_during_merge Sun Oct 27 20:18:16 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table config_data
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
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