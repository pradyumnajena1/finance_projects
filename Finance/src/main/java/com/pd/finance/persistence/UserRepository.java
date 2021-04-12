package com.pd.finance.persistence;

import com.pd.finance.model.Equity;
import com.pd.finance.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    @Query(value="{ 'email' : ?0 }")
    public User findByEmail(String name);
}
