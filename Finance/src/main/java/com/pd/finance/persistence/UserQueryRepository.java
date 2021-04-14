package com.pd.finance.persistence;

import com.pd.finance.model.UserEquityQuery;
import com.pd.finance.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserQueryRepository extends MongoRepository<UserEquityQuery, Long> {
    @Query(value="{'$and': [ { 'userId' : ?0 } ,{'_id':?1} ] }")
    public UserEquityQuery findByUserIdAndId(Long userId,Long id);
}
