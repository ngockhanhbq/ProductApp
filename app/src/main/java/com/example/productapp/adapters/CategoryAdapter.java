package com.example.productapp.adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.productapp.R;
import com.example.productapp.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categoryList;
    private Context context;

    private CategorySelectionListener listener;

    private ImageLoader mImageLoader;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public CategoryAdapter(Context context, List<Category> categoryList, CategorySelectionListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);
        holder.itemView.setSelected(selectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public interface CategorySelectionListener {
        void onCategorySelected(Category category);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryNameTextView;
        private final ImageView categoryImageView;
        private final LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            categoryImageView = itemView.findViewById(R.id.categoryImageView);
            layout = itemView.findViewById(R.id.layout_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previousSelectedPosition = selectedPosition;
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(previousSelectedPosition);
                    notifyItemChanged(selectedPosition);

                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        Category selectedCategory = categoryList.get(position);
                        listener.onCategorySelected(selectedCategory);
                    }
                }
            });
        }

        public void bind(Category category) {
            categoryNameTextView.setText(category.getTenLoaiSP());
            Glide.with(context).load(category.getHinhAnh()).into(categoryImageView);
            if (selectedPosition == getAdapterPosition()) {
                int color = ContextCompat.getColor(context, R.color.pink_100);
                layout.setBackgroundColor(color);
            } else {
                // Set default background color here
                layout.setBackgroundColor(Color.WHITE);
            }
            categoryImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(category.getMaLoaiSP());
                }
            });
        }
    }
}
