package com.cu1.cloudnotes.utils;


import com.cu1.cloudnotes.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息 用于代替 session 对象
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUsers(User user) {
        users.set(user);
    }

    public User getUser() { return users.get(); }

    public void clear() { users.remove(); }
}
