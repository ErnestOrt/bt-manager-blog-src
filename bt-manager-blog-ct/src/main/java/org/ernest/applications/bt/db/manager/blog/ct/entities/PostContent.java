package org.ernest.applications.bt.db.manager.blog.ct.entities;


public class PostContent {
	
	private String _id;
	private String _rev;
	
	private String content;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}