package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.entities.abstracts.User;

public interface UserService {

    Result confirm(int id);
    DataResult<User> getByConfirmedEmail(String email);
    DataResult<User> getByEmail(String email);

}
