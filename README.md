# PhotoPageView
轮播的相册
数据和动画在adapter里实现；
给photoPageView set adapter使用；

//例如：

      PhotoPageView pp = (PhotoPageView) findViewById(R.id.photo_page);
        SimplePhotoPageAdapter<Object> adapter =new SimplePhotoPageAdapter<Object>() {
            @Override
            public PhotoPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = View.inflate(parent.getContext(),R.layout.item_photo,null);
                return new PhotoPageViewHolder(view,viewType);
            }

            @Override
            public void onBindViewHolder(PhotoPageViewHolder holder, int position) {
                //bind you owrn view data
            }
        };
        adapter.addData(new Object());
        adapter.addData(new Object());
        adapter.addData(new Object());
        pp.setAdapter(adapter);
