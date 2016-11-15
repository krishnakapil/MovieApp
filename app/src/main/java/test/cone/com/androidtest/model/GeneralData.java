package test.cone.com.androidtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GeneralData implements Parcelable {

    private int id;
    private String name;

    @SerializedName("profile_path")
    private String profilePath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.profilePath);
    }

    public GeneralData() {
    }

    protected GeneralData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.profilePath = in.readString();
    }

    public static final Parcelable.Creator<GeneralData> CREATOR = new Parcelable.Creator<GeneralData>() {
        public GeneralData createFromParcel(Parcel source) {
            return new GeneralData(source);
        }

        public GeneralData[] newArray(int size) {
            return new GeneralData[size];
        }
    };
}
