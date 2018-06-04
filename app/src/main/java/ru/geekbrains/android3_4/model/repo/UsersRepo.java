package ru.geekbrains.android3_4.model.repo;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import ru.geekbrains.android3_4.model.api.ApiHolder;
import ru.geekbrains.android3_4.model.entity.User;
import ru.geekbrains.android3_4.model.entity.UserRepository;

public class UsersRepo {
    public Single<User> getUser(String username) {
        return ApiHolder.getApi().getUser(username);
    }

    public Single<List<UserRepository>> getUserRepositories(String username) {
        return ApiHolder.getApi().getUserRepositories(username);
    }
}
