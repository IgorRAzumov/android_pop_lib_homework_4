package ru.geekbrains.android3_4.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.android3_4.R;
import ru.geekbrains.android3_4.presenter.IListRepositoryPresenter;
import ru.geekbrains.android3_4.view.IListRepositoryRowView;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolderList> {
    private final IListRepositoryPresenter repositoriesPresenter;

    public RepositoryAdapter(IListRepositoryPresenter repositoriesPresenter) {
        this.repositoriesPresenter = repositoriesPresenter;
    }


    @NonNull
    @Override
    public ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderList(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderList holder, int position) {
        holder.position = position;
        repositoriesPresenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return repositoriesPresenter.getRepositoriesCount();
    }

    class ViewHolderList extends RecyclerView.ViewHolder implements IListRepositoryRowView {
        @BindView(R.id.tv_act_main_repo_name)
        TextView repoNameText;
        @BindView(R.id.tv_act_main_repo_created)
        TextView repoCreatedText;
        @BindView(R.id.tv_act_main_repo_updated)
        TextView repoUpdatedText;

        private int position;

        ViewHolderList(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setRepoName(String repoName) {
            repoNameText.setText(repoName);
        }

        @Override
        public void setRepoCreated(String repoCreated) {
            repoCreatedText.setText(repoCreated);
        }

        @Override
        public void setRepoUpdated(String repoUpdated) {
            repoUpdatedText.setText(repoUpdated);
        }

        @Override
        public int getReposPosition() {
            return position;
        }
    }
}
