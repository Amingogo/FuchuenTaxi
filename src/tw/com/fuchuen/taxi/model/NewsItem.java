package tw.com.fuchuen.taxi.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class NewsItem implements Parcelable {

    public String index;
    public String updateDate;
    public String content;

    public NewsItem() {}

    // Parcelable management
    private NewsItem(Parcel in) {
        index = in.readString();
        updateDate = in.readString();
        content = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(index);
        dest.writeString(updateDate);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
}
