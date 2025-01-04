package com.application.yokbantu.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Donation implements Parcelable {
    private String id;
    private String description;
    private Integer donationCount;
    private Integer donationTarget;
    private String title;
    private Integer totalDonation;
    private Integer totalExpensesUsed;
    private Integer totalNews;
    private String thumbnailUrl;
    private Date occureedAt = null;

    public Donation(
            String id,
            String description,
            Integer donationCount,
            Integer donationTarget,
            String title,
            Integer totalDonation,
            Integer totalExpensesUsed,
            Integer totalNews,
            String thumbnailUrl
    ){
        this.id = id;
        this.description = description;
        this.donationCount = donationCount;
        this.donationTarget = donationTarget;
        this.title = title;
        this.totalDonation = totalDonation;
        this.totalExpensesUsed = totalExpensesUsed;
        this.totalNews = totalNews;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Donation(
            String id,
            String description,
            Integer donationCount,
            Integer donationTarget,
            String title,
            Integer totalDonation,
            Integer totalExpensesUsed,
            Integer totalNews,
            String thumbnailUrl,
            Date occurredAt
    ){
        this.id = id;
        this.description = description;
        this.donationCount = donationCount;
        this.donationTarget = donationTarget;
        this.title = title;
        this.totalDonation = totalDonation;
        this.totalExpensesUsed = totalExpensesUsed;
        this.totalNews = totalNews;
        this.thumbnailUrl = thumbnailUrl;
        this.occureedAt = occurredAt;
    }

    protected Donation(Parcel in) {
        id = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            donationCount = null;
        } else {
            donationCount = in.readInt();
        }
        if (in.readByte() == 0) {
            donationTarget = null;
        } else {
            donationTarget = in.readInt();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            totalDonation = null;
        } else {
            totalDonation = in.readInt();
        }
        if (in.readByte() == 0) {
            totalExpensesUsed = null;
        } else {
            totalExpensesUsed = in.readInt();
        }
        if (in.readByte() == 0) {
            totalNews = null;
        } else {
            totalNews = in.readInt();
        }
        thumbnailUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(description);
        if (donationCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(donationCount);
        }
        if (donationTarget == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(donationTarget);
        }
        dest.writeString(title);
        if (totalDonation == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalDonation);
        }
        if (totalExpensesUsed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalExpensesUsed);
        }
        if (totalNews == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalNews);
        }
        dest.writeString(thumbnailUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Donation> CREATOR = new Creator<Donation>() {
        @Override
        public Donation createFromParcel(Parcel in) {
            return new Donation(in);
        }

        @Override
        public Donation[] newArray(int size) {
            return new Donation[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDonationCount() {
        return donationCount;
    }

    public void setDonationCount(Integer donationCount) {
        this.donationCount = donationCount;
    }

    public Integer getDonationTarget() {
        return donationTarget;
    }

    public void setDonationTarget(Integer donationTarget) {
        this.donationTarget = donationTarget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalDonation() {
        return totalDonation;
    }

    public void setTotalDonation(Integer totalDonation) {
        this.totalDonation = totalDonation;
    }

    public Integer getTotalExpensesUsed() {
        return totalExpensesUsed;
    }

    public void setTotalExpensesUsed(Integer totalExpensesUsed) {
        this.totalExpensesUsed = totalExpensesUsed;
    }

    public Integer getTotalNews() {
        return totalNews;
    }

    public void setTotalNews(Integer totalNews) {
        this.totalNews = totalNews;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Date getOccurredAt() {
        return occureedAt;
    }

    public void setOccureedAt(Date occurredAt) {
        this.occureedAt = occurredAt;
    }
}
