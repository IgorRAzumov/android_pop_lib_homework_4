package ru.geekbrains.android3_4.model.api;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.geekbrains.android3_4.model.entity.User;
import ru.geekbrains.android3_4.model.entity.UserRepository;

public interface ApiService
{
    @GET("/users/{user}")
    Single<User> getUser(@Path("user") String username);

    @GET("/users/{user}/repos")
    Single<List<UserRepository>> getUserRepositories(@Path("user") String username);
}
