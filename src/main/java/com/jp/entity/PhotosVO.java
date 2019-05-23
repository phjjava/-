package com.jp.entity;

import java.util.List;

public class PhotosVO {
		String albumname;//相册标题
		String remake;//相册简介
		String createtime;//创建时间
		List<?> photos;//相片列表
		public String getAlbumname() {
			return albumname;
		}
		public void setAlbumname(String albumname) {
			this.albumname = albumname;
		}
		public String getRemake() {
			return remake;
		}
		public void setRemake(String remake) {
			this.remake = remake;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public List<?> getPhotos() {
			return photos;
		}
		public void setPhotos(List<?> photos) {
			this.photos = photos;
		}
		
		
}
