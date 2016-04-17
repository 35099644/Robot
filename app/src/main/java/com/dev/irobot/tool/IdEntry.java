package com.dev.irobot.tool;

/**
 * Created by Jacky on 2016/4/17.
 */
public class IdEntry {
    /**
     * 我们自己声明的按钮的id
     */
    private final String id;
    /**
     * 我们自己声明的按钮的作用, 控件校准的时候会在对话框中显示
     */
    private final String name;

    public IdEntry(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IdEntry idEntry = (IdEntry) o;

        return id != null ? id.equals(idEntry.id) : idEntry.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IdEntry{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
