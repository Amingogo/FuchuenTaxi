package tw.com.fuchuen.taxi.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public final class BannerImageList implements Parcelable {

	public List<BannerImage> dataList;

    public BannerImageList() {}

    // Parcelable management
    private BannerImageList(Parcel in) {
    	dataList = in.createTypedArrayList(BannerImage.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    	dest.writeTypedList(dataList);
    }

    public static final Parcelable.Creator<BannerImageList> CREATOR = new Parcelable.Creator<BannerImageList>() {
        public BannerImageList createFromParcel(Parcel in) {
            return new BannerImageList(in);
        }

        public BannerImageList[] newArray(int size) {
            return new BannerImageList[size];
        }
    };
}
