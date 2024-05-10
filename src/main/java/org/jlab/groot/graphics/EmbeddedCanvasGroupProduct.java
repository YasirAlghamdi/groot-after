package org.jlab.groot.graphics;


import java.io.Serializable;

public class EmbeddedCanvasGroupProduct implements Serializable {
	private EmbeddedCanvas canvas = new EmbeddedCanvas();
	private int currentPage = 0;
	private int maxPages = 1;

	public EmbeddedCanvas getCanvas() {
		return canvas;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}

	public void previousPage(EmbeddedCanvasGroup embeddedCanvasGroup) {
		if (currentPage > 0) {
			currentPage--;
			this.canvas.clear();
			embeddedCanvasGroup.updateCanvas();
		}
	}

	public void nextPage(EmbeddedCanvasGroup embeddedCanvasGroup) {
		if ((currentPage + 1) < maxPages) {
			currentPage++;
			embeddedCanvasGroup.updateCanvas();
		}
	}
}