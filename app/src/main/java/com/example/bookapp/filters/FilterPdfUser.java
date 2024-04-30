package com.example.bookapp.filters;

import android.widget.Filter;

import com.example.bookapp.adapters.AdapterPdfUser;
import com.example.bookapp.models.ModelPdf;

import java.util.ArrayList;

public class FilterPdfUser extends Filter {

    //arrayList in which we want to search
    ArrayList<ModelPdf> filterList;
    //adapter in which filter need to be implement
    AdapterPdfUser adapterPdfUser;

    public FilterPdfUser(ArrayList<ModelPdf> filterList, AdapterPdfUser adapterPdfUser) {
        this.filterList = filterList;
        this.adapterPdfUser = adapterPdfUser;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        //value to be searched should not to be null/empty
        if(charSequence!=null||charSequence.length()>0){
            //not null not empty
            //change to uppercase  or lower to avoid case sensitivity
            charSequence= charSequence.toString().toUpperCase();
            ArrayList<ModelPdf> filteredModels = new ArrayList<>();

            for(int i =0 ;i<filterList.size();i++){
                //validable
                if(filterList.get(i).getTitle().toUpperCase().contains(charSequence)){
                    //search matches, add to list
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }else{
            //empty or null, make original list/result
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        //apply filter changes
        adapterPdfUser.pdfArrayList = (ArrayList<ModelPdf>) filterResults.values;
        //notify changes
        adapterPdfUser.notifyDataSetChanged();
    }
}
