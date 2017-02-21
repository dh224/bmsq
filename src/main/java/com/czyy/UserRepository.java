package com.czyy;

import com.czyy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by 都航本地 on 2017/2/18.
 */
public interface UserRepository extends JpaRepository <User, Integer> {
    public List<User> findByName(String name);//JpaSpecificationExecutor
    public List<User> findByPhone(String phone) ;
    public List<User> findByCollege(String college) ;
}
