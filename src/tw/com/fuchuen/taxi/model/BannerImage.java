package tw.com.fuchuen.taxi.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class BannerImage implements Parcelable {

    public String BannerURL;
    public String BannerDesc;

    public BannerImage() {}

    // Parcelable management
    private BannerImage(Parcel in) {
        BannerURL = in.readString();
        BannerDesc = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(BannerURL);
        dest.writeString(BannerDesc);
    }

    public static final Parcelable.Creator<BannerImage> CREATOR = new Parcelable.Creator<BannerImage>() {
        public BannerImage createFromParcel(Parcel in) {
            return new BannerImage(in);
        }

        public BannerImage[] newArray(int size) {
            return new BannerImage[size];
        }
    };
}
