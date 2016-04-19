package com.dev.irobot.tool;

/**
 * Created by Jacky on 2016/4/17.
 */
public class ViewDump {
    private String key;
    private String attachedActivity;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ViewDump dump = (ViewDump) o;

        if (key != null ? !key.equals(dump.key) : dump.key != null) {
            return false;
        }
        return attachedActivity != null ? attachedActivity.equals(dump.attachedActivity) : dump.attachedActivity == null;

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (attachedActivity != null ? attachedActivity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ViewDump{");
        sb.append("key='").append(key).append('\'');
        sb.append(", attachedActivity='").append(attachedActivity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
