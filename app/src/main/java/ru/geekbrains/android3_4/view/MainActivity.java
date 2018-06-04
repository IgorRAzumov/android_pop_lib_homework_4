package ru.geekbrains.android3_4.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.model.image.IImageLoader;
import ru.geekbrains.android3_4.model.image.android.PicassoImageLoader;
import ru.geekbrains.android3_4.presenter.MainPresenter;
import ru.geekbrains.android3_4.ui.adapters.RepositoryAdapter;
import ru.geekbrains.android3_4.ui.widgets.SpacingItemDecorator;


public class MainActivity extends MvpAppCompatActivity implements MainView {
    @BindView(R.id.iv_avatar)
    ImageView avatarImageView;
    @BindView(R.id.tv_username)
    TextView usernameTextView;
    @BindView(R.id.rv_act_main_repos_list)
    RecyclerView reposRecycler;

    @InjectPresenter
    MainPresenter presenter;

    private RepositoryAdapter repositoryAdapter;
    IImageLoader<ImageView> imageLoader;


    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        return new MainPresenter(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageLoader = new PicassoImageLoader();
    }


    @Override
    public void init() {
        reposRecycler.setLayoutManager(new LinearLayoutManager(this));
        //знаю, что надо задавать в px, а хранить в dp - сделано, для экономии времени
        Resources resources = getResources();
        reposRecycler.addItemDecoration(new SpacingItemDecorator(
                resources.getInteger(R.integer.rv_act_main_span_count),
                resources.getInteger(R.integer.rv_act_main_spacing_px),
                true));

        repositoryAdapter = new RepositoryAdapter(presenter.getRepositoriesPresenter());
        reposRecycler.setAdapter(repositoryAdapter);
    }

    @Override
    public void setUsernameText(String username) {
        usernameTextView.setText(username);
    }

    @Override
    public void loadImage(String url) {
        imageLoader.loadInto(url, avatarImageView);
    }

    @Override
    public void errorLoadUserData() {
        showErrorMessage(R.string.error_load_user);
    }

    @Override
    public void errorLoadUserRepos() {
        showErrorMessage(R.string.error_load_user_repos);
    }

    @Override
    public void updateRepositoryList() {
        repositoryAdapter.notifyDataSetChanged();
    }

    private void showErrorMessage(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
