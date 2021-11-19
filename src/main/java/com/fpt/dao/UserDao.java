package com.fpt.dao;

import com.fpt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/*
 * User Dao extends Jpa Repository
 */
@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Long> {

    /*
     * @Method: findByLastName
     *
     * @Query by method name
     * @param: username
     *
     * @return: User
     */
    User findByLastName(String username);

    /*
     * @Method: findById
     *
     * @Query by method name
     * @param: userid
     *
     * @return: User
     */
    User findById(int userId);
}
