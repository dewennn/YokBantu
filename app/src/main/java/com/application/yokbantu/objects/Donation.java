package com.application.yokbantu.objects;

public class Donation {
    private String id;
    private String description;
    private Integer donationCount;
    private Integer donationTarget;
    private String title;
    private Integer totalDonation;
    private Integer totalExpensesUsed;
    private Integer totalNews;

    public Donation(String id, String description, Integer donationCount, Integer donationTarget, String title, Integer totalDonation, Integer totalExpensesUsed, Integer totalNews) {
        this.id = id;
        this.description = description;
        this.donationCount = donationCount;
        this.donationTarget = donationTarget;
        this.title = title;
        this.totalDonation = totalDonation;
        this.totalExpensesUsed = totalExpensesUsed;
        this.totalNews = totalNews;
    }

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
}
