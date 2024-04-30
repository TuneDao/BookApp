package com.example.bookapp.filters;

import android.widget.Filter;

import com.example.bookapp.adapters.AdapterCategory;
import com.example.bookapp.models.ModelCategory;

import java.util.ArrayList;

public class FilterCategory extends Filter {
    // show danh sách khi muốn tìm kiếm
    ArrayList<ModelCategory> filterList;
    // adapter khi lọc ra dữ liệu cần
    AdapterCategory adapterCategory;
    // contructor

    public FilterCategory(ArrayList<ModelCategory> filterList, AdapterCategory adapterCategory) {
        this.filterList = filterList;
        this.adapterCategory = adapterCategory;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        // giá trị không được để trống
        if(charSequence != null && charSequence.length()>0){
            //change Upper case, or lower case to avoid case
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<ModelCategory> filteredModels = new ArrayList<>();
            for (int i = 0;i<filterList.size();i++){
                //validate
                if(filterList.get(i).getCategory().toUpperCase().contains(charSequence)){
                    //add to filteredModel
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        // apply filter change
        adapterCategory.modelCategoryList = (ArrayList<ModelCategory>) filterResults.values;
        // notify change
        adapterCategory.notifyDataSetChanged();
    }
}
