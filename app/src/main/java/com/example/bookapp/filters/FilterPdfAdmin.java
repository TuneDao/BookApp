package com.example.bookapp.filters;

import android.widget.Filter;

import com.example.bookapp.adapters.AdapterCategory;
import com.example.bookapp.adapters.AdapterPdfAdmin;
import com.example.bookapp.models.ModelCategory;
import com.example.bookapp.models.ModelPdf;

import java.util.ArrayList;

public class FilterPdfAdmin extends Filter {
    // show danh sách khi muốn tìm kiếm
    ArrayList<ModelPdf> filterList;
    // adapter khi lọc ra dữ liệu cần
    AdapterPdfAdmin adapterPdfAdmin;
    // contructor

    public FilterPdfAdmin(ArrayList<ModelPdf> filterList, AdapterPdfAdmin adapterPdfAdmin) {
        this.filterList = filterList;
        this.adapterPdfAdmin = adapterPdfAdmin;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        // giá trị không được để trống
        if(charSequence != null && charSequence.length()>0){
            //change Upper case, or lower case to avoid case
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<ModelPdf> filteredModels = new ArrayList<>();
            for (int i = 0;i<filterList.size();i++){
                //validate
                if(filterList.get(i).getTitle().toUpperCase().contains(charSequence)){
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
        adapterPdfAdmin.pdfArrayList = (ArrayList<ModelPdf>) filterResults.values;
        // notify change
        adapterPdfAdmin.notifyDataSetChanged();
    }
}
