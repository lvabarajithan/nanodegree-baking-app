package com.abb.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abb.bakingapp.R;
import com.abb.bakingapp.databinding.ItemRecipeStepBinding;
import com.abb.bakingapp.model.Step;

import java.util.List;

/**
 * Created by Abarajithan
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepHolder> {

    private List<Step> steps;

    private StepClickListener listener;

    private View prevItem = null;

    private boolean selectFirstItem;

    public StepsAdapter(List<Step> steps, boolean selectFirstItem, StepClickListener listener) {
        this.steps = steps;
        this.selectFirstItem = selectFirstItem;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipeStepBinding binding = ItemRecipeStepBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StepHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepHolder holder, int position) {
        final Step step = steps.get(position);
        holder.bind(step);
        holder.itemView.setOnClickListener(v -> {
            if (prevItem != null) {
                prevItem.setBackgroundResource(android.R.color.white);
            }
            holder.itemView.setBackgroundResource(R.color.colorGreySelected);
            prevItem = holder.itemView;
            listener.onStepItemClicked(holder.getAdapterPosition());
        });
        if (selectFirstItem && position == 0) {
            holder.itemView.performClick();
        }
    }

    @Override
    public int getItemCount() {
        return (this.steps == null) ? 0 : this.steps.size();
    }

    class StepHolder extends RecyclerView.ViewHolder {

        private final ItemRecipeStepBinding binding;

        StepHolder(@NonNull ItemRecipeStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Step step) {
            binding.setStep(step);
        }

    }

    public interface StepClickListener {
        public void onStepItemClicked(int stepId);
    }

}
