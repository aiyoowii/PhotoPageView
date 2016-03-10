# PhotoPageView

![image](https://github.com/cegrano/PhotoPageView/raw/master/Screenshot_2016-03-10.png)

set data and animation on a help class whitch extended from BasePhotoPageAdapter；

//simple use as：

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
