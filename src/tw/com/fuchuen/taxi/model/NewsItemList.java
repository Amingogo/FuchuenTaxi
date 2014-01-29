package tw.com.fuchuen.taxi.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public final class NewsItemList implements Parcelable {

	public List<NewsItem> dataList;

    public NewsItemList() {}

    // Parcelable management
    private NewsItemList(Parcel in) {
    	dataList = in.createTypedArrayList(NewsItem.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    	dest.writeTypedList(dataList);
    }

    public static final Parcelable.Creator<NewsItemList> CREATOR = new Parcelable.Creator<NewsItemList>() {
        public NewsItemList createFromParcel(Parcel in) {
            return new NewsItemList(in);
        }

        public NewsItemList[] newArray(int size) {
            return new NewsItemList[size];
        }
    };
}
