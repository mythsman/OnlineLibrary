package com.mythsman.onlinelibrary.component;

import com.mythsman.onlinelibrary.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by myths on 5/20/17.
 */
@Component
public class UserComponent {
    private static ThreadLocal<User> userThreadLocal=new ThreadLocal<User>();

    public User getUser(){
        return userThreadLocal.get();
    }

    public void setUser(User user){
        userThreadLocal.set(user);
    }

    public void clear(){
        userThreadLocal.remove();
    }
}
