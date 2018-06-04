package ru.geekbrains.android3_4.presenter;

import java.util.List;

import ru.geekbrains.android3_4.model.entity.UserRepository;
import ru.geekbrains.android3_4.view.IListRepositoryRowView;

public interface IListRepositoryPresenter {
    void addDta(List<UserRepository> repos);

    int getRepositoriesCount();

    void bindView(IListRepositoryRowView holder);
}
