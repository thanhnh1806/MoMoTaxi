package bhtech.com.cabbydriver.object;

public class SetupSlidingMenuItemObject {
    private String title;
    private int icon;
    private int favouriteDriver;
    private boolean isFavouriteDriverVisible = false;

    public SetupSlidingMenuItemObject(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public SetupSlidingMenuItemObject(String title, int icon, boolean isFavouriteDriverVisible, int favouriteDriver) {
        this.title = title;
        this.icon = icon;
        this.isFavouriteDriverVisible = isFavouriteDriverVisible;
        this.favouriteDriver = favouriteDriver;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }

    public int getFavouriteDriver() {
        return this.favouriteDriver;
    }

    public boolean getFavouriteDriverVisible() {
        return this.isFavouriteDriverVisible;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setFavouriteDriver(int favouriteDriver) {
        this.favouriteDriver = favouriteDriver;
    }

    public void setFavouriteDriverVisibility(boolean isFavouriteDriverVisible) {
        this.isFavouriteDriverVisible = isFavouriteDriverVisible;
    }
}
