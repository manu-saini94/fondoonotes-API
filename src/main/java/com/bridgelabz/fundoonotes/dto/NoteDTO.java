package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

public class NoteDTO {

	private Long id;
	private String title;
	private String takeanote;
	private boolean trashed;
	private boolean archived;
	private boolean pinned;
	private String color;
	private LocalDateTime reminder;
	private String labelname;
	private LocalDateTime createdtime = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(LocalDateTime createdtime) {
		this.createdtime = createdtime;
	}

	public String getLabelname() {
		return labelname;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTakeanote() {
		return takeanote;
	}

	public void setTakeanote(String takeanote) {
		this.takeanote = takeanote;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}

	public boolean isTrashed() {
		return trashed;
	}

	public void setTrashed(boolean trashed) {
		this.trashed = trashed;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	@Override
	public String toString() {
		return "NoteDTO [id=" + id + ", title=" + title + ", takeanote=" + takeanote + ", trashed=" + trashed
				+ ", archived=" + archived + ", pinned=" + pinned + ", color=" + color + ", reminder=" + reminder
				+ ", labelname=" + labelname + ", createdtime=" + createdtime + "]";
	}

}
