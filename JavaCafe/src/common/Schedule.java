package common;

public class Schedule {
	private String title;
	private String startDay;
	private String endDay;
	private String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDate(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDate(String endDay) {
		this.endDay = endDay;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Schedule [title=" + title + ", startDay=" + startDay + ", endDay=" + endDay + ", url=" + url + "]";
	}
	
	
	
}
