package ru.geekbrains.android3_4.view;

public interface IListRepositoryRowView {
    void setRepoName(String repoName);

    void setRepoCreated(String repoCreated);

    void setRepoUpdated(String repoUpdated);

    int getReposPosition();
}
