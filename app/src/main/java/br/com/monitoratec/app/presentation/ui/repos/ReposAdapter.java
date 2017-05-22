package br.com.monitoratec.app.presentation.ui.repos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.monitoratec.app.model.entity.Repo;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link Repo} {@link RecyclerView.Adapter}
 *
 * Created by falvojr on 1/14/17.
 */
class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private final List<Repo> mDataSet;

    ReposAdapter(List<Repo> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReposAdapter.ViewHolder holder, int position) {
        final Repo repo = mDataSet.get(position);
        holder.mTextView.setText(repo.name);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView mTextView;

        ViewHolder(View view) {
            super(view);
            //TODO (19) Butter Knife: ViewHolder
            ButterKnife.bind(this, view);
        }
    }
}
