package com.bitcup.whoseturn.model;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * History of person selection by activity
 */
public class SelectionHistory {
    private Map<Activity, List<PersonCount>> history = Maps.newHashMap();
    private Session session;

    public SelectionHistory() {
        // required for DynamoDBMapper marshalling
    }

    public Map<Activity, List<PersonCount>> getHistory() {
        return history;
    }

    public void setHistory(Map<Activity, List<PersonCount>> history) {
        this.history = history;
    }

    @JsonIgnore
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSession(String userId, String sessionId) {
        User user = User.builder().withUserId(userId).build();
        Session session = Session.builder().withUser(user).withSessionId(sessionId).build();
        setSession(session);
    }

    public void increment(String person, String activity) {
        Activity histActivity = getActivityWithName(activity);
        if (histActivity == null) {
            histActivity = new Activity(activity);
            history.put(histActivity, Lists.newArrayList());
        }
        PersonCount histPersonCount = getPersonCountWithName(person, histActivity);
        if (histPersonCount == null) {
            histPersonCount = new PersonCount(person);
            history.get(histActivity).add(histPersonCount);
        }
        histPersonCount.incrementCount();
    }

    @VisibleForTesting
    public Map<String, Integer> getCount(String activityName) {
        Map<String, Integer> count = Maps.newHashMap();
        Activity activity = getActivityWithName(activityName);
        if (activity == null) {
            return count;
        }
        for (PersonCount personCount : history.get(activity)) {
            if (!count.containsKey(personCount.getName())) {
                count.put(personCount.getName(), 0);
            }
            int current = count.get(personCount.getName());
            count.put(personCount.getName(), current + personCount.getCount());
        }
        return count;
    }

    private Activity getActivityWithName(String activityName) {
        for (Activity activity : history.keySet()) {
            if (activity.getName().equals(activityName)) {
                return activity;
            }
        }
        return null;
    }

    private PersonCount getPersonCountWithName(String personName, Activity activity) {
        for (PersonCount personCount : history.get(activity)) {
            if (personCount.getName().equals(personName)) {
                return personCount;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SelectionHistory that = (SelectionHistory) o;

        return new EqualsBuilder()
                .append(history, that.history)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(history)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("history", history)
                .toString();
    }
}
