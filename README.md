## master_recycler

### 1.PagedAdapter and SelectedAdapter. 

&nbsp;&nbsp;&nbsp;&nbsp; A PagedAdapter is used to  add a function to the RecyclerView that when it slide to the bottom, load another page data to show. while a SelectedAdapter is used to add a function to a RecyclerView that if its a item is clicked, the backgrand of the item will change to selected state.    
&nbsp;&nbsp;&nbsp;&nbsp; The both proxy adapter are decoupled from the orignal normal adapter, which hold the data set.   
&nbsp;&nbsp;&nbsp;&nbsp; Relating classes are in the *_5_page_selected* package.

&nbsp;&nbsp;&nbsp;&nbsp; You can use the adapters like this: (May be you should add another more code to initalize a RecyclerView, just as the Activity5.java shows)
```java
        adapter = new NormalAdapter5();
        selectedAdapter = new SelectedAdapter5(adapter);
        pageAdapter = new PageAdapter5(selectedAdapter);
        
        recyclerView.setAdapter(pageAdapter);
        recyclerView.addOnScrollListener(new LoadMoreScrollListener5() {
            @Override
            public void loadMore(int loadedItem, int pageSize) {
                // load next page data
                presenter.loadMore(loadedItem, pageSize);
            }
        });
        
        // load first page data
        presenter.loadMore(0, pageAdapter.pageSize());
```

&nbsp;&nbsp;&nbsp;&nbsp; And the app show: 

![load paged data and high-light selected item](https://github.com/tanhuang01/master_recycler/blob/master/pic/master_recycler_1_page_selected.gif)
