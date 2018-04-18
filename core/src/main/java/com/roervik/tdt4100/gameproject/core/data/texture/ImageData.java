package com.roervik.tdt4100.gameproject.core.data.texture;

public class ImageData {
    public int[] pixels;
    public int width;
    public int height;

    public ImageData(int width, int height, int[] data) {
        this.width = width;
        this.height = height;
        this.pixels = data;
    }
}
