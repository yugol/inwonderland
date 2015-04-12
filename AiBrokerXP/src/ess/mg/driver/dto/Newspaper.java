package ess.mg.driver.dto;


public class Newspaper {

    private final int index;
    private String    title;
    private Integer   sold;
    private Integer   votes;
    private Double    price;

    public Newspaper(final int index) {
        this.index = index;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        final Newspaper other = (Newspaper) obj;
        if (title == null) {
            if (other.title != null) { return false; }
        } else if (!title.equals(other.title)) { return false; }
        return true;
    }

    public int getIndex() {
        return index;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getSold() {
        return sold;
    }

    public String getTitle() {
        return title;
    }

    public Integer getVotes() {
        return votes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (title == null ? 0 : title.hashCode());
        return result;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public void setSold(final Integer sold) {
        this.sold = sold;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setVotes(final Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return String.format("%2d %3.2f %3d %3d %s", index, price, sold, votes, title);
    }

}
