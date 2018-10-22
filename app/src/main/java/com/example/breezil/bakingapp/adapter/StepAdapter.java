package com.example.breezil.bakingapp.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.breezil.bakingapp.ClickCallBack.StepClickListener;
import com.example.breezil.bakingapp.R;
import com.example.breezil.bakingapp.databinding.StepItemBinding;
import com.example.breezil.bakingapp.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder>{

    private List<Step> stepList;
    StepClickListener stepClickListener;



    public StepAdapter(List<Step> stepList, StepClickListener stepClickListener) {
        this.stepList = stepList;
        this.stepClickListener = stepClickListener;
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StepItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.step_item,parent,false);


        return new StepHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder holder, int position) {
        String count = String.valueOf(position);
        Step step = stepList.get(position);
        holder.bind(step,stepClickListener,count);

    }

    @Override
    public int getItemCount() {
        if(stepList == null){
            return 0;
        }
        return stepList.size();
    }

    public void setList(List<Step> steps){
        this.stepList = steps;
        notifyDataSetChanged();
    }

    public class StepHolder extends RecyclerView.ViewHolder{
        StepItemBinding stepItemBinding;

        public StepHolder(StepItemBinding binding) {
            super(binding.getRoot());
            this.stepItemBinding =  binding;
        }
        void bind(Step step, StepClickListener clickCallback , String count){
//           stepItemBinding.step.setText(step.getId());
            itemView.setOnClickListener(v -> clickCallback.showDetails(step));
           stepItemBinding.stepName.setText(step.getShortDescription());
           stepItemBinding.stepCount.setText(count);
        }
    }
}
