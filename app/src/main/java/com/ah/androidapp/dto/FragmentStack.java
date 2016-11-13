package com.ah.androidapp.dto;

public class FragmentStack {

    private boolean showSearch;

    private boolean showTitle;

    private String id;

    private String title;

    public FragmentStack(String title, boolean showSearch) {

        this.showSearch = showSearch;

        if (title.isEmpty()){
            this.showTitle = false;
        }else {
            this.showTitle = true;
        }

        this.title = title;
    }

    public boolean getSearchShow()
    {
        return this.showSearch;
    }

    public void setShowSearch(boolean showSearch){
        this.showSearch = showSearch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public boolean getShowTitle()
    {
        return this.showTitle;
    }

    public void setShowTitle(boolean showTitle)
    {
        this.showTitle = showTitle;
    }
}
