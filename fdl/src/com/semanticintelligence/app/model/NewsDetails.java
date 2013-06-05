package com.semanticintelligence.app.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDetails {

	private String url;
	private String title;

	public NewsDetails(Builder builder) {
		this.url = builder.url;
		this.title = builder.title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "NewsDetails [url=" + url + ", title=" + title + "]";
	}

	public static class Builder {

		private String url;
		private String title;

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public NewsDetails build() {
			return new NewsDetails(this);
		}
	}

	public static Map<Long, NewsDetails> mapNewsDetails(List<Object[]> result) {
		Map<Long, NewsDetails> newsIdDetails = new HashMap<Long, NewsDetails>();

		BigDecimal newsId;
		String title;
		String url;

		NewsDetails newsDerails;

		for (Object[] objects : result) {
			newsId = (BigDecimal) objects[0];
			url = objects[1] != null ? objects[1].toString() : "";
			title = objects[2] != null ? objects[2].toString() : "";

			newsDerails = new NewsDetails.Builder().title(title).url(url).build();

			newsIdDetails.put(newsId.longValue(), newsDerails);
		}

		return newsIdDetails;
	}

}
