package com.dev.irobot.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacky on 2016/4/17.
 */
public class ViewDump {
    private String key;
    private String attachedActivity;
    private final List<String> values = new ArrayList<String>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAttachedActivity() {
        return attachedActivity;
    }

    public void setAttachedActivity(String attachedActivity) {
        this.attachedActivity = attachedActivity;
    }

    public List<String> getValues() {
        return values;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ViewDump viewDump = (ViewDump) o;

        if (key != null ? !key.equals(viewDump.key) : viewDump.key != null) {
            return false;
        }
        if (attachedActivity != null ? !attachedActivity.equals(viewDump.attachedActivity) : viewDump.attachedActivity != null) {
            return false;
        }
        return values != null ? values.equals(viewDump.values) : viewDump.values == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (attachedActivity != null ? attachedActivity.hashCode() : 0);
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ViewDump{");
        sb.append("key='").append(key).append('\'');
        sb.append(", attachedActivity='").append(attachedActivity).append('\'');
        sb.append(", values=").append(values);
        sb.append('}');
        return sb.toString();
    }
}
