package test.cone.com.androidtest.model;

import com.google.gson.annotations.SerializedName;

public class Configuration {

    @SerializedName("images")
    private ImageConfiguaration imageConfiguaration;

    public ImageConfiguaration getImageConfiguaration() {
        return imageConfiguaration;
    }
}

