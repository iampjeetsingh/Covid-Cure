package com.example.hp_awareness_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

class FaqAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<FAQ> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    // Provide a suitable constructor (depends on the kind of dataset)
    public FaqAdapter(List<FAQ> myDataset) {
        mDataset = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView questionTextView;
//        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
//
            questionTextView = (TextView) itemView.findViewById(R.id.question_faq);
//            messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View faqView = inflater.inflate(R.layout.item_faq, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(faqView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FAQ faq = mDataset.get(position);
        TextView questionFaq = holder.itemView.findViewById(R.id.question_faq);
        TextView answerFaq = holder.itemView.findViewById(R.id.answer_faq);

        questionFaq.setText(faq.getQuestion());
        answerFaq.setText(faq.getAnswer());

    }


    // Involves populating data into the item through holder
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
//        // Get the data model based on position
//        FAQ faq = mDataset.get(position);
//
//        // Set item views based on your views and data model
////        TextView textView = viewHolder.nameTextView;
////        textView.setText(contact.getName());
////        Button button = viewHolder.messageButton;
////        button.setText(contact.isOnline() ? "Message" : "Offline");
////        button.setEnabled(contact.isOnline());
//    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}