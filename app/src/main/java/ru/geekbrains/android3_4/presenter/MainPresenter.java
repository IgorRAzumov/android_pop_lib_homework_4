package ru.geekbrains.android3_4.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_4.model.entity.UserRepository;
import ru.geekbrains.android3_4.model.repo.UsersRepo;
import ru.geekbrains.android3_4.view.IListRepositoryRowView;
import ru.geekbrains.android3_4.view.MainView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private final String userName = "igorRazumov";

    private final Scheduler mainThreadShceduler;
    private UsersRepo usersRepo;
    private IListRepositoryPresenter repositoriesPresenter;

    class RepositoriesListPresenter implements IListRepositoryPresenter {
        List<UserRepository> repositoriesList;

        RepositoriesListPresenter() {
            this.repositoriesList = new ArrayList<>();
        }

        @Override
        public void addDta(List<UserRepository> repos) {
            repositoriesList.addAll(repos);
        }

        @Override
        public int getRepositoriesCount() {
            return repositoriesList == null ? 0 : repositoriesList.size();
        }

        @Override
        public void bindView(IListRepositoryRowView repositoryRowView) {
            UserRepository userRepository = repositoriesList
                    .get(repositoryRowView.getReposPosition());
            repositoryRowView.setRepoName(userRepository.getName());
            repositoryRowView.setRepoCreated(userRepository.getCreatedAt());
            repositoryRowView.setRepoUpdated(userRepository.getUpdatedAt());
        }
    }


    public MainPresenter(Scheduler mainThreadShceduler) {
        repositoriesPresenter = new RepositoriesListPresenter();
        this.mainThreadShceduler = mainThreadShceduler;
        usersRepo = new UsersRepo();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
        getViewState().init();
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        usersRepo.getUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadShceduler)
                .subscribe(user -> {
                    loadRepos(user.getLogin());
                    getViewState().setUsernameText(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());
                }, throwable -> {
                    getViewState().errorLoadUserData();
                    Timber.e(throwable, "Failed to get user");
                });
    }

    @SuppressLint("CheckResult")
    private void loadRepos(String username) {
        usersRepo.getUserRepositories(username)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadShceduler)
                .subscribe(repos -> {
                            repositoriesPresenter.addDta(repos);
                            getViewState().updateRepositoryList();
                        },
                        throwable -> {
                            getViewState().errorLoadUserRepos();
                            Timber.e(throwable, "Failed to get user repos");
                        });
    }

    public IListRepositoryPresenter getRepositoriesPresenter() {
        return repositoriesPresenter;
    }
}
